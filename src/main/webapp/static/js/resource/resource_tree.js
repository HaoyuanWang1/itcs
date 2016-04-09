define(function (require, exports) {
	var $ = require('jquery');
	require('ztreecss');
	require('ztree');
	
	F.reloadFilter = function () {
		$.post(webContext + '/resource/reloadFilter')
			.done(function () {
				$.alert('重置资源拦截完成', 1000)
					.done(function () {
						$.alert('close');
					});
			});
	};
	
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
					pidKey : "parentItem"
				}
			}
		};
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
		var treeNode =$.fn.zTree.getZTreeObj("resourceTree").getSelectedNodes()[0];
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
	
	function editResource(id, parentId) {
		if(id==0){
			$("#id").val("");
			$("#name").val("");
			$("#pageUrl").val("");
			$("#parentId").val(parentId);
			$("#menuItem").val("");
			$("#type").val("");
			$("#orderNum").val("");
		}else{
			$.post(webContext + "/resource/resourceJson/"+id)
			.done(function(data) {
				$("#id").val(data.data.id);
				$("#name").val(data.data.name);
				$("#pageUrl").val(data.data.pageUrl);
				$("#parentId").val(data.data.parentId);
				$("#menuItem").val(data.data.menuItem);
				$("#type").val(data.data.type);
				$("#orderNum").val(data.data.orderNum);
			});
		}
		$('#resource_modal').modal('show');
	}
	function saveResource() {
		$.post(webContext + "/resource/save", $('#resourceForm').serialize())
			.done(
				function(data) {
					$('#resource_modal').modal('hide');
					var ztree =$.fn.zTree.getZTreeObj("resourceTree");
					var selectNode = ztree.getSelectedNodes()[0];
					var parentNode=selectNode.getParentNode();
// 					window.location.reload();
					$.post(webContext + "/resource/rootJson", function(data) {
						tree = $.fn.zTree.init($("#resourceTree"), setting, data.data);
						var ztree =$.fn.zTree.getZTreeObj("resourceTree");
						ztree.selectNode(selectNode);
						ztree.expandNode(parentNode,true,true,true);
					});
				});
	}
	function removeMenuItem(id) {
		$.post(webContext + "/resource/removeResource/" + id)
		.done(function(data) {
			var ztree =$.fn.zTree.getZTreeObj("resourceTree");
			var selectNode = ztree.getSelectedNodes()[0];
			var parentNode= selectNode.getParentNode();
			ztree.removeNode(selectNode);
			ztree.expandNode(parentNode,true,true,true);
		});
	}
	
	$.post(webContext + "/resource/rootJson")
		.done(function(data) {
			tree = $.fn.zTree.init($("#resourceTree"), setting, data.data);
		});
	
	$(document)
		.on('click.save-resource', '.btn-primary', function () {
			saveResource();
		})
		.on('click.removeItem', '.btn-confirm', function () {
			removeItem();
		});
});