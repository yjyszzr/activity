package com.dl.activity.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dl.activity.dao.DlOldBeltNewMapper;
import com.dl.activity.model.DlOldBeltNew;
import com.dl.base.service.AbstractService;

@Service
@Transactional(value = "transactionManager1")
public class DlOldBeltNewService extends AbstractService<DlOldBeltNew> {
	@Resource
	private DlOldBeltNewMapper dlOldBeltNewMapper;

	public List<DlOldBeltNew> finInvitationsByUserId(Integer userId) {
		return dlOldBeltNewMapper.finInvitationsByUserId(userId);
	}

}
