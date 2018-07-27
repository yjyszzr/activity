package com.dl.activity.dao;

import org.apache.ibatis.annotations.Param;

import com.dl.activity.model.DlQuestionsAndAnswers;
import com.dl.activity.model.DlQuestionsAndAnswersForBeforeNote;
import com.dl.base.mapper.Mapper;

public interface DlQuestionsAndAnswersMapper extends Mapper<DlQuestionsAndAnswers> {

	DlQuestionsAndAnswersForBeforeNote findBeforePeriodNoteBymatchId(@Param("id") Integer id);
}