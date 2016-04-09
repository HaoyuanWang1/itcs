define(function (require, exports) {
	
	var $ = require('jquery');
	
	require('autocomplete');
	require('autocompletecss');
	
	var utils = require('utils');
	utils.listenCheckbox();
	
	// 用户编辑页面初始化
	F.initUserInfoEditBox = function () {
		var $tenantSelect = $('#tenantSelect');
		$tenantSelect.autocomplete({
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
			target : ['code', 'name'],
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
	
	//保存
	F.saveUser = function($form){
		$.post(webContext + '/user/save',$form.serializeArray()).done(function(data){
			if(data.data === 'error'){
				$.alert('请重新填写，用户名称已存在！');
			}else{
				$.alert('保存成功', 1000)
				.done(function () {
					location.reload();
				});
			}
		});
	};
	
	//角色维护-保存
	F.saveUserRole = function ($form) {
		var ids = utils.getCheckedBoxes($form, '.check-one');
		$('#roleIds').val(ids.join(','));
		$form._save(webContext + '/userRole/saveUserRoleForUser', function (data) {
			if (data.success) {
				$.alert('保存成功', 1500).done(function () {
					location.reload();
				});
			}
		});
	};
	
	//重置密码
	F.pwdReset = function ($obj) {
		var uid = $obj.data('id');
		$.confirm("您确认密码重置吗?").done(function(){
			$.post(webContext + '/user/userInfoPwdResetSave/'+uid, function(){	
				location.replace(webContext + '/user/page');
			});
		});
	};
});