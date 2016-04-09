define(function (require, exports) {
	var $ = require('jquery'),
		utils = require('utils'),
		$doc = $(document);
	
	exports.init = function (context, editors, complete, controller) {
		context = context || $doc;
		editors = editors || context.find(controller.selector);
		
		var _count = editors.length; 
		if (_count > 0) {
			require.async('kindeditor', function () {
				
				editors.each(function () {
					_count -= 1;
					var editorId = this.id;
					var $target = $(this);
					if (editorId && $target.data('ke-editor-inited')) {
						return true;
					}
					
					$target.data('ke-editor-inited', true);
					var options = $target.data('editor');
					
					if (typeof options === 'string') {
						options = utils.json2obj(options);
					}
					
					var mode = options.mode || 'normal';
					
					if (!editorId) {
						editorId = utils.random('editor');
						this.id = editorId;
					}
					
					var html = this.innerHTML;
					this.innerHTML = html.replace(/\&nbsp;/g, '&amp;nbsp;').replace(/\</g, '&lt;').replace(/\>/g, '&gt;');
					
					F.editors[editorId] = KindEditor.create('#' + editorId, $.extend({}, F.editor[mode], options));
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