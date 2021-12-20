package com.sinosdx.service.log.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sinosdx.common.model.log.entity.biz.BizLogDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author pengjiahu
 * @date 2020-09-17 12:16
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BizLog extends BizLogDTO {

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    protected Integer id;
}
