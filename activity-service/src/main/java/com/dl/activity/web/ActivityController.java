package com.dl.activity.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dl.activity.dto.DlNumAndRewardDTO;
import com.dl.activity.model.Activity;
import com.dl.activity.model.DlOldBeltNew;
import com.dl.activity.param.StrParam;
import com.dl.activity.service.ActivityService;
import com.dl.base.result.BaseResult;
import com.dl.base.result.ResultGenerator;
import com.dl.base.util.DateUtil;
import com.dl.base.util.SessionUtil;
import com.dl.member.api.IUserService;
import com.dl.member.dto.UserDTO;
import com.dl.member.enums.MemberEnums;
import com.dl.member.param.UserIdParam;

import io.swagger.annotations.ApiOperation;

/**
 * Created by CodeGenerator on 2018/07/17.
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {
	@Resource
	private ActivityService activityService;
	@Resource
	private IUserService userService;

	@Resource
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 邀请人数和奖励
	 * 
	 * @param strParam
	 * @return
	 */
	@ApiOperation(value = "邀请人数和奖励", notes = "邀请人数和奖励")
	@PostMapping("/invitationNumAndReward")
	public BaseResult<String> invitationNumAndReward(@RequestBody StrParam strParam) {
		Integer userId = SessionUtil.getUserId();
		//推广活动流程begin
		//1.查询推广活动是否有效
		Activity activity = activityService.queryActivity(2);//参数2是伯乐奖
		if(activity==null) {//无效
			return ResultGenerator.genResult(MemberEnums.ACTIVITY_NOT_VALID.getcode(), MemberEnums.ACTIVITY_NOT_VALID.getMsg());
		}
		//2.查询当前用户奖励是否已经领取过（生效）
		UserIdParam userIdParam = new UserIdParam();
		userIdParam.setUserId(userId);
		BaseResult<UserDTO> buserDto = userService.queryUserInfo(userIdParam);
		if(buserDto!=null && buserDto.getData()!=null) {
			UserDTO userDto = buserDto.getData();
			if(userDto.getIsStatus()==1) {//生效
			} else { //未生效
				//3.判断此次充值时间是否在活动期间 在活动期间则修改用用户信息表未生效
				int currentTime = DateUtil.getCurrentTimeLong();
				if(Integer.valueOf(activity.getStart_time())<=currentTime && Integer.valueOf(activity.getEnd_time())>=currentTime) {
					//修改用户信息表为生效
					
				}
				//4.记录推广活动收益流水
				
				//5.维护推广活动用户信息
				
			}
		}else {
			return ResultGenerator.genFailResult("用户不存在！");
		}
		//推广活动流程end
		Integer rewardAmount = 0;
		Integer invitationNum = 0;
		DlNumAndRewardDTO numAndReward = new DlNumAndRewardDTO();
		if (userId != null) {
			List<DlOldBeltNew> dlOldBeltNewList = dlOldBeltNewService.finInvitationsByUserId(userId);
			for (int i = 0; i < dlOldBeltNewList.size(); i++) {
				if (dlOldBeltNewList.get(i).getConsumptionStatus() == 2) {
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
			numAndReward.setInvitationNum(invitationNum.toString());
			numAndReward.setReward(rewardAmount.toString());
		} else {
			numAndReward.setInvitationNum("-");
			numAndReward.setReward("-");
		}

		return ResultGenerator.genSuccessResult(null, numAndReward);
	}
}
