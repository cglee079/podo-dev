<template>
    <div id="wrapLogin">
        <div>
            <div id="desc" @click="login">
                "podo는 입에 잘 붙습니다"
            </div>

            <div id="btns">
                <div class="btn" @click="login('google')">
                    <img src="../../assets/btns/oauth/google-icon.png" alt="google_login" />
                </div>

                <div class="btn" @click="login('facebook')">
                    <img src="../../assets/btns/oauth/facebook-icon.png" alt="facebook_login" />
                </div>

                <div class="btn" @click="login('naver')">
                    <img src="../../assets/btns/oauth/naver-icon-green2.jpg" alt="naver_login" />
                </div>

                <div class="btn" @click="login('kakao')">
                    <img src="../../assets/btns/oauth/kakao-icon.svg" alt="kakao_login" />
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
}

#btns {
    display: flex;
    align-items: center;
    justify-content: center;

    .btn {
        margin: 5px;
        cursor: pointer;
        width: 45px;

        img {
            width: 100%;
            height: 100%;
            border-radius: 2px;
        }
    }
}
</style>
