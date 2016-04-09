<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${basePath}" />
<div class="navbar navbar-static-top">
	<div class="navbar-inner">
		<div class="container">
			<a class="brand" href="${ctx}/"><img src="${ctx}/static/images/logo.gif" alt="dcone"/>客户支持中心</a>
			<div class="nav-collapse collapse">
				<ul class="nav">
					<li class="dropdown">
						<a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown">
							<i class="icon-user"></i>${onlineUser.userName}<b class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li><a href="${ctx}/logout">退出</a></li>
						</ul>
					</li>
				</ul>
				<ul class="nav">
				<li class="dropdown">
						<a href="${ctx}/" class="dropdown-toggle"> 首页</a>
				</li>
					<c:forEach items="${onlineEngineerMenu }" var="menu">
						<c:choose>
							<c:when test="${fn:length(menu.children) == 0}">
								<li><a href="${ctx }${menu.url }">${menu.text }</a></li>
							</c:when>
							<c:otherwise>
								<li class="dropdown">
									<a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown"> ${menu.text }<b class="caret"></b>
									</a>
									<ul class="dropdown-menu">
										<c:forEach items="${menu.children}" var="grandson">
											<c:if test="${fn:length(grandson.children) == 0}">
												<li><a href="${ctx }${grandson.url }">${grandson.text }</a></li>
											</c:if>
										</c:forEach>
									</ul>
								</li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
</div>