<template>
    <div id="wrapBlogs" :class="$mq">
        <div id="blogs">
            <template v-for="(blog, index) in contents">
                <div :key="blog.id" class="wrap-blog-row">
                    <blog-list-item :blog="blog" :filter="filter" />
                </div>
                <div v-if="(index + 3) % 5 === 0" :key="blog.id + 'ad'" class="wrap-blog-row">
                    <blog-list-adsense />
                </div>
            </template>
        </div>
    </div>
</template>

<script>
import BlogListItem from "./BlogListItem";
import bus from "../../utils/bus";
import BlogListAdsense from "./BlogListAdsense";

export default {
    name: "BlogList",
    components: {
        BlogListAdsense,
        BlogListItem
    },
    watch: {
        "$route.query"() {
            console.log("ddd");
            this.fetchBlogByFilter();
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
                bus.$emit("spinner:start", "load-blog-list");
                this.contents = [];
            }

            this.isLoading = true;

            try {
                const response = await this.$axios.$get(`${this.$baseUrl()}/api/blogs`, {
                    params: {
                        page: page,
                        tag: this.filter.tag,
                        search: this.filter.search
                    }
                });

                response.contents.forEach(item => this.contents.push(item));
                this.pageSize = response.pageSize;
                this.currentPage = response.currentPage;
                this.totalElements = response.totalElements;
                this.totalPages = response.totalPages;
            } catch (e) {
            } finally {
                this.isLoading = false;
                bus.$emit("spinner:stop", "load-blog-list");
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
        this.fetchBlogByFilter();
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
