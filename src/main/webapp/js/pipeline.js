$(function(){
	queryPipeline();
	$("#searchPipeline").on('click',searchPipeline);
	$("#addPipeline").on('click',addPipeline);
});

function searchPipeline()
{
	var keyword = $.trim($("#keyword").val());
	queryPipeline(keyword);
}

function refreshPipeline()
{
	var keyword = $.trim($("#keyword").val());
	if(keyword == '')
	{
		queryPipeline();
	}
	else
	{
		queryPipeline(keyword);
	}
}

function addPipeline()
{
	var html='<div class="dbxx-tcc"><div class="dbxx-tcc-cont">';
    html+='<p><span class="dbxx-tcc-wz">流水线名称：</span><input type="text" id="name" value="" placeholder="流水线名称" class="dbxx-tcc-wb" /></p>';
	html+='<p><span class="dbxx-tcc-wz">流水线编号：</span><input type="text" id="number" value="" onblur="checkData.isNumSpan(\'number\')" placeholder="流水线编号" class="dbxx-tcc-wb" /></p>';
    html+='<p style="height:120px;"><span class="dbxx-tcc-wz">流水线简介：</span><textarea id="desc" value="" rows="7" placeholder="流水线简介" class="dbxx-tcc-wb"/></p>';
    html+='<p class="cz-cont"><a href="javascript:void(0);" class="cz-btn" id="doAddd">添加</a><a href="javascript:void(0);" onclick="MU.close(this)" class="cz-btn">取消</a></p>';
	html+='</div></div>';
    MU.alert(html,600,'新增流水线');
    $("#doAddd").on('click',doAddd);
}

function doAddd()
{
	var name = $.trim($("#name").val());
	if(name==''){
		MU.msgTips('warn','请输入流水线名称');
		$("#name").focus();
		return;
	}
	var number = $.trim($("#number").val());
	if(number==''){
		MU.msgTips('warn','请输入流水线编号');
		$("#number").focus();
		return;
	}
	if(!checkData.isNum("number")){
		MU.msgTips('warn','流水线编号必须为数字');
		$("#number").focus();
		return;
	}
	var desc = $.trim($("#desc").val());
	MU.msgTips("loading","执行中。。。");
	$.ajax({
		type:'POST',
		url:basePath+'engineer/pipelineAdd.html',
		data:{name:name,number:number,desc:desc},
		dataType:'json',
		timeout:100000,
		success:function(data){
			if(data.success)
			{
				MU.removeTips();
				MU.msgTips('success',data.jsonData);
				MU.close();
				refreshPipeline();
			}
			else
			{
				MU.removeTips();
				MU.msgTips('error',data.jsonData);
				MU.close();
			}
		},
		error:function(request,status,error){
			window.location.reload();
		}	   
	});
}


function deletePipeline(){
	var id = $(this).closest('tr').attr('data-id');
	 MU.msgTips("loading","执行中。。。");
	 $.ajax({
		type:'POST',
		url:basePath+'engineer/pipelineDelete.html',
		data:{id:id},
		dataType:'json',
		timeout:100000,
		success:function(data){
			if(data.success)
			{
				MU.removeTips();
				MU.msgTips('success',data.jsonData);
				refreshPipeline();
			}
			else
			{
				MU.removeTips();
				MU.msgTips('success',data.jsonData);
			}
		},
		error:function(request,status,error){
			window.location.reload();
		}	   
	}); 
}

function queryPipeline(keyword){
	var data={};
	if(keyword != null)
	{
		data['keyword']=keyword;
	}
	
	var urlPath=basePath + 'engineer/pipelineQuery.html';
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
		       		html += '<td id="name">'+jsonData[i].name+'</td>';
		       		html += '<td>'+jsonData[i].number+'</td>';
		       		var state = jsonData[i].state==1 ? "正常" : "异常";
		       		html += '<td>'+ state +'</td>';
		       		html += '<td>'+jsonData[i].stations+'</td>';
		       		html += '<td>'+jsonData[i].createTime+'</td>';
		       		html += '<td><div  style="width:300px; text-overflow:ellipsis; white-space:nowrap; -o-text-overflow: ellipsis; overflow:hidden;" title="' + jsonData[i].desc + '">'+jsonData[i].desc+'</div></td>';
		       		html+='<td><a class="editt" href="javascript:;">编辑&nbsp;</a><a class="deletee" href="javascript:;">&nbsp;删除</a></td>';
		       		html+='<td><a class="addd" href="javascript:;">添加工位&nbsp;</a><a class="operatee" href="javascript:;">&nbsp;删除工位</a></tr>';
		       		$("#tableList").append(html);
		       }
		       $(".editt").on('click',editPipeline);
		       $(".deletee").on('click',deletePipeline);
		       $(".addd").on('click',{name:name},addStation);
		       $(".operatee").on('click',operateStation);
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

