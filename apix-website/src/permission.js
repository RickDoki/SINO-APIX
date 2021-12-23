// import router from './router'
// import store from './store'
// import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getToken, removeToken } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'
import router from '@/router'


NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['/login', '/auth-redirect'] // no redirect whitelist
// const accessRoutes = store.dispatch('permission/generateRoutes', 0)
// router.addRoutes(accessRoutes)
router.beforeEach(async(to, from, next) => {
  // start progress bar
  NProgress.start()

  // set page title
  document.title = getPageTitle(to.meta.title)

  // determine whether the user has logged in
  const hasToken = getToken('FSH_AUTH_api')
  // if (to.path === '/login') {
  //   removeToken('Admin-Token')
  // }
  if (hasToken) {
    next()
    NProgress.done()
  } else {
    next()
    // router.replace({
    //   path: '/login',
    //   // query: {
    //   //   redirect: router.currentRoute.fullPath
    //   // }
    // })
    // router.push({path:'/login'})
    NProgress.done()
    /* has no token*/
    // console.log(process.env.VUE_APP_BASE_API)
    // console.log(process.env.marketing_url)
    // window.location = 'https://saic-aws.saicmotor.com/cspv2-web/#/login'
    // window.location = 'https://saic-aws.saicmotor.com/cspv2-web/#/login'
    // window.location = 'https://saic-aws.saicmotor.com/cspv2-web/#/login'
    // console.log(process.env.marketing_url)
  }
})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
