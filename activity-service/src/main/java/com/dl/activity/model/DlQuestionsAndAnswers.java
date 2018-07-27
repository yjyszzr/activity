package com.dl.activity.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "dl_questions_and_answers")
public class DlQuestionsAndAnswers {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 赛事Id
	 */
	@Column(name = "match_id")
	private Integer matchId;

	/**
	 * 开始时间
	 */
	@Column(name = "start_time")
	private Integer startTime;

	/**
	 * 结束时间
	 */
	@Column(name = "end_time")
	private Integer endTime;

	/**
	 * 活动类型
	 */
	@Column(name = "scope_of_activity")
	private Byte scopeOfActivity;

	/**
	 * 活动最低参与金额
	 */
	@Column(name = "limit_lottery_amount")
	private BigDecimal limitLotteryAmount;

	/**
	 * 奖池
	 */
	@Column(name = "bonus_pool")
	private BigDecimal bonusPool;

	/**
	 * 问题以及答案
	 */
	@Column(name = "question_and_answer")
	private String questionAndAnswer;

	/**
	 * 人数
	 */
	@Column(name = "num_of_people")
	private Integer numOfPeople;

	/**
	 * 题目创建时间
	 */
	@Column(name = "create_time")
	private Integer createTime;

	/**
	 * 答案状态(0草稿,1发布,2回填答案)
	 */
	private Byte status;

	/**
	 * 竞猜标题
	 */
	@Column(name = "guessing_title")
	private String guessingTitle;

	/**
	 * 答案回填时间
	 */
	@Column(name = "answer_show_time")
	private Integer answerShowTime;

	/**
	 * 期次
	 */
	private String period;

	/**
	 * 获奖人数
	 */
	@Column(name = "prizewinning_num")
	private Integer prizewinningNum;
}