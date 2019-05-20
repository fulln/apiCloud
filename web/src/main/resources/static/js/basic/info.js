   $(function () {
        //日期控件
        $.jeDate("#beginDate",{
            format:"YYYY-MM-DD",
            isTime:false,
            ishmsVal:false
        })
        $.jeDate("#endDate",{
            format:"YYYY-MM-DD",
            isTime:false,
            ishmsVal:false
        }) 
        $(".select").mousedown(function(){
        	var firstopt=$(".select option:first-child").text().trim();
        	if(firstopt == "请选择"){
			$(".select option").remove();     
        	$(".select").append("<option selected='selected' value='1'>一级用户</option>");
        	$(".select").append("<option value='2'>二级用户</option>");
        	$(".select").append("<option value='3'>三级用户</option>");
        	}
        });
        //点击查询按钮查询事件
        $(".query").click(function(){
			doquerylist();
        });
        
         //设置bootstrapTable起始的高度
	 $('.fixed-table-container').bootstrapTable({height:$("body").height() - $(".condition").height()
					- $(".page").height() - 150});
	//当表格内容的高度小于外面容器的高度，容器的高度设置为内容的高度，相反时容器设置为窗口的高度-160
     if($(".table-bordered").height()<$(".fixed-table-container").height()){
	 $(".fixed-table-container").css({"padding-bottom":"0px",height:$(".table-bordered").height()+20});
    // 是当内容少时，使用搜索功能高度保持不变
	 $('.table-bordered').bootstrapTable('resetView',{height:"auto"});
	}else{
     $(".fixed-table-container").css({height:$(window).height()-160});
 }
 
        //数据表格的高度自动调整
//       $(".table-bordered .tableResult").height($("body").height() - $(".condition").height()
//					- $(".page").height() - 150);
    });
    function checkUserCount(data){
		return  data == "1"?"一级用户": data == "2" ?"二级用户":data == "3"?" 三级用户":" ";	
    };
 function doquerylist(){
 	var pageNum = $("#pageNO").val();// 当前页
	var pageSize = $(".pageSize option:selected").val();// 当前页显示个数
  	var user=$("#user").val();
  	//获取权限
  	if(user!="admin"){myalert("没有权限!");return false}
  	//获得id
  	var id=$("#oneid").val();
  	//获得开始的时间
  	var benginDate=$("#beginDate").val();
  	//获得结束的时间
  	var endDate=$("#endDate").val();
  	//获取输入的用户名字
  	var username=$("#userinput").val();
  	//获取要查询的用户类型
  	var dateType=$(".select option:selected").val();
  	//时间的判断
  	if(benginDate!="" || endDate!=""){
	if(datecompare(benginDate,endDate)<0){myalert("开始时间不能大于结束时间");
	return false}}
	//以json格式进行传递
	var	 parm={
	"benginDate":benginDate,
	"endDate":endDate,
	"username":username,
	"dateType":dateType,
	"pageSize":pageSize,
	"pageNo":pageNum
	}	  	
	$.ajax({
		type: "POST" ,
		url:"checkInfo",
		data:parm,
		dataType:"text",
		success:function(data){
			var pageEntity=JSON.parse(data);
			var dataList = pageEntity["dataList"];
			var count=1;
			var htm="";
			if(dataList.length>=0){
				for (var i = 0; i < dataList.length; i++) {
					htm+="<tr value='"+dataList[i].id+"'><td>"+count+"</td>";
					htm+="<td>"+dataList[i].userName+"</td>";
					htm+="<td>"+getLocalTime(dataList[i].createTime)+"</td>";
					htm+="<td>"+checkUserCount(dataList[i].userType)+"</td>";
					htm+="<td>"+dataList[i].tips+"</td></tr>";
					count++;
				}
				$("#tableResult").html(htm);
				$(".table-bordered thead tr th").each(function(index){
						$(this).width($("#tableResult tr td").eq(index).width());					
				});
			}else{
				$("#tableResult").html('<tr><td colspan ="5"><center style="color:red">未查询到数据</center></td></tr>')
			}
			$("#tableResult tr").click(function(){
					$("#tableResult tr").removeClass("selectedTr");
					$(this).addClass("selectedTr");
			});
			
			var pageNumCount = pageEntity.total;// 当前记录总数
			if (pageNumCount < 1) {
				$(".page").attr("display","block");
			}else{
				$(".page").show();
				var startNum = (pageNum - 1) * pageSize + 1;// 起始记录
				var endNum = pageNum * pageSize;// 结束记录
				if (endNum > pageNumCount) {
					endNum = pageNumCount;
				}
				$(".pageinfo").html("显示第" + startNum + "至" + endNum + "项记录，共"
								+ pageNumCount + "项");	
					// 得到总页数
				var pageCount;
				if (pageNumCount / pageSize == 0) {
					pageCount = pageNumCount / pageSize;
				} else {
					pageCount = Math.ceil(pageNumCount / pageSize);
				}
				$(".pageCount").val(pageCount);
				
			}
		}
	});
 };