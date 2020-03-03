<template>
    <span>
        <img id="podo" v-if="writer.isAdmin" src="../../assets/icons/oauth/podo.jpg" alt="podo" />

        <img
            id="google"
            v-else-if="writer.oauthType === 'GOOGLE'"
            src="../../assets/icons/oauth/google.svg"
            alt="google"
        />

        <img
            id="facebook"
            v-else-if="writer.oauthType === 'FACEBOOK'"
            src="../../assets/icons/oauth/facebook.svg"
            alt="facebook"
        />

        <img
            id="kakao"
            v-else-if="writer.oauthType === 'KAKAO'"
            src="../../assets/icons/oauth/kakao.svg"
            alt="kakao"
        />

        <img
            id="naver"
            v-else-if="writer.oauthType === 'NAVER'"
            src="../../assets/icons/oauth/naver.svg"
            alt="naver"
        />

        <img
            id="github"
            v-else-if="writer.oauthType === 'GITHUB'"
            src="../../assets/icons/oauth/github.svg"
            alt="naver"
        />
    </span>
</template>

<script>
export default {
    name: "CommentWriterIcon",
    props: {
        writer: Object
    }
};
</script>

<style lang="scss" scoped>
#podo {
    width: 16px;
    height: 16px;
    margin-bottom: -1px;
    border-radius: 14px;
    opacity: 0.7;
}

#google {
    width: 16px;
    height: 16px;
    margin-bottom: -1px;
    border-radius: 14px;
    opacity: 0.7;
}

#facebook {
    width: 16px;
    height: 16px;
    margin-bottom: -1px;
    border-radius: 14px;
    opacity: 0.9;
}

#kakao {
    width: 17px;
    height: 17px;
    margin-bottom: -1px;
    border-radius: 15px;
}

#naver {
    width: 16px;
    height: 16px;
    margin-bottom: -1px;
    border-radius: 15px;
    opacity: 0.9;
}

#github {
    width: 15px;
    height: 15px;
    margin-bottom: -1px;
    opacity: 0.9;

}
</style>
