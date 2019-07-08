import Vue from 'vue'
import Router from 'vue-router'
import Article from '@/components/Article'
import ArticlePost from '@/components/ArticlePost'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    { path: '/', name: 'Article', component: Article },
    { path: '/articles/post', name: 'ArticlePost', component: ArticlePost },
  ]
})
