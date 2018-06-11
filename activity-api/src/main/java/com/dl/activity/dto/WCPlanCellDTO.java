package com.dl.activity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WCPlanCellDTO {

	@ApiModelProperty("国家id")
    private Integer countryId;
	@ApiModelProperty("国家名称")
    private String contryName;
	@ApiModelProperty("是否猜种0待开奖，1命中，2未命中")
	private Integer isGet;
}
