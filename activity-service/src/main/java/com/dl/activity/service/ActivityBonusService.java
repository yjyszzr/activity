package com.dl.activity.service;

import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dl.activity.dao.ActivityBonusMapper;
import com.dl.activity.model.ActivityBonus;
import com.dl.base.service.AbstractService;

@Service
@Transactional(value = "transactionManager1")
@Slf4j
public class ActivityBonusService extends AbstractService<ActivityBonus> {
	@Resource
	private ActivityBonusMapper activityBonusMapper;

	public List<ActivityBonus> queryActivityBonusList(Integer type) {
		ActivityBonus activityBonus = new ActivityBonus();
		activityBonus.setBonusType(type);
		List<ActivityBonus> activityBonusList = activityBonusMapper.queryActivityBonusListBySelective(activityBonus);
		return activityBonusList;
	}

}
