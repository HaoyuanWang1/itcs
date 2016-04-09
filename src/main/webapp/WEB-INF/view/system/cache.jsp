<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${basePath}" />
<html>
	<head>
		<title>系统属性设置</title>
	</head>
	<body>
		<ul class="breadcrumb">
			<li><a href="javascript:void(0)">系统设置</a> <span class="divider">/</span></li>
			<li class="active">缓存管理</li>
		</ul>
		<form class="form-horizontal" >
		  <div class="control-group">
		    <label class="control-label" >缓存组：</label>
		    <div class="controls">
		      <input id="cacheGroup" type="text"/>
		    </div>
		  </div>
		  <div class="control-group">
		    <label class="control-label" >缓存KEY：</label>
		    <div class="controls">
		      <input id="cacheKey" type="text"/>
		    </div>
		  </div>
		  <div class="control-group">
		    <label class="control-label" >操作：</label>
		    <div class="controls">
		      <input type="button" class="btn btn-info" data-listen="clearByKey" value="按组/KEY清除">
		      <input type="button" class="btn btn-success" data-listen="clearByGroup" value="按组清除">
		      <input type="button" class="btn btn-warning" data-listen="clearAll" value="全部清除">
		    </div>
		  </div>
		</form>
		<script type="text/javascript">F.run('modules/system/cache');</script>
	</body>
</html>

