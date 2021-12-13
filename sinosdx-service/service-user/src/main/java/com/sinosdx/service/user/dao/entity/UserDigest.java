package com.sinosdx.service.user.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * @author wendy
 * @date 2021/6/15
 */
@Data
@Builder
@TableName("user_digest")
public class UserDigest {

    @TableId(type = IdType.INPUT)
    private String digestKey;

    private String data;

    private String mobile;
}
