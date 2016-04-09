define(function (require, exports) {
	var $ = require('jquery'),
		utils = require('utils'),
		$doc = $(document);
	
	/**
	 * 初始化日期选择插件
	 * @param context 选择初始化的上下文范围
	 */
	exports.init = function (context, calendars, complete, controller) {
		context = context || $doc;
		calendars = calendars || context.find(controller.selector);
		var _count = calendars.length; 
		if (_count > 0) {
			
			require.async(['calendar', 'calendarcss'], function () {
				calendars.each(function () {
					_count -= 1;
					var $target = $(this),
						options = $target.data('calendar');
					
					if (typeof options === 'string') {
						options = utils.json2obj(options);
					}
					
					options.zIndex = 1050;
					options.size = options.size || 3;
					
					var touch = options.touch;
					var enable = options.enable;
					var min, max;
					if (touch === false && enable) {
						min = enable[0];
						max = enable[1];
						if (undefined !== min && min.setDate) {
							min.setDate(min.getDate() + 1);
						}
						if (undefined !== max && max.setDate) {
							max.setDate(max.getDate() - 1);
						}
						options.enable = [min, max];
					}
					
					if (options.pair) {  // 说明是区间日历
						var startDate = $target,
						endDate = context.find('[name="' + options.pair + '"]');
						
						startDate.addClass('date date-mini').attr('readonly', 'readonly');
						endDate.addClass('date date-mini').attr('readonly', 'readonly');
						
						options.start = startDate;
						options.end = endDate;
						
						$.calendarPair(options);
					} else {
						options.change = function (date) {
							$target.blur();
						};
						$target.attr('readonly', 'readonly').addClass('date');
						$target.calendar(options);
					}
				});
				
				if (_count === 0) {
					complete(controller.name);
				}
				
			});
		} else {
			complete(controller.name);
		}
		
	};
});