define(function (require, exports) {
	var $ = require('jquery');
	
	var _match = String.prototype.match;
	
	/**
	 * 生成随机数，可自定义前缀
	 * @param prefix
	 */
	exports.random = function (prefix) {
		prefix = prefix ? prefix : '';
		return prefix + Math.random().toString(36).substr(2) + Math.random().toString(36).substr(2);
	};
	
	/**
	 * json字符串转成对象
	 */
	exports.json2obj = function (jsonStr) {
		return (new Function('return ' + (jsonStr || 'undefined')))() || {};
	};
	
	/**
	 * 判断是否支持placeholder标签属性
	 */
	exports.supportPlaceholder = function () {
		return "placeholder" in document.createElement("input");
	};
	
	/**
	 * 格式化金钱为 100,000,000.00
	 * @param m
	 */
	exports.money = function (m) {
		var cut = 2, array, h,f,s;
		if (arguments.length == 2) {
			cut = arguments[1] * 1;
		}
		// 非数字直接返回0
		if (!/^\d+(\.\d+)?$/.test(m)) {
			return '0.00';
		}
		array = _match.call(m, /(\d+)/g);
		
		h = array[0];
		s = h.substr(0, h.length % 3);
		if (s) {
			s += ',';
		}
		h = s + h.substr(h.length % 3).split('').reverse().join('').match(/\d{3}/g).join(',').split('').reverse().join('');
		
		f = (array[1] || '') + '00';
		f = String.prototype.substr.call(f, 0, 2);
		
		return h + '.' + f;
		
	};
	
	/**
	 * 格式化日期
	 * @param date
	 * @param seperator
	 */
	exports.formatDate = function (date, seperator) {
		var y = date.getFullYear(), 
			m = date.getMonth() + 1, 
			d = date.getDate();
		if (seperator === undefined) {
			seperator = '';
		}
		
		m = m < 10 ? '0' + m : m;
		d = d < 10 ? '0' + d : d;
		
		return y + seperator + m + seperator + d;
	};
	
	/**
	 * 关闭窗口
	 */
	exports.closeWindow = function () {
		window.opener = null;
		window.open('', '_self', '');
		window.close();
		window.location.replace('about:blank');
	};
	
	exports.reset = function (context) {
		context = context || $(document);
		
		// 重置模糊查询
		var autocompletes = context.find('[data-autocomplete]');
		if (autocompletes.length) {
			// 注意模糊查询的依赖
			require.async('autocomplete', function () {
				autocompletes.each(function () {
					$(this).autocomplete('clear');
				});
			});
		}
		
		// 重置hidden
		context.find('input[type="hidden"]').val('');
		
		// 重置text
		context.find(':text').val('');
		
		// 重置select
		context.find('select').val('');
		
		// 重置checkbox
		context.find(':checkbox').each(function () {
			this.checked = false;
		});
		
		// 重置radio
		context.find(':radio').each(function () {
			this.checked = false;
		});
	};
	
	/**
	 * 监听复选框事件
	 * @param parentSelector 复选框公用最近祖先元素选择器--sizzle，默认table
	 */
	exports.listenCheckbox = function (parentSelector) {
		parentSelector = parentSelector || 'table';
		$(document)
			// 复选框全选
			.on('click.checkbox-ctrl', parentSelector + ' .check-all', function () {
				var $this = $(this),
					$parent = $this.parentsUntil(parentSelector).parent(parentSelector),
					$items = $parent.find('.check-one');
				
				$items.each(function () {
					this.checked = $this[0].checked;
				});
			})
			// 复选框子选
			.on('click.checkbox-ctrl-one', parentSelector + ' .check-one', function () {
				var $this = $(this),
					$parent = $this.parentsUntil(parentSelector).parent(parentSelector),
					all = $parent.find('.check-all')[0],
					$items = $parent.find('.check-one'),
					isCheckedAll = false;
				
				if (this.checked) {
					$items.each(function () {
						if (!this.checked) {
							return (isCheckedAll = false);
						}
						isCheckedAll = true;
					});
				}
				
				all.checked = isCheckedAll;
			});
	};
	
	/**
	 * 获取勾选checkbox的内容，数组返回
	 * @param context jQuery对象，上下文，一般为document，form，table
	 * @param selector 表单checkbox属性选择符
	 * @param fn 获取勾选checkbox的指定属性值，eg. value,data-* etc.
	 * @return {Array}
	 */
	exports.getCheckedBoxes = function (context, selector, fn) {
		var len = arguments.length,
			result = [];
		if (len === 1) {
			selector = context;
			fn = function ($item) {
				return $item.val();
			};
			context = $(document);
		} else if (len === 2) {
			if ($.isFunction(selector)) {
				fn = selector;
				selector = context;
				context = $(document);
			} else {
				fn = function ($item) {
					return $item.val();
				};
			}
		}
		context.find(selector).each(function () {
			if (this.checked) {
				result[result.length] = fn($(this));
			}
		});
		
		return result;
	};

	/**
	 * @param context jQuery对象，上下文
	 * @param selector 选择器
	 * @param strs 简单字符串，逗号隔开
	 */
	exports.setCheckedBoxes = function (context, selector, strs) {
		if (arguments.length == 2) {
			strs = selector;
			selector = context;
			context = $(document);
		}
		
		$.each(strs.split(/\,\s*/), function (i, val) {
			context.find(selector + '[value="' + val + '"]').attr('checked', 'checked');
		});
		
	};
	
});