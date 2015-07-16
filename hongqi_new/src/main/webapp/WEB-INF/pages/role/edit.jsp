<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp" %>
<div class="pageContent">
	<form method="post" action="${CTX }/role/doEdit.do" rel="roledoedit" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
		<input type="hidden" name="roleId" value="${role.roleId}">
			<p>
				<label>角色名称：</label>
				<input name="roleName" class="required" type="text" size="30" value="${role.roleName}"/>
			</p>
			<p>
				<label>角色描述：</label>
				<input name="descriptions" type="text" size="30" value="${role.descriptions }"/>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>