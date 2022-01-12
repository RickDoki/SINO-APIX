import request from '@/utils/request'

export function list(data) {
  return request({
    url: '/app/list' + data,
    method: 'get'
  })
}

export function create(data) {
  return request({
    url: '/app/create',
    method: 'post',
    data
  })
}

export function appDelete(id) {
  return request({
    url: '/app/' + id,
    method: 'delete'
  })
}

export function appEdit(id, data) {
  return request({
    url: '/app/' + id,
    method: 'put',
    data
  })
}

export function published(id, data) {
  return request({
    url: '/app/' + id,
    method: 'put',
    data
  })
}

export function detail(appCode, developerId) {
  return request({
    url: '/app/' + appCode + '?developerId=' + developerId,
    method: 'get'
  })
}

export function apiList(data) {
  return request({
    url: '/app/api/list?' + data,
    method: 'get'
    // data
  })
}

export function publish(appCode, data) {
  return request({
    url: '/app/' + appCode + '/publish',
    method: 'post',
    data
  })
}

export function addUser(appCode, data) {
  return request({
    url: '/app/' + appCode + '/developer/add',
    method: 'post',
    data
  })
}

export function userList(appCode) {
  return request({
    url: '/app/' + appCode + '/developer/list',
    method: 'get'
  })
}

export function userDeleted(appCode, userid) {
  return request({
    url: '/app/' + appCode + '/developer/delete/' + userid,
    method: 'delete'
  })
}

export function AppLease(appLesseeCode, appLessorCode, data) {
  return request({
    url: '/app/' + appLesseeCode + '/lease/' + appLessorCode,
    method: 'post',
    data
  })
}

export function getOption() {
  return request({
    url: '/app/product/list',
    method: 'get',
  })
}

export function getStatistics(data) {
  return request({
    url: '/app/data/statistics',
    method: 'post',
    data
  })
}

export function listSubscribe(msg) {
  return request({
    url: '/app/subscribed/list' + msg,
    method: 'get',
  })
}

export function Apilistnums(data) {
  return request({
    url: '/app/api/list' + data,
    method: 'get'
    // data
  })
}

// 开放服务列表
export function openList(data) {
  return request({
    url: '/app/open/list' + data,
    method: 'get'
    // data
  })
}

// 开放服务详情
export function appCodeDetail(appCode) {
  return request({
    url: `/app/open/${appCode}`,
    method: 'get'
    // data
  })
}

// 订阅开放服务
export function subscribe(appSubscribedCode) {
  return request({
    url: `/app/open/subscribe/${appSubscribedCode}`,
    method: 'get'
    // data
  })
}
