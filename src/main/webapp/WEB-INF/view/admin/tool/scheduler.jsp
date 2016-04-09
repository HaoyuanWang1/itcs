<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${basePath}" />
<div style="width: 1000px; margin: 10px auto;">
	<ul class="breadcrumb">
	  <li><a href="#">系统设置</a> <span class="divider">/</span></li>
	  <li class="active">定时任务管理</li>
	</ul>
	<table class="table table-bordered table-hover table-condensed">
		<thead>
			<tr>
				<th>任务ID</th>
				<th>执行规则</th>
				<th>是否在用</th>
				<th>描述信息</th>
				<th>上次执行时间</th>
				<th>下次执行时间</th>
				<th>执行完毕状态</th>
				<th width="110">
					操作<input type="button" onclick="editScheduler('','','','','','','','','')" value="新增"/>
				</th>
			</tr>
		</thead>
		<tbody>
		  <c:forEach items="${list}" var="item">
		    <tr>
				<td>${item.jobId}</td>
				<td>${item.cronExpression}</td>
				<td>${item.jobStatus}</td>
				<td>${item.jobName}</td>
				<td>${item.lastExecuteTime}</td>
				<td>${item.nextExecuteTime}</td>
				<td>${item.lastStatus}</td>
			 	<td width="150">
			 		<input type="button" onclick="editScheduler('${item.jobId}','${item.cronExpression}','${item.jobStatus}','${item.jobName}','${item.lastExecuteTime}','${item.nextExecuteTime}','${item.lastStatus}','${item.successMail}','${item.failMail}')" value="编辑"/>
				</td>
			</tr>
		  </c:forEach>
		</tbody>
	</table>

<div id="schedulerEidtDiv" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h2 id="myModalLabel">定时任务编辑</h3>
  </div>
  <div class="modal-body">
  	<form id="schedulerForm">
  	 	<label class="control-label" for="jobId">任务ID</label>
	  	<input id="jobId" name="jobId" type="text"/>
	  	<label class="control-label" for="cronExpression">执行时间</label>
	  	<input id="cronExpression" name="cronExpression" type="text"/>
	  	<label class="control-label" for="jobStatus">启用标识</label>
	  	<select id="jobStatus" name="jobStatus">
	  		<option value="1">是</option>
	  		<option value="0">否</option>
	  	</select>
	  	<label class="control-label" for="jobName">任务备注</label>
	  	<input id="jobName" name="jobName" type="text"/>
  		<input id="lastExecuteTime" name="lastExecuteTime" type="hidden"/>
  		<input id="nextExecuteTime" name="nextExecuteTime" type="hidden"/>
  		<input id="lastStatus" name="lastStatus" type="hidden"/>
  		<input id="successMail" name="successMail" type="hidden"/>
  		<input id="failMail" name="failMail" type="hidden"/>
  		<input id="id" name="id" type="hidden"/>
  	</form>
  </div>
  <div class="modal-footer">
    <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
    <button class="btn btn-primary" onclick="saveScheduler();">保存</button>
  </div>
</div>
</div>
<script type="text/javascript">
	var webContext = "${basePath}";
	function editScheduler(jobId,cronExpression,jobStatus,jobName,lastExecuteTime,nextExecuteTime,lastStatus,successMail,failMail) {
		$("#jobId").val(jobId);
		$("#cronExpression").val(cronExpression);
		$("#jobStatus").val(jobStatus);
		$("#jobName").val(jobName);
		$("#lastExecuteTime").val(lastExecuteTime);
		$("#nextExecuteTime").val(nextExecuteTime);
		$("#lastStatus").val(lastStatus);
		$("#successMail").val(successMail);
		$("#failMail").val(failMail);
		$('#schedulerEidtDiv').modal('show');
	}
	function saveScheduler(){
		$.post("${ctx}/admin/scheduler/save",
			$('#schedulerForm').serialize(),
			function(data){
				$('#schedulerEidtDiv').modal('hide');
				window.location.reload();
		});
	}
</script>