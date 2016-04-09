define(function (require, exports) {
	'use strict';
	
	var $ = require('jquery');
	var NP = require('nprogress');
	var utils = require('utils');
	var controller = require('controller');
	
	/**********      模拟alert confirm prompt       **********/
	/**
	 * $.alert 消息提示弹出
	 */
	(function () {
		function _create() {
			var random = String.prototype.replace.call(Math.random(), /\D/, ''),
				backdropId = 'alertBackdrop' + random,
				alertId = 'alertModal' + random,
				html = '<div class="alert-backdrop modal-backdrop fade hide" id="' + backdropId + '"></div>'
					 + '<div class="alert modal fade" id="' + alertId + '"></div>';
			$('body').append(html);
			
			$.data(document, 'alert-in-engineer', {
				backdrop: $('#' + backdropId),
				alertBody: $('#' + alertId)
			});
			
			$(document)
				.on('click.close-alert-modal', '.alert.modal .close', function () {
					_close();
				});
		}
		
		function _show(title, message, data) {
			var html = '<button type="button" class="close">&times;</button>';
			if (message) {
				html += '<h4>' + title + '</h4><blockquote><p>' + message + '</p></blockquote>';
			} else {
				html += '<h3 class="text-center">' + (title || '') + '</h3>';
			}
			
			data.backdrop.addClass('in');
			data.alertBody.addClass('in').html(html);
		}
		
		function _close() {
			var data = $.data(document, 'alert-in-engineer');
			if (data) {
				data.alertBody.removeClass('in').empty();
				data.backdrop.removeClass('in');
			}
		}
		
		function _alert(title, message, delay) {
			var d = $.Deferred(),
				data = $.data(document, 'alert-in-engineer');
			
			if ($.isNumeric(message)) {
				delay = message;
				message = null;
			}
			if (!data) {
				_create();
				data = $.data(document, 'alert-in-engineer');
			}
			_show(title, message, data);
			if (delay) {
				setTimeout(function () {
					d.resolve(_close);
				}, delay);
			} else {
				d.resolve(_close);
			}
			
			return d.promise();
		};
		
		$.alert = function (title, message, delay) {
			if (title === 'close') {
				_close();
				return this;
			} else {
				return $.when(_alert(title, message, delay));
			}
			
		};
	})();

	/**
	 * $.confirm 操作确认弹出
	 */
	(function () {
		function _create(sureText, cancelText, defer) {
			var data = $.data(document, 'confirm-in-engineer'),
				random, backdropId, confirmId, confirmModal, confirmFooter, html;
			
			if (!data) {
				random = String.prototype.replace.call(Math.random(), /\D/, ''),
				backdropId = 'confirmBackdrop' + random,
				confirmId = 'confirmModal' + random,
				html = [ 
				        '<div class="confirm-backdrop modal-backdrop fade hide" id="' + backdropId + '"></div>',
				        '<div class="confirm modal fade" id="' + confirmId + '">',
				        '	<div class="modal-body"></div>',
				        '	<div class="modal-footer"></div>',
				        '</div>'
				        ];
				$('body').append(html.join(''));
				confirmModal = $('#' + confirmId);
				confirmFooter = confirmModal.find('.modal-footer');
			} else {
				confirmModal = data.confirmModal;
				confirmFooter = data.confirmFooter;
			}
			
			confirmFooter
				.html('<input type="button" class="btn confirm-cancel" value="' + cancelText + '" />' +
				      '<input type="button" class="btn btn-primary confirm-sure" value="' + sureText + '" />')
				.one('click.confirm-cancel', '.confirm-cancel', function () {
					_close(defer, false);
				})
				.one('click.confirm-sure', '.confirm-sure', function () {
					_close(defer, true);
				});

			return data || $.data(document, 'confirm-in-engineer', {
				backdrop: $('#' + backdropId),
				confirmModal: confirmModal,
				confirmBody: confirmModal.find('.modal-body'),
				confirmFooter: confirmModal.find('.modal-footer')
			});
		}
		
		function _show(message, data) {
			data.backdrop.addClass('in');
			data.confirmModal.addClass('in');
			data.confirmBody.html(message || '');
		}
		
		function _close(defer, isSure) {
			var data = $.data(document, 'confirm-in-engineer');
			if (data) {
				data.confirmModal.removeClass('in');
				data.backdrop.removeClass('in');
				data.confirmBody.empty();
				if (isSure) {
					defer.resolve();
				} else {
					defer.reject();
				}
			}
		}
		
		function _confirm(message, sureText, cancelText) {
			var d = $.Deferred(),
				data;
			
			sureText = sureText || '确定';
			cancelText = cancelText || '取消';
			data = _create(sureText, cancelText, d);
			
			_show(message, data);
			
			return d.promise();
		};
		
		$.confirm = function (message, sureText, cancelText) {
			return $.when(_confirm(message, sureText, cancelText));
		};
	})();

	/**
	 * $.prompt 操作确认弹出
	 */
	(function () {
		function _create(defer) {
			var data = $.data(document, 'prompt-in-engineer'),
				random, backdropId, promptId, promptModal, promptHeader, promptText, html;
			
			if (!data) {
				random = String.prototype.replace.call(Math.random(), /\D/, ''),
				backdropId = 'promptBackdrop' + random,
				promptId = 'promptModal' + random,
				html = [ 
				        '<div class="prompt-backdrop modal-backdrop fade hide" id="' + backdropId + '"></div>',
				        '<div class="prompt modal fade" id="' + promptId + '">',
				        '	<div class="modal-header">',
				        '		<h3></h3>',
				        '	</div>',
				        '	<div class="modal-body">',
				        '		<input type="text" class="span6" />',
				        '	</div>',
				        '	<div class="modal-footer">',
				        '		<input type="button" class="btn prompt-cancel" value="取消" />',
					    '		<input type="button" class="btn btn-primary prompt-sure" value="确定" />',
					    '	</div>',
				        '</div>'
				        ];
				$('body').append(html.join(''));
				promptModal = $('#' + promptId);
				promptHeader = promptModal.find('h3');
				promptText = promptModal.find('input[type="text"]');
			} else {
				promptModal = data.promptModal;
				promptText = data.promptText;
			}
			
			promptText.on('keyup.prompt-sure-key', function (evt) {
				if (evt.keyCode === 13) {
					_close(defer, true);
				}
			});
			
			promptModal
				.on('click.prompt-cancel', '.prompt-cancel', function () {
					_close(defer, false);
				})
				.on('click.prompt-sure', '.prompt-sure', function () {
					_close(defer, true);
				});

			return data || $.data(document, 'prompt-in-engineer', {
				backdrop: $('#' + backdropId),
				promptModal: promptModal,
				promptHeader: promptHeader,
				promptText: promptText
			});
		}
		
		function _show(title, data) {
			data.backdrop.addClass('in');
			data.promptModal.addClass('in');
			data.promptHeader.html(title || '');
			data.promptText[0].focus();
		}
		
		function _close(defer, isSure) {
			var data = $.data(document, 'prompt-in-engineer');
			if (data) {
				_off(data);
				if (isSure) {
					defer.resolve(data.promptText.val());
				} else {
					defer.reject(null);
				}

				data.promptModal.removeClass('in');
				data.backdrop.removeClass('in');
				data.promptHeader.empty();
				data.promptText.val('');
				
			}
		}
		
		function _off(data) {
			data.promptText.off('keyup.prompt-sure-key');
			data.promptModal
				.off('click.prompt-cancel', '.prompt-cancel')
				.off('click.prompt-sure', '.prompt-sure');
		}
		
		function _prompt(title) {
			var d = $.Deferred();
			title = title || '请输入';
			_show(title, _create(d));
			return d.promise();
		};
		
		$.prompt = function (title) {
			return $.when(_prompt(title));
		};
	})();
	
	
	/***********    Ajax请求封装     ************/
	
	// Ajax请求控制器，防止用户误操作造成的连点和重复提交
	var AjaxUrls = {
		list: {},
		has: function (key) {
			return !!this.list[this.transfer(key)];
		},
		reg: /[\:\/\?\%\&\=\#]/g,
		transfer: function (key) {
			return key.replace(/\?.*/, '').replace(this.reg, '_')
		},
		push: function (key) {
			var _key = this.transfer(key);
			this.list[_key] = true; // 把当前URL请求加入进行状态
		},
		finish: function (key) {
			var _key = this.transfer(key);
			if (this.list[_key]) {
				delete this.list[_key];
			}
		}
	};

	$.ajaxSetup({
		global: true,
		beforeSend: function (jqxhr, settings) {
			F.log("\n^∆^ ^∆^ ^∆^ ^∆^ ^∆^");
			var url = settings.url;
			if (AjaxUrls.has(url)) { // URL存在在当前请求中
				jqxhr.abort('abortRepeat'); // 中断重复ajax请求
			} else {
				AjaxUrls.push(url);
			}
		}
	});
	
	// jQuery.ajax运行顺序 beforeSend > ajaxSend > ajaxSuccess+ajaxError > ajaxComplete > ajaxStop
	
	$(document)
		.ajaxSend(function ( event, jqxhr, settings ) {
			// 所有ajax请求加上随机码，防止缓存
			var clc = 'itss_time_clear_cache=' + utils.random();
			var url = settings.url.replace(/itss_time_clear_cache=.+?&(.*)/, '$1'); // 去重

			F.log('%c准备发起jQuery Ajax请求[' + url + ']', 'color:#099;background:#fef;font-size:14px');
			
			if (/\?$/.test(url) || /&$/.test(url)) {
				url += clc;
			} else if (/\?.+/.test(url)) {
				url += '&' + clc;
			} else {
				url += '?' + clc;
			}
			settings.url = url;
			NP.start();
		})
//		.ajaxStop(function () {
//			F.log("\n");
//			F.log('%c本次Ajax请求结束', 'color:#ff0;background:#222;font-size:18px;');
//		})
		.ajaxError(function (event, jqxhr, settings, thrownError) {
			F.log('%c本次Ajax请求出错', 'color:#f00;font-size:14px;');
			
			if (jqxhr.statusText === 'abortRepeat') {
				F.log('%c截断重复提交', 'color:#f90;font-size:16px;text-decoration:underline;');
			} else {
				F.log('%c' + jqxhr.statusText, 'background:#08c;color:#fff;font-style:italic;font-size:14px;');
				
				$.alert('请求错误，状态码：' + jqxhr.status, '错误信息：[ ' + jqxhr.statusText + ' ]，请联系系统管理员');
			}
		})
		.ajaxComplete(function (event, jqxhr, settings) {
			NP.done();
			if (jqxhr.statusText !== 'abortRepeat') {
				AjaxUrls.finish(settings.url);
			}
			
			F.log('%c本次Ajax请求结束', 'color:#0ff;text-shadow:1px 1px 1px #000;font-size:14px');
		})
		.ajaxSuccess(function (event, jqxhr, settings, result) {
			F.log('%c^_^本次Ajax请求成功', 'color:#080;text-shadow:1px 1px 1px #999;font-size:14px');
			
			var type = jqxhr.getResponseHeader('content-type');
			// 如果后台设置了json格式content-type，但是前端js设置的是其他格式接收数据，会被转掉
			if (/application\/json/i.test(type)) {
				if (typeof result === 'string') {
					result = (new Function('return ' + result))();
				}
				// 错误信息 AjaxResult中success:false表示后面返回错误信息
				// 后台业务逻辑异常
				if (result.success === false) {
					if (result.message === 'login') {
						$.alert('您没有登录，或者已经登录超时', '<a href="' + webContext + '/login">点击登录</a>');
					} else {
						$.alert('出现异常，信息如下：', result.message || result.error);
					}
				} else if (result.success === undefined) {
					if (result.exception) {
						// 后台java运行异常
						$.alert('后台处理异常，请联系系统管理员，异常信息如下：', result.error);
					}
				}
			}
		});
	
	var _ajax = $.ajax;
	
	$.ajax = function (url, options) {
		var defer = $.Deferred();
		
		if ( typeof url === "object" ) {
			options = url;
			url = undefined;
		}
		
		var _s = options.success;
		if ($.isFunction(_s)) {
			options.success = function (result, textStatus, jqxhr) {
				var type = jqxhr.getResponseHeader('content-type');
				if (/application\/json/i.test(type)) {
					if (typeof result === 'string') {
						result = (new Function('return ' + result))();
					}
					if (result.success === true) {
						_s(result, textStatus, jqxhr);
					}
				} else {
					_s(result, textStatus, jqxhr);
				}
			};
		}
		
		var jqxhr = _ajax(url, options);
		jqxhr.done(function (result) {
			var type = jqxhr.getResponseHeader('content-type');
			if (/application\/json/i.test(type)) {
				if (typeof result === 'string') {
					result = (new Function('return ' + result))();
				}
				if (result.success === true) {
					defer.resolve(result);
				}
			} else {
				defer.resolve(result);
			}
		});
		
		return defer.promise();
	};
	
	/*********       异步方法重新封装新增        ********/
	
	// 异步加载HTML代码，回写到页面并初始化组件
	$.fn._load = function (url, param) {
		var that = this;
		return $.get(url, param, 'html').done(function (html) {
			that.html(html);
			return controller.completed(that, url);
		});
	};
	
	// 异步删除组件，弹出确认按钮
	$.fn._remove = function (option) {
		var defer = $.Deferred(),
			that = this,
			option = option || {},
			message = option.message || '确认删除？',
			getUrl = option.getUrl || function (link) {
				return link.href; 
			};
		
		$.confirm(message).done(function () {
			$.post(getUrl(that[0])).done(function () {
				$.alert('删除成功', 1000).done(function(close) {
					close();
					defer.resolve(that[0]);
				});
			});
		});
		
		return defer.promise();
	};
	
	// 异步加载HTML并以bootstrap.modal的形式打开显示和初始化
	// ** 此方法好像一直没有用上
	$.fn._modal = function (option) {
		var defer = $.Deferred(),
			that = this,
			option = option || {},
			getUrl = option.getUrl || function (link) {
				return link.href;
			},
			target = option.target || this.data('load'),
			modal;
		if (typeof target === 'string') {
			modal = $(target);
		} else if (target.jquery) {
			modal = target;
		}
		
		modal._load(getUrl(this[0])).done(function() {
			modal.modal('show');
			defer.resolve(modal);
		});
		return defer.promise();
	};
	
	// 表单异步保存
	$.fn._save = function (saveUrl, refreshUrl) {
		$.post(saveUrl, this.serializeArray())
			.done(function (data) {
				if (data.success) {
					if (refreshUrl) {
						if (typeof refreshUrl === 'function') {
							$.alert('提交成功,即将跳转', 1000)
								.done(function (close) {
									close();
									refreshUrl(data);
								});
						} else {
							$.alert('提交成功，即将跳转', 1000)
							.done(function () {
								location.replace(refreshUrl + data.data);
							});
						}
					} else {
						$.alert('提交成功，即将刷新', 1000)
						.done(function () {
							location.reload();
						});
					}
				}
			});
	};
	
	// 解决赋值为null的问题，记住下面必须是【===】
	$.fn.text = function (value) {
		if (null === value) {
			value = '';
		}
		return jQuery.access( this, function( value ) {
			return value === undefined ?
				jQuery.text( this ) :
				this.empty().append( ( this[0] && this[0].ownerDocument || document ).createTextNode( value ) );
		}, null, value, arguments.length );
	};
	
});