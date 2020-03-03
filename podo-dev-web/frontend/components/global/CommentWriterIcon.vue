<template>
    <span>
        <img
            id="google"
            v-if="oauthType === 'GOOGLE'"
            src="../../assets/icons/oauth/google.png"
            alt="google"
        />

        <img
            id="facebook"
            v-if="oauthType === 'FACEBOOK'"
            src="../../assets/icons/oauth/facebook.svg"
            alt="facebook"
        />

        <img
            id="kakao"
            v-if="oauthType === 'KAKAO'"
            src="../../assets/icons/oauth/kakao.svg"
            alt="kakao"
        />

        <img
            id="naver"
            v-if="oauthType === 'NAVER'"
            src="../../assets/icons/oauth/naver.png"
            alt="naver"
        />
    </span>
</template>

<script>
export default {
    name: "CommentWriterIcon",
    props: {
        oauthType: String
    }
};
</script>

<style lang="scss" scoped>
#google {
    width: 14px;
    height: 14px;
    border-radius: 14px;
}

#facebook {
    width: 14px;
    height: 14px;
    border-radius: 14px;
}

#kakao {
    width: 15px;
    height: 15px;
    border-radius: 15px;
}

#naver {
    width: 15px;
    height: 15px;
    border-radius: 15px;
}

</style>
