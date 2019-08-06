package com.dl.activity.web;

import com.dl.activity.param.ActUserInitParam;
import com.dl.activity.param.ReceiveActPraiseParam;
import com.dl.activity.service.DlActivityUserInfoService;
import com.dl.base.result.BaseResult;
import com.dl.base.result.ResultGenerator;
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
     * 初始化推广活动用户信息:
     * @param ActUserInitParam
     * @param request
     * @return
     */
    @ApiOperation(value = "初始化推广活动用户信息", notes = "初始化推广活动用户信息")
    @PostMapping("/initActUserInfo")
    public BaseResult<Integer> initActUserInfo(@RequestBody ActUserInitParam actUserInitParam) {
        Integer actUserId = dlActivityUserInfoService.initActUserInfo(actUserInitParam);
        return ResultGenerator.genSuccessResult("初始活动用户信息成功",actUserId);
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
