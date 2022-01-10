import request from '@/utils/request'
export function create (data) {
  return request({
    url: '/app/api/create',
    method: 'post',
    data
  })
}
export function list (data) {
  return request({
    url: '/app/api/list?' + data,
    method: 'get'
    // data
  })
}
export function detail (id) {
  return request({
    url: '/app/api/' + id,
    method: 'get'
    // data
  })
}
export function listDelete (id) {
  return request({
    url: '/app/api/' + id,
    method: 'delete'
    // data
  })
}
export function edit (id, data) {
  return request({
    url: '/app/api/' + id,
    method: 'put',
    data
  })
}
export function NewVersion (data) {
  return request({
    url: '/app/api/create/version',
    method: 'post',
    data
  })
}
export function editApiVersion (id, data) {
  return request({
    url: '/app/api/version/' + id,
    method: 'PUT',
    data
  })
}
export function apidetail (id) {
  return request({
    url: '/app/api/template/' + id,
    method: 'get',
  })
}
