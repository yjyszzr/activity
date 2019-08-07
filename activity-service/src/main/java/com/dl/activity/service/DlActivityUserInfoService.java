package com.dl.activity.service;

import com.dl.activity.dao.ActivityAccountMapper;
import com.dl.activity.dao.ActivityMapper;
import com.dl.activity.dao.ActivityUserInfoMapper;
import com.dl.activity.enums.ActivityEnums;
import com.dl.activity.model.Activity;
import com.dl.activity.model.ActivityAccount;
import com.dl.activity.model.ActivityConfig;
import com.dl.activity.model.ActivityUserInfo;
import com.dl.activity.param.ActUserInitParam;
import com.dl.activity.param.GearHasReceivedParam;
import com.dl.activity.param.ReceiveActPraiseParam;
import com.dl.base.result.BaseResult;
import com.dl.base.result.ResultGenerator;
import com.dl.base.service.AbstractService;
import com.dl.base.util.DateUtil;
import com.dl.base.util.SessionUtil;
import com.dl.member.api.IUserService;
import com.dl.member.dto.UserDTO;
import com.dl.member.param.StrParam;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Transactional(value = "transactionManager1")
public class DlActivityUserInfoService extends AbstractService<ActivityUserInfo> {
    @Resource
    private ActivityUserInfoMapper activityUserInfoMapper;

    @Resource
    private ActivityMapper activityMapper;

    @Resource
    private ActivityAccountService activityAccountService;

    @Resource
    private ActivityConfigReceiveService activityConfigReceiveService;

    @Resource
    private IUserService iUserService;

    @Resource
    private ActivityService activityService;

    @Resource
    private ActivityConfigService activityConfigService;

    @Resource
    private ActivityAccountMapper activityAccountMapper;

    /**
     * 查询伯乐奖的邀请配置，初始化或更新被邀请人的信息
     * @param actUserInitParam
     * @return
     */
    public BaseResult<String> initActUserInfo(ActUserInitParam actUserInitParam){
        Activity actBole = activityMapper.getActivity(3);
        Integer inviteNum = 1;
        Double rewardMoney = 0d;
        if (actBole != null){
            inviteNum = actBole.getNumber();
            rewardMoney = actBole.getReward_money();
        }

        //已经邀请过
        List<ActivityAccount> activityAccountList = activityAccountMapper.queryAccountByType(actUserInitParam.getMobile(),1);
        if(!CollectionUtils.isEmpty(activityAccountList)){
            return ResultGenerator.genResult(ActivityEnums.DATA_ALREADY_IN_DB.getCode(),"已经邀请过");
        }

        ActivityUserInfo activityUserInfo = activityUserInfoMapper.getUserInfoByUserId(actUserInitParam.getUserId());
        if(activityUserInfo != null){
            ActivityUserInfo userInfoUpdate = new ActivityUserInfo();
            userInfoUpdate.setUser_id(actUserInitParam.getUserId());
            userInfoUpdate.setInvitation_number(activityUserInfo.getInvitation_number()+1);
            userInfoUpdate.setInvitation_number_reward(activityUserInfo.getInvitation_number_reward() + rewardMoney);
            userInfoUpdate.setWithdrawable_reward(activityUserInfo.getWithdrawable_reward() + rewardMoney);
            userInfoUpdate.setHistory_total_withdrawable_reward(activityUserInfo.getHistory_total_withdrawable_reward() + rewardMoney);
            userInfoUpdate.setHistory_invitation_number(activityUserInfo.getHistory_invitation_number() + inviteNum);
            userInfoUpdate.setHistory_invitation_number_reward(activityUserInfo.getHistory_invitation_number_reward() + rewardMoney);
            Integer updateNum = activityUserInfoMapper.updateActivityUserInfoByParentId(userInfoUpdate);
        }else{
            ActivityUserInfo actUserInfo = new ActivityUserInfo();
            actUserInfo.setUser_id(actUserInitParam.getUserId());
            actUserInfo.setMobile(actUserInitParam.getMobile());
            actUserInfo.setWithdrawable_reward(rewardMoney);
            actUserInfo.setHistory_total_withdrawable_reward(rewardMoney);
            actUserInfo.setInvitation_number(inviteNum);
            actUserInfo.setInvitation_number_reward(rewardMoney);
            actUserInfo.setHistory_invitation_number(inviteNum);
            actUserInfo.setHistory_invitation_number_reward(rewardMoney);
            actUserInfo.setMonth_return_reward(0d);
            actUserInfo.setInvitation_add_reward(0d);
            actUserInfo.setHistory_total_return_reward(0d);
            Integer saveNum = activityUserInfoMapper.insertActivityUserInfo(actUserInfo);
        }

        //生成邀请流水
        ActivityAccount activityAccount = new ActivityAccount();
        activityAccount.setUser_id(actUserInitParam.getUserId());
        activityAccount.setMobile(actUserInitParam.getMobile());
        activityAccount.setType(1);
        activityAccount.setReward_money(rewardMoney);
        activityAccount.setAdd_time(DateUtil.getCurrentTimeLong());
        Integer accountSaveNum = activityAccountMapper.insertActivityAccount(activityAccount);
        return ResultGenerator .genSuccessResult("success","邀请成功");
    }

