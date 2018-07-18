package com.dl.activity.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dl.activity.core.ProjectConstant;
import com.dl.activity.dao.UserBonusMapper;
import com.dl.activity.enums.VerificationEnums;
import com.dl.activity.model.ActivityBonus;
import com.dl.activity.model.UserBonus;
import com.dl.base.enums.SNBusinessCodeEnum;
import com.dl.base.exception.ServiceException;
import com.dl.base.service.AbstractService;
import com.dl.base.util.DateUtil;
import com.dl.base.util.SNGenerator;

@Service
@Transactional(value = "transactionManager1")
@Slf4j
public class UserBonusService extends AbstractService<UserBonus> {
	@Resource
	private UserBonusMapper userBonusMapper;

	@Resource
	private ActivityBonusService activityBonusService;

	/**
	 * 领取红包
	 * 
	 * @return
	 */
	public Boolean receiveUserBonus(Integer type, Integer userId) {
		if (null == userId) {
			return false;
		}
		List<ActivityBonus> activityBonusList = new ArrayList<ActivityBonus>();
		// if(ProjectConstant.REGISTER.equals(type)) {
		activityBonusList = activityBonusService.queryActivityBonusList(type);
		// }
		if (activityBonusList.size() == 0) {
			return false;
		}

		Integer now = DateUtil.getCurrentTimeLong();
		Date currentTime = new Date();
		List<UserBonus> userBonusLisgt = new ArrayList<UserBonus>();
		activityBonusList.stream().forEach(s -> {
			UserBonus userBonus = new UserBonus();
			userBonus.setBonusId(s.getBonusId());
			userBonus.setUserId(userId);
			userBonus.setBonusSn(SNGenerator.nextSN(SNBusinessCodeEnum.BONUS_SN.getCode()));
			userBonus.setBonusPrice(s.getBonusAmount());
			userBonus.setAddTime(now);
			userBonus.setReceiveTime(now);
			userBonus.setStartTime(DateUtil.getTimeAfterDays(currentTime, s.getStartTime(), 0, 0, 0));
			userBonus.setEndTime(DateUtil.getTimeAfterDays(currentTime, s.getEndTime(), 23, 59, 59));
			userBonus.setBonusStatus(ProjectConstant.BONUS_STATUS_UNUSED);
			userBonus.setIsDelete(ProjectConstant.NOT_DELETE);
			userBonus.setUseRange(ProjectConstant.BONUS_USE_RANGE_ALL);
			userBonus.setMinGoodsAmount(s.getMinGoodsAmount());
			userBonusLisgt.add(userBonus);
		});

		try {
			int rst = userBonusMapper.insertBatchUserBonus(userBonusLisgt);
			if (rst != userBonusLisgt.size()) {
				throw new ServiceException(VerificationEnums.COMMON_ERROR.getcode(), "用户" + userId + "领取红包异常,已回滚");
			}
		} catch (Exception e) {
			log.error("用户" + userId + "领取红包异常,已回滚");
		}
		return true;
	}
}
