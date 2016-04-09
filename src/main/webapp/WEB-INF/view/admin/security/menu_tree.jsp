<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${basePath}" />

<script src="${ctx}/static/bootstrap/js/bootstrap-confirm.js" type="text/javascript"></script>
<script type="text/javascript">
	var webContext = "${basePath}";
	$(function() {
		loadCatalog();
	});
	var tree = null;
	var setting = {
			view : {
				addHoverDom : addHoverDom,
				removeHoverDom : removeHoverDom,
				selectedMulti : false
			},
			callback: {
				beforeEditName: zTreeBeforeEditName,
				beforeRemove: zTreeBeforeRemove
			},
			edit : {
				enable : true,
				showRemoveBtn :true,
				showRenameBtn:true,
				removeTitle:'删除资源',
				renameTitle:'修改资源'
			},
			data : {
				key : {
					name : "text",
					url : "url",
					idKey : "id",
					pidKey : "parentId"
				}
			}
		};
	function loadCatalog() {
		$.post(webContext + "/resource/menuJson", function(data) {
			tree = $.fn.zTree.init($("#menuTree"), setting, data.data);
		});
	}
	function zTreeBeforeEditName(treeId, treeNode) {
		var treeObj = $.fn.zTree.getZTreeObj(treeId);
		treeObj.selectNode(treeNode);
		editResource(treeNode.id,treeNode.parentId);
		return false;
		
	}
	function zTreeBeforeRemove(treeId, treeNode) {
		var treeObj = $.fn.zTree.getZTreeObj(treeId);
		treeObj.selectNode(treeNode);
		$('#confirmModal').modal('show');
		return false;
	}
	function addHoverDom(treeId, treeNode) {
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0)
			return;
		var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
				+ "' title='添加菜单'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_" + treeNode.tId);
		if (btn)
			btn.bind("click", function() {
				var treeObj = $.fn.zTree.getZTreeObj(treeId);
				treeObj.selectNode(treeNode);
				editResource(0,treeNode.id);
				return false;
			});
	};
	function removeHoverDom(treeId, treeNode) {
		$("#addBtn_" + treeNode.tId).unbind().remove();
	};
	function removeItem(){
		$('#confirmModal').modal('hide');
		var treeNode =$.fn.zTree.getZTreeObj("menuTree").getSelectedNodes()[0];
		var rootItem = "0";
		if(treeNode.id==rootItem){
			alert("根结点不允许删除");
			return;
		}else{
			removeMenuItem(treeNode.id);
		}
	}
	function getChildrenCodes(itemCodes,treeNode){
		itemCodes.push(treeNode.code);
		if (treeNode.isParent){
				for(var obj in treeNode.children){
					getChildrenCodes(itemCodes,treeNode.children[obj]);
				}
		   }
		return itemCodes;
	}
</script>
<div style="width: 1000px; margin: 10px auto;">
	<ul class="breadcrumb">
		<li><a href="#">安全管理</a> <span class="divider">/</span></li>
		<li class="active">资源管理</li>
	</ul>
	<div class="row-fluid">
		<div class="span6">
		<ul id="menuTree" class="ztree"></ul>
		</div>
		<div class="span6">
		lakjflsakjlfksajdlfksajlfdkjsaldkfj
		</div>
	</div>
	<div id="menu_modal" class="modal hide fade" tabindex="-1">
		<div class="modal-header">
			<h3 id="myModalLabel">菜单信息</h3>
		</div>
		<div id="menu_modal_body" class="modal-body">
			<form class="form-horizontal" id="menu_from">
				<input type="hidden" id="id" name="id">
				<input type="hidden" id="parentId" name="parentId">
				<input type="hidden" id="menuItem" name="menuItem">
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
					<label class="control-label" for="menuText">菜单名称:</label>
					<div class="controls">
						<input type="text" id="menuText" name="menuText">
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
			<input type="button" class="btn btn-primary"
				onclick="saveMenu();" value="保存" /> <input type="button"
				class="btn" data-dismiss="modal" value="关闭" />
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
			<a onclick="removeItem();" class="btn btn-primary btn-confirm">确定</a> <a onclick="return false;"
				class="btn" data-dismiss="modal">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript" >
	var webContext = "${basePath}";
	function editResource(id, parentId) {
		if(id==0){
			$("#id").val("");
			$("#name").val("");
			$("#pageUrl").val("");
			$("#parentId").val(parentId);
			$("#menuItem").val("");
			$("#menuText").val("");
			$("#type").val("");
			$("#orderNum").val("");
		}else{
			$.post(webContext + "/resource/resourceJson/"+id,	function(data) {
				$("#id").val(data.data.id);
				$("#name").val(data.data.name);
				$("#pageUrl").val(data.data.pageUrl);
				$("#parentId").val(data.data.parentId);
				$("#menuItem").val(data.data.menuItem);
				$("#menuText").val(data.data.menuText);
				$("#type").val(data.data.type);
				$("#orderNum").val(data.data.orderNum);
			});
		}
		$('#menu_modal').modal('show');
	}
	function saveMenu() {
		$.post(webContext + "/resource/save", $('#menu_from').serialize(),
			function(data) {
				$('#menu_modal').modal('hide');
				var ztree =$.fn.zTree.getZTreeObj("menuTree");
				var selectNode = ztree.getSelectedNodes()[0];
				var parentNode=selectNode.getParentNode();
// 				window.location.reload();
				$.post(webContext + "/resource/rootJson", function(data) {
					tree = $.fn.zTree.init($("#menuTree"), setting, data.data);
					var ztree =$.fn.zTree.getZTreeObj("menuTree");
					ztree.selectNode(selectNode);
					ztree.expandNode(parentNode,true,true,true);
				});
			});
	}
	function removeMenuItem(id) {
		$.post(webContext + "/resource/removeResource/" + id, function(data) {
			var ztree =$.fn.zTree.getZTreeObj("menuTree");
			var selectNode = ztree.getSelectedNodes()[0];
			var parentNode= selectNode.getParentNode();
			ztree.removeNode(selectNode);
			ztree.expandNode(parentNode,true,true,true);
// 			window.location.reload();
		});
	}
</script>
