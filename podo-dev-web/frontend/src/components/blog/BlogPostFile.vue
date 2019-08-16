<template>
    <div>
        <div>
            <div id="btnFileUpload" @click="$refs.file.click()">파일올리기</div>
            <input ref="file" type="file" multiple @change="onFileChange" style="display: none"/>
        </div>

        <div id="fileList">
            <div class="file"
                 v-for="(file, index) in files"
                 v-bind:key="index"
                 :class="isValidFile(file.fileStatus) ? '' : 'disabled' "
            >
                <div class="name">[{{formatFilesize(file.filesize)}}] {{file.originName}}</div>
                <div class="btn-remove" @click="clickBtnRemove(index)">REMOVE</div>
            </div>
        </div>
    </div>
</template>

<script>
    import filesize from 'filesize'

    export default {
        name: "BlogPostFile",
        props: {
            files: Array
        },
        methods: {
            onFileChange(event) {
                //
                const files = event.target.files
                const index = Array.from(Array(files.length).keys())
                for (let i of index) {
                    this.uploadFile(files[i])
                }

            },

            uploadFile(file) {
                const config = {
                    headers: {'Content-Type': 'multipart/form-data'}
                }

                const formData = new FormData()
                formData.append("file", file)

                this.$axios
                    .post("/api/blogs/files", formData, config)
                    .then(res => {
                        res = res.data
                        const file = res.data
                        console.log(file)
                        this.$emit('add', file)
                    })
                    .catch(err => {
                        console.log(err)
                    })
            },

            /**
             * 삭제된 파일인지 검증
             * @param status
             * @returns {boolean}
             */
            isValidFile(status) {
                if (status === 'BE' || status === 'NEW') {
                    return true
                }
                return false
            },

            formatFilesize(value) {
                return filesize(value)
            },
            removeImage(index) {
                this.$emit('delete', index)
            },

            clickBtnRemove(index) {
                this.removeImage(index)
            }
        }
    }
</script>

<style scoped lang="scss">
    #btnFileUpload {
        width: 100%;
        height: 30px;
        border: 1px solid #CCC;
        background: #FAFAFA;
        display: flex;
        justify-content: center;
        align-items: center;
        cursor: pointer;
    }

    #fileList {

        .file {
            display: flex;
            align-items: center;
            justify-content: space-between;
            border: 1px solid #CCC;
            padding: 7px;
            margin: 5px 0px;

            &.disabled {
                display: none;
            }
        }

        .btn-remove {
            display: inline-block;
            padding: 5px 10px 5px 10px;
            background: #FCC;
            text-align: center;
            cursor: pointer;
        }

    }


</style>
