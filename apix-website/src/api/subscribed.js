import request from '@/utils/request'

export function list(msg) {
  return request({
    url: '/app/subscribed/list?' + msg,
    method: 'get',
  })
}
export function detail(appCode) {
  return request({
    url: '/app/subscribed/' + appCode,
    method: 'get',
  })
}
export function Apidetail(apiId) {
  return request({
    url: '/app/api/' + apiId,
    method: 'get',
  })
}
