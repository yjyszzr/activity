package com.dl.activity.web;

import io.swagger.annotations.ApiOperation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dl.activity.dto.DlWorldCupContryDTO;
import com.dl.activity.dto.GuessingCompetitionDTO;
import com.dl.activity.dto.SixteenGroupFourDTO;
import com.dl.activity.dto.SixteenGroupSixteenDTO;
import com.dl.activity.dto.SixteenGroupTwoDTO;
import com.dl.activity.dto.WCPlanDTO;
import com.dl.activity.enums.ActivityEnums;
import com.dl.activity.model.DlWorldCupContry;
import com.dl.activity.model.DlWorldCupPlan;
import com.dl.activity.param.EmptyParam;
import com.dl.activity.param.PlanStrParam;
import com.dl.activity.param.StrParam;
import com.dl.activity.service.DlWorldCupContryService;
import com.dl.activity.service.DlWorldCupPlanService;
import com.dl.base.result.BaseResult;
import com.dl.base.result.ResultGenerator;
import com.dl.base.util.SessionUtil;

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
		// Integer currentTimeInt = DateUtilNew.getCurrentTimeLong();
		Integer currentTimeInt = 1530201902;
		// Integer userId = 400253;
		Integer userId = SessionUtil.getUserId();
		BigDecimal amount = dlWorldCupPlanService.findAllOrderAmount(userId);
		int amountInt = 0;
		if (amount != null) {
			amountInt = amount.intValue();
		}
		// 判断订单购彩金额（每超过二百元可有一次投注机会）
		// int bettingNum = amountInt / 200;
		int bettingNum = 1;
		List<DlWorldCupPlan> worldCupPlanList = dlWorldCupPlanService.findByUserId(userId);
		// 总机会不得超过12次
		if (worldCupPlanList.size() >= 12) {
			bettingNum = 0;
		}
		GuessingCompetitionDTO competition = new GuessingCompetitionDTO();
		// a、第一阶段竞猜期：活动开始至6月25日22:00:00；小于 1529935200
		if (currentTimeInt <= 1529935200) {
			competition.setBettingNum(bettingNum);
			if (bettingNum != 0) {
				competition.setJumpStatus(1);
			} else {
				competition.setJumpStatus(0);
			}
			competition.setDescribetion("活动开始至6月25日22:00:00");
			// b、第二阶段等待期：6月25日22:00:01至6月29日05:00:00； 大于1529935200 小于 1530201900
		} else if (currentTimeInt > 1529935200 && currentTimeInt <= 1530201900) {
			competition.setBettingNum(0);
			competition.setJumpStatus(2);
			competition.setDescribetion("6月25日22:00:00至6月29日05:00:00为等待期");
			// c、第三阶段竞猜期：6月29日05:00:01至7月6日22:00:00。大于1530201900 小于 1530885600
		} else if (currentTimeInt > 1530201900 && currentTimeInt <= 1530885600) {
			competition.setBettingNum(bettingNum);
			competition.setJumpStatus(3);
			List<DlWorldCupContry> worldCupContryList = dlWorldCupContryService.findAll();
			List<DlWorldCupContry> worldCupContry16List = worldCupContryList.stream().filter(s -> StringUtils.isNoneBlank(s.getIs16()) && !s.getIs16().equals("0")).collect(Collectors.toList());
			// List转DTO
			List<DlWorldCupContryDTO> wcContry16List = new ArrayList<DlWorldCupContryDTO>();
			for (int i = 0; i < worldCupContry16List.size(); i++) {
				DlWorldCupContryDTO wcContryDTO = new DlWorldCupContryDTO();
				wcContryDTO.setName(worldCupContry16List.get(i).getContryName());
				wcContryDTO.setIcon(worldCupContry16List.get(i).getContryPic());
				wcContryDTO.setId(worldCupContry16List.get(i).getCountryId().toString());
				wcContryDTO.setSld(worldCupContry16List.get(i).getIs16());
				wcContry16List.add(wcContryDTO);
			}
			// DTO转Map
			Map<String, DlWorldCupContryDTO> worldCupContryMap = new HashMap<String, DlWorldCupContryDTO>(wcContry16List.size());
			wcContry16List.forEach(item -> worldCupContryMap.put(item.getSld(), item));
			// 组装数据
			SixteenGroupSixteenDTO sixteenGroupSixteenDTO = new SixteenGroupSixteenDTO();
			SixteenGroupFourDTO sixteenGroupFourDTO1 = new SixteenGroupFourDTO();
			List<SixteenGroupFourDTO> sixteenGroupFourDTOList = new ArrayList<SixteenGroupFourDTO>();
			List<SixteenGroupTwoDTO> sixteenGroupTwoList1 = new ArrayList<SixteenGroupTwoDTO>();
			SixteenGroupTwoDTO sixteenGroupTwoDTO1 = new SixteenGroupTwoDTO();
			List<DlWorldCupContryDTO> wcContryList1 = new ArrayList<DlWorldCupContryDTO>();
			wcContryList1.add(worldCupContryMap.get("A1"));
			wcContryList1.add(worldCupContryMap.get("B2"));
			sixteenGroupTwoDTO1.setWcContryList(wcContryList1);
			sixteenGroupTwoList1.add(sixteenGroupTwoDTO1);
			SixteenGroupTwoDTO sixteenGroupTwoDTO2 = new SixteenGroupTwoDTO();
			List<DlWorldCupContryDTO> wcContryList2 = new ArrayList<DlWorldCupContryDTO>();
			wcContryList2.add(worldCupContryMap.get("C1"));
			wcContryList2.add(worldCupContryMap.get("D2"));
			sixteenGroupTwoDTO2.setWcContryList(wcContryList2);
			sixteenGroupTwoList1.add(sixteenGroupTwoDTO2);
			SixteenGroupTwoDTO sixteenGroupTwoDTO3 = new SixteenGroupTwoDTO();
			List<DlWorldCupContryDTO> wcContryList3 = new ArrayList<DlWorldCupContryDTO>();
			wcContryList3.add(worldCupContryMap.get("E1"));
			wcContryList3.add(worldCupContryMap.get("F2"));
			sixteenGroupTwoDTO3.setWcContryList(wcContryList3);
			sixteenGroupTwoList1.add(sixteenGroupTwoDTO3);
			SixteenGroupTwoDTO sixteenGroupTwoDTO4 = new SixteenGroupTwoDTO();
			List<DlWorldCupContryDTO> wcContryList4 = new ArrayList<DlWorldCupContryDTO>();
			wcContryList4.add(worldCupContryMap.get("G1"));
			wcContryList4.add(worldCupContryMap.get("H2"));
			sixteenGroupTwoDTO4.setWcContryList(wcContryList4);
			sixteenGroupTwoList1.add(sixteenGroupTwoDTO4);
			sixteenGroupFourDTO1.setSixteenGroupTwoList(sixteenGroupTwoList1);
			sixteenGroupFourDTOList.add(sixteenGroupFourDTO1);
			SixteenGroupFourDTO sixteenGroupFourDTO2 = new SixteenGroupFourDTO();
			List<SixteenGroupTwoDTO> sixteenGroupTwoList2 = new ArrayList<SixteenGroupTwoDTO>();
			SixteenGroupTwoDTO sixteenGroupTwoDTO5 = new SixteenGroupTwoDTO();
			List<DlWorldCupContryDTO> wcContryList5 = new ArrayList<DlWorldCupContryDTO>();
			wcContryList5.add(worldCupContryMap.get("B1"));
			wcContryList5.add(worldCupContryMap.get("A2"));
			sixteenGroupTwoDTO5.setWcContryList(wcContryList5);
			sixteenGroupTwoList2.add(sixteenGroupTwoDTO5);
			SixteenGroupTwoDTO sixteenGroupTwoDTO6 = new SixteenGroupTwoDTO();
			List<DlWorldCupContryDTO> wcContryList6 = new ArrayList<DlWorldCupContryDTO>();
			wcContryList6.add(worldCupContryMap.get("D1"));
			wcContryList6.add(worldCupContryMap.get("C2"));
			sixteenGroupTwoDTO6.setWcContryList(wcContryList6);
			sixteenGroupTwoList2.add(sixteenGroupTwoDTO6);
			SixteenGroupTwoDTO sixteenGroupTwoDTO7 = new SixteenGroupTwoDTO();
			List<DlWorldCupContryDTO> wcContryList7 = new ArrayList<DlWorldCupContryDTO>();
			wcContryList7.add(worldCupContryMap.get("F1"));
			wcContryList7.add(worldCupContryMap.get("E2"));
			sixteenGroupTwoDTO7.setWcContryList(wcContryList7);
			sixteenGroupTwoList2.add(sixteenGroupTwoDTO7);
			SixteenGroupTwoDTO sixteenGroupTwoDTO8 = new SixteenGroupTwoDTO();
			List<DlWorldCupContryDTO> wcContryList8 = new ArrayList<DlWorldCupContryDTO>();
			wcContryList8.add(worldCupContryMap.get("H1"));
			wcContryList8.add(worldCupContryMap.get("G2"));
			sixteenGroupTwoDTO8.setWcContryList(wcContryList8);
			sixteenGroupTwoList2.add(sixteenGroupTwoDTO8);
			sixteenGroupFourDTO2.setSixteenGroupTwoList(sixteenGroupTwoList2);
			sixteenGroupFourDTOList.add(sixteenGroupFourDTO2);
			sixteenGroupSixteenDTO.setSixteenGroupFourList(sixteenGroupFourDTOList);
			// 将16强的数据带过去
			competition.setSixteenGroupSixteen(sixteenGroupSixteenDTO);
			competition.setDescribetion("6月29日05:00:01至7月6日22:00:00为竞猜期");
		} else {
			competition.setBettingNum(0);
			competition.setJumpStatus(0);
		}
		return ResultGenerator.genSuccessResult("", competition);
	}

	@ApiOperation(value = "提交推演方案", notes = "提交推演方案")
	@PostMapping("/add")
	public BaseResult<String> add(@RequestBody PlanStrParam planStrParam) {
		// Integer now = DateUtil.getCurrentTimeLong();
		Integer now = 1530201902;
		Integer userId = SessionUtil.getUserId();
		BigDecimal amount = dlWorldCupPlanService.findAllOrderAmount(userId);
		int amountInt = 0;
		if (amount != null) {
			amountInt = amount.intValue();
		}
		// 判断订单购彩金额（每超过二百元可有一次投注机会）
		int bettingNum = amountInt / 200;
		// int bettingNum = 1;
		List<DlWorldCupPlan> worldCupPlanList = dlWorldCupPlanService.findByUserId(userId);
		// 总机会不得超过12次
		if (worldCupPlanList.size() >= 12) {
			bettingNum = 0;
		}

		Integer leftNum = bettingNum - worldCupPlanList.size();
		if (leftNum < 1) {
			return ResultGenerator.genResult(ActivityEnums.WORLD_CUP_JUDGE_NOT_CERT.getCode(), ActivityEnums.WORLD_CUP_JUDGE_NOT_CERT.getMsg());
		}

		String palnStr = planStrParam.getPlanStrParam();
		if (now > 1529935200 && now < 1530201900) {// b、第二阶段等待期：6月25日22:00:01至6月29日05:00:00；
			return ResultGenerator.genResult(ActivityEnums.WORLD_CUP_JUDGE_WARN.getCode(), "16强比赛正在进行中,6月29日05:00:01 之后可以参与推演8强比赛");
		} else if (now > 1530201900 && now < 1530885600 && palnStr.contains("16|")) {
			return ResultGenerator.genResult(ActivityEnums.WORLD_CUP_JUDGE_WARN.getCode(), "不能提交16强数据");
		} else if (now > 1530885600) {// c、第二阶段竞猜期：6月29日05:00:01至7月6日22:00:00
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
