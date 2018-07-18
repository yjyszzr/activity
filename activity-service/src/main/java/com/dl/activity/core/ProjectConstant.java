package com.dl.activity.core;

/**
 * 项目常量
 */
public final class ProjectConstant {
	public static final String BASE_PACKAGE = "com.dl.activity";// 项目基础包名称，根据自己公司的项目修改

	public static final String MODEL_PACKAGE = BASE_PACKAGE + ".model";// Model所在包
	public static final String MAPPER_PACKAGE = BASE_PACKAGE + ".dao";// Mapper所在包
	public static final String SERVICE_PACKAGE = BASE_PACKAGE + ".service";// Service所在包
	public static final String SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".impl";// ServiceImpl所在包
	public static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".web";// Controller所在包

	public static final String MAPPER_INTERFACE_REFERENCE = BASE_PACKAGE + ".mapper.Mapper";// Mapper插件基础接口的完全限定名
	public static final String MAPPER_BASE = "com.dl.base.mapper.Mapper";// Mapper插件基础接口的完全限定名

	public static final String VERIFY_TYPE_REG = "1";

	public static final String SMS_PREFIX = "sms_";
	public final static int SMS_REDIS_EXPIRED = 300;
	public static final String USER_DEFAULT_HEADING_IMG = "http://i9-static.jjwxc.net/novelimage.php?novelid=3385656&coverid=100&ver=d8d2de8a8fb398618c161418abc58f04";
	public static final String USER_IS_NOT_REAL = "0";// 用户已没有进行过实名认证
	public static final String RESETPASS_TPLID = "76181";
	public static final String REGISTER_TPLID = "76179";

	public static final String REGISTER_CAPTCHA = "register_captcha";

	// 业务类型
	public static final Integer REGISTER = 1;

	public static final String LOGIN_SOURCE_ANDROID = "1";
	public static final String LOGIN_SOURCE_IOS = "2";
	public static final String LOGIN_SOURCE_PC = "3";
	public static final String LOGIN_SOURCE_H5 = "4";

	public static final String ANDROID = "android";
	public static final String IOS = "ios";
	public static final String PC = "pc";
	public static final String H5 = "h5";

	public static final int BONUS_STATUS_UNUSED = 0;// 红包未使用
	public static final int BONUS_STATUS_USED = 1;// 红包已使用
	public static final int BONUS_STATUS_EXPIRE = 2;// 红包已过期
	public static final int DELETE = 1;// //1代表已删除
	public static final int NOT_DELETE = 0;// 0代表未删除

	// 全场通用
	public static final Integer BONUS_USE_RANGE_ALL = 0;
}
