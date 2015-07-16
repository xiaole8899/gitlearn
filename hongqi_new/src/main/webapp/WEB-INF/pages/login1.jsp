<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="commons/taglibs.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>登陆页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">  
	<fatty:resource path="js/jquery-1.7.2.min.js"/>

	<script type="text/javascript">
	$(function() {
		$("input").focus(function() {
			$("legend").html("登陆");
		});
		
		var $loginForm =$("#loginForm").subumit(function() {
			$("#submit", $(this)).attr("disabled", "disabled");
		});
		// 绑定回车提交表单
		$(document).keydown(function(event) {
			if (event.keyCode == 13) {
				$loginForm.submit();
			}
		});
		
		
	});
	</script>  
  </head>
  
  <body>
  	<form id="loginForm" action="${CTX }/doLogin.do" method="post" style="width:350px;margin: 100px auto 0">
  		<fieldset style="background: url(images/logo.png) no-repeat;">
  			<c:if test="${empty loginError }">
  			<legend>登陆</legend>
  			</c:if>
  			<c:if test="${not empty loginError }">
  			<legend><span style="color: red;">${loginError }</span></legend>
  			</c:if>
  			<li>
  				<label for="username">账号：</label>
  				<input type="text" name="userName" id="username" alt="账号" />
  			</li>
  			<li>
  				<label for="password">密码：</label>
  			    <input type="password" name="passWord" id="password" alt="密码" /> 
  			</li>
  			<li>  			
  				<input type="submit" value="提交" style="display: inline-block; float: right;"/>
  			</li>
  		</fieldset>
  	</form>
  </body>
</html>
