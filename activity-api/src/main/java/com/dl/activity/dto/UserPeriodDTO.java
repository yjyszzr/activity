package com.dl.activity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserPeriodDTO {

	@ApiModelProperty("用户答题Id")
	public Integer userQuestionAndAnswersId;

	@ApiModelProperty("期次")
	public String period;
}
