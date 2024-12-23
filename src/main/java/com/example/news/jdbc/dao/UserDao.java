package com.example.news.jdbc.dao;
import com.example.news.jdbc.common.BaseDao;
import com.example.news.jdbc.model.User;

import java.sql.SQLException;
import java.sql.*;


public class UserDao extends BaseDao {

	// 登录, 成功返回true，失败返回false
	public boolean login(String userid, String password) throws SQLException {
		String sql = "SELECT * FROM user WHERE user_id = ? AND password = ?";
		ResultSet rs = executeQuery(sql, userid, password);
		return rs.next(); // 如果查询到记录，则表示登录成功
	}

	// 增加用户，账户ID已使用返回false，成功插入返回true
	public boolean addUser(String userid, String password) throws SQLException {
		String sql = "SELECT * FROM user WHERE user_id = ?";
		ResultSet rs = executeQuery(sql, userid);
		if (rs.next()) {
			return false; // 用户ID已存在
		}

		// 插入新用户
		String insertSql = "INSERT INTO user (user_id, user_name, password, sex, birthdate) VALUES (?, ?, ?, ?, ?)";
		int rowsAffected = executeUpdate(insertSql, userid, "未命名用户", password, "保密", null);
		return rowsAffected > 0;
	}

	// 删除用户，用户不存在返回false，成功删除返回true
	public boolean deleteUser(String userid) throws SQLException {
		String sql = "SELECT * FROM user WHERE user_id = ?";
		ResultSet rs = executeQuery(sql, userid);
		if (rs.next()) {
			String deleteSql = "DELETE FROM user WHERE user_id = ?";
			int rowsAffected = executeUpdate(deleteSql, userid);
			return rowsAffected > 0; // 如果删除成功，返回true
		}
		return false; // 用户不存在
	}

	// 修改用户信息
	public boolean updateUser(User user) throws SQLException {
		String sql = "SELECT * FROM user WHERE user_id = ?";
		ResultSet rs = executeQuery(sql, user.getUser_id());
		if (rs.next()) {
			String updateSql = "UPDATE user SET user_name = ?, password = ?, sex = ?, birthdate = ? WHERE user_id = ?";
			int rowsAffected = executeUpdate(updateSql, user.getUser_name(), user.getPassword(), user.getSex(), user.getBirthdate(), user.getUser_id());
			return rowsAffected > 0; // 如果更新成功，返回true
		}
		return false; // 用户不存在
	}

	// 取得用户信息
	public User getUser(String userid) throws SQLException {
		String sql = "SELECT * FROM user WHERE user_id = ?";
		ResultSet rs = executeQuery(sql, userid);
		if (rs.next()) {
			return mapResultSetToUser(rs); // 映射ResultSet到User对象
		}
		return null; // 用户不存在
	}

	// 将ResultSet映射到User对象
	private User mapResultSetToUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setUser_id(rs.getString("user_id"));
		user.setUser_name(rs.getString("user_name"));
		user.setPassword(rs.getString("password"));
		user.setSex(rs.getString("sex"));
		user.setBirthdate(rs.getString("birthdate"));
		return user;
	}
}

