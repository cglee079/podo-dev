<template>
    <div id="write" ref="write" :class="$mq" @click="clickWrite">
        <textarea
            id="contents"
            v-model="input.contents"
            :placeholder="write.placeholder"
            :disabled="!isLogin"
        ></textarea>
        <div id="sub">
            <div id="userInfo">
                <input class="username" type="text" placeholder="이름" v-model="input.username" disabled />
                <p-check class="notified p-default p-curve" v-model="input.notified">알림받기</p-check>
            </div>
            <div id="submit" @click="clickCommentPost">등록</div>
        </div>
    </div>
</template>

<script>
import { mapGetters } from "vuex";
import bus from "../../utils/bus";

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
            doing: false,
            write: {
                placeholder: "로그인 후 댓글을 입력해주세요"
            },
            input: {
                username: "",
                contents: "",
                notified: true,
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
        async clickCommentPost() {
            if (!this.isLogin) {
                return;
            }

            if (this.doing) {
                return;
            }

            try {
                bus.$emit("spinner:start", "post-blog-comment");
                this.doing = true;

                await this.$axios.$post(`/api/blogs/${this.blogId}/comments`, {
                    username: this.input.username,
                    contents: this.input.contents,
                    notified: this.input.notified,
                    parentId: this.parentId
                });

                this.$toast.show("댓글이 등록되었습니다");
                this.input.contents = "";
                this.$emit("reload");
                this.$emit("writeListener");
            } catch (e) {
            } finally {
                bus.$emit("spinner:stop", "post-blog-comment");
                this.doing = false;
            }
        },

        updateInput() {
            if (this.isLogin) {
                this.input.username = this.getUserinfo.username;
                this.write.placeholder = this.placeholder;
            }
        },

        clickWrite() {
            if (!this.isLogin) {
                this.$router.push({ name: "login" });
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

        #userInfo {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: 5px;

            input.username {
                width: 7rem;
                height: 2rem;
                font-size: 0.8rem;
                padding-left: 5px;
                border: 0.7px solid #e1e1e1;
                border-radius: 3px;
            }

            .notified{
                margin-left: 10px;
                font-size: 0.9rem;
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
