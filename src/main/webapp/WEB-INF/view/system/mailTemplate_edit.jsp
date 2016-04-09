<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="/WEB-INF/tags/flamingo-form.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${basePath}" />
<html>
	<head>
		<title>邮件模板管理</title>
	</head>
	<body>
		<ul class="breadcrumb">
   			<li><a href="javascript:void(0)">系统设置</a> <span class="divider">/</span></li>
   			<li class="active">邮件模板编辑</li>
   		</ul>
   		<form:form id="mtForm" action="${ctx}/system/mailTemplate/save" class="form-horizontal" methodParam="requestMethod" modelAttribute="mt">
			<div class="control-group">
				<label class="control-label">模板ID：</label>
				<div class="controls">
					<form:input path="code"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">邮件标题：</label>
				<div class="controls">
					<form:input path="title" class="span8"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">模板描述：</label>
				<div class="controls">
					<form:input path="descn" class="span8"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">邮件内容：</label>
				<div class="controls">
					<form:textarea path="content" data-editor="{mode: 'mail', width:'620px', height: '400px'}"/>
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<input type="submit" class="btn" value="保存" />
				</div>
			</div>
			<form:hidden path="id"/>
		</form:form>
	</body>
</html>
