<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="process" uri="/WEB-INF/tags/process.tld"%>
<%@ taglib prefix="form" uri="/WEB-INF/tags/flamingo-form.tld"%>
<%@ taglib prefix="str" uri="/WEB-INF/tags/stringTag.tld"%>
<c:set var="ctx" value="${basePath}"/>
<html>
	<head>
		<title>${apply.applyName}</title>
	</head>
	<body>
	<ul class="breadcrumb">
		<li><a href="${ctx}/">首页</a> <span class="divider">/</span></li>
		<li class="active">代提问题</li>
	</ul>
		<process:progress processId="${apply.processId}" pdName="${apply.pdName}"></process:progress>
		<form:form id="eventForm" class="form-horizontal" methodParam="requestMethod" modelAttribute="apply" validate="{debug:true}">
	      	<div class="control-groups">
						<div class="control-group">
							<label class="control-label">提交人：</label>
							<div class="controls">
							 	<div class="form-text">${apply.createUser.userName}</div>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">提交时间：</label>
							<div class="controls">
								${apply.submitTime}
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="tenant.id">客户：</label>
							<div class="controls">
								<form:hidden path="tenant.id" id="bigType" value="${apply.createUser.tenant.id}" data-autocomplete="${apply.createUser.tenant.name}" ruler="{must:1}"></form:hidden>
				   				<input type="hidden" name="bigTypeText" id="bigTypeText" value="${bigType}">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="submitUser.id">客户账号：</label>
							<div class="controls">
								<%-- <input type="hidden" name="submitUser.id" id="smallType" value="${apply.submitUser.id}" data-autocomplete="${apply.submitUser.uid}/${apply.submitUser.userName}"> --%>
								<form:hidden path="submitUser.id" id="smallType" value="${apply.submitUser.id}" data-autocomplete="${apply.submitUser.uid}/${apply.submitUser.userName}" ruler="{must:1}"></form:hidden>
				   				<input type="hidden" name="smallTypeText" id="smallTypeText" value="${smallType}">
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="linkMan">联系人：</label>
							<div class="controls">
								<form:input path="linkMan" value="${apply.submitUser.userName}" ruler="{must:1}"></form:input>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="tel">电话：</label>
						    <div class="controls">
						    	<form:input path="tel" value="${apply.submitUser.mobile}" ruler="{must:1}"></form:input>
					        </div>
						</div>
						<div class="control-group">
							<label class="control-label" for="email">E-mail：</label>
			                 <div class="controls">
			                 	<form:input path="email" value="${apply.submitUser.email}" ruler="{must:1}"></form:input>
			                 </div>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label"  for="topic">主题：</label>
						<div class="controls">
							<form:input path="topic" class="span08" ruler="{must:1}"></form:input>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label"  for="content">详细描述：</label>
						<div class="controls">
							<form:textarea path="content"  class="span08" rows="5" id="content" maxlength="2000" ruler="{must:1}"/>
							<p class="muted help-block">最多输入<b>2000</b>个字。</p>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label"  for="attachment">附件：</label>
						<div class="controls">
							<form:hidden path="attachment" data-attachment="{target:'#fileList'}"/>
							<div id="fileList"></div> 
						</div>
					</div>
					<div class="control-groups">
						<div class="control-group">
							<label class="control-label"  for="mainType">信息类型：</label>
							<div class="controls">
								<select name="mainType" data-ruler="{require:1}">
									<option value="" selected>——请选择——</option>
									<option value="0" <c:if test="${mainType eq 0 }">selected="selected"</c:if>>故障</option>
									<option value="1" <c:if test="${mainType eq 1 }">selected="selected"</c:if>>咨询</option>
									<option value="2" <c:if test="${mainType eq 2 }">selected="selected"</c:if>>建议</option>
									<option value="3" <c:if test="${mainType eq 3 }">selected="selected"</c:if>>需求</option>
									<option value="4" <c:if test="${mainType eq 4 }">selected="selected"</c:if>>投诉</option>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"  for="submitType">信息来源：</label>
							<div class="controls">
								<select name="submitType" data-ruler="{require:1}">
									<option value="" selected>——请选择——</option>
									<option value="0" <c:if test="${submitType eq 0 }">selected="selected"</c:if>>电话</option>
									<option value="1" <c:if test="${submitType eq 1 }">selected="selected"</c:if>>邮件</option>
									<option value="2" <c:if test="${submitType eq 2 }">selected="selected"</c:if>>其他</option>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"  for="serviceType.id">服务类型：</label>
							<div class="controls">
								<select name="serviceType.id" data-ruler="{require:1}">
									<option></option>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"  for="serviceLevel.id">紧急程度：</label>
							<div class="controls">
								<select name="serviceLevel.id" data-ruler="{require:1}">
									<option></option>
								</select>
							</div>
						</div>
					</div>
			
			<form:hidden path="id" id="eventId"/>
			<form:hidden path="state"/>
			<form:hidden path="submitType"/>
			<form:hidden path="submitUser.id"/>
			<form:hidden path="singerIds"/>
			<form:hidden path="createUser.id"/>
			<form:hidden path="processId"/>
			<form:hidden path="submitTime"/>
			<form:hidden path="recentAction"/>
		 	<form:hidden path="recentUser"/>
			<form:hidden path="code"/>
			<form:hidden path="submitMode"/>
			<form:hidden path="isInstead"/>
			<form:hidden path="isWarningFlag"/>
		 <form:hidden path="isOverduFlag"/>
			<div class="text-right engineer-btn">		
				<input type="button" value="提交，转至服务经理" class="btn btn-primary" data-listen="submitApply" data-target="#eventForm" />				
			</div>
		</form:form>
		<script type="text/javascript">F.run('static/js/event/applyEdit');</script>
	</body>
</html>