function operateStation()
{
	var id = $(this).closest('tr').attr('data-id');
	var html='<div><div class="table"><table border="0">';
	html += '<thead><tr><td></td><td>工位名称</td><td>工位编号</td><td>操作</td><td>设备标识</td><td>创建时间</td></tr></thead>';
	html += '<tbody>';
	$.ajax({
	type:'POST',
	url:basePath+'engineer/listStation.html',
	data:{pipelineId:id},
	dataType:'json',
	timeout:100000,
	success:function(data){
		if(data.success){
			var jsonData=data.jsonData;
			for(var i in jsonData){
	       		html += '<tr>';
	       		html += '<td><input type="checkbox" name="box" value="'+jsonData[i].id+'"></td>';
	       		html += '<td>'+jsonData[i].name+'</td>';
	       		html += '<td>'+jsonData[i].number+'</td>';
	       		html += '<td>'+jsonData[i].address+'</td>';
	       		html += '<td>'+jsonData[i].createTime+'</td>';
	       		html += '</tr>';
		    }
			
			html += '</tbody></table></div>';
			html += '<button class="btn btn-default" type="button" id="doDelete">&nbsp;删除</button>';
			html += '<button class="btn btn-default" type="button" onclick="MU.close(this)">&nbsp;取消</button>';
			html += '</div>';
		    MU.alert(html,600,'流水线工位列表');
		    $("#doDelete").on('click',doDelete);
		}
	},
	error:function(request,status,error){
		window.location.reload();
	}
});
}

function doDelete()
{
	 var id="";
	 
	 $("input[name='box']").each(function(){
		 if($(this).is(":checked"))
		 {
			 id += ","+$(this).val();
		 }
	 });
	 
	 MU.msgTips("loading","执行中。。。");
	 $.ajax({
		type:'POST',
		url:basePath+'engineer/stationDelete.html',
		data:{ids:id},
		dataType:'json',
		timeout:100000,
		success:function(data){
			if(data.success)
			{
				MU.removeTips();
				MU.msgTips('success',data.jsonData);
				MU.close();
				refreshPipeline();
			}
			else
			{
				MU.removeTips();
				MU.msgTips('error',data.jsonData);
				MU.close();
			}
		},
		error:function(request,status,error){
			window.location.reload();
		}	   
	}); 
}


function editPipeline(){
	var id = $(this).closest('tr').attr('data-id');
	$.ajax({
		type:'POST',
		url:basePath+'engineer/getPipeline.html',
		data:{id:id},
		dataType:'json',
		timeout:100000,
		success:function(data){
			if(data.success){
				//{"id":1,"enabled":true,"authorities":[{"id":1,"userId":1,"role":"ROLE_ADMIN"},{"id":7,"userId":1,"role":"ROLE_ENGINEER"},{"id":2,"userId":1,"role":"ROLE_USER"}],"email":"admin@126.com","expiration":"2020-12-30 00:00:00","name":"王光明","account":"admin","locked":false,"group":1,"password":"admin","mobile":"13584071111"}
				var dataa=data.jsonData;
				var html='<div class="dbxx-tcc"><div class="dbxx-tcc-cont"><input type="hidden" id="pipelineId" value="'+dataa.id+'"/>';
			    html+='<p><span class="dbxx-tcc-wz">流水线名称：</span><input type="text" id="pipelineName" value="'+dataa.name+'" placeholder="流水线名称" class="dbxx-tcc-wb" /></p>';
				html+='<p><span class="dbxx-tcc-wz">流水线编号：</span><input type="text" onblur="checkData.isNumSpan(\'pipelineNumber\');" id="pipelineNumber" value="'+dataa.number+'" placeholder="密 码" class="dbxx-tcc-wb" /></p>';
			    html+='<p><span class="dbxx-tcc-wz">工位数：</span><input type="text" id="pipelineStations" value="'+dataa.stations+'" disabled="disabled" class="dbxx-tcc-wb" /></p>';
			    html+='<p><span class="dbxx-tcc-wz">创建时间：</span><input type="text" id="createTime" value="'+dataa.createTime+'" disabled="disabled" class="dbxx-tcc-wb" /></p>';
			    html+='<p style="height:100px;"><span class="dbxx-tcc-wz">简介：</span><textarea id="desc" rows="7" value="'+dataa.desc+'" placeholder="简介" class="dbxx-tcc-wb" ></textarea></p>';
			    html+='<p class="cz-cont"><a href="javascript:void(0);" class="cz-btn" id="doUpdate">修改</a><a href="javascript:void(0);" onclick="MU.close(this)" class="cz-btn">取消</a></p>';
				html+='</div></div>';
			    MU.alert(html,600,'编辑流水线');
			    $("#doUpdate").on('click',doUpdate);
			}
		},
		error:function(request,status,error){
			window.location.reload();
		}
	});
}

