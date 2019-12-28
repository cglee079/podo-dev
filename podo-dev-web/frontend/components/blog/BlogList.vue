<template>
    <div id="wrapBlogs" :class="$mq">
        <div id="blogs">
            <div :key="blog.id" v-for="blog in contents" class="wrap-blog-row">
                <blog-list-item :blog="blog" :filter="filter" />
            </div>
        </div>
    </div>
</template>

<script>
import BlogListItem from "./BlogListItem";
import bus from "../../utils/bus";

export default {
    name: "BlogList",
    components: {
        BlogListItem
    },
    watch: {
        $route: {
            handler() {
                this.fetchBlogByFilter();
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
        async fetchBlog(page) {
            if (this.isLoading) {
                return;
            }

            if (page === 0) {
                bus.$emit("startSpinner");
                this.contents = [];
            }

            this.isLoading = true;

            try {
                const { result } = await this.$axios.$get(`${this.$baseUrl()}/api/blogs`, {
                    params: {
                        page: page,
                        tag: this.filter.tag,
                        search: this.filter.search
                    }
                });

                result.contents.forEach(item => this.contents.push(item));
                this.pageSize = result.pageSize;
                this.currentPage = result.currentPage;
                this.totalElements = result.totalElements;
                this.totalPages = result.totalPages;
            } catch (e) {
            } finally {
                this.isLoading = false;
                bus.$emit("stopSpinner");
            }
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
                this.fetchBlog(this.currentPage + 1);
            }
        },

        fetchBlogByFilter() {
            this.filter.tag = this.$route.query.tag;
            this.filter.search = this.$route.query.search;

            this.fetchBlog(0);
        }
    },

    mounted() {
        window.addEventListener("scroll", this.handleScroll);
    },

    beforeDestroy() {
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
