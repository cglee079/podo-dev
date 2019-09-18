<template>
    <div id="wrapBlog" :class="$mq">

        <div id="forCrawl" style="display: none;">
            {{blog.desc}}
        </div>

        <div id="head">
            <div id="tags">
                <span v-for="tag in blog.tags"
                      v-bind:key="tag.seq"
                >
                    #{{tag.val}}
                </span>
            </div>

            <div id="title">
                {{blog.title}}
            </div>

            <div id="info">
                <span>{{blog.createAt}}</span>
                <span>조회수 {{blog.hitCnt}}</span>
            </div>
        </div>


        <div id="submenus">
            <span v-if="isAdmin && isLogin" @click="clickModifyBlog(blog.seq)">수정</span>
            <span v-if="isAdmin && isLogin" @click="clickDeleteBlog(blog.seq)">삭제</span>
            <span @click="clickExport()">공유하기</span>
            <span><router-link
                :to="{name : 'index', query : { search: filter.search, tag : filter.tag}}">목록</router-link></span>
            <span @click="clickBefore()">이전글</span>
            <span @click="clickNext()">다음글</span>
        </div>

        <div id="files">
            <div v-for="file in blog.files"
                 v-bind:key="file.seq"
                 class="file"
            >
                <!--                <a :href="file.domainUrl + file.path + '/' +file.filename">-->
                <a href="javascript:void(0)" @click="clickFile(file.seq)">
                    <img src="@/assets/btns/btn-file.svg" class="file-icon"/>
                    <span class="file-name">{{file.originName}}</span>
                    <span class="file-size">[{{formatFilesize(file.filesize)}}]</span>
                </a>
            </div>
        </div>

        <div id="contents">
            <client-only>
                <toast-custom-viewer ref="viewer" :value="blog.contents"/>
            </client-only>
        </div>

        <blog-view-comment
            v-if="blog.seq"
            :blogSeq="blog.seq"
        />

        <blog-view-export
            ref="export"
            :blog="blog"
        />

    </div>
</template>

<script>
    import {mapGetters} from 'vuex'
    import filesize from 'filesize'
    import BlogViewComment from "@/components/blog/BlogViewComment"
    import BlogViewExport from "@/components/blog/BlogViewExport"
    import ToastCustomViewer from "@/components/global/ToastCustomViewer"

    export default {
        name: 'BlogView',

        head() {
            return {
                title: this.meta.title,
                meta: [
                    {name: 'keywords', content: this.meta.keywords},
                    {hid: "description", name: 'description', content: this.meta.description},
                    {hid: "og:title", property: 'og:title', content: this.meta.title},
                    {hid: "og:description", property: 'og:description', content: this.meta.description},
                    {hid: "og:image", property: 'og:image', content: this.meta.thumbnail},
                    {hid: "og:url", property: 'og:url', content: this.meta.url},
                ],
                link: [
                    {rel: 'canonical', href: this.meta.url},
                ]
            }
        },

        asyncData({$axios, error, params}) {
            const seq = params.seq

            let baseUrl = process.env.externalServerUrl
            if (process.server) {
                baseUrl = process.env.internalServerUrl
            }

            return $axios.$get(baseUrl + '/api/blogs/' + seq)
                .then(res => {
                    const blog = res.data

                    const keywords = blog.tags.map(tag => {
                        return tag.val
                    })

                    const meta = {
                        url: process.env.frontendUrl + "/blogs/" + blog.seq,
                        title: process.env.name + " : " + blog.title,
                        keywords: keywords.join(", "),
                        date: blog.createAt,
                        description: blog.desc.length > 300 ? blog.desc.substring(0, 300) : blog.desc,
                        thumbnail: blog.thumbnail ? blog.thumbnail : '/og-image.png',
                    }

                    return {
                        blog,
                        meta,
                    }

                })
                .catch(err => {
                    console.log(err)
                    error({
                        statusCode: 404
                    })
                })
        },


        data() {
            return {
                meta: {},
                blog: {},
                filter: {}
            }
        },

        components: {
            BlogViewComment,
            BlogViewExport,
            ToastCustomViewer
        },

        computed: {
            ...mapGetters({
                isAdmin: 'user/isAdmin',
                isLogin: 'user/isLogin',
            })
        },

        methods: {
            clickModifyBlog(seq) {
                this.$router.push({
                    name: 'blogs-seq-post',
                    params: {
                        seq: seq
                    }
                });
            },

            clickDeleteBlog(seq) {
                this.toastConfirm("정말 삭제하시겠습니까?", () => {
                    this.$axios
                        .delete('/api/blogs/' + seq)
                        .then(res => {
                            this.$router.push({name: 'index'})
                        })
                        .catch(err => {
                            console.log(err)
                        })
                })
            },

            clickExport() {
                this.$refs.export.onExport()
            },

            clickBefore() {
                const seq = this.blog.before
                if (!seq) {
                    this.$toast.show("이전글이 없습니다")
                    return
                }
                this.$router.push({
                    name: 'blogs-seq',
                    params: {
                        'seq': seq
                    }
                })
            },

            clickNext() {
                const seq = this.blog.next
                console.log(seq)
                if (!seq) {
                    this.$toast.show("다음글이 없습니다")
                    return
                }

                this.$router.push({
                    name: 'blogs-seq',
                    params: {
                        'seq': seq
                    }
                })
            },

            clickFile(fileSeq) {
                window.location.href = process.env.externalServerUrl + "/api/blogs/" + this.blog.seq + "/files/" + fileSeq
            },

            formatFilesize(value) {
                return filesize(value)
            },

            increaseHitCount(seq) {
                this.$axios.$post('/api/blogs/' + seq + '/hitCount')
            },

        },

        mounted() {
            this.filter.search = this.$route.query.search
            this.filter.tag = this.$route.query.tag

            const seq = this.blog.seq
            const HIT_BLOGS = 'HIT_BLOGS'
            let hitBlogs = this.$storage.getLocalStorage(HIT_BLOGS)

            if (!hitBlogs) {
                hitBlogs = []
            }

            if (!hitBlogs.includes(seq)) {
                hitBlogs.push(seq)

                this.$storage.setLocalStorage(HIT_BLOGS, hitBlogs)

                this.increaseHitCount(seq)
            }
        }

    }
