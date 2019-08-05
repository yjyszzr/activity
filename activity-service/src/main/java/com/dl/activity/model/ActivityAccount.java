package com.dl.activity.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "dl_activity_account")
public class ActivityAccount {
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
     * 手机号
     */
    @Column(name = "mobile")
    private String mobile;
    
    /**
     * 添加时间
     */
    @Column(name = "add_time")
    private Integer add_time;
    
    /**
     * 
     */
    @Column(name = "reward_money")
    private Double reward_money;
    
    /**
     * 邀请人1；单笔购彩3；累计购彩5；提取收益7；邀请人数达到额外奖励挡位9；
     */
    @Column(name = "type")
    private Integer type;

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

	public Integer getAdd_time() {
		return add_time;
	}

	public void setAdd_time(Integer add_time) {
		this.add_time = add_time;
	}

	public Double getReward_money() {
		return reward_money;
	}

	public void setReward_money(Double reward_money) {
		this.reward_money = reward_money;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
    
    
}