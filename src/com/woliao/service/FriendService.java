package com.woliao.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.woliao.dao.FriendDAO;
import com.woliao.dao.PropDAO;

public class FriendService {
	public boolean addFriend(String selfName, String friendName){
		return new FriendDAO().addFriend(selfName, friendName);
	}
	public boolean deleteFriend_Service(String selfName, String friendName){
		return new FriendDAO().deleteFriend_DAO(selfName, friendName);
	}
	public JSONArray getFriends(String username) throws JSONException{
		List<String> friendNames =  new FriendDAO().getFriends(username);
		//PropDAO propDAO = new PropDAO();
		JSONArray arr = new JSONArray();
		for(int i = 0; i < friendNames.size(); i++){
			JSONObject obj = new JSONObject();
			String friendName = friendNames.get(i);
			//int bloodpressure = propDAO.getAllProp(friendName).getBloodpressure();
			obj.put("friendName", friendName);
			//obj.put("bloodpressure", bloodpressure);
			arr.put(obj);
		}
		return arr;
	}
}
