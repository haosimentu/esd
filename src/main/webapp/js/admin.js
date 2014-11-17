$(function(){
	queryUser();
	$("#searchUser").on('click',searchUser);
	$("#addUser").on('click',addUser);
});
function searchUser()
{
	var keyword = $.trim($("#keyword").val());
//	if(keyword==''){
//		MU.msgTips('warn','请输入要搜索的用户名');
//		$("#keyword").focus();
//		return;
//	}
	
	queryUser(keyword);
}

function refreshUser()
{
	var keyword = $.trim($("#keyword").val());
	if(keyword == '')
	{
		queryUser();
	}
	else
	{
		queryUser(keyword);
	}
}

function addUser()
{
	var html='<div class="dbxx-tcc"><div class="dbxx-tcc-cont">';
    html+='<p><span class="dbxx-tcc-wz">用户名：</span><input type="text" id="username" value="" placeholder="用户名" class="dbxx-tcc-wb" /></p>';
	html+='<p><span class="dbxx-tcc-wz">帐 号：</span><input type="text" id="account" value="" class="dbxx-tcc-wb" /></p>';
    html+='<p><span class="dbxx-tcc-wz">密 码：</span><input type="password" id="password" placeholder="密 码" value="" class="dbxx-tcc-wb" /></p>';
    html+='<p><span class="dbxx-tcc-wz">邮 箱：</span><input type="text" id="email" placeholder="邮 箱" value="" class="dbxx-tcc-wb" /></p>';
    html+='<p><span class="dbxx-tcc-wz">手机号：</span><input type="text" id="mobile" placeholder="手机号码" value="" class="dbxx-tcc-wb" /></p>';
    html+='<div class="checkvalue"><div class="checkoption"><label><input id="isAdmin" type="checkbox" value="admin" > 管理员</label></div>';
    html+='<div class="checkoption"><label><input type="checkbox" id="isEngineer" value="engineer"> 工程师</label></div>';
    html+='<div class="checkoption"><label><input type="checkbox" id="isUser" value="user"> 用户</label></div></div><p></p>';
    html+='<p class="cz-cont"><a href="javascript:void(0);" class="cz-btn" id="doAdd">添加</a><a href="javascript:void(0);" onclick="MU.close(this)" class="cz-btn">取消</a></p>';
	html+='</div></div>';
    MU.alert(html,600,'新增用户');
    $("#doAdd").on('click',doAdd);
}

function doAdd()
{
	var username = $.trim($("#username").val());
	if(username==''){
		MU.msgTips('warn','请输入用户名');
		$("#username").focus();
		return;
	}
	var account = $.trim($("#account").val());
	if(account==''){
		MU.msgTips('warn','请输入用账号');
		$("#account").focus();
		return;
	}
	var password = $.trim($("#password").val());
	if(password==''){
		MU.msgTips('warn','请输入用密码');
		$("#password").focus();
		return;
	}
	var email = $.trim($("#email").val());
	if(email!='' && !checkData.isEmail("email"))
	{
		MU.msgTips('warn','邮箱格式不正确');
		$("#email").focus();
		return;
	}
	
	var mobile = $.trim($("#mobile").val());
	var adminRole = $("#isAdmin").is(":checked") ? 'ROLE_ADMIN' : '';
	var engineerRole = $("#isEngineer").is(":checked") ? 'ROLE_ENGINEER' : '';
	var userRole = $("#isUser").is(":checked") ? 'ROLE_USER' : '';
	var roles = adminRole+"|"+engineerRole+"|"+userRole;
	if(roles=='||'){
		MU.msgTips('warn','请选择用户角色');
		return;
	}
	MU.msgTips("loading","执行中。。。");
	$.ajax({
		type:'POST',
		url:basePath+'admin/accountAdd.html',
		data:{username:username,account:account,password:password,email:email,mobile:mobile,roles:roles},
		dataType:'json',
		timeout:100000,
		success:function(data){
			MU.removeTips();
			MU.msgTips('success',data.jsonData);
			MU.close();
			refreshUser();
		},
		error:function(request,status,error){
			window.location.reload();
		}	   
	});
}

