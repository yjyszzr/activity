package com.dl.activity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QuestionAndAnswersDTO {

	@ApiModelProperty("题号")
	public Integer questionNum;

	@ApiModelProperty("题设")
	public String questionSetting;

	@ApiModelProperty("答案1")
	public String answerSetting1;

	@ApiModelProperty("答案1状态")
	public Integer answerStatus1;

	@ApiModelProperty("答案2")
	public String answerSetting2;

	@ApiModelProperty("答案2状态")
	public Integer answerStatus2;

}
