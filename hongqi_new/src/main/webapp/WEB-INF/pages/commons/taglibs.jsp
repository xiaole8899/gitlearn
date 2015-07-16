<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%--自定义资源载入的标签 --%>
<%@ taglib uri="/dppc-tags" prefix="dppc" %>
<%-- JSTL 标准标签 --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%-- 在JSP里保存ContextPath 方便在Javascript代码里面明确请求URL --%>
<c:set value="${pageContext.servletContext.contextPath }" var="CTX" scope="request"/>