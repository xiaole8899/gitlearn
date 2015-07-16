<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp" %>
<div class="pageContent">
	<form method="post" action="${CTX }/balance/doUpdateBlance.do" class="pageForm required-validate" rel="dfghjkl" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
		    <input type="hidden" name="baId" value="${balance.baId}">
		    <input type="hidden" name="bizId" value="${balance.bizId}">
		    <input type="hidden" name="bizName" value="${balance.bizName}">
		    <input type="hidden" name="boothNo" value="${balance.boothNo}">
		    <input type="hidden" name="date" class="date" dateFmt="yyyy-MM-dd HH:mm:ss" value="<fmt:formatDate value="${balance.boundTime }" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			<p>
				<label>小秤编号：</label>
				<input type="text" class="required" name="balanceNo" value="${balance.balanceNo}" size="30" />
			</p>
			
			
			<div class="divider"></div>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive"><div class="buttonContent"><button type="submit">确定</button></div></div>
				</li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
<%-- <div id="orgselects-useradd" style="width: 152px; height:200px;  overflow:auto; position: absolute; background-color: #FFF; outline: black solid 1px;">
	<emrp:treeFragment treeId="org-single-tree-useradd" show="orgName" root="${returnList }" childs="orList" nodeId="orgId"/>
</div> --%>