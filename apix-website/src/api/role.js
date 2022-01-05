import request from '@/utils/request'

export function getRoutes() {
  return request({
    url: '/user/sys/menu/list',
    method: 'get'
  })
}

export function getRoles() {
  return request({
    url: '/user/sys/role/select',
    method: 'get'
  })
}

export function getRoleList(params) {
  return request({
    url: '/user/sys/role/list' + params,
    method: 'get'
  })
}

export function addRole(data) {
  return request({
    url: '/user/sys/role/save',
    method: 'post',
    data
  })
}

export function getRoleInfo(id) {
  return request({
    url: '/user/sys/role/info/' + id,
    method: 'get'
  })
}

export function updateRole(data) {
  return request({
    url: '/user/sys/role/update',
    method: 'post',
    data
  })
}

export function deleteRole(data) {
  return request({
    url: `/user/sys/role/delete`,
    method: 'post',
    data
  })
}

export function getroleNav(id) {
  return request({
    url: `user/sys/menu/permList/` + id,
    method: 'get'
  })
}