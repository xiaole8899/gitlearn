<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp" %>

<style type="text/css">
	.rechargeSpan{
		margin-left: 75%;
		margin-top: -5.5%;
		
	}
	fieldset input{
		color: green;
	}
	
	.balance{
		color: green;
	}
</style>

<div>
	<fieldset style="border: 1px solid #b8d0d6;height: 420px;width: 500px;margin-left: 30%;margin-top:10%;">
	<form method="post" action="${CTX }/merchant/doAddMerchantByCard.do" class="pageForm required-validate" onsubmit="return validateCallback(this,navTabAjaxDone);">
		<p align="center"><font><b>商户开卡备案</b></font></p>
		<div class="divider"></div>
		<div class="pageFormContent" layoutH="56" style="margin-left: 8%;">
			<p>
				<label>卡号：</label>
				<input type="text"  name="cardNo"   class="required number" readonly="readonly"   size="21" />
				<label class="rechargeSpan">
					<input type="button" style="margin-left: 5%;" id="addmerchantbycard-readCardNo"  value="读卡" />
				</label>
			</p>
			<p>
				<label>经营类型：</label>
				<select name="bizType" class="required" style="width: 210px;">
					<option value="">请选择：</option>
					<c:forEach items="${bizTypeList }" var="bt">
							<option value="${bt.key }">${bt.value }</option>
					</c:forEach>
				</select>
			</p>
			<p>
				<label id="add-merchant-name">商户备案号：</label>
				<input type="text" class="required" name="bizNo" readonly="readonly" size="30" />
			</p>
			<p>
				<label id="add-merchant-name">企业名称：</label>
				<input type="text" class="required" name="comName" readonly="readonly" size="30" />
			</p>
			
			<p>
				<label>持卡人：</label>
				<input type="text" class="required" name="bizName" readonly="readonly" size="30" />
			</p>
			
			<p>
				<label>身份证号：</label>
				<input type="text" autocomplete="off" value="" readonly="readonly"   name="identityCard" class="required idCard" size="30" />
			</p>
			
			<p>
				<label>法人代表：</label>
				<input type="text" class="required" readonly="readonly"  name="legalRepresent" size="30" />
			</p>
			<p >
				<label>工商登记号：</label>
				<input type="text" class="required number" autocomplete="off" value=""  name="regId" size="30" />
			</p>
			
			<p >
				<label>交易密码：</label>
				<input type="password" class="required" autocomplete="off" value=""  minlength="6"  name="tradePwd" size="30" />
			</p>
			<p>
				<label>联系电话：</label>
				<input type="text" class="required tel" name="tel" readonly="readonly" size="30" />
			</p>
			<p >
				<label>摊位编号：</label>
				<input type="text"  name="boothNo" size="30" />
			</p>
			<div style="float: right;margin-right: 2%;margin-top: 2%;">
				<div class="buttonActive"><div class="buttonContent"><button type="submit">确定</button></div></div>
				<div style="margin-left: 10px;" class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
			</div>
		</div>
	</form>
	</fieldset>
</div>
<script type="text/javascript">
$(function(){
	//读卡操作加载信息
	var $readCard=$("#addmerchantbycard-readCardNo").click(function(){
		//开始读卡
		$.readuser(function(data) {
			if (data.result == "success") {
				//卡号
				var cardNo=data.data['cardNumber'];
				//商户备案号
				var bizNo=data.data['bizNo'];
				//持卡人
				var bizName=data.data['bizName'];
				//企业名称
				var comName=data.data['mainName'];
				//身份证号
				var identityCard=data.data['identityCard'];
				//电话
				var tel=data.data['phone'];
				$("input[name='cardNo']").val(cardNo);
				$("input[name='bizNo']").val(bizNo);
				$("input[name='bizName']").val(bizName);
				$("input[name='legalRepresent']").val(bizName);
				$("input[name='comName']").val(comName);
				$("input[name='identityCard']").val(identityCard);
				$("input[name='tel']").val(tel);
			} else {
				alertMsg.error(data.data);
			}
		});
	});
});
</script>