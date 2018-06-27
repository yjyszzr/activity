package com.dl.activity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("用户答案参数")
@Data
public class UserAnswersParam {

	@ApiModelProperty(value = "用户答案Id")
	private Integer userAnswersId;
}
