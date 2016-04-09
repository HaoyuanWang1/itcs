define(function (require, exports) {
	
	var $ = require('jquery');
	
	var $defineXml = $('#defineXml');
	
	F.saveFlowDefine = function ($form) {
		if ($.browser.msie) {
			$defineXml.val(document.getElementById('FlexFlowIE').getFlowXML());
		} else {
			$defineXml.val(document.getElementById('FlexFlowFF').getFlowXML());
		}
		
		$form._save(webContext + '/flow/pd/save', function (data) {
			location.replace(webContext + '/flow/pd/edit/' + data.data.id);
		});
	}
	
	window.setFlowXML = function () {
		var xml = document.getElementById('defineXml').value;
		var flex = $.browser.msie ? document.getElementById('FlexFlowIE') : document.getElementById('FlexFlowFF');
		flex.setFlowXML(xml);
	}
});