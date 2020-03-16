<template>
    <div id="wrapBlogPost" :class="$mq">
        <div class="wrap-item">
            <span>공개여부</span>
            <span>
                <select v-model="input.status">
                    <option :value="CONST.BLOG_STATUS.PUBLISH">발행(발행일갱신)</option>
                    <option :value="CONST.BLOG_STATUS.VISIBLE">공개</option>
                    <option :value="CONST.BLOG_STATUS.INVISIBLE">비공개</option>
                </select>
            </span>
        </div>

        <div class="wrap-item">
            <span>제목</span>
            <span>
                <input type="text" v-model="input.title" />
            </span>
        </div>

        <div id="wrapEditor" ref="wrapEditor">
            <client-only>
                <TuiEditor
                    ref="tuiEditor"
                    mode="markdown"
                    :options="config.editor.options"
                    preview-style="vertical"
                    height="100%"
                    v-model="input.contents"
                ></TuiEditor>
            </client-only>
        </div>

        <div id="editorSubMenu" ref="editorSubMenu">
            <div>
                <blog-post-long-button
                    v-if="!config.editor.expand"
                    value="에디터 크게보기"
                    @click="expandEditor()"
                />
                <blog-post-long-button
                    v-if="config.editor.expand"
                    value="에디터 작게보기"
                    @click="contractEditor()"
                />
            </div>

            <div id="wrapImageUpload">
                <post-image
                    :attachImages="this.input.attachImages"
                    @add="addAttachImage"
                    @remove="removeAttachImage"
                />
            </div>
        </div>

        <div id="wrapFileUpload">
            <post-file
                :attachFiles="input.attachFiles"
                @add="addAttachFile"
                @remove="removeAttachFile"
            />
        </div>

        <div id="histories">
            <blog-post-long-button
                value="이전 저장 컨텐츠 가져오기"
                @click="config.showHistory = !config.showHistory"
            />
            <blog-post-history
                v-if="config.showHistory"
                :histories="blog.histories"
                @fetch="fetchHistory"
            />
        </div>

        <div id="wrapTag" class="wrap-item">
            <span>태그</span>

            <span>
                <input type="text" v-model="input.tagText" @keyup="keyupTagText" />
            </span>
        </div>

        <div id="tags">
            <span
                v-for="(tag, index) in input.tags"
                :key="tag.tagValue"
                @click="clickTag(index)"
                class="tag"
            >
                #{{ tag.tagValue }}
            </span>
        </div>

        <div id="submit">
            <div @click="clickSubmit">
                작성완료
            </div>
        </div>
    </div>
</template>

<script>
import BlogPostAttachImage from "./BlogPostAttachImage";
import BlogPostAttachFile from "./BlogPostAttachFile";
import bus from "../../utils/bus";
import { mapGetters } from "vuex";
import BlogPostHistory from "./BlogPostHistory";
import BlogPostLongButton from "./BlogPostLongButton";

