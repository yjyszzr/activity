package com.dl.activity.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dl.activity.model.ActivityAccount;
import com.dl.base.mapper.Mapper;

public interface ActivityAccountMapper extends Mapper<ActivityAccount> {
	Integer insertActivityAccount(ActivityAccount account);

	List<ActivityAccount> queryAccount(@Param("user_id") Integer user_id);

}