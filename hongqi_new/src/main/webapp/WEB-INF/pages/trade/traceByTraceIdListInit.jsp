<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/kit/dwz/js/dwz.resetformvalue.js"></script>	

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<%@ include file="../commons/taglibs.jsp" %>


<div class="pageHeader">
  		<form rel="dd" id="list_trade" onsubmit="return navTabSearch(this);" action="${CTX }/trade/dotraceByTraceIdList.do" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>追溯码：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" class="checkReset" name="traceId" /></td>
				</tr>
			 	
			
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit" >预览</button></div></div></li>
					<li><div class="buttonActive"><div class="buttonContent"><button class='resetForm'>重置</button></div></div></li>
				</ul>
			</div>
		</div>	
	</form>
</div>
<div class="pageContent" >

	
</div>
