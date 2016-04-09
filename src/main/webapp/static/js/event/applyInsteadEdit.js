define(function(require, exports) {
	
	var $ = require('jquery');
	F.submitApply = function ($form) {
		$form._save(webContext + '/event/submitApply', function(data){
			location.replace(webContext + '/event/applyPage/'+ data.data);
					});
			};
});