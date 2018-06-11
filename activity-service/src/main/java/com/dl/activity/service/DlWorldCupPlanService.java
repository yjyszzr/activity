package com.dl.activity.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dl.activity.dao.DlWorldCupPlanMapper;
import com.dl.activity.dao2.DlWorldCupContryMapper;
import com.dl.activity.model.DlWorldCupPlan;
import com.dl.base.service.AbstractService;
import com.dl.base.util.DateUtil;
import com.dl.base.util.SessionUtil;

@Service
// @Transactional(value = "transactionManager1")
public class DlWorldCupPlanService extends AbstractService<DlWorldCupPlan> {
	@Resource
	private DlWorldCupPlanMapper dlWorldCupPlanMapper;
	@Resource
	private DlWorldCupContryMapper dlWorldCupContryMapper;
	
    public Integer saveWorldCupPlan(String palnStr) {
		Integer userId = SessionUtil.getUserId();
		Integer addTime = DateUtil.getCurrentTimeLong();
		DlWorldCupPlan worldCupPlan = new DlWorldCupPlan();
		worldCupPlan.setUserId(userId);
		worldCupPlan.setAddTime(addTime);
		worldCupPlan.setModifyTime(addTime);
		worldCupPlan.setPlanJson(palnStr);
		int planId = dlWorldCupPlanMapper.insertWorldCupPlan(worldCupPlan);
		return planId;
	}

}
