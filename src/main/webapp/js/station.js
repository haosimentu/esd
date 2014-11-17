$(function(){
	queryStation();
	$("#searchStation").on('click',searchStation);
	$("#addStation").on('click',addStation);
});

function searchStation()
{
	var keyword = $.trim($("#keyword").val());
//	if(keyword==''){
//		MU.msgTips('warn','请输入要搜索的用户名');
//		$("#keyword").focus();
//		return;
//	}
	
	queryStation(keyword);
}

function refreshStation()
{
	var keyword = $.trim($("#keyword").val());
	if(keyword == '')
	{
		queryStation();
	}
	else
	{
		queryStation(keyword);
	}
}

function addStation()
{
	var html='<div class="dbxx-tcc"><div class="dbxx-tcc-cont">';
    html+='<p><span class="dbxx-tcc-wz">工位名称：</span><input type="text" id="stationName" value="" placeholder="工位名称" class="dbxx-tcc-wb" /></p>';
	html+='<p><span class="dbxx-tcc-wz">工位编号：</span><input type="text" id="stationNumber" value="" placeholder="工位编号" class="dbxx-tcc-wb" /></p>';
    html+='<p><span class="dbxx-tcc-wz">归属流水线：</span><select id="pipelineId" value="" class="dbxx-tcc-wb"><option value="1">流水线1</option><option value="2">流水线2</option><option value="3">流水线3</option></select></p>';
    html+='<p><span class="dbxx-tcc-wz">设备地址：</span><input type="text" id="address" value="" placeholder="设备地址" class="dbxx-tcc-wb" /></p>';
    html+='<p><span class="dbxx-tcc-wz">传感器地址：</span><input type="text" id="subAddress" value="" placeholder="传感器地址" class="dbxx-tcc-wb" /></p>';
    html+='<p style="height:120px;"><span class="dbxx-tcc-wz">工位简介：</span><textarea id="desc" rows="7" value="" placeholder="工位简介" class="dbxx-tcc-wb" ></textarea></p>';
    html+='<p class="cz-cont"><a href="javascript:void(0);" class="cz-btn" id="doAdd">添加</a><a href="javascript:void(0);" onclick="MU.close(this)" class="cz-btn">取消</a></p>';
	html+='</div></div>';
    MU.alert(html,600,'新增工位');
    $("#doAdd").on('click',doAdd);
}

function doAdd()
{
	var name = $.trim($("#stationName").val());
	if(name==''){
		MU.msgTips('warn','请输入工位名称');
		$("#stationName").focus();
		return;
	}
	var number = $.trim($("#stationNumber").val());
	if(number==''||isNaN(val)){
		MU.msgTips('warn','请输入工位数字编号');
		$("#stationNumber").focus();
		return;
	}
	var pipelineId = $.trim($("#pipelineId").val());
	if(pipelineId==''){
		MU.msgTips('warn','请选择用归属流水线');
		$("#pipelineId").focus();
		return;
	}
	var address = $.trim($("#address").val());
	if(address==''){
		MU.msgTips('warn','请输入设备地址');
		$("#address").focus();
		return;
	}
	
	var address = $.trim($("#subAddress").val());
	if(address==''){
		MU.msgTips('warn','请输入传感器地址');
		$("#subAddress").focus();
		return;
	}
	var desc = $.trim($("#desc").val());
	
	MU.msgTips("loading","执行中。。。");
	$.ajax({
		type:'POST',
		url:basePath+'engineer/stationAdd.html',
		data:{name:name,number:number,pipelineId:pipelineId,address:address,subAddress:subAddress,desc:desc},
		dataType:'json',
		timeout:100000,
		success:function(data){
			MU.removeTips();
			MU.msgTips('success',data.jsonData);
			MU.close();
			refreshStation();
		},
		error:function(request,status,error){
			window.location.reload();
		}	   
	});
}

