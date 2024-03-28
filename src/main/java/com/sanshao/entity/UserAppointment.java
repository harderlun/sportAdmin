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

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UserAppointment对象", description="")
public class UserAppointment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "预约订单编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "预约用户编号")
    @TableField("userId")
    private Long userid;

    @ApiModelProperty(value = "场地编号")
    @TableField("placeId")
    private Long placeid;

    @ApiModelProperty(value = "预约日期")
    @TableField("orderDate")
    private Date orderdate;

    @ApiModelProperty(value = "预约开始时间")
    @TableField("orderStart")
    private Integer orderstart;

    @ApiModelProperty(value = "预约结束时间")
    @TableField("orderEnd")
    private Integer orderend;

    @ApiModelProperty(value = "预约状态，0-已预约，1-取消预约")
    private Integer status;

    @ApiModelProperty(value = "预约订单价格")
    private Long price;

    @ApiModelProperty(value = "预约订单生成时间")
    private Date created;

    @TableField(exist = false)
    private Long placeStateId;

    @TableField(exist = false)
    private String username;

    @TableField(exist = false)
    private String placeName;

    @TableField(exist = false)
    private int radio;


}
