<template>
    <div id="wrapBlogs" :class="$mq">
        <div id="blogs">
            <div
                    :key="blog.seq"
                    v-for="blog in contents"
                    class="wrap-blog-row"
            >
                <blog-row :blog="blog"></blog-row>
            </div>

        </div>

        <div id="wrapTags" :class="$mq">
            <tag-values/>
        </div>

    </div>
</template>

<script>
    import BlogListTagValues from "@/components/blog/BlogListTagValues";
    import BlogListRow from "./BlogListRow";

    export default {
        name: 'BlogList',
        components: {
            'blog-row': BlogListRow,
            'tag-values' : BlogListTagValues
        },
        data() {
            return {
                isLoading : false,
                contents: [],
                pageSize: '',
                currentPage: '',
                totalElements: '',
                totalPages: ''
            }
        },
        methods: {
            /**
             * 게시글 페이징
             */
            loadBlog(page) {
                this.isLoading = true

                this.$axios
                    .get('/api/blogs', {
                        params: {
                            'page': page
                        }
                    })
                    .then(res => {
                        res = res.data.data
                        console.log(res.contents)
                        res.contents.forEach(item=>this.contents.push(item))
                        this.pageSize = res.pageSize
                        this.currentPage = res.currentPage
                        this.totalElements = res.totalElements
                        this.totalPages = res.totalPages
                        this.isLoading = false
                    })
                    .catch(err => {
                        console.log(err)
                    })
            },

            /**
             * 스크롤 페이징 이벤트
             */
            handleScroll() {
                const blogRows = document.getElementsByClassName("wrap-blog-row")
                const lastBlogRow = blogRows.item(blogRows.length - 1)

                const scrollBottom = window.pageYOffset + window.innerHeight

                if (blogRows.length
                    && (scrollBottom >= lastBlogRow.offsetTop)
                    && this.currentPage < this.totalPages
                    && !this.isLoading
                ) {
                    this.loadBlog(this.currentPage + 1)
                }
            }


        },
        created() {
            this.loadBlog(0)
            window.addEventListener('scroll', this.handleScroll)
        },
        destroyed() {
            window.removeEventListener('scroll', this.handleScroll)
        }
    }
</script>

<style scoped lang="scss">

    #wrapBlogs {
        display: flex;
        &.mobile {
            flex-flow: column-reverse;
        }
    }

    #blogs {
        flex: 1;
    }

    #wrapTags {
        position: sticky;
        margin-left: 50px;
        width: 15%;

        &.mobile {
            width: 90%;
            margin: 0px auto 30px auto;
            text-align: center;
        }
    }


</style>
