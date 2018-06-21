package com.dl.activity.model;

import java.math.BigDecimal;
import javax.persistence.*;

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

    /**
     * 获取赛事Id
     *
     * @return match_id - 赛事Id
     */
    public Integer getMatchId() {
        return matchId;
    }

    /**
     * 设置赛事Id
     *
     * @param matchId 赛事Id
     */
    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    /**
     * 获取开始时间
     *
     * @return start_time - 开始时间
     */
    public Integer getStartTime() {
        return startTime;
    }

    /**
     * 设置开始时间
     *
     * @param startTime 开始时间
     */
    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取结束时间
     *
     * @return end_time - 结束时间
     */
    public Integer getEndTime() {
        return endTime;
    }

    /**
     * 设置结束时间
     *
     * @param endTime 结束时间
     */
    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取活动类型
     *
     * @return scope_of_activity - 活动类型
     */
    public Byte getScopeOfActivity() {
        return scopeOfActivity;
    }

    /**
     * 设置活动类型
     *
     * @param scopeOfActivity 活动类型
     */
    public void setScopeOfActivity(Byte scopeOfActivity) {
        this.scopeOfActivity = scopeOfActivity;
    }

    /**
     * 获取活动最低参与金额
     *
     * @return limit_lottery_amount - 活动最低参与金额
     */
    public BigDecimal getLimitLotteryAmount() {
        return limitLotteryAmount;
    }

    /**
     * 设置活动最低参与金额
     *
     * @param limitLotteryAmount 活动最低参与金额
     */
    public void setLimitLotteryAmount(BigDecimal limitLotteryAmount) {
        this.limitLotteryAmount = limitLotteryAmount;
    }

    /**
     * 获取奖池
     *
     * @return bonus_pool - 奖池
     */
    public BigDecimal getBonusPool() {
        return bonusPool;
    }

    /**
     * 设置奖池
     *
     * @param bonusPool 奖池
     */
    public void setBonusPool(BigDecimal bonusPool) {
        this.bonusPool = bonusPool;
    }

    /**
     * 获取问题以及答案
     *
     * @return question_and_answer - 问题以及答案
     */
    public String getQuestionAndAnswer() {
        return questionAndAnswer;
    }

    /**
     * 设置问题以及答案
     *
     * @param questionAndAnswer 问题以及答案
     */
    public void setQuestionAndAnswer(String questionAndAnswer) {
        this.questionAndAnswer = questionAndAnswer;
    }

    /**
     * 获取人数
     *
     * @return num_of_people - 人数
     */
    public Integer getNumOfPeople() {
        return numOfPeople;
    }

    /**
     * 设置人数
     *
     * @param numOfPeople 人数
     */
    public void setNumOfPeople(Integer numOfPeople) {
        this.numOfPeople = numOfPeople;
    }

    /**
     * 获取题目创建时间
     *
     * @return create_time - 题目创建时间
     */
    public Integer getCreateTime() {
        return createTime;
    }

    /**
     * 设置题目创建时间
     *
     * @param createTime 题目创建时间
     */
    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取答案状态(0草稿,1发布,2回填答案)
     *
     * @return status - 答案状态(0草稿,1发布,2回填答案)
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置答案状态(0草稿,1发布,2回填答案)
     *
     * @param status 答案状态(0草稿,1发布,2回填答案)
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取竞猜标题
     *
     * @return guessing_title - 竞猜标题
     */
    public String getGuessingTitle() {
        return guessingTitle;
    }

    /**
     * 设置竞猜标题
     *
     * @param guessingTitle 竞猜标题
     */
    public void setGuessingTitle(String guessingTitle) {
        this.guessingTitle = guessingTitle;
    }

    /**
     * 获取答案回填时间
     *
     * @return answer_show_time - 答案回填时间
     */
    public Integer getAnswerShowTime() {
        return answerShowTime;
    }

    /**
     * 设置答案回填时间
     *
     * @param answerShowTime 答案回填时间
     */
    public void setAnswerShowTime(Integer answerShowTime) {
        this.answerShowTime = answerShowTime;
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
     * @param period 期次
     */
    public void setPeriod(String period) {
        this.period = period;
    }
}