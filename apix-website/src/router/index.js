import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/* Router Modules */

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    noCache: true                if set true, the page will no be cached(default is false)
    affix: true                  if set true, the tag will affix in the tags-view
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const asyncRoutes = [
  {
    path: '/dashboard',
    component: Layout,
    redirect: '/dashboard/index',
    children: [
      {
        path: '/dashboard/index',
        component: () => import('@/views/dashboard/index'),
        name: 'Dashboard',
        meta: {title: '控制台', requiresAuth: true, icon: 'dashboard', Aicon: 'dashboard_Aicon', affix: true}
      }
    ]
  },
  // {
  //   path: '/openServe',
  //   children: [
  //     {
  //       path: '/openServe',
  //       name: 'openServe',
  //       meta: { title: '开放服务', requiresAuth: false, icon: 'openServe', Aicon: 'openServe_Aicon', affix: true }
  //     },
  //   ]
  // },
  {
    path: '/serve',
    component: Layout,
    alwaysShow: true,
    meta: {title: '服务管理', mbxClick: false, requiresAuth: true, icon: 'serve', Aicon: 'serve_Aicon'},
    children: [
      {
        path: '/serve/center',
        component: () => import('@/views/serve/serveCenter'),
        name: 'serveCenter',
        meta: {title: '我的服务', mbxClick: true, golist: true, requiresAuth: true},
        children: [
          {
            path: '/serve/create',
            component: () => import('@/views/serve/createServe'),
            name: 'create',
            hidden: true,
            meta: {title: '创建服务', requiresAuth: true}
          },
          {
            path: '/serve/serveDetail/:appcode',
            component: () => import('@/views/serve/serveDetail'),
            name: 'serveDteail',
            hidden: true,
            meta: {title: '服务详情', mbxClick: true, requiresAuth: true},
            children: [
              {
                path: '/serve/serveDetail/editionDetail',
                component: () => import('@/views/serve/editionDetail'),
                name: 'editionDetail',
                hidden: true,
                meta: {title: '版本详情', requiresAuth: true}
              },
              {
                path: '/serve/newEdition',
                component: () => import('@/views/serve/newEdition'),
                hidden: true,
                name: 'newEdition',
                meta: {title: '添加新版本', requiresAuth: true}
              },
              {
                path: '/serve/serveDetail/plug-in',
                component: () => import('@/views/serve/plug-in'),
                name: 'plug-in',
                hidden: true,
                meta: {title: '插件中心', requiresAuth: true}
              },
              {
                path: '/serve/serveDetail/pluginConfig/:plugincode',
                component: () => import('@/views/serve/plugInConfig'),
                name: 'plugInConfig',
                hidden: true,
                meta: {title: '插件配置', requiresAuth: true}
              }
            ]
          }
        ]
      },
      {
        path: '/serve/subscribe/',
        component: () => import('@/views/serve/subscribe'),
        name: 'subscribe',
        meta: {title: '我的订阅', mbxClick: true, golist: true, requiresAuth: true},
        children: [
          {
            path: '/serve/subscribeDetail/:appCode',
            component: () => import('@/views/serve/subscribeDetail'),
            name: 'subscribeDetail',
            hidden: true,
            meta: {title: '订阅服务详情', requiresAuth: true}
          }
        ]
      },
    ]
  },
  {
    path: '/api',
    component: Layout,
    alwaysShow: true,
    meta: {title: 'API管理', mbxClick: false, requiresAuth: true, icon: 'api', Aicon: 'api_Aicon'},
    children: [
      {
        path: '/api/list',
        component: () => import('@/views/api/list'),
        name: 'ApiList',
        meta: {title: 'API列表', mbxClick: true, golist: true, requiresAuth: true},
        children: [
          {
            path: '/api/add',
            component: () => import('@/views/api/add'),
            name: 'CreateApi',
            hidden: true,
            meta: {title: '创建API', requiresAuth: true}
          },
          {
            path: '/api/edit/:id',
            component: () => import('@/views/api/add'),
            hidden: true,
            name: 'EditApi',
            meta: { title: '编辑API', requiresAuth: true }
          },
          {
            path: '/api/detail/:id',
            component: () => import('@/views/api/detail'),
            name: 'ApiDetail',
            hidden: true,
            meta: {title: 'API详情', requiresAuth: true}
          },
        ]
      },
      {
        path: '/api/upstream',
        component: () => import('@/views/upstream/index'),
        name: 'UpstreamList',
        meta: {title: '上游管理', mbxClick: true, golist: true, requiresAuth: true},
        children: [
          {
            path: '/api/upstream/create',
            component: () => import('@/views/upstream/detail'),
            hidden: true,
            name: 'UpstreamCreate',
            meta: {title: '创建上游服务', requiresAuth: true}
          },
          {
            path: '/api/upstream/edit/:id',
            component: () => import('@/views/upstream/detail'),
            hidden: true,
            name: 'UpstreamEdit',
            meta: {title: '配置上游服务', requiresAuth: true}
          }
        ]
      }
    ]
  },
  {
    path: '/log',
    component: Layout,
    children: [
      {
        path: '/log/index',
        component: () => import('@/views/system/log/list'),
        name: 'Log',
        meta: {title: '审计日志', mbxClick: false, requiresAuth: true, icon: 'log', Aicon: 'log_Aicon', affix: true}
      }
    ]
  },
  {
    path: '/docsEdit',
    component: Layout,
    children: [
      {
        path: '/docsEdit/:type',
        hidden: true,
        component: () => import('@/views/system/docsEdit'),
        name: 'docsEdit',
        meta: {title: '文档编辑', requiresAuth: true, affix: true}
      },
    ]
  },
  {
    path: '/system',
    component: Layout,
    alwaysShow: true,
    mbxClick: false,
    meta: {title: '系统设置', mbxClick: false, requiresAuth: true, icon: 'system', Aicon: 'system_Aicon'},
    children: [
      {
        path: '/system/index',
        component: () => import('@/views/user/index'),
        name: 'user',
        meta: {title: '个人信息', requiresAuth: true, affix: true}
      },
      {
        path: '/system/config',
        component: () => import('@/views/system/config'),
        name: 'config',
        meta: {title: '开放门户配置', requiresAuth: true, affix: true}
      }
    ]
  },
  /** when your routing map is too long, you can split it into small modules **/

  // 404 page must be placed at the end !!!
  {path: '*', redirect: '/404', hidden: true}
]
export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/redirect/index')
      }
    ]
  },
  {
    path: '/openServe',
    hidden: false,
    component: () => import('@/views/openServe/index'),
    name: 'openServe',
    meta: {title: '开放服务', requiresAuth: false, affix: true}
  },
  {
    path: '/openServe/detail',
    component: () => import('@/views/openServe/detail'),
    name: 'openServeDetail',
    hidden: true,
    meta: {title: '开发服务详情', requiresAuth: false, affix: true}
  },
  {
    path: '/openServe/docxDetail',
    component: () => import('@/views/openServe/docxDetail'),
    name: 'openServedocxDetail',
    hidden: true,
    meta: {title: '文档详情', requiresAuth: false, affix: true}
  },
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/',
    component: () => import('@/views/openServe/index'),
    redirect: '/openServe',
    hidden: true
  },
  {
    path: '/auth-redirect',
    component: () => import('@/views/login/auth-redirect'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/error-page/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/error-page/401'),
    hidden: true
  },
  ...asyncRoutes
]

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({y: 0}),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
