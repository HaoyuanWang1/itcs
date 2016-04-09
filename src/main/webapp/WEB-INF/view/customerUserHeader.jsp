<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${basePath}" />
<div class="navbar navbar-static-top">
	<div class="navbar-inner">
		<div class="container">
			<a class="brand" href="${ctx}/"><img src="${ctx}/static/images/logo.gif" alt="dcone"/>客户支持中心</a>
			<div class="nav-collapse collapse">
				<ul class="nav">
					<li class="dropdown">
						<a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown">
							<i class="icon-user"></i> ${onlineUser.userName}<b class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li><a href="${ctx}/user/updatePwdPage"><i class="icon-lock"></i> 修改密码</a></li>
							<li><a href="${ctx}/logout"><i class="icon-signout"></i> 退出</a></li>
						</ul>
					</li>
				</ul>
					<%-- <c:forEach items="${onlineEngineerMenu }" var="menu" >
						<c:choose>
							<c:when test="${fn:length(menu.children) == 0}">
							</c:when>
							<c:otherwise>
								<ul class="nav">
									<c:forEach items="${menu.children}" var="grandson" >
										 <c:if test="${fn:length(grandson.children) == 0}">
											<li class="dropdown">
												<a href="${ctx }${grandson.url }" class="dropdown-toggle" > ${grandson.text }</a>
											</li>
										</c:if>
									</c:forEach>
								</ul>
							</c:otherwise>
						</c:choose>
					</c:forEach> --%>
				<ul class="nav">
					<li class="dropdown">
						<a href="${ctx}/" class="dropdown-toggle"> 首页</a>
					</li>
					<li class="dropdown">
						<a href="${ctx}/event/page" class="dropdown-toggle">已提交的问题</a>
					</li>
				</ul>
				
			</div>
		</div>
	</div>
</div>