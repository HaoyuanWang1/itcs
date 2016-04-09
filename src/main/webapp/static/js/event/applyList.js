define(function (require, exports) {
	var $ = require('jquery');
	require('autocomplete'),
	require('autocompletecss');

	var bigType = $('#bigType'),
		smallType = $('#smallType'),
		applyUser = $('#applyUser'),
		bigTypeText = $('#bigTypeText'),
		smallTypeText = $('#smallTypeText'),
		$searchForm = $("#searchForm"),
		singerIds = $("#singerIds"),
		singerIdsText = $("#singerIdsText"),
		resultTableQuestion = $('#resultTableQuestion');
	
	// 当前处理人
	singerIds.autocomplete({
		source: function (pattern, response) {
			$.getJSON(webContext + '/user/findManagerOfCustomerAndService', {
				searchKey: pattern,
				size: 10
			}).done(function (data) {
				response(data.data);
			});
		},
		width: 220,
		minlength : 0,
		target : [ 'uid', 'name' ],
		placeholder: '请先选择当前处理人',
		format: function( item ) {
			return {
				label : item.uid+"/"+item.userName,
				uid : item.uid,
				name : item.userName,
				value : item.id
			};
		},
		change: function (item) {
			singerIdsText.val(item.label);
		},
		clear: function () {
			singerIdsText.val('');
		}
	});
	
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
		},
		clear: function () {
			smallType.autocomplete('freeze').clear();
			bigTypeText.val('');
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
		},
		clear: function () {
			smallTypeText.val('');
		}
	});
	// 冻结需求小类
	smallType.autocomplete('freeze');
	
	if(bigType.val()){
		// 解冻需求小类
		smallType.autocomplete('unfreeze');
	}
	
	engineerPageListLoad();
	
	F.searchBtn= function(){
		engineerPageListLoad();
	}
	
	function engineerPageListLoad(){
		resultTableQuestion._load(webContext + '/event/panel/engineerPage', $searchForm.serializeArray());
	}
});
