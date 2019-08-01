package com.dl.activity.dao;

import java.util.List;

import com.dl.activity.model.Activity;
import com.dl.base.mapper.Mapper;

public interface ActivityMapper extends Mapper<Activity> {

	Activity getActivity(Integer act_type);
	List<Activity> queryActivityList();
}