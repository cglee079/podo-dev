<template>
    <div id="wrapBlogPost">

        <div class="wrapItem">
            <span>썸네일</span>
            <span class="item-input">
                <img id="thumbnail-img">
                <input type="file" @change="fileChange" ref="thumbnail">
            </span>
        </div>

        <div class="wrapItem">
            <span>공개여부</span>
            <span>
                <select v-model="input.enabled">
                    <option value="true">공개</option>
                    <option value="false">비공개</option>
                </select>
            </span>
        </div>

        <div class="wrapItem">
            <span>제목</span>
            <span>
                <input type="text" v-model="input.title">
            </span>
        </div>

        <div class="wrapEditor">
            <editor
                    v-model="editor.text"
                    :options="editor.options"
                    :html="editor.html"
                    :visible="editor.visible"
                    :height="editor.height"
                    previewStyle="vertical"
                    @load="onEditorLoad"
                    @focus="onEditorFocus"
                    @blur="onEditorBlur"
                    @change="onEditorChange"
                    @stateChange="onEditorStateChange"/>
        </div>

        <div class="wrapItem">
            <span>태그</span>
            <span>
                <input type="text" v-model="input.tagText" @keyup="keyupTagText">
            </span>
        </div>

        <span id="tags">
            <span v-for='(tag, index) in input.tags'
                  v-bind:key="index"
                  @click="clickTag(index)"
                  class="tag">
                #{{ tag.val }}
            </span>
        </span>

        <div>
            <sub-button value="작성" @click="clickSubmit"/>
        </div>
    </div>

</template>

<script>
    import {Editor} from '@toast-ui/vue-editor'

    export default {
        name: 'app',
        components: {
            'editor': Editor
        },
        data() {
            return {
                isNew: true,
                seq: 0,
                input: {
                    title: '제목입니다',
                    tagText: '',
                    enabled: 'true',
                    tags: [],
                },
                editor: {
                    text: '<img src=http://192.168.219.103:7070/resources/image/home_icon_me.png">',
                    options: {},
                    html: '',
                    height: '700px',
                    visible: true
                }

            }
        },
        methods: {
            fileChange() {
                const thumbnail = this.$refs.thumbnail.files[0];
                const formData = new FormData();
                formData.append('thumbnail', thumbnail);

                this.$axios
                    .post('http://localhost:8091/api/images', formData, {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    })
                    .then(res => {
                        console.log(res)
                    })
                    .catch(err => {
                        console.log(err)
                    })
            },

            //태그 Input 입력 시,
            keyupTagText(event) {
                let txt = this.input.tagText
                if (txt.endsWith(" ") || event.keyCode === 13) {
                    txt = txt.trim();

                    if (txt.length) {
                        let included = false

                        this.input.tags.forEach((tag) => {
                            if (tag.val === txt) {
                                included = true
                                return
                            }
                        })

                        if (!included) {
                            const obj = {}
                            obj.seq = undefined
                            obj.val = txt
                            this.input.tags.push(obj)
                        }
                    }

                    this.input.tagText = ''
                }
            },

            // 태그 클릭 시, 태그 삭제
            clickTag(index) {
                this.input.tags.splice(index, 1)
            },

            // Toast Editor
            onEditorLoad() {
            },
            onEditorFocus() {
            },
            onEditorBlur() {
            },
            onEditorChange() {
            },
            onEditorStateChange() {
            },

            //게시글 수정 시, 게시글 정보 로딩
            loadBlog(seq) {
                this.$axios
                    .get('/api/blogs/' + seq)
                    .then(res => {
                        const blog = res.data.data
                        this.seq = blog.seq
                        this.input.title = blog.title
                        this.input.enabled = blog.enabled
                        this.input.tags = blog.tags
                        this.editor.text = blog.contents

                        console.log(blog)
                    })
                    .catch(err => {
                        console.log(err)
                    })
            },

            clickSubmit() {
                if (this.isNew) {
                    this.insertBlog()
                } else {
                    this.updateBlog()
                }
            },

            insertBlog() {
                this.$axios
                    .post('/api/blogs', {
                        title: this.input.title,
                        contents: this.editor.text,
                        enabled: this.input.enabled,
                        tags: this.input.tags
                    })

                    .then(res => {
                        this.$router.go(-1)
                        console.log(res)
                    })
                    .catch(err => {
                        console.log(err)
                    })
            },

            updateBlog() {
                this.$axios
                    .put('/api/blogs/' + this.seq, {
                        title: this.input.title,
                        contents: this.editor.text,
                        enabled: this.input.enabled,
                        tags: this.input.tags
                    })

                    .then(res => {
                        this.$router.go(-1)
                        console.log(res)
                    })
                    .catch(err => {
                        console.log(err)
                    })
            }
        },
        created() {
            const seq = this.$route.params.seq
            if (seq) {
                this.isNew = false
                this.loadBlog(seq)
            }

        }
    }
</script>

<style scoped>

    .wrapItem {
        width: 100%;
        margin-bottom: 30px;
    }

    .wrapEditor {
        margin: 40px 0px;
    }

    .wrapItem > span:nth-child(1) {
        display: inline-block;
        width: 130px;
    }

    .wrapItem > span:nth-child(2) > input[type=text] {
        width: 50%;
        padding: 2px 5px;
        border-bottom: 1px solid #DDDDDD;
    }

    #tags .tag {
        cursor: pointer;
        padding: 3px 10px;
        margin: 2px 3px;
        background: #E7E7E7;
        border-radius: 3px;
    }


</style>
