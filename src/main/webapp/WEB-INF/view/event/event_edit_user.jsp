<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="process" uri="/WEB-INF/tags/process.tld"%>
<%@ taglib prefix="form" uri="/WEB-INF/tags/flamingo-form.tld"%>
<%@ taglib prefix="str" uri="/WEB-INF/tags/stringTag.tld"%>
<c:set var="ctx" value="${basePath}"/>
<html>
	<head>
		<title>${apply.applyName}</title>
	</head>
	<body> 
	<ul class="breadcrumb">
		<li><a href="${ctx}/">首页</a> <span class="divider">/</span></li>
		<li class="active">问题提交</li>
	</ul>
	<process:progress processId="${apply.processId}" pdName="${apply.pdName}"></process:progress> 
	<form:form id="eventForm" class="form-horizontal" methodParam="requestMethod" modelAttribute="apply" validate="{debug:true}">
		<div class="control-groups">
			<div class="control-group">
				<label class="control-label">提交人：</label>
				<div class="controls">
					<div class="form-text">${apply.createUser.userName}</div>
				</div>
			</div>
			<div class="control-group">
			    <label class="control-label">提交时间：</label>
			    <div class="controls">
			    	<div class="form-text">${apply.submitTime}</div>
			    </div>
		    </div>
			<div class="control-group">
				<label class="control-label">客户：</label>
				<div class="controls">
					<div class="form-text">	${apply.createUser.tenant.name}</div>
					<form:hidden  path="tenant.id" value="${apply.createUser.tenant.id}"/> 
				</div>
			</div>
			<div class="control-group">
			    <label class="control-label">客户账号：</label>
			    <div class="controls">
			    	<div class="form-text">${apply.submitUser.uid}/${apply.submitUser.userName}</div>
			    </div>
		    </div>
		    <div class="control-group">
		      <label class="control-label" for="linkMan">联系人：</label>
		      <div class="controls">
		      		<form:input path="linkMan" value="${apply.submitUser.userName}" ruler="{must:1}"></form:input>
		      </div>
		    </div> 
		    <div class="control-group">
			    <label class="control-label" for="tel">电话：</label>
			    <div class="controls">
			    	<form:input path="tel" value="${apply.submitUser.mobile}" ruler="{must:1}"></form:input>
		        </div>
		    </div>
		    <div class="control-group">
                <label class="control-label" for="email">E-mail：</label>
                <div class="controls">
               		<form:input path="email" value="${apply.submitUser.email}" ruler="{must:1}"></form:input>
                </div>
            </div>
		</div>
		<div class="control-group">
			<label class="control-label"  for="topic">主题：</label>
			<div class="controls">
				<form:input path="topic" class="span08" ruler="{must:1}"></form:input>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"  for="content">详细描述：</label>
			<div class="controls">
				<form:textarea path="content"  class="span08" rows="5" id="content" maxlength="2000" ruler="{must:1}"/>
				<p class="muted help-block">最多输入<b>2000</b>个字。</p>
			</div>
		</div>
	    <div class="control-group">
			<label class="control-label"  for="attachment">附件：</label>
			<div class="controls">
				<form:hidden path="attachment" data-attachment="{target:'#fileList'}"/>
				<div id="fileList"></div> 
			</div>
	    </div>
		 <div class="text-right">	
		   <input type="button" value="提交" class="btn btn-primary" data-listen="submitApply" data-target="#eventForm" />	        
		 </div>								
		 
		 <form:hidden path="id"/>
		 <form:hidden path="state"/>
		 <form:hidden path="mainState"/>
		 <form:hidden path="submitUser.id"/>
		 <form:hidden path="createUser.id"/>
		 <form:hidden path="singerIds"/>
		 <form:hidden path="processId"/>
		 <form:hidden path="submitTime"/>
		 <form:hidden path="recentAction"/>
		 <form:hidden path="recentUser"/>
		 <form:hidden path="submitMode"/>
		 <form:hidden path="isInstead"/>
		 <form:hidden path="mainType"/>
		 <form:hidden path="code"/>
		 <form:hidden path="isWarningFlag"/>
		 <form:hidden path="isOverduFlag"/>
		 <form:hidden path="submitType"/>
	</form:form>
	<script type="text/javascript">F.run('static/js/event/applyInsteadEdit');</script>
	</body>
</html>