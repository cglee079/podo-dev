import Vue from 'vue'
import App from './App.vue'
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

Vue.use(Autocomplete)

Vue.config.productionTip = false
Vue.prototype.$axios = axios
Vue.prototype.$axios.defaults.baseURL='http://localhost:8090'

Vue.component('toast-custom-viewer', ToastCustomViewer)
Vue.component('sub-button', SubButton)

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
