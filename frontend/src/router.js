import Vue from 'vue'
import Router from 'vue-router'
import BlogView from '@/components/BlogView'
import BlogList from '@/components/BlogList'
import BlogPost from '@/components/BlogPost'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    { path: '/blogs/:seq', name: 'BlogView', component: BlogView },
    { path: '/', name: 'BlogList', component: BlogList },
    { path: '/blogs/post', name: 'BlogPost', component: BlogPost },
  ]
})
