import request from '@/utils/request'

/*
2022 1 8
 */
// 服务列表
export function serveList(query) {
  return request({
    url: '/app/list?' + query,
    method: 'get',
  })
}
// 创建服务
export function createServe(data) {
  return request({
    url: '/app/create',
    method: 'post',
    data
  })
}
// 服务更新
export function serveupdate(appcode, data) {
  return request({
    url: '/app/' + appcode,
    method: 'put',
    data
  })
}
// 服务删除
export function serveDelete(appcode) {
  return request({
    url: '/app/' + appcode,
    method: 'delete',
  })
}
// 服务详情
export function serveDetail(appcode) {
  return request({
    url: '/app/' + appcode,
    method: 'get',
  })
}
// 服务详情-内部统计
export function appNum(appcode) {
  return request({
    url: '/app/data/' + appcode + '/appNum',
    method: 'post',
  })
}
// 添加新版本查询api
export function apiList(id) {
  return request({
    url: '/app/api/list?developerId=' + id,
    method: 'get',
  })
}
// 创建新版本
export function publish(appcode, data) {
  return request({
    url: '/app/' + appcode + '/publish',
    method: 'post',
    data
  })
}

/*
2022 1 10
*/
// 服务版本删除
export function delApiversion(query) {
  return request({
    url: '/app/appVersion/' + query,
    method: 'delete',
  })
}
// 我的订阅列表
export function Mysubscribed(query) {
  return request({
    url: '/app/subscribed/list?' + query,
    method: 'get',
  })
}
// 根据appcode 和 appversionId 查询API合集
export function queryApiList(data) {
  return request({
    url: '/app/api/queryApiList',
    method: 'post',
    data
  })
}