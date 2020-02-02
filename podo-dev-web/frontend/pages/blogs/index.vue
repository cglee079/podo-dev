<template>
    <section>
        <blog-list />
    </section>
</template>

<script>
import BlogList from "../../components/blog/BlogList";

export default {
    name: "Index",
    head() {
        return {
            title: "podo-dev",
            meta: [
                { hid: "og:description", property: "og:description", content: "podo-dev, blogs" }
            ],
            link: [{ rel: "canonical", href: process.env.STATIC_URL }]
        };
    },
    components: {
        BlogList
    }
};
</script>
