package com.dl.activity.dao;

import com.dl.activity.model.DlWorldCupPlan;
import com.dl.base.mapper.Mapper;

public interface DlWorldCupPlanMapper extends Mapper<DlWorldCupPlan> {
	
	public void insertWorldCupPlan(DlWorldCupPlan worldCupPlan);
	
}