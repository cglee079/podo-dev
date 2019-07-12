<template>
    <div id="wrapBlogs">
        <div id="blogs">
            <blog-row v-for="blog in contents" :key="blog.seq" :blog="blog"></blog-row>
        </div>
        <div id="blogTags">
            <div>#Spring</div>
            <div>#VueJS</div>
            <div>#JPA</div>
            <div>#이론</div>
            <div>#Spring</div>
            <div>#VueJS</div>
            <div>#JPA</div>
            <div>#이론</div>
        </div>
    </div>
</template>

<script>
    import BlogRow from '@/components/BlogRow'

    export default {
        name: 'BlogList',
        components: {
            'blog-row' : BlogRow
        },
        data(){
            return {
                contents : [],
                pageSize : '',
                currentPage : '',
                totalElements : '',
                totalPages : ''
            }
        },
        methods: {
            /**
             * 게시글 페이징
             */
            loadBlog(page) {
                this.$axios
                    .get('http://localhost:8090/api/blogs', {
                        params: {
                            'page': page
                        }
                    })
                    .then(res => {
                        res = res.data.data
                        this.contents = res.contents
                        this.pageSize = res.pageSize
                        this.currentPage = res.currentPage
                        this.totalElements = res.totalElements
                        this.totalPages = res.totalPages
                    })
                    .catch(err => {
                        console.log(err)
                    })
            }


        },
        created() {
            this.loadBlog(0)
        }
    }
</script>

<style scoped>
    #wrapBlogs {
        display: flex;
    }

    #blogTags {
        position: sticky;
        margin-left: 50px;
        width: 15%;
    }

    #blogTags div{
        display: inline-block;
        margin: 5px 10px;
        cursor: pointer;
        opacity: 0.8;
    }

    #blogTags div:hover{
        opacity: 1;
    }

    #blogs{
        flex:1;
    }
</style>
