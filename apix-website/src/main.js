import Vue from 'vue'

import Cookies from 'js-cookie'

import 'normalize.css/normalize.css' // a modern alternative to CSS resets

import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import './styles/element-variables.scss'

import '@/styles/index.scss' // global css
import '../theme/index.css'

import App from './App'
import store from './store'
import router from './router'
import 'xe-utils'
import VXETable from 'vxe-table'
// import 'vxe-table/lib/style.css'
import './styles/vxe-table.scss'
import './icons' // icon
import './permission' // permission control
import './utils/error-log' // error log
import VueContextMenu from 'vue-contextmenu'
import * as filters from './filters' // global filters
import VueClipboard from 'vue-clipboard2'
import Moment from 'moment'
import echarts from 'echarts'
Vue.prototype.$echarts = echarts
Vue.use(VXETable)
// VXETable.setup({
//   headerCellStyle: { backgroundColor: '#fff', color: '#494E6A' },
//   border: "none",
//   rowConfig: { isCurrent: true, isHover: true },
//   editConfig: { trigger: 'click', mode: 'cell', showIcon: false }
// })
Vue.use(VueContextMenu)
Vue.use(VueClipboard)
Vue.filter('formatDate', function (value) {
  return Moment(value).format('YYYY-MM-DD HH:mm:ss')
})
/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online ! ! !
 */
if (process.env.NODE_ENV === 'production') {
  const { mockXHR } = require('../mock')
  mockXHR()
}

Vue.use(Element, {
  size: Cookies.get('size') || 'medium', // set element-ui default size
  // locale: enLang // 如果使用中文，无需设置，请删除
})

// register global utility filters
Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key])
})

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
