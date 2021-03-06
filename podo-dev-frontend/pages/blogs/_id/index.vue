<template>
    <section id="wrapBlog" :class="$mq">
        <blog-view-export ref="export" :blog="blog" />

        <div>
            <div id="head">
                <div id="tags">
                    <span v-for="tag in blog.tags" :key="tag.id">
                        <nuxt-link
                            :to="{
                                name: 'blogs',
                                query: { tag: tag.tagValue }
                            }"
                        >
                            #{{ tag.tagValue }}
                        </nuxt-link>
                    </span>
                </div>

                <h1 id="title">
                    {{ blog.title }}
                </h1>

                <div id="info">
                    <span>{{ blog.publishAt }}</span>
                    <span>조회수 {{ blog.hitCount }}</span>
                </div>
            </div>

            <div id="submenus">
                <div v-if="isAdmin && isLogin" @click="clickModifyBlog(blog.id)">
                    수정
                </div>
                <div v-else></div>

                <div v-if="isAdmin && isLogin" @click="clickDeleteBlog(blog.id)">
                    삭제
                </div>
                <div v-else></div>

                <div @click="$refs.export.onExport()">공유하기</div>
                <div>
                    <nuxt-link
                        :to="{
                            name: 'blogs',
                            query: { search: filter.search, tag: filter.tag }
                        }"
                    >
                        목록
                    </nuxt-link>
                </div>
                <div @click="clickBefore()">이전글</div>
                <div @click="clickNext()">다음글</div>
            </div>

            <div id="attachFiles">
                <div
                    v-for="attachFile in blog.attachFiles"
                    :key="attachFile.id"
                    class="attach-file"
                >
                    <span @click="clickFile(attachFile.id)">
                        <img
                            src="../../../assets/btns/btn-file.svg"
                            class="file-icon"
                            alt="fileIcon"
                        />
                        <span class="file-name">
                            {{ attachFile.originFilename }}
                        </span>
                        <span class="file-size">[{{ formatFilesize(attachFile.filesize) }}]</span>
                    </span>
                </div>
            </div>

            <div id="contents">
                <toast-custom-viewer ref="viewer" :value="blog.contents" />
            </div>

            <blog-view-relates
                :blog-id="blog.id"
                :blog-title="blog.title"
                :relates="blog.relates"
            />

            <div ref="comments">
                <blog-view-comment v-if="blog.id" :blog-id="blog.id" />
            </div>
        </div>
    </section>
</template>

