<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="/WEB-INF/tags/flamingo-form.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${basePath}" />
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	<h3 id="myModalLabel">系统属性编辑</h3>
</div>
<div class="modal-body">
	<form:form id="propertyForm" class="form-horizontal" methodParam="requestMethod" modelAttribute="appProperty">
		<div class="control-group">
			<label class="control-label">属性名称：</label>
			<div class="controls">
				<form:input path="name" cssClass="span4"></form:input>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">属性描述：</label>
			<div class="controls">
				<form:input path="descn" cssClass="span4"></form:input>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">属性值：</label>
			<div class="controls">
				<form:textarea path="value" cssClass="span4"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否加密：</label>
			<div class="controls">
				<label class="radio inline">
					<input type="radio" name="encrytFlag" value="1" <c:if test="${appProperty.encrytFlag==1}">checked</c:if> /> 是
				</label>
				<label class="radio inline">
					<input type="radio" name="encrytFlag" value="0" <c:if test="${appProperty.encrytFlag!=1}">checked</c:if> /> 否
				</label>
			</div>
		</div>
		<form:hidden path="id"/>
	</form:form>
</div>
<div class="modal-footer">
	<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
	<button class="btn btn-primary" data-listen="saveProperty" data-target="#propertyForm">保存</button>
</div>