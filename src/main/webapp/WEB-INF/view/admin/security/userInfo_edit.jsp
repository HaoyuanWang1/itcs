<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	<h3>用户信息编辑</h3>
</div>
<div class="modal-body">
	<form class="form-horizontal" name="userInfoForm" id="userInfoForm" >
		<div class="control-group">
	    	<label class="control-label" for="uid">用户UID：</label>
	    	<div class="controls ">
	    	<input type="text" name="uid" value="${user.uid}" >	    	
	    	</div>
	  	</div> 
	  	<div class="control-group">
	    	<label class="control-label" for="userName">用户名：</label>
	    	<div class="controls ">
	    	<input type="text" name="userName" value="${user.userName}" >	    	
	    	</div>
	  	</div> 
	  	<div class="control-group">
	    	<label class="control-label" for="tenant.id">所属租户：</label>
	    	<div class="controls ">
	    	<input type="hidden" name="tenant.id" value="${user.tenant.id}" id="fromTenantId" data-autocomplete="${user.tenant.code}/${user.tenant.name}" >	    	
	    	</div>
	  	</div> 		
	  	<div class="control-group">
	    	<label class="control-label" for="email">邮箱：</label>
	    	<div class="controls ">
	    	<input type="text" name="email" value="${user.email}"  >	    	
	    	</div>
	  	</div> 			
	  	<div class="control-group">
	    	<label class="control-label" for="tel">座机 ：</label>
	    	<div class="controls ">
	    	<input type="text" name="tel" value="${user.tel}" >	    	
	    	</div>
	  	</div> 		
	  	<div class="control-group">
	    	<label class="control-label" for="mobile">电话 ：</label>
	    	<div class="controls ">
	    	<input type="text" name="mobile" value="${user.mobile}" >	    	
	    	</div>
	  	</div> 			
	  	<div class="control-group">
	    	<label class="control-label" for="userType">用户类型 ：</label>
	    	<div class="controls ">
	    	<input type="text" name="userType" value="${user.userType}" >	    	
	    	</div>
	  	</div> 			
	  	<div class="control-group">
	    	<label class="control-label" for="deptCode">所属部门编号 ：</label>
	    	<div class="controls ">
	    	<input type="text" name="deptCode" value="${user.deptCode}" > [请输入数字]	    	
	    	</div>
	  	</div> 			
	  	<div class="control-group">
	    	<label class="control-label" for="deptName">所属部门名称 ：</label>
	    	<div class="controls ">
	    	<input type="text" name="deptName" value="${user.deptName}" >	    	
	    	</div>
	  	</div>
	  	<div class="control-group">
	    	<label class="control-label" for="leaveDate">离职日期 ：</label>
	    	<div class="controls ">
	    	<input type="text" name="leaveDate" value="${user.leaveDate}" >	    	
	    	</div>
	  	</div> 	 				

		<input type="hidden" name="localUser" value="${user.localUser}"/>
		<input type="hidden" name="state" value="${user.state}"/>
		<input type="hidden" name="password" value="${user.password}"/>
		<input type="hidden" name="enabled" value="${user.enabled}"/>
		<input type="hidden" name="id" value="${user.id}"/>
	</form>
</div>
<div class="modal-footer">
	<button type="button" class="btn btn-primary" data-listen="saveUserInfoForm" data-target="#userInfoForm" >保存</button>
	<!--<input type="button" class="btn" data-dismiss="modal" value="关闭"/> -->
</div>

