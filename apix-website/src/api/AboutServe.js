import request from '@/utils/request'

/*
2022 1 8
 */
// 服务列表
export function serveList (query) {
  return request({
    url: '/app/list?' + query,
    method: 'get',
  })
}
// 创建服务
export function createServe (data) {
  return request({
    url: '/app/create',
    method: 'post',
    data
  })
}
// 服务更新
export function serveupdate (appcode, data) {
  return request({
    url: '/app/' + appcode,
    method: 'put',
    data
  })
}
// 服务删除
export function serveDelete (appcode) {
  return request({
    url: '/app/' + appcode,
    method: 'delete',
  })
}
// 服务详情
export function serveDetail (appcode) {
  return request({
    url: '/app/' + appcode,
    method: 'get',
  })
}
// 服务详情-内部统计
export function appNum (appcode) {
  return request({
    url: '/app/data/' + appcode + '/appNum',
    method: 'post',
  })
}
// 添加新版本查询api
export function apiList (id) {
  return request({
    url: '/app/api/list?developerId=' + id,
    method: 'get',
  })
}
// 创建新版本
export function publish (appcode, data) {
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
export function delApiversion (query) {
  return request({
    url: '/app/appVersion/' + query,
    method: 'delete',
  })
}
// 我的订阅列表
export function Mysubscribed (query) {
  return request({
    url: '/app/subscribed/list?' + query,
    method: 'get',
  })
}
// 根据appcode 和 appversionId 查询API合集
export function queryApiList (data) {
  return request({
    url: '/app/api/queryApiList',
    method: 'post',
    data
  })
}
// app版本修改
export function changeAppversion (appvsersionId, data) {
  return request({
    url: '/app/appVersion/' + appvsersionId,
    method: 'post',
    data
  })
}
// 版本详情
export function versionDetail (appvsersionId) {
  return request({
    url: '/app/appVersion/' + appvsersionId,
    method: 'get',
  })
}
/*
2022 1 11
*/
// 请求日志
export function log (query) {
  return request({
    url: '/gateway/log?' + query,
    method: 'get',
  })
}
// 我的订阅服务详情
export function subscribed (query) {
  return request({
    url: '/app/subscribed/' + query,
    method: 'get',
  })
}
// 查询api详情
export function apiMessage (query) {
  return request({
    url: '/app/api/' + query,
    method: 'get',
  })
}
/*
2022 1 12
*/
// 添加插件
export function postPlugin (data) {
  return request({
    url: '/app/plugin',
    method: 'post',
    data
  })
}
// 获取随机值
export function randomKey () {
  return request({
    url: '/app/plugin/randomKey',
    method: 'get',
  })
}
// 保存限流规则
export function save (data) {
  return request({
    url: '/app/rateLimit/save',
    method: 'post',
    data
  })
}

/*
2022 1 13
*/
// 更新插件
export function putPlugin (data) {
  return request({
    url: '/app/plugin',
    method: 'put',
    data
  })
}
// 退订服务
export function gounSubscribe (appcode) {
  return request({
    url: '/app/unSubscribe/' + appcode,
    method: 'post',
  })
}
// 查询插件详情
export function getPlugin (id, code) {
  return request({
    url: '/app/plugin/' + id + '/' + code,
    method: 'get',
  })
}
export function getPluginDetail (id) {
  return request({
    url: '/app/plugin/detail?pluginId=' + id,
    method: 'get',
  })
}
// 限流启用
export function open (appId) {
  return request({
    url: '/app/rateLimit/open?appId=' + appId,
    method: 'post',
  })
}
// 限流停用
// 限流启用
export function close (appId) {
  return request({
    url: '/app/rateLimit/close?appId=' + appId,
    method: 'post',
  })
}