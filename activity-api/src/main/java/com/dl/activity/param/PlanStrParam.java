package com.dl.activity.param;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("方案参数")
@Data
public class PlanStrParam {
	
	@ApiModelProperty(value="方案参数 多少强 |排序名:国家id,排序名:国家id 例如16|A1:101,A2:102,B1:103;8|A1:101,A2:102,B1:103;4|A1:101,A2:102,A3:103;2|A1:101,A2:102;1|A3:103")
	@NotNull(message = "方案参数 不能为空")
	private String planStrParam;

}
