<template>
    <div id="wrapBlogs">
        <div id="blogs">
            <blog-row v-for="i in 20" :key="i">
            </blog-row>
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
                blog : ''
            }
        },
        methods: {
            /**
             * 게시글 페이징
             */
            loadBlog(page) {
                this.$axios
                    .get('http://localhost:8443/api/blogs', {
                        params: {
                            'page': page
                        }
                    })
                    .then(res => {
                        console.log(res)
                    })
                    .catch(err => {
                        console.log(err)
                    })
            }


        },
        created() {
            //this.loadBlog(0)
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
