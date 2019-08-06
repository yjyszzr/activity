package com.dl.activity.dao;

import com.dl.activity.model.ActivityConfig;
import com.dl.base.mapper.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ActivityConfigMapper extends Mapper<ActivityConfig> {

    ActivityConfig queryGearByActId(@Param("act_id") Integer actId,@Param("gear_position") String gearPosition);

    List<ActivityConfig> queryGearListByActId(@Param("act_id") Integer actId);
    
}