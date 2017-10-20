package com.woliao.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.woliao.constant.Config;
import com.woliao.model.Friend;
import com.woliao.model.User;
import com.woliao.util.DaoUtil;

public class UserDAO {
	// 成员变量
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	// 驱动名
	final String driver = "com.mysql.jdbc.Driver";
	final String uri = "jdbc:mysql://localhost/heartrate?useUnicode=true&amp;characterEncoding=UTF-8";
	//jdbc:mysql://localhost/chengyu?useUnicode=true&amp;characterEncoding=UTF-8
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

	// 保存方法，(及注册)
	public void save(User user) {
		getConnection();
		String sql = "insert into user (username, password, state) values ('"
				+ user.getUsername() + "', '" + user.getPassword() + "',"
				+ Config.USER_STATE_NON_ONLINE + ")";
		try {
			conn.setAutoCommit(false);
			stmt.executeUpdate(sql);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
				
					e1.printStackTrace();
				}
			}
		} finally {
			DaoUtil.closeConnection(conn, stmt, rs);
		}
	}

	// 根据用户名和密码查询，如果查到了返回值user不为空，没查到user为空
	public User findByUsernameAndPassword(String username, String password) {
		getConnection();
		User user = new User();
		String sql = "select * from user where username = '" + username
				+ "' and password = '" + password + "'";
		try {
			rs = stmt.executeQuery(sql);
			if (!rs.next()) {
				return null;
			} else {
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setState(rs.getInt("state"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.closeConnection(conn, stmt, rs);
		}
		return user;
	}
	
	// 根据用户名查询，如果查到了返回值user不为空，没查到user为空
	public User findByUsername(String username) {
		getConnection();
		User user = new User();
		String sql = "select * from user where username = '" + username + "'";
		try {
			rs = stmt.executeQuery(sql);
			if (!rs.next()) {
				return null;
			} else {
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setState(rs.getInt("state"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.closeConnection(conn, stmt, rs);
		}
		return user;
	}
	

	// 查询在线用户
	public List<String> findOnlineUsers() {
		getConnection();
		List<String> list = new ArrayList<String>();

		try {
			String sql = "select * from user where state="
					+ Config.USER_STATE_ONLINE + "";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				list.add(rs.getString("username"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DaoUtil.closeConnection(conn, stmt, rs);
		}
		System.out.println(list.size());
		return list;
	}
	//查询用户在线状态~~~~~~~
	public int getState(String username){
		getConnection();
		int state =0;
		String sql = "select state from user where username="+"'"+username+"'";
		try {
			rs=stmt.executeQuery(sql);
			while(rs.next()){
			  state = rs.getInt("state");
			}
			return state;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0 ;
	}
	// 设置用户状态
	public void setState(int state, String username) {
		getConnection();
		String sql = "update user set state = " + state + " where username='"
				+ username + "'";
		try {
			conn.setAutoCommit(false);
			stmt.execute(sql);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			DaoUtil.closeConnection(conn, stmt, rs);
		}
	}

	public int getScore(String username) {
		getConnection();
		String sql = "select * from score where username='" + username + "'";

		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int score = rs.getInt("score");
				return score;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.closeConnection(conn, stmt, rs);
		}

		return 0;
	}

	public int getScores(String username) {
		getConnection();
		String sql = "select score from score where username='" + username
				+ "'";
		System.out.println(sql);
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int i = rs.getInt("score");
				return i;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.closeConnection(conn, stmt, rs);
		}

		return 0;
	}

	public int updateScore(String username, int currentscore) {
		getConnection();
		String sql = "update score set score=" + currentscore
				+ " where username='" + username + "'";
		System.out.println(sql);
		try {
			int i = stmt.executeUpdate(sql);
			if (i > 0) {
				int score = getScores(username);
				return score;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.closeConnection(conn, stmt, rs);
		}

		return 0;
	}

	public void findProp(String username) {
		getConnection();
		// SELECT COUNT(*)|COUNT(列名) from 表名 [WHERE 条件语句]
		String sql = "select * from prop where username='" + username + "'";
		try {
			rs = stmt.executeQuery(sql);
			boolean b = rs.next();
			// System.out.println(i+"条记录");
			if (!b) {
				stmt.executeUpdate("insert into prop(username,mian,qu,points) values('"
						+ username + "',0,0,0)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.closeConnection(conn, stmt, rs);
		}
	}

	public Map<String, Integer> getProp(String username) {
		getConnection();
		String sql = "select mian,qu from prop where username='" + username
				+ "'";
		System.out.println(sql);
		// List<Integer> list = new ArrayList<Integer>();
		Map<String, Integer> map = new HashMap<String, Integer>();
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int i = rs.getInt("mian");
				int l = rs.getInt("qu");
				map.put("mian", i);
				map.put("qu", l);
				return map;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.closeConnection(conn, stmt, rs);
		}

		return null;
	}

	public Map<String, Integer> updateProp(String username, int currentmian,
			int currentqu) {
		getConnection();
		String sql = "update prop set mian=" + currentmian + ",qu=" + currentqu
				+ " where username='" + username + "'";
		System.out.println(sql);
		try {
			int i = stmt.executeUpdate(sql);
			if (i > 0) {
				// int score = getScores(username);
				// return score;
				Map<String, Integer> map = getProp(username);
				return map;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtil.closeConnection(conn, stmt, rs);
		}
		return null;
	}
}
