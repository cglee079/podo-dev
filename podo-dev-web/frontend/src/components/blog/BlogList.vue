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

    </div>
</template>

<script>
    import BlogListRow from "./BlogListRow";

    export default {
        name: 'BlogList',
        components: {
            'blog-row': BlogListRow,
        },
        watch: {
            $route() {
                this.loadBlogByFilter()
            }
        },
        data() {
            return {
                filter: {
                    tag: '',
                    search : '',
                },
                isLoading: false,
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
                if(this.isLoading){
                    return
                }

                if(page === 0){
                    this.contents = []
                }

                this.isLoading = true

                this.$axios
                    .get('/api/blogs', {
                        params: {
                            'page': page,
                            'tag': this.filter.tag,
                            'search' : this.filter.search
                        }
                    })
                    .then(res => {
                        res = res.data.data
                        console.log(res)
                        res.contents.forEach(item => this.contents.push(item))
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
            },

            loadBlogByFilter() {
                this.filter.tag = this.$route.query.tag
                this.filter.search = this.$route.query.search

                this.loadBlog(0)
            }

        },
        mounted() {
            window.addEventListener('scroll', this.handleScroll)
            this.loadBlogByFilter()
        },
        destroyed() {
            window.removeEventListener('scroll', this.handleScroll)
        },
    }
</script>

<style lang="scss" scoped >

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
