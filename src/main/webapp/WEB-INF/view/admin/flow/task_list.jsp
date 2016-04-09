<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %> --%>
<c:set var="ctx" value="${basePath}" />

<div style="width: 1000px; margin: 10px auto;">
	<ul class="breadcrumb">
	  <li><a href="#">流程管理</a> <span class="divider">/</span></li>
	  <li class="active">任务控制</li>
	</ul>
	<form id="searchForm" class="form-inline" action="${ctx}/user/page">
	  	<label class="control-label" >用户ITCODE</label>
	  	<input type="text" name="search_LIKE_uid" class="span2" value="${searchParam.search_LIKE_uid}">
		<label class="control-label" >用户姓名</label>
	  	<input type="text" name="search_LIKE_userName" class="span2" value="${searchParam.search_LIKE_userName}">
	    <input type="submit" class="btn" value="查询"/>
	</form>
	<table class="table table-bordered table-hover table-condensed">
		<thead>
			<tr>
				<th>ID</th>
				<th>UID</th>
				<th>用户名</th>
				<th>用户密码</th>
				<th>是否可用</th>
				<th width="110">
					操作
				</th>
			</tr>
		</thead>
		<tbody id="userReuslt">
		  <c:forEach items="${page.content}" var="user">
		    <tr>
		    	<td>${user.id}</td>
				<td>${user.uid}</td>
				<td>${user.userName}</td>
				<td>${user.password}</td>
				<td>${user.enabled}</td>
			 	<td width="150">
				<a href="javascript:;" class="table-btn" onclick=" editUser('${user.uid}'); "> 编辑 </a>
				</td> 	
			</tr>
		  </c:forEach>
		</tbody>
	</table>
	<tags:pagination page="${page}" paginationSize="5"/>

	<div id="user_modal" class="modal hide fade" tabindex="-1" >
	  <div class="modal-header">
	    <h3 id="myModalLabel">用户信息</h3>
	  </div>
	  <div id="user_modal_body" class="modal-body">
	  </div>
	  <div class="modal-footer">
	  	<input type="button" class="btn btn-primary" onclick="saveUser();" value="保存"/>
	    <input type="button" class="btn" data-dismiss="modal" value="关闭"/>
	  </div>
	</div>

</div>
<script type="text/javascript" >
	function editUser(uid){
		$("#user_modal_body").load("${ctx}/user/panel/"+uid);
		$('#user_modal').modal('show');
	}
	function saveUser(){
		$.post("${ctx}/user/save",
			$('#userForm').serialize(),
			function(data){
				$('#user_modal').modal('hide');
				window.location.reload();
		});
	}
</script>