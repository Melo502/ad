<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>广告投放结果</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h1 class="text-center mt-4">广告投放结果</h1>
    <div class="alert alert-success mt-4" role="alert">
        <h4 class="alert-heading">投放成功！</h4>
        <p>1. 确保在目标界面中引用了下面的静态代码！</p>
        <hr>
        <div>
            <pre>
                <!-- 引入 jQuery -->
                &lt;script src="https://code.jquery.com/jquery-3.6.0.min.js"&gt;&lt;/script&gt;
                <!-- 引入 Bootstrap 样式 -->
                &lt;link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet"&gt;
                <!-- 引入 Bootstrap 脚本 -->
                &lt;script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"&gt;&lt;/script&gt;
                <!-- 引入自定义广告加载脚本 -->
                &lt;script src="${pageContext.request.contextPath}/js/loadAd.js"&gt;&lt;/script&gt;
            </pre>
        </div>
        <p><strong>注释：</strong>上述代码引入了 jQuery、Bootstrap 样式与脚本以及自定义的广告加载脚本。这些资源需要按顺序引入。</p>

        <p>2. 设置广告显示的 div 为指定 id</p>
        <hr>
        <div>
            <pre>
                <!-- 广告展示区域 -->
                &lt;div id="ad-container"&gt;
                <!-- 后端返回的广告脚本将插入到这里 -->
                &lt;/div&gt;
            </pre>
        </div>
        <p><strong>注释：</strong>确保你的页面中定义了一个 ID 为 `ad-container` 的 div 元素，广告脚本将插入到这个区域内。</p>

        <a href="${website}" class="btn btn-primary mt-3">进入目标网页</a>
        <a href="${pageContext.request.contextPath}/advertiserServlet.do?method=viewAd" class="btn btn-primary mt-3">返回控制面板</a>
    </div>
</div>

<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
</body>
</html>
