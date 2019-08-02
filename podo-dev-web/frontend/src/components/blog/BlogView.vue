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
                <span>조회수 {{blog.hitCnt}}</span>
            </div>
        </div>


        <div id="submenus">
            <span @click="clickModifyBlog(blog.seq)">수정</span>
            <span>삭제</span>
            <span>공유하기</span>
            <span><router-link :to="{name : 'BlogList'}">목록</router-link></span>
            <span>이전글</span>
            <span>다음글</span>
        </div>

        <div id="contents" class="editor-contents">
            <toast-custom-viewer :value="blog.contents"/>
        </div>

        <blog-view-comment value="blog.contents"></blog-view-comment>


    </div>
</template>

<script>
    import BlogViewComment from '@/components/blog/BlogViewComment'

    export default {
        name: 'BlogVue',
        components: {
            'blog-view-comment': BlogViewComment,
        },
        data() {
            return {
                blog: ''
            }
        },
        methods: {
            clickModifyBlog(seq){
                this.$router.push({
                    name : 'BlogModify',
                    params: {
                        seq :seq
                    }
                });
            },
            loadBlog(seq) {
                this.$axios
                    .get('/api/blogs/' + seq)
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
        font-size: 1rem !important;
    }
</style>

