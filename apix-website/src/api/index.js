import request from '@/until/request.js'

export function apilogin (data) {
  return request({
    url: '/auth/login',
    method: 'post',
    data
  })
}
