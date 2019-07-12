<template>
    <div id="wrapBlog">
        <div id="head">
            <div id="tags">
                <span>#태그1</span>
                <span>#태그1</span>
            </div>

            <div id="title">
                {{blog.title}}
            </div>

            <div id="info">
                <span>{{blog.createAt}}</span>
                <span>{{blog.hitCnt}}</span>
            </div>
        </div>


        <div id="submenus">
            <span>수정</span>
            <span>삭제</span>
            <span>공유하기</span>
            <span><router-link :to="{name : 'BlogList'}">목록</router-link></span>
            <span>이전글</span>
            <span>다음글</span>
        </div>

        <div id="contents" class="editor-contents">
            <div v-html="blog.contents"></div>
        </div>

        <blog-view-comment></blog-view-comment>
    </div>
</template>

<script>
    import BlogViewComment from '@/components/BlogViewComment'

    export default {
        name: 'BlogVue',
        components: {
            'blog-view-comment': BlogViewComment
        },
        data() {
            return {
                blog: ''
            }
        },
        methods: {
            loadBlog(seq) {
                this.$axios
                    .get('http://localhost:8090/api/blogs/' + seq)
                    .then(res => {
                        this.blog = res.data.data
                        console.log(this.blog)
                    })
                    .catch(err => {
                        console.log(err)
                    })
            }
        },
        created() {
            const seq = this.$route.params.seq
            this.loadBlog(seq)
        }
    }
</script>

<style scoped>
    #wrapBlog {
        width: 800px;
        margin: 0px auto;
    }

    #head {
        margin: 150px 0;
    }

    #head #tags {
        margin-bottom: 15px;
        text-align: center;
        font-weight: bold;
        color: #ec5621;
        cursor: pointer;
    }

    #head #tags span {
        margin: 0px 5px;
    }

    #head #title {
        font-size: 2.5rem;
        margin-bottom: 15px;
        text-align: center;

        word-break: keep-all;
        word-wrap: break-word;
    }

    #head #info {
        display: flex;
        flex-flow: row nowrap;
        justify-content: center;
        align-items: center;
        font-size: 0.75rem;
        padding: 0 2px;
        color: #9199A4;
    }

    #head #info span {
        margin: 0px 5px;
    }

    #submenus {
        border-top: 1px solid #9199a4;
        border-bottom: 1px solid #ecf0f5;
        padding: 8px 0;

        display: flex;
        align-items: center;
        justify-content: flex-end;
    }

    #submenus span {
        cursor: pointer;
        margin-left: 10px;
        font-size: 0.9rem;
    }

    #contents {
        margin-top: 50px;
    }
</style>

<style scoped>
    /*CKEditor Class */

    .editor {
        padding: 1rem 2rem;
        max-width: var(--section-width-narrow);
        margin: 0 auto;
    }

    .editor-contents {
        color: #333;
        line-height: 2.4;
        word-break: break-all;
    }

    .editor-contents iframe {
        margin: 2rem 0;
    }

    .editor-contents h1, .editor-contents h2, .editor-contents h3 {
        margin: 0.3rem 0;
    }

    .editor-contents a, .editor-contents a:VISITED {
        text-decoration: underline;
        color: #00F;
    }

    .editor-contents ul, .editor-contents ol {
        margin: 0.3rem 1.5rem;
    }

    .editor-contents hr {
        border: 0.5px solid #DDD;
    }

    .editor-contents > p > img {
        height: auto;
        margin: 1.5rem 0;
        border-color: #DDD;
    }

    .editor-contents code {
        overflow-x: auto;
        margin: 10px 1px;
    }


    .editor-contents .emphasize {
        display: block;
        border-left: 5px solid #DDD;
        padding-left: 0.5rem;
        margin: 1rem 0.5rem;
        font-weight: bold;
        font-style: italic;
    }


    .editor-contents .quotation {
        display: block;
        font-style: italic;
        line-height: 1.7;
        opacity: 0.9;
        margin: 0 1rem
    }

    .editor-contents .mark {
        padding: 1px 5px;
        font-size: 0.95rem;
        background: #F1F1F1;
        border-radius: 5px;
    }
</style>
