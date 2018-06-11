package com.dl.activity.enums;

public enum ActivityEnums {
	WORLD_CUP_JUDGE_WARN(305001,"不在世界杯相应有效期内"),
	WORLD_CUP_JUDGE_NOT_CERT(305002,"暂无资格参加推演活动");
	
	private Integer code;
	private String msg;
	
	ActivityEnums(Integer code, String msg){
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
