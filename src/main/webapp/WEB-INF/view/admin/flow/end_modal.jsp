<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	<h3>撤单</h3>
</div>
<div class="modal-body">
	<form method="post" id="adminFlowEndForm">
		<label for="comment">撤单原因：</label>
		<textarea name="comment" rows="5" class="span6" maxlength="2000"></textarea>
		<input type="hidden" name="taskId" value="${taskId}"/>
	</form>
</div>
<div class="modal-footer">
	<input class="btn btn-primary" type="button" value="确定" data-listen="admin_end_flow" data-target="#adminFlowEndForm"/>
	<input class="btn" type="button" data-dismiss="modal" aria-hidden="true" value="取消" />
</div>
