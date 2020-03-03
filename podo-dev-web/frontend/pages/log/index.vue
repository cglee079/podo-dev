<template>
    <section id="logs" :class="$mq">
        <div id="tags" class="item">
            <h2 class="item-header">
                TAG
            </h2>
            <span v-for="tag in tags" :key="tag" class="tag">
                <nuxt-link :to="{ name: 'index', query: { tag: tag } }">#{{ tag }}</nuxt-link>
            </span>
        </div>

        <div id="recentComments" class="item">
            <h2 class="item-header">
                RECENT COMMENT
            </h2>
            <div v-for="comment in recentComments" :key="comment.id" class="comment">
                <nuxt-link :to="{ name: 'blogs-id', params: { id: comment.blogId } }">
                    <div class="writer-icon">
                        <comment-writer-icon :writer="comment.writer"/>
                    </div>
                    <div class="writer-name">
                        {{ comment.writer.username }}
                    </div>
                    <div class="contents">
                        {{ comment.contents }}
                    </div>
                    <div class="create-at">
                        {{ comment.createAt }}
                    </div>
                </nuxt-link>
            </div>
        </div>

        <div id="archive" class="item">
            <h2 class="item-header">
                ARCHIVE
            </h2>
            <div
                v-for="key in Object.keys(archive).sort((a, b) => b - a)"
                :key="key"
                class="archive-group"
            >
                <div class="key">
                    <span>{{ key }}</span>
                </div>

                <div class="values">
                    <div
                        v-for="blog in archive[key]"
                        :key="blog.id"
                        class="blog"
                        :class="blog.enabled ? '' : 'disabled'"
                    >
                        <nuxt-link :to="{ name: 'blogs-id', params: { id: blog.id } }">
                            <div class="title">
                                <span>{{ blog.title }}</span>
                            </div>
                            <div class="publish-at">
                                {{ blog.publishAt }}
                            </div>
                        </nuxt-link>
                    </div>
                </div>
            </div>
        </div>
    </section>
</template>

<script>
import CommentWriterIcon from "../../components/global/CommentWriterIcon";

export default {
    name: "Log",
    components: {CommentWriterIcon},
    head() {
        return {
            title: `${process.env.NAME} : log`,
            meta: [{ property: "og:description", content: "podo-dev, log" }],
            link: [{ rel: "canonical", href: `${process.env.STATIC_URL}/log` }]
        };
    },
    data() {
        return {
            tags: {},
            recentComments: {},
            archive: {}
        };
    },
    async asyncData({ $axios, app }) {
        const tags = await $axios.$get(`${app.$baseUrl()}/api/tags`);
        const recentComments = await $axios.$get(`${app.$baseUrl()}/api/comments/recent`);
        const archive = await $axios.$get(`${app.$baseUrl()}/api/blogs/archive`);

        return {
            tags: tags.result.contents,
            recentComments: recentComments.result.contents,
            archive: archive.result
        };
    }
};
</script>

<style lang="scss" scoped>
#logs {
    max-width: 800px;
    margin: 0px auto;

    &.tablet,
    &.mobile {
        padding: 0px 20px;
    }
}

#logs > .item {
    margin-top: 80px;

    > .item-header {
        border-bottom: 1px solid #e1e1e1;
        color: #036dd6;
        font-size: 1.5rem;
        padding-bottom: 5px;
        margin-bottom: 15px;
    }
}

#logs div#tags {
    margin-top: 40px;

    > span.tag {
        cursor: pointer;
        display: inline-block;
        margin: 5px;
        color: #333333;

        &:hover {
            color: #000000;
        }
    }
}

#logs div#recentComments {
    > div.comment > a {
        display: flex;
        margin: 10px 0;
        font-size: 0.95rem;
        cursor: pointer;
        color: #333333;
        padding-bottom: 3px;
        border-bottom: 1px solid #ffffff;

        &:hover {
            color: #111111;
            border-bottom: 1px solid #111111;
        }

        > .writer-icon{
            margin-right: 6px;
        }

        > .writer-name {
            font-weight: bold;
            width: 77px;
            overflow: hidden;
            text-overflow: ellipsis;
            max-height: 1.45rem;
            display: -webkit-box;
            -webkit-box-orient: vertical;
            white-space: normal;
            -webkit-line-clamp: 1;
            word-break: break-all;
            margin-right: 10px;
        }

        > .contents {
            flex: 1;
            overflow: hidden;
            text-overflow: ellipsis;
            max-height: 1.45rem;

            display: -webkit-box;
            -webkit-box-orient: vertical;
            white-space: normal;
            -webkit-line-clamp: 1;
            word-break: break-all;
        }

        > .create-at {
            min-width: 120px;
            text-align: right;
            font-size: 0.9rem;
        }
    }
}

#logs > div#archive {
    > .archive-group {
        margin-bottom: 35px;

        > .key {
            font-weight: bold;
            font-size: 1.4rem;
            margin-bottom: 10px;
        }

        > .values {
            > .blog {
                &.disabled {
                    opacity: 0.5;
                }

                > a {
                    font-size: 0.95rem;
                    margin: 5px 0px;
                    display: flex;
                    cursor: pointer;
                    border-bottom: 1px solid #ffffff;
                    color: #333333;

                    &:hover {
                        color: #111111;
                        border-bottom: 1px solid #222222;
                    }

                    > .title {
                        flex: 1;
                        display: -webkit-box;

                        overflow: hidden;
                        text-overflow: ellipsis;
                        max-height: 1.45rem;
                        -webkit-box-orient: vertical;
                        white-space: normal;
                        -webkit-line-clamp: 1;
                        word-break: break-all;
                    }

                    > .publish-at {
                        min-width: 150px;
                        text-align: right;
                        font-size: 0.9rem;
                    }
                }
            }
        }
    }
}
</style>
