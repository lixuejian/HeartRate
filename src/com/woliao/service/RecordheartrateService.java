package com.woliao.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import com.woliao.dao.ChengyuDAO;
import com.woliao.dao.FriendDAO;
import com.woliao.dao.PropDAO;
import com.woliao.dao.RecordheartrateDAO;
import com.woliao.model.Jianjie;
import com.woliao.model.Prop;
import com.woliao.model.Recordheartrate;

public class RecordheartrateService {
	
	
	//获取一条记录
	public Recordheartrate getOneRecord(String username){
		return new RecordheartrateDAO().getOneRecord(username);
	}

	/**
	 * 查询某个时间段的记录
	 * @return
	 * @throws JSONException 
	 */
	public JSONArray getPeriodRecords(String username,int dateNum) throws JSONException{
		ArrayList<Recordheartrate> records=  new RecordheartrateDAO().getPeriodRecords(username, dateNum);
		JSONArray arr = new JSONArray();
		for(int i = 0; i < records.size(); i++){
			JSONObject obj = new JSONObject();
			
			obj.put("id", records.get(i).getId());
			obj.put("username", records.get(i).getUsername());
			obj.put("heartraterecord", records.get(i).getHeartrecord());
			obj.put("recordtime", records.get(i).getRecordtime());
			obj.put("start", records.get(i).getStart());
			obj.put("end", records.get(i).getEnd());
			obj.put("avarage", records.get(i).getAvarage());
			obj.put("max", records.get(i).getMax());
			obj.put("min", records.get(i).getMin());
			obj.put("duration", records.get(i).getDuration());
			obj.put("date", records.get(i).getDate());
//			Recordheartrate onerecord = new Recordheartrate();
//			onerecord.setId(records.get(i).getId());
//			onerecord.setUsername(records.get(i).getUsername());
//			onerecord.setHeartrecord(records.get(i).getHeartrecord());
//			onerecord.setRecordtime(records.get(i).getRecordtime());
//			onerecord.setStart(records.get(i).getStart());
//			onerecord.setEnd(records.get(i).getEnd());
//			onerecord.setAvarage(records.get(i).getAvarage());
//			onerecord.setMax(records.get(i).getMax());
//			onerecord.setMin(records.get(i).getMin());
//			onerecord.setDuration(records.get(i).getDuration());
//			onerecord.setDate(records.get(i).getDate());
//			obj.put("onerecord", onerecord);
			arr.put(obj);
		}
		System.out.println("arr::::"+arr+"!!!!!!!!!!");
		return arr;
	}

	//获取所有记录
	public JSONArray getAllRecord (String username) throws JSONException{
		ArrayList<Recordheartrate> records=  new RecordheartrateDAO().getAllRecord(username);
		JSONArray arr = new JSONArray();
		for(int i = 0; i < records.size(); i++){
			JSONObject obj = new JSONObject();
			obj.put("id", records.get(i).getId());
			obj.put("username", records.get(i).getUsername());
			obj.put("heartraterecord", records.get(i).getHeartrecord());
			obj.put("recordtime", records.get(i).getRecordtime());
			obj.put("start", records.get(i).getStart());
			obj.put("end", records.get(i).getEnd());
			obj.put("avarage", records.get(i).getAvarage());
			obj.put("max", records.get(i).getMax());
			obj.put("min", records.get(i).getMin());
			obj.put("duration", records.get(i).getDuration());
			obj.put("date", records.get(i).getDate());
			arr.put(obj);
		}
		System.out.println("Service的arr值为:"+arr);
		return arr;
	}
	
	
	
	//获取所有记录
	public JSONArray getAllRecord4JIANJIE (String username) throws JSONException{
		ArrayList<Jianjie> records=  new RecordheartrateDAO().getRecord4JIANJIE(username);
		JSONArray arr = new JSONArray();
		for(int i = 0; i < records.size(); i++){
			JSONObject obj = new JSONObject();
			obj.put("id", records.get(i).getId());
			obj.put("duration", records.get(i).getDuration());
			obj.put("date", records.get(i).getDate());
			arr.put(obj);
		}
		System.out.println("Service的arr值为:"+arr);
		return arr;
	}
	
	//获取所有记录中的心率
	public JSONArray getAllRecordAboutHeartrate (String username,String lieming) throws JSONException{
		ArrayList<String> records=  new RecordheartrateDAO().getAllRecordAboutHeartrate(username,lieming);
		JSONArray arr = new JSONArray();
		for(int i = 0; i < records.size(); i++){
			JSONObject obj = new JSONObject();
			obj.put("onerecord", records.get(i));
			arr.put(obj);
		}
		System.out.println("Service的arr值为:"+arr);
		return arr;
	}
	
	
	
	//增加记录
	public void InsertOneRecord(String userName,String start,String end,String heartraterecord,
	        int avarage,int max,int min,String duration,String date){
		new RecordheartrateDAO().InsertOneRecord(userName,start, end,heartraterecord,avarage,max,min,duration,date);
	}
	
}
