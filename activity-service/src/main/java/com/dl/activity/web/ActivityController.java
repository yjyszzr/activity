package com.dl.activity.web;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dl.activity.enums.ActivityAccountEnums;
import com.dl.activity.model.Activity;
import com.dl.activity.model.ActivityAccount;
import com.dl.activity.model.ActivityUserInfo;
import com.dl.activity.param.StrParam;
import com.dl.activity.service.ActivityAccountService;
import com.dl.activity.service.ActivityService;
import com.dl.activity.service.ActivityUserInfoService;
import com.dl.base.result.BaseResult;
import com.dl.base.result.ResultGenerator;
import com.dl.base.util.DateUtil;
import com.dl.base.util.SessionUtil;
import com.dl.member.api.IUserAccountService;
import com.dl.member.api.IUserService;
import com.dl.member.dto.UserDTO;
import com.dl.member.enums.MemberEnums;
import com.dl.member.param.RecharegeParam;
import com.dl.member.param.UserIdParam;
import com.github.pagehelper.util.StringUtil;

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
	private ActivityUserInfoService activityUserInfoService;
	
	@Resource
	private ActivityAccountService activityAccountService;
	
	@Resource
	private IUserAccountService userAccountService;
	
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
		if(activity==null) {//没有伯乐活动
			return ResultGenerator.genResult(MemberEnums.ACTIVITY_NOT_VALID.getcode(), MemberEnums.ACTIVITY_NOT_VALID.getMsg());
		}
		//2.判断此次充值时间是否在活动期间 在活动期间则修改用用户信息表未生效
		int currentTime = DateUtil.getCurrentTimeLong();
		if(Integer.valueOf(activity.getStart_time())<=currentTime && Integer.valueOf(activity.getEnd_time())>=currentTime) {
			//3.查询当前是否二级用户，奖励是否已经领取过（生效）
			UserIdParam userIdParam = new UserIdParam();
			userIdParam.setUserId(userId);
			BaseResult<UserDTO> buserDto = userService.queryUserInfo(userIdParam);
			if(buserDto!=null && buserDto.getData()!=null) {
				UserDTO userDto = buserDto.getData();
				if(userDto.getIsStatus()!=1 && userDto.getParentUserId()!=null && !"".equals(userDto.getParentUserId().toString())) {//是二级用户且奖励未生效
					//4修改用户信息表为生效
					userService.updateUserInfoByUserId(userIdParam);
					//5.维护推广活动用户信息
					double reward = Double.parseDouble(StringUtil.isNotEmpty(activity.getReward_money())?activity.getReward_money():"0");//每邀请一人奖励金额
						//5.1获取当前用户的邀请人信息
					ActivityUserInfo activityUserInfo = activityUserInfoService.getUserInfoByUserId(userDto.getParentUserId());
					if(activityUserInfo!=null) {//有数据则修改
						activityUserInfo.setInvitation_number(activityUserInfo.getInvitation_number()==null?1:activityUserInfo.getInvitation_number()+1);
						activityUserInfo.setInvitation_number_reward((StringUtil.isNotEmpty(activityUserInfo.getInvitation_number_reward())?(Double.parseDouble(activityUserInfo.getInvitation_number_reward())+reward):reward)+"");
						activityUserInfo.setHistory_invitation_number(activityUserInfo.getHistory_invitation_number()==null?1:activityUserInfo.getHistory_invitation_number()+1);
						activityUserInfo.setHistory_invitation_number_reward((StringUtil.isNotEmpty(activityUserInfo.getHistory_invitation_number_reward())?(Double.parseDouble(activityUserInfo.getHistory_invitation_number_reward())+reward):reward)+"");
						activityUserInfo.setWithdrawable_reward((StringUtil.isNotEmpty(activityUserInfo.getWithdrawable_reward())?(Double.parseDouble(activityUserInfo.getWithdrawable_reward())+reward):reward)+"");
						activityUserInfo.setHistory_total_withdrawable_reward((StringUtil.isNotEmpty(activityUserInfo.getHistory_total_withdrawable_reward())?(Double.parseDouble(activityUserInfo.getHistory_total_withdrawable_reward())+reward):reward)+"");
						activityUserInfoService.updateActivityUserInfoByParentId(activityUserInfo);
					}else {//没有邀请人信息  插入
						activityUserInfo = new ActivityUserInfo();
						activityUserInfo.setUser_id(userDto.getParentUserId());
						userIdParam.setUserId(userDto.getParentUserId());
						BaseResult<UserDTO> parentbuser = userService.queryUserInfo(userIdParam);
						if(parentbuser!=null && parentbuser.getData()!=null) {
							activityUserInfo.setMobile(parentbuser.getData().getMobile());
						}
						activityUserInfo.setInvitation_number(1);
						activityUserInfo.setInvitation_number_reward(reward+"");
						activityUserInfo.setHistory_invitation_number(1);
						activityUserInfo.setHistory_invitation_number_reward(reward+"");
						activityUserInfo.setWithdrawable_reward(reward+"");
						activityUserInfo.setHistory_total_withdrawable_reward(reward+"");
						activityUserInfo.setMonth_return_reward("0");
						activityUserInfo.setHistory_total_return_reward("0");
						activityUserInfo.setInvitation_add_reward("0");
						activityUserInfoService.insertActivityUserInfo(activityUserInfo);
					}
					//6.记录推广活动收益流水
					ActivityAccount account = new ActivityAccount();
					account.setUser_id(userDto.getParentUserId());
					account.setMobile(userDto.getMobile());
					account.setAdd_time(currentTime);
					account.setReward_money(reward+"");
					account.setType(ActivityAccountEnums.TYPE_1.getCode());
					activityAccountService.insertActivityAccount(account);
				}
			}else {
				return ResultGenerator.genFailResult("用户不存在！");
			}
		}
		return ResultGenerator.genSuccessResult("succ", "success");
	}
	
	/**
	 * 累计消费返利
	 * 
	 * @param strParam
	 * @return
	 */
	@ApiOperation(value = "购彩返利", notes = "购彩返利")
	@PostMapping("/buyLotteryRerurnReward")
	public BaseResult<String> buyLotteryRerurnReward(@RequestBody StrParam strParam) {
		Integer userId = SessionUtil.getUserId();
		//推广活动流程begin
		//1.查询推广活动是否有效
		Activity activity = activityService.queryActivity(3);//参数3是荣耀奖
		if(activity==null) {//没有活动
			return ResultGenerator.genResult(MemberEnums.ACTIVITY_NOT_VALID.getcode(), MemberEnums.ACTIVITY_NOT_VALID.getMsg());
		}
		//2.判断此次购彩时间是否在活动期间 在活动期间则修改用用户信息表未生效
		int currentTime = DateUtil.getCurrentTimeLong();
		if(Integer.valueOf(activity.getStart_time())<=currentTime && Integer.valueOf(activity.getEnd_time())>=currentTime) {
			//3.查询当前是否二级用户，奖励是否已经领取过（生效）
			UserIdParam userIdParam = new UserIdParam();
			userIdParam.setUserId(userId);
			BaseResult<UserDTO> buserDto = userService.queryUserInfo(userIdParam);
			if(buserDto!=null && buserDto.getData()!=null) {
				UserDTO userDto = buserDto.getData();
				if(userDto.getIsStatus()==1 && userDto.getParentUserId()!=null && !"".equals(userDto.getParentUserId().toString())) {//是二级用户且已经充值103
					//4.维护推广活动用户信息
					double buyMoney = Double.parseDouble(StringUtil.isNotEmpty(strParam.getStr())?strParam.getStr():"0");//购彩金额
					int returnBl = activity.getNumber()/100;//购彩返利百分比
					double reward = 0;
					if(buyMoney>0) {
						reward = buyMoney*returnBl/100;
					}
						//4.1获取当前用户的邀请人信息
					ActivityUserInfo activityUserInfo = activityUserInfoService.getUserInfoByUserId(userDto.getParentUserId());
					if(activityUserInfo!=null) {//有数据则修改
						activityUserInfo.setMonth_return_reward((StringUtil.isNotEmpty(activityUserInfo.getMonth_return_reward())?(Double.parseDouble(activityUserInfo.getMonth_return_reward())+reward):reward)+"");
						activityUserInfo.setHistory_total_return_reward((StringUtil.isNotEmpty(activityUserInfo.getHistory_total_return_reward())?(Double.parseDouble(activityUserInfo.getHistory_total_return_reward())+reward):reward)+"");
						activityUserInfo.setWithdrawable_reward((StringUtil.isNotEmpty(activityUserInfo.getWithdrawable_reward())?(Double.parseDouble(activityUserInfo.getWithdrawable_reward())+reward):reward)+"");
						activityUserInfo.setHistory_total_withdrawable_reward((StringUtil.isNotEmpty(activityUserInfo.getHistory_total_withdrawable_reward())?(Double.parseDouble(activityUserInfo.getHistory_total_withdrawable_reward())+reward):reward)+"");
						activityUserInfo.setInvitation_add_reward((StringUtil.isNotEmpty(activityUserInfo.getInvitation_add_reward())?(Double.parseDouble(activityUserInfo.getInvitation_add_reward())+buyMoney):buyMoney)+"");
						activityUserInfoService.updateActivityUserInfoByParentId(activityUserInfo);
						
						//5.记录推广活动收益流水
						ActivityAccount account = new ActivityAccount();
						account.setUser_id(userDto.getParentUserId());
						account.setMobile(userDto.getMobile());
						account.setAdd_time(currentTime);
						account.setReward_money(reward+"");
						account.setType(ActivityAccountEnums.TYPE_3.getCode());
						activityAccountService.insertActivityAccount(account);
					}
				}
			}else {
				return ResultGenerator.genFailResult("用户不存在！");
			}
		}
		return ResultGenerator.genSuccessResult("succ", "success");
	}
	
	/**
	 * 提取收益  收益转现
	 * 
	 * @param strParam
	 * @return
	 */
	@ApiOperation(value = "提取收益", notes = "提取收益")
	@PostMapping("/rewardToMoney")
	public BaseResult<String> rewardToMoney(@RequestBody StrParam strParam) {
		Integer userId = SessionUtil.getUserId();
		int currentTime = DateUtil.getCurrentTimeLong();
		UserIdParam userIdParam = new UserIdParam();
		userIdParam.setUserId(userId);
		BaseResult<UserDTO> buserDto = userService.queryUserInfo(userIdParam);
		if(buserDto!=null && buserDto.getData()!=null) {
			UserDTO userDto = buserDto.getData();
			//1获取邀请人信息
			double reward = Double.parseDouble(StringUtil.isNotEmpty(strParam.getStr())?strParam.getStr():"0");//提取金额
			ActivityUserInfo activityUserInfo = activityUserInfoService.getUserInfoByUserId(userDto.getParentUserId());
			if(activityUserInfo!=null) {//有数据则修改
				double withdrawable_reward = StringUtil.isNotEmpty(activityUserInfo.getWithdrawable_reward())?Double.parseDouble(activityUserInfo.getWithdrawable_reward()):0;
				if(withdrawable_reward>=reward) {//可提取金额大于等于提取金额
					activityUserInfo.setWithdrawable_reward((withdrawable_reward-reward)+"");
					activityUserInfoService.updateActivityUserInfoByParentId(activityUserInfo);
					//5.记录推广活动收益流水
					ActivityAccount account = new ActivityAccount();
					account.setUser_id(userId);
					account.setMobile(userDto.getMobile());
					account.setAdd_time(currentTime);
					account.setReward_money(reward+"");
					account.setType(ActivityAccountEnums.TYPE_7.getCode());
					activityAccountService.insertActivityAccount(account);
					
					//更改用户余额并记录账户流水
					RecharegeParam recharegeParam = new RecharegeParam();
					recharegeParam.setAmount(BigDecimal.valueOf(reward));
					recharegeParam.setUserId(userId);
					recharegeParam.setPayId("8");
					recharegeParam.setGiveAmount("0");
					recharegeParam.setOrderSn(userId+""+currentTime);
					userAccountService.activityRewardUserMoney(recharegeParam);
				}
			}
		}else {
			return ResultGenerator.genFailResult("用户不存在！");
		}
		return ResultGenerator.genSuccessResult("succ", "success");
	}
	
	
}