<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="commons/dwzres.jsp" %>
<%@ include file="commons/taglibs.jsp" %>

<dppc:resource path="js/cardjsonp.js"/>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">




<title>肉菜批发市场追溯管理系统</title>


<script type="text/javascript">
	$(function() {
		DWZ.init("${CTX}/loadDwzFrags.do", {
			loginUrl : "${CTX}/toLogin.do",
			loginTitle : "登录", // 弹出登录对话框
			statusCode : {
				ok : 200,
				error : 300,
				timeout : 301
			},
			debug : false, // 调试模式 【true|false】
			callback : function() {
				initEnv();
			}
		});
	});

</script>
</head>
<body scroll="no">
	<div id="layout">
		<div id="header">
			<div class="headerNav">
				<a class="logo" href="http://www.dppc.org/">这里是Logo和主题</a>
				<ul class="nav">
					<li><a href="#">${USER_KEY.userName}，您好！</a></li>
					<li><a href="${CTX}/logout.do">退出</a></li>
				</ul>
			</div>
		</div>

		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse">
						<div></div>
					</div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse">
					<h2>主菜单</h2>
					<div>收缩</div>
				</div>
				<div class="accordion" fillSpace="sidebar">
					 <c:forEach var="parentMenu" items="${USER_KEY.menus }">
						<c:if test="${parentMenu.parentId eq 0 }">
							<div class="accordionHeader">
								<h2>
									<span>Folder</span>${parentMenu.menuName }
								</h2>
							</div>
							<div class="accordionContent">
								<ul class="tree treeFolder">
								<c:forEach var="childMenu" items="${USER_KEY.menus }">
									<c:if test="${childMenu.parentId eq parentMenu.menuId }">
										<c:if test="${childMenu.menuId < 0 }">
											<li><a href="${CTX }${childMenu.url}" target="_blank">${childMenu.menuName }</a></li>
										</c:if>
										<c:if test="${childMenu.menuId > 0}">
											<li><a href="${CTX }${childMenu.url}" target="navTab" rel="${childMenu.rel }">${childMenu.menuName }</a></li>
										</c:if>
									</c:if>
								</c:forEach>
								</ul>
							</div>
						</c:if>
					</c:forEach> 
				</div>
			</div>
		</div>
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent">
						<!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:;"><span><span
										class="home_icon">主页</span></span></a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div>
					<div class="tabsRight">right</div>
					<div class="tabsMore">more</div>
				</div>
				
				<ul class="tabsMoreList">
					<li><a href="javascript:;">主页</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page unitBox">
						<div class="pageFormContent" layoutH="80" style="margin-right:230px">
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>

	<div id="footer">
		Copyright &copy; 2010 <a href="http://www.dppc.org/" target="_blank">中商流通生产力促进中心有限公司</a>
		京ICP备05011365号
	</div>
</body>
</html>