function editUser(){
	var id = $(this).closest('tr').attr('data-id');
	$.ajax({
		type:'POST',
		url:basePath+'admin/getUser.html',
		data:{id:id},
		dataType:'json',
		timeout:100000,
		success:function(data){
			if(data.success){
				//{"id":1,"enabled":true,"authorities":[{"id":1,"userId":1,"role":"ROLE_ADMIN"},{"id":7,"userId":1,"role":"ROLE_ENGINEER"},{"id":2,"userId":1,"role":"ROLE_USER"}],"email":"admin@126.com","expiration":"2020-12-30 00:00:00","name":"王光明","account":"admin","locked":false,"group":1,"password":"admin","mobile":"13584071111"}
				var dataa=data.jsonData;
				var html='<div class="dbxx-tcc"><div class="dbxx-tcc-cont"><input type="hidden" id="userid" value="'+dataa.id+'"/>';
			    html+='<p><span class="dbxx-tcc-wz">用户名：</span><input type="text" id="username" value="'+dataa.name+'" placeholder="用户名" class="dbxx-tcc-wb" /></p>';
				html+='<p><span class="dbxx-tcc-wz">帐 号：</span><input type="text" disabled="disabled" id="acount" value="'+dataa.account+'" class="dbxx-tcc-wb" /></p>';
			    html+='<p><span class="dbxx-tcc-wz">密 码：</span><input type="password" id="password" placeholder="密 码" value="'+dataa.password+'" class="dbxx-tcc-wb" /></p>';
			    html+='<p><span class="dbxx-tcc-wz">邮 箱：</span><input type="text" id="email" placeholder="邮 箱" value="'+dataa.email+'" class="dbxx-tcc-wb" /></p>';
			    html+='<p><span class="dbxx-tcc-wz">手机号：</span><input type="text" id="mobile" placeholder="手机号码" value="'+dataa.mobile+'" class="dbxx-tcc-wb" /></p>';
				
			    var authorities = dataa.authorities;
		        var isAdmin=false;
		        var isEngineer = false;
		        var isUser=false;
		        for(var k in authorities){
			        var role= authorities[k].role;
			        if(role=="ROLE_ADMIN"){
			           isAdmin=true;
			        }
			        if(role=="ROLE_ENGINEER"){
			        	isEngineer=true;
			        }
			        if(role=="ROLE_USER"){
			        	isUser=true;
			        }
			    }
			    var adminChecked = isAdmin ? ' checked="checked" ' : '';
			    var engineerChecked = isEngineer ? ' checked="checked" ' : '';
			    var userChecked = isUser ?  ' checked="checked" ' : '';
			    html+='<div class="checkvalue"><div class="checkoption"><label><input id="isAdmin" type="checkbox" value="admin" ' + adminChecked + ' > 管理员</label></div>';
			    html+='<div class="checkoption"><label><input type="checkbox" id="isEngineer" value="engineer" ' + engineerChecked + '> 工程师</label></div>';
			    html+='<div class="checkoption"><label><input type="checkbox" id="isUser" value="user" ' + userChecked + '> 用户</label></div></div><p></p>';
			    html+='<p class="cz-cont"><a href="javascript:void(0);" class="cz-btn" id="doUpdate">修改</a><a href="javascript:void(0);" onclick="MU.close(this)" class="cz-btn">取消</a></p>';
				html+='</div></div>';
			    MU.alert(html,600,'编辑用户');
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
	var userid = $("#userid").val();
	var username = $("#username").val();
	var password = $("#password").val();
	var email = $("#email").val();
	var mobile = $("#mobile").val();
	var adminRole = $("#isAdmin").is(":checked") ? 'ROLE_ADMIN' : '';
	var engineerRole = $("#isEngineer").is(":checked") ? 'ROLE_ENGINEER' : '';
	var userRole = $("#isUser").is(":checked") ? 'ROLE_USER' : '';
	var roles = adminRole+"|"+engineerRole+"|"+userRole;
	MU.msgTips("loading","执行中。。。");
	$.ajax({
		type:'POST',
		url:basePath+'admin/accountUpdate.html',
		data:{userid:userid,username:username,password:password,email:email,mobile:mobile,roles:roles},
		dataType:'json',
		timeout:100000,
		success:function(data){
			MU.removeTips();
			MU.msgTips('success',data.jsonData);
			MU.close();
			refreshUser();
		},
		error:function(request,status,error){
			window.location.reload();
		}	   
	});
}


function deleteUser(){
	var id = $(this).closest('tr').attr('data-id');
	 MU.msgTips("loading","执行中。。。");
	 $.ajax({
		type:'POST',
		url:basePath+'admin/accountDelete.html',
		data:{id:id},
		dataType:'json',
		timeout:100000,
		success:function(data){
			MU.removeTips();
			MU.msgTips('success',data.jsonData);
			refreshUser();
		},
		error:function(request,status,error){
			window.location.reload();
		}	   
	}); 
}

function queryUser(keyword){
	var data={};
	if(keyword != null)
	{
		data['keyword']=keyword;
	}
	
	var urlPath=basePath + 'admin/accountQuery.html';
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
		       		html += '<td>'+jsonData[i].account+'</td>';
		       		html += '<td>'+jsonData[i].password+'</td>';
		       		html += '<td>'+jsonData[i].email+'</td>';
		       		html += '<td>'+jsonData[i].mobile+'</td>';
		       	    var authority=jsonData[i].authorities;
			        var roleName="";
			        for(var k in authority){
			        	 var role= authority[k].role;
			        	 if(role=="ROLE_ADMIN"){roleName+="[系统管理员] ";}
			        	 if(role=="ROLE_ENGINEER"){ roleName+="[工程师 ] ";}
			        	 if(role=="ROLE_USER"){ roleName+="[普通用户] ";}
			        }
			        html+='<td>'+roleName+'</td>';
		       		html+='<td><a class="editUser" href="javascript:;"><span></span>编辑&nbsp;</a><a class="deleteUser" href="javascript:;"><span></span>&nbsp;删除</a></td></tr>';
//		       		html += '<td><input type="text" value="多个标签请用分号分隔" class="bq-txt" data-id="'+jsonData[i].id+'"><a href="javascript:;" data-id="'+jsonData[i].id+'" class="bq-btn">打标</a></td></tr>';
		       		$("#tableList").append(html);
		       }
			   //操作返回的数据
		       $(".editUser").on('click',editUser);
		       $(".deleteUser").on('click',deleteUser);
		       
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