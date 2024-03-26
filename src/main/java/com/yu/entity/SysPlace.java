package com.yu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2023-05-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysPlace对象", description="")
public class SysPlace implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "场地名称")
    private String place;

    @ApiModelProperty(value = "联系人")
    private String people;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "场地地址")
    private String address;

    @ApiModelProperty(value = "场地描述")
    private String description;

    @ApiModelProperty(value = "创建时间")
    private Date created;

    @ApiModelProperty(value = "修改时间")
    private Date updated;

    @ApiModelProperty(value = "状态，0-发布，1-未发布，2-暂存")
    private Integer status;

    @ApiModelProperty(value = "场地图片")
    private String url;


}
