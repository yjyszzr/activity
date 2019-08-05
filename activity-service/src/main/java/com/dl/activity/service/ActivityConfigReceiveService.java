package com.dl.activity.service;

import com.dl.activity.dao.ActivityConfigReceiveMapper;
import com.dl.activity.model.ActivityConfigReceive;
import com.dl.activity.param.GearHasReceivedParam;
import com.dl.base.service.AbstractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional(value = "transactionManager1")
@Slf4j
public class ActivityConfigReceiveService extends AbstractService<ActivityConfigReceive> {
	@Resource
	private ActivityConfigReceiveMapper activityConfigReceiveMapper;

    public boolean hasReceivedGear(GearHasReceivedParam receiveActPraiseParam){
        Boolean hasReceived = false;
        Integer count = activityConfigReceiveMapper.hasReceivedConfigDuring(receiveActPraiseParam);
        return count >= 1?true:false;
    }


    public Integer saveReceivedGear(GearHasReceivedParam receiveActPraiseParam){
        return activityConfigReceiveMapper.saveReceived(receiveActPraiseParam);
    }

}
