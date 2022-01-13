import request from '@/utils/request'

// 获取门户配置
export function getDoorConfig () {
  return request({
    url: '/app/door',
    method: 'get'
  })
}

// 修改门户配置
export function updateDoorConfig (data) {
  return request({
    url: '/app/door',
    method: 'put',
    data
  })
}

export function Apilogin (data) {
  return request({
    url: '/auth/login',
    method: 'post',
    data
  })
}

export function getInfo (token) {
  return request({
    url: '/auth/token/verify',
    method: 'post',
    params: { token }
  })
}

export function logout () {
  return request({
    url: '/vue-element-admin/user/logout',
    method: 'post'
  })
}
export function Apiregister (data) {
  return request({
    url: '/user/sys/register',
    method: 'post',
    data
  })
}
export function getList (data) {
  return request({
    url: '/user/sys/list' + data,
    method: 'get'
  })
}
export function developerId () {
  return request({
    url: '/omp/user-info',
    method: 'get'
  })
}
export function updateUser (id, data) {
  return request({
    url: '/user/sys/' + id,
    method: 'put',
    data
  })
}
export function getUserInfo (id) {
  return request({
    url: '/user/sys/menu/nav/' + id,
    method: 'get',
  })
}
export function getuser (mobile) {
  return request({
    url: '/user/sys/login-info?mobile=' + mobile,
    method: 'get',
  })
}
export function updateorg (id, data) {
  return request({
    url: '/user/sys/org/' + id,
    method: 'put',
    data
  })
}
export function changePass (data) {
  return request({
    url: '/user/sys/modify/pwd',
    method: 'put',
    data
  })
}
export function orgList (data) {
  return request({
    url: '/user/sys/org/list' + data,
    method: 'get'
  })
}
export function neworg (id, data) {
  return request({
    url: '/user/sys/org/' + id,
    method: 'put',
    data
  })
}
export function orgAddUser (data) {
  return request({
    url: '/user/sys/add',
    method: 'post',
    data
  })
}