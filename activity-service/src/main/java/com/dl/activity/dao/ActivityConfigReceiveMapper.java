package com.dl.activity.dao;

import com.dl.activity.model.ActivityConfigReceive;
import com.dl.activity.param.GearHasReceivedParam;
import com.dl.base.mapper.Mapper;

public interface ActivityConfigReceiveMapper extends Mapper<ActivityConfigReceive> {

     Integer hasReceivedConfigDuring(GearHasReceivedParam receiveActPraiseParam);

    Integer saveReceived(GearHasReceivedParam receiveActPraiseParam);

}