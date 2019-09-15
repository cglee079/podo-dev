<template>
</template>

<script>
    export default {
        props: ['error'],
        layout: 'blog',

        mounted() {
            this.$toast.show("잘못된 페이지입니다")
            this.$router.push({name: 'index'})
        }
    }
</script>
