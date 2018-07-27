package com.dl.activity.model;

import java.math.BigDecimal;

import javax.persistence.Id;

import lombok.Data;

@Data
public class DlQuestionsAndAnswersForBeforeNote {
	@Id
	private Integer id;

	private Integer matchId;

	/**
	 * 获奖人数
	 */
	private Integer prizewinningNum;
	/**
	 * 奖池
	 */
	private BigDecimal bonusPool;
}