export default {
    name: "app",
    components: {
        BlogPostLongButton,
        BlogPostHistory,
        "post-image": BlogPostAttachImage,
        "post-file": BlogPostAttachFile
    },
    props: {
        id: Number
    },
    // watch: {
    //     id(value) {
    //         this.isNew = false;
    //         this.fetchBlog(value);
    //     }
    // },
    computed: {
        ...mapGetters({
            isAdmin: "user/isAdmin"
        })
    },
    data() {
        return {
            CONST: {
                BLOG_STATUS: {
                    PUBLISH: "PUBLISH",
                    VISIBLE: "VISIBLE",
                    INVISIBLE: "INVISIBLE"
                }
            },
            isNew: true,
            config: {
                maxAttachImageWidth: 720,
                showHistory: false,
                editor: {
                    expand: false,
                    options: {
                        exts: ["uml", "chart", "table"]
                    }
                }
            },
            autoSave: {
                key: "autoSavePost",
                interval: undefined
            },
            temp: "",
            blog: {
                id: "",
                histories: []
            },
            input: {
                title: "",
                contents: "",
                tagText: "",
                status: "INVISIBLE",
                tags: [],
                attachImages: [],
                attachFiles: []
            }
        };
    },
    methods: {
        expandEditor() {
            this.config.editor.expand = true;
            this.$scrollBlock.block("expand-editor");
            this.$classie.add(this.$refs.wrapEditor, "expand");
            this.$classie.add(this.$refs.editorSubMenu, "expand");
        },

        contractEditor() {
            this.config.editor.expand = false;
            this.$scrollBlock.unblock("expand-editor");
            this.$classie.remove(this.$refs.wrapEditor, "expand");
            this.$classie.remove(this.$refs.editorSubMenu, "expand");
        },

        //태그 Input 입력 시,
        keyupTagText(event) {
            let insertValue = this.input.tagText;
            if (insertValue.endsWith(" ") || event.keyCode === 13) {
                event.preventDefault();
                insertValue = insertValue.trim();

                if (insertValue.length) {
                    let included = false;

                    this.input.tags.forEach(tag => {
                        if (tag.tagValue === insertValue) {
                            included = true;
                        }
                    });

                    if (included) {
                        this.$toast.show(`${insertValue}는 중복됩니다!`);
                        this.input.tagText = "";
                        return;
                    }

                    const obj = {};
                    obj.id = undefined;
                    obj.tagValue = insertValue;
                    this.input.tags.push(obj);
                }

                this.input.tagText = "";
            }
        },

        // 태그 클릭 시, 태그 삭제
        clickTag(index) {
            this.input.tags.splice(index, 1);
        },

        async fetchBlog(blogId) {
            const blog = await this.$axios.$get(`/api/blogs/${blogId}`);
            this.blog.id = blog.id;
            this.blog.histories = blog.histories;
            this.input.title = blog.title;
            this.input.contents = blog.contents;
            this.input.tags = blog.tags;
            this.input.attachImages = blog.attachImages;
            this.input.attachFiles = blog.attachFiles;
            this.input.status = blog.enabled
                ? this.CONST.BLOG_STATUS.VISIBLE
                : this.CONST.BLOG_STATUS.INVISIBLE;
        },

        fetchHistory(historyId) {
            this.toastConfirm("정말 이전 저장 데이터를 가져오시겠습니까?", async () => {
                try {
                    const history = await this.$axios.$get(
                        `/api/blogs/${this.blog.id}/histories/${historyId}`
                    );
                    this.input.title = history.title;
                    this.input.contents = history.contents;

                    this.$toast.show("이전 저장 데이터를 가져왔습니다");
                } catch (e) {}
            });
        },

        clickSubmit() {
            if (this.input.status !== this.CONST.BLOG_STATUS.PUBLISH) {
                this.submit();
                return;
            }

            this.toastConfirm(
                "정말 발행하시겠습니까?",
                () => {
                    //OK
                    this.submit();
                },
                () => {
                    // NO
                    this.input.status = this.CONST.BLOG_STATUS.INVISIBLE;
                    this.submit();
                }
            );
        },

        submit() {
            if (this.isNew) {
                this.insertBlog();
            } else {
                this.updateBlog();
            }
        },

        async insertBlog() {
            bus.$emit("spinner:start", "post-blog");

            try {
                await this.$axios.$post("/api/blogs", {
                    title: this.input.title,
                    contents: this.input.contents,
                    status: this.input.status,
                    tags: this.input.tags,
                    attachImages: this.input.attachImages,
                    attachFiles: this.input.attachFiles
                });

                this.$router.push({ name: "index" });
            } catch (e) {
            } finally {
                this.removeAutoSaved(this.id);
                bus.$emit("spinner:stop", "post-blog");
            }
        },

        async updateBlog() {
            bus.$emit("spinner:start", "update-blog");

            try {
                await this.$axios.$patch(`/api/blogs/${this.id}`, {
                    title: this.input.title,
                    contents: this.input.contents,
                    status: this.input.status,
                    tags: this.input.tags,
                    attachImages: this.input.attachImages,
                    attachFiles: this.input.attachFiles
                });

                this.$router.push({
                    name: "blogs-id",
                    params: {
                        id: this.id
                    }
                });
            } catch (e) {
            } finally {
                this.removeAutoSaved(this.id);
                bus.$emit("spinner:stop", "update-blog");
            }
        },

        addAttachImage(attachImage) {
            const src = `${attachImage.uploadedUrl}${attachImage.saves.origin.filePath}/${attachImage.saves.origin.filename}`;

            const elementInContents = document.createElement("img");

            let imageWidth = attachImage.saves.origin.width;

            if (imageWidth > this.config.maxAttachImageWidth) {
                imageWidth = this.config.maxAttachImageWidth;
            }

            elementInContents.src = src;
            elementInContents.setAttribute("alt", attachImage.originFilename);
            elementInContents.setAttribute("style", `width:${imageWidth}px;`);

            this.input.contents += `\n${elementInContents.outerHTML}\n`;

            this.input.attachImages.push(attachImage);
        },

        removeAttachImage(index) {
            const image = this.input.attachImages[index];

            switch (image.attachStatus) {
                case "BE":
                    image.attachStatus = "REMOVE";
                    break;
                case "NEW":
                    image.attachStatus = "UNNEW";
                    break;
                default:
                    break;
            }

            this.removeImageElementInEditor(image);
        },

        removeImageElementInEditor(attachImage) {
            let existedContents = this.input.contents;
            const index = existedContents.indexOf(attachImage.saves.origin.filename);

            if (index < 0) {
                return;
            }

            const startImgElementIndex = existedContents.substring(0, index).lastIndexOf("<img");
            const endImgElementIndex =
                existedContents.substring(index, existedContents.length).indexOf(">") + index;
            const imageElement = existedContents.substring(
                startImgElementIndex,
                endImgElementIndex + 1
            );

            this.input.contents = existedContents.replace(imageElement, "");
        },

        addAttachFile(attachFile) {
            this.input.attachFiles.push(attachFile);
        },

        removeAttachFile(index) {
            const attachFile = this.input.attachFiles[index];
            switch (attachFile.attachStatus) {
                case "BE":
                    attachFile.attachStatus = "REMOVE";
                    break;
                case "NEW":
                    attachFile.attachStatus = "UNNEW";
                    break;
                default:
                    break;
            }
        },

        removeAutoSaved(blogId) {
            const autoSaveKey = this.autoSave.key + blogId;

            this.$storage.removeLocalStorage(autoSaveKey);
        }
    },

    mounted() {
        const blogId = this.id ? this.id : "";
        const autoSaveKey = this.autoSave.key + blogId;

        const savedInputValue = this.$storage.getLocalStorage(autoSaveKey);

        if (savedInputValue) {
            this.toastConfirm(
                "자동저장된 데이터가있습니다, 로딩하시겠습니까?",
                () => {
                    this.input = savedInputValue;
                    this.removeAutoSaved(blogId);
                },
                () => {
                    this.removeAutoSaved(blogId);
                }
            );
        }
    },

    created() {
        const blogId = this.id ? this.id : null;

        if (blogId) {
            this.isNew = false;
            this.fetchBlog(blogId);
        }

        //Setting Interval
        const autoSaveKey = this.autoSave.key + blogId;
        this.autoSave.interval = setInterval(() => {
            const currentInput = JSON.stringify(this.input);
            const saveInput = JSON.stringify(this.$storage.getLocalStorage(autoSaveKey));

            if (saveInput !== currentInput) {
                this.$toast.show("자동저장 되었습니다");
                this.$storage.setLocalStorage(autoSaveKey, currentInput);
            }
        }, 1000 * 60 * 2);
    },

    beforeDestroy() {
        clearInterval(this.autoSave.interval);
    }
};
</script>

