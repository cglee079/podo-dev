<template>
    <div id="wrapBlog" :class="$mq">
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
            <span><router-link :to="{name : 'BlogList'}">목록</router-link></span>
            <span @click="clickBefore()">이전글</span>
            <span @click="clickNext()">다음글</span>
        </div>

        <div id="contents">
            <toast-custom-viewer :value="blog.contents"/>
        </div>

        <blog-view-comment
                v-if="blog.seq"
                :blogSeq="blog.seq"
        />

        <the-export ref="export"
                    :blog="blog"
        />

    </div>
</template>

<script>
    import BlogViewComment from './BlogViewComment'
    import TheExport from "./BlogViewExport"
    import {mapGetters} from 'vuex'
    import customToast from '@/mixins/customToast'

    export default {
        name: 'BlogVue',
        components: {
            'blog-view-comment': BlogViewComment,
            'the-export': TheExport
        },
        mixins: [customToast],
        watch: {
            $route() {
                const seq = this.$route.params.seq
                this.loadBlog(seq)
            }
        },
        data() {
            return {
                blog: {},
            }
        },
        computed: {
            ...mapGetters([
                'isAdmin', 'isLogin'
            ]),
        },
        methods: {
            clickModifyBlog(seq) {
                this.$router.push({
                    name: 'BlogModify',
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
                            this.$router.push({name: 'BlogList'})
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
                    this.$toasted.show("이전글이 없습니다")
                    return
                }
                this.$router.push({
                    name: 'BlogView',
                    params: {
                        'seq': seq
                    }
                })
            },

            clickNext() {
                const seq = this.blog.next
                if (!seq) {
                    this.$toasted.show("다음글이 없습니다")
                    return
                }

                this.$router.push({
                    name: 'BlogView',
                    params: {
                        'seq': seq
                    }
                })
            },

            increaseHitCount(seq) {
                return new Promise((resolve, reject) => {
                    this.$axios
                        .post('/api/blogs/' + seq + '/hitCount')
                        .then(res => {
                            resolve(res)
                        })
                        .catch(err => {
                            reject(err)
                        })
                })
            },

            loadBlog(seq) {
                this.$axios
                    .get('/api/blogs/' + seq)
                    .then(res => {
                        res = res.data
                        this.blog = res.data
                        console.log(this.blog)
                    })
                    .catch(err => {
                        console.log(err)
                    })
            }
        },

        created() {
            const seq = this.$route.params.seq

            const COOKIE_ID = 'blogView'
            let blogView = JSON.parse(this.$cookies.get(COOKIE_ID))

            if (!blogView) {
                blogView = []
            }

            if (!blogView.includes(parseInt(seq))) {
                blogView.push(parseInt(seq))

                this.$cookies.set(COOKIE_ID, JSON.stringify(blogView))

                this.increaseHitCount(seq)
                    .then(res => {
                        this.loadBlog(seq)
                    })
                    .catch(err => {
                        console.log(err)
                    })

                return
            }

            this.loadBlog(seq)
        }
    }
</script>

<style scoped lang="scss">
    #wrapBlog {
        max-width: 800px;
        margin: 0px auto;

        &.mobile {
            margin: 0px 20px;
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

        #contents {
            margin-top: 50px;
            margin-bottom: 150px;
            font-size: 1rem !important;
        }

        &.mobile {
            #head {
                margin: 100px 10px;

                #title {
                    font-size: 1.6rem;
                }
            }
        }
    }
</style>

