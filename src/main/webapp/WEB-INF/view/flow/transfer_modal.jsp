<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	<h3>转办</h3>
</div>
<div class="modal-body">
	<form method="post" id="adminFlowTransferForm" data-validate="{debug: true}" class="form-horizontal">
		<div class="control-group">
			<label class="control-label" for="sourceUser">原操作人：</label>
			<div class="controls">
				<c:choose>
					<c:when test="${fn:length(actors)==1}">
						<c:forEach items="${actors}" var="user">
							<strong>${user.userText}</strong>
							<input type="hidden" value="${user.id}" name="sourceUser" />
						</c:forEach>
					</c:when>
					<c:otherwise>
						<select name="sourceUser">
							<c:forEach items="${actors}" var="user">
								<option value="${user.id}">${user.userText}</option>
							</c:forEach>
						</select>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="transferUser">请指定转办人：</label>
			<div class="controls">
				<input type="hidden" id="transferUser" name="transferUser"  data-user-config="{userType:'2'}" data-ruler="{must: 1}"/>
			</div>
		</div>
		<input type="hidden" name="taskId" value="${taskId}" />
	</form>
</div>
<div class="modal-footer">
	<input class="btn btn-primary" type="button" value="确定" data-listen="admin_transfer_flow" data-target="#adminFlowTransferForm"/>
	<input class="btn" type="button" data-dismiss="modal" aria-hidden="true" value="取消" />
</div>
