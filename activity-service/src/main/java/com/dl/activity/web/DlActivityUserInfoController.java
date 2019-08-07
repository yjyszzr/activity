package com.dl.activity.web;

import com.dl.activity.param.ActUserInitParam;
import com.dl.activity.param.ReceiveActPraiseParam;
import com.dl.activity.service.DlActivityUserInfoService;
import com.dl.base.result.BaseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
* Created by CodeGenerator on 2019/07/31.
*/
@RestController
@RequestMapping("/activity/userInfo")
public class DlActivityUserInfoController {
    @Resource
    private DlActivityUserInfoService dlActivityUserInfoService;

    /**
     * 查询伯乐奖的邀请配置，初始化或更新被邀请人的信息
     * @param ActUserInitParam
     * @param request
     * @return
     */
    @ApiOperation(value = "查询伯乐奖的邀请配置，初始化或更新被邀请人的信息", notes = "查询伯乐奖的邀请配置，初始化或更新被邀请人的信息")
    @PostMapping("/initActUserInfo")
    public BaseResult<String> initActUserInfo(@RequestBody ActUserInitParam actUserInitParam) {
        return dlActivityUserInfoService.initActUserInfo(actUserInitParam);
    }

    /**
     * 领取推广活动的奖励：(给推广活动用户加相应的金额和流水)
     * @param ReceiveActPraiseParam
     * @param request
     * @return
     */
    @ApiOperation(value = "领取推广活动的红包", notes = "领取推广活动的红包")
    @PostMapping("/recieveHBByActType")
    public BaseResult<String> recieveHBByActType(@RequestBody ReceiveActPraiseParam receiveActPraiseParam) {
        return dlActivityUserInfoService.receiveTgActPrize(receiveActPraiseParam);
    }

}
