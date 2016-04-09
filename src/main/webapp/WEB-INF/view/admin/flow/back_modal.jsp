<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	<h3>驳回</h3>
</div>
<div class="modal-body">
	<form method="post" id="adminFlowBackForm" class="form-inline">
		<label for="backNode">驳回节点：</label>
		<c:choose>
			<c:when test="${fn:length(backNodes)==1}">
				<c:forEach items="${backNodes}" var="node">
					<strong>${node.desc}</strong>
					<input type="hidden" value="${node.name}" name="backNode" />
				</c:forEach>
			</c:when>
			<c:otherwise>
				<select name="backNode">
					<c:forEach items="${backNodes}" var="node">
						<option value="${node.name}">${node.desc}</option>
					</c:forEach>
				</select>
			</c:otherwise>
		</c:choose>
		<p></p>
		<label for="comment">驳回原因：</label>
		<textarea name="comment" rows="5" class="span6" maxlength="2000"></textarea>
		<input type="hidden" name="taskId" value="${taskId}"/>
	</form>
</div>
<div class="modal-footer">
	<input class="btn btn-primary" type="button" value="确定" data-listen="admin_back_flow" data-target="#adminFlowBackForm"/>
	<input class="btn" type="button" data-dismiss="modal" aria-hidden="true" value="取消" />
</div>
