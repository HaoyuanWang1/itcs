<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="page" uri="/WEB-INF/tags/pagerTag.tld"%>
<%@ taglib prefix="str" uri="/WEB-INF/tags/stringTag.tld"%>
<c:set var="ctx" value="${basePath}" />


<div class="search-result">
<c:choose>
	<c:when test="${!empty newFlowApplyMap}">
	<span class="pull-right tip-descn">
		<i class="overdue-tip icon-exclamation-sign"></i>超期
		<i class="alert-tip icon-exclamation-sign"></i>预警
	</span>
		<table class="table table-bordered table-condensed table-hover">
			<thead>
				<tr>
					<th>编号</th>
					<th class="words4">客户</th>
					<th class="words4">客户账号</th>
					<th class="words7">主题</th>
					<th class="words2">信息类型</th>
					<th class="words2">紧急程度</th>
					<th class="words4-1">提交时间</th>
					<th class="words3">最近更新</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${newFlowApplyMap}" var="flowApply">
					<c:set var="isWarningFlag" value="${flowApply.value.isWarningFlag}" />
					<c:set var="isOverduFlag" value="${flowApply.value.isOverduFlag}" />
					<c:if test="${isWarningFlag==1}">
						<tr class="alert-tip">
					</c:if>
					<c:if test="${isOverduFlag==1}">	
						<tr class="overdue-tip">
					</c:if>
					
			 		<td><a href="${ctx}${flowApply.key.applyUrl}" target="_blank">${flowApply.key.applyNum}</a></td>
				 	<td><div class="words4 over-dotted" title="${flowApply.key.createUser.tenant.name }">${flowApply.key.createUser.tenant.name }</td> 
					<td><div class="words4 over-dotted" title="${flowApply.key.createUser.userName}">${flowApply.key.createUser.userName}</td> 
					<td><div class="words7 over-dotted" title="${flowApply.key.applyName}">${flowApply.key.applyName}</div></td> 
				 	<td>
				 	   <c:if test="${flowApply.value.mainType eq '0' }">故障</c:if>
				 	   <c:if test="${flowApply.value.mainType eq '1' }">咨询</c:if>
				 	   <c:if test="${flowApply.value.mainType eq '2' }">建议</c:if>
				 	   <c:if test="${flowApply.value.mainType eq '3' }">需求</c:if>
				 	   <c:if test="${flowApply.value.mainType eq '4' }">投诉</c:if>
				 	</td>
				 	<td>${flowApply.value.serviceLevel.name}</td>
					<td>${flowApply.key.createTime}</td>
					<td><div class="words3 over-dotted" title="${event.recentUser }${event.recentAction}">${flowApply.value.recentUser}${flowApply.value.recentAction}</div></td>
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
	</div>
	<page:outpage page="${page}" params="${searchParam}" url="/event/panel/signerPage" loadTarget="#taskSignerPage" />
	</c:when>
	<c:otherwise>
		<div class="alert">暂无记录。</div>
	</c:otherwise>
</c:choose>

	
