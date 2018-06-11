package dl.lottery.param;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("方案参数")
@Data
public class PlanStrParam {
	
	@ApiModelProperty(value="方案参数")
	@NotNull(message = "方案参数 不能为空")
	private String planStrParam;

}
