<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp" %>

<div class="pageContent">
	<form method="post" action="${CTX }/ews/doBindFec.do" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input type="hidden" id="ewsId"  name="ewsId" value="${ewsId}">
		<div class="pageFormContent" layoutH="56">
			<h1>请选择您要添加的前置机：</h1><br>
			<div style="display:block; height:50px;">
				<table class="table" width="560">
					<thead>
						<tr>
							<th width="40">请选择</th>
							<th width="40">前置机IP</th>
							<th width="40">前置机端口</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="fecall" items="${fecall}" >
						<tr>
							<td align="center">
								<input type="checkbox" id="fecId" name="fecIds"
								<c:forEach var="fecyet" items="${fecyet}" >
									<c:if test="${fecall.fecId==fecyet.fecId}"> checked="checked" </c:if>
								</c:forEach>
								 value="${fecall.fecId}"/>
							</td>
							<td align="center">${fecall.fecIp }</td>
							<td align="center">${fecall.port}</td>
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