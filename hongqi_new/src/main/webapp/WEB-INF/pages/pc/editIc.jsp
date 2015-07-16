<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp" %>
<div class="pageContent">
	<form method="post" action="${CTX }/ic/doUpdateIc.do" class="pageForm required-validate" rel="dfghjkl" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
		    <input type="hidden" name="icId" value="${ic.icId}">
		    <input type="hidden" name="date" class="date" dateFmt="yyyy-MM-dd HH:mm:ss" value="<fmt:formatDate value="${ic.inDate }" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			
			
			<p>
				<label>IC卡读写器名称：</label>
				<input type="text" class="required" name="icName" value="${ic.icName}" size="30" />
				<%-- <textarea class="required" name="content" rows="6" cols="65" >${notice.content}</textarea> --%>
			</p>
			<%-- <p>
				<label>时间：</label>
				<input type="text" name="date" class="date" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" size="28" value="<fmt:formatDate value="${ic.inDate }" pattern="yyyy-MM-dd 
HH:mm:ss"/>"/>
				<a class="inputDateButton" href="javascript:;">选择</a>
			</p> --%>
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