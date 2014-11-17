$(function(){
	queryHistory();
	$("#searchHistory").on('click',searchHistory);
	$("#exportHistory").on('click',exportHistory);
	$("#start_time").on('click', popUpCalendar);
	$("#end_time").on('click', popUpCalendar);
});

function popUpCalendar()
{
	laydate({istime: true, format: 'YYYY-MM-DD hh:mm'});
}
function exportHistory()
{
	var keyword = $.trim($("#keyword").val());
	if(keyword==''){
		MU.msgTips('warn','请输入导出条件');
		$("#keyword").focus();
		return;
	}
	
	var startTime = $.trim($("#start_time").val());
	var endTime = $.trim($("#end_time").val());
	var data = {};
	if(keyword!='')
	{
		data['keyword']=keyword;
	}
	
	if(startTime!='')
	{
		data['startTime']=startTime;
	}
	
	if(endTime!='')
	{
		data['endTime']=endTime;
	}
	
	MU.msgTips("loading","导出中。。。");
    $.ajax({
		type:'POST',
		url:basePath+'user/historyExport.html',
		data:data,
		dataType:'json',
		timeout:100000,
		success:function(data){
			if(data.success){
				MU.removeTips();
				window.open(basePath+"exports/"+data.file);
			}
		},
		error:function(request,status,error){
			window.location.reload();
		}	   
    }); 
}
function searchHistory()
{
	var keyword = $.trim($("#keyword").val());
	var startTime = $.trim($("#start_time").val());
	var endTime = $.trim($("#end_time").val());
	
	var data = {};
	if(keyword!='')
	{
		data['keyword']=keyword;
	}
	
	if(startTime!='')
	{
		data['startTime']=startTime;
	}
	
	if(endTime!='')
	{
		data['endTime']=endTime;
	}
	
	queryHistory(data);
}


function queryHistory(content){
	var data={};
	if(content != null)
	{
		data = content;
	}
	
	var urlPath=basePath + 'user/historyQuery.html';
	MU.msgTips("loading","执行中。。。");
	$("#ajax_list_page").whpage({
		"url":urlPath,
		"cur" : 'cur',
		"action" : 'post',
		"data" : data,
		"showNum" : 5,
		"format" : function(msg) {
			 if(msg.success){
				MU.removeTips();
				var data = {};
				pageCount = Math.ceil(msg.total / msg.pageSize);
				data['pageCount'] = pageCount;
				data['cur'] = msg.pageNo;
				data['records'] = msg.total;
		    	var jsonData=msg.jsonData;
		    	$("#tableList").empty();
		    	if(jsonData.length==0){
		    		$("#tableList").append('<tr><td colspan="3">没有相关数据</td></tr>');
		    		return data;
		    	}
		       for(var i in jsonData){
			        var html="";
		       		var j = parseInt(i)+(msg.pageNo-1)*10 + 1;
		       		html += '<tr data-id="'+jsonData[i].id+'"><td>'+j+'</td>';
		       		html += '<td id="name">'+jsonData[i].pipelineName+'</td>';
		       		html += '<td>'+jsonData[i].stationName+'</td>';
		       		html += '<td>'+jsonData[i].monitorTime+'</td>';
		       		html += '<td>'+jsonData[i].duration+'</td>';
		       		var result = "正常";
		       		if(jsonData[i].result == -1)
		       		{
		       			result = "告警";
		       		}
		       		else if(jsonData[i].result == 0)
		       		{
		       			result = "未使用";
		       		}
		       		
		       		
		       		html += '<td>'+result+'</td>';
		       		html += '<td>'+jsonData[i].value+'</td>';
		       		html+='</tr>';
		       		$("#tableList").append(html);
		       }
			   return data;
			}else{
			   MU.msgTips('error',msg.jsonData);
			}
		},
		error: function(){
			window.location.reload();
		}
	});
};