package com.dl.activity.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OldBeltNewParam {
	@ApiModelProperty(value = "电话")
	private String mobile;

	@ApiModelProperty(value = " 邀请人加密用户Id")
	private String inviterEncryptionUserId;

}
