package com.dl.activity.api;

import com.dl.activity.dto.ActivityDTO;
import com.dl.activity.param.ActUserInitParam;
import com.dl.base.result.BaseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value="activity-service")
public interface IActService {

    @ApiOperation(value = "初始化推广活动用户信息", notes = "初始化推广活动用户信息")
    @RequestMapping(path="/activity/userInfo/initActUserInfo", method= RequestMethod.POST)
    public BaseResult<Integer> initActUserInfo(@RequestBody ActUserInitParam actUserInitParam);


    @ApiOperation(value = "根据活动类型查询活动集合信息", notes = "根据活动类型查询活动集合信息")
    @RequestMapping(path="/queryActsByType", method= RequestMethod.POST)
    public BaseResult<List<ActivityDTO>> queryActsByType(@RequestBody Integer actType);

}
