<template>
    <div id="wrapBlogPost">

        <div class="wrapItem">
            <span>썸네일</span>
            <span class="item-input">
                <img id="thumbnail-img">
                <input type="file">
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
                    :value="editor.text"
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
            <span v-for='(value, index) in input.tags'
                  v-bind:key="value"
                  @click="clickTag(index)"
                  class="tag">
                #{{ value }}
            </span>
        </span>
    </div>

</template>

<script>

    import { Editor } from '@toast-ui/vue-editor'

    export default {
        name: 'app',
        components: {
            'editor': Editor
        },
        data() {
            return {
                input: {
                    title: '제목입니다',
                    tagText: '',
                    enabled: 'true',
                    tags: ['A', 'B'],
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
            //태그 Input 입력 시,
            keyupTagText(event) {
                let txt = this.input.tagText
                if (txt.endsWith(" ") || event.keyCode === 13) {
                    txt = txt.trim();

                    if (!this.input.tags.includes(txt) && txt.length) {
                        this.input.tags.push(txt)
                    }
                    this.input.tagText = ''
                }
            },

            // 태그 클릭 시, 태그 삭제
            clickTag(index) {
                this.input.tags.splice(index, 1)
            },

            // Toast Editor
            onEditorLoad() {},
            onEditorFocus() {},
            onEditorBlur() {},
            onEditorChange() {},
            onEditorStateChange() {},

            //게시글 수정 시 호출,
            loadBlog(seq){
                this.$axios
                    .get('http://localhost:8090/api/blogs/' + seq)
                    .then(res => {
                        const blog = res.data.data
                        this.input.title = blog.title
                        this.input.enabled = blog.enabled
                        this.editor.text = blog.contents
                    })
                    .catch(err => {
                        console.log(err)
                    })
            }
        },
        created() {
            const seq = this.$route.params.seq
            if(seq){
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
