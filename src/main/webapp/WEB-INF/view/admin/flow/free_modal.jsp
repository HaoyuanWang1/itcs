<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	<h3>自由跳转</h3>
</div>
<div class="modal-body">
	<form method="post" id="adminFlowFreeForm" class="form-inline">
		<label for="targetNode">指定环节：</label>
		<select name="targetNode">
			<c:forEach items="${jumpNodes}" var="node">
				<option value="${node.name}">${node.desc}</option>
			</c:forEach>
		</select>
		<p></p>
		<label for="comment">意见：</label>
		<textarea name="comment" rows="5" class="span6" maxlength="2000"></textarea>
		<input type="hidden" name="taskId" value="${taskId}"/>
	</form>
</div>
<div class="modal-footer">
	<input class="btn btn-primary" type="button" value="确定" data-listen="admin_free_flow" data-target="#adminFlowFreeForm"/>
	<input class="btn" type="button" data-dismiss="modal" aria-hidden="true" value="取消" />
</div>
