package com.dl.activity.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;

/**
 * 是否领取过档位奖励参数类
 * @author zhangzirong
 *
 */
@ApiModel("是否领取过档位奖励参数类")
@Data
public class GearHasReceivedParam implements Serializable{

    private Integer act_id;
    private Integer config_id;
    private Integer user_id;
    private String gear_position;
    private String gear_position_money;
    private Date add_time;
    private Integer act_start_time;
    private Integer act_end_time;

}