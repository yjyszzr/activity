package com.dl.activity.dto;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RewardDTO {

	@ApiModelProperty("奖项")
	private String prize;

	@ApiModelProperty("金额")
	private BigDecimal quota;

	@ApiModelProperty("中奖人数")
	private Integer peopleNum;

	@ApiModelProperty("平均数")
	private BigDecimal average;

}
