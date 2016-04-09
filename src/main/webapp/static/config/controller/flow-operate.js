define(function (require, exports) {
	var $ = require('jquery');
	
	exports.init = function (context, flowBtnGroups, complete, controller) {
		context = context || $doc;
		complete(controller.name);
	};
	
	F.url = {
		sign: webContext + '/userFlowCtrl/executeTask',
		cancel: webContext + '/userFlowCtrl/cancelProcess',
		back: webContext + '/userFlowCtrl/backTask',
		transfer: webContext + '/userFlowCtrl/transferTask',
		addSign: webContext + '/userFlowCtrl/addSignTask'
	};
	
	F.workflow = {
		
		/**
		 * 审批请求
		 */
		sign: function ($form,$taskId) {
			$.post(F.url.sign, {taskId:$taskId},$form.serializeArray())
			.done(function (result) {
				$.alert('提交成功，页面即将跳转', 2000)
				.done(function () {
					window.location.reload();
				});
			});
		},
		/**
		 * 加签
		 */
		addSign: function ($modal, $form) {
			$modal.modal('hide');
			$.alert('正在提交【加签】信息...');
			$.post(F.url.addSign, $form.serializeArray())
			.done(function (result) {
				$.alert('提交【加签】成功，页面即将跳转', 2000)
				.done(function () {
					window.location.reload();
				});
			});
		},
		/**
		 * 转办
		 */
		transfer: function ($modal, $form) {
			$modal.modal('hide');
			$.alert('正在提交【转办】信息...');
			$.post(F.url.transfer, $form.serializeArray())
			.done(function (result) {
				$.alert('提交【转办】成功，页面即将跳转', 2000)
				.done(function () {
					window.location.reload();
				});
			});
		},
		/**
		 * 驳回请求
		 */
		back: function ($form,$taskId) {
			$.post(F.url.back, {taskId:$taskId},$form.serializeArray())
			.done(function (result) {
				$.alert('驳回成功，页面即将跳转', 2000)
				.done(function () {
					window.location.reload();
				});
			});
		},
		/**
		 * 撤单请求
		 */
		cancel: function ($modal, $form) {
			$modal.modal('hide');
			$.alert('正在提交【撤单】信息...');
			$.post(F.url.cancel, $form.serializeArray())
			.done(function (result) {
				$.alert('【撤单】成功，页面即将跳转', 2000)
				.done(function () {
					window.location.reload();
				});
			});
		}
	};
	
	F.flowBtnClick = function ($form) {
		var $this = $(this),
			fn, defer;
		
		if (!(fn = F[$this.data('event')])) {
			fn = function () {
				return {'success': true};
			};
		}
		defer = $.Deferred();
		
		defer.then(fn).done(function (result) {
			if (result.success) {
				var $taskId = $this.data('id');
				if($this.hasClass('sign-btn')){
					F.workflow.sign(forms.$sign,$taskId);
				}else{
					F.workflow.back(forms.$back,$taskId);
				}
			}
		});
		defer.resolve();
	};
	
	var modals = {
		//$sign: $('#flowSignDialog'),
		$addSign: $('#flowAddSignDialog'),
		$transfer: $('#flowTransferDialog'),
		//$back: $('#flowBackDialog'),
		$cancel: $('#flowCancelDialog')
	};
	var forms = {
		$sign: $('#eventForm'),
		$addSign: $('#flowAddSignForm'),
		$transfer: $('#flowTransferForm'),
		$back: $('#eventForm'),
		$cancel: $('#flowCancelForm')
	};
	
	$(document)
	.on('click.flow-audit', '#flowAddSignDialog .btn-primary', function () {
		F.workflow.addSign(modals.$addSign, forms.$addSign);
	})
	.on('click.flow-audit', '#flowAddSignDialog .btn-primary', function () {
		F.workflow.transfer(modals.$transfer, forms.$transfer);
	})
	.on('click.flow-audit', '#flowBackDialog .btn-primary', function () {
		F.workflow.back(modals.$back, forms.$back);
	})
	.on('click.flow-audit', '#flowCancelDialog .btn-primary', function () {
		F.workflow.cancel(modals.$cancel, forms.$cancel);
	});
});