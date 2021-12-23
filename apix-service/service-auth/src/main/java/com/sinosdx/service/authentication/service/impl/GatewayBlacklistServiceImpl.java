package com.sinosdx.service.authentication.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sinosdx.service.authentication.constants.Constants;
import com.sinosdx.service.authentication.dao.entity.GatewayBlacklist;
import com.sinosdx.service.authentication.dao.mapper.GatewayBlacklistMapper;
import com.sinosdx.service.authentication.result.R;
import com.sinosdx.service.authentication.result.enums.ResultCodeEnum;
import com.sinosdx.service.authentication.service.GatewayBlacklistService;
import com.sinosdx.service.authentication.utils.ThreadContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Pattern;

/**
 * @author wendy
 * @date 2021/1/5
 */
@Service
public class GatewayBlacklistServiceImpl implements GatewayBlacklistService {

    @Resource
    private GatewayBlacklistMapper gatewayBlacklistMapper;

    /**
     * 创建黑名单
     *
     * @param gatewayBlacklist
     * @return
     */
    @Override
    public R<GatewayBlacklist> createGatewayBlacklist(GatewayBlacklist gatewayBlacklist) {
        if (StringUtils.isBlank(gatewayBlacklist.getType()) || StringUtils.isBlank(gatewayBlacklist.getContent())) {
            return R.fail(ResultCodeEnum.PARAM_NOT_COMPLETE);
        }
        if (gatewayBlacklist.getType().equals(Constants.BLACKLIST_TYPE_IP) && !Pattern.matches(Constants.IP_REGEX, gatewayBlacklist.getContent())) {
            return R.fail(ResultCodeEnum.BLACKLIST_CONTENT_IS_NOT_IP);
        }

        Long count = gatewayBlacklistMapper.selectCount(new QueryWrapper<GatewayBlacklist>()
                .eq("type", gatewayBlacklist.getType())
                .eq("content", gatewayBlacklist.getContent())
                .eq("del_flag", 0));
        if (count > 0) {
            return R.fail(ResultCodeEnum.BLACKLIST_IS_EXIST);
        }
        gatewayBlacklist.setCreationDate(LocalDateTime.now(TimeZone.getTimeZone("Asia/Shanghai").toZoneId()));
        gatewayBlacklist.setCreationBy(ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID));
        gatewayBlacklistMapper.insert(gatewayBlacklist);
        return R.success(gatewayBlacklist);
    }

    /**
     * 修改黑名单
     *
     * @param id
     * @param gatewayBlacklist
     * @return
     */
    @Override
    public R<GatewayBlacklist> updateGatewayBlacklist(Integer id, GatewayBlacklist gatewayBlacklist) {
        GatewayBlacklist oldBlacklist = gatewayBlacklistMapper.selectById(id);
        if (null == oldBlacklist) {
            return R.fail(ResultCodeEnum.BLACKLIST_IS_NOT_EXIST);
        }

        if (StringUtils.isNotBlank(gatewayBlacklist.getType())) {
            oldBlacklist.setType(gatewayBlacklist.getType());
        }
        if (StringUtils.isNotBlank(gatewayBlacklist.getContent())) {
            oldBlacklist.setContent(gatewayBlacklist.getContent());
        }

        if (oldBlacklist.getType().equals(Constants.BLACKLIST_TYPE_IP) && !Pattern.matches(Constants.IP_REGEX, oldBlacklist.getContent())) {
            return R.fail(ResultCodeEnum.BLACKLIST_CONTENT_IS_NOT_IP);
        }

        oldBlacklist.setLastUpdateDate(LocalDateTime.now(TimeZone.getTimeZone("Asia/Shanghai").toZoneId()));
        oldBlacklist.setLastUpdatedBy(ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID));
        gatewayBlacklistMapper.updateById(oldBlacklist);
        return R.success(oldBlacklist);
    }

    /**
     * 删除黑名单
     *
     * @param id
     * @return
     */
    @Override
    public R<Object> deleteGatewayBlacklist(Integer id) {
        GatewayBlacklist oldBlacklist = gatewayBlacklistMapper.selectById(id);
        if (null == oldBlacklist) {
            return R.fail(ResultCodeEnum.BLACKLIST_IS_NOT_EXIST);
        }
        gatewayBlacklistMapper.deleteById(id);
        return R.success();
    }

    /**
     * 查询黑名单列表
     *
     * @param type
     * @param content
     * @param limit
     * @param offset
     * @return
     */
    @Override
    public R<Map<String, Object>> queryGatewayBlacklist(String type, String content, Integer limit, Integer offset) {
        List<GatewayBlacklist> blacklist = gatewayBlacklistMapper.queryGatewayBlacklist(
                type, content, limit, offset == null || limit == null ? null : limit * (offset - 1));
        Map<String, Object> blacklistMap = new HashMap<>();
        blacklistMap.put("blacklist", blacklist);
        List<GatewayBlacklist> apiVos = gatewayBlacklistMapper.queryGatewayBlacklist(
                type, content, null, null);
        blacklistMap.put("total", apiVos.size());
        return R.success(blacklistMap);
    }

    /**
     * 查询黑名单
     *
     * @param type
     * @param content
     * @return
     */
    @Override
    public R<Map<String, String>> queryGatewayBlacklist(String type, String content) {
        List<Map<String, String>> mapList = gatewayBlacklistMapper.queryGatewayBlacklistByTypeAndContent(type, content);
        if (mapList.isEmpty()) {
            return R.fail(ResultCodeEnum.BLACKLIST_IS_NOT_EXIST);
        }
        return R.success(mapList.get(0));
    }
}
