package com.dl.activity.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 推广活动用户初始化参数类
 * @author zhangzirong
 *
 */
@ApiModel("推广活动线下充值累计")
@Data
public class ActivityParam {
	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 充值金额
	 */
	private double money;
	
}
