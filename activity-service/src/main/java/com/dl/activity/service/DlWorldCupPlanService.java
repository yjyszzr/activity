package com.dl.activity.service;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Condition;

import com.dl.activity.dao.DlWorldCupPlanMapper;
import com.dl.activity.model.DlWorldCupPlan;
import com.dl.base.service.AbstractService;
import com.dl.base.util.DateUtil;
import com.dl.base.util.SessionUtil;

@Service
// @Transactional(value = "transactionManager1")
public class DlWorldCupPlanService extends AbstractService<DlWorldCupPlan> {
	@Resource
	private DlWorldCupPlanMapper dlWorldCupPlanMapper;

	@Transactional(value = "transactionManager1")
	public Integer saveWorldCupPlan(String palnStr) {
		Integer userId = SessionUtil.getUserId();
		Integer addTime = DateUtil.getCurrentTimeLong();
		DlWorldCupPlan worldCupPlan = new DlWorldCupPlan();
		worldCupPlan.setUserId(userId);
		worldCupPlan.setAddTime(addTime);
		worldCupPlan.setModifyTime(addTime);
		worldCupPlan.setPlanJson(palnStr);
		dlWorldCupPlanMapper.insertWorldCupPlan(worldCupPlan);
		return worldCupPlan.getId();
	}

	public BigDecimal findAllOrderAmount(Integer userId) {
		return dlWorldCupPlanMapper.findAllOrderAmount(userId);
	}

	public List<DlWorldCupPlan> findByUserId(Integer userId) {
		Condition condition = new Condition(DlWorldCupPlan.class);
		condition.createCriteria().andEqualTo("userId", userId);
		return this.findByCondition(condition);
	}
}
