<template>
    <div id="wrapComment">
        <div id="count">댓글 (<a class="comment-cnt">{{comments.length}}</a>)</div>

        <div id="comments">
            <div v-for="comment in comments"
                 v-bind:key="comment.seq"
            >
                <comment-item
                        :comment="comment"
                        @delete="deleteBlogComment"
                />
            </div>
        </div>


        <div id="write">
            <textarea id="contents" :placeholder="write.placeholder" v-model="input.contents" :disabled="!isLogin"/>
            <div id="sub">
                <div id="user">
                    <div id="username">
                        <input type="text" placeholder="이름" v-model="input.username" disabled/>
                    </div>
                </div>
                <div id="submit" @click="clickCommentPost">등록</div>
            </div>
        </div>


    </div>
</template>

<script>
    import BlogViewCommentItem from '@/components/blog/BlogViewCommentItem'
    import {mapGetters} from 'vuex'
    import customToast from '@/mixins/customToast'

    export default {
        name: "BlogViewComment",
        components: {
            'comment-item': BlogViewCommentItem
        },
        props: {
            blogSeq: Number,
        },
        mixins: [customToast],
        data() {
            return {
                comments: [],
                write: {
                    placeholder: '로그인 후 댓글을 입력해주세요'
                },
                input: {
                    username: '',
                    password: '',
                    contents: ''
                }
            }
        },
        computed: {
            ...mapGetters([
                "isLogin", "getUser"
            ])
        },
        methods: {
            clickCommentPost() {
                if (!this.isLogin) {
                    return
                }

                this.$axios
                    .post('/api/blogs/' + this.blogSeq + "/comments", {
                        username: this.input.username,
                        password: this.input.password,
                        contents: this.input.contents
                    })
                    .then(res => {
                        this.$toasted.show("댓글이 등록되었습니다")
                        this.input.contents = ''
                        this.loadBlogComments()
                    })
                    .catch(err => {
                        console.log(err)
                    })
            },

            loadBlogComments() {
                this.$axios
                    .get('/api/blogs/' + this.blogSeq + "/comments")
                    .then(res => {
                        res = res.data
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

        mounted() {
            if (this.isLogin) {
                this.input.username = this.getUser.name
                this.write.placeholder = "댓글을 입력해주세요"
            }
        },

        created() {
            this.loadBlogComments()
        }

    }
</script>

<style scoped lang="scss">

    #wrapComment {
        margin-top: 100px;
    }

    #count {
        display: none;
        font-size: 1.25rem;
        font-weight: bold;
    }

    /*** Comment Write ****/
    #write {
        margin-top: 20px;

        #contents {
            width: 100%;
            box-sizing: border-box;
            height: 7rem;
            padding: 10px;
            resize: none;
            overflow: hidden;
            border: 0.7px solid #E1E1E1;
            border-radius: 3px;
        }

        #sub {
            display: flex;
            justify-content: space-between;
            align-items: baseline;

            #user {
                display: flex;
                justify-content: space-between;
                margin-top: 5px;
                height: 2rem;

                div {
                    margin-right: 3px;
                }

                input {
                    width: 7rem;
                    height: 2rem;
                    font-size: 0.8rem;
                    padding-left: 5px;
                    border: 0.7px solid #E1E1E1;
                    border-radius: 3px;
                }
            }

            #submit {
                display: flex;
                justify-content: center;
                align-items: center;
                height: 2rem;
                font-size: 0.8rem;
                padding: 0px 20px;
                color: #FFFFFF;
                border-radius: 3px;
                background: #222222;
                cursor: pointer;
            }

        }
    }


    /*!** Comment Modify ***!*/
    /*.comment-modify {*/
    /*    display: flex;*/
    /*    flex-flow: row wrap;*/
    /*    height: 50px;*/
    /*}*/

    /*.comment-modify .comment-write-contents {*/
    /*    flex: 1;*/
    /*    height: 100%;*/
    /*    resize: none;*/
    /*    overflow: hidden;*/
    /*    border: 0.7px solid #DDD;*/
    /*}*/

    /*.comment-modify .comment-write-submit {*/
    /*    color: #FFF;*/
    /*    height: 100%;*/
    /*    padding: 0 10px;*/
    /*    background: #222;*/
    /*    cursor: pointer;*/
    /*    font-size: 0.75rem;*/
    /*}*/

    /*.btn-modify.open {*/
    /*    opacity: 1;*/
    /*}*/


    /*!* Comment Reply *!*/
    /*.comment-reply {*/
    /*    display: flex;*/
    /*    width: 80%;*/
    /*    margin: 5px auto;*/
    /*    height: 20px;*/
    /*    font-size: 0.75rem;*/
    /*}*/

    /*.comment-reply-content {*/
    /*    flex: 1;*/
    /*    height: 100%;*/
    /*    overflow-y: auto;*/
    /*    overflow-x: hidden;*/
    /*    resize: none;*/
    /*}*/

    /*.comment-reply-submit {*/
    /*    color: #FFF;*/
    /*    padding: 0 10px;*/
    /*    height: 100%;*/
    /*    background: #222;*/
    /*    cursor: pointer;*/
    /*    font-size: 0.75rem;*/
    /*}*/

    /*.btn-reply.open {*/
    /*    opacity: 1;*/
    /*}*/
</style>
