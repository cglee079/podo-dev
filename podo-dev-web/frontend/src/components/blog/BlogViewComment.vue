<template>
    <div id="wrapComment" :class="$mq">
        <div id="count">
            <img src="https://image.flaticon.com/icons/svg/134/134718.svg"/>
            <a class="comment-cnt">{{this.commentCount}}</a>
            <div></div>
        </div>

        <div id="comments">
            <div v-for="comment in comments"
                 v-bind:key="comment.seq"
            >
                <comment-item
                        :blogSeq="blogSeq"
                        :comment="comment"
                        @delete="deleteBlogComment"
                        @reload="loadBlogComments"
                />
            </div>
        </div>


        <comment-write
                :blogSeq="blogSeq"
                :parentSeq="null"
                placeholder="댓글을 입력해주세요"
                @reload="loadBlogComments"
        />


    </div>
</template>

<script>
    import BlogViewCommentItem from '@/components/blog/BlogViewCommentItem'
    import {mapGetters} from 'vuex'
    import customToast from '@/mixins/customToast'
    import BlogViewCommentWrite from "./BlogViewCommentWrite";

    export default {
        name: "BlogViewComment",
        components: {
            'comment-item': BlogViewCommentItem,
            'comment-write': BlogViewCommentWrite
        },
        props: {
            blogSeq: Number,
        },
        mixins: [customToast],
        data() {
            return {
                comments: [],
            }
        },
        computed: {
            ...mapGetters([
                "isLogin", "getUser"
            ]),
            commentCount() {
                let count = 0;
                this.comments.forEach(comment => {
                    if (comment.enabled) {
                        count++;
                    }
                })

                return count
            }
        },
        methods: {
            loadBlogComments() {
                this.$axios
                    .get('/api/blogs/' + this.blogSeq + "/comments")
                    .then(res => {
                        res = res.data
                        this.comments = []
                        this.comments = res.data
                        console.log(this.comments)
                    })
                    .catch(err => {
                        console.log(err)
                    })
            },

            deleteBlogComment(commentSeq) {
                this.toastConfirm("정말 댓글을 삭제하시겠습니까?", () => {
                    this.$axios
                        .delete('/api/blogs/' + this.blogSeq + "/comments/" + commentSeq)
                        .then(res => {
                            this.$toasted.show("댓글이 삭제되었습니다")
                            this.loadBlogComments()
                        })
                        .catch(err => {
                            console.log(err)
                        })
                })
            }
        },

        created() {
            this.loadBlogComments()
        }

    }
</script>

<style lang="scss" scoped >

    #wrapComment {
        margin-top: 100px;

        &.mobile, &.tablet {
            #count {
                padding-left: 5%;
                padding-right: 5%;
            }
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

</style>
