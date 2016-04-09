//初始化审批DIV
function initSignDiv(taskId){
	var $signTaskDiv = $("<div id='signTaskDiv' style='padding:5px;width:300px;height:150px;'>"+
					"<p>请输入审批意见：</p><input id ='signTask' name='signTask' type='hidden' value='"+taskId+"'>"+
					"<textarea id ='signComment' name='signComment' style='width:300px;height:100px;'></textarea></div>");
	$("body").append($signTaskDiv);
	$("#signTaskDiv").popbox({
		title:"审批",
		width: 400,
		buttons: {
			"确认": function() {
				var signTask = $("#signTask").val();
				var signComment = $("#signComment").val();
				alert(signTask+"------"+signComment);
				$.ajax({
					type : "post",
					url : webContext + '/flow/executeTask',
					data :{
						taskId:signTask,
						comment:signComment
					},
					success : function(data, textStatus) {
						$('#signTaskDiv').popbox("close");
						window.location.reload();
					},
					error : function(xhr, ts, et) {
					}
				});
			},
			"取消": function() {
				$('#signTaskDiv').popbox("close");
			}
		}
	});
}
//初始化驳回窗口
function initBackDiv(){
	var $backTaskDiv = $("<div id='backTaskDiv' style='padding:5px;width:400px;height:250px;'>"+
					"<p>请选择要驳回的环节：</p><input id ='backSourceTask' name='backSourceTask' type='hidden'>"+
					"<select id='hisNode' name='hisNode'></select>"+
					"<p>驳回意见：</p>"+
					"<textarea id='backComment' name='backComment' style='width:300px;height:100px;'></textarea></div>");
	$("body").append($backTaskDiv);
	$.ajax({
		type : "post",
		url : webContext + '/flowView/findHistoryNode.do?taskId='+taskId,
		dataType:'json',
		success : function(data, textStatus) {
			$.each(data,function(i,hisNode){
				$("#hisNode").append("<option value='"+hisNode.nodeName+"'>"+hisNode.nodeDesc+"</option>");
			});
		},
		error : function(xhr, ts, et) {
			alert("历史下拉列表数据初始化失败");
		}
	});
	$('#backTaskDiv').dialog({
		title:'节点驳回',
		closed:true,
		buttons:[{
			text:'确认',
			iconCls:'icon-ok',
			handler:function(){
				var backSourceTask = $("#backSourceTask").val();
				var hisNode = $('#hisNode').val();
				var comment = $('#backComment').val();
				$.ajax({
					type : "post",
					url : webContext + '/flowCtrl/backTask.do',
					data :{
						taskId:backSourceTask,
						targetNode:hisNode,
						comment:comment
					},
					success : function(data, textStatus) {
						dc_checkError(data,"驳回成功！");
						$('#backTaskDiv').popbox("close");
						window.location.reload();
					},
					error : function(xhr, ts, et) {
					}
				});
			}
		},{
			text:'取消',
			handler:function(){
				$('#backTaskDiv').popbox("close");
			}
		}]
	});
}