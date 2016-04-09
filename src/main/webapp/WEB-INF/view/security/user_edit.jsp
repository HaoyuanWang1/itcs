<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="/WEB-INF/tags/flamingo-form.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${basePath}" />
<div class="modal-header">
	<span class="close" data-dismiss="modal" aria-hidden="true">&times;</span>
	<h3>编辑用户</h3>
</div>
<div class="modal-body">
	<form:form id="userInfoForm" class="form-horizontal" methodParam="requestMethod" modelAttribute="user"  validate="{debug:true}">
		<div class="control-group">
			<label class="control-label" for="uid">用户ID：</label>
			<c:choose>
				<c:when test="${!empty user.uid}">
					<div class="controls form-text">${user.uid}<form:hidden path="uid"/></div>
				</c:when>
				<c:otherwise>
					<div class="controls"><form:input path="uid" ruler="{must:1}"/></div>
				</c:otherwise>
			</c:choose>
		</div>
		<div class="control-group">
			<label class="control-label" for="userName">用户名称：</label>
			<div class="controls">
				<form:input path="userName" ruler="{must:1}"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="tenant.id">所属租户：</label>
			<div class="controls">
				<input type="hidden" data-ruler="{must:1}" id="tenantSelect" name="tenant.id" value="${user.tenant.id}" data-autocomplete="${user.tenant.code}/${user.tenant.name}"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否可用：</label>
			<div class="controls">
				<label class="radio inline">
					<input type="radio" name="enabled" value="1" <c:if test="${user.enabled==1}">checked</c:if> checked/>  是
				</label>
				<label class="radio inline">
					<input type="radio" name="enabled" value="0" <c:if test="${user.enabled==0}">checked</c:if> />  否
				</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="tel">手机：</label>
			<div class="controls">
				<form:input path="tel" ruler="{must:1}"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="mobile">座机：</label>
			<div class="controls">
				<form:input path="mobile" ruler="{must:1}"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="email">邮箱：</label>
			<div class="controls">
				<form:input path="email" ruler="{must:1}"/>	
			</div>
		</div>
		<form:hidden path="id"/>
		<form:hidden path="password"/>
	</form:form>
</div>
<div class="modal-footer">
	<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
    <button class="btn btn-primary" data-listen="saveUser" data-target="#userInfoForm">保存</button>
</div>