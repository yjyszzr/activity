package com.dl.activity.dao;

import com.dl.activity.model.DlActivityUserInfo;
import com.dl.base.mapper.Mapper;

public interface DlActivityUserInfoMapper extends Mapper<DlActivityUserInfo> {

    Integer insertWithReturnId(DlActivityUserInfo dlActivityUserInfo);

    Integer addSomeUserInfo(DlActivityUserInfo dlActivityUserInfo);
}