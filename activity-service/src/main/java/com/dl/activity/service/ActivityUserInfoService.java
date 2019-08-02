package com.dl.activity.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dl.activity.dao.ActivityUserInfoMapper;
import com.dl.activity.model.ActivityUserInfo;
import com.dl.base.service.AbstractService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(value = "transactionManager1")
@Slf4j
public class ActivityUserInfoService extends AbstractService<ActivityUserInfo> {
	@Resource
	private ActivityUserInfoMapper activityUserInfoMapper;
	
	/**
	 * 根据userId获取当前用户信息
	 * @param userId
	 * @return
	 */
	public ActivityUserInfo getUserInfoByUserId(Integer userId) {
		return activityUserInfoMapper.getUserInfoByUserId(userId);
	}
	/**
	 * 根据用户id修改用户信息
	 * @param activityUserInfo
	 * @return
	 */
	public Integer updateActivityUserInfoByParentId(ActivityUserInfo activityUserInfo) {
		return activityUserInfoMapper.updateActivityUserInfoByParentId(activityUserInfo);
	}
	/**
	 * 插入邀请人用户信息
	 * @param activityUserInfo
	 * @return
	 */
	public Integer insertActivityUserInfo(ActivityUserInfo activityUserInfo) {
		return activityUserInfoMapper.insertActivityUserInfo(activityUserInfo);
	}
}
