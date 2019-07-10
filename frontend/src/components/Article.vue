<template>
    <div id="wrapArticles">
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
        <div id="articleTags">
            <div>#Spring</div>
            <div>#VueJS</div>
            <div>#JPA</div>
            <div>#이론</div>
            <div>#Spring</div>
            <div>#VueJS</div>
            <div>#JPA</div>
            <div>#이론</div>
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

    #articleTags {
        position: sticky;
        margin-left: 50px;
        width: 15%;
    }

    #articleTags div{
        display: inline-block;
        margin: 5px 10px;
        cursor: pointer;
        opacity: 0.8;
    }

    #articleTags div:hover{
        opacity: 1;
    }

    #articles{
        flex:1;
    }
</style>
