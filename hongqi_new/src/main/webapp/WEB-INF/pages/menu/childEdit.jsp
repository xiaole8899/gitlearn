<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp" %>
<div class="pageContent">
	<form method="post" action="${CTX}/menu/doChildEdit.do" class="pageForm required-validate" rel="menudoadd" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>菜单名：</label>
				<input type="text" class="required" name="menuName" maxlength="40" value="${childMenu.menuName }" size="30" />
			</p>
			<p>
				<label>菜单地址(url)：</label>
				<input type="text" class="required" maxlength="50" size="30" name="url" value="${childMenu.url }" size="30" />
			</p>
			<p>
				<label>标签名：</label>
				<input type="text" class="required" name="rel" maxlength="40"   size="30" value="${childMenu.rel} " />
			</p>
			<p>
				<label>描述：</label>
				<input type="text" class="required" maxlength="80" name="description" value="${childMenu.description }" size="30" />
			</p>
			<p>
				<label>排序：</label>
				<input type="text" class="required number" name="sort" value="${childMenu.sort }" size="30" />
			</p>
			<div class="divider"></div>
		</div>
		<div class="formBar">
			<ul>
			    <li><input type="hidden" name="menuId" id="menuId" value="${childMenu.menuId}" /></li>
			    <li><input type="hidden" name="parentId" id="parentId" value="${childMenu.parentId}" /></li>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>