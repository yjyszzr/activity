package com.dl.activity.service;

import com.dl.activity.dao.ActivityAccountMapper;
import com.dl.activity.model.ActivityAccount;
import com.dl.base.service.AbstractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.annotation.Resource;

@Service
@Transactional(value = "transactionManager1")
@Slf4j
public class ActivityAccountService extends AbstractService<ActivityAccount> {
	@Resource
	private ActivityAccountMapper activityAccountMapper;

	/**
	 * 记录推广活动收益流水
	 * @param account
	 * @return
	 */
	public Integer insertActivityAccount(ActivityAccount account) {
		return activityAccountMapper.insertActivityAccount(account);
	}

	/**
	 * 获取推广流水
	 * @param account
	 * @return
	 */
	public List<ActivityAccount> queryAccount(Integer userId) {
		return activityAccountMapper.queryAccount(userId);
	}
}
