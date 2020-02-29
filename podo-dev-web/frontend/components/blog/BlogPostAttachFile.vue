<template>
    <div>
        <div>
            <blog-post-long-button value="파일올리기" @click="$refs.file.click()"></blog-post-long-button>
            <input ref="file" type="file" multiple @change="onFileChange" style="display: none" />
        </div>

        <div id="fileList">
            <div
                class="file"
                v-for="(attachFile, index) in attachFiles"
                :key="index"
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
import bus from "../../utils/bus";
import BlogPostLongButton from "./BlogPostLongButton";

export default {
    name: "BlogPostFile",
    components: {BlogPostLongButton},
    props: {
        attachFiles: Array
    },
    methods: {
        onFileChange(event) {
            const files = event.target.files;

            if (files.length === 0) {
                return;
            }

            this.uploadFile(files, 0);
        },

        async uploadFile(files, idx) {
            bus.$emit("spinner:start", "upload-blog-file");

            const config = {
                headers: { "Content-Type": "multipart/form-data" }
            };

            const formData = new FormData();
            formData.append("file", files[idx]);

            try {
                const response = await this.$axios.$post("/api/blogs/files", formData, config);

                const file = response.result;
                this.$emit("add", file);

                this.$toast.show(`'${files[idx].name}' 업로드 완료하였습니다`);
                bus.$emit("spinner:stop", "upload-blog-file");

                if (idx < files.length - 1) {
                    this.uploadFile(files, idx + 1);
                }
            } catch (e) {
                this.$toast.show(`죄송합니다, '${files[idx].name}' 업로드 실패하였습니다`);
                bus.$emit("spinner:stop", "upload-blog-file");
            }
        },

        /**
         * 삭제된 파일인지 검증
         */
        isValidFile(status) {
            return status === "BE" || status === "NEW";
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
#fileList {
    .file {
        display: flex;
        align-items: center;
        justify-content: space-between;
        border: 1px solid #ccc;
        padding: 7px;
        margin: 5px 0;

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
