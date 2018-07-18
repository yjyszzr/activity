package com.dl.activity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("分享链接")
@Data
public class DlShareLinkDTO {

	@ApiModelProperty("分享链接userId")
	private String userld;

}