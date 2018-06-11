package com.dl.activity.model;

import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "dl_world_cup_plan")
public class DlWorldCupPlan {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户Id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 添加时间
     */
    @Column(name = "add_time")
    private Integer addTime;

    /**
     * 修改时间
     */
    @Column(name = "modify_time")
    private Integer modifyTime;

    /**
     * 投注方案
     */
    @Column(name = "plan_json")
    private String planJson;

    /**
     * 16强状态(0是未获奖,1是获奖)
     */
    @Column(name = "status_16")
    private Integer status16;

    /**
     * 16强派奖时间
     */
    @Column(name = "reward_time_16")
    private Integer rewardTime16;

    /**
     * 派奖金额
     */
    @Column(name = "reward_amount_16")
    private BigDecimal rewardAmount16;

    /**
     * 8强状态(0是未获奖,1是获奖)
     */
    @Column(name = "status_8")
    private Integer status8;

    /**
     * 8强派奖时间
     */
    @Column(name = "reward_time_8")
    private Integer rewardTime8;

    /**
     * 8强派奖金额
     */
    @Column(name = "reward_amount_8")
    private BigDecimal rewardAmount8;

    /**
     * 4强状态(0是未获奖,1是获奖)
     */
    @Column(name = "status_4")
    private Integer status4;

    /**
     * 4强派奖时间
     */
    @Column(name = "reward_time_4")
    private Integer rewardTime4;

    /**
     * 4强派奖金额
     */
    @Column(name = "reward_amount_4")
    private BigDecimal rewardAmount4;

    /**
     * 冠亚军状态(0是未获奖,1是获奖)
     */
    @Column(name = "status_gyj")
    private Integer statusGyj;

    /**
     * 冠亚军强派奖时间
     */
    @Column(name = "reward_time_gyj")
    private Integer rewardTimeGyj;

    /**
     * 冠亚军派奖金额
     */
    @Column(name = "reward_amount_gyj")
    private BigDecimal rewardAmountGyj;

    /**
     * 冠军状态(0是未获奖,1是获奖)
     */
    @Column(name = "status_gj")
    private Integer statusGj;

    /**
     * 冠军强派奖时间
     */
    @Column(name = "reward_time_gj")
    private Integer rewardTimeGj;

    /**
     * 冠军派奖金额
     */
    @Column(name = "reward_amount_gj")
    private BigDecimal rewardAmountGj;

    /**
     * 终极大奖状态(0是未获奖,1是获奖)
     */
    @Column(name = "status_all_true")
    private Integer statusAllTrue;

    /**
     * 终极派奖时间
     */
    @Column(name = "reward_time_all_true")
    private Integer rewardTimeAllTrue;

    /**
     * 终极大奖派奖金额
     */
    @Column(name = "reward_amount_all_true")
    private BigDecimal rewardAmountAllTrue;

    /**
     * 获取id
     *
     * @return id - id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户Id
     *
     * @return user_id - 用户Id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置用户Id
     *
     * @param userId 用户Id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取添加时间
     *
     * @return add_time - 添加时间
     */
    public Integer getAddTime() {
        return addTime;
    }

    /**
     * 设置添加时间
     *
     * @param addTime 添加时间
     */
    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

    /**
     * 获取修改时间
     *
     * @return modify_time - 修改时间
     */
    public Integer getModifyTime() {
        return modifyTime;
    }

