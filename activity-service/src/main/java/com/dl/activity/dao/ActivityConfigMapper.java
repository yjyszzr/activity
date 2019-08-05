package com.dl.activity.dao;

import com.dl.activity.model.ActivityConfig;
import com.dl.base.mapper.Mapper;
import org.apache.ibatis.annotations.Param;

public interface ActivityConfigMapper extends Mapper<ActivityConfig> {

    ActivityConfig queryGearByActId(@Param("act_id") Integer actId,@Param("gear_position") String gearPosition);

}