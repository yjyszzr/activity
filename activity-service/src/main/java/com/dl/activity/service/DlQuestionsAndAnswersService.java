package com.dl.activity.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Condition;

import com.dl.activity.dao.DlQuestionsAndAnswersMapper;
import com.dl.activity.model.DlQuestionsAndAnswers;
import com.dl.activity.model.DlQuestionsAndAnswersForBeforeNote;
import com.dl.base.service.AbstractService;

@Service
public class DlQuestionsAndAnswersService extends AbstractService<DlQuestionsAndAnswers> {
	@Resource
	private DlQuestionsAndAnswersMapper dlQuestionsAndAnswersMapper;

	@Transactional(value = "transactionManager1", readOnly = true)
	public DlQuestionsAndAnswers getQuestionsAndAnswers(Integer matchId) {
		Condition c = new Condition(DlQuestionsAndAnswers.class);
		c.createCriteria().andEqualTo("matchId", matchId);
		List<DlQuestionsAndAnswers> list = this.findByCondition(c);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Transactional(value = "transactionManager1")
	public DlQuestionsAndAnswersForBeforeNote findBeforePeriodNoteBymatchId(Integer id) {
		return dlQuestionsAndAnswersMapper.findBeforePeriodNoteBymatchId(id);
	}
}
