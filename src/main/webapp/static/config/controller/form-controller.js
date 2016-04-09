// 表单事件监听
define(function (require, exports) {
	var $ = require('jquery'),
		utils = require('utils'),
		$doc = $(document);
	// 是否有表单提交处于进行中
	var SubmitPending = false;
	
	exports.init = function (context, forms, complete, controller) {
		context = context || $doc;
		forms = forms || context.find(controller.name);
		var _count = forms.length;
		if (_count > 0) {
			require.async(['validate', 'validatecss'], function () {
				
				forms.each(function () {
					var $form = $(this),
						registed = $form.data('validate-api'),
						options = $form.data('validate'),
						listened = $form.data('form-submit-listen'),
						target = $form.data('target');
					
					// 检测表单是否需要进行验证插件绑定，以及是否已经绑定
					if (!registed && options) {
						options = (new Function('return ' + options))() || {};
						
						// 以bootstrap的modal方式打开的form，调整zIndex
						if (context.hasClass('modal')) {
							options.zIndex = 1060;
						}
						
						$form.validate(options);
					}
					// 检测表单是否已经进行提交监听
					if (!listened) {
						$form.on('submit.form-submit-listen', function () {
							if (SubmitPending) {
								F.log('表单已经提交，不允许其他操作进入');
								return false;
							}
							var valid = false;
							valid = listen($form);
							
							// 纯查询表单
							if (target) {
								var $target = $(target);
								$target._load($form[0].action, $form.serialize());
								return false;
							}
							if (valid) { // 校验通过，进入submit后台
								SubmitPending = true;
							}
							return valid;
						}).data('form-submit-listen', true);
					}
					
					_count -= 1;
				});
				
				if (_count === 0) {
					complete(controller.name);
				}
				
			});
		} else {
			complete(controller.name);
		}
		
	};
	
	/**
	 * 保存前整理表单
	 */
	exports.syncForm = function ($form) {
		// kindeditor同步
		$form.find('[data-editor]').each(function() {
			var ed = F.editors[this.id];
			if (ed && ed.sync) {
				ed.sync();
			}
		});
	};
	
	/**
	 * 清除表单验证
	 */
	exports.clearValidate = function (context) {
		var $form;
		if (!context) {
			context = $doc;
		} 

		if (context.get().nodeName === 'FORM') {
			$form = context;
		} else {
			$form = context.find('form');
		}
		
		$form.each(function () {
			var $this = $(this);
			if ($this.data('validate-api')) {
				$this.validate('clear');
			}
		});
	};
	
	/**
	 * 监听表单，并回调
	 */
	function listen($form, callback) {
		var validate = $form.data('validate-api'),
			valid = false,
			that = this;
		
		if (!callback) {
			callback = function () {};
		}
		
		exports.syncForm($form);
		
		if (validate) {
			var before = validate.options.before;
			// 校验之前需要做的操作
			if (before && $.isFunction(F[before])) {
				(function () {
					var defer = $.Deferred();
					F[before].call(that, $form, defer);
					return defer.promise();
				})().done(function () {
					// 准备完成，开始校验整个表单
					$form.validate('run')
						.done(function () {
							checkAttachment($form);
							valid = true;
							callback.call(that, $form);
						})
						.fail(function () {
							valid = false;
							$.alert('表单有内容填写不正确，请返回修改', 1500).done(function () {
								$.alert('close');
							});
						});
				});
			} else {
				$form.validate('run')
					.done(function () {
						checkAttachment($form);
						valid = true;
						callback.call(that, $form);
					}).fail(function () {
						valid = false;
						$.alert('表单有内容填写不正确，请返回修改', 1500).done(function () {
							$.alert('close');
						});
					});
			}
			
		} else {
			checkAttachment($form);
			callback.call(that, $form);
			valid = true;
		}
		return valid;
	}
	
	// 表单验证成功之后，非必填附件字段，如果是因为清空了附件造成的value为空，需要重置为原key，以便后端保存操作时删除
	function checkAttachment($form) {
		$form.find('[data-attachment]').each(function () {
			var $atta = $(this);
			var old_entityKey = $atta.data('old-entity-key');
			if (!this.value && old_entityKey) {
				this.value = old_entityKey;
			}
		});
	}
	
	$doc
		.on('click.listen-btn-operate-form', '[data-listen]', function () {
			var $this = $(this),
				$form = $($this.data('target')),
				callback = F[$this.data('listen')];
			
			var self = this;
			// 防止按钮多次重复点击
			if (this.tagName === 'input' && (this.type === 'button' || this.type === 'submit')) { // 按钮点击
				this.disabled = true;
				window.setTimeout(function () {
					self.disabled = false;
				}, 1500);
			}
			
			// 非表单按钮操作，直接调用监听方法
			if (!$this.data('target')) {
				callback.call(this, $this);
				return false;
			}
			
			listen.call(this, $form, callback);
			
			return false;
		})
		.on('click.listen-form-reset', '[data-reset]', function () {
			$form = $($(this).data('reset'));
			utils.reset($form);
			return false;
		});
	
});