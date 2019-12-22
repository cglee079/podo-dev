<template>
    <div>
        <div>
            <div id="btnFileUpload" @click="$refs.file.click()">
                <div>파일올리기</div>
            </div>
            <input ref="file" type="file" multiple @change="onFileChange" style="display: none" />
        </div>

        <div id="fileList">
            <div
                class="file"
                v-for="(attachFile, index) in attachFiles"
                v-bind:key="index"
                :class="isValidFile(attachFile.attachStatus) ? '' : 'disabled'"
            >
                <div class="name">
                    [{{ formatFilesize(attachFile.filesize) }}]
                    {{ attachFile.originFilename }}
                </div>
                <div class="btn-remove" @click="clickBtnAttachFileRemove(index)">
                    REMOVE
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import filesize from "filesize";

export default {
    name: "BlogPostFile",
    props: {
        attachFiles: Array
    },
    methods: {
        onFileChange(event) {
            const files = event.target.files;

            this.uploadFile(files, 0, files.length - 1);
        },

        uploadFile(files, i, until) {
            this.$emit("onProgress");

            new Promise((resolve, reject) => {
                const config = {
                    headers: { "Content-Type": "multipart/form-data" }
                };

                const formData = new FormData();
                formData.append("file", files[i]);

                this.$axios
                    .$post("/api/blogs/files", formData, config)
                    .then(res => {
                        resolve(res);
                    })
                    .catch(err => {
                        reject(err);
                    });
            })
                .then(res => {
                    const file = res.result;
                    this.$emit("add", file);
                    this.$emit("offProgress");

                    if (i < until) {
                        this.uploadFile(files, i + 1, until);
                    }
                })
                .catch(() => {
                    this.$emit("offProgress");
                });
        },

        /**
         * 삭제된 파일인지 검증
         * @param status
         * @returns {boolean}
         */
        isValidFile(status) {
            if (status === "BE" || status === "NEW") {
                return true;
            }
            return false;
        },

        formatFilesize(value) {
            return filesize(value);
        },

        removeAttachFile(index) {
            this.$emit("remove", index);
        },

        clickBtnAttachFileRemove(index) {
            this.removeAttachFile(index);
        }
    }
};
</script>

<style lang="scss" scoped>
#btnFileUpload {
    height: 30px;
    border: 1px solid #ccc;
    background: #fafafa;
    cursor: pointer;
    display: flex;

    div {
        flex: 1;
        display: flex;
        justify-content: center;
        align-items: center;
    }
}

#fileList {
    .file {
        display: flex;
        align-items: center;
        justify-content: space-between;
        border: 1px solid #ccc;
        padding: 7px;
        margin: 5px 0px;

        &.disabled {
            display: none;
        }
    }

    .btn-remove {
        display: inline-block;
        padding: 5px 10px 5px 10px;
        margin-left: 10px;
        background: #fcc;
        text-align: center;
        cursor: pointer;
    }
}
</style>
