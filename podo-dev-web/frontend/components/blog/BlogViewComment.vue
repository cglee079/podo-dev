<template>
    <div id="wrapComment" :class="$mq">
        <div id="count">
            <img src="https://image.flaticon.com/icons/svg/134/134718.svg" alt="CommentCount" />
            <a class="comment-cnt">{{ this.totalElements }}</a>
            <div></div>
        </div>

        <div
            id="btnPaging"
            @click="fetchBlogComments(page + 1, page + 1)"
            :class="hasMoreComment ? 'on' : ''"
        >
            이전 댓글 보기
        </div>

        <div id="comments" ref="comments">
            <div v-for="(comment, index) in comments" :key="comment.id">
                <comment-item
                    :blog-id="blogId"
                    :index="index"
                    :comment="comment"
                    @delete="deleteBlogComment"
                    @reload="refetchBlogComments"
                />
            </div>
        </div>

        <comment-write
            :blog-id="blogId"
            :parent-id="null"
            placeholder="댓글을 입력해주세요"
            @reload="refetchBlogComments"
        />
    </div>
</template>

<script>
import BlogViewCommentItem from "./BlogViewCommentItem";
import BlogViewCommentWrite from "./BlogViewCommentWrite";
import bus from "../../utils/bus";
import { mapGetters } from "vuex";

export default {
    name: "BlogViewComment",
    components: {
        "comment-item": BlogViewCommentItem,
        "comment-write": BlogViewCommentWrite
    },
    props: {
        blogId: Number
    },
    data() {
        return {
            page: 0,
            pageSize: 0,
            totalElements: 0,
            totalPages: 0,
            isLoading: false,
            comments: []
        };
    },
    computed: {
        ...mapGetters({
            isAdmin: "user/isAdmin",
            isLogin: "user/isLogin"
        }),

        hasMoreComment() {
            return (this.page + 1) * this.pageSize < this.totalElements;
        }
    },
    methods: {
        refetchBlogComments() {
            if (this.page > 0 && this.comments.length % this.pageSize === 0) {
                this.page++;
            }

            this.$refs.comments.classList.remove("on");
            this.comments = [];

            this.fetchBlogComments(0, this.page);
        },

        async fetchBlogComments(pageIdx, currentPage) {
            if (this.isLoading) {
                return;
            }

            bus.$emit("startSpinner");
            this.isLoading = true;

            try {
                const response = await this.$axios.$get(`/api/blogs/${this.blogId}/comments`, {
                    params: {
                        page: pageIdx
                    }
                });

                const result = response.result;
                result.contents
                    .slice()
                    .reverse()
                    .forEach(item => this.comments.unshift(item));

                this.page = pageIdx;
                this.pageSize = result.pageSize;
                this.totalElements = result.totalElements;
                this.totalPages = result.totalPages;

                if (pageIdx < currentPage) {
                    this.fetchBlogComments(pageIdx + 1, currentPage);
                    return;
                }

                this.$refs.comments.classList.add("on");
            } catch (e) {
            } finally {
                this.isLoading = false;
                bus.$emit("stopSpinner");
            }
        },

        deleteBlogComment(commentId, index) {
            this.toastConfirm("정말 댓글을 삭제하시겠습니까?", async () => {
                bus.$emit("startSpinner");
                this.isLoading = true;

                try {
                    const response = await this.$axios.$delete(
                        `/api/blogs/${this.blogId}/comments/${commentId}`
                    );

                    if (response) {
                        this.$toast.show("댓글이 삭제되었습니다");
                        this.comments[index].enabled = false;
                        this.comments[index].contents = "삭제된 댓글입니다";

                        this.totalElements--;

                        //삭제후, 총 댓글 갯수에 맞추어 다음 페이징할 위치를 맞춘다.
                        if (this.totalElements % this.pageSize === 0) {
                            this.page--;
                        }
                    }
                } finally {
                    bus.$emit("stopSpinner");
                    this.isLoading = false;
                }
            });
        }
    },

    created() {
        this.fetchBlogComments(0, 0);
    }
};
</script>

<style lang="scss" scoped>
#wrapComment {
    margin-top: 70px;

    &.mobile,
    &.tablet {
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
            background: #f1f1f1;
        }
    }

    #btnPaging {
        border-bottom: 1px solid #f1f1f1;
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
