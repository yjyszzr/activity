package com.dl.activity.dao;

import java.util.List;

import com.dl.activity.model.DlOldBeltNew;
import com.dl.base.mapper.Mapper;

public interface DlOldBeltNewMapper extends Mapper<DlOldBeltNew> {

	List<DlOldBeltNew> finInvitationsByUserId(Integer userId);
}