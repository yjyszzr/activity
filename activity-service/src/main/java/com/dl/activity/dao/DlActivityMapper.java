package com.dl.activity.dao;

import com.dl.activity.model.ActivityAccount;
import com.dl.activity.model.DlActivity;
import com.dl.base.mapper.Mapper;

import java.util.List;

public interface DlActivityMapper extends Mapper<DlActivity> {

    List<DlActivity> queryActsByType(Integer actType);

    Integer saveAccount(ActivityAccount activityAccount);

}