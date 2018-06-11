package com.dl.activity.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dl.activity.dto.DlWorldCupContryDTO;
import com.dl.activity.dto.GuessingCompetitionDTO;
import com.dl.activity.dto.WCPlanDTO;
import com.dl.activity.model.DlWorldCupContry;
import com.dl.activity.model.DlWorldCupPlan;
import com.dl.activity.param.EmptyParam;
import com.dl.activity.param.PlanStrParam;
import com.dl.activity.param.StrParam;
import com.dl.activity.service.DlWorldCupContryService;
import com.dl.activity.service.DlWorldCupPlanService;
import com.dl.base.result.BaseResult;
import com.dl.base.result.ResultGenerator;
import com.dl.base.util.DateUtil;
import com.dl.base.util.DateUtilNew;
import com.dl.base.util.SessionUtil;

import io.swagger.annotations.ApiOperation;
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

	@ApiOperation(value = "提交推演方案", notes = "提交推演方案")
	@PostMapping("/add")
	public BaseResult<String> add(@RequestBody PlanStrParam planStrParam) {
		Integer now = DateUtil.getCurrentTimeLong();
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
		
		Integer leftNum = bettingNum - worldCupPlanList.size();
		if(leftNum < 1) {
			return ResultGenerator.genResult(ActivityEnums.WORLD_CUP_JUDGE_NOT_CERT.getCode(), ActivityEnums.WORLD_CUP_JUDGE_NOT_CERT.getMsg());
		}
		
		String palnStr = planStrParam.getPlanStrParam();
		if(now > 1529935200 && now < 1530201900){// b、第二阶段等待期：6月25日22:00:01至6月29日05:00:00；
			return ResultGenerator.genResult(ActivityEnums.WORLD_CUP_JUDGE_WARN.getCode(), "16强比赛正在进行中,6月29日05:00:01 之后可以参与推演8强比赛");
		}else if(now > 1530201900 && now < 1530885600 && palnStr.contains("16|")) {
			return ResultGenerator.genResult(ActivityEnums.WORLD_CUP_JUDGE_WARN.getCode(), "不能提交16强数据");
		}else if(now > 1530885600){// c、第二阶段竞猜期：6月29日05:00:01至7月6日22:00:00
			return ResultGenerator.genResult(ActivityEnums.WORLD_CUP_JUDGE_WARN.getCode(), "4强比赛不能再推演");
		}
		
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
