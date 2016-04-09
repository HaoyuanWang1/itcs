define(function(require, exports) {
	require('jquery');

	var $taskSignerUserPage = $('#taskSignerUserPage');
	$taskSignerUserPage._load(webContext + '/event/panel/UserPage');
});