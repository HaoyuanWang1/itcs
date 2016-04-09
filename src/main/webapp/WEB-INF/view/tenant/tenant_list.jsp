<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="page" uri="/WEB-INF/tags/pagerTag.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${basePath}" />
<html>
    <head>
        <title>客户管理</title>
    </head>
    <body>
   		<ul class="breadcrumb">
   			<li><a href="${ctx}/">首页</a> <span class="divider">/</span></li>
   			<li class="active">组织管理</li>
   		</ul>
		<form class="form-inline search-form" id="searchForm" method="post" action="${ctx}/tenant/tenantPage">
			<label for="textName">组织名称：</label>
			<input id="textName" type="text" name="search_LIKE_name" value="${searchParam.search_LIKE_name}" />
			
			<button type="submit" class="btn"><i class="icon-search"></i> 查询</button>
			<button type="reset" class="btn" data-reset="#searchForm"><i class="icon-refresh"></i> 重置</button>
			<a  href="${ctx}/tenant/tenantEdit/0" class="btn btn-primary"> 新建组织</a>
		</form>
		<c:choose>
			<c:when test="${fn:length(page.content)>0}">
				<table class="table table-condensed table-bordered table-hover">
					<thead>
						<tr>
							<th>组织名称</th>
							<th>组织编号</th> 
							<th class ="span3">类型</th>
							<th class="span1">状态</th> 
							<th class="span1">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.content}" var="bean">
							<tr>
								<td>${bean.name}</td>
								<td>${bean.code}</td>
								<td><c:if test= "${bean.type =='C'}">客户 </c:if>
									<c:if test = "${bean.type =='S'}">服务商 </c:if> 
								</td>
								<td>${bean.stateText}</td>
								<td>
									<a class="btn-link" href="${ctx}/tenant/tenantEdit/${bean.id }">编辑</a> 
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<page:outpage page="${page}" params="${searchParam}" url="/tenant/tenantPage" />
			</c:when>
			<c:otherwise>
				<div class="alert">暂无记录</div>
			</c:otherwise>
		</c:choose>
    </body>
</html>

