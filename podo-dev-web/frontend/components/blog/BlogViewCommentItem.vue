<template>
    <div
            v-bind:style="{'padding-left': (comment.depth * 2) + 'rem'}"
            class="wrap-comment"
            :class="comment.enabled ? '' : 'disabled'"
    >
        <div class="comment">
            <div class="header">
                <div class="info">
                    <a class="writer">{{comment.username}}</a>
                    <a class="create-at">{{comment.createAt}}</a>
                </div>
                <div class="menu">
                    <a v-if="comment.enabled && comment.depth < config.maxDepth" @click="clickReply">{{reply.message}}</a>
                    <a v-if="comment.enabled && comment.isMine" @click="clickCommentDelete(comment.seq, index)">삭제</a>
                </div>
            </div>

            <div class="contents">
                <span v-if="comment.depth !== 0">ㄴ </span>
                <span v-html="comment.contents"/>
            </div>

            <div id="reply">
                <component
                        :is="reply.comp"
                        :blogSeq="blogSeq"
                        :index = "index"
                        :parentSeq="comment.seq"
                        :placeholder="comment.username + ' 님에게 답글'"
                        @reload="$emit('reload')"
                        @writeListener="writeListener"
                />
            </div>
        </div>
    </div>
</template>

<script>
    import BlogViewCommentWrite from "./BlogViewCommentWrite"

    export default {
        name: "BlogViewCommentItem",
        props: {
            index : Number,
            blogSeq: Number,
            comment: Object
        },
        components: {
            'comment-write': BlogViewCommentWrite
        },
        data() {
            return {
                config: {
                    maxDepth : 3
                },
                reply: {
                    on: false,
                    message: '답글',
                    comp: null
                }
            }
        },
        methods: {
            offReply() {
                this.reply.on = false
                this.reply.message = "답글"
                this.reply.comp = null
            },

            onReply() {
                this.reply.on = true
                this.reply.message = "답글닫기"
                this.reply.comp = 'comment-write'
            },
            clickReply() {
                if (!this.reply.on) {
                    this.onReply()
                } else {
                    this.offReply()
                }
            },

            writeListener() {
                this.offReply()
            },

            clickCommentDelete(commentSeq, index) {
                this.$emit('delete', commentSeq, index)
            }
        }
    }
</script>

<style lang="scss" scoped >
    .wrap-comment {
        border-bottom: 0.5px solid #F1F1F1;

        &:hover {
            background: #FAFAFA;
        }

        &.disabled {
            opacity: 0.6;
        }

        .comment {
            padding: 25px 20px 25px 20px;

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
