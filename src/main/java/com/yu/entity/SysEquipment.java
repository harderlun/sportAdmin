package com.yu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author testjava
 * @since 2023-05-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysEquipment对象", description="")
public class SysEquipment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "器材id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "器材名称")
    private String name;

    @ApiModelProperty(value = "器材类别")
    private String classification;

    @ApiModelProperty(value = "器材剩余数量")
    private Long surplus;

    @ApiModelProperty(value = "器材总数")
    private Long total;

    @ApiModelProperty(value = "器材金额")
    private Integer money;

    @ApiModelProperty(value = "0-可租用，1-不可租用")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date created;


}
