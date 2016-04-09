define(function (require, exports) {
	var $ = require('jquery');
	var utils = require('utils');
	require('autocomplete');
	require('autocompletecss');
	utils.listenCheckbox();
	/**
	 * 允许登录
	 */
	F.permission = function($link){
		var url = $link.data('url');
		$.post(url).done(function(data){
			$.alert("修改登录权限成功！", 1000).done(function(){
				location.reload();
			});
		});
	};
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
	
	// 初始化弹出框
	F.initUserInfoEditBox = function (modal) {
		$('#fromTenantId').autocomplete({
			source : function(pattern, response) {
				$.getJSON(webContext + '/tenant/organizationSelect', {
					searchKey : pattern,
					size : 10
				}).done(function(data) {
					response(data.data);
				});
			},
			placeholder : '请选择客户',
			width : 220,
			minlength : 0,
			target : [ 'code', 'name'],
			format : function(item) {
				return {
					label : item.code + '/' + item.name,
					code : item.code,
					name : item.name,
					value : item.id
				};
			}
		});
	};	
	
	F.saveUserInfoForm = function ($form) {
		$form._save(webContext + '/user/save', function (data) {
			if (data.success) {
				$.alert('提交成功', 1000).done(function () {
					location.reload();
				});
			}
		});
	};
	
	F.saveUserInfoForm = function ($form) {
		$form._save(webContext + '/user/save', function(){	
			location.replace(webContext + '/user/page');
		});
	};
	
	F.pwdResetSuccess = function ($form) {
		$form._save(webContext + '/user/userInfoPwdResetSave', function(){	
			location.replace(webContext + '/user/page');
		});
	};
	
});