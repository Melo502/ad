layui.config({
    base: '${sessionScope.bpath}res/static/js/util/' //存放新模块的目录，注意，不是layui的模块目录
}).use(['mm','carousel','jquery'],function(){
    var carousel = layui.carousel,
        mm = layui.mm;
    var option = {
        elem: '#test1'
        ,width: '100%' //设置容器宽度
        ,arrow: 'always'
        ,height:'298'
        ,indicator:'none'
    }
    carousel.render(option);
    $('.sort a').on('click',function(){
        $(this).addClass('active').siblings().removeClass('active');
    })
    $('.list-box dt').on('click',function(){
        if($(this).attr('off')){
            $(this).removeClass('active').siblings('dd').show()
            $(this).attr('off','')
        }else{
            $(this).addClass('active').siblings('dd').hide()
            $(this).attr('off',true)
        }
    })
});