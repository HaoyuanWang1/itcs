<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${basePath}" />
<html>
	<head>
		<meta charset="utf-8" />
		<title>Flamingo后台管理-<sitemesh:title/></title>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta http-equiv="Cache-Control" content="no-store" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		<script type="text/javascript">
			var webContext = '${ctx}';
		</script>
		<link href="${ctx}/static/core/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
		<link href="${ctx}/static/css/admin.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="${ctx}/static/core/seajs/sea.js"></script>
		<script type="text/javascript" src="${ctx}/static/config/main.js"></script>
		<sitemesh:head/>
	</head>

	<body>
		<div class="navbar navbar-inverse navbar-fixed-top">
			<div class="navbar-inner">
				<div class="container">
					<a class="brand" href="${ctx}/admin"><i class="icon-thumbs-up"></i> Flamingo</a>
					<div class="nav-collapse collapse">
						<ul class="nav">
							<li><a href="${ctx}/admin"><i class="icon-home"></i> 首页</a></li>
							<li class="dropdown">
								<a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown">
									<i class="icon-key"></i> 安全管理<b class="caret"></b>
								</a>
								<ul class="dropdown-menu">
									<li><a href="${ctx}/user/page"><i class="icon-user"></i> 用户</a></li>
									<li><a href="${ctx}/role/page"><i class="icon-user-md"></i> 角色</a></li>
									<li><a href="${ctx}/resource/tree"><i class="icon-fire"></i> 资源</a></li>
									<!--  <li class="divider"></li> -->
								</ul>
							</li>
							<li class="dropdown">
								<a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown">
									<i class="icon-truck"></i> 流程引擎<b class="caret"></b>
								</a>
								<ul class="dropdown-menu">
									<li><a href="${ctx}/admin/pd/page"><i class="icon-edit"></i> 流程定义</a></li>
									<li class="divider"></li>
									<li><a href="${ctx}/admin/flowCtrl/workItemPage"><i class="icon-wrench"></i> 待办任务控制</a></li>
									<li><a href="${ctx}/admin/flowCtrl/applyPage"><i class="icon-search"></i> 流程申请查询</a></li>
								</ul>
							</li>
							<li class="dropdown">
								<a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown">
									<i class="icon-cog"></i> 系统设置<b class="caret"></b>
								</a>
								<ul class="dropdown-menu">
									<li><a href="${ctx}/admin/mailTemplate/list"><i class="icon-envelope-alt"></i> 邮件模板</a></li>
									<li><a href="${ctx}/admin/scheduler/list"><i class="icon-time"></i> 定时任务</a></li>
									<li><a href="${ctx}/admin/property/list"><i class="icon-cogs"></i> 系统属性设置</a></li>
									<li><a href="${ctx}/admin/cache"><i class="icon-save"></i> 系统缓存</a></li>
								</ul>
							</li>
						</ul>
						<p class="navbar-text pull-right">
							${onlineUser.uid}/${onlineUser.userName}
						</p>
					</div>
				</div>
			</div>
		</div>
		<div class="container">
	    		<sitemesh:body/>
	    </div>
	</body>
</html>