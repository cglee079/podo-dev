<template>
    <div>
        <div>
            <div id="btnImageUpload" @click="$refs.file.click()">사진올리기</div>
            <input ref="file" type="file" accept="image/*" multiple
                   @change="onFileChange" style="display: none"
            />
        </div>

        <div id="imageList">
            <div class="image"
                 v-for="(image, index) in images"
                 v-bind:key="index"
                 @click="clickImage(index)"
                 :class="isValidImage(image.fileStatus) ? '' : 'disabled' "
            >
                <img :src="image.domainUrl + image.saves.w100.path + '/' + image.saves.w100.filename"/>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "BlogPostImage",
        props: {
            images: Array
        },
        methods: {
            onFileChange(event) {
                //
                const files = event.target.files
                const index = Array.from(Array(files.length).keys())
                for (let i of index) {
                    this.uploadImage(files[i])
                }

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
                        console.log(image)
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

            clickImage(index) {
                this.removeImage(index)
            }
        }
    }
</script>

<style scoped lang="scss">
    #btnImageUpload {
        width: 100%;
        height: 30px;
        border: 1px solid #CCC;
        background: #FAFAFA;
        display: flex;
        justify-content: center;
        align-items: center;
        cursor: pointer;
    }

    #imageList {
        display: flex;

        .image {
            margin-top: 10px;
            margin-right: 10px;
            border: 1px solid #DDDDDD;
            width: 100px;
            height: 100px;
            overflow: hidden;

            &.disabled {
                display: none;
            }

            img {
                width: 100px;
                height: 100px;
                overflow: hidden;
            }
        }

    }

</style>
