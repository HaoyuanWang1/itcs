<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="/WEB-INF/tags/flamingo-form.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${basePath}" />
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	<h3 id="myModalLabel">定时任务编辑</h3>
</div>
<div class="modal-body">
	<form:form id="schedulerJobForm" class="form-horizontal" methodParam="requestMethod" modelAttribute="schedulerJob">
		<div class="control-group">
			<label class="control-label">任务ID：</label>
			<div class="controls">
				<form:input path="jobId" cssClass="span4"></form:input>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">执行时间：</label>
			<div class="controls">
				<form:input path="cronExpression" cssClass="span4"></form:input>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">启用标识：</label>
			<div class="controls">
			<select name="jobStatus">
		  		<option value="1">是</option>
		  		<option value="0">否</option>
		  	</select>
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">任务备注：</label>
			<div class="controls">
				<form:input path="jobName" cssClass="span4"></form:input>
			</div>
		</div>
		<form:hidden path="lastExecuteTime"/>
		<form:hidden path="nextExecuteTime"/>
		<form:hidden path="lastStatus"/>
		<form:hidden path="successMail"/>
		<form:hidden path="failMail"/>
	</form:form>
</div>
<div class="modal-footer">
	<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
	<button class="btn btn-primary" data-listen="saveScheduler" data-target="#schedulerJobForm">保存</button>
</div>
