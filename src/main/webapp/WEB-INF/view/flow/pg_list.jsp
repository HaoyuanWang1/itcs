<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${basePath}" />
<html>
	<head>
		<title>流程进度编辑</title>
	</head>
	<body>
		<ul class="breadcrumb">
   			<li><a href="${ctx}/flow/pd/page">流程引擎</a> <span class="divider">/</span></li>
   			<li class="active"><b>[${pd.name}]</b>&nbsp;流程进度</li>
   		</ul>
   		
   		<input type="button" class="btn" data-listen="openProgressModal" data-pg="{name:'',sortNum:'',id:'',nodeNames:'',nodeDescs:''}" value="新建" /> 
   		<br />
		<table class="table table-bordered table-hover table-condensed">
			<thead>
				<tr>
					<th>流程进度名</th>
					<th>包含环节名称</th>
					<th>包含环节描述</th>
					<th>排序号</th>
					<th class="span2">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="pg">
					<tr>
						<td>${pg.name}</td>
						<td>${pg.nodeNames}</td>
						<td>${pg.nodeDescs}</td>
						<td>${pg.sortNum}</td>
						<td><a href="javascript:void(0)" data-listen="openProgressModal" data-pg="{name:'${pg.name}',sortNum:'${pg.sortNum}',id:'${pg.id }',nodeNames:'${pg.nodeNames}',nodeDescs:'${pg.nodeDescs}'}">变更</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<div id="pgEditModal" class="modal hide fade">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				<h4>流程进度编辑</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" id="progressForm">
					<div class="control-group">
						<label class="control-label" for="name">流程进度名</label>
						<div class="controls">
							<input type="text" name="name" />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="sortNum">序号</label>
						<div class="controls">
							<input type="text" name="sortNum" />
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="nodeNames">流程环节</label>
						<div class="controls">
							<c:forEach items="${nodeList}" var="item">
								<label class="checkbox">
									<input type="checkbox" class="node-name" value="【${item.name}】" data-desc="【${item.desc}】"/>
									${item.desc }
								</label>
							</c:forEach>
						</div>
					</div>
					<input type="hidden" name="pdId" value="${pd.id}" />
					<input type="hidden" name="nodeNames" />
					<input type="hidden" name="nodeDescs" />
					<input type="hidden" name="id" />
				</form>
			</div>
			<div class="modal-footer">
				<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
				<button class="btn btn-primary" data-listen="saveProgress" data-target="#progressForm">保存</button>
			</div>
		</div>
		
		<script type="text/javascript">F.run('modules/flow/processProgress');</script>
	</body>
</html>