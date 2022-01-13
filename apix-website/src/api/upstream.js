import request from '@/utils/request'

export function getUpstreamList(params) {
  return request({
    url: '/app/upstream/list' + params,
    method: 'get'
  })
}

export function addUpstream(data) {
  return request({
    url: '/app/upstream',
    method: 'post',
    data
  })
}

export function getUpstreamInfo(id) {
  return request({
    url: '/app/upstream/' + id,
    method: 'get'
  })
}

export function updateUpstream(id, data) {
  return request({
    url: '/app/upstream/' + id,
    method: 'put',
    data
  })
}

export function deleteUpstream(id) {
  return request({
    url: `/app/upstream/` + id,
    method: 'delete'
  })
}
