package com.dl.activity.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dl.activity.dao.DlWorldCupPlanMapper;
import com.dl.activity.dao2.DlWorldCupContryMapper;
import com.dl.activity.model.DlWorldCupPlan;
import com.dl.base.service.AbstractService;

@Service
// @Transactional(value = "transactionManager1")
public class DlWorldCupPlanService extends AbstractService<DlWorldCupPlan> {
	@Resource
	private DlWorldCupPlanMapper dlWorldCupPlanMapper;
	@Resource
	private DlWorldCupContryMapper dlWorldCupContryMapper;

}
