package com.woliao.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;

import com.woliao.dao.UserDAO;

public class DaoUtil {
//	public static UserDAO userDAO = new UserDAO();
	
	//关闭数据库连接的方法
	public static void closeConnection(Connection conn, Statement stmt, ResultSet rs){
		try {
			if(rs != null){
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(stmt != null){
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				try {
					if(conn != null){
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
