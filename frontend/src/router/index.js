import Vue from 'vue'
import Router from 'vue-router'
import Article from '@/components/Article'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Article',
      component: Article
    }
  ]
})
