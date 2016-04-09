define(function (require, exports) {
	var $ = require('jquery');
	var utils = require('utils');
	require('autocomplete');
	require('autocompletecss');
	
	$('#roleUser').autocomplete({
		source: function (pattern, response) {
			$.getJSON(webContext + '/userRole/select', {
				searchKey: pattern,
				size: 20
			}).done(function (data) {
				response(data.data);
			});
		},
		width: 220,
		minlength: 0,
		target: ['uid', 'name'],
		format: function( item ) {
			return {label: item.uid+'/'+item.userName, 
				uid: item.uid, 
				name: item.userName, 
				value: item.id};
		}
	});
	
	F.saveUserRole = function ($form) {
		$form._save(webContext + '/userRole/saveUserRole', function (data) {
			if (data.success) {
				$.alert('提交成功', 1000).done(function () {
					location.reload();
				});
			}
		});
	};
	
});