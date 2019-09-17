<template>
    <blog-post
        :seq="seq"
    />
</template>

<script>
    import BlogPost from "@/components/blog/BlogPost"

    export default {
        name: "post",
        components:{
            BlogPost
        },
        data(){
            return {
                seq : null,
            }
        },
        created() {
            this.seq = parseInt(this.$route.params.seq)
        },
    }
</script>