function doUpdate()
{
	var id = $("#pipelineId").val();
	var name = $("#pipelineName").val();
	var number = $("#pipelineNumber").val();
	var stations = $("#pipelineStations").val();
	if(!checkData.isNum("pipelineNumber"))
	{
		MU.msgTips('warn','流水线编号必须为数字');
		$("#pipelineNumber").focus();
		return;
	}
	var desc = $("#desc").val();
	MU.msgTips("loading","执行中。。。");
	$.ajax({
		type:'POST',
		url:basePath+'engineer/pipelineUpdate.html',
		data:{id:id,name:name,number:number,stations:stations,desc:desc},
		dataType:'json',
		timeout:100000,
		success:function(data){
			if(data.success)
			{
				MU.removeTips();
				MU.msgTips('success',data.jsonData);
				MU.close();
				refreshPipeline();
			}
			else
			{
				MU.removeTips();
				MU.msgTips('error',data.jsonData);
				MU.close();
			}
		},
		error:function(request,status,error){
			window.location.reload();
		}	   
	});
}

function addStation(event)
{
	var id = $(this).closest('tr').attr('data-id');
	var html='<div class="dbxx-tcc"><div class="dbxx-tcc-cont">';
    html+='<p><span class="dbxx-tcc-wz">工位名称：</span><input type="text" id="stationName" value="" placeholder="工位名称" class="dbxx-tcc-wb" /></p>';
	html+='<p><span class="dbxx-tcc-wz">工位编号：</span><input type="text" id="stationNumber" onblur="checkData.isNumSpan(\'stationNumber\')" value="" placeholder="工位编号" class="dbxx-tcc-wb" /></p>';
    html+='<p><span class="dbxx-tcc-wz">设备标识：</span><input type="text" id="address" value="" placeholder="设备标识" class="dbxx-tcc-wb" /></p>';
    html+='<p style="height:120px;"><span class="dbxx-tcc-wz">工位简介：</span><textarea id="desc" rows="7" value="" placeholder="工位简介" class="dbxx-tcc-wb" ></textarea></p>';
    html+='<p class="cz-cont"><a href="javascript:void(0);" class="cz-btn" id="doAdd">添加</a><a href="javascript:void(0);" onclick="MU.close(this)" class="cz-btn">取消</a></p>';
	html+='</div></div>';
    MU.alert(html,600,'新增工位');
    $("#doAdd").on('click',{pipelineId:id},doAdd);
}

function doAdd(event)
{
	var name = $.trim($("#stationName").val());
	if(name==''){
		MU.msgTips('warn','请输入工位名称');
		$("#stationName").focus();
		return;
	}
	var number = $.trim($("#stationNumber").val());
	if(number==''){
		MU.msgTips('warn','请输入工位编号');
		$("#stationNumber").focus();
		return;
	}
	if(!checkData.isNum("stationNumber")){
		MU.msgTips('warn','工位编号必须为数字');
		$("#stationNumber").focus();
		return;
	}
	var address = $.trim($("#address").val());
	if(address==''){
		MU.msgTips('warn','请输入设备地址');
		$("#address").focus();
		return;
	}
	var desc = $.trim($("#desc").val());
	
	MU.msgTips("loading","执行中。。。");
	$.ajax({
		type:'POST',
		url:basePath+'engineer/stationAdd.html',
		data:{name:name,number:number,pipelineId:event.data.pipelineId,address:address,desc:desc},
		dataType:'json',
		timeout:100000,
		success:function(data){
			if(data.success)
			{
				MU.removeTips();
				MU.msgTips('success',data.jsonData);
				MU.close();
				refreshPipeline();
			}
			else
			{
				MU.removeTips();
				MU.msgTips('error',data.jsonData);
				MU.close();
			}
		},
		error:function(request,status,error){
			window.location.reload();
		}	   
	});
}
