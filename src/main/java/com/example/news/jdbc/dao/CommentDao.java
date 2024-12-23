package com.example.news.jdbc.dao;

import com.example.news.jdbc.common.BaseDao;
import com.example.news.jdbc.model.Comment;

import java.sql.SQLException;
import java.util.List;




import java.sql.*;
import java.util.ArrayList;

public class CommentDao extends BaseDao {

	// 增加评论
	public boolean addComment(Comment comment) throws SQLException {
		String sql = "INSERT INTO comment (comment_id, news_id, content, user_name, comment_date) VALUES (?, ?, ?, ?, ?)";
		int rowsAffected = executeUpdate(sql, comment.getComment_id(), comment.getNews_id(), comment.getContent(), comment.getUser_name(), comment.getComment_date());
		return rowsAffected > 0;
	}

	// 根据新闻ID查看评论
	public List<Comment> getCommentByNewsId(int newsId) throws SQLException {
		String sql = "SELECT * FROM comment WHERE news_id = ? ORDER BY DATE_FORMAT(comment_date,'%y-%m-%d %H:%i:%s') DESC";
		ResultSet rs = executeQuery(sql, newsId);
		List<Comment> comments = new ArrayList<>();
		while (rs.next()) {
			comments.add(mapResultSetToComment(rs)); // 映射ResultSet到Comment对象
		}
		return comments;
	}

	// 根据用户名查看评论
	public List<Comment> getCommentByUserName(String userName) throws SQLException {
		String sql = "SELECT * FROM comment WHERE user_name = ? ORDER BY DATE_FORMAT(comment_date,'%y-%m-%d %H:%i:%s') DESC";
		ResultSet rs = executeQuery(sql, userName);
		List<Comment> comments = new ArrayList<>();
		while (rs.next()) {
			comments.add(mapResultSetToComment(rs)); // 映射ResultSet到Comment对象
		}
		return comments;
	}

	// 根据评论ID删除评论
	public boolean deleteCommentByCommentId(int commentId) throws SQLException {
		String sql = "DELETE FROM comment WHERE comment_id = ?";
		int rowsAffected = executeUpdate(sql, commentId);
		return rowsAffected > 0;
	}

	// 根据新闻ID删除评论
	public boolean deleteCommentByNewsId(int newsId) throws SQLException {
		String sql = "DELETE FROM comment WHERE news_id = ?";
		int rowsAffected = executeUpdate(sql, newsId);
		return rowsAffected > 0;
	}

	// 根据用户名删除评论
	public boolean deleteCommentByUserName(String userName) throws SQLException {
		String sql = "DELETE FROM comment WHERE user_name = ?";
		int rowsAffected = executeUpdate(sql, userName);
		return rowsAffected > 0;
	}

	// 将ResultSet映射到Comment对象
	private Comment mapResultSetToComment(ResultSet rs) throws SQLException {
		Comment comment = new Comment();
		comment.setComment_id(rs.getInt("comment_id"));
		comment.setNews_id(rs.getInt("news_id"));
		comment.setContent(rs.getString("content"));
		comment.setUser_name(rs.getString("user_name"));
		comment.setComment_date(rs.getString("comment_date"));
		return comment;
	}
}

