<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="process" uri="/WEB-INF/tags/process.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="ctx" value="${basePath}" />
<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	<h4>紧急程度编辑</h4>
</div>
<div class="modal-body">
 	<form id="serviceLevelForm" class="form-horizontal"  data-validate="{debug:true}"> 
 		<div class="control-group">
		    	<label class="control-label" for="name">紧急程度名称：</label>
		    	<div class="controls controls-new">
		    		<input type="text" name="name" value="${ serviceLevel.name}" data-ruler="{must:1}">
		    	</div>
		</div>
  		<div class="control-group">
		    	<label class="control-label" for="overdueTime">超期时长：</label>
		    	<div class="controls controls-new">
		    		<input type="text" name="overdueTime" value="${ serviceLevel.overdueTime}" >
		    	</div>
		</div>
   		<div class="control-group">
		    	<label class="control-label" for="alertTime">预警时长：</label>
		    	<div class="controls controls-new">
		    		<input type="text" name="alertTime" value="${ serviceLevel.alertTime}" >
		    	</div>
		</div>
		<div class="control-group">
		    	<label class="control-label" for="replayTime">响应时长：</label>
		    	<div class="controls controls-new">
		    		<input type="text" name="replayTime" value="${serviceLevel.replayTime}" >
		    	</div>
		</div>
	  	<div class="control-group">
	    	<label class="control-label" for="state">状态：</label>
	    	<div class="controls controls-new">
				<label class="radio inline">
					<input type="radio" name="state" value="1" <c:if test="${serviceLevel.state eq 1 }">checked</c:if> checked/> 有效
				</label>
				<label class="radio inline">
					<input type="radio" name="state" value="0" <c:if test="${serviceLevel.state eq 0 }">checked</c:if> /> 无效
				</label>
	    	</div>
	  	</div>  
	     <input type="hidden" name="id" value="${serviceLevel.id }" > 
	     <input type="hidden" name="tenant.id" value="${tenantID}" id="tenantID"> 
	 </form> 
</div>
<div class="modal-footer">
     <button type="button" class="btn btn-primary" data-listen="serviceLevelSave" data-target="#serviceLevelForm">保存</button>
</div>







