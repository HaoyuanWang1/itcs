define(function(require, exports) {
	require('jquery');
	var $eventlogPage = $('#eventlogPage');
	var eventId = $("#eventId").val();
			F.save = function ($form) {
				return $.post(webContext + '/event/saveEventApply', $('#eventForm').serializeArray());
	};
	
	

			
			function loadRecord() {
				$eventlogPage._load(webContext + '/eventLog/panel/eventlogList/' + eventId).done(function(){
					$("#eventlogPage div.span012:even").css("backgroundColor","#f1f1f1");
				});;
			}
			loadRecord();
});