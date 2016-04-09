define(function(require, exports) {
	var $ = require('jquery');
	
	var initDeferred = $.Deferred();
	
	// 重置所有全局组件状态
	function __reset() {
		for (var i = 0, len = F.items.length; i < len; i++) {
			F[F.items[i] + '_complete'] = false;
		}
		F.initCount = F.items.length;
	};
	
	// 完成初始化一项全局组件
	function __complete (item) {
		F.log('%c(^__^) 初始化完成 [ ' + item + ' / ' + F.$kv[item] + ' ].', 'color:#f00');

		F[item + '_complete'] = true;
		F.initCount -= 1;
		if (F.initCount === 0 && initDeferred) {
			initDeferred.resolve();
			__reset();
			F.log('%c所有初始化文件完成!', 'font-size:16px;font-weight:bold;color:#090;');
		}
	};
	
	// 开始遍历初始化指定容器组件
	function __init(context, url) {
		__reset();
		
		context = context || $(document);
		F.log('%c准备开始初始化页面/容器 [ ' + (url || location.href) + ' ]', 'color: #08c');
		
		var length = F.items.length,
			i = 0;
		for (; i < length; i++) {
			// 异步调用，防止闭包
			(function (controller) {
				if (!controller.using) {
					return;
				}
				
				var $selectors = context.find(controller.selector);
				if ($selectors.length) {
					require.async(controller.alias, function (callback) {
						callback[controller.method || 'init'](context, $selectors, __complete, controller);
					});
				} else {
					__complete(controller.name);
				}
			})(F.items[i]);
		}
	};
	
	// 全局组件初始化Promise
	exports.completed = function (context, url) {
		initDeferred = $.Deferred();
		__init(context, url);
		return initDeferred.promise();
	};
	
	// 运行所有业务模块请求
	exports.run = function () {
		var len = F.callList.length;

		if (len === 0) {
			return false;
		}
		
		var factory, type, isArray;
		for (var i = 0; i < len; i++) {
			factory = F.callList[i];
			type = typeof factory;
			isArray = $.isArray(factory);
			if (type === 'function') {
				F.log('运行JS方法 : ' + factory.toString());
				factory();
			} else if (type === 'string' || isArray) {
				F.log('运行JS文件 : ' + (isArray ? factory.join(', ') : factory));
				seajs.use(factory); // seajs 支持调用单个文件和多个数组文件
			}
		}
	};
	
});