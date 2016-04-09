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
        <title>Login</title>
    </head>
    <body>
 	<div class="container">
		<!--  个人代码开始 -->
		<ul class="breadcrumb">
			<li><a href="${ctx}/">首页</a><span class="divider">/</span></li>
			<li class="active">问题跟踪</li>
		</ul>
        <div class="feedback">
           <form class="form-horizontal" id="searchForm" action="${ctx}/event/panel/engineerPage" method="post">
             <div class="control-groups">
               <div class="control-group">
                 <label class="control-label">客户：</label>
                 <div class="controls">
                   <input type="hidden" name="search_EQ_tenant_id" id="bigType" value="${searchParam.search_EQ_tenant_id}" data-autocomplete="${searchParam.tenantNameText}">
				   <input type="hidden" name="tenantNameText" id="bigTypeText" value="${searchParam.tenantNameText}">
                 </div>
               </div>
               <div class="control-group">
                 <label class="control-label">客户账号：</label>
                 <div class="controls">
                   <input type="hidden" name="search_EQ_submitUser_id" id="smallType" value="${searchParam.search_EQ_submitUser_id}" data-autocomplete="${searchParam.smallTypeText}">
				   <input type="hidden" name="smallTypeText" id="smallTypeText" value="${searchParam.smallTypeText}">
                 </div>
               </div>
               <div class="control-group">
                 <label class="control-label">主题：</label>
                 <div class="controls">
                   <input type="text" class="form-text" name="search_LIKE_topic" value="${searchParam.search_LIKE_topic}">
                 </div>
               </div>
               <div class="control-group">
                 <label class="control-label">提交日期：</label>
                   <div class="controls time-search">
                   <span>从</span>&nbsp;<input type="text" class="short-text form-text" name="search_GTE_submitTime" class="date-mini search-time-limit" value="${searchParam.search_GTE_submitTime}" data-calendar="{pair:'search_LTE_submitTime',scope:'year,1'}"> 
                   <span>到</span>&nbsp;<input type="text" class="short-text form-text" name="search_LTE_submitTime" value="${searchParam.search_LTE_submitTime}">
                 </div>
               </div>
               <div class="control-group">
                 <label class="control-label">当前处理人：</label>
                 <div class="controls">
                    <input type="hidden" name="search_LIKE_singerIds" id="singerIds" value="${searchParam.search_LIKE_singerIds }" data-autocomplete="${searchParam.singerIdsText}" >
                 	<input type="hidden" name="singerIdsText" id="singerIdsText" value="${searchParam.singerIdsText}">
                 </div>
               </div>
               <div class="control-group">
                 <label class="control-label">状态：</label>
                 <div class="controls">
                   <select name="search_EQ_mainState">
						<option value="" selected>——请选择——
						<option value="0" <c:if test="${searchParam.search_EQ_mainState eq 0 }">selected="selected"</c:if> >受理中</option> 
						<option value="1" <c:if test="${searchParam.search_EQ_mainState eq 1 }">selected="selected"</c:if> >待客户确认</option>
						<option value="2" <c:if test="${searchParam.search_EQ_mainState eq 2 }">selected="selected"</c:if> >已关闭</option>
					 </select>
                 </div>
               </div>
              <div class="control-group">
                 <label class="control-label">超期预警：</label>
                 <div class="controls">
                   <select name="search_EQ_overAndWarningData">
						<option value="0" selected>——请选择——</option>
						<option value="2" <c:if test="${searchParam.search_EQ_overAndWarningData eq 2 }">selected="selected"</c:if>>超期</option>
						<option value="1" <c:if test="${searchParam.search_EQ_overAndWarningData eq 1 }">selected="selected"</c:if>>预警</option>
					 </select>
                 </div>
               </div>
             </div>
             <div class="row-fluid">
                <button type="button" data-listen="searchBtn" class="btn">查询</button> <%--  ${role.role.rid} --%>
              	<a class="btn btn-diy" href="${ctx }/event/exportEvent" target="_blank" data-export="#searchForm">导出到Excel</a>
              	<c:forEach items="${userRole }" var="role">
              		<c:if test="${role.role.rid == 'customerManagerUser' }">
                		<a href="${ctx }/event/applyEdit/0" class="btn btn-primary" target="_blank">&nbsp代客户提交问题</a>
              		</c:if>
              	</c:forEach>
              </div>
           </form>
         </div>
         <div id="resultTableQuestion"></div>
	</div>
	<script type="text/javascript">F.run('static/js/event/applyList');</script>
    </body>
</html>

