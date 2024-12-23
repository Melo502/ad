<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import = " java.util.List" %>
<%@ page import="com.example.news.jdbc.dao.CommentDao" %>
<%@ page import="com.example.news.jdbc.model.Comment" %>
<%@ page import="java.sql.SQLException" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
//NewsDao newsdao = new NewsDao();
//List<News> list = newsdao.searchNewsByDateTopTen();
//for(News news : list){
//	out.println(news.getPublish_date()+"<br/>");
//}
%>
<%
CommentDao commentDao = new CommentDao();
List<Comment> list = null;
    try {
        list = commentDao.getCommentByNewsId(1);
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    for(Comment comment : list){
	out.println(comment.toString()+"<br/>");
}
%>
</body>
</html>