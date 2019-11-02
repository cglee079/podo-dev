<template>
    <div id="wrapComment" :class="$mq">
        <div id="count">
            <img src="https://image.flaticon.com/icons/svg/134/134718.svg"/>
            <a class="comment-cnt">{{this.totalElements}}</a>
            <div></div>
        </div>

        <div
                id="btnPaging"
                @click="loadBlogComments(page + 1, page + 1)"
                :class="hasMoreComment ? 'on': ''"
        >
            이전 댓글 보기
        </div>

        <div id="comments" ref="comments">
            <div v-for="(comment, index) in comments"
                 v-bind:key="comment.id"
            >
                <comment-item
                        :blogId="blogId"
                        :index="index"
                        :comment="comment"
                        @delete="deleteBlogComment"
                        @reload="reloadBlogComments"
                        @onProgress="onProgress"
                        @offProgress="offProgress"
                />
            </div>
        </div>


        <comment-write
                :blogId="blogId"
                :parentId="null"
                placeholder="댓글을 입력해주세요"
                @reload="reloadBlogComments"
                @onProgress="onProgress"
                @offProgress="offProgress"
        />


    </div>
</template>

<script>
    import BlogViewCommentItem from "./BlogViewCommentItem";
    import BlogViewCommentWrite from "./BlogViewCommentWrite";
    import {mapGetters} from 'vuex'


    export default {
        name: "BlogViewComment",
        components: {
            'comment-item': BlogViewCommentItem,
            'comment-write': BlogViewCommentWrite
        },
        props: {
            blogId: Number,
        },
        data() {
            return {
                page: 0,
                pageSize: 0,
                totalElements: 0,
                totalPages: 0,
                isLoading: false,
                comments: [],
            }
        },
        computed: {
            ...mapGetters({
                isAdmin: 'user/isAdmin',
                isLogin: 'user/isLogin',
            }),

            hasMoreComment() {
                return (this.page + 1) * this.pageSize < this.totalElements
            }
        },
        methods: {
            onProgress() {
                this.$emit("onProgress")
            },

            offProgress() {
                this.$emit("offProgress")
            },

            reloadBlogComments() {
                if (this.page > 0 && this.comments.length % this.pageSize === 0) {
                    this.page++
                }

                this.$refs.comments.classList.remove("on")
                this.comments = []

                this.loadBlogComments(0, this.page)
            },

            loadBlogComments(page, until) {
                this.onProgress()

                return new Promise((resolve, reject) => {
                    if (this.isLoading) {
                        return
                    }

                    this.isLoading = true

                    this.$axios
                        .$get('/api/blogs/' + this.blogId + "/comments", {
                            params: {
                                page: page
                            }
                        })
                        .then(res => {

                            resolve(res)
                        })
                        .catch(err => {
                            reject(err)
                        })
                }).then((res) => {
                    this.offProgress()

                    res = res.data
                    res.contents.slice().reverse().forEach(item => this.comments.unshift(item))
                    this.page = page
                    this.pageSize = res.pageSize
                    this.totalElements = res.totalElements
                    this.totalPages = res.totalPages
                    this.isLoading = false

                    if (page < until) {
                        this.loadBlogComments(page + 1, until)
                    } else {
                        this.$refs.comments.classList.add("on")
                    }
                }).catch(err => {
                    this.offProgress()
                    console.log(err)
                })
            },

            deleteBlogComment(commentId, index) {
                this.toastConfirm("정말 댓글을 삭제하시겠습니까?", () => {
                    this.onProgress()

                    this.$axios
                        .$delete('/api/blogs/' + this.blogId + "/comments/" + commentId)
                        .then(() => {
                            this.offProgress()

                            this.$toast.show("댓글이 삭제되었습니다")
                            this.comments[index].enabled = false
                            this.comments[index].contents = "삭제된 댓글입니다"

                            this.totalElements--
                            if (this.totalElements % this.pageSize === 0) {
                                this.page--
                            }

                            //this.reloadBlogComments()
                        })
                        .catch(err => {
                            this.offProgress()
                        })
                })
            },

        },

        mounted() {
            this.loadBlogComments(0, 0)
        }

    }
</script>

<style lang="scss" scoped>

    #wrapComment {
        margin-top: 70px;

        &.mobile, &.tablet {
            #count {
                padding-left: 5%;
                padding-right: 5%;
            }
        }

        #comments {
            display: none;

            &.on {
                display: block;
            }
        }

        #count {
            display: flex;
            align-items: center;
            margin-bottom: 20px;
            font-size: 1.25rem;
            font-weight: bold;
            opacity: 0.7;

            img {
                width: 27px;
                margin-right: 7px;
                margin-top: 2px;
            }

            div {
                flex: 1;
                height: 5px;
                margin-left: 10px;
                margin-top: 5px;
                background: #F1F1F1;
            }
        }

        #btnPaging {
            border-bottom: 1px solid #F1F1F1;
            padding: 15px 0px;
            text-align: center;
            cursor: pointer;
            display: none;
            opacity: 0.9;

            &.on {
                display: block;
            }
        }
    }

</style>
