<template>
    <div class="blog-row" @click="viewBlog(blog.seq)">
        <div class="wrap-thumbnail"
             v-if="blog.thumbnail != null"
             :class="$mq"
        >
            <img class="thumbnail"
                 :src="blog.thumbnail"/>
        </div>

        <div class="content"
             :class="$mq + ' ' + (blog.thumbnail != null ? 'small' : '')"
        >
            <div class="title">{{ blog.title }}</div>
            <div v-html="blog.desc" class="desc">
            </div>
            <div class="info">
                <div class="tags">
                    <span v-for="(tag, index) in blog.tags"
                          v-bind:key=index
                          class="tag"
                    >
                        #{{tag.val}}
                    </span>
                </div>
                <div class="subinfo">
                    <span>댓글 {{blog.commentCnt}}</span>
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
    $desktop-content-height: 120px;
    $mobile-thumbnail-width: 6em;
    $mobile-content-height: 70px;

    .blog-row {
        display: flex;
        align-items: center;
        cursor: pointer;
        border-bottom: 1px solid #F1F1;
        padding: 30px 20px;

        &:hover .content .title {
            margin-left: 10px;
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
            height: $desktop-content-height;
        }

        &.mobile {
            margin-right: 1rem;
            width: $mobile-thumbnail-width;
            height: $mobile-content-height;

            img.thumbnail {
                height: $mobile-content-height;
            }
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
            transition: margin .3s cubic-bezier(0.215, 0.61, 0.355, 1);
            margin-bottom: 10px;
            -webkit-line-clamp: 1;
            overflow: hidden;
            max-height: 1.7rem;
        }

        .desc {
            flex: 1;
            display: -webkit-box;
            text-overflow: clip;
            overflow: hidden;
            -webkit-box-orient: vertical;
            white-space: normal;
            -webkit-line-clamp: 2;
            max-height: 3.2rem;
            color: #797979;
            word-break: break-all;
            margin-bottom: 15px;
        }

        .info {
            display: flex;
            align-items: flex-end;
            justify-content: space-between;
            color: #9199a4;
            font-size: 0.9rem;

            .tags .tag {
                margin-right: 10px;
                color: #ec5621;
            }

            .subinfo span {
                padding: 2px 5px;
                margin-left: 4px;
            }
        }


        &.mobile {

            &.small {
                height: $mobile-content-height;

                .desc {
                    -webkit-line-clamp: 1;
                    max-height: 1.6rem;
                    font-size: 0.9rem;
                    margin-bottom: 6px;
                }
            }

            .title {
                font-size: 1rem;
                margin-bottom: 0px;
            }

            .info {
                font-size: 0.8rem;

                /*.tags .tag:nth-child(-n+3) {*/
                /*    display: none;*/
                /*}*/
            }

        }

    }

</style>
