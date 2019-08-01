<template >
    <div class="blog-row" @click="viewBlog(blog.seq)">
        <img class="thumbnail"/>
        <div class="content">
            <div class="title">{{ blog.title }}</div>
            <div class="desc" >
                <div v-html="blog.desc" ></div>
            </div>
            <div class="info">
                <div class="tags">
                    <span class="tag">#Network</span>
                    <span class="tag">#Test</span>
                    <span class="tag">#DFD</span>
                </div>
                <div class="subinfo">
                    <span>{{ blog.createAt }}</span>
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

<style scoped>
    .blog-row {
        display: flex;
        cursor: pointer;
        padding-bottom: 30px;
        margin-bottom: 30px;
        border-bottom: 1px solid #F1F1;
    }

    .thumbnail {
        margin: 0px 20px;
        width: 150px;
    }

    .content {
        flex: 1;
    }

    .title {
        color: #333;
        cursor: pointer;
        font-size: 1.25rem;
        font-weight: bold;
        transition: margin .3s cubic-bezier(0.215, 0.61, 0.355, 1);
        margin-bottom: 10px;
    }

    .blog-row:hover .title {
        margin-left: 10px;
    }

    .desc {
        display: box;
        overflow: hidden;
        -webkit-box-orient: vertical;
        white-space: normal;
        -webkit-line-clamp: 2;
        max-height: 3.2rem;
        color: #606060;
        word-break: break-all;
        margin-bottom: 15px;
        opacity: 0.9;
    }

    .info {
        display: flex;
        align-items: flex-end;
        justify-content: space-between;
        color: #9199a4;
        font-size: 0.9rem;
    }

    .info .tags .tag {
        padding: 2px 5px;
        margin-right: 4px;
    }

    .info .subinfo span {
        padding: 2px 5px;
        margin-left: 4px;
    }

</style>
