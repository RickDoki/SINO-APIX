import request from '@/utils/request'
export function templateList(name) {
  return request({
    url: '/app/api/template/list' + name,
    method: 'get'
  })
}