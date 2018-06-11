package com.dl.activity.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dl.activity.dto.WCPlanDTO;
import com.dl.activity.model.DlWorldCupPlan;
import com.dl.activity.param.EmptyParam;
import com.dl.activity.param.PlanStrParam;
import com.dl.activity.service.DlWorldCupPlanService;
import com.dl.base.result.BaseResult;
import com.dl.base.result.ResultGenerator;
import com.dl.base.util.SessionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.ApiOperation;


/**
 * Created by CodeGenerator on 2018/06/10.
 */
@RestController
@RequestMapping("/dl/activity/worldCupPlan")
public class DlWorldCupPlanController {
	@Resource
	private DlWorldCupPlanService dlWorldCupPlanService;

    @PostMapping("/add")
    public BaseResult<String> add(@RequestBody PlanStrParam planStrParam) {
        Integer planId = dlWorldCupPlanService.saveWorldCupPlan(planStrParam.getPlanStrParam());
        return ResultGenerator.genSuccessResult();
    }
	

	@ApiOperation(value = "我的世界杯推演记录", notes = "我的世界杯推演记录")
	@PostMapping("/list")
	public BaseResult<List<WCPlanDTO>> list(@RequestBody EmptyParam param) {
		Integer userId = SessionUtil.getUserId();
		List<WCPlanDTO> worldCupPlanList = dlWorldCupPlanService.worldCupPlanList(userId);
		return ResultGenerator.genSuccessResult(null, worldCupPlanList);
	}
}
