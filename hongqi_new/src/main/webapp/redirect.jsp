<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	请稍后，正在转向首页 ...
	<%-- 因为把spring-mvc 配置成了传统的 *.do 有后缀的模式 所以这里选择利用 redirect.jsp 进行首页跳转 --%>
	<% response.sendRedirect("toLogin.do"); %>
</body>
</html>