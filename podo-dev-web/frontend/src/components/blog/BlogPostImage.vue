<template>
    <div>
        <div id="pasteImageUpload">
            <input
                    @paste="onPaste"
                    @keypress="onKeyPress"
                    placeholder="여기에 이미지 붙여넣기를 해주세요!"
            />
        </div>

        <div>
            <div id="btnImageUpload" @click="$refs.file.click()">
                <div>
                    사진올리기
                </div>
            </div>
            <input ref="file" type="file" accept="image/*" multiple
                   @change="onFileChange" style="display: none"
            />
        </div>

        <div id="imageList">
            <div class="image"
                 v-for="(image, index) in images"
                 v-bind:key="index"

                 :class="isValidImage(image.fileStatus) ? '' : 'disabled' "
            >
                <div class="menus">
                    <div class="menu-delete" @click="clickImageDelete(index)">삭제</div>
                </div>

                <img :src=" image.domainUrl + image.saves.origin.path +
                    '/' + image.saves.origin.filename"/>
            </div>
        </div>
    </div>
</template>

<script>
    import customToast from '@/mixins/customToast'

    export default {
        name: "BlogPostImage",
        props: {
            images: Array
        },
        mixins: [customToast],
        methods: {
            onKeyPress(event) {
                event.preventDefault()
            },
            onPaste(event) {
                event.preventDefault()

                const clipboardData = event.clipboardData
                const imageType = /^image/
                const plainType = /plain/
                const urlPattern = new RegExp('(http|ftp|https)://[a-z0-9\-_]+(\.[a-z0-9\-_]+)+([a-z0-9\-\.,@\?^=%&;:/~\+#]*[a-z0-9\-@\?^=%&;/~\+#])?', 'i');

                if (!clipboardData) {
                    return
                }

                clipboardData.types.forEach((type, i) => {
                    if (type.match(plainType) || clipboardData.items[i].type.match(plainType)) {
                        clipboardData.items[i].getAsString((text) => {
                            if (urlPattern.test(text)) {
                                this.toastConfirm(
                                    "이미지를 서버에 저장하시겠습니까?",
                                    () => {
                                        this.uploadImageUrl(text)
                                    })
                                return
                            }

                            if (text.indexOf("base64,") !== -1) {
                                this.toastConfirm(
                                    "이미지를 서버에 저장하시겠습니까?",
                                    () => {
                                        this.uploadBase64(text)
                                    })
                                return
                            }

                            this.$toasted.show("잘못된 데이터 입니다")
                            return


                        })

                    }

                    if (type.match(imageType) || clipboardData.items[i].type.match(imageType)) {
                        this.readImageAsBase64(clipboardData.items[i])
                    }
                })

            },

            readImageAsBase64(item) {
                if (!item || typeof item.getAsFile !== 'function') {
                    return
                }

                const file = item.getAsFile();

                this.toastConfirm(
                    "이미지를 서버에 저장하시겠습니까?",
                    () => {
                        const reader = new FileReader();
                        reader.onload = (evt) => {
                            const base64 = evt.target.result;
                            this.uploadBase64(base64)
                        }
                        reader.readAsDataURL(file);
                    })
            },
            onFileChange(event) {
                //
                const files = event.target.files
                const index = Array.from(Array(files.length).keys())
                for (let i of index) {
                    this.uploadImage(files[i])
                }

            },

            uploadBase64(base64) {
                this.$axios
                    .post("/api/blogs/images", {base64: base64})
                    .then(res => {
                        res = res.data
                        const image = res.data
                        this.$emit('add', image)
                    })
                    .catch(err => {
                        console.log(err)
                    })
            },

            uploadImageUrl(url) {
                this.$axios
                    .post("/api/blogs/images", {url: url})
                    .then(res => {
                        res = res.data
                        const image = res.data
                        this.$emit('add', image)
                    })
                    .catch(err => {
                        console.log(err)
                    })
            },

            uploadImage(file) {
                const config = {
                    headers: {'Content-Type': 'multipart/form-data'}
                }
                const formData = new FormData()
                formData.append("image", file)

                this.$axios
                    .post("/api/blogs/images", formData, config)
                    .then(res => {
                        res = res.data
                        const image = res.data
                        this.$emit('add', image)
                    })
                    .catch(err => {
                        console.log(err)
                    })
            },

            /**
             * 삭제된 이미지 인지 검증
             * @param status
             * @returns {boolean}
             */
            isValidImage(status) {
                if (status === 'BE' || status === 'NEW') {
                    return true
                }
                return false
            },

            removeImage(index) {
                this.$emit('delete', index)
            },

            clickImageDelete(index) {
                this.removeImage(index)
            }
        }
    }
</script>

<style lang="scss" scoped>
    #pasteImageUpload {
        margin-top: 10px;
        display: flex;

        border-bottom: 1px solid #CCCCCC;

        input {
            flex: 1;
            padding: 5px 10px;
        }
    }

    #btnImageUpload {
        display: flex;
        height: 30px;
        border: 1px solid #CCCCCC;
        background: #FAFAFA;
        margin-top: 20px;
        cursor: pointer;

        div {
            flex: 1;
            display: flex;
            justify-content: center;
            align-items: center;
        }
    }

    #imageList {
        display: flex;
        flex-wrap: wrap;

        .image {
            position: relative;
            margin-top: 10px;
            margin-right: 10px;
            border: 1px solid #DDDDDD;
            width: 100px;
            height: 100px;
            overflow: hidden;

            &.disabled {
                display: none;
            }

            .menus {
                position: absolute;
                left: 0;
                top: 0;
                bottom: 0;
                right: 0;

                display: flex;
                justify-content: flex-end;
                align-items: flex-end;
                flex-flow: wrap;

                .menu-delete {
                    padding: 2px 5px;
                    background: #FF0000;
                    border: 1px solid #FFFFFF;
                    opacity: 0.8;
                    color: #FFFFFF;
                    cursor: pointer;
                }
            }


            img {
                z-index: -1;
                opacity: 0.8;
                position: absolute;
                top: 0;
                max-height: 100px;
            }
        }

    }

</style>
