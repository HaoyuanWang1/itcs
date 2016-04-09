;(function (window) {
	
	// 编辑器菜单
	var editItems = [ 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor',
          'bold', 'italic', 'underline', 'removeformat', '|',
          'justifyleft', 'justifycenter', 'justifyright',
          'insertorderedlist', 'insertunorderedlist', '|', 'image', 'link' ];
	
	// 定义全局变量，缓存对象
	window.F = {
		editors: {},
		uploaders: {},
		ztrees: {},
		
		// 全局待初始化组件列表，
		// name: 组件key 
		// desc: 组件描述
		// alias：seajs调用路径别名 
		// selector：组件初始化方法调用选择器 
		// using：是否开始该组件的全局初始化
		$kv: {
			editor: '富文本编辑器',
			calendar: '日期插件',
			user: '用户选择模糊查询',
			image: '图片上传',
			attachment: '附件上传',
			file: '查找显示附件列表',
			form: '表单初始化',
			flowOperate: '流程事件监听'
		},
		items: [{
			name: 'editor',
			desc: '富文本编辑器',
			alias: 'editor',
			selector: '[data-editor]',
			using: true
		}, {
			name: 'calendar',
			desc: '日期插件',
			alias: 'calendarInit',
			selector: '[data-calendar]',
			using: true
		}, {
			name: 'user',
			desc: '用户选择模糊查询',
			alias: 'users',
			selector: '[data-user-config]',
			using: true
		}, {
			name: 'image',
			desc: '图片上传',
			alias: 'images',
			selector: '[data-image]',
			using: true
		}, {
			name: 'attachment',
			desc: '附件上传',
			alias: 'attachment',
			selector: '[data-attachment]',
			using: true
		}, {
			name: 'file',
			desc: '查找显示附件列表',
			alias: 'attachment',
			selector: '[data-attachment-list]',
			method: 'load',
			using: true
		}, {
			name: 'form',
			desc: '表单初始化',
			alias: 'form',
			selector: 'form',
			using: true
		}, {
			name: 'flowOperate',
			desc: '流程事件监听',
			alias: 'flowOperate',
			selector: '[data-flow-operate]',
			using: true
		}],
		callList: [], // 用户自定义回调方法
		editor: {
			normal: {
				resizeType : 0,
				formatUploadUrl: false,
				allowImageRemote: false,
				pasteType: 1,
				minWidth: 100,
				uploadJson : webContext + '/image/upload',
				items : editItems
			},
			
			// 邮件模板
			mail: {
				resizeType : 1,
				allowPreviewEmoticons : false,
				allowImageUpload : false,
				minWidth: 100,
				items : [
					'source','fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
					'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
					'insertunorderedlist', '|', 'link','fullscreen']
			}
		}
	};
	
	F.initCount = F.items.length;
	// 添加用户业务模块方法进入队列等待被调用
	F.run = function (factory) {
		F.callList.push(factory);
	};
	
	// 调试用消息打印
	F.debug = true;
	F.log = function () {
		if (window.console && F.debug) {
	        Function.apply.call(console.log, console, arguments);
	    }
	};
	
	/************* seajs 配置  ************/
	seajs.config({
		// 别名配置
		alias : {
			// 前提依赖
			// jQuery @https://github.com/jquery/jquery/
			'jquery' : 'core/jquery/1.8.3/jquery.min',
			// bootstrap @http://www.bootcss.com/
			'bootstrap' : 'core/bootstrap/js/bootstrap.min',
			
			// 工具
			'utils': 'config/utils',
			
			// 控制器
			'controller': 'config/controller/controller',
			
			// 配置封装
			'extend': 'config/extend',
			
			// 监听组件初始化
			'editor': 'config/controller/editor-controller',
			'calendarInit': 'config/controller/calendar-controller',
			'form': 'config/controller/form-controller',
			'attachment': 'config/controller/file-upload-controller',
			'images': 'config/controller/image-upload-controller',
			'users': 'config/controller/user-select-controller',
			'flowOperate': 'config/controller/flow-operate',
			
			/****** 插件库 ************/
			// 进度条 @http://ricostacruz.com/nprogress
			'nprogress': 'core/nprogress/nprogress',
			
			// jquery.ztree 树 @http://www.ztree.me/v3/api.php
			'ztree' : 'core/ztree/js/jquery.ztree.all-3.5.min',
			'ztreeexhide' : 'core/ztree/js/jquery.ztree.exhide-3.5.min',
			'ztreecss': 'core/ztree/css/zTreeStyle.css',
			
			// Kindeditor 富文本编辑器 @http://kindeditor.net/doc.php
			'kindeditor': 'core/kindeditor/kindeditor-min',
			'kindeditorcss': 'core/kindeditor/themes/default/default.css',
			
			// jquery.autocomplete jQuery模糊匹配插件
			'autocomplete': 'core/form/jquery.autocomplete.min',
			'autocompletecss': 'core/form/jquery.autocomplete.css',
			
			// jquery.calendar jQuery日期插件
			'calendar': 'core/form/jquery.calendar.min',
			'calendarcss': 'core/form/jquery.calendar.css',
			
			// jquery.validate jQuery表单验证插件
			'validate': 'core/form/jquery.validate',
			'validatecss': 'core/form/jquery.validate.css',
			
			//jquery.flot jQuery柱形图插件
			'flot': 'core/flot/jquery.flot.js',
			'flotpie': 'core/flot/jquery.flot.pie.js',
			'flottime': 'core/flot/jquery.flot.time.js',
			'excanvas': 'core/flot/excanvas.min.js',
			'flotaxislabels': 'core/flot/jquery.flot.axislabels.js'
		},
		// 路径配置
		paths : {
			'static' : webContext + '/static', 			// 静态文件包
			'core': webContext + '/static/core', 		// 依赖包
			'config': webContext + '/static/config',	// 项目配置包
			'modules': webContext + '/static/modules'	// 业务模块包
		},
		shim : {
			'jquery' : {
				exports : 'jQuery'
			},
			'bootstrap' : {
				deps : [ 'jquery' ]
			},
			'ztree' : {
				deps : [ 'jquery' ]
			},
			'kindeditor' : {
				deps : [ 'jquery' ]
			},
			'ztreeexhide': {
				deps: [ 'ztree' ]
			},
			'flot': {
				deps: [ 'jquery' ]
			},
			'flotpie': {
				deps: [ 'flot' ]
			},
			'flottime': {
				deps: [ 'flot' ]
			},
			'flotaxislabels': {
				deps: [ 'flot' ]
			}
		},
		preload : [ 'jquery'],
		base : webContext + '/static/core/seajs/',
		// 文件编码
		charset : 'utf-8'
	});
	
	seajs.use(['jquery', 'controller', 'utils', 'extend', 'bootstrap', 'form'], function ($, controller, utils) {
		
		$(function () {
			
			controller.completed().done(function () {
				controller.run();
			});
			
			// 全局 按钮/链接 监听方法
			$(document)
				
				// 弹出确认调用删除ajax
				.on('click.remove-entity-by-url', 'a[data-remove]', function () {
					var $this = $(this),
						callback = $this.data('remove');
					$this._remove().done(function (link) {
						if ($.isFunction(F[callback])) {
							F[callback].call($this);
						} else {
							window.location.reload();
						}
					});
					return false;
				})
				
				// 弹出确认调用ajax，为异步删除、异步保存的补充功能
				.on('click.confirm-and-refresh', 'a[data-confirm]', function () {
					var $this = $(this),
						url = this.href,
						// 逗号隔开的两条信息，1：确认信息，2：后台操作完成信息
						messages = $this.data('confirm').split(/\,\s*/);
					
					$.confirm(messages[0])
						.done(function () {
							$.post(url, function () {
								$.alert(messages[1], 1000).done(function () {
									location.reload();
								});
							});
						});
					
					return false;
				})
				
				// 异步加载进入弹出框并显示
				.on('click.edit-entity-by-modal', 'a[data-load]', function () {
					var $this = $(this),
						callback = $this.data('load-callback'),
						$target = $($this.data('load'));
					
					if ($target.hasClass('modal')) { // 容器为弹出bootstrap.modal
						$target.modal('show')._load(this.href)
						.done(function (modal) {
							if (typeof F[callback] === 'function') {
								// modal弹出并加载完成页面后的回调操作，
								// 一般用来做全局初始化组件外的其他初始化和事件绑定
								F[callback](modal);
							}
						});
						
						$target.on('hide', function () {
							// 弹出层关闭后，清除弹出层里注册了验证的表单
							// 比如 清除弹出层里出现的验证提示图标等
							seajs.use('form', function (form) {
								form.clearValidate($target);
							});
						});
						
					} else { 
						
						// 非弹出，直接为目标容器加载异步HTML
						$target._load(this.href).done(function () {
							// 全局初始化组件外的其他初始化和事件绑定
							if (typeof F[callback] === 'function') {
								F[callback](modal);
							}
						});
					}

					return false;
				})
				
				// 分页器异步加载
				.on('click.pagination-ajax-load', '.pagination [data-target]', function () {
					var $this = $(this);
					var $target = $($this.data('target'));
					$target._load(this.href);
					return false;
				})
				
				// 报表导出
				// 查询和报表导出共享同一个查询表单，但是form只有一个action，一般为查询方法，
				// 所以在调用报表导出时，抓取表单已填写数据、序列化到导出url上，采用超链接跳转方式调用
				.on('click.export-excel', 'a[data-export]', function () {
					var $this = $(this);
					// 每次导出报表之前清空超链接上原有传参，避免后台解析参数错误
					this.href = this.href.replace(/\?.*/, '') +  '?' + $($this.data('export')).serialize();
					return true;
				});
		});
	});
})(window);