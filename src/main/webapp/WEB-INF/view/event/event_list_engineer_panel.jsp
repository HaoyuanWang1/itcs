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
	<c:when test="${!empty page.content }">
		<span class="tip-descn">
			<i class="overdue-tip icon-exclamation-sign"></i>超期
			<i class="alert-tip icon-exclamation-sign"></i>预警
		</span>
		<div class="table-question">
			<table class="table table-hover table-bordered table-condensed">
				<thead>
					<tr>
						<th class="words4">编号</th>
						<th class="words2">客户</th>
						<th class="words2">客户账号</th>
						<th class="words6">主题</th>
						<th class="words4-1">提交时间</th>
						<th class="words3">当前处理人</th>
						<th class="words4-1">解决时间</th>
						<th class="words3">最近更新</th>
						<th class="words2">状态</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.content }" var="event">
						<c:set var="isWarningFlag" value="${event.isWarningFlag}" />
						<c:set var="isOverduFlag" value="${event.isOverduFlag}" />
						<c:choose>
							<c:when test="${isWarningFlag==1}">
								<tr class="alert-tip">
							</c:when>
							<c:when test="${isOverduFlag==1}">
								<tr class="overdue-tip">
							</c:when>
							<c:otherwise>
								<tr>
							</c:otherwise>
						</c:choose>
							<td><a href="${ctx}${event.applyUrl}" target="_blank">${event.code}</a></td>
							<td><div class="words2 over-dotted" title="${event.tenant.name }">${event.tenant.name}</div></td>
							<td><div class="words3 over-dotted" title="${event.submitUser.uid }">${event.submitUser.uid}</div></td>
							<td><div class="words6 over-dotted" title="${event.topic }">${event.topic}</div></td>
							<td>${event.submitTime}</td>
							<td><div class="words4 over-dotted" title="${event.singerIdsText }"><c:if test="${!empty event.singerIds && event.singerIds != '' }">${event.singerIdsText}</c:if></div></td>  
							<td>${event.solveTime}</td>
							<td><div class="words3 over-dotted" title="${event.recentUser }${event.recentAction}">${event.recentUser }${event.recentAction}</div></td>
							<td>
								<c:if test="${event.mainState eq 0}">受理中</c:if> 
								<c:if test="${event.mainState eq 1}">待客户确认</c:if> 
								<c:if test="${event.mainState eq 2}">已关闭</c:if> 
							</td> 
							<td>
							<c:if test="${isWarningFlag==1}">
								<i class="alert-tip icon-exclamation-sign"></i>
							</c:if>
							<c:if test="${isOverduFlag==1}">	
								<i class="overdue-tip icon-exclamation-sign"></i>
							</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="row-fluid event_list_engineer">
				<page:outpage page="${page}" params="${searchParam}" url="/event/panel/engineerPage" loadTarget="#resultTableQuestion"/>
			</div>
		</div>         	
	</c:when>
	<c:otherwise>
	<div class="alert">暂无记录。</div>
	</c:otherwise>
</c:choose>

