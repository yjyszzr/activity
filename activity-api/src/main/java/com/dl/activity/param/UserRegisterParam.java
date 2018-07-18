package com.dl.activity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户注册参数类
 * 
 * @author Mr.Li
 *
 */
@ApiModel("用户注册参数类")
@Data
public class UserRegisterParam {

	@ApiModelProperty(value = "手机号,不能为空", required = true)
	private String mobile;

	@ApiModelProperty(value = "短信验证码,不能为空", required = true)
	private String smsCode;

	@ApiModelProperty(value = "登录来源 1= android,2= ios,3= pc,4 =h5,此接口写:老带新")
	private String loginSource;

	@ApiModelProperty(value = "邀请人Id", required = true)
	private String invitationUserId;

	@ApiModelProperty("消息推送的唯一值")
	private String pushKey;

	@ApiModelProperty(value = "密码,不能为空")
	private String passWord;

	// @ApiModelProperty(value = "手机设备信息", required = false)
	// private UserDeviceParam device;
}
