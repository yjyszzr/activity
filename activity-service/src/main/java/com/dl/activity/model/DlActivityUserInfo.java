package com.dl.activity.model;

import javax.persistence.*;

@Table(name = "dl_activity_user_info")
public class DlActivityUserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 邀请人数
     */
    @Column(name = "invitation_number")
    private Integer invitationNumber;

    /**
     * 可提现收益
     */
    @Column(name = "withdrawable_reward")
    private Double withdrawableReward;

    /**
     * 历史可提现总收益
     */
    @Column(name = "history_total_withdrawable_reward")
    private Double historyTotalWithdrawableReward;

    /**
     * 当月返利
     */
    @Column(name = "month_return_reward")
    private Double monthReturnReward;

    /**
     * 人数奖励
     */
    @Column(name = "invitation_number_reward")
    private Double invitationNumberReward;

    /**
     * 历史返利
     */
    @Column(name = "history_total_return_reward")
    private Double historyTotalReturnReward;

    /**
     * 好友累计购彩金额
     */
    @Column(name = "invitation_add_reward")
    private Double invitationAddReward;

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
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取手机号码
     *
     * @return mobile - 手机号码
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机号码
     *
     * @param mobile 手机号码
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取邀请人数
     *
     * @return invitation_number - 邀请人数
     */
    public Integer getInvitationNumber() {
        return invitationNumber;
    }

    /**
     * 设置邀请人数
     *
     * @param invitationNumber 邀请人数
     */
    public void setInvitationNumber(Integer invitationNumber) {
        this.invitationNumber = invitationNumber;
    }


    public Double getWithdrawableReward() {
        return withdrawableReward;
    }

    public void setWithdrawableReward(Double withdrawableReward) {
        this.withdrawableReward = withdrawableReward;
    }

    public Double getHistoryTotalWithdrawableReward() {
        return historyTotalWithdrawableReward;
    }

    public void setHistoryTotalWithdrawableReward(Double historyTotalWithdrawableReward) {
        this.historyTotalWithdrawableReward = historyTotalWithdrawableReward;
    }

    public Double getMonthReturnReward() {
        return monthReturnReward;
    }

    public void setMonthReturnReward(Double monthReturnReward) {
        this.monthReturnReward = monthReturnReward;
    }

    public Double getInvitationNumberReward() {
        return invitationNumberReward;
    }

    public void setInvitationNumberReward(Double invitationNumberReward) {
        this.invitationNumberReward = invitationNumberReward;
    }

    public Double getHistoryTotalReturnReward() {
        return historyTotalReturnReward;
    }

    public void setHistoryTotalReturnReward(Double historyTotalReturnReward) {
        this.historyTotalReturnReward = historyTotalReturnReward;
    }

    public Double getInvitationAddReward() {
        return invitationAddReward;
    }

    public void setInvitationAddReward(Double invitationAddReward) {
        this.invitationAddReward = invitationAddReward;
    }
}