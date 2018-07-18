package com.dl.activity.web;

import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.TextUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dl.activity.config.MemberConfig;
import com.dl.activity.core.ProjectConstant;
import com.dl.activity.dto.DlNumAndRewardDTO;
import com.dl.activity.dto.DlShareLinkDTO;
import com.dl.activity.enums.VerificationEnums;
import com.dl.activity.model.DlOldBeltNew;
import com.dl.activity.model.User;
import com.dl.activity.param.ConsumerSmsParam;
import com.dl.activity.param.StrParam;
import com.dl.activity.param.UserRegisterParam;
import com.dl.activity.service.DlOldBeltNewService;
import com.dl.activity.service.SmsService;
import com.dl.activity.service.UserBonusService;
import com.dl.activity.service.UserService;
import com.dl.activity.utils.AESUtils;
import com.dl.base.result.BaseResult;
import com.dl.base.result.ResultGenerator;
import com.dl.base.util.RandomUtil;
import com.dl.base.util.RegexUtil;

/**
 * Created by CodeGenerator on 2018/07/17.
 */
@RestController
@RequestMapping("/dlOldBeltNew")
public class DlOldBeltNewController {
	@Resource
	private DlOldBeltNewService dlOldBeltNewService;

	@Resource
	private UserService userService;

	@Resource
	private UserBonusService userBonusService;

	@Resource
	private MemberConfig memberConfig;

	@Resource
	private SmsService smsService;

	@Resource
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 发送手机验证码
	 * 
	 * @param smsParam
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "发送短信验证码", notes = "发送短信验证码")
	@PostMapping("/sendVerificationCode")
	public BaseResult<String> sendVerificationCode(@RequestBody ConsumerSmsParam smsParam, HttpServletRequest request) {
		String smsType = smsParam.getSmsType();
		String tplId = "";
		String tplValue = "";
		String strRandom4 = RandomUtil.getRandNum(4);
		if (ProjectConstant.VERIFY_TYPE_REG.equals(smsType)) {
			User user = userService.findBy("mobile", smsParam.getMobile());
			if (user != null) {
				if (user.getRegFrom().equals("老带新")) {
					return ResultGenerator.genResult(VerificationEnums.ALREADY_REGISTER_AND_RECEIVE_REWARD.getcode(), VerificationEnums.ALREADY_REGISTER_AND_RECEIVE_REWARD.getMsg());
				}
				return ResultGenerator.genResult(VerificationEnums.ALREADY_REGISTER.getcode(), VerificationEnums.ALREADY_REGISTER.getMsg());
			}
			if (!RegexUtil.checkMobile(smsParam.getMobile())) {
				return ResultGenerator.genResult(VerificationEnums.MOBILE_VALID_ERROR.getcode(), VerificationEnums.MOBILE_VALID_ERROR.getMsg());
			}
			tplId = memberConfig.getREGISTER_TPLID();
			tplValue = "#code#=" + strRandom4;
		}
		if (!TextUtils.isEmpty(tplValue)) {
			BaseResult<String> smsRst = smsService.sendSms(smsParam.getMobile(), tplId, tplValue);
			if (smsRst.getCode() != 0) {
				return ResultGenerator.genFailResult("发送短信验证码失败", smsRst.getData());
			}
			// 缓存验证码
			int expiredTime = ProjectConstant.SMS_REDIS_EXPIRED;
			String key = ProjectConstant.SMS_PREFIX + tplId + "_" + smsParam.getMobile();
			// 短信发送成功执行保存操作
			stringRedisTemplate.opsForValue().set(key, strRandom4, expiredTime, TimeUnit.SECONDS);
			return ResultGenerator.genSuccessResult("发送短信验证码成功", null);
		} else {
			return ResultGenerator.genFailResult("参数异常");
		}
	}

	/**
	 * 新用户注册:
	 * 
	 * @param userRegisterParam
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "新用户注册", notes = "新用户注册")
	@PostMapping("/register")
	public BaseResult register(@RequestBody UserRegisterParam userRegisterParam, HttpServletRequest request) {
		String cacheSmsCode = stringRedisTemplate.opsForValue().get(ProjectConstant.SMS_PREFIX + ProjectConstant.REGISTER_TPLID + "_" + userRegisterParam.getMobile());
		if (StringUtils.isEmpty(cacheSmsCode) || !cacheSmsCode.equals(userRegisterParam.getSmsCode())) {
			return ResultGenerator.genResult(VerificationEnums.SMSCODE_WRONG.getcode(), VerificationEnums.SMSCODE_WRONG.getMsg());
		}
		String passWord = userRegisterParam.getPassWord();
		if (passWord.equals("-1")) {
			userRegisterParam.setPassWord("");
		} else if (!passWord.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$")) {
			return ResultGenerator.genResult(VerificationEnums.PASS_FORMAT_ERROR.getcode(), VerificationEnums.PASS_FORMAT_ERROR.getMsg());
		}
		String invitationUserId = userRegisterParam.getInvitationUserId();
		// 解密邀请码获取UserId
		String dcodeUserId = AESUtils.dcodes(invitationUserId, AESUtils.INVITING_SECRET_KEY);
		if (dcodeUserId != null) {
			BaseResult<Integer> regRst = userService.registerUser(userRegisterParam, request);
			if (regRst.getCode() != 0) {
				return ResultGenerator.genResult(regRst.getCode(), regRst.getMsg());
			}
			Integer userId = regRst.getData();
			userBonusService.receiveUserBonus(ProjectConstant.REGISTER, userId);
			DlOldBeltNew dlOldBeltNew = new DlOldBeltNew();
			dlOldBeltNew.setConsumptionStatus(0);
			dlOldBeltNew.setId(0);
			dlOldBeltNew.setInviterEncryptionUserId(userRegisterParam.getInvitationUserId());
			dlOldBeltNew.setInviterUserId(Integer.parseInt(dcodeUserId));
			dlOldBeltNew.setRegisterUserId(userId);
			dlOldBeltNewService.save(dlOldBeltNew);
			stringRedisTemplate.delete(ProjectConstant.SMS_PREFIX + ProjectConstant.REGISTER_TPLID + "_" + userRegisterParam.getMobile());
			return ResultGenerator.genSuccessResult("领取成功!");
		}
		return ResultGenerator.genSuccessResult("邀请码有误!");
	}

	/**
	 * 分享链接
	 * 
	 * @param strParam
	 * @return
	 */
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

	/**
	 * 邀请人数和奖励
	 * 
	 * @param strParam
	 * @return
	 */
	@ApiOperation(value = "邀请人数和奖励", notes = "邀请人数和奖励")
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
}
