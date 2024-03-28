package com.sanshao.entity;

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
 * @since 2023-05-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UserBorrow对象", description="")
public class UserBorrow implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "租用订单编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    @TableField("userId")
    private Long userid;

    @ApiModelProperty(value = "器材id")
    @TableField("equipmentId")
    private Long equipmentid;

    @ApiModelProperty(value = "数量")
    private Integer number;

    @ApiModelProperty(value = "总租用金额")
    @TableField("totalMoney")
    private Long totalmoney;

    @ApiModelProperty(value = "0-待审批，1-通过，2-驳回")
    private Integer status;

    @ApiModelProperty(value = "租用开始时间")
    @TableField("startTime")
    private Date starttime;

    @ApiModelProperty(value = "租用结束时间")
    @TableField("endTime")
    private Date endtime;

    @ApiModelProperty(value = "创建时间")
    private Date created;

    @TableField(exist = false)
    private String username;

    @TableField(exist = false)
    private String equipmentName;

    @TableField(exist = false)
    private Integer repairsStatus;
}
