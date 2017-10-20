package com.woliao.dao;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.woliao.constant.Config;
import com.woliao.model.Jianjie;
import com.woliao.model.Prop;
import com.woliao.model.Recordheartrate;
import com.woliao.model.User;
import com.woliao.util.DaoUtil;

public class RecordheartrateDAO {
	// 成员变量
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	// 驱动名
	final String driver = "com.mysql.jdbc.Driver";
	final String uri = "jdbc:mysql://localhost/heartrate?useUnicode=true&amp;characterEncoding=UTF-8";

	// 获取连接
	private void getConnection() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(uri, "root", "123456");
			stmt = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Recordheartrate getOneRecord(String username) {
		Recordheartrate recordheartrate= new Recordheartrate();
		getConnection();
		try {
			String sql = "select * from recordheartrate where username = '" + username
					+ "'"+" order  by   id   desc   limit   1";
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println("rs内容为："+rs.toString());
			while (rs.next()) {
				recordheartrate.setId(rs.getInt("id"));
				recordheartrate.setMax(rs.getInt("max"));
				recordheartrate.setMin(rs.getInt("min"));
				recordheartrate.setAvarage(rs.getInt("avarage"));
				recordheartrate.setStart(rs.getString("start"));
				recordheartrate.setEnd(rs.getString("end"));
				recordheartrate.setDuration(rs.getString("duration"));
				recordheartrate.setDate(rs.getString("date"));
				recordheartrate.setUsername(rs.getString("username"));
				recordheartrate.setHeartrecord(rs.getString("heartrecord"));
				recordheartrate.setRecordtime((Date)rs.getTimestamp("recordtime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DaoUtil.closeConnection(conn, stmt, rs);
		}
		return recordheartrate;
	}

	/**
	 * 查询某个时间段的记录
	 * @return
	 */
	public ArrayList<Recordheartrate> getPeriodRecords(String username,int n) {
		ArrayList<Recordheartrate> periodRecords=new ArrayList<Recordheartrate>();
		getConnection();
		try {
			String sql = "select * from recordheartrate where username = '" + username + "'" +" and to_days(recordtime) - to_days(now()) >= -"+n;
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Recordheartrate recordheartrate = new Recordheartrate();
				recordheartrate.setId(rs.getInt("id"));
				recordheartrate.setUsername(rs.getString("username"));
				recordheartrate.setHeartrecord(rs.getString("heartrecord"));
				recordheartrate.setRecordtime((Date)rs.getTimestamp("recordtime"));
				recordheartrate.setStart(rs.getString("start"));
				recordheartrate.setEnd(rs.getString("end"));
				recordheartrate.setAvarage(rs.getInt("max"));
				recordheartrate.setMax(rs.getInt("max"));
				recordheartrate.setMin(rs.getInt("min"));
				recordheartrate.setDuration(rs.getString("duration"));
				recordheartrate.setDate(rs.getString("date"));
				periodRecords.add(recordheartrate);
				recordheartrate=null;
				}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DaoUtil.closeConnection(conn, stmt, rs);
		}
		System.out.println("periodRecords:"+periodRecords+"!!!!!!!!!!!!!!!!!!!!!");
		
		
		return periodRecords;
	}

	// 获取所有记录
	public ArrayList<Recordheartrate> getAllRecord(String username) {
		ArrayList<Recordheartrate> allrecords= new ArrayList<Recordheartrate>();
		getConnection();
		try {
			String sql = "select * from recordheartrate where username = '" + username
					+ "'";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Recordheartrate recordheartrate = new Recordheartrate();
				recordheartrate.setId(rs.getInt("id"));
				recordheartrate.setUsername(rs.getString("username"));
				recordheartrate.setHeartrecord(rs.getString("heartrecord"));
				recordheartrate.setRecordtime((Date)rs.getTimestamp("recordtime"));
				recordheartrate.setStart(rs.getString("start"));
				recordheartrate.setEnd(rs.getString("end"));
				recordheartrate.setAvarage(rs.getInt("max"));
				recordheartrate.setMax(rs.getInt("max"));
				recordheartrate.setMin(rs.getInt("min"));
				recordheartrate.setDuration(rs.getString("duration"));
				recordheartrate.setDate(rs.getString("date"));
				allrecords.add(recordheartrate);
				recordheartrate=null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DaoUtil.closeConnection(conn, stmt, rs);
		}
		System.out.println("allrecords的值为："+allrecords);
		return allrecords;
	}
	
	// 获取所有记录中的某列纪录
	public ArrayList<String> getAllRecordAboutHeartrate(String username,String lieming) {
		ArrayList<String> allrecords= new ArrayList<String>();
		getConnection();
		try {
			String sql = "select "+lieming+"  from recordheartrate where username = '" + username
					+ "'";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				
				String string=rs.getString(lieming);
				allrecords.add(string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DaoUtil.closeConnection(conn, stmt, rs);
		}
		System.out.println("allrecords的值为："+allrecords);
		return allrecords;
	}
	
	
	// 获取所有记录中的三列纪录供手机端查看心率信息简介
	public ArrayList<Jianjie> getRecord4JIANJIE(String username) {
		ArrayList<Jianjie> allrecords= new ArrayList<Jianjie>();
		getConnection();
		try {
			String sql = "select id duration date from recordheartrate where username = '" + username
					+ "'";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Jianjie jianjie=new Jianjie();
				jianjie.setId(rs.getInt("id"));
				jianjie.setDuration(rs.getString("duration"));
				jianjie.setDate(rs.getString("date"));
				allrecords.add(jianjie);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DaoUtil.closeConnection(conn, stmt, rs);
		}
		System.out.println("allrecords的值为："+allrecords);
		return allrecords;
	}
	

	public void InsertOneRecord(String userName,String start,String end,String heartrecord,
	        int avarage,int max,int min,String duration,String date) {
		getConnection();
		String sql = "insert into recordheartrate (username,heartrecord,recordtime,start,end,avarage,max,min,duration,date) " +
				"values ('" + userName + "','"+heartrecord+ "',now(),"+"'"+start+"','"+end+"','"+avarage+"','"+max+"','"+min+"','"+duration+"','"+date+"'"+")";
		try {
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DaoUtil.closeConnection(conn, stmt, rs);
		}
	}

}
