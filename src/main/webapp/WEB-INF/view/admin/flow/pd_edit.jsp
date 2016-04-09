<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${basePath}" />
<script src="${ctx}/static/js/flow/flowDefinition.js" type="text/javascript"></script>
<script type="text/javascript">
	var webContext = "${basePath}";
</script>
<div style="width: 1000px; margin: 10px auto;">
	<ul class="breadcrumb">
		<li><a href="#">流程管理</a> <span class="divider">/</span></li>
		<li class="active">流程定义</li>
	</ul>
	<form id="flowDefineForm" class="form-inline" method="post">
		<table width="100%">
			<tr>
				<td width="120" align="right">
					<input type="hidden" name="id" value="${pd.id}"/>
				*流程定义名:</td>
				<td><input onkeyup="value=value.replace(/[\W]/g,'') "onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" class="input-xlarge" type="text" id="pid"
					name="pid" value="${pd.pid}" />
				</td>
				<td width="120" align="right">*流程描述:</td>
				<td><input class="input-xlarge" type="text" id="name"
					name="name" value="${pd.name}" />
			</tr>
			<tr>
				<td align="right">*业务处理BEAN:</td>
				<td><input class="input-xlarge" type="text" id="handleBean"
					name="eventBean" value="${pd.eventBean}" /></td>
				<td align="right">*业务消息BEAN:</td>
				<td><input class="input-xlarge" type="text" id="messageBean"
					name="messageBean" value="${pd.messageBean}" /></td>
			</tr>
			<tr>
				<td align="right">*版本:</td>
				<td><input class="input-xlarge" type="text" id="version"
					name="version" value="${pd.version}" /></td>
				<td align="right">*状态:</td>
				<td>
					<select class="input-xlarge" id="state" name="state">
						<option value="0" <c:if test="${pd.state==0}">selected</c:if>>草稿</option>
						<option value="1" <c:if test="${pd.state==1}">selected</c:if>>发布</option>
						<option value="2" <c:if test="${pd.state==2}">selected</c:if>>过期</option>
					</select>
				</td>
			</tr>
			<tr>
				<td align="right">*XML:</td>
				<td colspan="3"><textarea name="defineXml" id="defineXml"
						style="font-size: 12px; width: 99%">${pd.defineXml}</textarea></td>
			</tr>
		</table>
		<div align="center" style="margin-bottom: 10px; margin-top: 10px;">
			<input type="button" class="btn" onclick="saveDefine()" value="保存" />
			<input type="submit" class="btn" onclick="this.form.action='${ctx}/admin/pd/publish'" value="发布" />
		</div>
	</form>
	<!--  -->
	<div style="width: 1000px; height:1000px;" id="div-center">
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
</div>
