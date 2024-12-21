function addgwc(id){
    var cnums=$("#cnums").val();
    if(!ckznum(cnums)){
        alert("购买数量输入有误");
        return;
    }
    $.ajax({
        url:'AddSvlt',
        type:'post',
        data:{"tbname":"tb_users_tb_cars","tb_goods_id":id,"cnums":cnums},
        dataType:'json',
        success:function(data){
            alert(data.msg);
        }
    });
}