<template>
    <div id="wrapBlogs" :class="$mq">
        <progress-bar ref="progressBar" />

        <div id="blogs">
            <div :key="blog.id" v-for="blog in contents" class="wrap-blog-row">
                <blog-row :blog="blog" :filter="filter" />
            </div>
        </div>
    </div>
</template>

<script>
import BlogListRow from "./BlogListRow";
import ProgressBar from "../global/ProgressBar";

export default {
    name: "BlogList",
    components: {
        ProgressBar,
        "blog-row": BlogListRow
    },
    watch: {
        $route: {
            handler() {
                this.loadBlogByFilter();
            },
            immediate: true
        }
    },
    data() {
        return {
            filter: {
                tag: "",
                search: ""
            },
            isLoading: false,
            contents: [],
            pageSize: "",
            currentPage: "",
            totalElements: "",
            totalPages: ""
        };
    },
    methods: {
        onProgress() {
            if(this.$refs.progressBar) {
                this.$refs.progressBar.on();
            }
        },

        offProgress() {
            this.$refs.progressBar.off();
        },

        /**
         * 게시글 페이징
         */
        loadBlog(page) {
            if (this.isLoading) {
                return;
            }

            if (page === 0) {
                this.onProgress();
                this.contents = [];
            }

            this.isLoading = true;

            this.$axios
                .$get("/api/blogs", {
                    params: {
                        page: page,
                        tag: this.filter.tag,
                        search: this.filter.search
                    }
                })
                .then(res => {
                    res = res.result;
                    res.contents.forEach(item => this.contents.push(item));
                    this.pageSize = res.pageSize;
                    this.currentPage = res.currentPage;
                    this.totalElements = res.totalElements;
                    this.totalPages = res.totalPages;
                    this.isLoading = false;

                    if (page === 0) {
                        this.offProgress();
                    }
                })
                .catch(() => {
                    if (page === 0) {
                        this.offProgress();
                    }
                });
        },

        /**
         * 스크롤 페이징 이벤트
         */
        handleScroll() {
            const blogRows = document.getElementsByClassName("wrap-blog-row");

            const lastBlogRowIndex = blogRows.length - this.pageSize + 1;

            if (lastBlogRowIndex < 0) {
                return;
            }

            const lastBlogRow = blogRows.item(lastBlogRowIndex);
            const scrollBottom = window.pageYOffset + window.innerHeight;

            if (
                blogRows.length &&
                scrollBottom >= lastBlogRow.offsetTop &&
                this.currentPage < this.totalPages &&
                !this.isLoading
            ) {
                this.loadBlog(this.currentPage + 1);
            }
        },

        loadBlogByFilter() {
            this.filter.tag = this.$route.query.tag;
            this.filter.search = this.$route.query.search;

            this.loadBlog(0);
        }
    },
    created() {
        window.addEventListener("scroll", this.handleScroll);
    },
    destroyed() {
        window.removeEventListener("scroll", this.handleScroll);
    }
};
</script>

<style lang="scss" scoped>
#wrapBlogs {
    max-width: var(--wild-max-width);
    margin: 0px auto;

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
