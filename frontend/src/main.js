import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import CKEditor from '@ckeditor/ckeditor5-vue';
import Autocomplete from '@trevoreyre/autocomplete-vue'
import '@trevoreyre/autocomplete-vue/dist/style.css'

Vue.use( CKEditor )
Vue.use(Autocomplete)

Vue.config.productionTip = false
Vue.prototype.$axios = axios

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
