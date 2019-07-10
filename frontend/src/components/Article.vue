<template>
    <div id="wrapArticles">
        <div id="articleTree">
            <div>dfdfd</div>
            <div>dfdfd</div>
            <div>dfdfd</div>
            <div>dfdfd</div>
        </div>
        <div id="articles">
            <ArticleRow :article=article></ArticleRow>
            <ArticleRow :article=article></ArticleRow>
            <ArticleRow :article=article></ArticleRow>
            <ArticleRow :article=article></ArticleRow>
            <ArticleRow :article=article></ArticleRow>
            <ArticleRow :article=article></ArticleRow>
            <ArticleRow :article=article></ArticleRow>
            <ArticleRow :article=article></ArticleRow>
            <ArticleRow :article=article></ArticleRow>
            <ArticleRow :article=article></ArticleRow>
            <ArticleRow :article=article></ArticleRow>
            <ArticleRow :article=article></ArticleRow>
            <ArticleRow :article=article></ArticleRow>
            <ArticleRow :article=article></ArticleRow>
            <ArticleRow :article=article></ArticleRow>
            <ArticleRow :article=article></ArticleRow>
        </div>
    </div>
</template>

<script>
    import ArticleRow from '@/components/ArticleRow'

    export default {
        components: {
            ArticleRow
        },
        name: 'Article',
        methods: {
            /**
             * 게시글 페이징
             */
            loadArticle(page) {
                this.$axios
                    .get('http://localhost:8443/api/articles', {
                        params: {
                            'page': page
                        }
                    })
                    .then(res => {
                        console.log(res)
                    })
                    .catch(err => {
                        console.log(err)
                    })
            }


        },
        created() {
            this.loadArticle(0)
        }
    }
</script>

<style scoped>
    #wrapArticles {
        display: flex;
    }

    #articleTree {
        width: 20%;
    }

    #articles{
        flex:1;
    }
</style>
