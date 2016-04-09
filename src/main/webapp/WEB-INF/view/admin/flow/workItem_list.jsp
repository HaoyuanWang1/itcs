<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="page" uri="/WEB-INF/tags/pagerTag.tld"%>
<c:set var="ctx" value="${basePath}" />

<div style="width: 1000px; margin: 10px auto;">
	<ul class="breadcrumb">
	  <li><a href="#">流程管理</a> <span class="divider">/</span></li>
	  <li class="active">任务控制</li>
	</ul>
	<form id="searchForm" class="form-inline" action="${ctx}/admin/flowCtrl/workItemPage">
	  	<label class="control-label" >实体ID</label>
	  	<input type="text" name="search_EQ_entityId" class="span2" value="${searchParam.search_EQ_entityId}">
		<label class="control-label" >任务ID</label>
	  	<input type="text" name="search_EQ_taskId" class="span2" value="${searchParam.search_EQ_taskId}">
	  	<label class="control-label" >流程ID</label>
	  	<input type="text" name="search_EQ_processId" class="span2" value="${searchParam.search_EQ_processId}">
	  	<input type="hidden" name="search_EQ_taskState" value="1">
	    <input type="submit" class="btn" value="查询"/>
	</form>
	<table class="table table-bordered table-hover table-condensed"  style="font-size:12px;">
		<thead>
			<tr>
				<th>任务ID</th>
				<th>进入时间</th>
				<th>实体ID</th>
				<th>环节</th>
				<th>流程定义</th>
				<th>实例ID</th>
				<th>状态</th>
				<th width="110">
					操作
				</th>
			</tr>
		</thead>
		<tbody id="userReuslt">
		  <c:forEach items="${page.content}" var="item">
		    <tr>
		    	<td>${item.taskId}</td>
				<td>${item.createTime}</td>
				<td>${item.entityId}</td>
				<td>${item.nodeName}/${item.nodeDesc}</td>
				<td>${item.pdName}</td>
				<td>${item.processId}</td>
				<td>${item.taskState}</td>
			 	<td width="150">
			 		<c:if test="${item.taskState==1}">
					<div class="btn-group">
						<input type="button" class="btn btn-small" value="界面" onclick="open_task_ctrl_page('${item.taskId}');" />
						<input type="button" class="btn btn-small" value="审批" onclick="open_task_ctrl_sign('${item.taskId}');" />
						<input type="button" class="btn btn-small" value="驳回" onclick="open_task_ctrl_back('${item.taskId}');" />
						<input type="button" class="btn btn-small" value="中止" onclick="open_task_ctrl_end('${item.taskId}');" />
						<input type="button" class="btn btn-small" value="转办" onclick="open_task_ctrl_transfer('${item.taskId}');" />
						<input type="button" class="btn btn-small" value="自由" onclick="open_task_ctrl_free('${item.taskId}');" />
					</div>
					</c:if>
				</td> 	
			</tr>
		  </c:forEach>
		</tbody>
	</table>
	<page:outpage page="${page}" params="${searchParam}" url="/admin/flowCtrl/workItemPage" />

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