package com.dl.activity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("世界杯16强信息")
@Data
public class DlWorldCupContryDTO {
	// @ApiModelProperty("国家队Id")
	// private Integer countryId;
	// @ApiModelProperty("国家队名称")
	// private String contryName;
	// @ApiModelProperty("国家队国旗")
	// private String contryPic;
	// @ApiModelProperty("16强")
	// private String is16;

	@ApiModelProperty("16强")
	private String sld;
	@ApiModelProperty("国家队国旗")
	private String icon;
	@ApiModelProperty("国家队名称")
	private String name;
	@ApiModelProperty("国家队Id")
	private String id;

}