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

    public Long getEquipmentid() {
        return equipmentid;
    }

    public void setEquipmentid(Long equipmentid) {
        this.equipmentid = equipmentid;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Long getTotalmoney() {
        return totalmoney;
    }

    public void setTotalmoney(Long totalmoney) {
        this.totalmoney = totalmoney;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public Integer getRepairsStatus() {
        return repairsStatus;
    }

    public void setRepairsStatus(Integer repairsStatus) {
        this.repairsStatus = repairsStatus;
    }
}
