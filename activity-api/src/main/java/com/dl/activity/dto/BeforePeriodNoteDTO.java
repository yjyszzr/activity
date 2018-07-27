package com.dl.activity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("人数和奖金")
@Data
public class BeforePeriodNoteDTO {

	@ApiModelProperty("获奖人数")
	private Integer numOfPeople;

	@ApiModelProperty("奖金")
	private String reward;

	@ApiModelProperty("奖池金额")
	private String bonusPool;

}