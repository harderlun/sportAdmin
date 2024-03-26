package com.yu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2023-05-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysCompensate对象", description="")
public class SysCompensate implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "赔偿id")
    private Long id;

    @ApiModelProperty(value = "租借id")
    @TableField("borrowId")
    private Long borrowid;

    @ApiModelProperty(value = "赔偿原因")
    private String reason;

    @ApiModelProperty(value = "赔偿金额")
    private Long price;

    @ApiModelProperty(value = "赔偿状态，0-未支付，1-支付")
    private Integer status;

    @ApiModelProperty(value = "用户id")
    @TableField("userId")
    private Long userid;

    @ApiModelProperty(value = "创建时间")
    private Date created;


}
