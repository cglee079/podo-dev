<template>
    <nuxt-link
        class="blog-row"
        :class="[blog.enabled ? '' : 'disabled', $mq]"
        :to="{
            name: 'blogs-id',
            params: { id: blog.id },
            query: { search: filter.search, tag: filter.tag }
        }"
    >
        <div v-if="blog.thumbnail != null" class="wrap-thumbnail">
            <img :src="blog.thumbnail" class="thumbnail" alt="thumbnail" />
        </div>

        <div class="content">
            <div class="title">{{ blog.title }}</div>

            <div v-html="blog.description" class="description"></div>

            <div class="info">
                <div class="tags">
                    <span
                        v-for="tag in blog.tags"
                        :key="tag.tagValue"
                        @click.stop.prevent="clickTag(tag.tagValue)"
                        class="tag"
                        :class="
                            filter.tag && tag.tagValue.toUpperCase() === filter.tag.toUpperCase()
                                ? 'on'
                                : ''
                        "
                    >
                        #{{ tag.tagValue }}
                    </span>
                </div>
                <div class="subinfo">
                    <span>{{ blog.publishAt }}</span>
                    <span class="hit-count">
                        <img src="../../assets/icons/icon-hit-count.svg" alt="hitCount" />
                        <span>{{ blog.hitCnt }}</span>
                    </span>

                    <nuxt-link :to="{ name: 'blogs-id', params: { id: blog.id } , hash : '#comment'}"  class="comment-count">
                        <img src="../../assets/icons/icon-comment2.svg" alt="commentCount" />
                        <span>{{ blog.commentCount }}</span>
                    </nuxt-link>
                </div>
            </div>
        </div>
    </nuxt-link>
</template>

<script>
export default {
    name: "BlogListItem",
    props: {
        blog: Object,
        filter: Object
    },
    methods: {
        clickTag(tagValue) {
            if (this.filter.tag === tagValue) {
                this.$router.push({ name: "blogs" });
                return;
            }

            this.$router.push({ name: "blogs", query: { tag: tagValue } });
        }
    }
};
</script>

<style lang="scss" scoped>
$desktop-thumbnail-width: 12rem;
$desktop-content-height: 120px;
$mobile-thumbnail-width: 6em;
$mobile-content-height: 70px;

.blog-row {
    display: flex;
    align-items: center;
    cursor: pointer;
    border-bottom: 1px solid #f1f1f1;
    padding: 5% 20px;

    &.disabled {
        opacity: 0.5;
    }

    &:hover {
        .content {
            .title {
                margin-left: 10px;
            }
        }
    }

    &.mobile,
    &tablet {
        .wrap-thumbnail {
            display: none;
            margin-right: 1rem;
        }

        .content {
            .title {
                font-size: 1rem;
            }

            .description {
                font-size: 0.85rem;
            }

            .info {
                font-size: 0.8rem;
            }
        }
    }

    .wrap-thumbnail {
        border-radius: 5px;
        margin-right: 1.5rem;
        width: $desktop-thumbnail-width;
        height: $desktop-content-height;
        overflow: hidden;
        display: flex;
        justify-content: center;
        align-items: center;

        img.thumbnail {
            border-radius: 5px;
            overflow: hidden;
            height: $desktop-content-height;
        }
    }

    .content {
        flex: 1;
        display: flex;
        flex-flow: column;

        .title {
            color: #333;
            cursor: pointer;
            font-size: 1.25rem;
            font-weight: bold;
            transition: margin 0.3s cubic-bezier(0.215, 0.61, 0.355, 1);
            margin-bottom: 10px;
            -webkit-line-clamp: 1;
            overflow: hidden;
            max-height: 1.75rem;
        }

        .description {
            display: -webkit-box;
            text-overflow: clip;
            overflow: hidden;
            -webkit-box-orient: vertical;
            white-space: normal;
            -webkit-line-clamp: 2;
            max-height: 3.5rem;
            font-size: 0.95rem;
            color: #797979;
            word-break: break-all;
            margin-bottom: 15px;
        }

        .info {
            display: flex;
            align-items: center;
            justify-content: space-between;
            color: #9199a4;
            font-size: 0.9rem;
        }

        .info .tags {
            flex: 1;
            max-height: 1.3rem;
            overflow: hidden;
            text-overflow: ellipsis;

            .tag {
                margin-right: 10px;
                color: #ec5621;
                font-weight: bold;

                &.on {
                    color: #0000cc;
                }
            }
        }

        .subinfo {
            display: flex;

            > span {
                padding: 2px 5px;
                margin-left: 3px;
            }

            > .hit-count {
                display: none;
                align-items: center;

                img {
                    width: 14px;
                    margin-top: 1px;
                    margin-right: 5px;
                    opacity: 0.4;
                }
            }

            > .comment-count {
                display: flex;
                align-items: center;

                img {
                    width: 14px;
                    margin-top: 1px;
                    margin-right: 5px;
                    opacity: 0.5;
                }
            }
        }
    }
}
</style>

<style>
.content .description search {
    color: #0000ff;
}
</style>
