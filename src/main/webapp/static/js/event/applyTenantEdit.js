define(function(require, exports) {
	require('jquery');
	require('autocomplete');
	require('autocompletecss');
	/**
	 * 提交
	 */
	F.submitApply = function ($form) {
		$form._save(webContext + '/event/submitApply', function(data){
						location.replace(webContext + '/event/applyPage/'+ data.data);
					});
			};
			
});