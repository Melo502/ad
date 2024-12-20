<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="site-nav-bg">
  <div class="site-nav w1200">
    <p class="sn-back-home">
    </p>
    <div class="sn-quick-menu">
      <c:if test="${utype ne'tb_users'}">
        <div class="login"><a href="${bpath}login.jsp">登录</a></div>
        <div class="login"><a href="${bpath}reg.jsp">注册</a></div>
      </c:if>
      <c:if test="${utype eq 'tb_users'}">
        <div class="login"><a href="${bpath}infomation.jsp" style="color: black;">个人中心</a><span></span></div>
        <div class="login"><a href="${bpath}LogoutSvlt" style="color: black;">安全退出</a><span></span></div>
      </c:if>
    </div>
  </div>
</div>


<div class="header">
  <div class="headerLayout w1200">
    <div class="headerCon">
      <h1 class="mallLogo">
        <a href="${bpath}SySvlt?tbname=sy" title="网上图书购买系统" style="font-family: 'SimSun'">
          网上商城
        </a>
      </h1>
      <div class="mallSearch">
        <form action="${bpath}SySvlt" class="layui-form" novalidate>
          <input type="text" name="gname" required  lay-verify="required" autocomplete="off" class="layui-input" placeholder="请输入需要的商品">
          <input type="hidden" name="tbname" value="getglist"/>
          <button class="layui-btn" lay-submit lay-filter="formDemo">
            <i class="layui-icon layui-icon-search"></i>
          </button>
        </form>
      </div>
    </div>
  </div>
</div>


<div class="content content-nav-base">
  <div class="main-nav">
    <div class="inner-cont0">
      <div class="inner-cont1 w1200">
        <div class="inner-cont2">

        </div>
      </div>
    </div>
  </div>
</div>