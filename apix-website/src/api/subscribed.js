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
export function Apilease(appLesseeCode,appLessorCode) {
  return request({
    url: '/app/remove/'+ appLesseeCode +'/lease/' + appLessorCode,
    method: 'delete',
  })
}
export function getApplessor(apiId) {
  return request({
    url: '/app/'+ apiId +'/lessee/list',
    method: 'get',
  })
}
export function getcurrent(developerId,appCode) {
  return request({
    url: '/app/subscribe/current/list?developerId=' +developerId + '&appCode=' + appCode,
    method: 'get',
  })
}