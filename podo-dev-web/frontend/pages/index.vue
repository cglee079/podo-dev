<template>
    <div></div>
</template>

<script>
export default {
    created() {
        const params = this.$route.params;
        const query = this.$route.query;
        this.$router.push({ name: "blogs", params: params, query: query });
    }
};
</script>
