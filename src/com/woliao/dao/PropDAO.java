package com.woliao.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.woliao.constant.Config;
import com.woliao.model.Prop;
import com.woliao.model.User;
import com.woliao.util.DaoUtil;

public class PropDAO {
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

	// 获取所有信息
	public Prop getAllProp(String username) {
		Prop prop = new Prop();

		getConnection();
		try {
			String sql = "select * from prop where username = '" + username
					+ "'";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				prop.setId(rs.getInt("id"));
				prop.setUsername(rs.getString("username"));
				prop.setSex(rs.getInt("sex"));
				prop.setAge(rs.getInt("age"));
				prop.setSportlevel(rs.getInt("sportlevel"));
				prop.setBloodpressure(rs.getInt("bloodpressure"));
				prop.setBloodsugar(rs.getInt("bloodsugar"));
				prop.setDrinkwater(rs.getInt("drinkwater"));
				prop.setLongitude(rs.getDouble("longitude"));
				prop.setLatitude(rs.getDouble("latitude"));
				prop.setUploadtime((Date)rs.getTimestamp("uploadtime"));
				prop.setBpuploadtime((Date)rs.getTimestamp("bpuploadtime"));
				prop.setBsuploadtime((Date)rs.getTimestamp("bsuploadtime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DaoUtil.closeConnection(conn, stmt, rs);
		}
		return prop;
	}
	
	// 修改信息
	public void modifyProp(String username, String propName, int num) {
		getConnection();
		try {
			String sqlUpdate = "update prop set " + propName + " = "
					+ num + " where username = '" + username + "'";
			stmt.executeUpdate(sqlUpdate);
			
			if (propName.equals("bloodpressure")) {
				modifyPropTime(username,"bpuploadtime");
			} else if(propName.equals("bloodsugar")){
				modifyPropTime(username,"bsuploadtime");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DaoUtil.closeConnection(conn, stmt, rs);
		}
	}
	
	public void modifyPropAddress(String username, double longitudenum , double latitudenum) {
		getConnection();
		try {
			String sqlUpdate = "update prop set " + "longitude" + " = "
					+ longitudenum + "," +"latitude"+"="+ latitudenum + " where username = '" + username + "'";
			stmt.executeUpdate(sqlUpdate);
			
			
				modifyPropTime(username,"uploadtime");
			  
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DaoUtil.closeConnection(conn, stmt, rs);
		}
	}
	
	
	public void modifyPropTime(String username, String propName) {
		getConnection();
		try {
			String sqlUpdate = "update prop set " + propName + " = "
					+ "now()" + " where username = '" + username + "'";
			stmt.executeUpdate(sqlUpdate);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DaoUtil.closeConnection(conn, stmt, rs);
		}
	}
	
	// 添加信息
	public void addProp(String username,int age,int sex,int sportlevel) {
		getConnection();
		String sql = "insert into prop (username,sex,age,sportlevel,bloodpressure,bloodsugar,drinkwater,longitude,latitude,uploadtime,bpuploadtime,bsuploadtime) " +
							"values ('" + username + "','"+sex+"','"+age+"','"+sportlevel+"','5','300','300','80.1111','80.1111',now(),now(),now())";
		try {
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DaoUtil.closeConnection(conn, stmt, rs);
		}
	}

}
