<template>
    <div id="container">
        <img id="logo" src="../assets/podo-dev.svg"  alt="logo"/>

        <nuxt-link :to="{ name: 'index' }">
            <div id="message">
                <span v-if="error.statusCode === 404">페이지를 찾을 수 없습니다 :(</span>
                <span v-else>알 수 없는 에러 :(</span>
            </div>
        </nuxt-link>
    </div>
</template>

<script>
export default {
    props: ["error"],
    layout: "default",

    mounted() {}
};
</script>

<style lang="scss" scoped>
#container {
    display: flex;
    flex-flow: column;
    justify-content: center;
    align-items: center;
    height: 70vh;

    #logo {
        width: 130px;
        opacity: 0.7;
    }

    #message {
        margin-top: 40px;

        span {
            font-size: 1.1rem;
            color: #333333;
            font-weight: bold;
            text-decoration: underline;
        }
    }
}
</style>
