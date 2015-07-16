<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="commons/taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>登录-肉菜批发市场追溯管理系统</title>
<link rel="stylesheet" type="text/css" href="${CTX}/css/logPage.css"/>
  <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">  
	<dppc:resource path="js/jquery.min.js"/>
	<script type="text/javascript">
	$(function() {
		var $loginBtn =$("#login_submit").click(function() {
			// 重复提交限制
			var disabled =$(this).attr("disabled");
			if (!disabled) {
				$(this).attr("disabled", "disabled");
				$("#loginForm").submit();
				return false;
			}
			//alert("正在努力登陆中，请稍后 ...");
			return false;
		});
		// 绑定回车提交表单
		$(document).keydown(function(event) {
			if (event.keyCode == 13) {
				$("#loginForm").submit();
			}
		});
		
		$("#selectImg").bind("click",function(){
			var second=new Date().getSeconds();
			this.src="yzmServlet?second="+second;
			
		});
	});
	</script>  
</head>

<body>
	<form id="loginForm" action="${CTX }/doLogin.do" method="post" >

		<div class="logBox">
   			<div class="logHeader">
     			<div class="logTitle"></div>
   	   		</div>
   	   	   <div class="logContent">
	   	   	   	<c:if test="${empty loginError }">
	  			<legend></legend>
	  			</c:if>
	  			<c:if test="${not empty loginError }">
	  			<legend><span style="color: red;">${loginError }</span></legend>
	  			</c:if>
	  				<c:if test="${empty loginError }">
	  			<legend></legend>
	  			</c:if>
	  			<c:if test="${not empty yzmError }">
	  			<legend><span style="color: red;">${yzmError }</span></legend>
	  			</c:if>
		        <ul class="m1">
				    <li class="m2 f1">用户名：<input name="userName" type="text" class="logInput1" /></li>
					<li class="m2 f1"><span style="letter-spacing:1em">密</span>码：<input name="passWord" type="password" class="logInput1" />
					</li>
					<li class="m2 f1">验证码：<input name="yzm_code" type="text" class="logInput2" /> </li>
					<div class="yzm f1">
					<img id="selectImg" alt="点击刷新验证码" title="点击刷新验证码" src="${pageContext.servletContext.contextPath}/yzmServlet" style="width: 100px;height: 30px;" />
					</div>
				    <li class="m3 f1" ><input id="login_submit" type="submit" class="inputBtn" onmouseover="this.style.backgroundPosition='-139px -116px'" onmouseout="this.style.backgroundPosition='0px -116px'" value="" />	<li>
				</ul>
          </div>
         <div class="logFooter"></div>
	 </div>
</form>
</body>
</html>
