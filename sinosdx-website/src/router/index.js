import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)
const login = r => require.ensure([], () => r(require('@/views/login')), 'login')

const index = r => require.ensure([], () => r(require('@/views/containers')), 'index')

const dashboard = r => require.ensure([], () => r(require('@/views/dashboard')), 'dashboard')

const system = r => require.ensure([], () => r(require('@/views/system')), 'system')

export default new Router({
  routes: [
    {
      path: '/login',
      name: 'login',
      component: login
    },
    {
      path: '/index',
      component: index,
      children: [
        {
          path: '/index',
          name: 'index',
          component: dashboard
        },
        {
          path: '/system',
          name: 'system',
          component: system
        }
      ]
    },
    {
      path: '/',
      redirect: '/login'
    }
  ]
})
