package com.dl.activity.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
public class ActivityTgDTO {
	private List<Activity> activity = new ArrayList<Activity>();//活动基本信息
	private ActivityUserInfo activityUserInfo;//活动用户数据
	private List<ActivityConfig> acitvityBl;//伯乐将挡位
	private List<ActivityConfig> acitvityRy;//荣耀将挡位
	private Integer code;
}