package com.dl.activity.model;

import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "dl_activity_config")
public class ActivityConfig {
    /**
     * id 
     */
    @Id
    @Column(name = "id")
    private Integer id;
    /**
     * 活动id
     */
    @Column(name = "act_id")
    private Integer act_id;
    
    /**
     * 档位（人数或金额）
     */
    @Column(name = "gear_position")
    private String gear_position;
    
    /**
     * 奖励金额
     */
    @Column(name = "gear_position_money")
    private String gear_position_money;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAct_id() {
		return act_id;
	}

	public void setAct_id(Integer act_id) {
		this.act_id = act_id;
	}

	public String getGear_position() {
		return gear_position;
	}

	public void setGear_position(String gear_position) {
		this.gear_position = gear_position;
	}

	public String getGear_position_money() {
		return gear_position_money;
	}

	public void setGear_position_money(String gear_position_money) {
		this.gear_position_money = gear_position_money;
	}
    
    
}