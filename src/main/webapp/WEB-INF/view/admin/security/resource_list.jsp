<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${basePath}" />

<div style="width: 1000px; margin: 10px auto;">
	<ul class="breadcrumb">
	  <li><a href="#">安全管理</a> <span class="divider">/</span></li>
	  <li class="active">授权管理</li>
	</ul>
	<form id="searchForm" class="form-inline">
		<input type="button" class="btn" value="新建" onclick="editResource('','','');"/>
	    <input type="submit" class="btn" value="刷新"/>
	</form>
	<table class="table table-bordered table-hover table-condensed">
		<thead>
			<tr>
				<th>URL</th>
				<th>授权</th>
				<th width="110">
					操作
				</th>
			</tr>
		</thead>
		<tbody id="userReuslt">
		  <c:forEach items="${list}" var="resource">
		    <tr>
				<td>${resource.url}</td>
				<td>${resource.perms}</td>
			 	<td width="150">
				<a href="javascript:;" class="table-btn" onclick="editResource('${resource.id}','${resource.url}','${resource.perms}'); "> 编辑 </a>
				|<a href="javascript:;" class="table-btn" onclick="removeResource(${resource.id}); ">删除 </a>
				</td> 	
			</tr>
		  </c:forEach>
		</tbody>
	</table>

	<div id="resource_modal" class="modal hide fade" tabindex="-1" >
	  <div class="modal-header">
	    <h3 id="myModalLabel">资源授权信息</h3>
	  </div>
	  <div id="resource_modal_body" class="modal-body">
	    <form class="form-horizontal" id="resourceForm">
		  <div class="control-group">
		    <label class="control-label" for="url">URL:</label>
		    <div class="controls">
		      <input type="text" id="url" name="url" >
		      <input type="hidden" id="id" name="id" >
		    </div>
		  </div>
		  <div class="control-group">
		    <label class="control-label" for="inputPassword">授权:</label>
		    <div class="controls">
		      <input type="text" id="perms" name="perms">
		    </div>
		  </div>
		</form>
	  </div>
	  <div class="modal-footer">
	  	<input type="button" class="btn btn-primary" onclick="saveResource();" value="保存"/>
	    <input type="button" class="btn" data-dismiss="modal" value="关闭"/>
	  </div>
	</div>

</div>
<script type="text/javascript" >
	function editResource(id,url,perms){
		$("#id").val(id);
		$("#url").val(url);
		$("#perms").val(perms);
		$('#resource_modal').modal('show');
	}
	function saveResource(){
		$.post("${ctx}/resource/save",
			$('#resourceForm').serialize(),
			function(data){
				$('#resource_modal').modal('hide');
				window.location.reload();
		});
	}
	function removeResource(id){
		$.post("${ctx}/resource/remove/"+id,
			function(data){
				window.location.reload();
		});
	}
</script>