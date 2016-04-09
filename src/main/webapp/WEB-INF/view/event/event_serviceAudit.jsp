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
	        <li class="active">服务经理受理</li>
	    </ul>
 		<process:progress processId="${apply.processId}" pdName="${apply.pdName}"></process:progress> 
		<form:form id="eventForm" class="form-horizontal" methodParam="requestMethod" modelAttribute="apply" >
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
						<form:hidden path="tenant.id"/> 
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
						<form:hidden path="tel"/>
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
					<label class="control-label">信息类型：</label>
					<div class="controls">
						<div class="form-text">
							<c:if test="${apply.mainType != null}">
								<div class="form-text">${apply.mainTypeText}</div>
							</c:if>
						</div>
					</div>
				</div>
				<c:if test='${apply.isInstead !="no" }'>
					<div class="control-group">
						<label class="control-label">信息来源：</label>
						<div class="controls">
							<div class="form-text">
								<c:if test="${!empty apply.submitType }">
									${apply.submitTypeText }
								</c:if>
							</div>
						</div>
					</div>
				</c:if>
				<div class="control-group">
					<label class="control-label">服务类型：</label>
					<div class="controls">
 						<form:select path="serviceType.id">
							<form:options items="${serviceTypeList}" itemValue="id" itemLabel="name" />
						</form:select>
 					</div>
						
				</div>
				<div class="control-group">
					<label class="control-label">紧急程度：</label>
					<div class="controls">
						<div class="form-text">${apply.serviceLevel.name }</div>
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
			<form:hidden path="mainState"/>
			<form:hidden path="mainType"/>
			<form:hidden path="submitType"/>
			<form:hidden path="serviceLevel.id"/>
			<form:hidden path="serviceType.id"/>
			<form:hidden path="submitUser.id"/>
			<form:hidden path="singerIds"/>
			<form:hidden path="createUser.id"/>
			<form:hidden path="code"/>
			<form:hidden path="recentAction"/>
			<form:hidden path="recentUser"/>
			<form:hidden path="processId"/>
			<form:hidden path="submitTime"/>
			<form:hidden path="isSlove"/>
			<form:hidden path="isInstead"/>
			<form:hidden path="isWarningFlag"/>
			<form:hidden path="isOverduFlag"/>
			<input type="hidden" name="taskId" id="taskId"  value="${flowAuth.taskId }" >
			
		</form:form>
		<process:operate flowAuth="${flowAuth}" className="form-operate">
			<div class="span12">
				<button type="button" class="btn btn-primary" data-listen="other" data-target="#eventForm">仅回复，提交</button>
				<input type="button" class="btn btn-diy flow-btnbtn btn-primary"  data-listen="save"  data-target="#eventForm"  value="已解决，提交">
			</div>
		</process:operate> 
		<script type="text/javascript">F.run('static/js/event/applyServiceEdit');</script>
	</body>
</html>