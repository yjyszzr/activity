package com.dl.activity.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 推广活动用户初始化参数类
 * @author zhangzirong
 *
 */
@ApiModel("推广活动用户初始化参数类")
@Data
public class ActUserInitParam {

	private Integer userId;

	private Integer sonUserId;

	private String mobile;

	
}
