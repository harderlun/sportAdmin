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
@ApiModel(value="SysCompetition对象", description="")
public class SysCompetition implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "竞赛id")
    private Long id;

    @ApiModelProperty(value = "竞赛名称")
    private String name;

    @ApiModelProperty(value = "竞赛图片")
    private String url;

    @ApiModelProperty(value = "竞赛开始时间")
    @TableField("startTime")
    private Date starttime;

    @ApiModelProperty(value = "竞赛结束时间")
    @TableField("endTime")
    private Date endtime;

    @ApiModelProperty(value = "竞赛状态，0-未发布，1-发布")
    private Integer status;

    @ApiModelProperty(value = "竞赛创建人")
    @TableField("userId")
    private Long userid;

    @ApiModelProperty(value = "竞赛创建时间")
    private Date created;

    @ApiModelProperty(value = "竞赛地点")
    private String place;

    @ApiModelProperty(value = "竞赛介绍")
    private String description;

    @TableField(exist = false)
    private String username;

    @TableField(exist = false)
    private Integer applyStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }
}
