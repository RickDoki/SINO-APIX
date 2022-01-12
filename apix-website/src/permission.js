import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getToken, removeToken, setToken } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'
import router from '@/router'
import { getUserInfo } from '@/api/user'
import Layout from '@/layout'
import { asyncRoutes, baseRoutes } from '@/router'


NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['/login', '/auth-redirect'] // no redirect whitelist
router.beforeEach(async (to, from, next) => {
  // next()
  //路由拦截器 是否需要登录
  if (to.meta.requiresAuth) {
    const token = getToken('token')
    if (!token) {
      console.log("需要登录");
      next('/login');
    } else {
      console.log("已登录");
      next()
    }
  } else {
    console.log("不需要登录");
    next()
  }
  NProgress.start()

  // set page title
  document.title = getPageTitle(to.meta.title)

  // determine whether the user has logged in
})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