</script>

<style lang="scss" scoped>
    #wrapBlog {
        max-width: 800px;
        margin: 0px auto;

        &.mobile, &.tablet {
            #head, #submenus, #contents {
                padding-left: 5%;
                padding-right: 5%;
            }

            #head {
                margin: 150px 10px;

                #title {
                    font-size: 1.6rem;
                }
            }

            #files {
                .file {
                    padding-left: 5%;
                    padding-right: 5%;
                }
            }
        }

        #head {
            margin: 150px 0;

            #tags {
                margin-bottom: 15px;
                text-align: center;
                font-weight: bold;
                color: #ec5621;
                cursor: pointer;

                span {
                    margin: 0px 5px;
                }

            }

            #title {
                font-size: 2.1rem;
                margin-bottom: 15px;
                text-align: center;

                word-break: keep-all;
                word-wrap: break-word;
            }

            #info {
                display: flex;
                flex-flow: row nowrap;
                justify-content: center;
                align-items: center;
                font-size: 0.75rem;
                padding: 0 2px;
                color: #9199A4;

                span {
                    margin: 0px 5px;
                }
            }

        }


        #submenus {
            border-top: 1px solid #9199a4;
            border-bottom: 1px solid #ecf0f5;
            padding: 8px 0;

            display: flex;
            align-items: center;
            justify-content: flex-end;

            span {
                cursor: pointer;
                margin-left: 10px;
                font-size: 0.9rem;
            }
        }

        #files {
            margin-top: 20px;
            text-align: right;


            .file {
                margin: 5px 0px;
                opacity: 0.95;
                white-space: nowrap;

                a {
                    display: flex;
                    justify-content: flex-end;
                    align-items: center;

                    .file-icon {
                        width: 15px;
                        margin-top: 5px;
                    }

                    .file-name {
                        margin-left: 5px;
                        overflow: hidden;
                        max-width: 50%;
                        text-overflow: ellipsis
                    }

                    .file-size {
                        margin-left: 10px;
                    }
                }


            }
        }

        #contents {
            margin-top: 70px;
            margin-bottom: 150px;
            font-size: 1rem !important;
        }

    }
</style>

