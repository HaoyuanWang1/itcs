define(function(require, exports) {
			require('jquery');
			var $eventlogPage = $('#eventlogPage'),
			    eventId = $("#eventId").val();
			
			F.userBack = function () {
				return $.post(webContext + '/event/userBack', $('#eventForm').serializeArray());
			};
			
			F.save = function () {
				return $.post(webContext + '/event/userPass', $('#eventForm').serializeArray());
			};
			
			function loadRecord() {
				$eventlogPage._load(webContext + '/eventLog/panel/eventlogList/' + eventId).done(function(){
					$("#eventlogPage div.span012:even").css("backgroundColor","#f1f1f1");
				});
			}
			
			loadRecord();
			
});