import request from '@/utils/request'

export function Apilogin(data) {
  return request({
    url: '/auth/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/auth/token/verify',
    method: 'post',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/vue-element-admin/user/logout',
    method: 'post'
  })
}
export function Apiregister(data) {
  return request({
    url: '/user/sys/register',
    method: 'post',
    data
  })
}
export function getList(data) {
  return request({
    url: '/user/sys/list' + data,
    method: 'get'
  })
}
export function developerId() {
  return request({
    url: '/omp/user-info',
    method: 'get'
  })
}

export function updateUser(id, data) {
  return request({
    url: '/user/sys/' + id,
    method: 'put',
    data
  })
}
