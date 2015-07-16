<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp" %>

<div class="pageContent">
	<form method="post" action="${CTX }/user/doRoleAdd.do" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input type="hidden" id="userId"  name="userId" value="${userId}">
		<div class="pageFormContent" layoutH="56">
			<h1>请选择您要添加的角色：</h1><br>
			<div style="display:block; height:50px;">
				<table class="table" width="560">
					<thead>
						<tr>
							<th width="40">请选择</th>
							<th width="40">角色名称</th>
							<th width="40">描述</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="roleall" items="${roleall}" >
						<tr>
							<td align="center">
								<input type="checkbox" id="roleId" name="roleIds"
								<c:forEach var="roleyet" items="${roleyet}" >
									<c:if test="${roleall.roleId==roleyet.roleId}"> checked="checked" </c:if>
								</c:forEach>
								 value="${roleall.roleId}"/>
							</td>
							<td align="center">${roleall.roleName }</td>
							<td align="center">${roleall.descriptions}</td>
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