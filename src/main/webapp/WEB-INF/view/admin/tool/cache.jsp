<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${basePath}" />
<div style="width: 1000px; margin: 10px auto;">
	<ul class="breadcrumb">
	  <li><a href="#">系统设置</a> <span class="divider">/</span></li>
	  <li class="active">缓存管理</li>
	</ul>
	<form class="form-horizontal">
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
	      <input type="button" class="btn btn-info" onclick="clearByKey();" value="按组/KEY清除">
	      <input type="button" class="btn btn-success" onclick="clearByGroup();" value="按组清除">
	      <input type="button" class="btn btn-warning" onclick="clearAll();" value="全部清除">
	    </div>
	  </div>
	</form>
</div>
<script type="text/javascript">
	var webContext = "${basePath}";
	function clearByKey(){
		var group = $('#cacheGroup').val();
		var key = $('#cacheKey').val();
		if(group==""||key==""){
			alert("组和KEY必填");
		}else{
			$.post("${ctx}/admin/cache/removeByGroupAndKey",{
				group:group,key:key
				},function(data){
					alert("清理完毕");
			});
		}
	}
	function clearByKey(){
		var group = $('#cacheGroup').val();
		$.post("${ctx}/admin/cache/removeByGroup",{group:group},function(data){
			alert("清理完毕");
		});
	}
	function clearAll(){
		$.post("${ctx}/admin/cache/removeAll",function(data){
			alert("清理完毕");
		});
	}
</script>