<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="page" uri="/WEB-INF/tags/pagerTag.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${basePath}" />
<html>
    <head>
        <title>查询已提交的问题</title>
    </head>
    <body>
    	<div class="container">
			<!--  个人代码开始 -->
			<ul class="breadcrumb">
				<li><a href="${ctx}/">首页</a> <span class="divider">/</span></li>
				<li class="active">已提交的问题</li>
			</ul>
            <!-- 已提交问题查询-->
			<form class="form-horizontal" id="searchForm">
				<div class="control-groups">
					<div class="control-group">
						<label class="control-label">主题：</label>
						<div class="controls">
							<input type="text" name="search_LIKE_topic" value="${searchParam.search_LIKE_topic}">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">提交日期：</label>
							<div class="controls time-search">
							<span>从</span>&nbsp;<input type="text" class="short-text" name="search_GTE_submitTime" value="${searchParam.search_GTE_submitTime}" data-calendar="{pair:'search_LTE_submitTime',scope:'year,1'}"> 
							<span>到</span>&nbsp;<input type="text" class="short-text" name="search_LTE_submitTime" value="${searchParam.search_LTE_submitTime}">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">状态：</label>
						<div class="controls">
							<select NAME="search_EQ_mainState">
								<option value="" >——请选择——</option>
								<option value="0" <c:if test="${searchParam.search_EQ_mainState eq 0 }">selected="selected"</c:if>>受理中</option>
								<option value="1" <c:if test="${searchParam.search_EQ_mainState eq 1 }">selected="selected"</c:if>>待客户确认</option>
								<option value="2" <c:if test="${searchParam.search_EQ_mainState eq 2 }">selected="selected"</c:if>>已关闭</option>
							</select>
						</div>
					</div>
					<div class="control-group">
						<div class="controls">
							<button type="submit" class="btn">查询</button>
				   			<button type="reset" class="btn" data-reset="#searchForm">重置</button>
						</div> 
					</div>
				</div>
			</form>
           
            <!--查询问题列表-->
            <div class="all-question">
            <c:choose>
            	<c:when test="${!empty page.content }">
			            <table class="table table-hover table-bordered table-condensed">
			                <thead>
			                  <tr>
			                    <th>编号</th>
			                    <th class="span5">主题</th>
			                    <th class="span2">提交人</th>
			                    <th>提交时间</th>
			                    <th>最近更新</th>
			                    <th>状态</th>
			                  </tr>
			                </thead>
			                <tbody>
							<c:forEach items="${page.content }" var="v">
			               		<tr>
									<td><a href="${ctx}${v.applyUrl}" target="_blank">${v.code}</a></td>
									<td><div class="span5 over-dotted" title="${v.topic }">${v.topic}</div></td>
									<td>${v.createUser.userName}</td>					
									<td>${v.submitTime}</td>
									<td>${v.recentUser }${v.recentAction}</td>
									<td> 
										<c:if test="${v.mainState!=null }"> 
											${v.mainStateText}
										</c:if> 
									</td>
								</tr>
							</c:forEach>
			                </tbody>
			              </table>
			            
					<page:outpage page="${page}" params="${searchParam}" url="/event/page" />
            	</c:when>
            	<c:otherwise>
					<div class="alert">暂无记录。</div>
				</c:otherwise>
            </c:choose>
            </div>
		 </div>
    </body>
</html>

