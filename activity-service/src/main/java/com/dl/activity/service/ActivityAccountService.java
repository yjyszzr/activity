package com.dl.activity.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dl.activity.dao.ActivityAccountMapper;
import com.dl.activity.model.ActivityAccount;
import com.dl.base.service.AbstractService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(value = "transactionManager1")
@Slf4j
public class ActivityAccountService extends AbstractService<ActivityAccount> {
	@Resource
	private ActivityAccountMapper activityAccountMapper;

}
