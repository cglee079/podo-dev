<template>
    <div id="wrapLogin">
        <div>
            <div id="desc">
                <img src="../../assets/image/login.jpg" alt="desc">

                "become a gooooood develpoer"
            </div>

            <div id="btns">
                <div class="btn" @click="login('google')">
                    <img src="../../assets/btns/oauth/google.svg" alt="google_login" />
                </div>

                <div class="btn" @click="login('facebook')">
                    <img src="../../assets/btns/oauth/facebook.svg" alt="facebook_login" />
                </div>

                <div class="btn" @click="login('github')">
                    <img src="../../assets/btns/oauth/github.svg" alt="github_login" />
                </div>

                <div class="btn" @click="login('kakao')">
                    <img src="../../assets/btns/oauth/kakao.svg" alt="kakao_login" />
                </div>

                <div class="btn" @click="login('naver')">
                    <img src="../../assets/btns/oauth/naver.svg" alt="naver_login" />
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { mapActions } from "vuex";

export default {
    name: "login",
    methods: {
        ...mapActions({
            login: "user/login"
        }),
    }
};
</script>

<style lang="scss" scoped>
#wrapLogin {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: -5%;

    > div {
        opacity: 0.9;
        padding: 60px 40px;
        display: flex;
        justify-content: center;
        align-items: center;
        flex-flow: column;
        border-radius: 20px;
    }
}

#desc {
    text-align: center;
    font-weight: bold;
    margin: 20px 0;
    font-style: italic;
    opacity: 0.8;

    > img {
        height: 200px;
        display: block;
        margin-bottom: 20px;
        border-radius: 10px;
    }
}

#btns {
    display: flex;
    align-items: center;
    justify-content: center;

    .btn {
        padding: 5px;
        cursor: pointer;
        width: 40px;
        opacity: 0.95;

        img {
            width: 100%;
            height: 100%;
            border-radius: 7px;
        }
    }
}
</style>
