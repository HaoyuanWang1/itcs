<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="/WEB-INF/tags/flamingo-form.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${basePath}" />
<html>
    <head>
        <title>编辑组织信息</title>
    </head>
    <body>
   		<ul class="breadcrumb">
   			<li><a href="${ctx}/">首页</a> <span class="divider">/</span></li>
   			<li class="active">组织编辑</li>
   		</ul>
   		
   		<div class="markdown">
		<h4>1.组织基本信息</h4>
		<form:form id="tenantForm" class="form-horizontal" methodParam="requestMethod" modelAttribute="tenant">
			<div class="control-group">
				<label class="control-label">组织名称：</label>
				<div class="controls">
					<form:input path="name"></form:input>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">组织编号：</label>
				<div class="controls">
					<div class="form-text">${empty tenant.code ? '自动生成' : tenant.code}</div>
					<form:hidden path="code" />
				</div>
			</div>
			<div class="control-group">
                 <label class="control-label">组织状态：</label>
                 <div class="controls">
                   <select name="type">
						<option value="C" <c:if test= "${tenant.type == 'C'}">selected</c:if>>客户</option>
						<option value="S" <c:if test= "${tenant.type == 'S'}">selected</c:if>>服务商</option>
					 </select>
                 </div>
               </div>
             </div>

			<form:hidden path="id" id="tenantID" />
			<div class="control-group">
				<div class="controls">
						<input type="button" data-listen="saveTenantForm" data-target="#tenantForm" class="btn btn-primary" value="保存"/>
					<c:if test="${!empty tenant.id}">
						<c:if test="${ tenant.state==1}">
							<input type="button" data-listen="onStart" data-target="#tenantForm" class="btn btn-primary" value="停用"/>
						</c:if>
						<c:if test="${tenant.state==0}">
							<input type="button" data-listen="start" data-target="#tenantForm" class="btn btn-primary" value="启用"/>
						</c:if>
					</c:if>
				</div>
			</div>
		</form:form>
		
		<c:if test = "${tenant.type == 'C'}">
		<c:if test="${!empty tenant.id}">
			<h4>2.设置客户经理</h4>
			<div>
				<input type="hidden" id="tenantManager" />
			</div>
			<div id="tenantManagerList" data-url="${ctx}/tenantManager/panel/list/${tenant.id}">
			</div>
			<h4>3.设置服务类型</h4>
			<a href="${ctx }/serviceType/serviceTypeEdit/panel/0/${tenant.id}" data-load="#serviceTypeEditID" data-load-callback="serviceTypeEditBox">新增服务类型</a>
			<div id="serviceTypeList" data-url="${ctx}/tenant/tenantEdit/panel/list/${tenant.id}">
				<c:choose>
					<c:when test="${fn:length(serviceTypeList)>0}">
						<table class="table table-condensed table-hover table-striped">
							<thead>
								<tr>
									<th>服务类型</th>
									<th>服务经理</th>
									<th>状态</th>
									<th class="span1">操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${serviceTypeList}" var="serviceType">
									<tr> 
										<td>${serviceType.name}</td>
										<td>
											<div class="sm" data-url="${ctx}/tenant/getServiceManagerByServiceTypeId/${serviceType.id}"></div>
										</td>
										<td>${serviceType.stateText }</td>
										<td><a href="${ctx }/serviceType/serviceTypeEdit/panel/${serviceType.id}/${tenant.id}" data-load="#serviceTypeEditID" data-load-callback="serviceTypeEditBox" >编辑</a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:when>
					<c:otherwise>
						<div class="alert">还没有服务类型，请先添加。</div>
					</c:otherwise>
				</c:choose>
			</div>
			<h4>4.设置SLA</h4>
			<a href="${ctx }/serviceLevel/serviceLevelEdit/panel/0/${tenant.id}" data-load="#serviceLevelModal">新增紧急程度</a>
			<div id="serviceTypeList" data-url="${ctx}/tenant/tenantEdit/panel/list/${tenant.id}">
				<c:choose>
					<c:when test="${fn:length(serviceLevelList)>0}">
						<table class="table table-condensed table-hover table-striped">
							<thead>
								<tr>
									<th>紧急程度</th>
									<th>响应时间(小时)</th>
									<th>预警时间(小时)</th>
									<th>超期时间(小时)</th>
									<th>状态</th>
									<th class="span1">操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${serviceLevelList}" var="serviceLevel">
									<tr> 
										<td>${serviceLevel.name}</td>
										<td>${serviceLevel.replayTime }</td>
										<td>${serviceLevel.alertTime }</td>
										<td>${serviceLevel.overdueTime }</td>
										<td>${serviceLevel.stateText }</td>
										<td><a href="${ctx }/serviceLevel/serviceLevelEdit/panel/${serviceLevel.id}/${tenant.id}" data-load="#serviceLevelModal">编辑</a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:when>
					<c:otherwise>
						<div class="alert">还没有服务类型，请先添加。</div>
					</c:otherwise>
				</c:choose>
			</div>
			<h4>5.设置登陆账号</h4><a href="${ctx }/user/page">维护账号请点此链接</a>
		</c:if>
		</div>
		</c:if>
		<div class="modal fade hide" id="customerManagerModal"></div> 
		<div class="modal fade hide" id="serviceTypeModal"></div>
		<div class="modal fade hide" id="serviceLevelModal"></div>
		<div class="modal fade hide" id="serviceTypeEditID" style="width: 800px;margin-left: -400px;"></div>
		<script type="text/javascript">
			F.run('modules/tenant/tenantEdit');
		</script>
    </body>
</html>