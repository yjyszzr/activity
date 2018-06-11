package com.dl.activity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

import lombok.Data;

@ApiModel("世界杯竞猜信息")
@Data
public class GuessingCompetitionDTO {
	@ApiModelProperty("可投注次数")
	private Integer BettingNum;
	@ApiModelProperty("跳转状态:0-结束,1-小组赛竞猜,2-等待期,3-8强竞猜期")
	private Integer jumpStatus;
	@ApiModelProperty("16强结果")
	private List<DlWorldCupContryDTO> worldCupContryList;
}
