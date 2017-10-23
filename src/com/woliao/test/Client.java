package com.woliao.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.woliao.constant.Config;

public class Client {
	private Socket socket;
	private static final String IP_SERVER = "10.103.12.84";
	private static final int PORT_SERVER = 6788;
	private PrintWriter out;
	private BufferedReader in;
	
	public Client(){
		try {
			socket = new Socket(IP_SERVER, PORT_SERVER);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		JSONObject jObject = new JSONObject();
		try {
			
//			/**
//			 * 登录
//			 */
//			jObject.put("requestType", Config.REQUEST_LOGIN);
//			jObject.put("username", "aaa");
//			jObject.put("password", "aaa");
			
//			/**
//			 * 注册
//			 */
//			jObject.put("requestType", Config.REQUEST_REGISTER);
//			jObject.put("username", "hhh");
//			jObject.put("password", "hhh");
			
//			/**
//			 * 获取属性
//			 */
//			jObject.put("requestType", Config.REQUEST_GET_PROP);
//			jObject.put("username", "aaa");
			
//			/**
//			 * 修改经纬度
//			 */
//			jObject.put("requestType", Config.REQUEST_MODIFY_PROPADDRESS);
//			jObject.put("username", "aaa");
//			jObject.put("longitudenum", 333.222);
//			jObject.put("latitudenum", 334.333);
			
//			/**
//			 * 获取经纬度
//			 */
//			jObject.put("requestType", Config.REQUEST_ADDRESS);
//			jObject.put("username", "aaa");
//			jObject.put("friendname", "bbb");
			
//			/**
//			 * 上传一条记录
//			 */
//			jObject.put("requestType", Config.REQUEST_UPLOAD_ONE_RECORD);
//			jObject.put("username", "bbb");
//			jObject.put("heartraterecord", "bbbbbbbbbbbbbbbbbbbbbbbbbb");
			
			
//			/**
//			 * 查询所有心率记录
//			 */
//			jObject.put("requestType", Config.REQUEST_GET_ALL_RECORDS);
//			jObject.put("username", "aaa");
			
//			/**
//			 * 查询好友心率地址信息
//			 */
//			jObject.put("requestType", Config.REQUEST_FRIEND_INFO);
//			jObject.put("username", "bbb");
//			jObject.put("friendname", "aaa");
			
//			/**
//			 * 查询好友心率地址信息
//			 */
//			jObject.put("requestType", Config.REQUEST_ADD_FRIEND);
//			jObject.put("username", "aaa");
//			jObject.put("playername", "ddd");
			
//			/**
//			 * 查询好友心率地址信息
//			 */
//			jObject.put("requestType", Config.REQUEST_GET_FRIEND);
//			jObject.put("username", "aaa");
			
//			/**
//			 * 查询好在线好友
//			 */
//			jObject.put("requestType", Config.REQUEST_GET_USERS_ONLINE);
			
			
//			/**
//			 * 查询我的好友
//			 */
//			jObject.put("requestType", Config.REQUEST_GET_FRIEND);
//			jObject.put("username", "aaa");
			
			
//			/**
//			 * 删除好友
//			 */
//			jObject.put("requestType", Config.REQUEST_DELETE_FRIEND);
//			jObject.put("username", "aaa");
//			jObject.put("friendname", "hhh");
			
			
//			/**
//			 * 查询所有心率中某一列记录
//			 */
//			jObject.put("requestType", Config.REQUEST_GET_ALL_MOUYILIE_RECORDS);
//			jObject.put("username", "aaa");
//			jObject.put("lieming", "heartrecord");
//			
			
//			/**
//			 * 查询某时间段心率记录
//			 */
//			jObject.put("requestType", Config.REQUEST_GET_PERIOD_RECORDS);
//			jObject.put("username", "bbb");
//			jObject.put("datenum", 32);
			
			/**
			 * 查询心率一条记录
			 */
			jObject.put("requestType", Config.REQUEST_GET_ONE_RECORDS);
			jObject.put("username", "aaa");
			
//			Date a=new Date();
//			long b=a.getTime();
//			@SuppressWarnings("deprecation")
//			String c=a.toLocaleString();
//			System.out.println(c);
			
			//jObject.put("requestType", Config.REQUEST_REGISTER);//注册新用户
//			jObject.put("requestType", Config.REQUEST_ADDRESS);//获取地址
//			jObject.put("username", "yyy");
//			jObject.put("friendname", "yyy");
			//jObject.put("password", "yyy");	
			//jObject.put("num", "80.6666");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Client client = new Client();
		while(true){
			client.out.println(jObject.toString());
			System.out.println("client启动");
		}
	}
}
