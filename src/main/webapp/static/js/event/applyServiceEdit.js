define(function(require, exports) {
		require('jquery');
		var $eventlogPage = $('#eventlogPage');
		var eventId = $("#eventId").val();
		var taskId = $('#taskId').val();
		F.save = function () {
			$.post(webContext + '/event/serviceSlove', $('#eventForm').serializeArray())
			.done(function(data){
				if(data.data === "exist"){
					$.alert("解决并提交时，不可修改服务类型！");
				}else{
					$.post(webContext + '/userFlowCtrl/executeTask', {taskId:taskId}).done(function(){
						$.alert("提交成功");
						location.reload() ;
						});
					
					
				}
			});
		};
	
		F.other = function ($form) {
			$form._save(webContext + '/event/serviceOther', function () {
				location.reload() ;
			});
		};
		
		
		function loadRecord() {
			$eventlogPage._load(webContext + '/eventLog/panel/eventlogList/' + eventId).done(function(){
				$("#eventlogPage div.span012:even").css("backgroundColor","#f1f1f1");
			});;
		}
		
		loadRecord();
});