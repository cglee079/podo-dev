<template>
    <div
        class="wrap-comment"
        :style="{ 'padding-left': `${comment.depth * 2}rem` }"
        :class="[comment.enabled ? '' : 'disabled', $mq]"
    >
        <div class="comment">
            <div class="header">
                <div class="info">
                    <comment-writer-icon :writer="comment.writer"/>
                    <a class="writer">{{ comment.writer.username }}</a>
                    <a class="create-at">{{ comment.createAt }}</a>
                </div>
                <div class="menu">
                    <a
                        v-if="comment.enabled && comment.depth < config.maxDepth"
                        @click="clickReply"
                    >
                        {{ reply.message }}
                    </a>
                    <a
                        v-if="comment.enabled && comment.isMine"
                        @click="clickCommentDelete(comment.id, index)"
                    >
                        삭제
                    </a>
                </div>
            </div>

            <div class="contents">
                <span v-if="comment.depth !== 0">ㄴ</span>
                <span v-html="comment.contents"></span>
            </div>

            <div id="reply">
                <component
                    :is="reply.component"
                    :blog-id="blogId"
                    :index="index"
                    :parent-id="comment.id"
                    :placeholder="`${comment.writer.username} 님에게 답글`"
                    @reload="$emit('reload')"
                    @writeListener="writeListener"
                />
            </div>
        </div>
    </div>
</template>

<script>
import BlogViewCommentWrite from "./BlogViewCommentWrite";
import CommentWriterIcon from "../global/CommentWriterIcon";

export default {
    name: "BlogViewCommentItem",
    props: {
        index: Number,
        blogId: Number,
        comment: Object
    },
    components: {
        "comment-write": BlogViewCommentWrite,
        CommentWriterIcon
    },
    data() {
        return {
            config: {
                maxDepth: 3
            },
            reply: {
                on: false,
                message: "답글",
                component: null
            }
        };
    },
    methods: {
        offReply() {
            this.reply.on = false;
            this.reply.message = "답글";
            this.reply.component = null;
        },

        onReply() {
            this.reply.on = true;
            this.reply.message = "답글닫기";
            this.reply.component = "comment-write";
        },

        clickReply() {
            if (!this.reply.on) {
                this.onReply();
            } else {
                this.offReply();
            }
        },

        writeListener() {
            this.offReply();
        },

        clickCommentDelete(commentId, index) {
            this.$emit("delete", commentId, index);
        }
    }
};
</script>

<style lang="scss" scoped>
.wrap-comment {
    border-bottom: 0.5px solid #f1f1f1;

    &.mobile,
    &.tablet {
        margin-left: 5%;
        margin-right: 5%;
    }

    &:hover {
        background: #fafafa;
    }

    &.disabled {
        opacity: 0.6;
    }

    .comment {
        padding: 4% 3%;

        .header {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;

            .info {
                .writer {
                    color: #4c2b2b;
                    font-weight: bold;
                }

                .create-at {
                    color: #ab8888;
                    font-size: 0.85rem;
                    margin-left: 10px;
                }
            }

            .menu {
                font-size: 0.8rem;
                color: #444;
                text-align: right;

                a {
                    margin-left: 7px;
                    cursor: pointer;
                }
            }
        }

        .contents {
            color: #444;
            font-size: 0.9rem;
            word-break: break-all;
            word-wrap: break-word;
        }

        #reply {
            margin-left: 30px;
        }
    }
}
</style>
