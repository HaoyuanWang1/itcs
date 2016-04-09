<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${basePath}" />
<html>
	<head>
		<title>资源管理</title>
		<style type="text/css">
		.ztree li span.button.menuIcon_ico_open, .ztree li span.button.menuIcon_ico_close, .ztree li span.button.menuIcon_ico_docu{
			margin-right:2px; background: url(${ctx}/static/plugin/ztree/css/img/diy/1_close.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
		.ztree li span.button.linkIcon_ico_open, .ztree li span.button.linkIcon_ico_close, .ztree li span.button.linkIcon_ico_docu{
			margin-right:2px; background: url(${ctx}/static/plugin/ztree/css/img/diy/3.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
		</style>
		<script type="text/javascript">
			var webContext = '${basePath}';
		</script>
		<script src="${ctx}/static/plugin/seajs/sea.js" type="text/javascript"></script>
		<script src="${ctx}/static/plugin/seajs/sea-config.js" type="text/javascript"></script>
		<script type="text/javascript">
			seajs.use('static/js/resource/resource_tree');
		</script>
	</head>
	
	<body>
		
		<div class="clear"></div>
		
		<a class="btn" href="javascript:;" data-listen="reloadFilter"><i class="icon-refresh"></i> 重置资源拦截</a>
		<hr />
		<ul id="resourceTree" class="ztree"></ul>
		<div id="resource_modal" class="modal hide fade" tabindex="-1">
			<div class="modal-header">
				<h3 id="myModalLabel">资源信息</h3>
			</div>
			<div id="resource_modal_body" class="modal-body">
				<form class="form-horizontal" id="resourceForm">
					<input type="hidden" id="id" name="id">
					<input type="hidden" id="parentId" name="parentId">
					<div class="control-group">
						<label class="control-label" for="name">名称:</label>
						<div class="controls">
							<input type="text" id="name" name="name">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="pageUrl">链接地址:</label>
						<div class="controls">
							<input type="text" id="pageUrl" name="pageUrl">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="pageUrl">是否菜单:</label>
						<div class="controls">
							<select id="menuItem" name="menuItem">
								<option value="">请选择</option>
								<option value="1">是</option>
								<option value="0">否</option>
							</select>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="type">资源类型:</label>
						<div class="controls">
							<select id="type" name="type">
								<option value="">请选择</option>
								<option value="0">公共资源</option>
								<option value="1">用户资源</option>
								<option value="2">角色资源</option>
							</select>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="orderNum">排序号:</label>
						<div class="controls">
							<input type="text" id="orderNum" name="orderNum">
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<input type="button" class="btn btn-primary" value="保存" /> 
				<input type="button" class="btn" data-dismiss="modal" value="关闭" />
			</div>
		</div>
		<div class="modal hide fade" id="confirmModal">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h3>系统提示</h3>
			</div>
			<div class="modal-body">
				<p>确定删除资源吗？</p>
			</div>
			<div class="modal-footer">
				<a class="btn btn-confirm">确定</a> 
				<a onclick="return false;" class="btn" data-dismiss="modal">取消</a>
			</div>
		</div>
	</body>
</html>
