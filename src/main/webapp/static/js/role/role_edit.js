define(function (require, exports) {
	var $ = require('jquery');
	require('ztreecss');
	require('ztree');
	
	var tree = null;
	var setting = {
		check : {
			enable : true,
			chkboxType : {
				"Y" : "ps",
				"N" : "ps"
			}
		},
		callback : {
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
	function loadResourceTree() {
		$.post(webContext + "/resource/rootJson").done(function(data) {
			tree = $.fn.zTree.init($("#resourceTree"), setting, data.data);
			if (rList.length > 0) {
				$.each(rList, function(i, it) {
					var node = tree.getNodeByParam("id", it);
					//修改加载时父子结点不进行级联
					tree.checkNode(node, true, false);
				});
			}
		});
	}
	F.saveRole = function($form) {
		var id = $('#id').val();
		var rid = $('#roleKey').val();
		var name = $('#name').val();
		if($.trim(rid)==''){
			alert("关键字不能为空");
			return;
		}
		var items = '';
		var ztree = $.fn.zTree.getZTreeObj("resourceTree");
		var selectNodes = ztree.getCheckedNodes(true);
		if (selectNodes.length > 0) {
			$.each(selectNodes, function(i, node) {
				if (i == 0) {
					items += node.id;
				} else {
					items += ',' + node.id;
				}
			});
		}
		//修改为选中或取消节点时同时修改父和子的状态
		$.post(webContext + "/role/save", {
			id : id,
			rid : rid,
			name : name,
			resources : items
		}).done(function(data){
			window.location.href=webContext+"/role/page";
		});
	}
	$(function() {
		loadResourceTree();
	});
});