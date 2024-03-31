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
 * @since 2023-05-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UserApply对象", description="")
public class UserApply implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "参赛信息id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "竞赛id")
    @TableField("competitionId")
    private Long competitionid;

    @ApiModelProperty(value = "用户id")
    @TableField("userId")
    private Long userid;

    @ApiModelProperty(value = "用户参赛时间")
    private Date created;

    @ApiModelProperty(value = "用户参赛状态，0-已报名，1-取消报名")
    private Integer status;

    @TableField(exist = false)
    private String username;

    @TableField(exist = false)
    private String userAvatar;

    //竞赛名称
    @TableField(exist = false)
    private String competitionName;

    @TableField(exist = false)
    private Date competitionStartTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompetitionid() {
        return competitionid;
    }

    public void setCompetitionid(Long competitionid) {
        this.competitionid = competitionid;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getCompetitionName() {
        return competitionName;
    }

    public void setCompetitionName(String competitionName) {
        this.competitionName = competitionName;
    }

    public Date getCompetitionStartTime() {
        return competitionStartTime;
    }

    public void setCompetitionStartTime(Date competitionStartTime) {
        this.competitionStartTime = competitionStartTime;
    }
}
