define(function(require, exports) {
	
	var $ = require('jquery');
	require('autocomplete'),
	require('autocompletecss');

	var bigType = $('#bigType'),
	smallType = $('#smallType'),
	applyUser = $('#applyUser'),
	bigTypeText = $('#bigTypeText'),
	smallTypeText = $('#smallTypeText'),
	$from = $("#eventForm");
	//初始服务类型和服务级别联动
	getServiceTypeOptions($('#bigType').val());
	getServiceLevelOptions($('#bigType').val());
	
	// 客户
	bigType.autocomplete({
		source: function (pattern, response) {
			$.getJSON(webContext + '/tenant/findAllTenantsMess', {
				searchKey: pattern,
				size: 100
			}).done(function (data) {
				response(data.data);
			});
		},
		width: 220,
		size: 100,
		placeholder: '请先选择客户',
		format: function( item ) {
			return {label: item.name, value: item.id};
		},
		change: function(item) {
			bigTypeText.val(item.label);
			smallType.autocomplete('unfreeze').clear();
			//服务类型和服务级别联动
			getServiceTypeOptions(item.value);
			getServiceLevelOptions(item.value);
		},
		clear: function () {
			smallType.autocomplete('freeze').clear();
			bigTypeText.val('');
			getServiceTypeOptions("");
			getServiceLevelOptions("");
		}
	});
	
	// 客户账号
	smallType.autocomplete({
		source: function (pattern, response) {
			$.getJSON(webContext + '/user/findAccountMessOfTenants', {
				tenantId:$('#bigType').val(),
				searchKey: pattern,
				size: 100
			}).done(function (data) {
				response(data.data);
			});
		},
		width: 220,
		size: 100,
		minlength: 0,
		placeholder: '请先选择客户账号',
		format: function( item ) {
			return {label: item.uid+"/"+item.userName,value: item.id, self: item};
		},
		change: function (item) {
			smallTypeText.val(item.label);
			
			var submitUser = item.self.userName;
			$('[name="linkMan"]').val(submitUser);
			
			var mobile = item.self.mobile;
			$('[name="tel"]').val(mobile);
			
			var email = item.self.email;
			$('[name="email"]').val(email);
		},
		clear: function () {
			smallTypeText.val('');
			$('[name="linkMan"]').val('');
			$('[name="tel"]').val('');
			$('[name="email"]').val('');
		}
	});
	// 冻结需求小类
	smallType.autocomplete('freeze');
	
	if(bigType.val()){
		// 解冻需求小类
		smallType.autocomplete('unfreeze');
	}
	
	
	//服务类型
	function getServiceTypeOptions(tenantId) {
		var $serviceType = $('[name="serviceType.id"]');
		var opts = ['<option value="">——请选择——</option>'];
		$.post(webContext + '/serviceType/findServiceTypesByTenantId?tenantId=' + tenantId, function( data ) {
			$.each(data.data, function(i, serviceType) {
				if ( tenantId ) {
					opts[opts.length] = '<option value="' + serviceType.id + '">' + serviceType.name + '</option>';
				}
			});
			$serviceType.html(opts.join(''));
		});
	}	
	//服务级别
	function getServiceLevelOptions(tenantId) {
		var $serviceType = $('[name="serviceLevel.id"]'),
			opts = ['<option value="">——请选择——</option>'];
		$.post(webContext + '/serviceLevel/findServiceLevelsByTenantId?tenantId=' + tenantId, function( data ) {
			$.each(data.data, function(i, serviceLevel) {
				if ( tenantId) {
					opts[opts.length] = '<option value="' + serviceLevel.id + '">' + serviceLevel.name + '</option>';
				}
			});
			$serviceType.html(opts.join(''));
		});
	}
	
	F.submitApply = function ($form) {
		$form._save(webContext + '/event/submitApply', function(data){
			location.replace(webContext + '/event/applyPage/'+ data.data);
					});
			};
			
			
			
			
});