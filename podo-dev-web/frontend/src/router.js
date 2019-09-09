import Vue from 'vue'
import Router from 'vue-router'
import BlogView from './components/blog/BlogView'
import BlogList from './components/blog/BlogList'
import BlogPost from './components/blog/BlogPost'
import TagList from "./components/tag/TagList";
import Resume from "./components/resume/Resume";

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    { path: '/resume', name: 'Resume', component: Resume },

    { path: '/blogs/post', name: 'BlogPost', component: BlogPost },
    { path: '/blogs/:seq/post', name: 'BlogModify', component: BlogPost },
    { path: '/blogs/:seq', name: 'BlogView', component: BlogView },
    { path: '/', name: 'BlogList', component: BlogList },

    { path: '/tags', name: 'TagList', component: TagList }
  ],
  scrollBehavior() {
    return {x: 0, y: 0}
  }
})
