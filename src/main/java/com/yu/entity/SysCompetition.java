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

}
