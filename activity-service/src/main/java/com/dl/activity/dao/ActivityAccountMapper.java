package com.dl.activity.dao;

import com.dl.activity.model.ActivityAccount;
import com.dl.base.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityAccountMapper extends Mapper<ActivityAccount> {
	Integer insertActivityAccount(ActivityAccount account);

	List<ActivityAccount> queryAccount(@Param("user_id") Integer user_id);

    List<ActivityAccount> queryAccountByType(@Param("mobile") String mobile,@Param("type") Integer type);


}