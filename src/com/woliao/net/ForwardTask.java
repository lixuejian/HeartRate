package com.woliao.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.woliao.constant.Config;
import com.woliao.model.Prop;
import com.woliao.model.Recordheartrate;
import com.woliao.model.User;
//import com.woliao.service.ChengyuService;
import com.woliao.service.FriendService;
import com.woliao.service.PropService;
import com.woliao.service.RecordheartrateService;
import com.woliao.service.UserService;
import com.woliao.util.RandomUtil;

public class ForwardTask extends Task{
	//这个HashMap是用来存放每个Socket连接的
	private static HashMap<String, Socket> map = new HashMap<String, Socket>();
	private String name=null;
	private String ip;
	//输入流
	private BufferedReader in;
	//输出流
	private PrintWriter out;
	//Socket对象
	private Socket socket;
	//用来存放接收的信息
	private JSONObject message;
	//请求类型
	private int requestType;
	//控制run方法里的while循环
	private boolean onWork=true;
	
	//接收一个Socket对象的构造方法
	public ForwardTask(Socket socket){
		this.socket = socket;
		try {
			//初始化输入流和输出流
			in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")), true);
			ip = socket.getInetAddress().getHostAddress();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		
		while (onWork) 
		{
			try 
			{
				receiveMessage();
			} catch (Exception e) 
			{
				//e.printStackTrace();
				System.out.println( ip +" Client is close");
				if(name!=null)
				{
					System.out.println("服务器71行"+ip+":"+name+"~~退出了！！");
					map.remove(name); 
					new UserService().setStateToNonOnline(name);
				}
				break; //为什么要让receiveMessage方法中的异常抛出来，这个break是重点，当有异常时终止while循环
			}
		}
		try {
			if(socket != null){
				socket.close();
			}
			if(in != null){
				in.close();
			}
			if(out != null){
				out.close();
			}
			socket = null;
			in = null;
			out = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setWorkState(boolean state){
		this.onWork = state;
	}
	
	//接收信息
	public void receiveMessage() {
//		将从输入流中读到的信息封装成JSONObject对象
		try{
			System.out.println("******************************收到请求***********************************");
			message = new JSONObject(in.readLine());
			System.out.println(ip +":客户端发来的请求是："+message);
			requestType = message.getInt(Config.REQUEST_TYPE);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		switch (requestType) {
		case Config.REQUEST_LOGIN: //处理“登录”请求
			handLogin();
			break;
		case Config.REQUEST_REGISTER: //处理“注册”请求
			handRegister();
			break;
		case Config.REQUEST_EXIT: //处理“退出”请求
			handExit();
			break;
		case Config.REQUEST_GET_PROP: //处理“获取属性”请求
			handGetProp();
			break;
		case Config.REQUEST_MODIFY_PROP: //处理“修改属性”请求
			handModifyProp();
			break;
		case Config.REQUEST_MODIFY_PROPADDRESS: //处理“修改经纬度”请求
			handModifyPropAddress();
			break;
		case Config.REQUEST_ADDRESS: //处理“获取经纬度”请求
			handGetAddress();
			break;
		case Config.REQUEST_GET_ONE_RECORDS: //获取最新一条心率记录请求
			handGetOneHeartraterecord();
			break;
		case Config.REQUEST_GET_PERIOD_RECORDS: //获取某时间段的心率记录请求
			handGetPeriodHeartraterecords();
			break;
		case Config.REQUEST_UPLOAD_ONE_RECORD: //处理“上传心率纪录”请求
			handUploadheartraterecord();
			break;
		case Config.REQUEST_GET_ALL_RECORDS: //处理“查询所有心率记录”请求
			handGetAllHeartraterecord();
			break;
		case Config.REQUEST_GET_ALL_MOUYILIE_RECORDS: //处理“查询所有心率记录中的某几列”请求
			handGetAllMOUYITIAOHeartraterecord();
			break;
		case Config.REQUEST_ADD_FRIEND: //处理“增加好友”请求
			handAddFriend();
			break;
		case Config.REQUEST_DELETE_FRIEND: //处理“删除好友”请求
			handDeleteFriend_ForwardTask();
			break;
		case Config.REQUEST_GET_FRIEND: //处理“获取好友”请求
			handGetFriends();
			break;
		case Config.REQUEST_FRIEND_INFO: //处理“获取好友位置心率”请求
			handgetFriendInfo();
			break;
		case Config.REQUEST_GET_USERS_ONLINE: //处理“获取在线用户”请求
			handGetOnlineUsers();
			break;
		case Config.REQUEST_GET_BLOODSUGAR: //处理“获取血压”请求
			handGetBloodsugar();
			break;
//		case Config.REQUEST_ADD_SCORES: //处理“添加积分”请求
//			handModifyProp();
//			break;
//		case Config.REQUEST_GET_CHENGYU: //处理“获取成语”请求
//			handGetChengyu();
//			break;
		case Config.REQUEST_SEND_INVITE://处理“邀战”请求
			handInvite();
			break;
		case Config.REQUEST_INVITE_RESULT://处理“邀战结果”请求
			handInviteResult();
			break;
		case Config.REQUEST_EXIT_GAME://退出游戏界面的请求
			handExitGame();
			break;
		case Config.REQUEST_ADD_PLAYERSCORE: //处理添加用户积分的请求
			handAddPlayerScore();
			break;
		case Config.REQUEST_PK_RESULT: //邀战的结果
			handPKRestult();
			break;
		case Config.LLK_SEND_WEI_XIN:
			handSendWeiXin_forwarTask();
			break;
		default:
			break;
		}
	}
	
	//处理“登录”请求
	private void handLogin()
	{
		try 
		{
			System.out.println(ip + "客户端发出了登录请求");
			
			String username = message.getString("username");
			String password = message.getString("password");
			
			JSONObject obj = new JSONObject();
			obj.put(Config.REQUEST_TYPE, Config.REQUEST_LOGIN);
			
			UserService userService = new UserService();
			boolean result=userService.getUsernameState(username)==Config.USER_STATE_NON_ONLINE;
			if(userService.login(username, password)&&result)
			{
				name = username;
				map.put(username, socket);
				obj.put(Config.RESULT, Config.SUCCESS);
				System.out.println(ip+":"+ username + "登录成功");
			}else
			{
				obj.put(Config.RESULT, Config.FAIl);
				System.out.println(ip+":"+ username + "登录失败");
			}
			out.println(obj.toString());
			System.out.println("服务器发送的结果为："+obj.toString());
		} catch (JSONException e) 
		{
			e.printStackTrace();
		}
	}

	//注册
	private void handRegister() {
		try {
			System.out.println(ip+"客户端发出了注册请求");
			
			JSONObject obj = new JSONObject();
			obj.put(Config.REQUEST_TYPE, Config.REQUEST_REGISTER);
			
			User user = new User();
			String name = message.getString("username");
			user.setUsername(name);
			user.setPassword(message.getString("password"));
			int sex=message.getInt("sex");
			int age=message.getInt("age");
			int sportlevel=message.getInt("sportlevel");
			
			if(new UserService().register(user,sex,age,sportlevel)){
				obj.put(Config.RESULT, Config.SUCCESS);
				System.out.println(ip + ":" + name + "注册成功");
			}else{
				obj.put(Config.RESULT, Config.FAIl);
				System.out.println(ip + ":" + name + "注册失败");
			}
			out.println(obj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//pk结果的发送请求
	private void handPKRestult(){
		System.out.println("客户发送了pk结果的请求！");
		try {
//			String username = message.getString("username");//发送者
			String playername = message.getString("playername");//接受者
			Socket sendSocket = map.get(playername);
			PrintWriter outSend = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(sendSocket.getOutputStream(),
							"UTF-8")), true);
			JSONObject sendObject = new JSONObject();
			sendObject.put("requestType", Config.REQUEST_PK_RESULT);
//			sendObject.put("result",Config.FAIl);
			outSend.println(sendObject.toString());
			outSend.flush();
			System.out.println(sendObject.toString()+"这是pk结果json");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	//挑战是“添加积分”请求
	private void handAddPlayerScore(){
		try {
			System.out.println("客户发出了挑战时添加积分告诉对方的请求！");
			int num = message.getInt("num");
			String playername = message.getString("playername");
			Socket sendSocket = map.get(playername);
			PrintWriter outSend = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(sendSocket.getOutputStream(),
							"UTF-8")), true);
			JSONObject sendObject = new JSONObject();
			sendObject.put("requestType", Config.REQUEST_ADD_PLAYERSCORE);
			sendObject.put("num", num);
			outSend.println(sendObject.toString());
			System.out.println(sendObject.toString()+"这是挑战时的添加积分的json");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	//处理获取在线用户请求
	private void handGetOnlineUsers() {
		try {
			System.out.println(ip+":发出了获取在线用户请求");
			JSONObject obj= new UserService().findOnlineUsers();
			String data = obj.toString();
			System.out.println(ip+":获取在线用户成功~~~"+data);
			out.println(data);
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	//添加好友
	private void handAddFriend() {
		try {
			String selfName = message.getString("username");
			String friendName = message.getString("playername");
			
			System.out.println(ip+":"+selfName + "发出了添加好友请求");
			JSONObject obj = new JSONObject();
			obj.put(Config.REQUEST_TYPE, Config.REQUEST_ADD_FRIEND);
			if(new FriendService().addFriend(selfName, friendName)){
				obj.put(Config.RESULT, Config.SUCCESS);
				System.out.println(ip+":"+selfName + "添加好友成功");
			}else{
				obj.put(Config.RESULT, Config.FAIl);
				System.out.println(ip+":"+selfName + "添加好友失败");
			}
			out.println(obj.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
		//删除好友
		private void handDeleteFriend_ForwardTask() {
			try {
				String selfName = message.getString("username");
				String friendName = message.getString("friendname");
				//String friendName = "xff";
				
				System.out.println(ip+":"+selfName + "发出了删除好友请求");
				JSONObject obj = new JSONObject();
				obj.put(Config.REQUEST_TYPE, Config.REQUEST_DELETE_FRIEND);
				if(new FriendService().deleteFriend_Service(selfName, friendName)){
					obj.put(Config.RESULT, Config.SUCCESS);
					System.out.println(ip+":"+selfName + "添加删除成功");
				}else{
					obj.put(Config.RESULT, Config.FAIl);
					System.out.println(ip+":"+selfName + "添加删除失败");
				}
				out.println(obj.toString());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
		
		
		
		//查看好友地址心率信息
		private void handgetFriendInfo() {
			try {
				String selfName = message.getString("username");
				String friendName = message.getString("friendname");
				
				System.out.println(ip+":"+selfName + "发出了查看好友地址心率信息请求");
				JSONObject obj = new JSONObject();
				obj.put(Config.REQUEST_TYPE, Config.REQUEST_FRIEND_INFO);
				Prop prop=new PropService().getProp(friendName);
				
				obj.put("friendname", friendName);
				obj.put("sex", prop.getSex());
				obj.put("age", prop.getAge());
				obj.put("sportlevel", prop.getSportlevel());
				obj.put("bloodperssure", prop.getBloodpressure());
				obj.put("bloodsugar", prop.getBloodsugar());
				obj.put("drinkwater", prop.getDrinkwater());
				obj.put("longitude", prop.getLongitude());
				obj.put("latitude", prop.getLatitude());
				obj.put("uploadtime", prop.getUploadtime());
				obj.put("bpuploadtime", prop.getBpuploadtime());
				obj.put("bsuploadtime", prop.getBsuploadtime());
				
				out.println(obj.toString());
				
				System.out.println(ip + ":" + selfName + "查看朋友信息成功，结果为："+obj.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	//修改道具
	private void handModifyProp() {
		try {
			System.out.println(ip + ":发出了修改属性请求");
			String username = message.getString("username");
			String propName = message.getString("propName");
			int num = message.getInt("num");
			new PropService().modifyProp(username, propName, num);
			JSONObject obj = new JSONObject();
			obj.put(Config.REQUEST_TYPE, Config.REQUEST_MODIFY_PROP);
			obj.put(Config.RESULT, Config.SUCCESS);
			out.println(obj.toString());
			System.out.println(ip + ":" + username + "修改属性成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void handGetAllHeartraterecord() {
		System.out.println("******************************请求处理***********************************");
		System.out.println("客户端发送了查看心率记录的请求！");
		try {
			String username=message.getString("username");
			JSONObject obj = new JSONObject();
			JSONArray arr = new RecordheartrateService().getAllRecord(username);
			obj.put(Config.REQUEST_TYPE, Config.REQUEST_GET_ALL_RECORDS);
			obj.put("list", arr);
			String str=obj.toString();
			out.println(str);
			System.out.println(ip + ":" + username + "查看记录成功"+str);
			System.out.println("******************************返回结果*************************************");
			System.out.println();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void handGetAllMOUYITIAOHeartraterecord() {
		System.out.println("******************************请求处理***********************************");
		System.out.println("客户端发送了查看心率记录的请求！");
		try {
			String username=message.getString("username");
			String lieming=message.getString("lieming");
			JSONObject obj = new JSONObject();
			JSONArray arr = new RecordheartrateService().getAllRecordAboutHeartrate(username,lieming);
			obj.put(Config.REQUEST_TYPE, Config.REQUEST_GET_ALL_MOUYILIE_RECORDS);
			obj.put("list", arr);
			String str=obj.toString();
			out.println(str);
			System.out.println(ip + ":" + username + "查看记录成功"+str);
			System.out.println("******************************返回结果*************************************");
			System.out.println();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void handGetPeriodHeartraterecords() {
		System.out.println("******************************请求处理***********************************");
		System.out.println("客户端发送了查看某时间段的心率记录的请求！");
		try {
			String username=message.getString("username");
			int dateNum=message.getInt("datenum");
			JSONObject obj = new JSONObject();
			JSONArray arr = new RecordheartrateService().getPeriodRecords(username, dateNum);
			obj.put(Config.REQUEST_TYPE, Config.REQUEST_GET_PERIOD_RECORDS);
			obj.put("list", arr);
			String str=obj.toString();
			out.println(str);
			System.out.println(ip + ":" + username + "查看记录成功"+str);
			System.out.println("******************************返回结果*************************************");
			System.out.println();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void handUploadheartraterecord() {
		System.out.println("******************************请求处理***********************************");
		System.out.println("客户端发送了上传心率记录的请求！");
		try {
			String username=message.getString("username");
			String start=message.getString("start");
			String end=message.getString("end");
			String heartraterecord=message.getString("heartraterecord");
			int avarage=message.getInt("avarage");
			int max=message.getInt("max");
			int min=message.getInt("min");
			String duration=message.getString("duration");
			String date=message.getString("date");
			
			new RecordheartrateService().InsertOneRecord(username,start,end, heartraterecord,avarage,max,min,duration,date);
			JSONObject obj = new JSONObject();
			obj.put(Config.REQUEST_TYPE, Config.REQUEST_UPLOAD_ONE_RECORD);
			obj.put(Config.RESULT, Config.SUCCESS);
			out.println(obj.toString());
			System.out.println(ip + ":" + username + "插入记录成功");
			System.out.println("**************************返回结果*************************************");
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handGetOneHeartraterecord() {
		System.out.println("******************************请求处理***********************************");
		System.out.println("客户端发送了查看一条心率记录的请求！");
		try {
			String username=message.getString("username");
			Recordheartrate onerecord = new RecordheartrateService().getOneRecord(username);
			JSONObject obj = new JSONObject();
			
			obj.put(Config.REQUEST_TYPE, Config.REQUEST_GET_ONE_RECORDS);
			obj.put("start", onerecord.getStart());
			obj.put("end", onerecord.getEnd());
			obj.put("heartrecord", onerecord.getHeartrecord());
			obj.put("avarage", onerecord.getAvarage());
			obj.put("max", onerecord.getMax());
			obj.put("min", onerecord.getMin());
			obj.put("duration", onerecord.getDuration());
			obj.put("date", onerecord.getDate());
			String str=obj.toString();
			out.println(str);
			System.out.println(ip + ":" + str + "查看一条记录成功"+str);
			System.out.println("**************************返回结果*************************************");
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//获取好友列表
	private void handGetFriends() {
		System.out.println(ip + "发出了获取好友列表请求");
		try {
			String username = message.getString("username");
			JSONObject obj = new JSONObject();
			obj.put(Config.REQUEST_TYPE, Config.REQUEST_GET_FRIEND);
			JSONArray arr = new FriendService().getFriends(username);
			obj.put("list", arr);
			String str = obj.toString();
			out.println(str);
			System.out.println(ip + "获取好友列表成功"+str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//处理获取道具
	private void handGetProp() {
		try {
			System.out.println(ip+":发出了获取属性请求");
			String username = message.getString("username");
			Prop prop = new PropService().getProp(username);
			JSONObject obj = new JSONObject();
			obj.put(Config.REQUEST_TYPE, Config.REQUEST_GET_PROP);
			obj.put("sex", prop.getSex());
			obj.put("age", prop.getAge());
			obj.put("sportlevel", prop.getSportlevel());
			obj.put("bloodperssure", prop.getBloodpressure());
			obj.put("bloodsugar", prop.getBloodsugar());
			obj.put("drinkwater", prop.getDrinkwater());
			String data = obj.toString();
			System.out.println(ip + ":" + username + "成功获取属性~~~"+data);
			out.println(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//修改地址
	private void handModifyPropAddress() {
		try {
			System.out.println(ip + ":发出了修改经纬度请求");
			String username = message.getString("username");
			//String longitude = message.getString("longitude");
			double longitudenum = message.getDouble("longitudenum");
			//String latitude = message.getString("latitude");
			double latitudenum = message.getDouble("latitudenum");
			new PropService().modifyPropAddress(username, longitudenum,latitudenum);
			JSONObject obj = new JSONObject();
			obj.put(Config.REQUEST_TYPE, Config.REQUEST_MODIFY_PROP);
			obj.put(Config.RESULT, Config.SUCCESS);
			out.println(obj.toString());
			System.out.println(ip + ":" + username + "修改经纬度成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//处理获取地址
	private void handGetAddress() {
		try {
			System.out.println(ip+":发出了获取地址请求");
			String username = message.getString("username");
			
			String friendname = message.getString("friendname");//被查看地址的人
			
			Prop prop = new PropService().getProp(friendname);
			JSONObject obj = new JSONObject();
			obj.put(Config.REQUEST_TYPE, Config.REQUEST_ADDRESS);
			obj.put("longitude", prop.getLongitude());
			obj.put("latitude", prop.getLatitude());
			obj.put("uploadtime", prop.getUploadtime());
//			obj.put("score", prop.getScore());
			String data = obj.toString();
			System.out.println(ip + ":" + username + "成功获取地址~~~"+data);
			out.println(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//处理获取积分
		private void handGetBloodsugar() {
			try {
				System.out.println(ip+"获取玩家信息请求");
				String username = message.getString("username");
				Prop prop = new PropService().getProp(username);
				JSONObject obj = new JSONObject();
				obj.put(Config.REQUEST_TYPE, Config.REQUEST_GET_BLOODSUGAR);
				obj.put("sex", prop.getSex());
				obj.put("age", prop.getAge());
				obj.put("sportlevel", prop.getSportlevel());
				obj.put("jia", prop.getBloodpressure());
				obj.put("score", prop.getBloodsugar());
				String data = obj.toString();
				System.out.println(ip+"获取"+username+"信息请求~~"+data);
				out.println(data);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	//处理“邀战”请求
	private void handInvite(){
		try {
			System.out.println("客户发送了邀战的请求！");
			String inviteplayer = message.getString("username");//发送邀请的人
			String rec_inviteplayer = message.getString("playername");//被邀请的人
			int model = message.getInt("model");//游戏模式
			Socket sendSocket = map.get(rec_inviteplayer);
			PrintWriter outSend = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(sendSocket.getOutputStream(),
							"UTF-8")), true);
			JSONObject sendObject = new JSONObject();
			sendObject.put("requestType", Config.REQUEST_SEND_INVITE);
			sendObject.put("username",inviteplayer);
			sendObject.put("model", model);
			outSend.println(sendObject.toString());
			System.out.println(sendObject.toString()+"这是邀战时的json");
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	//处理“邀战结果”请求
	private void handInviteResult(){
		try{
			System.out.println("客户发送邀战结果的请求！");
			int result = message.getInt("result");
			String receivePlayer = message.getString("playername");
			Socket sendSocket = map.get(receivePlayer);
			PrintWriter outSend = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(sendSocket.getOutputStream(),
							"UTF-8")), true);
			JSONObject sendObject = new JSONObject();
			sendObject.put("result", result);
			sendObject.put("requestType", Config.REQUEST_INVITE_RESULT);
			outSend.println(sendObject.toString());
			System.out.println(sendObject.toString()+"邀战的结果哦！！");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	//处理“退出游戏界面”请求！
	private void handExitGame(){
		System.out.println("客户发送了退出游戏界面的请求！");
		try {
			String playername = message.getString("playername");
			String username = message.getString("username");
			Socket sendSocket = map.get(playername);
			PrintWriter outSend = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(sendSocket.getOutputStream(),
							"UTF-8")), true);
			JSONObject sendObject = new JSONObject();
			sendObject.put("username", username);
			sendObject.put("requestType", Config.REQUEST_EXIT_GAME);
			outSend.println(sendObject.toString());
			System.out.println("退出游戏界面"+sendSocket.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//处理“退出”请求
	private void handExit(){
		try {
			System.out.println(ip+"发出了退出请求");
			String username = message.getString("username");
			//将run方法中的while停止
			setWorkState(false);
			name = null;
			map.remove(username);
			new UserService().setStateToNonOnline(username);
			JSONObject obj = new JSONObject();
			obj.put(Config.REQUEST_TYPE, Config.REQUEST_EXIT);
			obj.put(Config.RESULT, Config.SUCCESS);
			out.println(obj.toString());
			System.out.println(ip + ":" + username + "退出成功"+obj.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected boolean needExecuteImmediate() {
		return false;
	}
	
	private void handSendWeiXin_forwarTask(){
		try {
			System.out.println("收到了客户端发来的微信的请求！");

			String receiver = message.getString("receiver");
			String sender = message.getString("sender");
			String content = message.getString("content");
			Socket sendSocket = map.get(receiver);
			
			System.out.println(receiver+sender+content+"sendSocket"+sendSocket);
			
			PrintWriter outSend = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(sendSocket.getOutputStream(),
							"UTF-8")), true);
			
			System.out.println("outSend"+outSend);
			JSONObject sendObject = new JSONObject();
			sendObject.put("requestType", Config.LLK_GET_WEI_XIN);
			sendObject.put("receiver", receiver);
			sendObject.put("sender", sender);
			sendObject.put("content", content);
			outSend.println(sendObject.toString());
			
			System.out.println("发送给接收端："+sendObject.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
