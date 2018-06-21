package com.dl.activity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("赛事Id参数")
@Data
public class MatchIdParam {

	@ApiModelProperty(value = "赛事Id")
	private Integer matchId;

}
