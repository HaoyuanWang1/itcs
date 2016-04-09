define(function (require, exports) {
	var $ = require('jquery'),
		utils = require('utils'),
		$doc = $(document);
	
	/**
	 * 初始化人员选择autocomplete
	 * @param context 选择初始化的上下文范围
	 */
	exports.init = function (context, users, complete, controller) {
		context = context || $doc;
		users = users || context.find(controller.selector);
		var _count = users.length;
		if (_count > 0) {
			
			require.async(['autocomplete', 'autocompletecss'], function () {
				
				users.each(function () {
					_count -= 1;
					var $target = $(this),
						options = $target.data('user-config');
					
					if (typeof options === 'string') {
						options = utils.json2obj(options);
					}
					
					var config = {
						source: function (pattern, response) {
							$.getJSON(webContext + '/user/select', {
								searchKey: pattern,
								size: options.size || 30
							}).done(function (data) {
								response(data.data);
							});
						},
						width: 220,
						minlength: 0,
						target: ['uid', 'name'],
						format: function( item ) {
							return {label: item.userText, uid: item.uid, name: item.userName, value: item.id};
						}
					};
					
					$(this).autocomplete($.extend({}, config, options));
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