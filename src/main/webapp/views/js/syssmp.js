//提交前特殊字符处理
function hstr(html){
	var str=$('<div/>').text(html).html();
	str=str.replace(/\"/g, "&quot;");
	str=str.replace(/\'/g, "&apos; ");
	return str;
}
//赋值前，特殊字符还原
function gstr(html){
	var str=html=$('<div/>').html(html).text();
	return str;
}
//验证非负整数
function ckznum(znum) {
    var checkNum = /^[1-9]\d*|0$/;
    return checkNum.test(znum);    
}
//验证非负浮点型
function ckfffds(znum) {
    var checkNum= /^\d+(\.\d+)?$/;
    return checkNum.test(znum);
}
//验证邮箱格式
function ckEmail(email){
    var checkEmail=/[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/;
    return checkEmail.test(email);
}
//验证手机号
function ckPhone(phone){
    var checkPhone=/^1[3|4|5|7|8]\d{9}$/;
    return checkPhone.test(phone);
}
//验证字符串长度
function ckstr(str,len){
	if(str.length>len){
		return 1;
	}else if(str.length<len){
		return -1;
	}else{
		return 0;
	}
}
//身份证验证
function ckIdcard(idcard){
	var checkIdcard = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
	//var checkIdcard=/( ^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/;
	return checkIdcard.test(idcard);
}
//非法字符验证
function ckNString(str){	
	var ckstr=new RegExp("[^a-zA-Z0-9\_\u4e00-\u9fa5]","i"); 
	if(ckstr.test(str)==true) return false;
	return true;
}
//设置cookie
function SetCookie(name,value)
{
    var Days = 300; //此 cookie 將被保存300天
    var exp  = new Date();    //new Date("December 31, 9998");
    exp.setTime(exp.getTime() + Days*24*60*60*1000);
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}
//获取cookie值
function getCookie(name)
{
    var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
    if(arr != null) return unescape(arr[2]); return null;
}
//删除cookie
function delCookie(name)
{
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval=getCookie(name);
    if(cval!=null) document.cookie= name + "="+cval+";expires="+exp.toGMTString();
}
/**
 * 增加浏览历史记录
 * historyCount 记录个数
 * @return
 */
function setHistory(keyWord,historyCount) {
    var keyWords = $.cookie('keyWord');
    if (keyWords) {
        if(keyWord) {
            var keys = keyWords.split(",");
            for (var i = keys.length - 1; i >= 0; i--) {
                if (keys[i] == keyWord) {
                    keys.splice(i, 1);
                }
            }
            keys.push(keyWord);
            if (keys.length >= historyCount) {
                //删除最开始的多余记录
                var count = keys.length - historyCount + 1; //需要删除的个数
                keys.splice(0, count); //开始位置,删除个数
            }
            $.cookie('keyWord', keys.join(','), {expires: 365, path: '/'});
        }
    } else {
        $.cookie('keyWord', keyWord, {expires: 365, path: '/'});
    }
}
 
function  delHistory(){
    $.cookie("keyWord",null,{path:"/",expires: -1});
}
 
function  getHistory(){
    var keyWords = $.cookie('keyWord');
    if(keyWords) {
        var keys=  keyWords.split(",");
        var length = keys.length;
        if (length > 0) {
            $("#historyRecord").empty();
            var htmlString = "<dt>历史搜索</dt><dd>";
            for (var i = length - 1; i >= 0; i--) {
                htmlString += "<a href='javascript:;' >" + keys[i] + "</a>";
            }
            htmlString += "</dd>";
            $("#historyRecord").html(htmlString);
        }
    }
}