<script>
import { mapGetters } from "vuex";
import filesize from "filesize";
import BlogViewComment from "../../../components/blog/BlogViewComment";
import BlogViewExport from "../../../components/blog/BlogViewExport";
import BlogViewRelates from "../../../components/blog/BlogViewRelates";
import ToastCustomViewer from "../../../components/global/ToastCustomViewer";
import bus from "../../../utils/bus";
export default {
    name: "BlogView",
    components: {
        BlogViewComment,
        BlogViewExport,
        BlogViewRelates,
        ToastCustomViewer
    },
    head() {
        return {
            title: this.meta.title,
            meta: [
                { name: "keywords", content: this.meta.keywords },
                {
                    hid: "description",
                    name: "description",
                    content: this.meta.description
                },
                {
                    hid: "og:title",
                    property: "og:title",
                    content: this.meta.title
                },
                {
                    hid: "og:description",
                    property: "og:description",
                    content: this.meta.description
                },
                {
                    hid: "og:image",
                    property: "og:image",
                    content: this.meta.thumbnail
                },
                { hid: "og:url", property: "og:url", content: this.meta.url },
                {
                    hid: "article:mobile_url",
                    property: "article:mobile_url",
                    content: this.meta.url
                },
                {
                    hid: "article:pc_url",
                    property: "article:pc_url",
                    content: this.meta.url
                },
                {
                    hid: "article:pc_view_url",
                    property: "article:pc_view_url",
                    content: this.meta.url
                },
                {
                    hid: "article:talk_channel_view_url",
                    property: "article:talk_channel_view_url",
                    content: this.meta.url
                },
                { hid: "plink", property: "plink", content: this.meta.url },
                {
                    hid: "dg:plink",
                    property: "dg:plink",
                    content: this.meta.url
                }
            ],
            link: [{ rel: "canonical", href: this.meta.url }]
        };
    },
    async asyncData({ $axios, app, error, params }) {
        const id = params.id;
        try {
            const blog = await $axios.$get(`${app.$baseUrl()}/api/blogs/${id}`);
            const keywords = blog.tags.map(tag => tag.val);
            const meta = {
                url: `${process.env.STATIC_URL}/blogs/${blog.id}`,
                title: `${process.env.NAME} : ${blog.title}`,
                keywords: keywords.join(", "),
                description:
                    blog.description.length > 300
                        ? blog.description.substring(0, 300)
                        : blog.description,
                thumbnail: blog.thumbnail ? blog.thumbnail : "/og-image.png",
                createAt: blog.createAt,
                publishAt: blog.publishAt,
                updateAt: blog.updateAt
            };
            return { blog, meta };
        } catch (e) {
            error({
                statusCode: 404
            });
        }
    },
    data() {
        return {
            meta: {},
            blog: {},
            filter: {}
        };
    },
    computed: {
        ...mapGetters({
            isAdmin: "user/isAdmin",
            isLogin: "user/isLogin"
        })
    },
    methods: {
        clickModifyBlog(blogId) {
            this.$router.push({
                name: "blogs-id-post",
                params: {
                    id: blogId
                }
            });
        },
        clickDeleteBlog(blogId) {
            this.toastConfirm("정말 삭제하시겠습니까?", async () => {
                try {
                    bus.$emit("spinner:start", "delete-blog");
                    await this.$axios.delete(`/api/blogs/${blogId}`);
                    this.$router.push({ name: "blogs" });
                } catch (e) {
                } finally {
                    bus.$emit("spinner:stop", "delete-blog");
                }
            });
        },
        clickBefore() {
            const beforeBlogId = this.blog.beforeBlogId;
            if (!beforeBlogId) {
                this.$toast.show("이전글이 없습니다");
                return;
            }
            this.$router.push({
                name: "blogs-id",
                params: {
                    id: beforeBlogId
                }
            });
        },
        clickNext() {
            const nextBlogId = this.blog.nextBlogId;
            if (!nextBlogId) {
                this.$toast.show("다음글이 없습니다");
                return;
            }
            this.$router.push({
                name: "blogs-id",
                params: {
                    id: nextBlogId
                }
            });
        },
        clickFile(fileId) {
            window.location.href = `${process.env.EXTERNAL_SERVER_URL}/api/blogs/${this.blog.id}/files/${fileId}`;
        },
        formatFilesize(value) {
            return filesize(value);
        },
        increaseHitCount(blogId) {
            this.$axios.$post(`/api/blogs/${blogId}/hitCount`);
        }
    },
    mounted() {
        this.filter.search = this.$route.query.search;
        this.filter.tag = this.$route.query.tag;
        const blogId = this.blog.id;
        const HIT_BLOGS_KEY = "HIT_BLOGS";
        let hitBlogs = this.$storage.getLocalStorage(HIT_BLOGS_KEY);
        if (!hitBlogs) {
            hitBlogs = [];
        }
        if (!hitBlogs.includes(blogId)) {
            hitBlogs.push(blogId);
            this.$storage.setLocalStorage(HIT_BLOGS_KEY, hitBlogs);
            this.increaseHitCount(blogId);
        }
        if (this.$route.hash === "#comment") {
            setTimeout(
                () => window.scrollTo(0, this.$refs.comments.getBoundingClientRect().top),
                1
            );
        }
    }
};
</script>

<style lang="scss" scoped>
#wrapBlog {
    max-width: var(--max-width);
    margin: 0px auto;
    &.mobile,
    &.tablet {
        #head,
        #submenus,
        #contents {
            padding-left: 5%;
            padding-right: 5%;
        }
        #head {
            margin: 150px 10px;
            #title {
                font-size: 1.6rem;
            }
        }
        #attachFiles {
            .attach-file {
                padding-left: 5%;
                padding-right: 5%;
            }
        }
    }
    #head {
        margin: 150px 0;
        #tags {
            margin-bottom: 15px;
            text-align: center;
            font-weight: bold;
            color: #ec5621;
            span {
                margin: 0px 5px;
            }
        }
        #title {
            font-size: 2.1rem;
            margin-bottom: 15px;
            text-align: center;
            font-weight: bold;
            word-break: keep-all;
            word-wrap: break-word;
        }
        #info {
            display: flex;
            flex-flow: row nowrap;
            justify-content: center;
            align-items: center;
            font-size: 0.75rem;
            padding: 0 2px;
            color: #9199a4;
            span {
                margin: 0px 5px;
            }
        }
    }
    #submenus {
        border-top: 1px solid #9199a4;
        border-bottom: 1px solid #ecf0f5;
        padding: 8px 0;
        display: flex;
        align-items: center;
        justify-content: flex-end;
        div {
            cursor: pointer;
            margin-left: 10px;
            font-size: 0.9rem;
        }
    }
    #attachFiles {
        margin-top: 20px;
        text-align: right;
        .attach-file {
            margin: 5px 0px;
            opacity: 0.95;
            white-space: nowrap;
            > span {
                display: flex;
                justify-content: flex-end;
                align-items: center;
                cursor: pointer;
                .file-icon {
                    width: 15px;
                    margin-top: 5px;
                }
                .file-name {
                    margin-left: 5px;
                    overflow: hidden;
                    max-width: 50%;
                    text-overflow: ellipsis;
                }
                .file-size {
                    margin-left: 10px;
                }
            }
        }
    }
    #contents {
        margin-top: 70px;
        margin-bottom: 150px;
        font-size: 1rem !important;
    }
}
</style>
