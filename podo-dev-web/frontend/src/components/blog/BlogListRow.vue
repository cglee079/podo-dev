<template>
    <div class="blog-row" @click="viewBlog(blog.seq)">
        <div class="wrap-thumbnail"
             v-if="blog.thumbnail != null"
             :class="$mq"
        >
            <img class="thumbnail"
                 :src="blog.thumbnail"
                 :class="$mq"/>
        </div>

        <div class="content" :class="$mq">
            <div class="title" :class="$mq">{{ blog.title }}</div>
            <div class="desc" :class="$mq">
                <div v-html="blog.desc"></div>
            </div>
            <div class="info" :class="$mq">
                <div class="tags">
                    <span v-for="(tag, index) in blog.tags"
                          v-bind:key=index
                          class="tag"
                    >
                        #{{tag.val}}
                    </span>
                </div>
                <div class="subinfo">
                    <span>댓글 0</span>
                </div>
            </div>

        </div>
    </div>
</template>

<script>
    export default {
        name: 'BlogRow',
        props: {
            blog: Object
        },
        methods: {
            viewBlog(seq) {
                this.$router.push({
                    name: 'BlogView',
                    params: {
                        'seq': seq
                    }
                })
            }
        }
    }
</script>

<style scoped lang="scss">
    $desktop-thumbnail-width: 12rem;
    $desktop-row-height: 8rem;
    $mobile-thumbnail-width: 6em;
    $mobile-row-height: 4.5rem;

    .blog-row {
        display: flex;
        align-items: center;
        cursor: pointer;
        border-bottom: 1px solid #F1F1;
        padding : 30px 20px 30px 20px;
    }

    .wrap-thumbnail {
        border-radius: 5px;
        margin-right: 1.5rem;
        width: $desktop-thumbnail-width;
        height: $desktop-row-height;
        overflow: hidden;
        display: flex;
        justify-content: center;
        align-items: center;

        &.mobile {
            margin-right: 1rem;
            width: $mobile-thumbnail-width;
            height: $mobile-row-height;
        }
    }

    .wrap-thumbnail img.thumbnail {
        height: $desktop-row-height;

        &.mobile {
            height: $mobile-row-height;
        }
    }

    .content {
        flex: 1;
        display: flex;
        flex-flow: column;
        height: $desktop-row-height;

        &.mobile {
            height: $mobile-row-height;
        }
    }

    .title {
        color: #333;
        cursor: pointer;
        font-size: 1.25rem;
        font-weight: bold;
        transition: margin .3s cubic-bezier(0.215, 0.61, 0.355, 1);
        margin-bottom: 10px;
        -webkit-line-clamp: 1;
        overflow: hidden;
        max-height: 1.7rem;

        &.mobile {
            font-size: 1rem;
            margin-bottom: 3px;
        }
    }

    .blog-row:hover .title {
        margin-left: 10px;
    }

    .desc {
        flex: 1;
        overflow: hidden;
        -webkit-box-orient: vertical;
        white-space: normal;
        -webkit-line-clamp: 2;
        max-height: 3.2rem;
        color: #606060;
        word-break: break-all;
        margin-bottom: 15px;
        opacity: 0.9;

        &.mobile {
            -webkit-line-clamp: 1;
            max-height: 1.6rem;
            font-size: 0.9rem;
            margin-bottom: 5px;
        }
    }

    .info {
        display: flex;
        align-items: flex-end;
        justify-content: space-between;
        color: #9199a4;
        font-size: 0.9rem;

        &.mobile {
            font-size: 0.8rem;
        }
    }

    .info .tags .tag {
        margin-right: 10px;
    }

    .info .subinfo span {
        padding: 2px 5px;
        margin-left: 4px;
    }

</style>
