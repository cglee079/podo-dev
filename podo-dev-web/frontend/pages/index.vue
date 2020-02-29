<template>
    <div></div>
</template>

<script>
export default {
    created() {
        const query = this.$route.query;
        this.$router.replace({ name: "blogs", query: query });
    }
};
</script>
