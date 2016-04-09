define(function (require, exports) {
	var $ = require('jquery');
	var utils = require('utils');
	
	utils.listenCheckbox();
	
	F.saveUserRole = function ($form) {
		var ids = utils.getCheckedBoxes($form, '.check-one');
		$('#roleIds').val(ids.join(','));
		$form._save(webContext + '/userRole/saveUserRoleForUser', function (data) {
			if (data.success) {
				$.alert('提交成功', 1500).done(function () {
					location.reload();
				});
			}
		});
	};
	
	F.completeRoleCheckbox = function () {
		var $form = $('#userRoleForm');
		
		var ids =  $('#roleIds').val();
		
		utils.setCheckedBoxes($form, '.check-one', ids);
	};
});