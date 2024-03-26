import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import Element from 'element-ui'
import "element-ui/lib/theme-chalk/index.css"
import axios from "./axios";
import global from "./globalFun"

Vue.prototype.$axios = axios
Vue.config.productionTip = false
Vue.use(Element)
import globalWebSocket from './global'
Vue.prototype.$globalWebSocket = globalWebSocket
// require("./mock")

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
