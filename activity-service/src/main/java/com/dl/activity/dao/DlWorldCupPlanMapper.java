package com.dl.activity.dao;


import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dl.activity.model.DlWorldCupPlan;
import com.dl.base.mapper.Mapper;

public interface DlWorldCupPlanMapper extends Mapper<DlWorldCupPlan> {

	public void insertWorldCupPlan(DlWorldCupPlan worldCupPlan);
	
	List<DlWorldCupPlan> getWorldCupPlanList(@Param("userId") Integer userId);

	BigDecimal findAllOrderAmount(@Param("userId")Integer userId, @Param("startTime")Integer startTime);
}