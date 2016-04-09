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
		<li class="active">客户经理受理</li>
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
						<div class="form-text">	${apply.tenant.name}</div>
						<form:hidden path="tenant.id" /> 
					</div>
				</div>                
				<div class="control-group">
					<label class="control-label">客户账号：</label>
					<div class="controls">
						<div class="form-text">	${apply.submitUser.uid}/${apply.submitUser.userName}</div>
					</div>
				</div>                
				<div class="control-group">
					<label class="control-label">联系人：</label>
					<div class="controls">
						<div class="form-text">	${apply.linkMan}</div>
						<form:hidden path="linkMan"/>
					</div>
				</div>                
				<div class="control-group">
					<label class="control-label">电话：</label>
					<div class="controls">
						<div class="form-text">${apply.tel}</div>
						<form:hidden path="tel" />
					</div>
				</div>                
				<div class="control-group">
					<label class="control-label">E-mail：</label>
					<div class="controls">
						<div class="form-text">${apply.email}</div>
						<form:hidden path="email"/>
					</div>
				</div>	  
			</div>              
			<div class="control-group">
				<label class="control-label">主题：</label>
				<div class="controls">
					<div class="form-text">${apply.topic}</div>
					<form:hidden path="topic"/>  
				</div>
			</div>
			<div class="control-group">
					<label class="control-label">编号：</label>
				    <div class="controls">
				    	<div class="form-text">${apply.code}</div>
				    </div>
			</div> 
			<div class="control-group">
				<label class="control-label">详细描述：</label>
				<div class="controls">
					<div class="form-text">${apply.content}</div>
					<form:hidden path="content"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">附件：</label>
				<div class="controls">		
					<div data-attachment-list="${apply.attachment}"></div>
					<form:hidden path="attachment"/>
				</div>
			</div>
			<div class="control-groups">
				<div class="control-group">
					<label class="control-label"  for="mainType">信息类型：</label>
					<div class="controls">
						<select name="mainType" data-ruler="{require:1}">
							<option value="" selected>——请选择——</option>
							<option value="0"  <c:if test="${apply.mainType eq 0 }">selected="selected"</c:if>>故障</option>
							<option value="1" <c:if test="${apply.mainType eq 1 }">selected="selected"</c:if>>咨询</option>
							<option value="2" <c:if test="${apply.mainType eq 2 }">selected="selected"</c:if>>建议</option>
							<option value="3" <c:if test="${apply.mainType eq 3 }">selected="selected"</c:if>>需求</option>
							<option value="4" <c:if test="${apply.mainType eq 4 }">selected="selected"</c:if>>投诉</option>
						</select>
					</div>
				</div>
				<c:if test="${apply.isInstead !='no' }">
					<div class="control-group">
						<label class="control-label"  for="submitType">信息来源：</label>
						<div class="controls">
							<select name="submitType" data-ruler="{require:1}">
								<option value="" selected>——请选择——</option>
								<option value="0" <c:if test="${apply.submitType eq 0 }">selected="selected"</c:if>>邮件</option>
								<option value="1" <c:if test="${apply.submitType eq 1 }">selected="selected"</c:if>>电话</option>
								<option value="2" <c:if test="${apply.submitType eq 2 }">selected="selected"</c:if>>其他</option>
							</select>
						</div>
					</div>
				</c:if>
				<div class="control-group">
					<label class="control-label" for="serviceType.id">服务类型：</label> 
					<div class="controls">
						<select name="serviceType.id" data-ruler="{require:1}">
							<option value="">——请选择——</option>
							<c:forEach items="${serviceTypeList }" var="type">
								<option value="${type.id }" <c:if test="${type.id == apply.serviceType.id}">selected="selected"</c:if>>${type.name }</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label"  for="serviceLevel.id">紧急程度：</label>
					<div class="controls">
						<select name="serviceLevel.id" data-ruler="{require:1}">
							<option value="">——请选择——</option>
							<c:forEach items="${serviceLevelList }" var="level">
								<option value="${level.id }" <c:if test="${level.id == apply.serviceLevel.id}">selected="selected"</c:if>>${level.name }</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</div>		
			<div class="process">
				<h4>处理记录：</h4>
				<div id="eventlogPage"></div>
			</div>
			<div class="text-submit">
				<textarea rows="5"  name="context"  class="long-textarea"></textarea>
			</div>
			<div class="control-group">
				<label class="pull-left"  for="attachment">附件：</label>
				<input type="hidden" name="appendix" data-attachment="{target:'#fileList'}">
				<div id="fileList"></div> 
			</div>
			<form:hidden path="id" id="eventId"/>
			<form:hidden path="state"/>
			<form:hidden path="submitType"/>
			<form:hidden path="submitUser.id"/>
			<form:hidden path="singerIds"/>
			<form:hidden path="createUser.id"/>
			<form:hidden path="processId"/>
			<form:hidden path="recentAction"/>
		    <form:hidden path="recentUser"/>
			<form:hidden path="code"/>
			<form:hidden path="submitTime"/>
			<form:hidden path="submitMode"/>
			<form:hidden path="isInstead"/>
			<form:hidden path="isWarningFlag"/>
		    <form:hidden path="isOverduFlag"/>
		</form:form>
		<process:operate flowAuth="${flowAuth}" className="form-operate accept_customer">
			<div class="span12">
				<input type="button" class="btn btn-diy flow-btnbtn btn-primary sign-btn" data-event="save" data-id="${flowAuth.taskId }" data-listen="flowBtnClick" data-target="#eventForm" value="提交，转至服务经理">
			</div>
		</process:operate> 
		<script type="text/javascript">F.run('static/js/event/applyManagerEdit');</script>
	</body>
</html>