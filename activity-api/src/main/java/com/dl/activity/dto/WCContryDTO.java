package com.dl.activity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WCContryDTO {

	@ApiModelProperty("国家id")
    private Integer countryId;
	@ApiModelProperty("国家名称")
    private String contryName;
	@ApiModelProperty("国家图标")
    private String contryPic;
	@ApiModelProperty("是否16强")
    private String is16;
	@ApiModelProperty("是否8强")
    private String is8;
	@ApiModelProperty("是否4强")
    private String is4;
	@ApiModelProperty("是否冠亚军")
    private String is2;
	@ApiModelProperty("是否冠军")
    private String is1;
}
