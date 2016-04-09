<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${basePath}" />
<html>
    <head>
        <title>客户登录</title>
        <link rel="stylesheet" href="${ctx}/static/css/login.css" />
    </head>
    <body>
 	<div class="login" id="login">
			<div class="login-banner">
				<img class="dc-logo" src="${ctx}/static/images/dc-logo.gif" alt="dcone"/>
				<h2>客户支持中心</h2>
			</div>
			<div class="login-form">
				<form class="form-horizontal" action="${ctx}/login" method="post">
					<div class="control-group">
						<label for="username" class="control-label">帐&emsp;号：</label>
						<div class="controls">
							<div class="input-prepend">
								<span class="add-on"><i class="icon-user"></i></span>
								<input type="text" name="username" id="username" class="span3" autocomplete="false">
							</div>
						</div>
					</div>
					<div class="control-group">
						<label for="password" class="control-label">密&emsp;码：</label>
						<div class="controls">
							<div class="input-prepend">
								<span class="add-on"><i class="icon-key"></i></span>
								<input type="password" name="password" id="password" class="span3" autocomplete="false"/>
							</div>
						</div>
					</div>
					<%
						String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
						if (error != null) {
					%>
					<div class="text-error"><i class="icon-exclamation-sign"></i> 登录失败，请重试</div>
					<%
						}
					%>
					<div class="control-group">
						<div class="controls">
							<button type="submit" class="btn btn-primary"><i class="icon-signin"></i> 登录</button>
							<br><br>
							<div class="text-info">
								<p class="btn-fp" onclick="forgetPs();">忘记密码？</p>
								<div class="forgetPs hide" id="forgetPs">
									<i class="corebefore"></i><i class="coreafter"></i>
									<span class="close" onclick="closePs();">&times;</span>
									<p>请联系您的客户经理或拨打<span class="concat">010-82707888-2</span></p>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
    	</div>
    	<div class="footer">
				<div class="footer-detail">
					<p><span class="footer-info">版权所有&emsp;神州数码控股有限公司</span>All contents are Copyright &copyright; Digital China Holdings Limited. All rights reserved</p>
				</div>
			</div> 
			<!--[if gte IE 8]> 
				<script type="text/javascript">
				    var clientHeight = document.body.clientHeight;
				    var viewHeight = (clientHeight-260)+'px';
				    var loginHeight = document.getElementById('login');
				    loginHeight.style.height=viewHeight;
				 </script>
			<![endif]-->  
			<script type="text/javascript">
			function forgetPs(){
				var $forgetPs = document.getElementById('forgetPs');
				$forgetPs.style.display='block';
			}
			function closePs(){
				var $forgetPs = document.getElementById('forgetPs');
				$forgetPs.style.display='none';
			}
			</script>
    </body>
    
    
</html>

