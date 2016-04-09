<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="page" uri="/WEB-INF/tags/pagerTag.tld"%>
<%@ taglib prefix="str" uri="/WEB-INF/tags/stringTag.tld"%>
<c:set var="ctx" value="${basePath}" />
<c:choose>
	<c:when test="${!empty page.content}">
		<table class="table table-bordered table-condensed table-hover">
				<thead>
					<tr>
						<th>编号</th>
						<th class="span4">主题</th>
						<th>提交人</th>
						<th>提交时间</th>
						<th>状态</th>
						<th>最近更新</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.content}" var="v">
						<tr>
					 		<td><a href="${ctx}${v.applyUrl}" target="_blank">${v.code}</a></td>
						 	<td><div class="span4 over-dotted" title="${v.topic }">${v.topic }</div></td> 
							<td class="span2">${v.createUser.userName}</td> 
							<td>${v.createTime}</td> 
						 	<td>${v.mainStateText}</td>
						 	<td>${v.recentUser}${v.recentAction}</td>
							<td><a href="${ctx}${v.applyUrl}" target="_blank">查看详细</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		<page:outpage page="${page}" params="${searchParam}" url="/event/panel/UserPage" loadTarget="#taskSignerUserPage"/>	
	</c:when>
	<c:otherwise>
		<div class="alert">暂无记录。</div>
	</c:otherwise>
</c:choose>
	
