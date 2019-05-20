$(function(){
    var name = $("#username").val();
    var id=$("#userId").val();
    if(name==null||name==""){
        $(".logined").hide();
        $(".nologin").show();
    }else{
        $(".nologin").hide();
    }

    $("#menu").metisMenu();

    $(".path").click(function(){
        var url=$(this).attr("url");
        var title=$(this).text().trim();
        if(url==null||url=="")return false;
        $("#pageDiv iframe").attr("src",url);//  页面的跳转功能

        //默认选中修改
        $("navbar-right li:first-child").removeClass("active");

        //面包屑导航的更改
        if(title=="主页")$(".breadcrumb").html('<li class="path" url="welcome"><a href="javascript:void(0)">主页</a></li>');
        var mainTitle=$(".breadcrumb li:last-child").text().trim();
        if(title!=mainTitle)$(".breadcrumb").append('<li class="path" url="'+url+'"><a href="javascript:void(0)">'+title+'</a></li>');

    });

});