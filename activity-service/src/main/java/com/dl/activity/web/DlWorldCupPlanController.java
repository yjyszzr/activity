package com.dl.activity.web;

import io.swagger.annotations.ApiOperation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dl.activity.dto.DlWorldCupContryDTO;
import com.dl.activity.dto.GuessingCompetitionDTO;
import com.dl.activity.model.DlWorldCupContry;
import com.dl.activity.model.DlWorldCupPlan;
import com.dl.activity.param.PlanStrParam;
import com.dl.activity.param.StrParam;
import com.dl.activity.service.DlWorldCupContryService;
import com.dl.activity.service.DlWorldCupPlanService;
import com.dl.base.result.BaseResult;
import com.dl.base.result.ResultGenerator;
import com.dl.base.util.DateUtilNew;
import com.dl.base.util.SessionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * Created by CodeGenerator on 2018/06/10.
 */
@RestController
@RequestMapping("/worldCupPlan")
public class DlWorldCupPlanController {
	@Resource
	private DlWorldCupPlanService dlWorldCupPlanService;

	@Resource
	private DlWorldCupContryService dlWorldCupContryService;

	@ApiOperation(value = "世界杯竞猜", notes = "世界杯竞猜")
	@PostMapping("/guessingCompetition")
	public BaseResult<GuessingCompetitionDTO> guessingCompetition(@RequestBody StrParam strParam) {
		Integer currentTimeInt = DateUtilNew.getCurrentTimeLong();
		// a、第一阶段竞猜期：活动开始至6月25日22:00:00；小于 1529935200
		GuessingCompetitionDTO competition = new GuessingCompetitionDTO();
		if (currentTimeInt <= 1529935200) {
			// Integer userId = 400253;
			Integer userId = SessionUtil.getUserId();
			BigDecimal amount = dlWorldCupPlanService.findAllOrderAmount(userId);
			int amountInt = 0;
			if (amount != null) {
				amountInt = amount.intValue();
			}
			// 判断订单购彩金额（每超过二百元可有一次投注机会）
			int bettingNum = amountInt / 200;
			List<DlWorldCupPlan> worldCupPlanList = dlWorldCupPlanService.findByUserId(userId);
			// 总机会不得超过12次
			if (worldCupPlanList.size() >= 12) {
				bettingNum = 0;
			}
			competition.setBettingNum(bettingNum);
			if (bettingNum != 0) {
				competition.setJumpStatus(1);
			} else {
				competition.setJumpStatus(0);
			}
			// b、第二阶段等待期：6月25日22:00:01至6月29日05:00:00； 大于1529935200 小于 1530201900
		} else if (currentTimeInt > 1529935200 || currentTimeInt <= 1530201900) {
			competition.setBettingNum(0);
			competition.setJumpStatus(2);
			// c、第二阶段竞猜期：6月29日05:00:01至7月6日22:00:00。大于1530201900 小于 1530885600
		} else if (currentTimeInt > 1530201900 || currentTimeInt <= 1530885600) {
			competition.setBettingNum(0);
			competition.setJumpStatus(2);
			List<DlWorldCupContry> worldCupContryList = dlWorldCupContryService.findAll();
			List<DlWorldCupContryDTO> worldCupContry16List = new ArrayList<DlWorldCupContryDTO>();
			for (int i = 0; i < worldCupContryList.size(); i++) {
				DlWorldCupContry worldCupContry = worldCupContryList.get(i);
				DlWorldCupContryDTO worldCupContryDTO = new DlWorldCupContryDTO();
				if (worldCupContry.getIs16() == 1) {
					worldCupContryDTO.setContryName(worldCupContryList.get(i).getContryName());
					worldCupContryDTO.setCountryId(worldCupContryList.get(i).getCountryId());
					worldCupContryDTO.setContryPic(worldCupContryList.get(i).getContryPic());
					worldCupContry16List.add(worldCupContryDTO);
				}
			}
			// 将16强的数据带过去
			competition.setWorldCupContryList(worldCupContry16List);
		} else {
			competition.setBettingNum(0);
			competition.setJumpStatus(0);
		}
		return ResultGenerator.genSuccessResult("", competition);
	}

	@PostMapping("/add")
	public BaseResult<String> add(@RequestBody PlanStrParam planStrParam) {
		Integer planId = dlWorldCupPlanService.saveWorldCupPlan(planStrParam.getPlanStrParam());
		return ResultGenerator.genSuccessResult();
	}

	@PostMapping("/delete")
	public BaseResult delete(@RequestParam Integer id) {
		dlWorldCupPlanService.deleteById(id);
		return ResultGenerator.genSuccessResult();
	}

	@PostMapping("/update")
	public BaseResult update(DlWorldCupPlan dlWorldCupPlan) {
		dlWorldCupPlanService.update(dlWorldCupPlan);
		return ResultGenerator.genSuccessResult();
	}

	@PostMapping("/detail")
	public BaseResult detail(@RequestParam Integer id) {
		DlWorldCupPlan dlWorldCupPlan = dlWorldCupPlanService.findById(id);
		return ResultGenerator.genSuccessResult(null, dlWorldCupPlan);
	}

	@PostMapping("/list")
	public BaseResult list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
		PageHelper.startPage(page, size);
		List<DlWorldCupPlan> list = dlWorldCupPlanService.findAll();
		PageInfo pageInfo = new PageInfo(list);
		return ResultGenerator.genSuccessResult(null, pageInfo);
	}
}
