package com.dl.activity.dao;

import org.apache.ibatis.annotations.Param;

import com.dl.activity.model.ActivityUserInfo;
import com.dl.base.mapper.Mapper;

public interface ActivityUserInfoMapper extends Mapper<ActivityUserInfo> {
	ActivityUserInfo getUserInfoByUserId(@Param("user_id") Integer userId);
	Integer updateActivityUserInfoByParentId(ActivityUserInfo activityUserInfo);
	Integer insertActivityUserInfo(ActivityUserInfo activityUserInfo);
}