package com.woliao.service;

import java.util.List;

import com.woliao.dao.PropDAO;
import com.woliao.model.Prop;

public class PropService {
	//获取所有道具
	public Prop getProp(String username){
		return new PropDAO().getAllProp(username);
	}
	
	//修改道具
	public void modifyProp(String username, String propName, int num){
		new PropDAO().modifyProp(username, propName, num);
	}
	
	public void modifyPropAddress(String username,  double longitudenum ,double latitudenum){
		new PropDAO().modifyPropAddress(username,  longitudenum, latitudenum);
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getProp2(String username){
		return (List<Integer>) new PropDAO().getAllProp(username);
	}
}
