<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${basePath}" />
<form id="userForm" name="userForm" class="form-horizontal">
   <table class="table">
	<tr>
		<td width="90" align="right">ITCODE:</td>
		<td >
		  <input class="input-medium" type="text" id="userId" name="userId" 
		  	<c:if test="${user.userId!=null}">readOnly="readOnly"</c:if> value="${user.userId}"
		  />
		</td>
	</tr>
	<tr>
		<td width="90" align="right">姓名:</td>
		<td><input class="input-medium" type="text" id="userName" name="userName" value="${user.userName}"/></td>
	</tr>
	<tr>
		<td align="right">密码:</td>
		<td><input class="input-medium" type="text" id="password" name="password" value="${user.password}"/></td>
	</tr>
	<tr>
		<td align="right">是否可用:</td>
		<td>
			<select id="enabled" name="enabled" class="input-medium">
				<option></option>
				<option value="1" <c:if test="${user.enabled==1}">selected="selected"</c:if>>是</option>
				<option value="0" <c:if test="${user.enabled==0}">selected="selected"</c:if>>否</option>
			</select>
		</td>
	</tr>
</table>
</form>