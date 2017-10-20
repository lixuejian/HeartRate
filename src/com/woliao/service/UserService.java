package com.woliao.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.woliao.constant.Config;
import com.woliao.dao.PropDAO;
import com.woliao.dao.UserDAO;
import com.woliao.model.User;

public class UserService {
	//处理登录
	public boolean login(String username, String password){
		UserDAO userDAO = new UserDAO();
		User user = userDAO.findByUsernameAndPassword(username, password);
		if(user == null){
			return false;
		}else{
			userDAO.setState(Config.USER_STATE_ONLINE, username);
			return true;
		}
	}
	//处理注册
	public boolean register(User user,int age,int sex,int sportlevel){
		UserDAO userDAO = new UserDAO();
//		if(userDAO.findByUsernameAndPassword(user.getUsername(), user.getPassword()) == null){
		if(userDAO.findByUsername(user.getUsername()) == null){
			userDAO.save(user);
			PropDAO propDAO = new PropDAO();
			propDAO.addProp(user.getUsername(),age,sex,sportlevel);
			return true;
		}else{
			return false;
		}
	}
	//设置用户的状态为不在线
	public void setStateToNonOnline(String username){
		UserDAO userDAO = new UserDAO();
		userDAO.setState(Config.USER_STATE_NON_ONLINE, username);
	}
	//设置用户的状态为在线
	public void setStateToOnline(String username){
		UserDAO userDAO = new UserDAO();
		userDAO.setState(Config.USER_STATE_ONLINE, username);
	}
	//获取用户的状态
	public int getUsernameState(String username){
		UserDAO userDAO = new UserDAO();
		return userDAO.getState(username);
	}
	//查询在线用户
	public JSONObject findOnlineUsers() throws JSONException{
		List<String> userList =  new UserDAO().findOnlineUsers();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = null;
		PropDAO propDAO = new PropDAO();
		JSONObject jo = new JSONObject();
		for(int i = 0; i < userList.size(); i++){
			String username = userList.get(i);
			map = new HashMap<String, Object>();
			int bloodpressure = propDAO.getAllProp(username).getBloodpressure();
			map.put("username", username);
			map.put("bloodpressure", bloodpressure);
			list.add(map);
		}
		jo.put("list", list);
		jo.put(Config.REQUEST_TYPE, Config.REQUEST_GET_USERS_ONLINE);
		return jo;
	}
}
