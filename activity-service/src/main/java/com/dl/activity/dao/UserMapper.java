package com.dl.activity.dao;

import com.dl.activity.model.MemberThirdApiLog;
import com.dl.activity.model.User;
import com.dl.base.mapper.Mapper;

public interface UserMapper extends Mapper<User> {

	int insertWithReturnId(User user);

	int saveMemberThirdApiLog(MemberThirdApiLog thirdApiLog);
}