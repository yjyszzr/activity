package com.dl.activity.dao;

import com.dl.activity.model.ActivityUserInfo;
import com.dl.base.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

public interface ActivityUserInfoMapper extends Mapper<ActivityUserInfo> {
	ActivityUserInfo getUserInfoByUserId(@Param("user_id") Integer userId);
	Integer updateActivityUserInfoByParentId(ActivityUserInfo activityUserInfo);
	Integer insertActivityUserInfo(ActivityUserInfo activityUserInfo);



    Integer insertWithReturnId(ActivityUserInfo dlActivityUserInfo);

    Integer addSomeUserInfo(ActivityUserInfo dlActivityUserInfo);


}