package com.sinosdx.service.authentication.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 客户端申请token的密钥
 *
 * @author wendy
 * @date 2022/1/6
 */
@Data
@TableName("client_app_secret")
public class ClientAppSecret {

    @TableId
    private Integer id;
    private Integer clientId;
    private Integer userId;
    private String appCode;
    private String secretKey;
}
