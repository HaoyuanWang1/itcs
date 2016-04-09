<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="/WEB-INF/tags/flamingo-form.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${basePath}" />
<html>
	<head>
		<title>流程引擎管理</title>
	</head>
	<body>
		<ul class="breadcrumb">
   			<li><a href="${ctx}/flow/pd/page">流程引擎</a> <span class="divider">/</span></li>
   			<li class="active">流程引擎编辑</li>
   		</ul>
		
		<form:form id="flowDefineForm" action="${ctx}/flow/pd/publish" class="form-horizontal" methodParam="requestMethod" modelAttribute="pd">
			<div class="control-groups">
				<div class="control-group">
					<label class="control-label">流程定义名：</label>
					<div class="controls">
						<form:input path="pid" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">流程描述：</label>
					<div class="controls">
						<form:input path="name" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">业务处理BEAN：</label>
					<div class="controls">
						<form:input path="eventBean" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">业务消息BEAN：</label>
					<div class="controls">
						<form:input path="messageBean" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">版本：</label>
					<div class="controls">
						<form:input path="version" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">状态：</label>
					<div class="controls">
						<select name="state">
							<option value="0" <c:if test="${pd.state==0}">selected</c:if>>草稿</option>
							<option value="1" <c:if test="${pd.state==1}">selected</c:if>>发布</option>
							<option value="2" <c:if test="${pd.state==2}">selected</c:if>>过期</option>
						</select>
					</div>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">XML：</label>
				<div class="controls">
					<form:textarea path="defineXml" id="defineXml" cssClass="span9 code-font" rows="5"/>
				</div>
			</div>
			<form:hidden path="id" />
			<div class="control-group">
				<div class="controls">
					<input type="button" class="btn" data-listen="saveFlowDefine" value="保存" data-target="#flowDefineForm" />
					<input type="submit" class="btn btn-primary" value="发布" />
				</div>
			</div>
		</form:form>
		
		<hr />

		<div id="flowFlexContainer">
			<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
				id="FlexFlowIE" width="100%" height="500px"
				codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">
				<param name="movie" value="${ctx }/static/swf/FlexFlow.swf" />
				<param name="quality" value="high" />
				<param name="bgcolor" value="#ffffff" />
				<param name="allowScriptAccess" value="sameDomain" />
				<embed src="${ctx }/static/swf/FlexFlow.swf" id="FlexFlowFF"
					quality="high" bgcolor="#ffffff" width="100%" height="100%"
					align="middle" play="true" loop="false" quality="high"
					allowScriptAccess="sameDomain" type="application/x-shockwave-flash"
					pluginspage="http://www.adobe.com/go/getflashplayer">
				</embed>
			</object>
		</div>
		
		<script type="text/javascript">F.run('modules/flow/flowDefinition');</script>
	</body>
</html>