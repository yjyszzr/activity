package com.dl.activity.dao;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Param;

import com.dl.activity.model.DlQuestionsAndAnswersForBeforeNote;
import com.dl.activity.model.DlQuestionsAndAnswersUser;
import com.dl.base.mapper.Mapper;

public interface DlQuestionsAndAnswersUserMapper extends Mapper<DlQuestionsAndAnswersUser> {

	BigDecimal getTodayAllOrderAmount(@Param("userId") Integer userId, @Param("currentTime") String currentTime);

	DlQuestionsAndAnswersForBeforeNote findBeforePeriodNoteBymatchId(@Param("id") Integer id);
}