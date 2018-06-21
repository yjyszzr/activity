package com.dl.activity.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "dl_questions_and_answers_user")
public class DlQuestionsAndAnswersUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * 赛事ID
	 */
	@Column(name = "match_id")
	private Integer matchId;

	/**
	 * 用户ID
	 */
	@Column(name = "user_id")
	private Integer userId;

	/**
	 * 问题ID
	 */
	@Column(name = "question_id")
	private Integer questionId;

	/**
	 * 用户答案
	 */
	@Column(name = "user_answer")
	private String userAnswer;

	/**
	 * 答题时间
	 */
	@Column(name = "answer_time")
	private Integer answerTime;

	/**
	 * 期次
	 */
	private String period;

	/**
	 * 是否中奖0未中奖 1 中奖
	 */
	@Column(name = "get_award")
	private Byte getAward;

	/**
	 * 倍率
	 */
	private Integer multiple;

	/**
	 * 奖金金额
	 */
	@Column(name = "bonus_amount")
	private BigDecimal bonusAmount;

	/**
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMatchId() {
		return matchId;
	}

	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * 获取问题ID
	 *
	 * @return question_id - 问题ID
	 */
	public Integer getQuestionId() {
		return questionId;
	}

	/**
	 * 设置问题ID
	 *
	 * @param questionId
	 *            问题ID
	 */
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	/**
	 * 获取用户答案
	 *
	 * @return user_answer - 用户答案
	 */
	public String getUserAnswer() {
		return userAnswer;
	}

	/**
	 * 设置用户答案
	 *
	 * @param userAnswer
	 *            用户答案
	 */
	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}

	/**
	 * 获取答题时间
	 *
	 * @return answer_time - 答题时间
	 */
	public Integer getAnswerTime() {
		return answerTime;
	}

	/**
	 * 设置答题时间
	 *
	 * @param answerTime
	 *            答题时间
	 */
	public void setAnswerTime(Integer answerTime) {
		this.answerTime = answerTime;
	}

	/**
	 * 获取期次
	 *
	 * @return period - 期次
	 */
	public String getPeriod() {
		return period;
	}

	/**
	 * 设置期次
	 *
	 * @param period
	 *            期次
	 */
	public void setPeriod(String period) {
		this.period = period;
	}

	/**
	 * 获取是否中奖0未中奖 1 中奖
	 *
	 * @return get_award - 是否中奖0未中奖 1 中奖
	 */
	public Byte getGetAward() {
		return getAward;
	}

	/**
	 * 设置是否中奖0未中奖 1 中奖
	 *
	 * @param getAward
	 *            是否中奖0未中奖 1 中奖
	 */
	public void setGetAward(Byte getAward) {
		this.getAward = getAward;
	}

	/**
	 * 获取倍率
	 *
	 * @return multiple - 倍率
	 */
	public Integer getMultiple() {
		return multiple;
	}

	/**
	 * 设置倍率
	 *
	 * @param multiple
	 *            倍率
	 */
	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

	/**
	 * 获取奖金金额
	 *
	 * @return bonus_amount - 奖金金额
	 */
	public BigDecimal getBonusAmount() {
		return bonusAmount;
	}

	/**
	 * 设置奖金金额
	 *
	 * @param bonusAmount
	 *            奖金金额
	 */
	public void setBonusAmount(BigDecimal bonusAmount) {
		this.bonusAmount = bonusAmount;
	}
}