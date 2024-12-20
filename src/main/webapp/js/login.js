function lgbt() {
    var uname = $("#uphone").val();
    var upwd = $("#upwd").val();
    if (uname === "" || upwd === "") {
        alert("登录信息不完整");
        return;
    }
    $.ajax({
        url: 'LoginSvlt',
        type: 'post',
        dataType: 'json',
        data: {
            "uname": uname,
            "upassword": upwd,
            "utype": "tb_users"
        },
        beforeSend: function () {
            // 显示加载动画
            $("#loading").show();
        },
        success: function (data) {
            if (data.msg == 1) {
                window.location.href = "SySvlt?tbname=sy";
            } else {
                alert(data.msg);
            }
        },
        error: function () {
            alert("网络异常，请稍后再试");
        },
        complete: function () {
            // 隐藏加载动画
            $("#loading").hide();
        }
    });
}