function editStation() {
	var id = $(this).closest('tr').attr('data-id');
	$.ajax({
		type : 'POST',
		url : basePath + 'engineer/getStation.html',
		data : {id : id},
		dataType : 'json',
		timeout : 100000,
		success : function(data) {
			if (data.success) {
				var dataa = data.jsonData;
				var html = '<div class="dbxx-tcc"><div class="dbxx-tcc-cont"><input type="hidden" id="stationId" value="' + dataa.id + '"/>';
				html += '<p><span class="dbxx-tcc-wz">工位名称：</span><input type="text" id="name" value="' + dataa.name + '" placeholder="工位名称" class="dbxx-tcc-wb" /></p>';
				html += '<p><span class="dbxx-tcc-wz">工位编号：</span><input type="text" id="number" value="' + dataa.number + '" placeholder="工位编号" class="dbxx-tcc-wb" /></p>';
				html += '<p><span class="dbxx-tcc-wz">流水线：</span><input type="text" id="pipelineId" placeholder="流水线" value="' + dataa.pipelineId + '" class="dbxx-tcc-wb" /></p>';
				html += '<p><span class="dbxx-tcc-wz">设备标识：</span><input type="text" id="address" placeholder="设备标识" value="' + dataa.address + '" class="dbxx-tcc-wb" /></p>';
				html += '<p><span class="dbxx-tcc-wz">工位简介：</span><input type="text" id="desc" placeholder="工位简介" value="' + dataa.desc + '" class="dbxx-tcc-wb" /></p>';

				html += '<p class="cz-cont"><a href="javascript:void(0);" class="cz-btn" id="doUpdate">修改</a><a href="javascript:void(0);" onclick="MU.close(this)" class="cz-btn">取消</a></p>';
				html += '</div></div>';
				MU.alert(html, 600, '编辑工位');
				$("#doUpdate").on('click', doUpdate);
			}
		},
		error : function(request, status, error) {
			window.location.reload();
		}
	});
}

function doUpdate()
{
	var id = $("#stationId").val();
	var name = $("#name").val();
	var number = $("#number").val();
	var pipelineId = $("#pipelineId").val();
	var address = $("#address").val();
	var desc = $("#desc").val();
	MU.msgTips("loading","执行中。。。");
	$.ajax({
		type:'POST',
		url:basePath+'engineer/stationUpdate.html',
		data:{id:id,name:name,number:number,pipelineId:pipelineId,address:address,desc:desc},
		dataType:'json',
		timeout:100000,
		success:function(data){
			MU.removeTips();
			MU.msgTips('success',data.jsonData);
			MU.close();
			refreshStation();
		},
		error:function(request,status,error){
			window.location.reload();
		}	   
	});
}


function deleteStation(){
	var id = $(this).closest('tr').attr('data-id');
	 MU.msgTips("loading","执行中。。。");
	 $.ajax({
		type:'POST',
		url:basePath+'engineer/stationDelete.html',
		data:{ids:id},
		dataType:'json',
		timeout:100000,
		success:function(data){
			MU.removeTips();
			MU.msgTips('success',data.jsonData);
			refreshStation();
		},
		error:function(request,status,error){
			window.location.reload();
		}	   
	}); 
}

function queryStation(keyword){
	var data={};
	if(keyword != null)
	{
		data['keyword']=keyword;
	}
	
	var urlPath=basePath + 'engineer/stationQuery.html';
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
		       		html += '<td>'+jsonData[i].name+'</td>';
		       		html += '<td>'+jsonData[i].number+'</td>';
		       		html += '<td>'+jsonData[i].pipelineName+'</td>';
		       		var state = jsonData[i].state==1 ? "正常" : "异常";
		       		html += '<td>'+ state +'</td>';
		       		html += '<td>'+jsonData[i].address+'</td>';
		       		html += '<td>'+jsonData[i].subAddress+'</td>';
		       		html += '<td>'+jsonData[i].createTime+'</td>';
		       		html += '<td>'+jsonData[i].desc+'</td>';
		       		html+='<td><a class="editStation" href="javascript:;" class="cz-btn" >编辑&nbsp;</a><a class="deleteStation" href="javascript:;" class="cz-btn" >&nbsp;删除</a></td></tr>';
		       		$("#tableList").append(html);
		       }
		       $(".editStation").on('click',editStation);
		       $(".deleteStation").on('click',deleteStation);
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

