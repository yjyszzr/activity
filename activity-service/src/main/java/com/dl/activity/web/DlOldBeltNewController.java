package com.dl.activity.web;

import io.swagger.annotations.ApiOperation;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dl.activity.dto.DlNumAndRewardDTO;
import com.dl.activity.dto.DlShareLinkDTO;
import com.dl.activity.model.DlOldBeltNew;
import com.dl.activity.param.OldBeltNewParam;
import com.dl.activity.param.StrParam;
import com.dl.activity.service.DlOldBeltNewService;
import com.dl.activity.utils.AESUtils;
import com.dl.base.result.BaseResult;
import com.dl.base.result.ResultGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * Created by CodeGenerator on 2018/07/17.
 */
@RestController
@RequestMapping("/dlOldBeltNew")
public class DlOldBeltNewController {
	@Resource
	private DlOldBeltNewService dlOldBeltNewService;

	@ApiOperation(value = "添加新用户", notes = "添加新用户")
	@PostMapping("/add")
	public BaseResult add(@RequestBody OldBeltNewParam oldBeltNewParam) {
		DlOldBeltNew dlOldBeltNew = new DlOldBeltNew();
		dlOldBeltNewService.save(dlOldBeltNew);
		return ResultGenerator.genSuccessResult();
	}

	@ApiOperation(value = "分享链接userId", notes = "分享链接userId")
	@PostMapping("/shareMyLinks")
	public BaseResult<DlShareLinkDTO> shareMyLinks(@RequestBody StrParam strParam) {
		// Integer userId = SessionUtil.getUserId();
		Integer userId = 400387;
		DlShareLinkDTO shareLink = new DlShareLinkDTO();
		String ecodeUserId = AESUtils.ecodes(userId.toString(), AESUtils.INVITING_SECRET_KEY);
		shareLink.setUserld(ecodeUserId);
		return ResultGenerator.genSuccessResult(null, shareLink);
	}

	@PostMapping("/invitationNumAndReward")
	public BaseResult<DlNumAndRewardDTO> invitationNumAndReward(@RequestBody StrParam strParam) {
		// Integer userId = SessionUtil.getUserId();
		Integer userId = 400387;
		List<DlOldBeltNew> dlOldBeltNewList = dlOldBeltNewService.finInvitationsByUserId(userId);
		Integer rewardAmount = 0;
		Integer invitationNum = 0;
		for (int i = 0; i < dlOldBeltNewList.size(); i++) {
			if (dlOldBeltNewList.get(i).getConsumptionStatus() == 1) {
				rewardAmount += 20;
				invitationNum++;
			}
		}
		if (invitationNum >= 10 && invitationNum < 20) {
			rewardAmount += 15;
		} else if (invitationNum >= 20 && invitationNum < 30) {
			rewardAmount += 30;
		} else if (invitationNum >= 30 && invitationNum < 40) {
			rewardAmount += 50;
		} else if (invitationNum >= 40 && invitationNum < 50) {
			rewardAmount += 70;
		} else if (invitationNum >= 50 && invitationNum < 100) {
			rewardAmount += 100;
		} else if (invitationNum >= 100) {
			rewardAmount += 200;
		}
		DlNumAndRewardDTO numAndReward = new DlNumAndRewardDTO();
		numAndReward.setInvitationNum(invitationNum);
		numAndReward.setReward(rewardAmount);
		return ResultGenerator.genSuccessResult(null, numAndReward);
	}

	@PostMapping("/delete")
	public BaseResult delete(@RequestParam Integer id) {
		dlOldBeltNewService.deleteById(id);
		return ResultGenerator.genSuccessResult();
	}

	@PostMapping("/update")
	public BaseResult update(DlOldBeltNew dlOldBeltNew) {
		dlOldBeltNewService.update(dlOldBeltNew);
		return ResultGenerator.genSuccessResult();
	}

	@PostMapping("/detail")
	public BaseResult detail(@RequestParam Integer id) {
		DlOldBeltNew dlOldBeltNew = dlOldBeltNewService.findById(id);
		return ResultGenerator.genSuccessResult(null, dlOldBeltNew);
	}

	@PostMapping("/list")
	public BaseResult list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
		PageHelper.startPage(page, size);
		List<DlOldBeltNew> list = dlOldBeltNewService.findAll();
		PageInfo pageInfo = new PageInfo(list);
		return ResultGenerator.genSuccessResult(null, pageInfo);
	}
}
