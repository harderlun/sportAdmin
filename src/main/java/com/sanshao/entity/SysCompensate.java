package com.sanshao.entity;

import java.util.Date;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBorrowid() {
        return borrowid;
    }

    public void setBorrowid(Long borrowid) {
        this.borrowid = borrowid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
