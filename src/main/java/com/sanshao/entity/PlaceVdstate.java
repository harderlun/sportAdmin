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
@ApiModel(value="PlaceVdstate对象", description="")
public class PlaceVdstate implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "场地预约详细编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "场地编号")
    @TableField("placeId")
    private Long placeid;

    @ApiModelProperty(value = "日期")
    @TableField("vdstateDate")
    private Date vdstatedate;

    @ApiModelProperty(value = "分时状态，第i位代表第i~i + 1小时的场地状态，0代表可用，1代表不可用，2代表已经被预约")
    @TableField("vdstateSt")
    private String vdstatest;

    @TableField(exist = false)
    private String placeName;

}
