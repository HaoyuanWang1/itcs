<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="page" uri="/WEB-INF/tags/pagerTag.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${basePath}" />

<c:choose>
	<c:when test="${!empty eventLogEntrySet}">
		<c:forEach items="${eventLogEntrySet  }" var="newMap">
			<div class="span012">
				<p>
					<i class="icon-user"></i>
						<span class="name">
						
							<c:forEach items="${newMap.value}" var="role">
								<c:if test="${role.role.rid eq 'customerManagerUser' }">
								    <c:if test="${newMap.key.action eq '转至服务经理'}">
								    	${role.role.name }
								    </c:if>
								</c:if>
								<c:if test="${role.role.rid eq 'serviceManagerUser'}">
									<c:if test="${newMap.key.action eq '已解决' || newMap.key.action eq '回复' }">
								    	${role.role.name }
								    </c:if>
								</c:if>
								<c:if test="${role.role.rid eq 'commonUser'}">
									<c:if test="${newMap.key.action eq '回复' || newMap.key.action eq '驳回' || newMap.key.action eq '通过' }">
								    	${role.role.name }
								    </c:if>
								</c:if>
							</c:forEach>
						
						:&nbsp;&nbsp;${newMap.key.createUser.userName }</span>
					<i class="icon-time"></i>
					<span>${newMap.key.createTime }</span>
					<span class="state">${newMap.key.action }</span>
				</p>
				<p>
					<i class="icon-pencil">&nbsp;</i>
					<span>${newMap.key.context }</span>
				</p>
				<c:if test="${newMap.key.attachment !=null && newMap.key.attachment !=''}">
					<div>
						<label class="pull-left">附件：</label>
						<div data-attachment-list="${newMap.key.attachment}"></div>
					</div>
				</c:if>
			</div>
		</c:forEach>	
	</c:when>
	<c:otherwise>
		<div class="alert">无记录。</div>
	</c:otherwise>
</c:choose>


