<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp" %>
<div class="pageContent">
	<form method="post" action="${CTX }/user/doAdd.do" class="pageForm required-validate" rel="userdoadd" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>用户名：</label>
				<input type="text" class="required alphanumeric" name="userName" size="30" maxlength="30" />
			</p>
			<p>
				<label>密码：</label>
				<input type="password" class="required password" id="password" name="passWord" size="30"  minlength="6" maxlength="20" />
			</p>
			<p>
				<label>确认密码：</label>
				<input type="password" class="required password" class="required" equalto="#password"  size="30"  />
			</p>
			 <p>
				<label>真实姓名：</label>
				<input type="text" class="required" name="realName" size="30" maxlength="30"/>
			</p>
			<p>
				<label>部门名称：</label>
				<input type="text" class="required" name="department"  size="30" maxlength="100"/>
			</p>
			<p>
				<label>职位：</label>
				<input type="text" class="required" name="position"  size="30" maxlength="80">
			</p>
			<p>
				<label>手机：</label>
				<input type="text" name="mobilePhone" class="tel"  size="30">
			</p>
			<p>
				<label>联系电话：</label>
				<input type="text" class="telephone" name="telephone" size="30" />
			</p>
			<p>
				<label>电子邮箱：</label>
				<input type="text" name="email" class="email textInput" size="30" />
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