package com.dl.activity.dto;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WCPlanDTO {

	@ApiModelProperty("竞猜时间")
	private String addTime;
	@ApiModelProperty("竞猜16强选项")
	private List<WCPlanCellDTO> plan16 = new ArrayList<WCPlanCellDTO>(0);
	@ApiModelProperty("是否猜种0待开奖，1命中，2未命中")
	private Integer rst16 = 0;
	@ApiModelProperty("竞猜8强选项")
	private List<WCPlanCellDTO> plan8 = new ArrayList<WCPlanCellDTO>(0);;
	@ApiModelProperty("是否猜种0待开奖，1命中，2未命中")
	private Integer rst8 = 0;
	@ApiModelProperty("竞猜4强选项")
	private List<WCPlanCellDTO> plan4 = new ArrayList<WCPlanCellDTO>(0);;
	@ApiModelProperty("是否猜种0待开奖，1命中，2未命中")
	private Integer rst4 = 0;
	@ApiModelProperty("竞猜冠亚军选项")
	private List<WCPlanCellDTO> plan2 = new ArrayList<WCPlanCellDTO>(0);;
	@ApiModelProperty("是否猜种0待开奖，1命中，2未命中")
	private Integer rst2 = 0;
	@ApiModelProperty("竞猜冠军选项")
	private WCPlanCellDTO plan1 = new WCPlanCellDTO();
	@ApiModelProperty("是否猜种0待开奖，1命中，2未命中")
	private Integer rst1 = 0;
	
}
