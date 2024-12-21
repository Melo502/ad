<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="${sessionScope.bpath}">
    <link rel="stylesheet" type="text/css" href="${sessionScope.bpath}/res/static/css/main.css">
    <link rel="stylesheet" type="text/css" href="${sessionScope.bpath}/res/layui/css/layui.css">
    <script type="text/javascript" src="${sessionScope.bpath}/res/layui/layui.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/index.js"></script>

    <script type="text/javascript">
        function getdata(){
            <c:if test="${empty syflag}">
            window.location.href="${sessionScope.bpath}SySvlt?tbname=sy";
            </c:if>
        }
    </script>

</head>
<body id="body-list-cont" onload="getdata();">
<%@include file="/top.jsp" %>
<div class="content">
    <div class="floors">
        <div class="sk" style="display: none">
            <div class="sk_inner w1200">
                <div class="sk_hd">
                    <a href="javascript:A">
                        <img src="${sessionScope.bpath}res/static/img/s_img1.jpg">
                    </a>
                </div>
                <div class="sk_bd">
                    <div class="layui-carousel" id="test1">
                        <div carousel-item>
                            <c:forEach varStatus="vs" var="a" items="${sytjglist}">
                                <c:if test="${vs.index%4==0}">
                                    <div class="item-box">
                                </c:if>
                                <div class="item">
                                    <a href="${sessionScope.bpath}SySvlt?tbname=goods&id=${a.id}"><img src="${sessionScope.bpath}upfiles/${a.gpics}"></a>
                                    <div class="title" style="width: 190px;">${fn:substring(a.gnames,0,30) }</div>
                                    <div class="price">
                                        <span>￥${a.gvals}</span>
                                    </div>
                                </div>
                                <c:if test="${vs.index%4==3||vs.last}">
                                    </div>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="content content-nav-base commodity-content">
        <div class="commod-cont-wrap">
            <div class="commod-cont w1200 layui-clear">
                <div class="left-nav">
                    <div class="title">所有分类</div>
                    <div class="list-box">
                        <c:forEach var="a" items="${syfgtlist}">
                            <dl>
                                <dt>${a.fgtname }</dt>
                                <c:forEach var="b" items="${a.sgtlist }">
                                    <dd><a href="${sessionScope.bpath}SySvlt?tbname=getglist&sgtid=${b.id}">${b.sgtnames }</a></dd>
                                </c:forEach>
                            </dl>
                        </c:forEach>
                    </div>
                </div>
                <div class="right-cont-wrap">
                    <div class="right-cont">
                        <div class="cont-list layui-clear" id="list-cont">
                            <c:forEach var="a" items="${alist}">
                                <div class="item">
                                    <div class="img">
                                        <a href="${sessionScope.bpath}SySvlt?tbname=goods&id=${a.id}"><img style="width: 280px;height: 280px;" src="${sessionScope.bpath}upfiles/${a.gpics}"></a>
                                    </div>
                                    <div class="text">
                                        <p class="title">${a.gnames}</p>
                                        <p class="price">
                                            <span class="pri">￥${a.gvals}</span>
                                            <span class="nub">优品</span>
                                        </p>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <%@include file="/footer.jsp" %>

</body>
</html>
