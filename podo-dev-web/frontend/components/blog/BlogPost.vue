<template>
    <div id="wrapBlogPost" :class="$mq">
        <progress-bar ref="progressBar"/>

        <div class="wrap-item">
            <span>공개여부</span>
            <span>
                <select v-model="input.enabled">
                    <option value="true">공개</option>
                    <option value="false">비공개</option>
                </select>
            </span>
        </div>

        <div class="wrap-item">
            <span>제목</span>
            <span>
                <input type="text" v-model="input.title">
            </span>
        </div>

        <div id="wrapEditor">
            <client-only>
                <TuiEditor ref="tuiEditor"
                           mode="markdown"
                           preview-style="vertical"
                           height="100%"
                           v-model="input.contents"
                />
            </client-only>

        </div>


        <div id="wrapImageUpload">
            <post-image
                :images="this.input.images"
                @add="addImage"
                @delete="deleteImage"
                @onProgress="onProgress"
                @offProgress="offProgress"
            />
        </div>

        <div id="wrapFileUpload">
            <post-file
                :files="this.input.files"
                @add="addFile"
                @delete="deleteFile"
                @onProgress="onProgress"
                @offProgress="offProgress"
            />

        </div>

        <div id="wrapTag" class="wrap-item">
            <span>태그</span>

            <span>
                <input type="text" v-model="input.tagText" @keyup="keyupTagText">
            </span>
        </div>

        <div id="tags">
            <span v-for='(tag, index) in input.tags'
                  v-bind:key="index"
                  @click="clickTag(index)"
                  class="tag">
                #{{tag.val}}
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
    import BlogPostImage from "./BlogPostImage";
    import BlogPostFile from "./BlogPostFile";
    import ProgressBar from "../global/ProgressBar";

    export default {
        name: 'app',
        components: {
            ProgressBar,
            'post-image': BlogPostImage,
            'post-file': BlogPostFile
        },
        props: {
            id: Number
        },
        watch: {
            id(value) {
                this.isNew = false
                this.loadBlog(value)
            }
        },
        data() {
            return {
                isNew: true,
                config: {
                    maxWidth: 720
                },
                autoSave: {
                    key: "autoSave_post_",
                    interval: undefined
                },
                temp: '',
                input: {
                    title: '',
                    contents: '',
                    tagText: '',
                    enabled: true,
                    tags: [],
                    images: [],
                    files: []
                },

            }
        },
        methods: {
            onProgress() {
                this.$refs.progressBar.on()
            },
            offProgress() {
                this.$refs.progressBar.off()
            },

            //태그 Input 입력 시,
            keyupTagText(event) {
                let txt = this.input.tagText
                if (txt.endsWith(" ") || event.keyCode === 13) {
                    event.preventDefault()
                    txt = txt.trim();

                    if (txt.length) {
                        let included = false

                        this.input.tags.forEach((tag) => {
                            if (tag.val === txt) {
                                included = true
                                return
                            }
                        })

                        if (included) {
                            this.$toast.show(txt + "는 중복됩니다!")
                            this.input.tagText = ''
                            return
                        }

                        const obj = {}
                        obj.id = undefined
                        obj.val = txt
                        this.input.tags.push(obj)
                    }

                    this.input.tagText = ''
                }
            },

            // 태그 클릭 시, 태그 삭제
            clickTag(index) {
                this.input.tags.splice(index, 1)
            },

            //게시글 수정 시, 게시글 정보 로딩
            loadBlog(id) {
                this.$axios
                    .$get('/api/blogs/' + this.id)
                    .then(res => {
                        console.log(res)
                        const blog = res.data
                        this.input.title = blog.title
                        this.input.enabled = blog.enabled
                        this.input.tags = blog.tags
                        this.input.images = blog.images
                        this.input.files = blog.files
                        this.input.contents = blog.contents

                    })
                    .catch(err => {
                        console.log(err)
                    })
            },

            clickSubmit() {
                this.removeSaved(this.id)

                if (this.isNew) {
                    this.insertBlog()
                } else {
                    this.updateBlog()
                }
            },

            insertBlog() {
                this.onProgress()

                this.$axios
                    .$post('/api/blogs', {
                        title: this.input.title,
                        contents: this.input.contents,
                        enabled: this.input.enabled,
                        tags: this.input.tags,
                        images: this.input.images,
                        files: this.input.files
                    })

                    .then(res => {
                        this.offProgress()
                        this.$router.push({name: 'index'})
                    })
                    .catch(err => {
                        this.offProgress()
                    })
            },

            updateBlog() {
                this.onProgress()

                this.$axios
                    .$put('/api/blogs/' + this.id, {
                        title: this.input.title,
                        contents: this.input.contents,
                        enabled: this.input.enabled,
                        tags: this.input.tags,
                        images: this.input.images,
                        files: this.input.files
                    })

                    .then(res => {
                        this.offProgress()
                        this.$router.push({
                            name: 'blogs-id',
                            params: {
                                id: this.id
                            }
                        });
                    })
                    .catch(err => {
                        this.offProgress()
                    })
            },

            /**
             * Call By Child
             */
            addImage(image) {
                const src = image.uploadedUrl + image.saves.origin.path + "/" + image.saves.origin.filename
                const tag = document.createElement("img")

                let width = image.saves.origin.width

                if (width > this.config.maxWidth) {
                    width = this.config.maxWidth
                }

                tag.src = src
                tag.setAttribute("style", 'width:' + width + 'px;')

                this.input.contents += "\n\n" + tag.outerHTML + "\n\n"

                this.input.images.push(image)
            },

            deleteImage(index) {

                const image = this.input.images[index]
                switch (image.fileStatus) {
                    case 'BE' :
                        image.fileStatus = 'REMOVE'
                        break
                    case 'NEW' :
                        image.fileStatus = 'UNNEW'
                        break
                    default :
                        break
                }

                this.removeImageTagInEditor(image)
            },

            removeImageTagInEditor(image) {

                let text = this.input.contents
                const index = text.indexOf(image.saves.origin.filename)
                const startTagIndex = text.substring(0, index).lastIndexOf("<img")
                const endTagIndex = text.substring(index, text.length).indexOf(">") + index
                const tag = text.substring(startTagIndex, endTagIndex + 1)

                text = text.replace(tag, "")

                this.input.contents = text
            },

            addFile(file) {
                this.input.files.push(file)
            },

            deleteFile(index) {
                const file = this.input.files[index]
                switch (file.fileStatus) {
                    case 'BE' :
                        file.fileStatus = 'REMOVE'
                        break
                    case 'NEW' :
                        file.fileStatus = 'UNNEW'
                        break
                    default :
                        break
                }
            },

            removeSaved(id) {
                const autoSaveKey = this.autoSave.key + id

                this.$storage.removeLocalStorage(autoSaveKey + "_is")
                this.$storage.removeLocalStorage(autoSaveKey + "_input")
            }


        },
        created() {

            const id = this.id ? this.id : null

            if (id) {
                this.isNew = false
                this.loadBlog(id)
            }

            if (process.client) {
                const id = this.id ? this.id : ''
                const autoSaveKey = this.autoSave.key + id

                const isSave = this.$storage.getLocalStorage(autoSaveKey + "_is")
                const saveInput = this.$storage.getLocalStorage(autoSaveKey + "_input")


                if (isSave) {

                    this.toastConfirm("자동저장된 데이터가있습니다, 로딩하시겠습니까?",
                        () => {
                            this.input = saveInput
                            this.removeSaved(id)
                        },
                        () => {
                            this.removeSaved(id)
                        }
                    )
                }
            }


        },

        mounted() {
            if (process.client) {
                const id = this.id ? this.id : ''
                const autoSaveKey = this.autoSave.key + id

                //Setting Interval
                this.autoSave.interval = setInterval(() => {
                    const currentInput = JSON.stringify(this.input)
                    const saveInput = JSON.stringify(this.$storage.getLocalStorage(autoSaveKey + "_input"))

                    if (saveInput !== currentInput) {
                        this.$toast.show("자동저장 되었습니다")
                        this.$storage.setLocalStorage(autoSaveKey + "_is", "true")
                        this.$storage.setLocalStorage(autoSaveKey + "_input", currentInput)
                    }
                }, 1000 * 60 * 2)
            }
        },

        destroyed() {
            if (process.client) {
                clearInterval(this.autoSave.interval)
            }
        },

    }
</script>

<style lang="scss" scoped>
    #wrapBlogPost {
        &.mobile, &.tablet {
            padding-left: 5%;
            padding-right: 5%;
        }

        #wrapEditor {
            margin: 40px 0px;
            width: 100%;
            height: 700px;
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
                background: #E7E7E7;
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
                color: #FFFFFF;
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
                > input[type=text] {
                    width: 50%;
                    padding: 2px 5px;
                    border-bottom: 1px solid #DDDDDD;
                }
            }
        }

    }


</style>
