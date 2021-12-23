import axios from 'axios'

// import { baseURL } from 'config'
const service = axios.create({
  baseURL: process.env.baseURL,
  withCredentials: true, // send cookies when cross-domain requests
  timeout: 60000, // request timeout
  headers: {
    'content-type': 'application/json; charset=UTF-8'
  }
})

console.log(process.env)
// request 拦截器
service.interceptors.request.use(
  config => {
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// respone拦截器
service.interceptors.response.use(
  response => {
    return Promise.resolve(response.data)
  },
  error => {
    return Promise.reject(error)
  }
)

export default service
