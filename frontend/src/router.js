import Vue from 'vue'
import Router from 'vue-router'
import Article from '@/components/Article'
import ArticleList from '@/components/ArticleList'
import ArticlePost from '@/components/ArticlePost'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    { path: '/articles/:seq', name: 'Article', component: Article },
    { path: '/', name: 'ArticleList', component: ArticleList },
    { path: '/articles/post', name: 'ArticlePost', component: ArticlePost },
  ]
})
