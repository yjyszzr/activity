package com.dl.activity.service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dl.activity.core.ProjectConstant;
import com.dl.activity.dao.UserMapper;
import com.dl.activity.enums.VerificationEnums;
import com.dl.activity.model.User;
import com.dl.activity.param.UserParam;
import com.dl.activity.param.UserRegisterParam;
import com.dl.activity.utils.Encryption;
import com.dl.base.model.UserDeviceInfo;
import com.dl.base.result.BaseResult;
import com.dl.base.result.ResultGenerator;
import com.dl.base.service.AbstractService;
import com.dl.base.util.DateUtil;
import com.dl.base.util.IpUtil;
import com.dl.base.util.RandomUtil;
import com.dl.base.util.RegexUtil;
import com.dl.base.util.SessionUtil;

@Service
@Transactional(value = "transactionManager1")
@Slf4j
public class UserService extends AbstractService<User> {

	@Resource
	private UserMapper userMapper;

	/**
	 * 注册用户
	 * 
	 * @param userRegisterParam
	 * @param request
	 * @return
	 */
	public BaseResult<Integer> registerUser(UserRegisterParam userRegisterParam, HttpServletRequest request) {
		if (!RegexUtil.checkMobile(userRegisterParam.getMobile())) {
			return ResultGenerator.genResult(VerificationEnums.MOBILE_VALID_ERROR.getcode(), VerificationEnums.MOBILE_VALID_ERROR.getMsg());
		}

		User user = this.findBy("mobile", userRegisterParam.getMobile());
		if (null != user) {
			return ResultGenerator.genResult(VerificationEnums.ALREADY_REGISTER.getcode(), VerificationEnums.ALREADY_REGISTER.getMsg());
		}

		UserParam userParam = new UserParam();
		userParam.setMobile(userRegisterParam.getMobile());
		userParam.setPassWord("");
		userParam.setRegIp(IpUtil.getIpAddr(request));
		userParam.setLastIp(IpUtil.getIpAddr(request));
		if (userRegisterParam.getLoginSource().equals(ProjectConstant.LOGIN_SOURCE_ANDROID)) {
			userParam.setLoginSource(ProjectConstant.ANDROID);
		} else if (userRegisterParam.getLoginSource().equals(ProjectConstant.LOGIN_SOURCE_IOS)) {
			userParam.setLoginSource(ProjectConstant.IOS);
		} else if (userRegisterParam.getLoginSource().equals(ProjectConstant.LOGIN_SOURCE_PC)) {
			userParam.setLoginSource(ProjectConstant.PC);
		} else if (userRegisterParam.getLoginSource().equals(ProjectConstant.LOGIN_SOURCE_H5)) {
			userParam.setLoginSource(ProjectConstant.H5);
		} else {
			userParam.setLoginSource(userRegisterParam.getLoginSource());
		}

		if (!userRegisterParam.getLoginSource().equals(ProjectConstant.LOGIN_SOURCE_H5)) {
			if (null == userRegisterParam.getPushKey()) {
				userParam.setPushKey("");
			} else {
				userParam.setPushKey(userRegisterParam.getPushKey());
			}
		} else {
			userParam.setPushKey("");
		}

		Integer userId = this.saveUser(userParam);

		return ResultGenerator.genSuccessResult("注册成功", userId);
	}

	/**
	 * 保存用户
	 * 
	 * @param uParams
	 */
	public Integer saveUser(UserParam userParam) {
		User user = new User();
		String userName = generateUserName(userParam.getMobile());// 账号
		String nickName = generateNickName(userParam.getMobile());// 昵称
		user.setMobile(userParam.getMobile());
		user.setUserName(userName);
		user.setNickname(nickName);
		user.setHeadImg(ProjectConstant.USER_DEFAULT_HEADING_IMG);
		user.setRegTime(DateUtil.getCurrentTimeLong());
		user.setLastTime(DateUtil.getCurrentTimeLong());
		user.setRegIp(userParam.getRegIp());
		user.setRegFrom(userParam.getLoginSource());
		user.setUserStatus(0);// 用户状态：有效
		user.setUserType(false);// 用户类型：个人用户
		user.setMobileSupplier("");
		user.setMobileProvince("");
		user.setMobileCity("");
		user.setLastIp(userParam.getLastIp());
		user.setSex(false);
		user.setMobile(userParam.getMobile());
		String loginsalt = Encryption.salt();
		user.setSalt(loginsalt);
		String paysalt = Encryption.salt();
		user.setPayPwdSalt(paysalt);
		if (!StringUtils.isEmpty(userParam.getPassWord())) {
			user.setPassword(Encryption.encryption(userParam.getPassWord(), loginsalt));
		} else {
			user.setPassword("");
		}
		user.setRankPoint(0);
		user.setPassWrongCount(0);
		user.setIsReal(ProjectConstant.USER_IS_NOT_REAL);
		user.setPushKey(userParam.getPushKey());
		UserDeviceInfo userDevice = SessionUtil.getUserDevice();
		if (userDevice != null) {
			String channel = userDevice.getChannel();
			user.setDeviceChannel(channel);
		}
		Integer insertRsult = userMapper.insertWithReturnId(user);
		if (1 != insertRsult) {
			log.error("注册用户失败");
			return null;
		}
		Integer userId = user.getUserId();
		return userId;
	}

	/**
	 * 校验用户的手机号
	 * 
	 * @param mobileNumberParam
	 * @return
	 */
	public BaseResult<String> validateUserMobile(String mobileNumber) {
		if (!RegexUtil.checkMobile(mobileNumber)) {
			return ResultGenerator.genResult(VerificationEnums.MOBILE_VALID_ERROR.getcode(), VerificationEnums.MOBILE_VALID_ERROR.getMsg());
		}

		User user = this.findBy("mobile", mobileNumber);
		if (null == user) {
			return ResultGenerator.genResult(VerificationEnums.NO_REGISTER.getcode(), VerificationEnums.NO_REGISTER.getMsg());
		}

		return ResultGenerator.genSuccessResult("用户手机号校验成功");
	}

	/**
	 * 生成昵称：
	 *
	 * @param mobile
	 * @return
	 */
	public String generateNickName(String mobile) {
		if (StringUtils.isEmpty(mobile)) {
			return "****彩主";
		}
		String userName = String.format("%s彩主", mobile.substring(mobile.length() - 4, mobile.length()));
		return userName.toString();
	}

	/**
	 * 生成账号： 1.随机生成4位字母 2.生成用户名 3.查询重复的用户名条数 4.如果有重复用户名，则重新生成
	 * 
	 * @param mobile
	 * @return
	 */
	public String generateUserName(String mobile) {
		StringBuffer userName = new StringBuffer("dl");
		String strRandom4 = RandomUtil.generateUpperString(4);
		userName.append(mobile.replace(mobile.substring(3, 7), strRandom4));
		User user = this.findBy("userName", userName.toString());
		if (null != user) {
			generateUserName(mobile);
		}
		return userName.toString();
	}
}
