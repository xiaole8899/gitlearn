<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp" %>
<div class="pageContent">
	<form method="post" action="${CTX }/pc/doUpdatePc.do" class="pageForm required-validate" rel="dfghjkl" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
		    <input type="hidden" name="pcId" value="${pc.pcId}">
		    <input type="hidden" name="date" class="date" dateFmt="yyyy-MM-dd HH:mm:ss" value="<fmt:formatDate value="${pc.inDate }" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
			
			<p>
				<label>IP地址：</label>
				<input type="text" class="required ip" name="pcIp" value="${pc.pcIp}"  size="30">
			</p>
			 <p>
				<label>MAC地址：</label>
				<input type="text" class="required" name="mac" value="${pc.mac}" size="30" />
			</p>
			<p>
				<label>主板型号：</label>
				<input type="text" class="" name="mainBoard" value="${pc.mainBoard}" size="30" />
			</p>
			<p>
				<label>显卡型号：</label>
				<input type="text" class="" name="vgaDriver" value="${pc.vgaDriver}" size="30" />
			</p>
			<p>
				<label>CPU：</label>
				<input type="text" class="" name="cpu" value="${pc.cpu}" size="30" />
			</p>
			<p>
				<label>内存：</label>
				<input type="text" class="" name="memoryBank" value="${pc.memoryBank}"  size="30" />
			</p>
			<p>
				<label>显示器：</label>
				<input type="text" class="" name="monito" value="${pc.mainBoard}" size="30" />
			</p>
			<%-- <p>
				<label>键盘：</label>
				<input type="text" class="" name="keyBoard" value="${pc.keyBoard}"  size="30" />
			</p>
			<p>
				<label>鼠标：</label>
				<input type="text" class="" name="mouse" value="${pc.mouse}"  size="30" />
			</p> --%>
			<%-- <p>
				<label>时间：</label>
				<input type="text" name="date" class="date" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" size="28" value="<fmt:formatDate value="${pc.inDate }" pattern="yyy-mm-dd HH:mm:ss" />" />
				<a class="inputDateButton" href="javascript:;">选择</a>
			</p> --%> 
			
			<%-- <p>
				<label>绑定时间：</label>
				<input type="text" name="date" class="date" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" size="28" value="<fmt:formatDate value="${fec.bindTime }" pattern="yyyy-MM-dd 
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