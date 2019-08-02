package com.dl.activity.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dl.activity.param.StrParam;
import com.dl.base.result.BaseResult;

@FeignClient(value="activity-service")
public interface IActiviService {

	 /**
     * 推广活动-邀请充值
     * @param 
     * @return
     */
    @PostMapping("/activity/invitationNumAndReward")
    BaseResult<String> invitationNumAndReward(@RequestBody StrParam strParam);
    
    /**
     * 推广活动-购彩累计
     * @param 
     * @return
     */
    @PostMapping("/activity/buyLotteryRerurnReward")
    BaseResult<String> buyLotteryRerurnReward(@RequestBody StrParam strParam);
}
