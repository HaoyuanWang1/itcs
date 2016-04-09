<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${basePath}"/>
<html>
<head>
	<title>密码修改</title>
	<script type="text/javascript">F.run('static/js/user/updatePwdPage');</script>
</head>
<body>

<form id="updatePwdPageForm" class="form-horizontal"  data-validate="{debug:true}"> 
	<div class="control-group">
		<label class="control-label" for="rid">旧密码：</label>
		<div class="controls">
		<input type="password" name="oldPassWord">
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="rid">新密码：</label>
		<div class="controls">
		<input type="password" name="newPassWord" >
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="rid">再输入一次新密码：</label>
		<div class="controls">
			<input type="password" name="newPassWordAgain" >
		</div>
	</div>
	<input type="hidden" name="id" value="${userInfoId}" id="userInfoId"> 
	<div class="control-group">
		<div class="controls">
		<button type="button" class="btn btn-primary" data-listen="saveUpdatePwdPageForm" data-target="#updatePwdPageForm">保存</button>
		</div>
	</div>
	
</form>

</body>
</html>