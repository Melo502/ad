function changeNum(id, num) {
    $.ajax({
        url: bpath + 'UpdSvlt',
        type: 'post',
        data: { "tbname": "tb_users_tb_cars", "id": id, "cnums": num },
        dataType: 'json',
        success: function (data) {
            // 处理成功回调
        }
    });
}

function delbt(id) {
    $.ajax({
        url: 'DelSvlt',
        type: 'post',
        dataType: 'json',
        data: { "ids": id, "tbname": "tb_cars" },
        success: function (data) {
            // 处理成功回调
        }
    });
}

function delsbt() {
    var ids = "";
    $("input:checkbox").each(function () {
        if ($(this).prop("checked")) {
            var nm = $(this).prop("name");
            if (nm.indexOf("select-all") === 0) {
                ids += $(this).val() + ",";
            }
        }
    });
    if (ids === "") {
        alert("请选择所要删除的数据.");
        return;
    }
    $.ajax({
        url: bpath + 'DelSvlt',
        type: 'post',
        dataType: 'json',
        data: { "ids": ids, "tbname": "tb_cars" },
        success: function (data) {
            // 处理成功回调
        }
    });
}

function jsbt() {
    var addid = $("#addr").val();
    if (addid === "0") {
        alert("请选择收货地址");
        return;
    }
    var ids = "";
    $("input:checkbox").each(function () {
        if ($(this).prop("checked")) {
            var nm = $(this).prop("name");
            if (nm.indexOf("select-all") === 0) {
                ids += $(this).val() + ",";
            }
        }
    });
    if (ids === "") {
        alert("请选择商品.");
        return;
    }
    $.ajax({
        url: 'AddSvlt',
        type: 'post',
        dataType: 'json',
        data: { "addrid": addid, "ids": ids, "tbname": "tb_users_tb_orders" },
        success: function (data) {
            alert(data.msg);
            window.location.href = 'InitSvlt?tbname=tb_users_tb_cars';
        }
    });
}
