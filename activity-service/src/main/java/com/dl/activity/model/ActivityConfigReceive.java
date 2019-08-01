package com.dl.activity.model;

import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "dl_activity_config_user_receive")
public class ActivityConfigReceive {
    /**
     * id 
     */
    @Id
    @Column(name = "id")
    private Integer id;
    /**
     * 活动id
     */
    @Column(name = "config_id")
    private Integer config_id;
    /**
     * 活动id
     */
    @Column(name = "user_id")
    private Integer user_id;
    
    /**
     * 档位（人数或金额）
     */
    @Column(name = "gear_position")
    private String gear_position;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getConfig_id() {
		return config_id;
	}

	public void setConfig_id(Integer config_id) {
		this.config_id = config_id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getGear_position() {
		return gear_position;
	}

	public void setGear_position(String gear_position) {
		this.gear_position = gear_position;
	}
    
   
    
}