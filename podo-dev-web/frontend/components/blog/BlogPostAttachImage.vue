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
            <input
                ref="file"
                type="file"
                accept="image/*"
                multiple
                @change="onFileChange"
                style="display: none"
            />
        </div>

        <div id="imageList">
            <div
                class="image"
                v-for="(attachImage, index) in attachImages"
                :key="index"
                :class="isValidImage(attachImage.attachStatus) ? '' : 'disabled'"
            >
                <div class="menus">
                    <div class="menu-delete" @click="clickAttachImageRemove(index)">
                        삭제
                    </div>
                </div>

                <img
                    :src="
                        `${attachImage.uploadedUrl}${attachImage.saves.origin.filePath}/${attachImage.saves.origin.filename}`
                    "
                    alt="attachImage"
                />
            </div>
        </div>
    </div>
</template>

<script>
import bus from "../../utils/bus";

export default {
    name: "BlogPostAttachImage",
    props: {
        attachImages: Array
    },
    methods: {
        onKeyPress(event) {
            event.preventDefault();
        },

        onPaste(event) {
            event.preventDefault();

            const clipboardData = event.clipboardData;
            const imageType = /^image/;
            const plainType = /plain/;
            const urlPattern = new RegExp(
                "(http|ftp|https)://[a-z0-9\-_]+(\.[a-z0-9\-_]+)+([a-z0-9\-\.,@\?^=%&;:/~\+#]*[a-z0-9\-@\?^=%&;/~\+#])?",
                "i"
            );

            if (!clipboardData) {
                return;
            }

            clipboardData.types.forEach((type, i) => {
                if (type.match(plainType) || clipboardData.items[i].type.match(plainType)) {
                    clipboardData.items[i].getAsString(text => {
                        if (urlPattern.test(text)) {
                            this.toastConfirm("이미지를 서버에 저장하시겠습니까?", () => {
                                this.uploadImageUrl(text);
                            });
                            return;
                        }

                        if (text.indexOf("base64,") !== -1) {
                            this.toastConfirm("이미지를 서버에 저장하시겠습니까?", () => {
                                this.uploadBase64(text);
                            });
                            return;
                        }

                        this.$toast.show("잘못된 데이터 입니다");
                    });
                }

                if (type.match(imageType) || clipboardData.items[i].type.match(imageType)) {
                    this.readImageAsBase64(clipboardData.items[i]);
                }
            });
        },

        readImageAsBase64(item) {
            if (!item || typeof item.getAsFile !== "function") {
                return;
            }

            const file = item.getAsFile();

            this.toastConfirm("이미지를 서버에 저장하시겠습니까?", () => {
                const reader = new FileReader();
                reader.onload = evt => {
                    const base64 = evt.target.result;
                    this.uploadBase64(base64);
                };
                reader.readAsDataURL(file);
            });
        },

        onFileChange(event) {
            const fileOfAttachImages = event.target.files;

            if (fileOfAttachImages.length === 0) {
                return;
            }

            this.uploadImage(fileOfAttachImages, 0);
        },

        async uploadImage(files, idx) {
            bus.$emit("startSpinner");

            const config = {
                headers: { "Content-Type": "multipart/form-data" }
            };

            const formData = new FormData();
            formData.append("fileOfImage", files[idx]);

            try {
                const response = await this.$axios.$post("/api/blogs/images", formData, config);

                const attachImage = response.result;

                this.$emit("add", attachImage);
                bus.$emit("stopSpinner");

                this.$toast.show(`'${files[idx].name}' 업로드 완료하였습니다`);

                if (idx < files.length - 1) {
                    this.uploadImage(files, idx + 1);
                }
            } catch {
                this.$toast.show(`죄송합니다, '${files[idx].name}' 업로드 실패하였습니다`);
                bus.$emit("stopSpinner");
            }
        },

        async uploadBase64(base64) {
            bus.$emit("startSpinner");

            try {
                const response = this.$axios.$post("/api/blogs/images", { base64: base64 });
                const attachImage = response.result;
                this.$emit("add", attachImage);
            } catch (e) {
                this.$toast.show(`죄송합니다, 업로드 실패하였습니다`);
            } finally {
                bus.$emit("stopSpinner");
            }
        },

        async uploadImageUrl(imageUrl) {
            bus.$emit("startSpinner");

            try {
                const response = await this.$axios.$post("/api/blogs/images", {
                    imageUrl: imageUrl
                });
                const attachImage = response.result;
                this.$emit("add", attachImage);
            } catch {
                this.$toast.show(`죄송합니다, 업로드 실패하였습니다`);
            } finally {
                bus.$emit("stopSpinner");
            }
        },

        /**
         * 삭제된 이미지 인지 검증
         */
        isValidImage(status) {
            return status === "BE" || status === "NEW";
        },

        clickAttachImageRemove(index) {
            this.removeAttachImage(index);
        },

        removeAttachImage(index) {
            this.$emit("remove", index);
        }
    }
};
</script>

<style lang="scss" scoped>
#pasteImageUpload {
    margin-top: 10px;
    display: flex;

    border-bottom: 1px solid #cccccc;

    input {
        flex: 1;
        padding: 5px 10px;
    }
}

#btnImageUpload {
    display: flex;
    height: 30px;
    border: 1px solid #cccccc;
    background: #fafafa;
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
        border: 1px solid #dddddd;
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
                background: #ff0000;
                border: 1px solid #ffffff;
                opacity: 0.8;
                color: #ffffff;
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
