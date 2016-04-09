define(function (require, exports) {
	var $ = require('jquery');
	require('autocomplete');
	require('autocompletecss');
	
	var $tenantManager = $('#tenantManager');	
	var $customerManagerModal = $('#customerManagerModal');
	var $serviceTypeModal = $('#serviceTypeModal');
	var $serviceLevelModal = $('#serviceLevelModal');
	var $tenantManagerList = $('#tenantManagerList');
	var tenantManagerUrl = $tenantManagerList.data('url');
	var tenantID = $('#tenantID').val();
	
	if (tenantID) {
		initWegdt();
	}
	
	$tenantManagerList._load(tenantManagerUrl);
	
	function initWegdt () {
		// 客户经理模糊查询
		$tenantManager.autocomplete({
			source : function(pattern, response) {
				$.getJSON(webContext + '/user/findManagerOfCustomerAndService', {
					searchKey : pattern,
					size : 100
				}).done(function(data) {
					response(data.data);
				});
			},
			placeholder : '选择客户经理并添加',
			width : 220,
			minlength : 0,
			target : [ 'uid', 'name' ],
			format : function(item) {
				return {
					label : item.userText,
					uid : item.uid,
					name : item.userName,
					value : item.id
				};
			},
			change: function (item) {
				saveTenantManager(item.value);
				$tenantManager.autocomplete('clear');
			}
		});
	}
	
	function saveTenantManager(userId) {
		$.post(webContext + '/tenantManager/save', {
			'tenant.id': tenantID,
			'tenantManager.id': userId
		}).done(function (data) {
			$tenantManagerList._load(tenantManagerUrl);
		});
	}
	
	/**
	 * 保存客户基本信息
	 */
	F.saveTenantForm = function ($form) {
		$.post(webContext + '/tenant/tenantSave/', $form.serializeArray()).done(function(data){
			if (data.data === 'exist') {
				$.alert('请重新填写，客户名称已存在!');
			}else{
				location.replace(webContext + '/tenant/tenantEdit/'+data.data);
			}
		})
		
	};
	
	F.removeCurrentTr = function () {
		$('[data-listen="removeCurrentTr"]').each(function(i){
			var $this = $(this);
			$this.click(function(){
				$.post(webContext + '/tenantManager/remove/'+$this.data('id')).done(function(data){
					if (data.data === 'error') {
						$.alert('不可删除，启用的客户需保留至少一个客户经理！');
					}else{
						$this.parent().parent().remove();
					}
				});
			});
		});
	};
	//服务类型对应的多位服务经理
	var sm = $('.sm');
	$('.sm').each(function () {
		var $this = $(this);
		loadServiceManager($this, $this.data('url'));
	});
	
	var usersList = {};
	
	function loadServiceManager($wrap, url) {
		
		$.getJSON(url).done(function (data) {
			var users = data.users;
			var serviceType = data.serviceType;
			usersList[serviceType.id] = users;
			var html =[];
			for(var i = 0; i < users.length; i++){
				html.push('<div class="tags-label">' + users[i].uid+'/' + users[i].userName + '</div>');
			};
			$wrap.html(html.join(''));
		});
	}
	
	//服务经理模糊查询
	F.serviceTypeEditBox = function () {
		var $serviceManager = $('#serviceManagerAutoId');
		var serviceTypeId = $('#serviceTypeId').val();
		var $ids = $('#ids');
		$serviceManager.autocomplete({
			multiple: true,
			source : function(pattern, response) {
				$.getJSON(webContext + '/user/findManagerOfCustomerAndService', {
					searchKey : pattern,
					size : 10
				}).done(function(data) {
					response(data.data);
				});
			},
			placeholder : '选择服务经理',
			width : 520,
			minlength : 0,
			target : [ 'uid', 'name' ],
			format : function(item) {
				return {
					label : item.userText,
					uid : item.uid,
					name : item.userName,
					value : item.id
				};
			},
			change: function (list) {
				var ids = [];
				for (var i = 0; i < list.length; i++) {
					ids.push(list[i].value);
				}
				$ids.val(ids.join(','));
			},
			clear: function () {
				$ids.val('');
			}
		});
		
		var list = [];
		var users = usersList[serviceTypeId];
		var item = null;
		if (users && users.length) {
			
			for (var i = 0; i < users.length; i++) {
				item = users[i];
				list.push({
					label : item.userText,
					uid : item.uid,
					name : item.userName,
					value : item.id
				});
			}
			
			$serviceManager.autocomplete().set(list);
		}
		
	};
	
	//服务类型保存
	F.saveServiceType =  function($form){
		var tenanId = $('#tenantID').val();
		$.post(webContext + '/serviceType/serviceTypeSave/'+tenanId, $form.serializeArray()).done(function(data){
			if (data.data === 'serviceTypeIsNULL') {
				$.alert('不可删除，启用的客户需保留至少一个有效服务类型！');
			}else if(data.data ==='idsIsNUll'){
				$.alert('服务经理不能为空 ！');
			}else{
				location.replace(webContext + '/tenant/tenantEdit/'+data.data);
			}
		})
	};
	
	//启用
	F.start = function () {
		var tenanId = $('#tenantID').val();
		$.post(webContext + '/tenant/startTenant/'+tenanId+'/'+1).done(function (data) {
			if(data.data === 'error'){
				$.alert('当启用的时,请确保服务类型,服务级别,客户经理均不为空 !');
			}else{
				$.alert('启用成功', 1000).done(function () {
					location.reload();
				});
			}
		});
	};
	//停用
	F.onStart = function () {
		var tenanId = $('#tenantID').val();
		$.post(webContext + '/tenant/startTenant/'+tenanId+'/'+0).done(function (data) {
			$.alert('停用成功', 1000).done(function () {
				location.reload();
			});
		});
	};
	
	//服务界别（sla）保存
	F.serviceLevelSave = function($form){
		var tenanId = $('#tenantID').val();
		$.post(webContext + '/serviceLevel/serviceLevelSave/'+tenanId, $form.serializeArray()).done(function(data){
			if (data.data === 'error') {
				$.alert('不可删除，启用的客户需保留至少一个有效服务级别！');
			}else{
				location.replace(webContext + '/tenant/tenantEdit/'+data.data);
			}
		})
	};
});











