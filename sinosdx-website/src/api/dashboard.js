import request from '@/utils/request'

export function dashboard() {
  return request({
    url: '/app/dashboard',
    method: 'get',
  })
}