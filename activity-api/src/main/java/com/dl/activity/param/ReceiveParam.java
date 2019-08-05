package com.dl.activity.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 领取推广活动红包参数类
 * @author zhangzirong
 *
 */
@ApiModel("领取推广活动红包参数类")
@Data
public class ReceiveParam implements Serializable{

	private static final long serialVersionUID = 1L;

	private  String actType;//3-荣耀奖 4-伯乐奖

	
}
