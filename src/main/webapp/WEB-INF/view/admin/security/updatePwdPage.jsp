<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="/WEB-INF/tags/flamingo-form.tld"%>
<%@ taglib prefix="str" uri="/WEB-INF/tags/stringTag.tld"%>
<c:set var="ctx" value="${basePath}"/>
<html>
<head>
	<title>密码修改</title>
	<script type="text/javascript">F.run('static/js/user/updatePwdPage');</script>
	
</head>
<body>

 <form id="updatePwdPageForm" class="form-horizontal"  data-validate="{debug:true}"> 
旧密码：<input type="text" name="oldPassWord" >
<br>
新密码：<input type="password" name="newPassWord" >
<br>
再输入一次新密码：<input type="password" name="newPassWordAgain" >
<br>
<input type="hidden" name="id" value="${userInfoId}" id="userInfoId"> 
<button type="button" class="btn btn-primary" data-listen="saveUpdatePwdPageForm" data-target="#updatePwdPageForm">保存</button>
</form>

</body>
</html>