<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- 流程审批对话框 -->
<div class="flow-modal-group">
	<%-- 流程审批对话框 --%>
	<c:if test="${flowAuth.isCanSign()}">
		<div class="modal-audit modal fade hide" id="flowSignDialog">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h3>审批</h3>
			</div>
			<div class="modal-body">
				<form method="post" id="flowSignForm">
					<label for="signComment">审批意见：</label>
					<textarea name="comment" id="signComment" rows="5" maxlength="2000"></textarea>
					<p class="help help-block muted"><b>必填</b>，最多可输入<b>2000</b>字</p>
					<input type="hidden" name="taskId" value="${flowAuth.taskId}" />
				</form>
			</div>
			<div class="modal-footer">
				<input class="btn btn-primary" type="button" value="确定" />
				<input class="btn" type="button" data-dismiss="modal" aria-hidden="true" value="取消" />
			</div>
		</div>
	</c:if>
	
	<%-- 流程驳回对话框 --%>
	<c:if test="${flowAuth.isCanBack()}">
		<div class="modal-audit modal fade hide" id="flowBackDialog">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h3>驳回</h3>
			</div>
			<div class="modal-body">
				<form method="post" id="flowBackForm">
					<c:if test="${fn:length(flowAuth.backNodes)==1}">
						<h4>即将驳回到【${flowAuth.backNodes.get(0).getDesc() }】环节</h4>
						<input type="hidden" name="backNode" value="${flowAuth.backNodes.get(0).getName() }" />						
					</c:if>
					<c:if test="${fn:length(flowAuth.backNodes)>1}">
						<label for="backNode">请驳回到指定环节：</label>
						<select name="backNode">
							<c:forEach items="${flowAuth.backNodes }" var="node">
								<option value="${node.name }">${node.desc }</option>								
							</c:forEach>
						</select>
					</c:if>
					<label for="backComment">驳回原因：</label>
					<textarea name="comment" id="backComment" rows="5" maxlength="2000"></textarea>
					<p class="help help-block muted"><b>必填</b>，最多可输入<b>2000</b>字</p>
					<input type="hidden" name="taskId" value="${flowAuth.taskId}" />
				</form>
			</div>
			<div class="modal-footer">
				<input class="btn btn-primary" type="button" value="确定" />
				<input class="btn" type="button" data-dismiss="modal" aria-hidden="true" value="取消" />
			</div>
		</div>
	</c:if>
</div>