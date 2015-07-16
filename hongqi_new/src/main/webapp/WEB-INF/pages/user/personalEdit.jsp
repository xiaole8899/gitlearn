<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp" %>
<div class="pageContent">
	<form method="post" action="${CTX }/user/doPersonalEdit.do" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<input type="hidden" name="userId" value="${user.userId}">
			<input type="hidden" name="passWord" value="${user.passWord}">
			<input type="hidden" name="bz" id="bz" value="false">
			<p>
				<label>用户名：</label>
				<input type="text" class="required" name="userName" value="${user.userName}" size="30" />
			</p>
			<p style="margin-left: 20%">
				<label>真实姓名：</label>
				<input type="text" class="required" name="realName" value="${user.realName}" size="30" />
			</p>
			<div class="divider"></div>
			<p>
				<label>部门名称：</label>
				<input type="text" class="required" name="department" value="${user.department}"  size="30">
			</p>
			<p style="margin-left: 20%">
				<label>职位：</label>
				<input type="text" class="required" name="position" value="${user.position}"  size="30">
			</p>
			<div class="divider"></div>
			<p>
				<label>手机：</label>
				<input type="text" name="mobilePhone" value="${user.mobilePhone}"  size="30">
			</p>
			<p style="margin-left: 20%">
				<label>联系电话：</label>
				<input type="text" class="telephone" name="telephone" value="${user.telephone}" size="30" />
			</p>
			<div class="divider"></div>
			<p>
				<label>电子邮箱：</label>
				<input type="text" name="email" class="email textInput" value="${user.email}" size="30" />
			</p> 
			<div class="divider"></div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>