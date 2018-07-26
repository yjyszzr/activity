package com.dl.activity.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddAnswersParam {
	@ApiModelProperty(value = "赛事Id")
	public Integer matchId;

	@ApiModelProperty(value = "答案列表")
	public String answers;
}
