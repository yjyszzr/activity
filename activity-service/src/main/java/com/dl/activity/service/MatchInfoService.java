package com.dl.activity.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dl.activity.dao2.MatchInfoMapper;
import com.dl.activity.dto.MatchInfoDTO;
import com.dl.activity.model.MatchInfo;
import com.dl.base.service.AbstractService;

@Service
public class MatchInfoService extends AbstractService<MatchInfo> {
	@Resource
	private MatchInfoMapper matchInfoMapper;

	@Transactional(value = "transactionManager2", readOnly = true)
	public MatchInfoDTO getMatchInfo(Integer matchId) {
		MatchInfoDTO matchInfoDTO = new MatchInfoDTO();
		MatchInfo matchInfo = new MatchInfo();
		matchInfo = matchInfoMapper.getMatchInfo(matchId);
		if (matchInfo != null) {
			matchInfoDTO.setHomeTeamAbbr(matchInfo.getHomeTeamAbbr());
			matchInfoDTO.setHomeTeamPic(matchInfo.getHomeTeamPic());
			matchInfoDTO.setMatchId(matchInfo.getMatchId());
			matchInfoDTO.setVisitingTeamAbbr(matchInfo.getVisitingTeamAbbr());
			matchInfoDTO.setVisitingTeamPic(matchInfo.getVisitingTeamPic());
		}
		return matchInfoDTO;
	}
}
