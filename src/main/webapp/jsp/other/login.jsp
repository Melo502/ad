<%--
  Created by IntelliJ IDEA.
  User: 86182
  Date: 2024/12/16
  Time: 12:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"  %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>广告投放与管理系统登录</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/style1.css">
</head>
<body>

<!-- 错误消息显示 -->
<c:if test="${not empty error}">
    <div class="row">
        <div class="col">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="bi bi-exclamation-triangle-fill me-2"></i>
                    ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="关闭"></button>
            </div>
        </div>
    </div>
</c:if>

<div class="login-container">
    <h2>广告系统账号登录</h2>
    <form action="${pageContext.request.contextPath}/login.do" method="POST">
        <!-- 登录类型选择 -->
        <div class="form-group">
            <label for="userType">选择登录类型：</label>
            <select id="userType" name="userType" required>
                <option value="">请选择</option>
                <option value="广告主">广告主</option>
                <option value="网站长">网站长</option>
                <option value="管理员">管理员</option>
            </select>
        </div>

        <!-- 账号输入框 -->
        <div class="form-group">
            <label for="username">用户名：</label>
            <input type="text" id="username" name="username" required />
        </div>

        <!-- 密码输入框 -->
        <div class="form-group">
            <label for="password">密码：</label>
            <input type="password" id="password" name="password" required />
        </div>

        <!-- 登录按钮 -->
        <div class="form-group">
            <input type="submit" value="登录" />
        </div>

        <p>还没有账户？<a href="${pageContext.request.contextPath}/jsp/other/register.jsp">点击注册</a></p>
    </form>
</div>

</body>
</html>

