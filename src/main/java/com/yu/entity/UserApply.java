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

}
