package com.dl.activity.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Condition;

import com.dl.activity.dao.DlQuestionsAndAnswersUserMapper;
import com.dl.activity.dto.MatchInfoDTO;
import com.dl.activity.dto.UserPeriodDTO;
import com.dl.activity.model.DlQuestionsAndAnswersUser;
import com.dl.base.service.AbstractService;

@Service
@Transactional
public class DlQuestionsAndAnswersUserService extends AbstractService<DlQuestionsAndAnswersUser> {
	@Resource
	private DlQuestionsAndAnswersUserMapper dlQuestionsAndAnswersUserMapper;
	@Resource
	private MatchInfoService matchInfoService;

	@Transactional(value = "transactionManager2", readOnly = true)
	public MatchInfoDTO findMatchInfo(Integer matchId) {
		MatchInfoDTO matchInfo = matchInfoService.getMatchInfo(matchId);
		return matchInfo;
	}

	@Transactional(value = "transactionManager1")
	public DlQuestionsAndAnswersUser getQuestionsAndAnswersForUser(Integer userId, Integer questionId) {
		Condition c = new Condition(DlQuestionsAndAnswersUser.class);
		c.createCriteria().andEqualTo("userId", userId).andEqualTo("questionId", questionId);
		List<DlQuestionsAndAnswersUser> list = this.findByCondition(c);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Transactional(value = "transactionManager1")
	public BigDecimal getTodayAllOrderAmount(Integer userId, String currentYearMonthDay) {
		return dlQuestionsAndAnswersUserMapper.getTodayAllOrderAmount(userId, currentYearMonthDay);
	}

	@Transactional(value = "transactionManager1")
	public List<UserPeriodDTO> findByUserId(Integer userId) {
		List<DlQuestionsAndAnswersUser> questionsAndAnswersUserList = dlQuestionsAndAnswersUserMapper.findByUserId(userId);
		List<UserPeriodDTO> userPeriodlist = new ArrayList<UserPeriodDTO>();
		for (int i = 0; i < questionsAndAnswersUserList.size(); i++) {
			UserPeriodDTO userPeriod = new UserPeriodDTO();
			userPeriod.setPeriod(questionsAndAnswersUserList.get(i).getPeriod());
			userPeriod.setMatchId(questionsAndAnswersUserList.get(i).getMatchId());
			userPeriodlist.add(userPeriod);
		}
		return userPeriodlist;
	}

	@Transactional(value = "transactionManager1")
	public DlQuestionsAndAnswersUser findUserAnswerMatchId(Integer matchId, Integer userId) {
		Condition c = new Condition(DlQuestionsAndAnswersUser.class);
		c.createCriteria().andEqualTo("matchId", matchId).andEqualTo("userId", userId);
		List<DlQuestionsAndAnswersUser> list = this.findByCondition(c);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
