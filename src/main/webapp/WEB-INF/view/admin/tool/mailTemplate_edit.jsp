<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${basePath}" />
<link rel="stylesheet" href="${ctx}/static/plugin/kindeditor/themes/default/default.css" />
<script charset="utf-8" src="${ctx}/static/plugin/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="${ctx}/static/plugin/kindeditor/lang/zh_CN.js"></script>
<script type="text/javascript">
	var webContext = "${basePath}";
	var editor;
	KindEditor.ready(function(K) {
		editor = K.create('textarea[name="content"]', {
			resizeType : 1,
			allowPreviewEmoticons : false,
			allowImageUpload : false,
			items : [
				'source','fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
				'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
				'insertunorderedlist', '|', 'link','fullscreen']
		});
	});
</script>
<div style="width: 1000px; margin: 10px auto;">
	<ul class="breadcrumb">
		<li><a href="#">系统设置</a> <span class="divider">/</span></li>
		<li class="active">邮件模板管理</li>
	</ul>
	<form id="mtForm" class="form-inline" action="${ctx}/admin/mailTemplate/save" method="post">
		<table width="100%">
			<tr>
				<td width="120" align="right">*模板ID:
				<input type="hidden" name="id" value="${mt.id}"/></td>
				<td><input onkeyup="value=value.replace(/[\W]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" class="input-xlarge" type="text" id="code"
					name="code" value="${mt.code}" /></td>
				<td width="120" align="right">*模板描述:</td>
				<td><input class="input-xlarge" type="text" id="descn"
					name="descn" value="${mt.descn}" />
			</tr>
			<tr>
				<td align="right">*邮件标题:</td>
				<td colspan="3"><input class="input-xlarge" type="text" id="title"
					name="title" value="${mt.title}" /></td>
			</tr>
			<tr>
				<td align="right">*邮件内容:</td>
				<td colspan="3"><textarea name="content" id="content"
						style="font-size: 12px; width: 99%">${mt.content}</textarea></td>
			</tr>
		</table>
		<div align="center" style="margin-bottom: 10px; margin-top: 10px;">
			<input type="submit" class="btn" value="保存" />
			<input type="button" class="btn" onclick="cancel();" value="返回" />
		</div>
	</form>
</div>
