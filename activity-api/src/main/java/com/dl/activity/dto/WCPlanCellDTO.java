package com.dl.activity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WCPlanCellDTO {

	@ApiModelProperty("国家id")
    private Integer countryId;
	@ApiModelProperty("国家名称")
    private String contryName;
	@ApiModelProperty("是否猜种0未命中，1命中")
	private Integer isGet;
	
	public WCPlanCellDTO(){}
	
	public WCPlanCellDTO(Integer countryId, String contryName){
		this.countryId = countryId;
		this.contryName = contryName;
	}
}
