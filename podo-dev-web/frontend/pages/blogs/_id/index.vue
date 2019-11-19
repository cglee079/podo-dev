<template>
    <section id="wrapBlog" :class="$mq">
        <progress-bar ref="progressBar"/>
        <blog-view-export
            ref="export"
            :blog="blog"
        />

        <div>
            <div id="head">
                <div id="tags">
                <span v-for="tag in blog.tags"
                      v-bind:key="tag.id"
                >
                    <router-link :to="{name: 'index', query: {tag: tag.tagValue}}"> #{{tag.tagValue}}</router-link>
                </span>
                </div>

                <h1 id="title">
                    {{blog.title}}
                </h1>

                <div id="info">
                    <span>{{blog.publishAt}}</span>
                    <span>조회수 {{blog.hitCount}}</span>
                </div>
            </div>

            <div id="submenus">
                <span v-if="isAdmin && isLogin" @click="clickModifyBlog(blog.id)">수정</span>
                <span v-if="isAdmin && isLogin" @click="clickDeleteBlog(blog.id)">삭제</span>
                <span @click="clickExport()">공유하기</span>
                <span><router-link
                    :to="{name : 'index', query : { search: filter.search, tag : filter.tag}}">목록</router-link></span>
                <span @click="clickBefore()">이전글</span>
                <span @click="clickNext()">다음글</span>
            </div>

            <div id="attachFiles">
                <div v-for="attachFile in blog.attachFiles"
                     v-bind:key="attachFile.id"
                     class="attachFile"
                >
                    <span @click="clickFile(attachFile.id)">
                        <img src="@/assets/btns/btn-file.svg" class="file-icon"/>
                        <span class="file-name">{{attachFile.originName}}</span>
                        <span class="file-size">[{{formatFilesize(attachFile.filesize)}}]</span>
                    </span>
                </div>
            </div>

            <div id="contents">
                <toast-custom-viewer ref="viewer" :value="blog.contents"/>
            </div>

            <blog-view-relates
                :blogId="blog.id"
                :blogTitle="blog.title"
                :relates="blog.relates"
            />

            <blog-view-comment
                v-if="blog.id"
                :blogId="blog.id"
                @onProgress="onProgress"
                @offProgress="offProgress"
            />


        </div>

    </section>
</template>

<script>
    import {mapGetters} from 'vuex'
    import filesize from 'filesize'
    import BlogViewComment from "@/components/blog/BlogViewComment"
    import BlogViewExport from "@/components/blog/BlogViewExport"
    import BlogViewRelates from "@/components/blog/BlogViewRelates"
    import ToastCustomViewer from "@/components/global/ToastCustomViewer"
    import ProgressBar from "@/components/global/ProgressBar";

    export default {
        name: 'BlogView',
        components: {
            ProgressBar,
            BlogViewComment,
            BlogViewExport,
            BlogViewRelates,
            ToastCustomViewer
        },

        mounted() {
            this.filter.search = this.$route.query.searchw
            this.filter.tag = this.$route.query.tag

            const id = this.blog.id
            const HIT_BLOGS = 'HIT_BLOGS'
            let hitBlogs = this.$storage.getLocalStorage(HIT_BLOGS)

            if (!hitBlogs) {
                hitBlogs = []
            }

            if (!hitBlogs.includes(id)) {
                hitBlogs.push(id)

                this.$storage.setLocalStorage(HIT_BLOGS, hitBlogs)

                this.increaseHitCount(id)
            }
        },

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
                    {hid: "article:mobile_url", property: 'article:mobile_url', content: this.meta.url},
                    {hid: "article:pc_url", property: 'article:pc_url', content: this.meta.url},
                    {hid: "article:pc_view_url", property: 'article:pc_view_url', content: this.meta.url},
                    {
                        hid: "article:talk_channel_view_url",
                        property: 'article:talk_channel_view_url',
                        content: this.meta.url
                    },
                    {hid: "plink", property: 'plink', content: this.meta.url},
                    {hid: "dg:plink", property: 'dg:plink', content: this.meta.url},
                ],
                link: [
                    {rel: 'canonical', href: this.meta.url},
                ]
            }
        },

        asyncData({$axios, error, params}) {
            const id = params.id

            let baseUrl = process.env.externalServerUrl
            if (process.server) {
                baseUrl = process.env.internalServerUrl
            }

            return $axios.$get(baseUrl + '/api/blogs/' + id)
                .then(res => {
                    const blog = res.result

                    const keywords = blog.tags.map(tag => {
                        return tag.val
                    })

                    const meta = {
                        url: process.env.frontendUrl + "/blogs/" + blog.id,
                        title: process.env.name + " : " + blog.title,
                        keywords: keywords.join(", "),
                        description: blog.desc.length > 300 ? blog.desc.substring(0, 300) : blog.desc,
                        thumbnail: blog.thumbnail ? blog.thumbnail : '/og-image.png',
                        createAt: blog.createAt,
                        publishAt: blog.publishAt,
                        updateAt: blog.updateAt,
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


        computed: {
            ...mapGetters({
                isAdmin: 'user/isAdmin',
                isLogin: 'user/isLogin',
            })
        },

        methods: {
            onProgress() {
                this.$refs.progressBar.on()
            },

            offProgress() {
                this.$refs.progressBar.off()
            },

            clickModifyBlog(blogId) {
                this.$router.push({
                    name: 'blogs-id-post',
                    params: {
                        id: id
                    }
                });
            },

            clickDeleteBlog(id) {
                this.toastConfirm("정말 삭제하시겠습니까?", () => {
                    this.$axios
                        .delete('/api/blogs/' + id)
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
                const id = this.blog.before
                if (!id) {
                    this.$toast.show("이전글이 없습니다")
                    return
                }
                this.$router.push({
                    name: 'blogs-id',
                    params: {
                        'id': id
                    }
                })
            },

            clickNext() {
                const id = this.blog.next
                if (!id) {
                    this.$toast.show("다음글이 없습니다")
                    return
                }

                this.$router.push({
                    name: 'blogs-id',
                    params: {
                        'id': id
                    }
                })
            },

            clickFile(fileId) {
                window.location.href = process.env.externalServerUrl + "/api/blogs/" + this.blog.id + "/files/" + fileId
            },

            formatFilesize(value) {
                return filesize(value)
            },

            increaseHitCount(id) {
                this.$axios.$post('/api/blogs/' + id + '/hitCount')
            },

        },


    }
</script>

<style lang="scss" scoped>
    #wrapBlog {
        max-width: var(--max-width);
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

            #attachFiles {
                .attachFile {
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

                span {
                    margin: 0px 5px;
                }

            }

            #title {
                font-size: 2.1rem;
                margin-bottom: 15px;
                text-align: center;
                font-weight: bold;
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

        #attachFiles {
            margin-top: 20px;
            text-align: right;


            .file {
                margin: 5px 0px;
                opacity: 0.95;
                white-space: nowrap;

                > span {
                    display: flex;
                    justify-content: flex-end;
                    align-items: center;
                    cursor: pointer;

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