<style lang="scss" scoped>
#wrapBlogPost {
    &.mobile,
    &.tablet {
        padding-left: 5%;
        padding-right: 5%;
    }

    #wrapEditor {
        margin: 40px 0;
        width: 100%;
        height: 700px;

        &.expand {
            position: fixed;
            left: 0;
            right: 0;
            top: 0;
            bottom: 15%;
            margin: unset;
            height: unset;
            background: #ffffff;
            z-index: 10;
        }
    }

    #editorSubMenu {
        &.expand {
            padding: 10px 20px;
            position: fixed;
            left: 0;
            right: 0;
            top: 85%;
            bottom: 0;
            background: #ffffff;
            overflow-y: scroll;
            z-index: 10;
        }
    }

    #histories {
        margin-top: 10px;
    }

    #wrapImageUpload {
        margin-top: 10px;
    }

    #wrapFileUpload {
        margin-top: 10px;
    }

    #wrapTag {
        margin-top: 50px;
    }

    #tags {
        .tag {
            display: inline-block;
            cursor: pointer;
            padding: 3px 10px;
            margin: 2px 3px;
            background: #e7e7e7;
            border-radius: 3px;
        }
    }

    #submit {
        margin-top: 50px;
        text-align: right;

        div {
            cursor: pointer;
            margin: 0px 5px;
            padding: 5px 15px;
            border-radius: 5px;
            text-align: center;
            display: inline-block;

            background: #111111;
            border: 1px solid #111111;
            color: #ffffff;
        }
    }

    .wrap-item {
        width: 100%;
        margin-bottom: 30px;

        span:nth-child(1) {
            display: inline-block;
            width: 130px;
        }

        span:nth-child(2) {
            > input[type="text"] {
                width: 50%;
                padding: 2px 5px;
                border-bottom: 1px solid #dddddd;
            }
        }
    }
}
</style>
