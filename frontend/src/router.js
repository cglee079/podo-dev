import Vue from 'vue'
import Router from 'vue-router'
import Blog from '@/components/Blog'
import BlogList from '@/components/BlogList'
import BlogPost from '@/components/BlogPost'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    { path: '/blogs/:seq', name: 'Blog', component: Blog },
    { path: '/', name: 'BlogList', component: BlogList },
    { path: '/blogs/post', name: 'BlogPost', component: BlogPost },
  ]
})
