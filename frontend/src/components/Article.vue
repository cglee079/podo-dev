<template>
  <div id="wrap-articles">
    아티클 리스트
  </div>
</template>

<script>
export default {
  name: 'Article',
  methods: {
    /**
     * 게시글 페이징
     */
    loadArticle (page) {
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
  created () {
    this.loadArticle(0)
  }
}
</script>

<style scoped>
</style>
