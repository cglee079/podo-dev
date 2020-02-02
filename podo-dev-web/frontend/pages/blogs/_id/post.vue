<template>
    <blog-post :id="id" />
</template>

<script>
import BlogPost from "../../../components/blog/BlogPost";

export default {
    name: "post",
    middleware: ["authenticated"],
    components: {
        BlogPost
    },
    data() {
        return {
            id: null
        };
    },
    created() {
        this.id = parseInt(this.$route.params.id);
    }
};
</script>