    /**
     * 领取推广活动档位奖励
     * @param actUserInitParam
     * @return
     */
    public BaseResult<String> receiveTgActPrize(ReceiveActPraiseParam receiveActPraiseParam){
        Integer userId = SessionUtil.getUserId();
        String invitedMobile = "";
        Double gearPositionMoney = 0d;
        BaseResult<UserDTO> userDtoRst = iUserService.userInfoExceptPassReal(new StrParam());
        if(userDtoRst.isSuccess()){
            invitedMobile = userDtoRst.getData().getMobile();//被邀请人手机号
        }

        Activity activity = activityService.queryActivity(Integer.valueOf(receiveActPraiseParam.getActType()));
        if(activity == null){
            ResultGenerator.genResult(ActivityEnums.DATA_NO_IN_DB.getCode(),"该类型活动不存在");
        }

        ActivityConfig activityConfig = activityConfigService.queryGearByActId(Integer.valueOf(receiveActPraiseParam.getActId()),receiveActPraiseParam.getGearPosition());
        if(activityConfig != null){
            gearPositionMoney = Double.parseDouble(activityConfig.getGear_position_money());
        }

        GearHasReceivedParam gearHasReceivedParam = new GearHasReceivedParam();
        gearHasReceivedParam.setUser_id(userId);
        gearHasReceivedParam.setConfig_id(Integer.valueOf(activityConfig.getId()));
        gearHasReceivedParam.setGear_position(receiveActPraiseParam.getGearPosition());
        gearHasReceivedParam.setAdd_time(new Date());
        gearHasReceivedParam.setAct_start_time(activity.getStart_time());
        gearHasReceivedParam.setAct_end_time(activity.getEnd_time());
        Boolean hasReceived = activityConfigReceiveService.hasReceivedGear(gearHasReceivedParam);
        if(hasReceived){
            return ResultGenerator.genResult(ActivityEnums.DATA_ALREADY_IN_DB.getCode(),"活动期间已经领取过该档位奖励,不能重复领取.");
        }

        ActivityUserInfo actUserInfo = new ActivityUserInfo();
        actUserInfo.setUser_id(userId);
        actUserInfo.setMobile(invitedMobile);
        actUserInfo.setWithdrawable_reward(gearPositionMoney);
        actUserInfo.setHistory_total_withdrawable_reward(gearPositionMoney);
        if(receiveActPraiseParam.getActType().equals("3")){//伯乐奖
            actUserInfo.setInvitation_number_reward(gearPositionMoney);
            actUserInfo.setHistory_invitation_number_reward(gearPositionMoney);
        }

        int saveRst = activityUserInfoMapper.addSomeUserInfo(actUserInfo);
        if(saveRst > 0){
            activityConfigReceiveService.saveReceivedGear(gearHasReceivedParam);
        }

        ActivityAccount activityAccount = new ActivityAccount();
        activityAccount.setMobile(invitedMobile);
        activityAccount.setUser_id(userId);
        activityAccount.setAdd_time(DateUtil.getCurrentTimeLong());
        activityAccount.setType(receiveActPraiseParam.getActType().equals("3")?9:5);//活动类型3-伯乐奖对应活动流水类型中的9,4-荣耀奖对应...5
        activityAccount.setAdd_time(DateUtil.getCurrentTimeLong());
        activityAccount.setReward_money(gearPositionMoney);
        int updateRst = activityAccountService.insertActivityAccount(activityAccount);
        return ResultGenerator.genSuccessResult("领取推广活动档位奖励成功");
    }

}
