import axios from 'axios'
import { Message } from 'element-ui'
// import store from '@/store'
import { getToken } from '@/utils/auth'
import router from '@/router'
const curPath = window.location.host;
// var pathname = window.location.port;
const protocol = window.location.protocol
let baseURL = ''
if(process.env.NODE_ENV === 'development') {
   baseURL = ''
} else {
   baseURL = protocol + '//' + curPath + '/api'
}
// create an axios instance
const service = axios.create({
  baseURL: baseURL, // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 50000 // request timeout
})

// request interceptor
service.interceptors.request.use(
  config => {
    // do something before request is sent

    // if (store.getters.token) {
    //   // let each request carry token
    //   // ['X-Token'] is a custom headers key
    //   // please modify it according to the actual situation
    //   // config.headers['X-Token'] = getToken()
    //   config.headers['Authorization'] = 'Bearer ' + getToken()
    //   console.log('token')
    // }
    if (getToken('token')) {
      config.headers['Authorization'] = 'Bearer ' + getToken('token')
      // console.log(service.interceptors.request)
      // config.headers['JWT'] = getToken('FSH_AUTH')
    } else {

    }
    return config
  },
  error => {
    // do something with request error
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
  */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  response => {
    const res = response.data
    if (res.code !== 200) {
      if (res.code === 821022) {
        Message({
          message: '登录已过期,请重新登录',
          type: 'error',
          duration: 5 * 1000
        })
        router.replace({
          path: '/login',
        })
      } else if (res.code === 4003) {
        Message({
          message: '登录已过期,请重新登录',
          type: 'error',
          duration: 5 * 1000
        })
        router.replace({
          path: '/login',
        })
      } else {
        Message({
          message: res.msg,
          type: 'error',
          duration: 5 * 1000
        })
      }
    }

    // console.log(99999999999)
    // if the custom code is not 20000, it is judged as an error.
    // if (res.code !== 200) {
    //   Message({
    //     message: res.message || 'Error',
    //     type: 'error',
    //     duration: 5 * 1000
    //   })

    //   // 50008: Illegal token; 50012: Other clients logged in; 50014: Token expired;
    //   if (res.code === 50008 || res.code === 50012 || res.code === 50014) {
    //     // to re-login
    //     MessageBox.confirm('You have been logged out, you can cancel to stay on this page, or log in again', 'Confirm logout', {
    //       confirmButtonText: 'Re-Login',
    //       cancelButtonText: 'Cancel',
    //       type: 'warning'
    //     }).then(() => {
    //       store.dispatch('user/resetToken').then(() => {
    //         location.reload()
    //       })
    //     })
    //   }
    //   return Promise.reject(new Error(res.message || 'Error'))
    // } else {
    //   return res
    // }
    return res
  },
  error => {
    // const res = response.data
    // console.log(error.response)
    if (error.response.data.code === 821022) {
      Message({
        message: '登录已过期,请重新登录',
        type: 'error',
        duration: 5 * 1000
      })
      // window.location = 'https://saic-aws.saicmotor.com/cspv2-web/#/login'
      // window.location = 'https://www.sail-cloud.com/#/login'

      // window.location = process.env.marketing_url
      router.replace({
        path: '/login',
        // query: {
        //   redirect: router.currentRoute.fullPath
        // }
      })
    } else {
      Message({
        message: error.message,
        type: 'error',
        duration: 5 * 1000
      })
    }
    return Promise.reject(error)
  }
)

export default service
