<template>
    <div
            v-bind:style="{'margin-left': (comment.depth * 2) + 'rem'}"
            class="comment"
            :class="comment.enabled ? '' : 'disabled'"
    >

        <div class="header">
            <div class="info">
                <a class="writer">{{comment.username}}</a>
                <a class="create-at">{{comment.createAt}}</a>
            </div>
            <div class="menu">
                <a v-if="comment.enabled" @click="clickReply">{{reply.message}}</a>
                <a v-if="comment.enabled && comment.isMine" @click="clickCommentDelete(comment.seq)">삭제</a>
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
                    :parentSeq="comment.seq"
                    :placeholder="comment.username + ' 님에게 답글'"
                    @reload="$emit('reload')"
                    @writeListener="writeListener"
            />
        </div>

    </div>
</template>

<script>
    import BlogViewCommentWrite from "./BlogViewCommentWrite";

    export default {
        name: "BlogViewCommentItem",
        props: {
            blogSeq: Number,
            comment: Object
        },
        components: {
            'comment-write': BlogViewCommentWrite
        },
        data() {
            return {
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

            clickCommentDelete(commentSeq) {
                this.$emit('delete', commentSeq)
            }
        }
    }
</script>

<style scoped lang="scss">
    .comment {
        padding: 30px 5px 25px 5px;
        border-bottom: 1px solid #F1F1F1;

        &.disabled {
            opacity: 0.6;
        }

        .header {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;

            .info {
                .writer {
                    color: #4c2b2b;
                    font-weight: bold;
                    margin-right: 5px;
                }

                .create-at {
                    color: #777;
                    font-size: 0.8rem;
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


</style>
