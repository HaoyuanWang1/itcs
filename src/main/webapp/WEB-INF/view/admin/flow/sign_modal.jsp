<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	<h3>审批</h3>
</div>
<div class="modal-body">
	<form method="post" id="adminFlowSignForm">
		<label for="comment">审批意见：</label>
		<textarea name="comment" rows="5" class="span6" maxlength="2000"></textarea>
		<input type="hidden" name="taskId" value="${taskId}" />
	</form>
</div>
<div class="modal-footer">
	<input class="btn btn-primary" type="button" value="确定" data-listen="admin_sign_flow" data-target="#adminFlowSignForm"/>
	<input class="btn" type="button" data-dismiss="modal" aria-hidden="true" value="取消" />
</div>
