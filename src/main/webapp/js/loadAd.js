/**
 * 通过 Ajax 请求广告脚本并插入到指定的元素中
 *
 * @param {string} targetSelector - 要插入广告脚本的div
 */
function loadAdScript(targetSelector) {
    // 使用 jQuery 的 ajax 请求广告脚本
    $.ajax({
        url: 'http://localhost:8080/advertiserServlet.do?method=postAd',  // 后端 API 地址
        type: 'GET',  // 请求类型
        success: function (response) {
            // 将后端返回的 script 元素插入到指定的目标元素中
            $(targetSelector).append(response);
        },
        error: function () {
            console.error('广告加载失败');
        }
    });
}

// 页面加载完成后调用 loadAdScript 函数
$(document).ready(function () {
    loadAdScript('#ad-container');  // 传入广告显示的目标 div 选择器
})

