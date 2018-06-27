package com.dl.activity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserPeriodDTO {

	@ApiModelProperty("赛事Id")
	public Integer matchId;

	@ApiModelProperty("期次")
	public String period;
}
