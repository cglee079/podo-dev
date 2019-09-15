<template>
    <blog-list/>
</template>

<script>
    import BlogList from "@/components/blog/BlogList"

    export default {
        name: "index",
        head() {
            return {
                meta: [
                    {property: 'og:title', content: 'podo-dev'},
                    {property: 'og:description', content: "podo-dev, blogs"},
                    {property: 'og:image', content: "/og-image.png"},
                ]
            }
        },

        components: {
            BlogList
        }
    }
</script>