    /**
     * 设置修改时间
     *
     * @param modifyTime 修改时间
     */
    public void setModifyTime(Integer modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 获取投注方案
     *
     * @return plan_json - 投注方案
     */
    public String getPlanJson() {
        return planJson;
    }

    /**
     * 设置投注方案
     *
     * @param planJson 投注方案
     */
    public void setPlanJson(String planJson) {
        this.planJson = planJson;
    }

    /**
     * 获取16强状态(0是未获奖,1是获奖)
     *
     * @return status_16 - 16强状态(0是未获奖,1是获奖)
     */
    public Integer getStatus16() {
        return status16;
    }

    /**
     * 设置16强状态(0是未获奖,1是获奖)
     *
     * @param status16 16强状态(0是未获奖,1是获奖)
     */
    public void setStatus16(Integer status16) {
        this.status16 = status16;
    }

    /**
     * 获取16强派奖时间
     *
     * @return reward_time_16 - 16强派奖时间
     */
    public Integer getRewardTime16() {
        return rewardTime16;
    }

    /**
     * 设置16强派奖时间
     *
     * @param rewardTime16 16强派奖时间
     */
    public void setRewardTime16(Integer rewardTime16) {
        this.rewardTime16 = rewardTime16;
    }

    /**
     * 获取派奖金额
     *
     * @return reward_amount_16 - 派奖金额
     */
    public BigDecimal getRewardAmount16() {
        return rewardAmount16;
    }

    /**
     * 设置派奖金额
     *
     * @param rewardAmount16 派奖金额
     */
    public void setRewardAmount16(BigDecimal rewardAmount16) {
        this.rewardAmount16 = rewardAmount16;
    }

    /**
     * 获取8强状态(0是未获奖,1是获奖)
     *
     * @return status_8 - 8强状态(0是未获奖,1是获奖)
     */
    public Integer getStatus8() {
        return status8;
    }

    /**
     * 设置8强状态(0是未获奖,1是获奖)
     *
     * @param status8 8强状态(0是未获奖,1是获奖)
     */
    public void setStatus8(Integer status8) {
        this.status8 = status8;
    }

    /**
     * 获取8强派奖时间
     *
     * @return reward_time_8 - 8强派奖时间
     */
    public Integer getRewardTime8() {
        return rewardTime8;
    }

    /**
     * 设置8强派奖时间
     *
     * @param rewardTime8 8强派奖时间
     */
    public void setRewardTime8(Integer rewardTime8) {
        this.rewardTime8 = rewardTime8;
    }

    /**
     * 获取8强派奖金额
     *
     * @return reward_amount_8 - 8强派奖金额
     */
    public BigDecimal getRewardAmount8() {
        return rewardAmount8;
    }

    /**
     * 设置8强派奖金额
     *
     * @param rewardAmount8 8强派奖金额
     */
    public void setRewardAmount8(BigDecimal rewardAmount8) {
        this.rewardAmount8 = rewardAmount8;
    }

    /**
     * 获取4强状态(0是未获奖,1是获奖)
     *
     * @return status_4 - 4强状态(0是未获奖,1是获奖)
     */
    public Integer getStatus4() {
        return status4;
    }

    /**
     * 设置4强状态(0是未获奖,1是获奖)
     *
     * @param status4 4强状态(0是未获奖,1是获奖)
     */
    public void setStatus4(Integer status4) {
        this.status4 = status4;
    }

    /**
     * 获取4强派奖时间
     *
     * @return reward_time_4 - 4强派奖时间
     */
    public Integer getRewardTime4() {
        return rewardTime4;
    }

    /**
     * 设置4强派奖时间
     *
     * @param rewardTime4 4强派奖时间
     */
    public void setRewardTime4(Integer rewardTime4) {
        this.rewardTime4 = rewardTime4;
    }

    /**
     * 获取4强派奖金额
     *
     * @return reward_amount_4 - 4强派奖金额
     */
    public BigDecimal getRewardAmount4() {
        return rewardAmount4;
    }

    /**
     * 设置4强派奖金额
     *
     * @param rewardAmount4 4强派奖金额
     */
    public void setRewardAmount4(BigDecimal rewardAmount4) {
        this.rewardAmount4 = rewardAmount4;
    }

    /**
     * 获取冠亚军状态(0是未获奖,1是获奖)
     *
     * @return status_gyj - 冠亚军状态(0是未获奖,1是获奖)
     */
    public Integer getStatusGyj() {
        return statusGyj;
    }

    /**
     * 设置冠亚军状态(0是未获奖,1是获奖)
     *
     * @param statusGyj 冠亚军状态(0是未获奖,1是获奖)
     */
    public void setStatusGyj(Integer statusGyj) {
        this.statusGyj = statusGyj;
    }

    /**
     * 获取冠亚军强派奖时间
     *
     * @return reward_time_gyj - 冠亚军强派奖时间
     */
    public Integer getRewardTimeGyj() {
        return rewardTimeGyj;
    }

    /**
     * 设置冠亚军强派奖时间
     *
     * @param rewardTimeGyj 冠亚军强派奖时间
     */
    public void setRewardTimeGyj(Integer rewardTimeGyj) {
        this.rewardTimeGyj = rewardTimeGyj;
    }

    /**
     * 获取冠亚军派奖金额
     *
     * @return reward_amount_gyj - 冠亚军派奖金额
     */
    public BigDecimal getRewardAmountGyj() {
        return rewardAmountGyj;
    }

    /**
     * 设置冠亚军派奖金额
     *
     * @param rewardAmountGyj 冠亚军派奖金额
     */
    public void setRewardAmountGyj(BigDecimal rewardAmountGyj) {
        this.rewardAmountGyj = rewardAmountGyj;
    }

    /**
     * 获取冠军状态(0是未获奖,1是获奖)
     *
     * @return status_gj - 冠军状态(0是未获奖,1是获奖)
     */
    public Integer getStatusGj() {
        return statusGj;
    }

    /**
     * 设置冠军状态(0是未获奖,1是获奖)
     *
     * @param statusGj 冠军状态(0是未获奖,1是获奖)
     */
    public void setStatusGj(Integer statusGj) {
        this.statusGj = statusGj;
    }

    /**
     * 获取冠军强派奖时间
     *
     * @return reward_time_gj - 冠军强派奖时间
     */
    public Integer getRewardTimeGj() {
        return rewardTimeGj;
    }

    /**
     * 设置冠军强派奖时间
     *
     * @param rewardTimeGj 冠军强派奖时间
     */
    public void setRewardTimeGj(Integer rewardTimeGj) {
        this.rewardTimeGj = rewardTimeGj;
    }

    /**
     * 获取冠军派奖金额
     *
     * @return reward_amount_gj - 冠军派奖金额
     */
    public BigDecimal getRewardAmountGj() {
        return rewardAmountGj;
    }

    /**
     * 设置冠军派奖金额
     *
     * @param rewardAmountGj 冠军派奖金额
     */
    public void setRewardAmountGj(BigDecimal rewardAmountGj) {
        this.rewardAmountGj = rewardAmountGj;
    }

    /**
     * 获取终极大奖状态(0是未获奖,1是获奖)
     *
     * @return status_all_true - 终极大奖状态(0是未获奖,1是获奖)
     */
    public Integer getStatusAllTrue() {
        return statusAllTrue;
    }

    /**
     * 设置终极大奖状态(0是未获奖,1是获奖)
     *
     * @param statusAllTrue 终极大奖状态(0是未获奖,1是获奖)
     */
    public void setStatusAllTrue(Integer statusAllTrue) {
        this.statusAllTrue = statusAllTrue;
    }

    /**
     * 获取终极派奖时间
     *
     * @return reward_time_all_true - 终极派奖时间
     */
    public Integer getRewardTimeAllTrue() {
        return rewardTimeAllTrue;
    }

    /**
     * 设置终极派奖时间
     *
     * @param rewardTimeAllTrue 终极派奖时间
     */
    public void setRewardTimeAllTrue(Integer rewardTimeAllTrue) {
        this.rewardTimeAllTrue = rewardTimeAllTrue;
    }

    /**
     * 获取终极大奖派奖金额
     *
     * @return reward_amount_all_true - 终极大奖派奖金额
     */
    public BigDecimal getRewardAmountAllTrue() {
        return rewardAmountAllTrue;
    }

    /**
     * 设置终极大奖派奖金额
     *
     * @param rewardAmountAllTrue 终极大奖派奖金额
     */
    public void setRewardAmountAllTrue(BigDecimal rewardAmountAllTrue) {
        this.rewardAmountAllTrue = rewardAmountAllTrue;
    }
}