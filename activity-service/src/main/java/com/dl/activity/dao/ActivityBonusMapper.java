package com.dl.activity.dao;

import java.util.List;

import com.dl.activity.model.ActivityBonus;
import com.dl.base.mapper.Mapper;

public interface ActivityBonusMapper extends Mapper<ActivityBonus> {

	List<ActivityBonus> queryActivityBonusListBySelective(ActivityBonus activityBonus);
}