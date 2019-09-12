<template>
    <div>
        <div>
            <div id="btnFileUpload" @click="$refs.file.click()">
                <div>파일올리기</div>
            </div>
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
                const files = event.target.files

                this.uploadFile(files, 0, files.length - 1)

            },

            uploadFile(files, i, until) {
                this.$emit('onProgress')

                new Promise((resolve, reject) => {
                    const config = {
                        headers: {'Content-Type': 'multipart/form-data'}
                    }

                    const formData = new FormData()
                    formData.append("file", files[i])

                    this.$axios
                        .post("/api/blogs/files", formData, config)
                        .then(res => {
                            resolve(res)
                        })
                        .catch(err => {
                            reject(err)
                        })
                }).then(res => {
                    res = res.data
                    const file = res.data
                    this.$emit('add', file)
                    this.$emit('offProgress')

                    if (i < until) {
                        this.uploadFile(files, i + 1, until)
                    }

                }).catch(err => {
                    console.log(err)
                    this.$emit('offProgress')
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

<style lang="scss" scoped>
    #btnFileUpload {
        height: 30px;
        border: 1px solid #CCC;
        background: #FAFAFA;
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
            margin-left: 10px;
            background: #FCC;
            text-align: center;
            cursor: pointer;
        }

    }


</style>
