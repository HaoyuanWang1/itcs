define(function (require, exports) {
	var $ = require('jquery');
	var utils = require('utils');
	
	var $pgEditModal = $('#pgEditModal'),
		progressForm = $('#progressForm');
	
	F.openProgressModal = function () {
		$pgEditModal.modal('show');
		setProgressForm(progressForm, utils.json2obj($(this).data('pg')));
	};
	
	F.saveProgress = function ($form) {
		var nodeNames = [], nodeDescs = [];
		$form.find('.node-name').each(function () {
			if (this.checked) {
				nodeNames.push(this.value);
				nodeDescs.push($(this).data('desc'));
			}
		});
		$form.find('[name="nodeNames"]').val(nodeNames.join(','));
		$form.find('[name="nodeDescs"]').val(nodeDescs.join(','));
		
		$form._save(webContext + '/flow/processProgress/save');
	};
	
	function setProgressForm(progressForm, param) {
		progressForm.find('[name="name"]').val(param.name);
		progressForm.find('[name="sortNum"]').val(param.sortNum);
		progressForm.find('[name="nodeNames"]').val(param.nodeNames);
		progressForm.find('[name="nodeDescs"]').val(param.nodeDescs);
		progressForm.find('[name="id"]').val(param.id);
		
		progressForm.find('.node-name').each(function () {
			this.checked = false;
		});
		
		if (param.nodeNames) {
			$.each(param.nodeNames.split(','), function (i, nodeName) {
				progressForm.find('.node-name[value="'+nodeName+'"]')[0].checked = true;
			})
		}
	}
	
});