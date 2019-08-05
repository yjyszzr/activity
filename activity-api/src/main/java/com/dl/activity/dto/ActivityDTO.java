package com.dl.activity.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("活动DTO")
@Data
public class ActivityDTO {
	private Integer actId;

	/**
	 * 活动名称
	 */
	private String actName;

	/**
	 * 活动标题
	 */
	private String actTitle;

	/**
	 * 活动类型0 - 注册活动 1- 充值活动 2 - 伯乐奖 3-荣耀奖

	 */
	private Integer actType;

	/**
	 * 活动图片
	 */
	private String actImg;

	/**
	 * 活动跳转url
	 */
	private String actUrl;

	/**
	 * 活动开始时间
	 */
	private Integer startTime;

	/**
	 * 活动结束时间
	 */
	private Integer endTime;

	/**
	 * 活动状态 0-有效 1-无效
	 */
	private Integer isFinish;

	/**
	 * 限购数量
	 */
	private Integer purchaseNum;

	/**
	 * 审核状态 0- 未通过 1-通过
	 */
	private Integer status;

	/**
	 * 票种范围：0-全部 1-部分
	 */
	private Integer useRange;

	/**
	 * 奖励金额
	 */
	private String rewardMoney;

	/**
	 * 邀请人数
	 */
	private Integer number;

	/**
	 * 是否删除 1:删除0:未删除
	 */

	private String isDel;

}