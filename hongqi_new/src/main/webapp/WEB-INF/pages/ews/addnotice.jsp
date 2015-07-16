<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp" %>
<div class="pageContent">
	<form method="post" action="${CTX }/ews/doAddNotice.do" class="pageForm required-validate" rel="dfghjkl" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>公告时间：</label>
				<input type="text" name="date" class="date" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" size="28" />
				<a class="inputDateButton" href="javascript:;">选择</a>
			</p>
			<div class="divider"></div>
			<p>
				<label>公告内容：</label>
				<!-- <input type="textaer" class="required " id="content" name="content" size="30"  /> -->
				<textarea class="required"  name="content" rows="5" cols="63" ></textarea>
				<!-- <textarea name="content" class="required " cols="40" rows="3" ></textarea> -->
				<!-- <textarea rows="3" cols="17" readonly="readonly" style="border: 0px solid;color:red;margin-left: 57px"></textarea> -->
			</p>
			
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