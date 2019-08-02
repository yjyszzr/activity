package com.dl.activity.enums;

public enum ActivityAccountEnums {

	TYPE_1(1, "邀请单人奖励"),
	TYPE_3(3, "单笔购彩返利"),
	TYPE_5(5, "累计购彩奖励"),
	TYPE_7(7, "提取收益"),
	TYPE_9(9, "邀请人数达到额外奖励挡位");

	private Integer code;
	private String msg;

	ActivityAccountEnums(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
