define(function (require, exports) {
	var $ = require('jquery');
	
	var taskCtrlSign = $('#taskCtrlSign');
	var taskCtrlBack = $('#taskCtrlBack');
	var taskCtrlEnd = $('#taskCtrlEnd');
	var taskCtrlTransfer = $('#taskCtrlTransfer');
	var taskCtrlFree = $('#taskCtrlFree');
	
	F.url = {
		sign: webContext + '/userFlowCtrl/executeTask',
		back: webContext + '/userFlowCtrl/backTask',
		transfer: webContext + '/userFlowCtrl/transferTask',
		end: webContext + '/userFlowCtrl/cancelProcess',
		free: webContext + '/userFlowCtrl/freeJump'
	};
	
	F.admin_sign_flow = function ($form) {
		taskCtrlSign.modal('hide');
		$.alert('正在审批');
		$.post(F.url.sign, $form.serializeArray())
			.done(function (result) {
				$.alert('【审批】成功', 1000)
				.done(function () {
					$.alert('close');
				});
			});
	};
	
	F.admin_back_flow = function ($form) {
		taskCtrlBack.modal('hide');
		$.alert('正在驳回');
		$.post(F.url.back, $form.serializeArray())
			.done(function (result) {
				$.alert('【驳回】成功', 1000)
				.done(function () {
					$.alert('close');
				});
			});
	};
	
	F.admin_end_flow = function ($form) {
		taskCtrlEnd.modal('hide');
		$.alert('正在撤单');
		$.post(F.url.end, $form.serializeArray())
			.done(function (result) {
				$.alert('【撤单】成功', 1000)
				.done(function () {
					$.alert('close');
				});
			});
	};
	
	F.admin_transfer_flow = function ($form) {
		taskCtrlTransfer.modal('hide');
		$.alert('正在转办');
		$.post(F.url.transfer, $form.serializeArray())
			.done(function (result) {
				$.alert('【转办】成功', 1000)
				.done(function () {
					$.alert('close');
				});
			});
	};
	
	F.admin_free_flow = function ($form) {
		taskCtrlFree.modal('hide');
		$.alert('正在自由跳转');
		$.post(F.url.free, $form.serializeArray())
			.done(function (result) {
				$.alert('【自由跳转】成功', 1000)
				.done(function () {
					$.alert('close');
				});
			});
	};
	
});