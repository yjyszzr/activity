package com.dl.activity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("邀请人数和奖金")
@Data
public class DlNumAndRewardDTO {

	@ApiModelProperty("邀请人数")
	private String invitationNum;

	@ApiModelProperty("奖金")
	private String reward;

}