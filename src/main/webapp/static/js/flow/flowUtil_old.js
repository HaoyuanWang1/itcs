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
						$('#backTaskDiv').dialog('close');
						window.location.reload();
					},
					error : function(xhr, ts, et) {
					}
				});
			}
		},{
			text:'取消',
			handler:function(){
				$('#backTaskDiv').dialog('close');
			}
		}]
	});
}
//初始化转办Div
function initTransferDiv(){
	var $transferTaskDiv = $("<div id='transferTaskDiv' style='padding:5px;width:400px;height:200px;'>"+
						"<p>请输入任务的新审批人itcode：</p><input id ='transferTask' name='transferTask' type='hidden'>"+
						"<input id ='transferUser' name='transferUser' type='text'></div>");
	$("body").append($transferTaskDiv);
	$('#transferTaskDiv').dialog({
		title:'任务转办',
		closed:true,
		buttons:[{
			text:'确认',
			iconCls:'icon-ok',
			handler:function(){
				var transferTask = $("#transferTask").val();
				var transferUser = $('#transferUser').val();
				$.ajax({
					type : "post",
					url : webContext + '/flowCtrl/transferTask.do',
					data :{
						taskId:transferTask,
						transferUser:transferUser
					},
					success : function(data, textStatus) {
						dc_checkError(data,"转办成功！");
						$('#transferTaskDiv').dialog('close');
						window.location.reload();
					},
					error : function(xhr, ts, et) {
					}
				});
			}
		},{
			text:'取消',
			handler:function(){
				$('#transferTaskDiv').dialog('close');
			}
		}]
	});
}
//初始化管理员转办窗口
function initAdminTransferDiv(){
	var $adminTransferDiv = $("<div id='adminTransferDiv' style='padding:5px;width:400px;height:250px;'>"+
					"<p>请选择要转的用户：</p>"+
					"<select id='adminTransferTask' name='adminTransferTask'></select>"+
					"<p>请填写要转给用户：</p>"+
					"<input id ='adminTransferUser' name='adminTransferUser' type='text'></div>");
	$("body").append($adminTransferDiv);
	$.ajax({
		type : "post",
		url : webContext + '/flowView/findNodeTask.do?taskId='+taskId,
		dataType:'json',
		success : function(data, textStatus) {
			$.each(data,function(i,fnode){
				$("#adminTransferTask").append("<option value='"+fnode.taskId+"'>"+fnode.auditor+"</option>");
			});
		},
		error : function(xhr, ts, et) {
			alert("历史下拉列表数据初始化失败");
		}
	});
	$('#adminTransferDiv').dialog({
		title:'管理员转办',
		closed:true,
		buttons:[{
			text:'确认',
			iconCls:'icon-ok',
			handler:function(){
				var transferTask = $('#adminTransferTask').val();
				var sourceUser = $('#adminTransferTask').find("option:selected").text();
				var transferUser = $('#adminTransferUser').val();
				$.ajax({
					type : "post",
					url : webContext + '/flowCtrl/transferTask.do',
					data :{
						taskId:transferTask,
						sourceUser:sourceUser,
						transferUser:transferUser
					},
					success : function(data, textStatus) {
						dc_checkError(data,"转办成功！");
						$('#adminTransferDiv').dialog('close');
						window.location.reload();
					},
					error : function(xhr, ts, et) {
					}
				});
			}
		},{
			text:'取消',
			handler:function(){
				$('#adminTransferDiv').dialog('close');
			}
		}]
	});
}
//初始化撤单Div
function initCancelProcessDiv(){
	var $cancelProcessDiv = $("<div id='cancelProcessDiv' style='padding:5px;width:400px;height:250px;'>"+
					"<p>请输入撤单原因：</p><input id ='cancelTask' name='cancelTask' type='hidden'>"+
					"<textarea id ='cancelComment' name='cancelComment' style='width:300px;height:100px;'></textarea></div>");
	$("body").append($cancelProcessDiv);
	$('#cancelProcessDiv').dialog({
		title:'撤单',
		closed:true,
		buttons:[{
			text:'确认',
			iconCls:'icon-ok',
			handler:function(){
				$('#cancelProcessDiv').dialog('close');
				var cancelTask = $("#cancelTask").val();
				var cancelComment = $("#cancelComment").val();
				$.ajax({
					type : "post",
					url : webContext + '/flowCtrl/cancelProcess.do',
					data :{
						taskId:cancelTask,
						comment:cancelComment
					},
					success : function(data, textStatus) {
						dc_checkError(data,"撤单成功！");
						$('#cancelProcessDiv').dialog('close');
						window.location.reload();
					},
					error : function(xhr, ts, et) {
					}
				});
			}
		},{
			text:'取消',
			handler:function(){
				$('#cancelProcessDiv').dialog('close');
			}
		}]
	});
}
//初始化审批DIV
function initSignDiv(){
	var $signTaskDiv = $("<div id='signTaskDiv' style='padding:5px;width:400px;height:250px;'>"+
					"<p>请输入审批意见：</p><input id ='signTask' name='signTask' type='hidden'>"+
					"<textarea id ='signComment' name='signComment' style='width:300px;height:100px;'></textarea></div>");
	$("body").append($signTaskDiv);
	$("#signTaskDiv").popbox({
		title:"审批",
		width: 750,
		buttons: {
			"确认": function() {
				$('#signTaskDiv').dialog('close');
				var signTask = $("#signTask").val();
				var signComment = $("#signComment").val();
				$.ajax({
					type : "post",
					url : webContext + '/flowCtrl/executeTask.do',
					data :{
						taskId:signTask,
						comment:signComment
					},
					success : function(data, textStatus) {
						dc_checkError(data,"审批成功！");
						$('#signTaskDiv').dialog('close');
						window.location.reload();
					},
					error : function(xhr, ts, et) {
					}
				});
			},
			"取消": function() {
				$('#signTaskDiv').dialog('close');
			}
		}
	});
}
//初始化加签DIV
function initAddSignDiv(){
	var $addSignDiv = $("<div id='addSignDiv' style='padding:5px;width:400px;height:200px;'>"+
					"<p>请输入加签人itcode(每次只能加签一个人)：</p><input id ='addSignTask' name='addSignTask' type='hidden'>"+
					"<input id ='addSignUser' name='addSignUser' type='text'></div>");
	$("body").append($addSignDiv);
	$('#addSignDiv').dialog({
		title:'加签',
		closed:true,
		buttons:[{
			text:'确认',
			iconCls:'icon-ok',
			handler:function(){
				$('#signTaskDiv').dialog('close');
				var addSignTask = $("#addSignTask").val();
				var addSignUser = $("#addSignUser").val();
				$.ajax({
					type : "post",
					url : webContext + '/flowCtrl/addSignTask.do',
					data :{
						taskId:addSignTask,
						addSignUser:addSignUser
					},
					success : function(data, textStatus) {
						dc_checkError(data,"加签成功！");
						$('#dc_sign').hide();//隐藏审批按钮
						$('#dc_transfer').hide();//隐藏转办按钮
						$('#dc_back').hide();//隐藏驳回按钮
						$('#dc_history').hide();//隐藏历史按钮
						$('#addSignDiv').dialog('close');
					},
					error : function(xhr, ts, et) {
					}
				});
			}
		},{
			text:'取消',
			handler:function(){
				$('#addSignDiv').dialog('close');
			}
		}]
	});
}
//初始化加签撤签操作DIV
function initCancelAddSignDiv(){
	var $cancelAddSignDiv = $("<div id='cancelAddSignDiv' style='padding:5px;width:400px;height:250px;'>"+
					"<p>请选择要撤签的加签人:</p>"+
					"<input id ='addSignSourceTask' name='addSignSourceTask' type='hidden'>"+
					"<select id='addSignTasks' name='addSignTasks'></select></div>");
	$("body").append($cancelAddSignDiv);
	$.ajax({
		type : "post",
		url : webContext + '/flowView/findUnCloseAddTask.do?taskId='+taskId,
		dataType:'json',
		success : function(data, textStatus) {
			$.each(data,function(i,task){
				$("#adminTransferTask").append("<option value='"+task.id+"'>"+task.auditor+"</option>");
			});
		},
		error : function(xhr, ts, et) {
			alert("历史下拉列表数据初始化失败");
		}
	});
	$('#cancelAddSignDiv').dialog({
		title:'加签撤签',
		closed:true,
		buttons:[{
			text:'确认',
			iconCls:'icon-ok',
			handler:function(){
				var addSignTask = $('#addSignTasks').val();
				$.ajax({
					url : webContext + '/flowCtrl/cancelAddSign.do',
					data :{ taskId:addSignTask },
					success : function(data, textStatus) {
						dc_checkError(data,"撤销加签成功！");
						$('#cancelAddSignDiv').dialog('close');
						window.location.reload();
					},
					error : function(xhr, ts, et) {
					}
				});
			}
		},{
			text:'取消',
			handler:function(){
				$('#cancelAddSignDiv').dialog('close');
			}
		}]
	});
}
//初始化中止操作DIV
function initEndProcessDiv(){
	var $endProcessDiv = $("<div id='endProcessDiv' style='padding:5px;width:400px;height:250px;'>"+
					"<p>请输入中止原因：</p><input id ='endProcessTask' name='endProcessTask' type='hidden'>"+
					"<textarea id ='endComment' name='endComment' style='width:300px;height:100px;'></textarea></div>");
	$("body").append($endProcessDiv);
	$('#endProcessDiv').dialog({
		title:'中止',
		closed:true,
		buttons:[{
			text:'确认',
			iconCls:'icon-ok',
			handler:function(){
				var endProcessTask = $("#endProcessTask").val();
				var endComment = $("#endComment").val();
				$.ajax({
					url : webContext + '/flowCtrl/endProcess.do',
					data :{
						taskId:endProcessTask,
						comment:endComment
					},
					success : function(data, textStatus) {
						dc_checkError(data,"流程中止");
						$('#endProcessDiv').dialog('close');
						window.location.reload();
					},
					error : function(xhr, ts, et) {
					}
				});
			}
		},{
			text:'取消',
			handler:function(){
				$('#endProcessDiv').dialog('close');
			}
		}]
	});
}
//初始化自由跳转DIV
function initFreeJumpDiv(){
	var $freeJumpDiv = $("<div id='freeJumpDiv' style='padding:5px;width:400px;height:250px;'>"+
					"<p>请选择要跳转的环节:</p>"+
					"<input id ='jumpSourceTask' name='jumpSourceTask' type='hidden'>"+
					"<select id='jumpTargetNode' name='jumpTargetNode'></select>"+
					"<p>跳转原因:</p>"+
					"<textarea id='jumpComment' name='jumpComment' style='width:300px;height:100px;'></textarea></div>");
	$("body").append($freeJumpDiv);
	$.ajax({
		type : "post",
		url : webContext + '/flowView/findAllNode.do?taskId='+taskId,
		dataType:'json',
		success : function(data, textStatus) {
			$.each(data,function(i,task){
				$("#jumpTargetNode").append("<option value='"+task.nodeName+"'>"+task.nodeDesc+"</option>");
			});
		},
		error : function(xhr, ts, et) {
			alert("历史下拉列表数据初始化失败");
		}
	});
	$('#freeJumpDiv').dialog({
		title:'自由跳转',
		closed:true,
		buttons:[{
			text:'确认',
			iconCls:'icon-ok',
			handler:function(){
				var jumpSourceTask = $("#jumpSourceTask").val();
				var jumpComment = $("#jumpComment").val();
				var jumpTargetNode = $('#jumpTargetNode').val();
				$.ajax({
					type : "post",
					url : webContext + '/flowCtrl/freeJump.do',
					data :{
						taskId:jumpSourceTask,
						targetNode:jumpTargetNode,
						comment:jumpComment
					},
					success : function(data, textStatus) {
						dc_checkError(data,"跳转成功！");
						$('#freeJumpDiv').dialog('close');
						window.location.reload();
					},
					error : function(xhr, ts, et) {
					}
				});
			}
		},{
			text:'取消',
			handler:function(){
				$('#freeJumpDiv').dialog('close');
			}
		}]
	});
}
//打开审批窗口
function openSignTaskDialog(){
	$("#signComment").attr("value","");
	$("#signTask").attr("value",taskId);
	$('#signTaskDiv').dialog('open');
}
//打开撤单窗口
function openCancelProcessDialog(){
	$("#cancelComment").attr("value","");
	$("#cancelTask").attr("value",taskId);
	$('#cancelProcessDiv').dialog('open');
}
//打开驳回窗口
function openBackTaskDialog(){
	$("#backComment").attr("value","");
	$("#backSourceTask").attr("value",taskId);
	
	$('#backTaskDiv').dialog('open');
}
//打开转办窗口
function openTransferTaskDialog(){
	if(flowRole.indexOf('ADMIN')>=0&&taskId!==""){	//如果是管理员
		$('#adminTransferDiv').dialog('open');
	}else{
		$("#transferTask").attr("value",taskId);
		$('#transferTaskDiv').dialog('open');
	}
}
//打开加签窗口
function openAddSignDialog(){
	$("#addSignTask").attr("value",taskId);
	$('#addSignDiv').dialog('open');
}
//打开加签撤签窗口
function openCancelAddSignDialog(){
	$('#cancelAddSignDiv').dialog('open');
}
//打开中止窗口
function openEndProcessDialog(){
	$("#endComment").attr("value","");
	$("#endProcessTask").attr("value",taskId);
	$('#endProcessDiv').dialog('open');
}
//打开自由跳转窗口
function openFreeJumpDialog(){
	$("#jumpComment").attr("value","");
	$("#jumpSourceTask").attr("value",taskId);
	$('#freeJumpDiv').dialog('open');
}
//审批
function flow_sign(){
	openSignTaskDialog();
}

function flow_history(flowname){
	window.open(webContext+'/core/workflow/jsp/flowProcessHis.jsp?processId='+processId+"&flowName="+flowname);
}
