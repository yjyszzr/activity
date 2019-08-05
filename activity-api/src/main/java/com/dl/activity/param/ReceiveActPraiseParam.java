package com.dl.activity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 推广活动档位奖励参数类
 * @author zhangzirong
 *
 */
@ApiModel("推广活动档位奖励参数类")
@Data
public class ReceiveActPraiseParam implements Serializable{

	private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "活动id")
    @NotBlank(message = "活动id不能为空")
    private String actId;

    @ApiModelProperty(value = "活动类型：2 - 伯乐奖 3-荣耀奖")
    @NotBlank(message = "活动类型不能为空")
    private String actType;

    @ApiModelProperty(value = "档位:整数中不要带小数点，小数保留两位")
    @NotBlank(message = "档位不能为空")
    private String gearPosition;

    @ApiModelProperty(value = "档位值:整数中不要带小数点，小数保留两位")
    @NotBlank(message = "档位值不能为空")
    private String gearPositionMoney;

}
