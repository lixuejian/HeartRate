package com.woliao.constant;

public interface Config {
	public static final int REQUEST_LOGIN = 1000; //登录请求
	public static final int REQUEST_REGISTER = 1001; //注册请求
	public static final int REQUEST_EXIT = 1002;  //退出请求
	public static final int REQUEST_QUICK_LOGIN = 1003; //快速登录请求4
	public static final int REQUEST_GET_PROP = 1004; //	获取属性请求
	public static final int REQUEST_MODIFY_PROP = 1005; //	修改道具请求
	public static final int REQUEST_UPLOAD_ONE_RECORD = 1020; //	上传记录请求
	public static final int REQUEST_GET_ALL_RECORDS = 1021; //	获取所有记录请求
	public static final int REQUEST_GET_ALL_MOUYILIE_RECORDS = 2021; //	获取所有记录某一列请求
	public static final int REQUEST_GET_ONE_RECORDS = 1022; //	获取一条记录请求 
	public static final int REQUEST_GET_PERIOD_RECORDS = 1023; //	获取某时间段记录请求
	public static final int REQUEST_MODIFY_PROPADDRESS = 2005; //	修改经纬度请求
	public static final int REQUEST_ADDRESS = 2006; //	查看朋友地址请求
	public static final int REQUEST_ADD_FRIEND = 1006; //	添加好友请求
	public static final int REQUEST_GET_FRIEND = 1007; //	获取好友列表请求
	public static final int REQUEST_GET_USERS_ONLINE = 1008; //	获取在线用户请求
	public static final int REQUEST_ADD_SCORES=1009; //添加积分的请求
	public static final int REQUEST_GET_BLOODSUGAR=1010; //获取积分的请求
	public static final int REQUEST_GET_CHENGYU=1011; //获取成语的请求
	public static final int REQUEST_SEND_INVITE=1012;// 发送邀请
	public static final int REQUEST_INVITE_RESULT = 1013;//邀请结果
	public static final int REQUEST_EXIT_GAME = 1014;//退出游戏界面请求
	public static final int REQUEST_ADD_PLAYERSCORE = 1015;//PK时添加积分的请求
	public static final int REQUEST_PK_RESULT = 1016;//PK结果
	public static final int REQUEST_DELETE_FRIEND = 1100; //	删除好友请求
	public static final int LLK_SEND_WEI_XIN = 1101; //	删除好友请求
	public static final int LLK_GET_WEI_XIN = 1102; //	删除好友请求
    public static final int REQUEST_FRIEND_INFO = 2007; //	查看朋友信息请求

	
	public static final int SUCCESS = 2000;  //成功结果
	public static final int FAIl = 2001;     //失败结果
	
	public static final int USER_STATE_ONLINE = 3000;  //用户在线的状态
	public static final int USER_STATE_NON_ONLINE = 3001; //用户不在线的状态
	
	public static final String RESULT = "result";//结果
	public static final String REQUEST_TYPE = "requestType";//请求类型
	
}
