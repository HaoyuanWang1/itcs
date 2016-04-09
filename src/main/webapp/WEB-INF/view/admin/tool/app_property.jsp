<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${basePath}" />
<style type="text/css">
.table td {
	white-space: nowrap;
}

.table td.bd {
	white-space: normal;
}
textarea {
	overflow: auto;
	resize: none;
}
</style>
<div style="width: 1000px; margin: 10px auto;">
	<ul class="breadcrumb">
		<li><a href="#">系统设置</a> <span class="divider">/</span></li>
		<li class="active">系统属性设置</li>
	</ul>
	
	<table class="table table-bordered table-hover table-condensed">
		<thead>
			<tr>
				<th>属性名</th>
				<th>属性描述</th>
				<th>属性值</th>
				<th><input class="btn btn-info btn-small" type="button" onclick="editProperty()" value="新增" /></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="item">
				<tr>
					<td>${item.name}</td>
					<td>${item.descn}</td>
					<td class="bd">${item.encrytFlag == 1 ? '已加密' : item.value}</td>
					<td>
						<input class="btn btn-link" type="button" value="编辑" 
							onclick="editProperty(${item.id},'${item.name}','${item.descn}','${item.encrytFlag == 1 ? '' : item.value}', ${item.encrytFlag == 1 ? 1 : 0});"/>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<div id="propertyEditDiv" class="modal hide fade">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h3 id="myModalLabel">系统属性编辑</h3>
		</div>
		<div class="modal-body">
			<form id="propertyForm">
				<label for="name">属性名称</label> 
				<input class="span6" id="name" name="name" type="text" />
				<label for="descn">属性描述</label> 
				<input class="span6" id="descn" name="descn" type="text" /> 
				<label for="value">属性值</label>
				<textarea class="span6" rows="3" id="value" name="value"></textarea>
				<p id="encrytTip" class="alert alert-info hide">新属性值，如果要修改输入即可，否则可以不修改</p>
				<div id="encrytFlagDiv">
					<label>是否加密</label>
					<label class="radio inline"><input type="radio" value="1" name="encrytFlag" /> 是</label>
					<label class="radio inline"><input type="radio" value="0" name="encrytFlag" checked="checked" /> 否</label>
				</div>
				<input type="hidden" name="encrytFlag" id="encrytFlag" />
				<input id="id" name="id" type="hidden" />
			</form>
		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
			<button class="btn btn-primary" onclick="saveProperty();">保存</button>
		</div>
	</div>
	
</div>
<script type="text/javascript">
	var webContext = "${basePath}";
	var propertyEditDiv = $('#propertyEditDiv');
	var propertyForm = $('#propertyForm');
	var pf_id = $('#id');
	var pf_name = $('#name');
	var pf_descn = $('#descn');
	var pf_value = $('#value');
	var encrytTip = $('#encrytTip');
	var encrytFlagDiv = $('#encrytFlagDiv').detach();
	var encrytFlag = $('#encrytFlag').detach();
	
	function editProperty(id,name,descn,value, flag) {
		// 新增
		if (!id) {
			propertyForm.append(encrytFlagDiv);
			encrytFlag.detach();
		} else {
			encrytFlagDiv.detach();
			propertyForm.append(encrytFlag);
			encrytFlag.val(flag);
		}
		pf_id.val(id);
		pf_name.val(name);
		pf_descn.val(descn);
		pf_value.val(value);
		
		if (flag === 1) {
			encrytTip.removeClass('hide');
		} else {
			encrytTip.addClass('hide');
		}
		propertyEditDiv.modal('show');
	}
	function saveProperty(){
		$.post(webContext + "/admin/property/save", propertyForm.serialize(),
			function(data){
				propertyEditDiv.modal('hide');
				window.location.reload();
			});
	}
</script>