package com.dl.activity.dao;

import java.util.List;

import com.dl.activity.model.UserBonus;
import com.dl.base.mapper.Mapper;

public interface UserBonusMapper extends Mapper<UserBonus> {

	int insertBatchUserBonus(List<UserBonus> list);
}