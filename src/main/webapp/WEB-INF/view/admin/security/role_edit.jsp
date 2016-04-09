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
	<title>角色管理</title>
	<style type="text/css">
	.ztree li span.button.menuIcon_ico_open, .ztree li span.button.menuIcon_ico_close, .ztree li span.button.menuIcon_ico_docu{
		margin-right:2px; background: url(${ctx}/static/core/ztree/css/img/diy/1_close.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
	.ztree li span.button.linkIcon_ico_open, .ztree li span.button.linkIcon_ico_close, .ztree li span.button.linkIcon_ico_docu{
		margin-right:2px; background: url(${ctx}/static/core/ztree/css/img/diy/3.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
	</style>
</head>
<body>
	
	<form id="roleEditForm" class="form-horizontal" method="post">
      	<div class="form-part">
      		<h3 class="form-title"><i class="iconfont">&#xe642;</i> 基本信息</h3>
      		<div class="form-group">
      			<div class="control-groups">
					<div class="control-group">
						<label class="control-label" for="rid">角色KEY：</label>
						<div class="controls">
							<%-- <form:input path="rid" ruler="{must:1}"/> --%>
							  <INPUT TYPE="text" NAME="rid" value="${role.rid }" id="roleKey">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="name">角色名称：</label>
						<div class="controls">
						<%-- 	<form:input path="name" ruler="{must:1}"/> --%>
						  <INPUT TYPE="text" NAME="name" value="${role.name }" id="name">
						</div>
					</div>
				</div>
			</div>
      	</div>
      	
      	<div class="form-part">
      		<h3 class="form-title"><i class="iconfont">&#xe642;</i> 权限设置</h3>
      		<div class="form-group">
      			<ul id="resourceTree" class="ztree"></ul>
      		</div>
      	</div>
      	
     <%--  	<form:hidden path="id"/> --%>
      	<input type=hidden name='id' value="${role.id }" id="id">
      	
    <div class="form-operate">
       <button type="button" class="btn btn-diy" data-listen="saveRole" data-target="#roleEditForm">保存</button>				
   </div>
   
	</form>
	
   
	<script type="text/javascript">
			var rList = [];
	</script>
	<c:forEach items="${resources }" var="item" varStatus="status">
	<script type="text/javascript">
		rList['${status.index}'] = '${item.id}';
	</script>
	</c:forEach>
	<script type="text/javascript">F.run('static/js/role/role_edit');</script>
</body>
</html>