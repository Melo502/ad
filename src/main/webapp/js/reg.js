function rgbt(){
    var uphone=$("#uphone").val();
    var upwd=$("#upwd").val();
    var uname=$("#uname").val();
    var uaddr=$("#uaddr").val();
    if(uname==""||upwd==""||uphone==""||uaddr==""){
        alert("用户信息不完整");
        return;
    }
    $.ajax({
        url:'AddSvlt',
        type:'post',
        dataType:'json',
        data:{"unames":uname,"upwds":upwd,"uphones":uphone,"uaddrs":uaddr,"tbname":"tb_users_tb_users","isLogin":"1"},
        success:function(data){
            alert(data.msg);
        }
    });
}