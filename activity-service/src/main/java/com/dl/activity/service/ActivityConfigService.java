package com.dl.activity.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dl.activity.dao.ActivityConfigMapper;
import com.dl.activity.model.ActivityConfig;
import com.dl.base.service.AbstractService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(value = "transactionManager1")
@Slf4j
public class ActivityConfigService extends AbstractService<ActivityConfig> {
	@Resource
	private ActivityConfigMapper activityConfigMapper;
	
}
