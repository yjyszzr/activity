package com.dl.activity.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dl.activity.dao2.DlWorldCupContryMapper;
import com.dl.activity.model.DlWorldCupContry;
import com.dl.base.service.AbstractService;

@Service
// @Transactional(value = "transactionManager2")
public class DlWorldCupContryService extends AbstractService<DlWorldCupContry> {
	@Resource
	private DlWorldCupContryMapper dlWorldCupContryMapper;

}
