<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="process" uri="/WEB-INF/tags/process.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${basePath}" />
	 
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		<h4>服务类型编辑</h4>
	</div>

	<div class="modal-body">
		<form id="serviceTypeForm" class="form-horizontal"  data-validate="{debug:true}"> 
		  	<div class="control-group">
		    	<label class="control-label" for="name">服务类型：</label>
		    	<div class="controls controls-new">
		    		<input type="text" name="name" data-ruler="{must:1}" value="${serviceType.name }">
		    	</div>
		  	</div> 
		    <div class="control-group">
		    	<label class="control-label" for="name">服务经理：</label>
		    	<div class="controls controls-new">
		    		<input type="hidden"   id="serviceManagerAutoId" data-autocomplete="${user.uid}/${user.userName}"/>
		    	</div>
		  	</div>
		  	<div class="control-group">
		    	<label class="control-label" for="state">状态：</label>
		    	<div class="controls controls-new">
					<label class="radio inline">
						<input type="radio" name="state" value="1" <c:if test="${serviceType.state eq 1 }">checked</c:if> checked/> 有效
					</label>
					<label class="radio inline">
						<input type="radio" name="state" value="0" <c:if test="${serviceType.state eq 0 }">checked</c:if> /> 无效
					</label>
		    	</div>
		  	</div>
		     <input type="hidden" name="id" id="serviceTypeId" value="${serviceType.id }" >
		     <input type="hidden" id="tenantID" name="tenantID" value="${tenantID}" >
		     <input type="hidden" name="tenant.id" value="${serviceType.tenant.id}" >
		     <input type="hidden" name="ids" id="ids" value="${users}"/>
		 </form> 
	</div>
	
	<div class="modal-footer">
	    <button type="button" class="btn btn-primary" data-listen="saveServiceType" data-target="#serviceTypeForm">保存</button>
	</div>





