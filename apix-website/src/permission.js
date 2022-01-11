// import router from './router'
// import store from './store'
// import { Message } from 'element-ui'
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
// const accessRoutes = store.dispatch('permission/generateRoutes', 0)
// router.addRoutes(accessRoutes)
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
  // router.addRoutes(routersList)
  // console.log(router.options.isAddDynamicMenuRoutes)
  // const userid = getToken('userId_api')

  // console.log(userid)
  // const routersList = []
  // const buttonList = []
  // if (to.path !== '/login') {
  //   if (!router.options.isAddDynamicMenuRoutes) {
  //     // console.log(asyncRoutes)
  //     // asyncRoutes = []
  //     getUserInfo(userid).then(res => {
  //       console.log(res.data.menuList)
  //       const item = res.data.menuList
  //       let createApi = false
  //       let createApp = false
  //       let ApiState = false
  //       let upstrem = false
  //       for (let index = 0; index < item.length; index++) {
  //         routersList.push({
  //           path: item[index].url,
  //           component: Layout,
  //           meta: { title: item[index].name, icon: item[index].icon },
  //           children: []
  //         })
  //         for (let index1 = 0; index1 < item[index].list.length; index1++) {
  //           if (item[index].list[index1].name === 'API发布') {
  //             createApi = true
  //           }
  //           if (item[index].list[index1].name === '数据统计') {
  //             ApiState = true
  //           }
  //           if (item[index].list[index1].name === '我的应用') {
  //             createApp = true
  //           }
  //           if (item[index].list[index1].name === '上游管理') {
  //             // upstrem = true
  //             for (let index2 = 0; index2 < item[index].list[index1].list.length; index2++) {
  //               if (item[index].list[index1].list[index2].name === '创建') {
  //                 upstrem = true
  //               }
  //             }
  //             // if(item[index].list[index1].list.length !==)
  //           }
  //           sessionStorage.setItem('createApi', createApi)
  //           sessionStorage.setItem('createApp', createApp)
  //           sessionStorage.setItem('ApiState', ApiState)
  //           sessionStorage.setItem('upstrem', upstrem)

  //           routersList[index].children.push({
  //             path: item[index].list[index1].pathUrl,
  //             component: () => import(item[index].list[index1].path),
  //             name: item[index].list[index1].pathName,
  //             meta: { title: item[index].list[index1].name, icon: item[index].list[index1].icon, affix: true },
  //             hidden: item[index].list[index1].drillDown
  //           })
  //           buttonList.push({
  //             name: item[index].list[index1].name,
  //             list: item[index].list[index1].list
  //           })
  //         }
  //       }
  //       console.log(buttonList)
  //       // setToken('buttonList',buttonList)
  //       sessionStorage.setItem('buttonList', JSON.stringify(buttonList))

  //       routersList.push({
  //         path: '/user',
  //         component: Layout,
  //         children: [
  //           {
  //             path: 'index',
  //             component: () => import('@/views/user/index'),
  //             name: 'user',
  //             meta: { title: '个人信息', icon: 'user' }
  //           }
  //         ]
  //       })
  //       // const menuList = res.data.menuList
  //       // menuList.forEach(item => {

  //       // });
  //       // console.log(asyncRoutes)
  //       // console.log(routersList)
  //       routersList.forEach(item => {
  //         asyncRoutes.push(item)
  //         // router.addRoutes(item)
  //       });
  //       // asyncRoutes.push(routersList)
  //       // asyncRoutes = routersList
  //       router.addRoutes(routersList)
  //       console.log(asyncRoutes)
  //       console.log(router.options.isAddDynamicMenuRoutes)
  //       router.options.isAddDynamicMenuRoutes = true
  //     })
  //   }
  // } else {
  //   router.options.isAddDynamicMenuRoutes = false
  //   console.log(asyncRoutes)
  //   // asyncRoutes = []
  //   // asyncRoutes.push({})
  //   asyncRoutes.splice(0, asyncRoutes.length)

  //   next()
  // }

  // start progress bar
  NProgress.start()

  // set page title
  document.title = getPageTitle(to.meta.title)

  // determine whether the user has logged in
  const hasToken = getToken('token')
  // if (to.path === '/login') {
  //   removeToken('Admin-Token')
  // }
  // if()
  console.log(to)
  if (to.path === '/login') {
    next()
    NProgress.done()
  } else {
    if (hasToken) {
      next()
      NProgress.done()
    } else {
      // next()
      router.push({
        path: '/',
      })
      NProgress.done()

      // next()
      // router.push({path:'/login'})
      // NProgress.done()
      /* has no token*/
      // console.log(process.env.VUE_APP_BASE_API)
      // console.log(process.env.marketing_url)
      // window.location = 'https://saic-aws.saicmotor.com/api-mgmt/#/login'
      // window.location = 'https://saic-aws.saicmotor.com/cspv2-web/#/login'
      // window.location = 'https://saic-aws.saicmotor.com/cspv2-web/#/login'
      // console.log(process.env.marketing_url)
    }
  }

})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
