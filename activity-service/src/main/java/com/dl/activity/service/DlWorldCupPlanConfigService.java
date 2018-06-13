package com.dl.activity.service;
import com.dl.activity.model.DlWorldCupPlanConfig;
import com.dl.activity.dao.DlWorldCupPlanConfigMapper;
import com.dl.base.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class DlWorldCupPlanConfigService extends AbstractService<DlWorldCupPlanConfig> {
    @Resource
    private DlWorldCupPlanConfigMapper dlWorldCupPlanConfigMapper;

}
