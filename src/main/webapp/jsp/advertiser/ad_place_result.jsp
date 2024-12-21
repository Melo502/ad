<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>广告投放结果</title>
    <!-- 引入Bootstrap CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <!-- 引入Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/successPlace.css">
</head>
<body>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card shadow-sm">
                <div class="card-header bg-success text-white d-flex align-items-center">
                    <i class="bi bi-check-circle-fill me-2" style="font-size: 1.5rem;"></i>
                    <h3 class="mb-0">广告投放结果</h3>
                </div>
                <div class="card-body">
                    <div class="alert alert-success" role="alert">
                        <h4 class="alert-heading"><i class="bi bi-emoji-smile-fill me-2"></i>投放成功！</h4>
                        <hr>
                        <ol>
                            <li>
                                <p>确保在目标界面中引用了下面的静态代码：</p>
                                <pre><code>&lt;script src="${pageContext.request.contextPath}/js/loadAd.js"&gt;&lt;/script&gt;</code></pre>
                                <p><strong>注释：</strong>上述代码引入了自定义的广告加载脚本。</p>
                            </li>
                            <li>
                                <p>设置广告显示的 <code>div</code> 为指定 ID：</p>
                                <pre><code>&lt;div id="ad-container"&gt;&lt;/div&gt;</code></pre>
                                <p><strong>注释：</strong>确保你的页面中定义了一个 ID 为 <code>ad-container</code> 的 <code>div</code> 元素，广告脚本将插入到这个区域内。</p>
                            </li>
                        </ol>
                        <div class="btn-group-custom mt-4">
                            <a href="${website}" class="btn btn-primary btn-lg">
                                <i class="bi bi-arrow-right-circle-fill me-2"></i>进入目标网页
                            </a>
                            <a href="${pageContext.request.contextPath}/advertiserServlet.do?method=viewAd" class="btn btn-secondary btn-lg">
                                <i class="bi bi-arrow-left-circle-fill me-2"></i>返回控制面板
                            </a>
                        </div>
                    </div>
                </div>
                <div class="card-footer text-muted text-center">
                    &copy; 2024 广告投放系统
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 引入Bootstrap JS -->
<script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
</body>
</html>
