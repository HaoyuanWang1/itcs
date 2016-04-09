define(function (require, exports) {
	var $ = require('jquery');
	
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
		sign: function ($modal, $form) {
			$modal.modal('hide');
			$.alert('正在提交【审批】信息...');
			$.post(F.url.sign, $form.serializeArray())
			.done(function (result) {
				$.alert('【审批】成功，页面即将跳转', 2000)
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
		back: function ($modal, $form) {
			$modal.modal('hide');
			$.alert('正在提交【驳回】信息...');
			$.post(F.url.back, $form.serializeArray())
			.done(function (result) {
				$.alert('【驳回】成功，页面即将跳转', 2000)
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
			$modal = $($this.data('modal')),
			fn, defer;
		
		if (!(fn = F[$this.data('event')])) {
			fn = function () {
				return {'success': true};
			};
		}
		defer = $.Deferred();
		defer.then(fn).done(function (result) {
			if (result.success) {
				$modal.modal('show');
			}
		});
		defer.resolve();
	};
	
	$(document).ready(function () {
		var modals = {
			$sign: $('#flowSignDialog'),
			$addSign: $('#flowAddSignDialog'),
			$transfer: $('#flowTransferDialog'),
			$back: $('#flowBackDialog'),
			$cancel: $('#flowCancelDialog')
		},
		forms = {
			$sign: $('#flowSignForm'),
			$addSign: $('#flowAddSignForm'),
			$transfer: $('#flowTransferForm'),
			$back: $('#flowBackForm'),
			$cancel: $('#flowCancelForm')
		};
		
		
		$(document)
		// 以下为dialog审批框确定操作
		.on('click.flow-audit', '#flowSignDialog .btn-primary', function () {
			F.workflow.sign(modals.$sign, forms.$sign);
		})
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
	
});