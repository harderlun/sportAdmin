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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getPlaceid() {
        return placeid;
    }

    public void setPlaceid(Long placeid) {
        this.placeid = placeid;
    }

    public Date getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Date orderdate) {
        this.orderdate = orderdate;
    }

    public Integer getOrderstart() {
        return orderstart;
    }

    public void setOrderstart(Integer orderstart) {
        this.orderstart = orderstart;
    }

    public Integer getOrderend() {
        return orderend;
    }

    public void setOrderend(Integer orderend) {
        this.orderend = orderend;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Long getPlaceStateId() {
        return placeStateId;
    }

    public void setPlaceStateId(Long placeStateId) {
        this.placeStateId = placeStateId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public int getRadio() {
        return radio;
    }

    public void setRadio(int radio) {
        this.radio = radio;
    }
}
