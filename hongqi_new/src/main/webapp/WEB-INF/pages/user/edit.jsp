<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp" %>
<script type="text/javascript">
var SL_USER_NP={
	see : function () {
		$("#user_password").removeAttr("disabled").val("");
		$("#user_bz").val(true);
	},
	blur: function() {
		var $pwd =$("#user_password");
		if ($pwd.val() == "") {
			$pwd.val($("#user_src_pwd").val()).attr("disabled", "disabled");
			$("#user_bz").val("false")
		}
	}
}; 
</script> 
<div class="pageContent">
	<form method="post" action="${CTX }/user/doEdit.do" class="pageForm required-validate" rel="userdoadd" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input type="hidden" name="userId" value="${user.userId}"/>
		<input type="hidden" name="passWord1" id="user_src_pwd" value="${user.passWord}"/>
		<input type="hidden" name="bz" id="user_bz" value="false"/>
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>用户名：</label>
				<input type="text" class="required" name="userName" value="${user.userName}" size="30" />
			</p>
			<p>
				<label>密码：</label>
				<input type="button" value="编辑密码" onclick="SL_USER_NP.see();"/>
				<input  onblur="SL_USER_NP.blur();" type="password" id="user_password" name="passWord" disabled="disabled" class="required password"  value="${user.passWord}" size="18"/>
			</p>
			<p>
				<label>真实姓名：</label>
				<input type="text" class="required" name="realName" value="${user.realName}" size="30" />
			</p>
			<p>
				<label>部门名称：</label>
				<input type="text" class="required" name="department" value="${user.department}"  size="30">
			</p>
			<p>
				<label>职位：</label>
				<input type="text" class="required" name="position" value="${user.position}"  size="30">
			</p>
			<p>
				<label>手机：</label>
				<input type="text" name="mobilePhone" class="tel" value="${user.mobilePhone}"  size="30">
			</p>
			<p>
				<label>联系电话：</label>
				<input type="text" class="telephone" name="telephone" value="${user.telephone}" size="30" />
			</p>
			<p>
				<label>电子邮箱：</label>
				<input type="text" name="email" class="email textInput" value="${user.email}" size="30" />
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