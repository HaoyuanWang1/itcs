<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="modal-header">
	<!-- <button type="button" data-dismiss="modal" aria-hidden="true">&times;</button> -->
	<h4>密码重置</h4>
</div>
<div class="modal-body">
	<form class="form-horizontal" name="pwdResetForm" id="pwdResetForm" >
	<font color="grey">您确认密码重置吗?</font> 
	<input type="hidden" name="uid" value="${userUID }">
	</form>
</div>
<div class="modal-footer">
	<button type="button" class="btn btn-primary" data-listen="pwdResetSuccess" data-target="#pwdResetForm">确认</button>
	<input type="button" class="btn" data-dismiss="modal" value="取消"/>
</div>

