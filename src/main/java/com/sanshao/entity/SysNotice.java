package com.sanshao.entity;

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
 * @since 2023-05-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysNotice对象", description="")
public class SysNotice implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "公告Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "公告标题")
    private String title;

    @ApiModelProperty(value = "公告内容")
    private String content;

    @ApiModelProperty(value = "发布公告的用户名")
    private String writer;

    @ApiModelProperty(value = "1-发布，2-撤回，3-未发布")
    private Integer status;

    @ApiModelProperty(value = "公告创建时间")
    private Date created;

    @ApiModelProperty(value = "公告修改时间")
    private Date updated;


}
