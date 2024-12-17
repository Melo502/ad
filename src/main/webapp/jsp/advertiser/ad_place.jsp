<%--
  Created by IntelliJ IDEA.
  User: 86183
  Date: 2024/12/15
  Time: 15:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>广告投放</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h1 class="text-center mt-4">选择投放广告的目标网站</h1>

    <!-- 选择投放广告 -->
    <div class="row mt-4">
        <div class="col-md-6 offset-md-3">
            <form action="advertiserServlet.do" method="post">
                <input type="hidden" name="method" value="placeAd">
                <div class="form-group">
                    <label for="website">目标网站</label>
                    <select id="website" name="website" class="form-control" required>
                        <option value="" disabled selected>请选择目标网站</option>
                        <option value="http://localhost:8080/jsp/advertiser/test.jsp">新闻</option>
                        <option value="http://localhost:8080/jsp/advertiser/test.jsp">书城</option>
                        <!-- 添加更多网站选项 -->
                    </select>
                </div>

                <div class="form-group mt-3">
                    <label for="adIds">选择广告</label>
                    <select id="adIds" name="adIds" class="form-control" multiple size="5" required>
                        <c:forEach var="ad" items="${adList}">
                            <option value="${ad.id}">${ad.title}</option>
                        </c:forEach>
                    </select>
                    <small class="form-text text-muted">按住Ctrl (Windows) 或 Command (Mac) 键以选择多个广告。</small>
                </div>

                <button type="submit" class="btn btn-primary btn-lg mt-3">投放广告</button>
            </form>
            <div id="message" class="mt-3">
                <c:if test="${not empty message}">
                   ${message}
                </c:if>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
</body>
</html>
