<template>
    <blog-list/>
</template>

<script>
    import BlogList from "@/components/blog/BlogList"

    export default {
        name: "index",
        head() {
            return {
                title: "podo-dev",
                meta: [
                    {hid:"og:description", property: 'og:description', content: "podo-dev, blogs"},
                ]
            }
        },

        components: {
            BlogList
        }
    }
</script>
