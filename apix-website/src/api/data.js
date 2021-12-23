import request from '@/utils/request'
export function getList(data) {
  return request({
    url: '/gateway/log' + data,
    method: 'get'
  })
}
export function getLogList(params) {
  return request({
    url: '/log/audit/list' + params,
    method: 'get'
  })
}

