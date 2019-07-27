import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import Autocomplete from '@trevoreyre/autocomplete-vue'
import '@trevoreyre/autocomplete-vue/dist/style.css'

import 'tui-editor/dist/tui-editor.css';
import 'tui-editor/dist/tui-editor-contents.css';
import 'codemirror/lib/codemirror.css';
import Editor from '@toast-ui/vue-editor/src/Editor.vue'

Vue.use(Autocomplete)

Vue.config.productionTip = false
Vue.prototype.$axios = axios

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
