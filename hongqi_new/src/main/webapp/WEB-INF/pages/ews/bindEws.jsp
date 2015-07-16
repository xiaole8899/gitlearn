<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp" %>

<div class="pageContent">
	<form method="post" action="${CTX }/ews/doBindEws.do" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input type="hidden" id="fecId"  name="fecId" value="${fecId}">
		<div class="pageFormContent" layoutH="56">
			<h1>请选择您要添加的电子秤：</h1><br>
			<div style="display:block; height:50px;">
				<table class="table" width="560">
					<thead>
						<tr>
							<th width="40">请选择</th>
							<th width="40">电子秤SN号</th>
							<th width="40">电子秤IP</th>
							<th width="40">商户编号</th>
							<th width="40">商户名称</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="ewsall" items="${ewsall}" >
						<tr>
							<td align="center">
								<input type="checkbox" id="ewsId" name="ewsIds"
								<c:forEach var="ewsyet" items="${ewsyet}" >
									<c:if test="${ewsall.ewsId==ewsyet.ewsId}"> checked="checked" </c:if>
								</c:forEach>
								 value="${ewsall.ewsId}"/>
							</td>
							<td align="center">${ewsall.ewsSn }</td>
							<td align="center">${ewsall.ewsIp}</td>
							<td align="center">${ewsall.salerNumber}</td>
							<td align="center">${ewsall.salerName}</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
			
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button id="roleadd_btn" type="submit">确定</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>