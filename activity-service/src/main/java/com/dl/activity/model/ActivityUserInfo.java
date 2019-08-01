package com.dl.activity.model;

import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "dl_activity_user_info")
public class ActivityUserInfo {
    /**
     * id
     */
    @Id
    @Column(name = "id")
    private Integer id;
  
    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Integer user_id;
  
    /**
     * 手机号码
     */
    @Column(name = "mobile")
    private String mobile;
    
    /**
     * 邀请人数
     */
    @Column(name = "invitation_number")
    private Integer invitation_number;
    
    /**
     * 邀请人数奖励
     */
    @Column(name = "invitation_number_reward")
    private String invitation_number_reward;
    
    /**
     *历史 邀请人数
     */
    @Column(name = "history_invitation_number")
    private Integer history_invitation_number;
    
    /**
     *历史 邀请人数奖励
     */
    @Column(name = "history_invitation_number_reward")
    private String history_invitation_number_reward;
    
    /**
     * 可提现收益
     */
    @Column(name = "withdrawable_reward")
    private String withdrawable_reward;
    
    /**
     * 历史可提现总收益
     */
    @Column(name = "history_total_withdrawable_reward")
    private String history_total_withdrawable_reward;
    
    /**
     * 当月返利
     */
    @Column(name = "month_return_reward")
    private String month_return_reward;
    
    /**
     * 历史总返利
     */
    @Column(name = "history_total_return_reward")
    private String history_total_return_reward;
    
    /**
     * 好友累计购彩金额
     */
    @Column(name = "invitation_add_reward")
    private String invitation_add_reward;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getInvitation_number() {
		return invitation_number;
	}

	public void setInvitation_number(Integer invitation_number) {
		this.invitation_number = invitation_number;
	}

	public String getInvitation_number_reward() {
		return invitation_number_reward;
	}

	public void setInvitation_number_reward(String invitation_number_reward) {
		this.invitation_number_reward = invitation_number_reward;
	}

	public Integer getHistory_invitation_number() {
		return history_invitation_number;
	}

	public void setHistory_invitation_number(Integer history_invitation_number) {
		this.history_invitation_number = history_invitation_number;
	}

	public String getHistory_invitation_number_reward() {
		return history_invitation_number_reward;
	}

	public void setHistory_invitation_number_reward(String history_invitation_number_reward) {
		this.history_invitation_number_reward = history_invitation_number_reward;
	}

	public String getWithdrawable_reward() {
		return withdrawable_reward;
	}

	public void setWithdrawable_reward(String withdrawable_reward) {
		this.withdrawable_reward = withdrawable_reward;
	}

	public String getHistory_total_withdrawable_reward() {
		return history_total_withdrawable_reward;
	}

	public void setHistory_total_withdrawable_reward(String history_total_withdrawable_reward) {
		this.history_total_withdrawable_reward = history_total_withdrawable_reward;
	}

	public String getMonth_return_reward() {
		return month_return_reward;
	}

	public void setMonth_return_reward(String month_return_reward) {
		this.month_return_reward = month_return_reward;
	}

	public String getHistory_total_return_reward() {
		return history_total_return_reward;
	}

	public void setHistory_total_return_reward(String history_total_return_reward) {
		this.history_total_return_reward = history_total_return_reward;
	}

	public String getInvitation_add_reward() {
		return invitation_add_reward;
	}

	public void setInvitation_add_reward(String invitation_add_reward) {
		this.invitation_add_reward = invitation_add_reward;
	}
    

}