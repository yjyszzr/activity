package com.dl.activity.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class WCPlanDTO {

	@ApiModelProperty("竞猜时间")
	private String addTime;

	@ApiModelProperty("竞猜16强选项")
	private List<WCPlanCellDTO> plan16 = new ArrayList<WCPlanCellDTO>(0);

	@ApiModelProperty("是否猜种0待开奖，1命中，2未命中")
	private Integer rst16 = 0;

	@ApiModelProperty(" 1高亮，2不高亮")
	private Integer isGet16 = 0;

	@ApiModelProperty("16强奖金")
	private String rst16Amount;

	@ApiModelProperty("竞猜8强选项")
	private List<WCPlanCellDTO> plan8 = new ArrayList<WCPlanCellDTO>(0);;

	@ApiModelProperty("是否猜种0待开奖，1命中，2未命中")
	private Integer rst8 = 0;

	@ApiModelProperty(" 1高亮，2不高亮")
	private Integer isGet8 = 0;

	@ApiModelProperty("8强奖金")
	private String rst8Amount;

	@ApiModelProperty("竞猜4强选项")
	private List<WCPlanCellDTO> plan4 = new ArrayList<WCPlanCellDTO>(0);;

	@ApiModelProperty("是否猜种0待开奖，1命中，2未命中")
	private Integer rst4 = 0;

	@ApiModelProperty(" 1高亮，2不高亮")
	private Integer isGet4 = 0;

	@ApiModelProperty("4强奖金")
	private String rst4Amount;

	@ApiModelProperty("竞猜冠亚军选项")
	private List<WCPlanCellDTO> plan2 = new ArrayList<WCPlanCellDTO>(0);;

	@ApiModelProperty("是否猜种0待开奖，1命中，2未命中")
	private Integer rst2 = 0;

	@ApiModelProperty(" 1高亮，2不高亮")
	private Integer isGet2 = 0;

	@ApiModelProperty("冠亚军奖金")
	private String rst2Amount;

	@ApiModelProperty("竞猜冠军选项")
	private WCPlanCellDTO plan1 = new WCPlanCellDTO();

	@ApiModelProperty("是否猜种0待开奖，1命中，2未命中")
	private Integer rst1 = 0;

	@ApiModelProperty(" 1高亮，2不高亮")
	private Integer isGet1 = 0;

	@ApiModelProperty("冠军奖金")
	private String rst1Amount;

	@ApiModelProperty("是否猜种0待开奖，1命中，2未命中")
	private Integer rstAllTrue = 0;

	@ApiModelProperty(" 1高亮，2不高亮")
	private Integer isGetAll = 0;

	@ApiModelProperty("终极奖金")
	private String rstAllTrueAmount;

}
