function changeFrameHeight(){
    var ifm= document.getElementById("mfrm");
    var ifmw=ifm.contentWindow;
    ifm.height=ifmw.document.documentElement.scrollHeight || ifmw.document.body.scrollHeight;
    $("#mfrm").css("min-height","500px");
}
window.onresize=function(){
    changeFrameHeight();
}