define(function (require, exports) {
	
	var $ = require('jquery');
	
	F.saveScheduler = function ($form){
		$form._save(webContext + "/system/scheduler/save");
	};
	
});