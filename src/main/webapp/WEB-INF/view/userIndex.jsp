<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${basePath}" />
<html>
	<head>
		<title>用户首页</title>
	</head>
	<body>
		<div class="container">
			<!--介绍文字-->
			<div class="introduce text-margin">
                <p>尊敬的<span>${currUser.tenant.name}</span>客户：您好！</p>
                <p class="para1">感谢联系神州数码！您的所有问题、需求、意见或建议，都有专人受理并尽快向您反馈。</p>
                <c:forEach items="${serviceLevelList }" var="level">
                		<c:if test="${level.replayTime==null && level.overdueTime==null}">
					  </c:if>
					  <c:if test="${ level.replayTime!=null && level.overdueTime!=null}">
					   <tr>
						 <td>
						   <p class="para2">${level.name }：<span>${level.replayTime}</span>小时响应，<span>${level.overdueTime}</span>小时内解决</p>
						 </td>
					  </tr>
					  </c:if>
					  <c:if test="${ level.replayTime!=null && level.overdueTime==null}">
					   <tr>
						 <td>
						   <p class="para2">${level.name }：<span>${level.replayTime}</span>小时响应</p>
						 </td>
					  </tr>
					  </c:if>
					  <c:if test="${ level.replayTime==null && level.overdueTime!=null}">
					   <tr>
						 <td>
						   <p class="para2">${level.name }：<span>${level.overdueTime}</span>小时内解决</p>
						 </td>
					  </tr>
					  </c:if>
					  
		  		</c:forEach>
		  		<p class="para1">您可以在线提交问题，也可以直接联系下方客户经理：</p>
		  		<c:forEach items="${tenantManagerlist }" var="manager">
					 <tr>
						 <td>
						    <p class="para2"><span>${manager.tenantManager.userName }</span>，电话：<span>${manager.tenantManager.tel }</span>，e-mail：<span>${manager.tenantManager.email }</span></p> 
						 </td>
					 </tr>
		 	  </c:forEach>
            </div>
            <!--问题提交-->
            <div class="submit">
             <p>
             	<a href="${ctx }/event/applyEdit/0" class="btn btn-large btn-primary" type="button" target="_blank"><i class="icon-plus"></i>&nbsp;提交问题/建议</a>
             </p>
            </div>
            <!--待确认问题-->
            <div class="unconfirmed">
	            <h4>待您确认的问题：</h4>
	            <div id="taskSignerUserPage"></div>
            </div>
		</div>
		<script type="text/javascript">F.run('static/js/event/customerUserIndex');</script>
	</body>
</html>
