package com.dl.activity.dto;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class MatchInfoDTO {

	@ApiModelProperty("赛事Id")
	private Integer matchId;

	@ApiModelProperty("主队简称")
	private String homeTeamAbbr;

	@ApiModelProperty("主队Logo")
	private String homeTeamPic;

	@ApiModelProperty("客队简称")
	private String visitingTeamAbbr;

	@ApiModelProperty("客队Logo")
	private String visitingTeamPic;

	@ApiModelProperty("截止时间")
	private Integer stopTime;

	@ApiModelProperty("再投注金额")
	private BigDecimal onceBettingAmount;

	@ApiModelProperty("机会:有没有投注机会0:没有1有")
	private Integer chance;

	@ApiModelProperty("答题时间状态:0已结束,1开始,2未开始")
	private Integer answerTimeStatus;

	@ApiModelProperty("答案状态:空是待开奖,0是未中奖,1中奖")
	private Integer userGetAwardStatus;

	@ApiModelProperty("人数")
	private Integer numOfPeople;

	@ApiModelProperty("奖池金额")
	private String bonusPool;

	@ApiModelProperty("获奖金额")
	private String reward;

	@ApiModelProperty("问题及答案")
	private List<QuestionAndAnswersDTO> questionAndAnswersList;
}
