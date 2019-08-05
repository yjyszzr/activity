package com.dl.activity.service;

import com.dl.activity.dao.DlActivityUserInfoMapper;
import com.dl.activity.enums.ActivityEnums;
import com.dl.activity.model.Activity;
import com.dl.activity.model.ActivityAccount;
import com.dl.activity.model.ActivityConfig;
import com.dl.activity.model.DlActivityUserInfo;
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
import com.dl.member.param.UserIdRealParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
@Transactional(value = "transactionManager1")
public class DlActivityUserInfoService extends AbstractService<DlActivityUserInfo> {
    @Resource
    private DlActivityUserInfoMapper dlActivityUserInfoMapper;

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

    /**65
     * 初始化推广活动用户信息
     * @param actUserInitParam
     * @return
     */
    public Integer initActUserInfo(ActUserInitParam actUserInitParam){
        DlActivityUserInfo actUserInfo = new DlActivityUserInfo();
        actUserInfo.setUserId(actUserInitParam.getUserId());
        actUserInfo.setMobile(actUserInitParam.getMobile());
        return  dlActivityUserInfoMapper.insertWithReturnId(actUserInfo);
    }

    /**
     * 领取推广活动档位奖励
     * @param actUserInitParam
     * @return
     */
    public BaseResult<String> receiveTgActPrize(ReceiveActPraiseParam receiveActPraiseParam){
        Integer userId = SessionUtil.getUserId();
        String invitedMobile = "";
        String inviteMobile = "";
        Double gearPositionMoney = 0d;
        Integer parentUserId = 0;
        BaseResult<UserDTO> userDtoRst = iUserService.userInfoExceptPassReal(new StrParam());
        if(userDtoRst.isSuccess()){
            invitedMobile = userDtoRst.getData().getMobile();//被邀请人手机号
            parentUserId = userDtoRst.getData().getParentUserId();//邀请人user_id
            UserIdRealParam userIdReal = new UserIdRealParam();
            userIdReal.setUserId(parentUserId);
            BaseResult<UserDTO> userDtoRst_ = iUserService.queryUserInfoReal(userIdReal);
            if(userDtoRst_.isSuccess()){
                inviteMobile = userDtoRst_.getData().getMobile();
            }

        }

        Activity activity = activityService.queryActivity(Integer.valueOf(receiveActPraiseParam.getActType()));
        if(activity == null){
            ResultGenerator.genResult(ActivityEnums.DATA_NO_IN_DB.getCode(),"该类型活动不存在");
        }

        ActivityConfig activityConfig = activityConfigService.queryGearByActId(Integer.valueOf(receiveActPraiseParam.getActId()),receiveActPraiseParam.getGearPosition());
        if(activityConfig != null){
            java.text.DecimalFormat myformat=new java.text.DecimalFormat("#0.00");
            gearPositionMoney = Double.parseDouble(activityConfig.getGear_position_money());
        }

        GearHasReceivedParam gearHasReceivedParam = new GearHasReceivedParam();
        gearHasReceivedParam.setUser_id(parentUserId);
        gearHasReceivedParam.setAct_id(Integer.valueOf(receiveActPraiseParam.getActId()));
        gearHasReceivedParam.setGear_position(receiveActPraiseParam.getGearPosition());
        gearHasReceivedParam.setAdd_time(new Date());
        gearHasReceivedParam.setAct_start_time(activity.getStart_time());
        gearHasReceivedParam.setAct_end_time(activity.getEnd_time());
        Boolean hasReceived = activityConfigReceiveService.hasReceivedGear(gearHasReceivedParam);
        if(hasReceived){
            return ResultGenerator.genResult(ActivityEnums.DATA_ALREADY_IN_DB.getCode(),"活动期间已经领取过该档位奖励,不能重复领取.");
        }


        DlActivityUserInfo actUserInfo = new DlActivityUserInfo();
        actUserInfo.setUserId(parentUserId);
        actUserInfo.setMobile(inviteMobile);
        actUserInfo.setWithdrawableReward(gearPositionMoney);
        actUserInfo.setHistoryTotalWithdrawableReward(gearPositionMoney);
        if(receiveActPraiseParam.getActType().equals(2)){//伯乐奖
            actUserInfo.setInvitationNumberReward(gearPositionMoney);
        }

        int saveRst = dlActivityUserInfoMapper.addSomeUserInfo(actUserInfo);
        if(saveRst > 0){
            activityConfigReceiveService.saveReceivedGear(gearHasReceivedParam);
        }

        ActivityAccount activityAccount = new ActivityAccount();
        activityAccount.setMobile(invitedMobile);
        activityAccount.setUser_id(parentUserId);
        activityAccount.setAdd_time(DateUtil.getCurrentTimeLong());
        activityAccount.setType(receiveActPraiseParam.getActType().equals("2")?9:5);//活动类型2-伯乐奖对应活动流水类型中的9,3-荣耀奖对应...5
        activityAccount.setAdd_time(DateUtil.getCurrentTimeLong());
        activityAccount.setReward_money(gearPositionMoney);
        int updateRst = activityAccountService.insertActivityAccount(activityAccount);
        return ResultGenerator.genSuccessResult("领取推广活动档位奖励成功");
    }

}
