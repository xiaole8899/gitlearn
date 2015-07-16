<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp" %>
<div class="pageContent">
	<form method="post" action="${CTX }/ews/doUpdateEws.do" class="pageForm required-validate" rel="dfghjkl" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
		    <input type="hidden" name="ewsId" value="${ews.ewsId}">
		    <input type="hidden" name="salerName" value="${ews.salerName}" size="30" />
		    <input type="hidden" id="salerNumber" name="salerNumber" value="${ews.salerNumber}" size="30"  />
		    <input type="hidden" name="date" class="date" dateFmt="yyyy-MM-dd HH:mm:ss" value="<fmt:formatDate value="${ews.bindTime }" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			<%-- <p>
				<label>商户名称：</label>
				<input type="text" class="required" name="salerName" value="${ews.salerName}" size="30" />
			</p>
			<p>
				<label>商户编码：</label>
				<input type="text" class="required number" id="salerNumber" name="salerNumber" value="${ews.salerNumber}" size="30"  />
			</p> --%>
			<p>
				<label>SN号：</label>
				<input type="text" class="required" name="ewsSn" value="${ews.ewsSn}"  size="30">
			</p>
			 <p>
				<label>电子秤IP地址：</label>
				<input type="text"  name="ewsIp" size="30" value="${ews.ewsIp}" />
			</p>
			<p>
				<label>电子秤MAC地址：</label>
				<input type="text"  name="ewsMac"  size="30" value="${ews.ewsMac}">
			</p>
			<%-- <p>
				<label>绑定时间：</label>
				<input type="text" name="date" class="date" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" size="28" value="<fmt:formatDate value="${ews.bindTime }" pattern="yyyy-MM-dd 
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