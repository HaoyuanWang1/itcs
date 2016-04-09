define(function(require, exports) {
	require('jquery');
	var $eventlogPage = $('#eventlogPage');
	var eventId = $("#eventId").val();
	
	F.other = function ($form) {
		$form._save(webContext + '/event/userMessage', function () {
			location.reload() ;
		});
	};
	
	function loadRecord() {
		$eventlogPage._load(webContext + '/eventLog/panel/eventlogList/' + eventId).done(function(){
			$("#eventlogPage div.span012:even").css("backgroundColor","#f1f1f1");
		});
	}
	
	loadRecord();
});