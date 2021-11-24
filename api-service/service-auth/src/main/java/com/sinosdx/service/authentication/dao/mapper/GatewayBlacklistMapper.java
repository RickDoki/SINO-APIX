package com.sinosdx.service.authentication.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sinosdx.service.authentication.dao.entity.GatewayBlacklist;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author wendy
 * @date 2020/12/7
 */
@Mapper
public interface GatewayBlacklistMapper extends BaseMapper<GatewayBlacklist> {

    List<GatewayBlacklist> queryGatewayBlacklist(@Param(value = "type") String type,
                                             @Param(value = "content") String content,
                                             @Param(value = "limit") Integer limit,
                                             @Param(value = "offset") Integer offset);

    List<Map<String, String>> queryGatewayBlacklistByTypeAndContent(@Param(value = "type") String type,
                                                 @Param(value = "content") String content);
}
