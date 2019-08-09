package com.dl.activity.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dl.activity.dto.ActivityDTO;
import com.dl.activity.enums.ActivityAccountEnums;
import com.dl.activity.model.Activity;
import com.dl.activity.model.ActivityAccount;
import com.dl.activity.model.ActivityConfig;
import com.dl.activity.model.ActivityTgDTO;
import com.dl.activity.model.ActivityUserInfo;
import com.dl.activity.param.ActTypeParam;
import com.dl.activity.param.GearHasReceivedParam;
import com.dl.activity.param.StrParam;
import com.dl.activity.service.ActivityAccountService;
import com.dl.activity.service.ActivityConfigReceiveService;
import com.dl.activity.service.ActivityConfigService;
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
	private ActivityConfigService activityConfigService;

	@Resource
	private ActivityConfigReceiveService activityConfigReceiveService;
	
	@Resource
	private ActivityUserInfoService activityUserInfoService;

	@Resource
	private ActivityAccountService activityAccountService;

	@Resource
	private IUserAccountService userAccountService;

	@Resource
	private IUserService iuserService;

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
		Activity activity = activityService.queryActivity(3);//参数3是伯乐奖
		if(activity==null) {//没有伯乐活动
			return ResultGenerator.genResult(MemberEnums.ACTIVITY_NOT_VALID.getcode(), MemberEnums.ACTIVITY_NOT_VALID.getMsg());
		}
		//2.判断此次充值时间是否在活动期间 在活动期间则修改用用户信息表未生效
		int currentTime = DateUtil.getCurrentTimeLong();
		if(Integer.valueOf(activity.getStart_time())<=currentTime && Integer.valueOf(activity.getEnd_time())>=currentTime) {
			//3.查询当前是否二级用户，奖励是否已经领取过（生效）
			UserIdParam userIdParam = new UserIdParam();
			userIdParam.setUserId(userId);
			BaseResult<UserDTO> buserDto = iuserService.queryUserInfo(userIdParam);
			if(buserDto!=null && buserDto.getData()!=null) {
				UserDTO userDto = buserDto.getData();
				if(userDto.getIsStatus()!=1 && userDto.getParentUserId()!=null && !"".equals(userDto.getParentUserId().toString())) {//是二级用户且奖励未生效
					//4修改用户信息表为生效
					iuserService.updateUserInfoByUserId(userIdParam);
					//5.维护推广活动用户信息
					double reward = activity.getReward_money()!=null?activity.getReward_money():0;//每邀请一人奖励金额
						//5.1获取当前用户的邀请人信息
					ActivityUserInfo activityUserInfo = activityUserInfoService.getUserInfoByUserId(userDto.getParentUserId());
					if(activityUserInfo!=null) {//有数据则修改
						activityUserInfo.setInvitation_number(activityUserInfo.getInvitation_number()==null?1:activityUserInfo.getInvitation_number()+1);
						activityUserInfo.setInvitation_number_reward((activityUserInfo.getInvitation_number_reward()!=null?(activityUserInfo.getInvitation_number_reward()+reward):reward));
						activityUserInfo.setHistory_invitation_number(activityUserInfo.getHistory_invitation_number()==null?1:activityUserInfo.getHistory_invitation_number()+1);
						activityUserInfo.setHistory_invitation_number_reward((activityUserInfo.getHistory_invitation_number_reward()!=null?(activityUserInfo.getHistory_invitation_number_reward()+reward):reward));
						activityUserInfo.setWithdrawable_reward((activityUserInfo.getWithdrawable_reward()!=null?(activityUserInfo.getWithdrawable_reward()+reward):reward));
						activityUserInfo.setHistory_total_withdrawable_reward((activityUserInfo.getHistory_total_withdrawable_reward()!=null?(activityUserInfo.getHistory_total_withdrawable_reward()+reward):reward));
						activityUserInfoService.updateActivityUserInfoByParentId(activityUserInfo);
					}else {//没有邀请人信息  插入
						activityUserInfo = new ActivityUserInfo();
						activityUserInfo.setUser_id(userDto.getParentUserId());
						userIdParam.setUserId(userDto.getParentUserId());
						BaseResult<UserDTO> parentbuser = iuserService.queryUserInfo(userIdParam);
						if(parentbuser!=null && parentbuser.getData()!=null) {
							activityUserInfo.setMobile(parentbuser.getData().getMobile());
						}
						double zero = 0;
						activityUserInfo.setInvitation_number(1);
						activityUserInfo.setInvitation_number_reward(reward);
						activityUserInfo.setHistory_invitation_number(1);
						activityUserInfo.setHistory_invitation_number_reward(reward);
						activityUserInfo.setWithdrawable_reward(reward);
						activityUserInfo.setHistory_total_withdrawable_reward(reward);
						activityUserInfo.setMonth_return_reward(zero);
						activityUserInfo.setHistory_total_return_reward(zero);
						activityUserInfo.setInvitation_add_reward(zero);
						activityUserInfoService.insertActivityUserInfo(activityUserInfo);
					}
					//6.记录推广活动收益流水
					ActivityAccount account = new ActivityAccount();
					account.setUser_id(userDto.getParentUserId());
					account.setMobile(userDto.getMobile());
					account.setAdd_time(currentTime);
					account.setReward_money(reward);
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
		Activity activity = activityService.queryActivity(4);//参数4是荣耀奖
		if(activity==null) {//没有活动
			return ResultGenerator.genResult(MemberEnums.ACTIVITY_NOT_VALID.getcode(), MemberEnums.ACTIVITY_NOT_VALID.getMsg());
		}
		//2.判断此次购彩时间是否在活动期间 在活动期间则修改用用户信息表未生效
		int currentTime = DateUtil.getCurrentTimeLong();
		if(Integer.valueOf(activity.getStart_time())<=currentTime && Integer.valueOf(activity.getEnd_time())>=currentTime) {
			//3.查询当前是否二级用户，奖励是否已经领取过（生效）
			UserIdParam userIdParam = new UserIdParam();
			userIdParam.setUserId(userId);
			BaseResult<UserDTO> buserDto = iuserService.queryUserInfo(userIdParam);
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
						activityUserInfo.setMonth_return_reward((activityUserInfo.getMonth_return_reward()!=null?(activityUserInfo.getMonth_return_reward()+reward):reward));
						activityUserInfo.setHistory_total_return_reward((activityUserInfo.getHistory_total_return_reward()!=null?(activityUserInfo.getHistory_total_return_reward()+reward):reward));
						activityUserInfo.setWithdrawable_reward((activityUserInfo.getWithdrawable_reward()!=null?(activityUserInfo.getWithdrawable_reward()+reward):reward));
						activityUserInfo.setHistory_total_withdrawable_reward((activityUserInfo.getHistory_total_withdrawable_reward()!=null?(activityUserInfo.getHistory_total_withdrawable_reward()+reward):reward));
						activityUserInfo.setInvitation_add_reward((activityUserInfo.getInvitation_add_reward()!=null?(activityUserInfo.getInvitation_add_reward()+buyMoney):buyMoney));
						activityUserInfoService.updateActivityUserInfoByParentId(activityUserInfo);

						//5.记录推广活动收益流水
						ActivityAccount account = new ActivityAccount();
						account.setUser_id(userDto.getParentUserId());
						account.setMobile(userDto.getMobile());
						account.setAdd_time(currentTime);
						account.setReward_money(reward);
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
		BaseResult<UserDTO> buserDto = iuserService.queryUserInfo(userIdParam);
		if(buserDto!=null && buserDto.getData()!=null) {
			UserDTO userDto = buserDto.getData();
			//1获取邀请人信息
//			double reward = Double.parseDouble(StringUtil.isNotEmpty(strParam.getStr())?strParam.getStr():"0");//提取金额
			ActivityUserInfo activityUserInfo = activityUserInfoService.getUserInfoByUserId(userId);
			if(activityUserInfo!=null) {//有数据则修改
				double withdrawable_reward = activityUserInfo.getWithdrawable_reward()==null?0:activityUserInfo.getWithdrawable_reward();
//				if(withdrawable_reward>=reward) {//可提取金额大于等于提取金额
					activityUserInfo.setWithdrawable_reward(0d);
					activityUserInfoService.updateActivityUserInfoByParentId(activityUserInfo);
					//5.记录推广活动收益流水
					ActivityAccount account = new ActivityAccount();
					account.setUser_id(userId);
					account.setMobile(userDto.getMobile());
					account.setAdd_time(currentTime);
					account.setReward_money(withdrawable_reward);
					account.setType(ActivityAccountEnums.TYPE_7.getCode());
					activityAccountService.insertActivityAccount(account);

					//更改用户余额并记录账户流水
					RecharegeParam recharegeParam = new RecharegeParam();
					recharegeParam.setAmount(BigDecimal.valueOf(withdrawable_reward));
					recharegeParam.setUserId(userId);
					recharegeParam.setPayId("8");
					recharegeParam.setGiveAmount("0");
					recharegeParam.setOrderSn(userId+""+currentTime);
					userAccountService.activityRewardUserMoney(recharegeParam);
//				}
			}
		}else {
			return ResultGenerator.genFailResult("用户不存在！");
		}
		return ResultGenerator.genSuccessResult("succ", "转入可提现账户成功！");
	}

	/**
	 * 获取推广页面信息
	 *
	 * @param strParam
	 * @return
	 */
	@ApiOperation(value = "推广活动数据展示", notes = "推广活动数据展示")
	@PostMapping("/showActivityByTg")
	public BaseResult<ActivityTgDTO> showActivityByTg(@RequestBody StrParam strParam) {
		Integer userId = SessionUtil.getUserId();
		UserIdParam userIdParam = new UserIdParam();
		userIdParam.setUserId(userId);
		BaseResult<UserDTO> buserDto = iuserService.queryUserInfo(userIdParam);
		ActivityTgDTO tgdto = null;
		if(buserDto!=null && buserDto.getData()!=null) {
			tgdto = new ActivityTgDTO();
			//1获取邀请人信息
			ActivityUserInfo activityUserInfo = activityUserInfoService.getUserInfoByUserId(userId);
			if(activityUserInfo==null) { //若为空
				activityUserInfo = new ActivityUserInfo();
				activityUserInfo.setUser_id(userId);
				activityUserInfo.setMobile(buserDto.getData().getMobile());
				double zero = 0;
				activityUserInfo.setInvitation_number(0);
				activityUserInfo.setInvitation_number_reward(zero);
				activityUserInfo.setHistory_invitation_number(0);
				activityUserInfo.setHistory_invitation_number_reward(zero);
				activityUserInfo.setWithdrawable_reward(zero);
				activityUserInfo.setHistory_total_withdrawable_reward(zero);
				activityUserInfo.setMonth_return_reward(zero);
				activityUserInfo.setHistory_total_return_reward(zero);
				activityUserInfo.setInvitation_add_reward(zero);
				activityUserInfoService.insertActivityUserInfo(activityUserInfo);
			}
			//2推广活动
			Activity activity = activityService.queryActivity(3);//参数3是伯乐奖
			tgdto.getActivity().add(activity);
			tgdto.setActivityUserInfo(activityUserInfo);
			if(activity==null) {//没有伯乐活动
//				return ResultGenerator.genResult(MemberEnums.ACTIVITY_NOT_VALID.getcode(), MemberEnums.ACTIVITY_NOT_VALID.getMsg());
			}else {
				List<ActivityConfig> acitvityBlCon = activityConfigService.queryGearListByActId(activity.getAct_id());
				List<ActivityConfig> acitvityBl = new ArrayList<>();
				for (ActivityConfig activityConfig : acitvityBlCon) {
					GearHasReceivedParam gp = new GearHasReceivedParam();
					gp.setAct_id(activity.getAct_id());
					gp.setUser_id(userId);
					gp.setConfig_id(activityConfig.getId());
					gp.setGear_position(activityConfig.getGear_position());
					gp.setAct_start_time(activity.getStart_time());
					gp.setAct_end_time(activity.getEnd_time());
					if(activityConfigReceiveService.hasReceivedGear(gp)) {
						activityConfig.setIs_status(1);
					}else {
						activityConfig.setIs_status(0);
					}
					acitvityBl.add(activityConfig);
				}
				tgdto.setAcitvityBl(acitvityBl);
			}
			Activity activity3= activityService.queryActivity(4);//参数4是荣耀奖
			tgdto.getActivity().add(activity3);
			if(activity3==null) {//没有伯乐活动
//				return ResultGenerator.genResult(MemberEnums.ACTIVITY_NOT_VALID.getcode(), MemberEnums.ACTIVITY_NOT_VALID.getMsg());
			}else {
				List<ActivityConfig> acitvityRyCon = activityConfigService.queryGearListByActId(activity3.getAct_id());
				List<ActivityConfig> acitvityRy = new ArrayList<>();
				for (ActivityConfig activityConfig : acitvityRyCon) {
					GearHasReceivedParam gp = new GearHasReceivedParam();
					gp.setAct_id(activity.getAct_id());
					gp.setUser_id(userId);
					gp.setConfig_id(activityConfig.getId());
					gp.setGear_position(activityConfig.getGear_position());
					gp.setAct_start_time(activity.getStart_time());
					gp.setAct_end_time(activity.getEnd_time());
					if(activityConfigReceiveService.hasReceivedGear(gp)) {
						activityConfig.setIs_status(1);
					}else {
						activityConfig.setIs_status(0);
					}
					acitvityRy.add(activityConfig);
				}
				tgdto.setAcitvityRy(acitvityRy);
			}
		}else {
			return ResultGenerator.genFailResult("用户不存在！");
		}
		return ResultGenerator.genSuccessResult("succ", tgdto);
	}


    @ApiOperation(value = "根据活动类型查询活动集合信息", notes = "根据活动类型查询活动集合信息")
    @PostMapping("/queryActsByType")
    public BaseResult<ActivityDTO> queryActsByType(@RequestBody ActTypeParam actTypeParam){
        Activity act = activityService.queryActivity(actTypeParam.getActType());
        ActivityDTO actDTO = new ActivityDTO();
        if(act!=null){
            actDTO.setActId(act.getAct_id());
            actDTO.setActType(act.getAct_type());
            actDTO.setIsFinish(act.getStart_time());
            actDTO.setStartTime(act.getStart_time());
            actDTO.setEndTime(act.getEnd_time());
        }
        return ResultGenerator.genSuccessResult("success",actDTO);
    }

    @ApiOperation(value = "获取返利流水", notes = "获取返利流水")
    @PostMapping("/queryResultAccount")
    public BaseResult<Map<String, Object>> queryResultAccount(@RequestBody StrParam strParam){
    	Integer userId = SessionUtil.getUserId();
    	List<ActivityAccount> accountList = activityAccountService.queryAccount(userId);
    	for (ActivityAccount activityAccount : accountList) {
    		if(activityAccount.getType()==1) {
    			activityAccount.setTitle("邀请好友");
    			activityAccount.setRemark("成功邀请"+hideMobile(activityAccount.getMobile())+"用户，奖励您");
    		}else if(activityAccount.getType()==3) {
    			activityAccount.setTitle("好友单笔购彩");
    			activityAccount.setRemark("好友完成购彩，奖励您");
    		}else if(activityAccount.getType()==5) {
    			activityAccount.setTitle("好友累计购彩");
    			activityAccount.setRemark("领取好友累计购彩红包奖励");
    		}else if(activityAccount.getType()==7) {
    			activityAccount.setTitle("提取收益");
    			activityAccount.setRemark("转入可提现账户");
    		}else if(activityAccount.getType()==9) {
    			activityAccount.setTitle("邀请好友");
    			activityAccount.setRemark("领取累计邀请好友红包奖励");
    		}else {
    			activityAccount.setTitle("");
    			activityAccount.setRemark("");
    		}
    		activityAccount.setMobile(hideMobile(activityAccount.getMobile()));
		}
    	if(accountList!=null) {
    		accountList = accountList.stream().filter(dto ->{
    			if(dto.getType()==3) {
    				return true;
    			}else {
    				return false;
    			}
    		}).collect(Collectors.toList());
    	}
    	ActivityUserInfo activityUserInfo = activityUserInfoService.getUserInfoByUserId(userId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accountList", accountList);
		if(activityUserInfo==null) {
			activityUserInfo = new ActivityUserInfo();
			activityUserInfo.setHistory_invitation_number(0);
			activityUserInfo.setHistory_total_return_reward(0d);
		}
		map.put("history_invitation_number", activityUserInfo.getHistory_invitation_number());
		map.put("history_total_return_reward", activityUserInfo.getHistory_total_return_reward());
        return ResultGenerator.genSuccessResult("success",map);
    }
    
    @ApiOperation(value = "获取所有流水", notes = "获取所有流水")
    @PostMapping("/queryAllAccount")
    public BaseResult<Map<String, Object>> queryAllAccount(@RequestBody StrParam strParam){
    	Integer userId = SessionUtil.getUserId();
    	List<ActivityAccount> accountList = activityAccountService.queryAccount(userId);
    	for (ActivityAccount activityAccount : accountList) {
    		if(activityAccount.getType()==1) {
    			activityAccount.setTitle("邀请好友");
    			activityAccount.setRemark("成功邀请"+hideMobile(activityAccount.getMobile())+"用户，奖励您");
    		}else if(activityAccount.getType()==3) {
    			activityAccount.setTitle("好友单笔购彩");
    			activityAccount.setRemark("好友完成购彩，奖励您");
    		}else if(activityAccount.getType()==5) {
    			activityAccount.setTitle("好友累计购彩");
    			activityAccount.setRemark("领取好友累计购彩红包奖励");
    		}else if(activityAccount.getType()==7) {
    			activityAccount.setTitle("提取收益");
    			activityAccount.setRemark("转入可提现账户");
    		}else if(activityAccount.getType()==9) {
    			activityAccount.setTitle("邀请好友");
    			activityAccount.setRemark("领取累计邀请好友红包奖励");
    		}else {
    			activityAccount.setTitle("");
    			activityAccount.setRemark("");
    		}
    		activityAccount.setMobile(hideMobile(activityAccount.getMobile()));
		}
		ActivityUserInfo activityUserInfo = activityUserInfoService.getUserInfoByUserId(userId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accountList", accountList);
		if(activityUserInfo==null) {
			activityUserInfo = new ActivityUserInfo();
			activityUserInfo.setHistory_total_withdrawable_reward(0d);
			activityUserInfo.setWithdrawable_reward(0d);
		}
		map.put("history_total_withdrawable_reward", activityUserInfo.getHistory_total_withdrawable_reward());
		map.put("withdrawable_reward", activityUserInfo.getWithdrawable_reward());
        return ResultGenerator.genSuccessResult("success",map);
    }
    /**
     * 隐藏手机号中间4位数
     * @param mobile
     * @return
     */
    public String hideMobile(String mobile) {
    	return mobile.substring(0,3)+"****"+mobile.substring(6);
    }
}