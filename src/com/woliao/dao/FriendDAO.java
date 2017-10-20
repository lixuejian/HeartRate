package com.woliao.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.woliao.util.DaoUtil;

public class FriendDAO {
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

	// 增加好友
	public boolean addFriend(String selfName, String friendName) {
		boolean result = true;
		getConnection();
		try {
			String sqlStr = "select * from friend where username='" + selfName
					+ "' and friendname='" + friendName + "'";
			rs = stmt.executeQuery(sqlStr);
			
			if (!rs.next()) {
				String sqlStr1 = "insert into friend(username, friendname) values('"
						+ selfName + "', '" + friendName + "')";
				String sqlStr2 = "insert into friend(username, friendname) values('"
						+ friendName + "', '" + selfName + "')";

				conn.setAutoCommit(false);
				System.out.println("addFriend() sql1=" + sqlStr1);
				System.out.println("addFriend() sql2=" + sqlStr2);
				stmt.executeUpdate(sqlStr1);
				stmt.executeUpdate(sqlStr2);
				conn.commit();
			}else{
				System.out.println("@@@@@@@@@@@@@@@");
				result = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DaoUtil.closeConnection(conn, stmt, rs);
		}
		System.out.println("result~~~~~"+result);
		return result;
	}

	// 删除好友
	public boolean deleteFriend_DAO(String selfName, String friendName) {
		boolean result = true;
		getConnection();
		try {
			String sqlStr = "select * from friend where username='" + selfName
					+ "' and friendname='" + friendName + "'";
			rs = stmt.executeQuery(sqlStr);
			
			System.out.println("先查找 " + sqlStr);
			
			if (!rs.next()) {// 未找到
				System.out.println("查找\n"+sqlStr+"\n失败");
				result = false;
				
			}else{
			
				String sqlStr1 = "delete from friend where username = '" +selfName+"' and friendname ='"+friendName+"'";
				String sqlStr2 = "delete from friend where username = '" +friendName+"' and friendname ='"+selfName+"'";
				/*先不要轻易修改数据库。。*/
				conn.setAutoCommit(false);
				//System.out.println("deleteFriend() sql1=" + sqlStr1);
				//System.out.println("deleteFriend() sql2=" + sqlStr2);
				stmt.executeUpdate(sqlStr1);
				stmt.executeUpdate(sqlStr2);
				conn.commit();
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			DaoUtil.closeConnection(conn, stmt, rs);
		}
		System.out.println("result~~~~~"+result);
		return result;
	}
	// 好友列表
	public List<String> getFriends(String username) {
		getConnection();
		List<String> list = new ArrayList<String>();
		String sql = "select friendname from friend where username='"
				+ username + "'";
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				list.add(rs.getString("friendname"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.closeConnection(conn, stmt, rs);
		}
		return list;
	}

}
