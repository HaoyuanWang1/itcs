<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="page" uri="/WEB-INF/tags/pagerTag.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${basePath}" />

<div class="modal-close">
	<span class="close" data-listen="close">&times;</span>
</div>
<span class="pull-right tip-descn">
	<i class="overdue-tip icon-exclamation-sign"></i>超期
	<i class="alert-tip icon-exclamation-sign"></i>预警
</span>
<table class="table table-hover table-bordered table-condensed">
	<thead>
		<tr>
			<th>编号</th>
			<th class="words4">客户</th>
			<th class="words4">客户账号</th>
			<th class="span4">主题</th>
			<th>信息类型</th>
			<th class="words5">当前处理人</th>
			<th>状态</th>
			<th class="words5">最近更新</th>
			<th ></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${newFlowApplyMap}" var="v">
				<c:set var="isWarningFlag" value="${v.value.isWarningFlag}" />
				<c:set var="isOverduFlag" value="${v.value.isOverduFlag}" />
				<c:if test="${isWarningFlag==1}">
					<tr class="alert-tip">
				</c:if>
				<c:if test="${isOverduFlag==1}">	
					<tr class="overdue-tip">
				</c:if>
				<td><a href="${ctx}${v.key.applyUrl}" target="_blank">${v.key.applyNum }</a></td>
				<td><div class="words4 over-dotted" title="${v.key.createUser.tenant.name }">${v.key.createUser.tenant.name }</div></td>
				<td><div class="words4 over-dotted" title="${v.key.createUser.userName}">${v.key.createUser.userName}</div></td>					
				<td><div class="span4 over-dotted" title=""${v.key.applyName}>${v.key.applyName}</div></td>
				<td>
				<c:if test="${v.value.mainType eq '0' }">故障</c:if>
			 	   <c:if test="${v.value.mainType eq '1' }">咨询</c:if>
			 	   <c:if test="${v.value.mainType eq '2' }">建议</c:if>
			 	   <c:if test="${v.value.mainType eq '3' }">需求</c:if>
			 	   <c:if test="${v.value.mainType eq '4' }">投诉</c:if>
			 	   </td>
				<td><div class="words5 over-dotted" title="${v.value.singerIdsText }"><c:if test="${!empty v.value.singerIds && v.value.singerIds != '' }">${v.value.singerIdsText}</c:if></div></td>  
				<td>
					<c:if test="${v.value.mainState != null }">
						${v.value.mainStateText }
					</c:if>
				</td>
				<td>${v.value.recentUser }${v.value.recentAction}</td>
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
<page:outpage page="${page}" params="${searchParam}"  url="/event/panel/showEventList/${managerID }" loadTarget="#eventResuleTable" />
