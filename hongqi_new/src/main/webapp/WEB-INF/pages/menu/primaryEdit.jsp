<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp" %>
<div class="pageContent">
	<form method="post" action="${CTX}/menu/doPrimaryEdit.do" class="pageForm required-validate" rel="menudoadd" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>菜单名：</label>
				<input type="text" class="required" name="menuName" maxlength="40" value="${primaryMenu.menuName }" size="30" />
			</p>
			<p>
				<label>菜单地址(url)：</label>
				<input type="text" class="required" name="url" maxlength="80" value="${primaryMenu.url }" size="30" />
			</p>
			<p>
				<label>描述：</label>
				<input type="text" class="required" name="description" maxlength="80" value="${primaryMenu.description }" size="30" />
			</p>
			<p>
				<label>排序：</label>
				<input type="text" class="required number" name="sort"  size="30" value="${primaryMenu.sort }"/>
			</p>
			<div class="divider"></div>
		</div>
		<div class="formBar">
			<ul>
			    <li><input type="hidden" name="parentId" id="parentId" value="${primaryMenu.parentId}" /></li>
			    
			    <li><input type="hidden" name="menuId" id="menuId" value="${primaryMenu.menuId}" /></li>
			    
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>