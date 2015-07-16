<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp" %>

<div class="pageContent">
	<form method="post" action="${CTX }/merchant/doAddMerchant.do" class="pageForm required-validate" id="addMerchant" onsubmit="return validateCallback(this,navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label id="add-merchant-name">注册(字号)名称：</label>
				<input type="text" class="required" name="comName" size="30" />
			</p>
			
			
			<p  style="margin-left: 20%">
				<label>商户性质：</label>
				<input type="radio" checked="checked" class="required" name="property" value="个体" size="5" />个体
				<input type="radio" class="required" name="property"  value="企业" size="5" />企业
			</p>
			<div class="divider"></div>
			
			<p>
				<label>持卡人：</label>
				<input type="text" class="required" name="bizName" size="30" />
			</p>
			
			<p style="margin-left: 20%">
				<label>商户类型：</label>
				<select name="businessType" class="required combox">
					<option value="">请选择：</option>
					<c:forEach items="${businessTypeList }" var="btype">
							<option value="${btype.key }">${btype.value }</option>
					</c:forEach>
				</select>
			</p>
			<div class="divider"></div>
			<p id="addSfzDiv" >
				<label>身份证号：</label>
				<input type="text" autocomplete="off" value=""   name="identityCard" class="required idCard" size="30" />
			</p>
			
			<p style="margin-left: 20%">
				<label>法人代表：</label>
				<input type="text" class="required"  name="legalRepresent" size="30" />
			</p>
			<div class="divider"></div>
			<p >
				<label>工商登记号：</label>
				<input type="text" class="required number" autocomplete="off" value=""  name="regId" size="30" />
			</p>
			
			<p style="margin-left: 20%">
				<label>经营类型：</label>
				<select name="bizType" class="required combox">
					<option value="">请选择：</option>
					<c:forEach items="${bizTypeList }" var="bt">
							<option value="${bt.key }">${bt.value }</option>
					</c:forEach>
				</select>
			</p>
			<div class="divider"></div>
			<p >
				<label>交易密码：</label>
				<input type="password" class="required" autocomplete="off" value=""  minlength="6"  name="tradePwd" size="30" />
			</p>
			<p style="margin-left: 20%">
				<label>联系电话：</label>
				<input type="text" class="required tel" name="tel" size="30" />
			</p>
			<div class="divider"></div>
			<p >
				<label>摊位编号：</label>
				<input type="text"  name="boothNo" size="30" />
			</p>
			
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit" id="addMerchant">确定</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">
var $addMerchantChangeProperty=$("input[name='property']").change(function(){
	if($(this).val()=="个体"){
		$("#add-merchant-name").text("注册(字号)名称：");
	}else{
		$("#add-merchant-name").text("企业名称：");
	}
});
</script>