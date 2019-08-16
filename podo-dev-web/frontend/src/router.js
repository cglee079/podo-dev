import Vue from 'vue'
import Router from 'vue-router'
import BlogView from '@/components/blog/BlogView'
import BlogList from '@/components/blog/BlogList'
import BlogPost from '@/components/blog/BlogPost'
import TagList from "@/components/tag/TagList";

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    { path: '/blogs/post', name: 'BlogPost', component: BlogPost },
    { path: '/blogs/:seq/post', name: 'BlogModify', component: BlogPost },
    { path: '/blogs/:seq', name: 'BlogView', component: BlogView },
    { path: '/', name: 'BlogList', component: BlogList },

    { path: '/tags', name: 'TagList', component: TagList }
  ]
})
