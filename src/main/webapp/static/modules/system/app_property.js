define(function (require, exports) {
	
	var $ = require('jquery');
	
	F.saveProperty = function ($form){
		$form._save(webContext + "/system/property/save");
	};
	
});