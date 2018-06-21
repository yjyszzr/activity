package com.dl.activity.dao2;

import org.apache.ibatis.annotations.Param;

import com.dl.activity.model.MatchInfo;
import com.dl.base.mapper.Mapper;

public interface MatchInfoMapper extends Mapper<MatchInfo> {

	MatchInfo getMatchInfo(@Param("matchId") Integer matchId);
}