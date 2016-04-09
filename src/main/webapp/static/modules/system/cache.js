define(function (require, exports) {
	
	var $ = require('jquery');
	
	
	F.clearByKey = function(){
		var group = $('#cacheGroup').val(),
			key = $('#cacheKey').val();
		if(group==""||key==""){
			$.alert("组和KEY必填");
		}else{
			$.post(webContext + "/system/cache/removeByGroupAndKey",{
				group:group,key:key
				},function(data){
					$.alert("清理完毕");
			});
		}
	};
	
	F.clearByGroup = function(){
		var group = $('#cacheGroup').val();
		$.post(webContext + "/system/cache/removeByGroup",{group:group},function(data){
			$.alert("清理完毕");
		});
	};
	
	F.clearAll = function(){
		$.post(webContext + "/system/cache/removeAll",function(data){
			$.alert("清理完毕");
		});
	}
});

