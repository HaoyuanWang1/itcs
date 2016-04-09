<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${basePath}" />
<c:choose>
	<c:when test="${fn:length(tenantManagerList)>0}">
		<table class="table table-condensed table-hover table-striped">
			<thead>
				<tr>
					<th>客户经理名称</th>
					<th>客户经理ITcode</th>
					<th class="span1">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${tenantManagerList}" var="m">
					<tr>
						<td>${m.tenantManager.userName}</td>
						<td>${m.tenantManager.uid}</td>
						<td>
							<a href="javascript:;" data-id="${m.id }" data-listen="removeCurrentTr">删除</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:when>
	<c:otherwise>
		<div class="alert">还没有客户经理，请先添加。</div>
	</c:otherwise>
</c:choose>
