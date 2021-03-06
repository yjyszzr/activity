package com.dl.activity.service;

import com.dl.activity.dao.ActivityMapper;
import com.dl.activity.model.Activity;
import com.dl.base.service.AbstractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(value = "transactionManager1")
@Slf4j
public class ActivityService extends AbstractService<Activity> {
	@Resource
	private ActivityMapper activityMapper;

	public List<Activity> queryActivityList(Integer type) {
		List<Activity> activityList = activityMapper.queryActivityList();
		if (type > -1) {
			activityList.stream().filter(activity -> {
				if (activity.getAct_type() == type) {
					return true;
				} else {
					return false;
				}
			}).collect(Collectors.toList());
		}
		return activityList;
	}

	
	public Activity queryActivity(Integer type) {
		Activity activity = activityMapper.getActivity(type);
		return activity == null?null:activity;
	}

}
