define(function(require, exports) {
	var $ = require('jquery');
	
	var userInfoId = $("#userInfoId").val();
	// 保存草稿
	F.saveUpdatePwdPageForm = function ($form) {
		$.post(webContext + '/user/updatePwdPageSave/'+userInfoId,$form.serializeArray()).done(function(data){
			if(data.data === 'error'){
				$.alert('旧密码错误！');
			}else if(data.data === 'different'){
				$.alert("两次密码输入不一致");
			}else{
				$.alert('修改成功', 1000)
				.done(function () {
					location.replace(webContext);
				});
			}
		});
	};
});