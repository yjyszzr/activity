package com.dl.activity.service;
import com.dl.activity.model.DlWorldCupPlan;
import com.dl.activity.dao.DlWorldCupPlanMapper;
import com.dl.base.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class DlWorldCupPlanService extends AbstractService<DlWorldCupPlan> {
    @Resource
    private DlWorldCupPlanMapper dlWorldCupPlanMapper;

}
