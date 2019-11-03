<template>
    <section id="logs" v-bind:class="$mq">
        <div id="tags" class="item">
            <h2 class="item-header">TAG</h2>
            <span v-for="tag in tags"
                  v-bind:key="tag"
                  class="tag"
            >
                <router-link :to="{name: 'index', query: {tag: tag}}">
                #{{tag}}
                </router-link>

            </span>
        </div>

        <div id="recentComments" class="item">
            <h2 class="item-header">RECENT COMMENT</h2>
            <div v-for="comment in recentComments"
                 v-bind:key="comment.id"
                 class="comment"
            >
                <router-link :to="{name: 'blogs-id', params: {id: comment.blogId}}">
                    <div class="username">{{comment.username}}</div>
                    <div class="contents">{{comment.contents}}</div>
                    <div class="create-at">{{comment.createAt}}</div>
                </router-link>
            </div>
        </div>

        <div id="archive" class="item">
            <h2 class="item-header">ARCHIVE</h2>
            <div v-for="key in Object.keys( archive ).sort( ( a , b ) => b - a)"
                 v-bind:key="key"
                 class="archive-group"
            >
                <div class="key">
                    <span>{{key}}</span>
                </div>

                <div class="values">
                    <div v-for="blog in archive[key]"
                         v-bind:key="blog.id"
                         class="blog">

                        <router-link :to="{name: 'blogs-id', params: {id: blog.id}}">
                            <div class="title"><span> {{blog.title}}</span></div>
                            <div class="publish-at">{{blog.publishAt}}</div>
                        </router-link>
                    </div>
                </div>

            </div>
        </div>


    </section>
</template>

<script>

    export default {
        name: 'Log',
        head() {
            return {
                title: process.env.name + " :  log",
                meta: [
                    {property: 'og:description', content: "podo-dev, log"},
                ],
                link: [
                    {rel: 'canonical', href: process.env.frontendUrl + "/log"},
                ]

            }
        },
        data() {
            return {
                tags: {},
                recentComments: {},
                archive: {},
            }
        },
        async asyncData({$axios}) {
            let baseUrl = process.env.externalServerUrl
            if (process.server) {
                baseUrl = process.env.internalServerUrl
            }

            const tags = await $axios.$get(baseUrl + '/api/tags')
            const recentComments = await $axios.$get(baseUrl + '/api/comments/recent')
            const archive = await $axios.$get(baseUrl + '/api/blogs/archive')

            return {
                tags: tags.data,
                recentComments: recentComments.data,
                archive: archive.data
            }
        },

    }
</script>

<style lang="scss" scoped>
    #logs {
        max-width: 800px;
        margin: 0px auto;

        &.tablet, &.mobile {
            padding: 0px 20px;
        }
    }


    #logs > .item {
        margin-top: 80px;

        > .item-header {
            border-bottom: 1px solid #E1E1E1;
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
            margin: 10px 0px;
            font-size: 0.95rem;
            cursor: pointer;
            color: #333333;
            padding-bottom: 3px;
            border-bottom: 1px solid #FFFFFF;

            &:hover {
                color: #111111;
                border-bottom: 1px solid #111111;
            }

            > .username {
                font-weight: bold;
                width: 80px;
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

                > .blog > a {
                    font-size: 0.95rem;
                    margin: 5px 0px;
                    display: flex;
                    cursor: pointer;
                    border-bottom: 1px solid #FFFFFF;
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

</style>
