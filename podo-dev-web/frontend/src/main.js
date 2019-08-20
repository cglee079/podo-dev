import Vue from 'vue'
import App from './App.vue'
import VueMq from 'vue-mq'
import router from './router'
import axios from 'axios'

import '@trevoreyre/autocomplete-vue/dist/style.css'
import 'tui-editor/dist/tui-editor.css';
import 'tui-editor/dist/tui-editor-contents.css';
import 'codemirror/lib/codemirror.css';
import 'highlight.js/styles/github.css';

import Autocomplete from '@trevoreyre/autocomplete-vue'
import ToastCustomViewer from "@/components/global/ToastCustomViewer";
import SubButton from "./components/global/SubButton";

import Toasted from 'vue-toasted';


Vue.use(Toasted, {
  theme: "toasted-primary",
  position: "top-center",
  duration: 3000
})

Vue.use(Autocomplete)

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
Vue.prototype.$axios.defaults.baseURL='http://localhost:8090'
Vue.prototype.$scrollToTop = () => window.scrollTo(0,0)

Vue.component('toast-custom-viewer', ToastCustomViewer)
Vue.component('sub-button', SubButton)

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
