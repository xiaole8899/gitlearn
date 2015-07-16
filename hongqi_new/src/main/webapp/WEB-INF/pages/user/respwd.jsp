<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp" %>
<div class="pageContent">
	<form method="post" action="${CTX }/user/doResPwd.do" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<%-- <input type="hidden" name="userId" value="${user.userId}"> --%>
			<p>
				<label>原密码：</label>
				<input type="password" id="oldpassword" class="required password" name="oldPassWord" value="" size="18" onclick="this.value=''"/>
			</p>
			<p style="margin-left: 20%">
				<label>新密码：</label>
				<input type="password" id="newpassword" class="required password" name="newPassWord" value="" size="18"/>
			</p>
			<div class="divider"></div>
			<p>
				<label>确认新密码：</label>
				<input type="password" id="resnewpassword" class="required password" name="resNewPassword" value="" size="18" />
			</p>
			<div class="divider"></div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button id="repwd_button" type="submit">确定</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">
	$(function() {
		$("#repwd_button").click(function() {
			if($("#newpassword").val()==$("#resnewpassword").val()){
				return true;
			}else{
				alertMsg.error("两次输入密码不相同");
				return false;
			}
		});
	});
</script>