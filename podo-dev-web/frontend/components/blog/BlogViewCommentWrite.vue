<template>
    <div id="write" ref="write" :class="$mq" @click="clickWrite">
        <textarea
            id="contents"
            :placeholder="write.placeholder"
            v-model="input.contents"
            :disabled="!isLogin"
        ></textarea>
        <div id="sub">
            <div id="user">
                <div id="username">
                    <input type="text" placeholder="이름" v-model="input.username" disabled />
                </div>
            </div>
            <div id="submit" @click="clickCommentPost">등록</div>
        </div>
    </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

export default {
    name: "BlogViewCommentWrite",
    props: {
        index: Number,
        blogId: Number,
        parentId: Number,
        placeholder: String
    },
    data() {
        return {
            write: {
                placeholder: "로그인 후 댓글을 입력해주세요"
            },
            input: {
                username: "",
                contents: ""
            }
        };
    },
    computed: {
        ...mapGetters({
            isLogin: "user/isLogin",
            getUserinfo: "user/getUserinfo"
        })
    },
    methods: {
        ...mapActions({
            login: "user/login"
        }),

        clickCommentPost() {
            if (!this.isLogin) {
                return;
            }

            this.$axios
                .$post(`/api/blogs/${this.blogId}/comments`, {
                    username: this.input.username,
                    contents: this.input.contents,
                    parentId: this.parentId
                })
                .then(() => {
                    this.$toast.show("댓글이 등록되었습니다");
                    this.input.contents = "";
                    this.$emit("reload");
                    this.$emit("writeListener");
                });
        },

        updateInput() {
            if (this.isLogin) {
                this.input.username = this.getUserinfo.username;
                this.write.placeholder = this.placeholder;
            }
        },

        clickWrite() {
            if (!this.isLogin) {
                this.login();
            }
        }
    },

    updated() {
        this.updateInput();
    },

    created() {
        this.updateInput();
    }
};
</script>

<style lang="scss" scoped>
#write {
    margin-top: 20px;

    &.mobile,
    &.tablet {
        padding-left: 5%;
        padding-right: 5%;
    }

    #contents {
        width: 100%;
        box-sizing: border-box;
        height: 7rem;
        padding: 10px;
        resize: none;
        overflow: hidden;
        border: 0.7px solid #e1e1e1;
        border-radius: 3px;
    }

    #sub {
        display: flex;
        justify-content: space-between;
        align-items: baseline;

        #user {
            display: flex;
            justify-content: space-between;
            margin-top: 5px;
            height: 2rem;

            div {
                margin-right: 3px;
            }

            input {
                width: 7rem;
                height: 2rem;
                font-size: 0.8rem;
                padding-left: 5px;
                border: 0.7px solid #e1e1e1;
                border-radius: 3px;
            }
        }

        #submit {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 2rem;
            font-size: 0.8rem;
            padding: 0px 20px;
            color: #ffffff;
            border-radius: 3px;
            background: #222222;
            cursor: pointer;
        }
    }
}
</style>
