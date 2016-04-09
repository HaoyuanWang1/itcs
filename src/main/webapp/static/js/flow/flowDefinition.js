function setFlowXML() {
	if ($.browser.msie) {
		$("#FlexFlowIE").get(0).setFlowXML($("#defineXml").val());
	} else {
		$("#FlexFlowFF").get(0).setFlowXML($("#defineXml").val());
	}
}
function saveDefine() {
	if ($.browser.msie) {
		$("#defineXml").val($("#FlexFlowIE").get(0).getFlowXML());
	} else {
		$("#defineXml").val($("#FlexFlowFF").get(0).getFlowXML());
	}
	var flowName = $("#flowName").val();
	var description = $("#description").val();
	var signURL = $("#signURL").val();
	var handleBean = $("#handleBean").val();
	var enable = $("#enable").val();
	var defineXML = $("#defineXml").val();
	if (flowName == "" || description == "" || signURL == "" || enable == ""
			|| defineXML == "" || handleBean == "") {
		alert("*为必填，请填写完全");
		return false;
	}
	$.post(webContext+"/admin/pd/save",
			$('#flowDefineForm').serialize(), function(data) {
		if(data.success){
			window.location.href=webContext+"/admin/pd/edit/"+data.data.id;
		}else{
			alert(data.error);
		}
	});
}
function cancel() {
	window.close();
}