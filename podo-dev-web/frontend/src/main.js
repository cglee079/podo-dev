import Vue from 'vue'
import App from './App.vue'
import VueMq from 'vue-mq'
import router from './router'
import store from './store'
import axios from 'axios'

import VueScrollTo from 'vue-scrollto'
import Toasted from 'vue-toasted'
import Autocomplete from '@trevoreyre/autocomplete-vue'
import '@trevoreyre/autocomplete-vue/dist/style.css'

import 'tui-editor/dist/tui-editor.css'
import 'tui-editor/dist/tui-editor-contents.css'
import 'codemirror/lib/codemirror.css'
import 'highlight.js/styles/github.css'

import VueCookies from 'vue-cookies'
import ToastCustomViewer from "./components/global/ToastCustomViewer"
import SubButton from "./components/global/SubButton"
import Tooltip from 'vue-directive-tooltip';
import 'vue-directive-tooltip/dist/vueDirectiveTooltip.css';

Vue.use(Tooltip, {
    delay: 300,
    placement: 'right',
    triggers: ['hover'],
    offset: 0
})

VueCookies.config('7d')
Vue.use(VueCookies)

Vue.use(Autocomplete)
Vue.component('toast-custom-viewer', ToastCustomViewer)
Vue.component('sub-button', SubButton)

Vue.use(VueScrollTo, {
    container: "body",
    duration: 500,
    easing: "ease",
    offset: 0,
    force: true,
    cancelable: true,
    onStart: false,
    onDone: false,
    onCancel: false,
    x: false,
    y: true
})


Vue.use(Toasted, {
    theme: "toasted-primary",
    position: "top-center",
    duration: 2000
})

Vue.use(VueMq, {
    breakpoints: { // default breakpoints - customize this
        mobile: 720,
        tablet: 1080,
        desktop: Infinity,
    },
    defaultBreakpoint: '1080' // customize this for SSR
})

Vue.config.productionTip = false
Vue.prototype.$axios = axios
Vue.prototype.$axios.defaults.baseURL = store.getters.getServerUrl
Vue.prototype.$axios.interceptors.response.use(
    res => {
        return res
    },
    err => {
        if (err.response.status == 403) {
            Vue.prototype.$toasted.show("권한이 없습니다")
            status.actions.logout()
        }

        const response = err.response.data
        if (response.errors) {
            response.errors.forEach(err => Vue.prototype.$toasted.show(err))
        }

        return Promise.reject(err)
    }
)

/**
 * 라우터 인증 제어
 */
router.beforeEach((to, from, next) => {
    const isLogin = store.getters.isLogin

    // 권한 제어
    if (!isLogin) {
        if (to.name === 'BlogPost') {
            next({name: 'BlogList'})
            return
        }
    }

    next()
})

new Vue({
    router,
    store,
    render: h => h(App),
}).$mount('#app')
