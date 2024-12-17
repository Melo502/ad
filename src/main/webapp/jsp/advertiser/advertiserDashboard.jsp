<%--
  Created by IntelliJ IDEA.
  User: 86183
  Date: 2024/12/8
  Time: 10:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>广告投递网站</title>
</head>
<body>

<%
    response.sendRedirect(request.getContextPath()+"/advertiserServlet.do?method=viewAd");
%>

</body>
</html>
