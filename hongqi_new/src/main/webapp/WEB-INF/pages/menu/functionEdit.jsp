<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp" %>
<div class="pageContent">
	<form method="post" action="${CTX}/menu/doEditFunction.do" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>功能名称：</label>
				<input type="text" class="required" maxlength="40" name="functionName"  size="30" value="${function.functionName }"/>
			</p>
			<p>
				<label>地址(url)：</label>
				<input type="text" maxlength="100" maxlength="100" class="required" name="url"  size="30" value="${function.url}"/>
			</p>
			<p>
				<label>描述：</label>
				<input type="text" class="required" maxlength="200" name="description"  size="30" value="${function.description}" />
			</p>
			<div class="divider"></div>
		</div>
		<div class="formBar">
			<ul>
			    <li><input type="hidden" name="functionId" id="functionId" value="${function.functionId}" /></li>
			    <li><input type="hidden" name="menuId" id="menuId" value="${function.menu.menuId}" /></li>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>