<template>
    <blog-post />
</template>

<script>
import BlogPost from "../../../components/blog/BlogPost";

export default {
    middleware: ["authenticated"],
    components: {
        BlogPost
    }
};
</script>
