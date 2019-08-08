<template>
    <div>
        <div>
            <div id="btnImageUpload" @click="$refs.file.click()">사진올리기</div>
            <input ref="file" type="file" accept="image/*" multiple
                   @change="onFileChange" style="display: none;"
            />

        </div>

        <div class="image-list">

        </div>
    </div>
</template>

<script>
    export default {
        name: "BlogPostImage",
        methods : {
            onFileChange(event) {
                //
                const config = {
                    headers: {'Content-Type': 'multipart/form-data'}
                }

                const formData = new FormData();
                formData.append("images", event.target.files);

                this.$axios
                    .post("/api/blogs/images", formData, config)
                    .then(res => {
                        this.image = res.data.data;
                        alert("이미지를 업로드하였습니다");
                    })
                    .catch(err => {
                        console.log(err)
                    })
            },
        }
    }
</script>

<style scoped>
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
</style>
