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
	<fieldset style="border: 1px solid #b8d0d6;height: 320px;width: 470px;margin-left: 30%;margin-top:10%;">
	<form method="post" action="${CTX }/merchant/doMerchantRecharge.do" class="pageForm required-validate" onsubmit="return validateCallback(this,navTabAjaxDone);">
		<p align="center"><font><b>充值</b></font></p>
		<div class="divider"></div>
		<div class="pageFormContent" layoutH="56" style="margin-left: 8%;">
			<p>
				<label>类型：</label>
				<select  id="changeRechargeType" style="width: 150px;">
					<option value="0">手动</option>
					<option value="1" selected="selected">刷卡</option>
				</select>
			</p>
			<p>
				<label>卡号：</label>
				<input type="text"  name="cardNo" id="recharge-cardNo" class="required number" readonly="readonly"   size="20" />
				<label class="rechargeSpan">
					<input type="button" id="recharge-checkCardNo" style="display: none;"  value="查询" />
					<input type="button" id="recharge-readCardNo"  value="读卡" />
				</label>
			</p>
			<!-- <div class="divider"></div> -->
			<p >
				<label>当前金额：</label>
				<input type="text"  readonly="readonly"   id="recharge-currentBalance" class="required"   size="20" />
			</p>
			<p >
				<label>充值金额：</label>
				<input type="text"  readonly="readonly"  name="balance" id="recharge-balance" class="required digits"   size="20" />
			</p>
			<p>
				<label>持卡人：</label>
				<input type="text" id="recharge-bizName"  value="请点击刷卡或查询"  readonly="readonly"  style="border: 0px solid green;"   size="20" />
			</p>
			<!-- <div class="divider"></div> -->
			<p>
				<label>持卡人证件：</label>
				<input type="text"  id="recharge-identityCard" value="请点击刷卡或查询"  readonly="readonly" style="border: 0px solid green;"  size="20" />
			</p>
			
			<c:if test="${not empty balance }">
				<p style="margin-left: 25%;margin-top: 5%;" id="recharge-current-balance">
					<input type="text"  id="recharge-result" class="balance" value="${balance }元"  readonly="readonly" style="border: 0px solid blue;font-size: 16px;font-weight: bold;"  size="20" />
				</p>
			</c:if>
			
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
	//改变类型时
	var $changeRechargeType=$("#changeRechargeType").change(function(){
		var rechargeType=$(this).val();
		//手动
		if(rechargeType=="0"){
			$("#recharge-checkCardNo").css("display","block");
			$("#recharge-readCardNo").css("display","none");
			$("#recharge-balance,#recharge-cardNo").removeAttr("readonly");
		}else{
			//刷卡
			$("#recharge-checkCardNo").css("display","none");
			$("#recharge-readCardNo").css("display","block");
			$("#recharge-balance,#recharge-cardNo").attr("readonly",true);
		}
	});
	
	//查询卡信息时
	var $getMerchantInfo=$("#recharge-checkCardNo").click(function(){
		$("#recharge-current-balance").css("display","none");
		var cardNo=$("#recharge-cardNo").val();
		if(cardNo==""){
			alertMsg.error('请输入卡号')
		}else{
			//发送AJAX请求
			$.post("${CTX}/merchant/getMerchantByCardNo.do",{cardNo:cardNo},function(data){
				if(data.status=="200"){
					//卡状态
					var cardStatus=data.cardStatus;
					if(cardStatus=="0"){
						$("#recharge-bizName").val(data.bizName);
						$("#recharge-identityCard").val(data.identityCard);
						$("#recharge-currentBalance").val(data.currentBalance);
						$("#recharge-balance").removeAttr("readonly");
						//alertMsg.correct('查询成功!');
					}else if(cardStatus=="1"){
						alertMsg.error('该卡已挂失!');
						$("#recharge-balance").attr("readonly",true);
					}else {
						alertMsg.error('该卡已注销!');
						$("#recharge-balance").attr("readonly",true);
					}
				}else {
					alertMsg.error('该卡已被解除绑定!');
					$("#recharge-balance").attr("readonly",true);
				}
			},"json");
		}
	});
	
	//读卡操作加载信息
	var $readCard=$("#recharge-readCardNo").click(function(){
		//隐藏余额
		$("#recharge-current-balance").css("display","none");
		//开始读卡
		$.readuser(function(data) {
			if (data.result == "success") {
				var cardNo=data.data['cardNumber'];
				$("input[name='cardNo']").val(cardNo);
				//根据卡号发送AJAX请求
				$.post("${CTX}/merchant/getMerchantByCardNo.do",{cardNo:cardNo},function(data){
					if(data.status=="200"){
						//卡状态
						var cardStatus=data.cardStatus;
						if(cardStatus=="0"){
							$("#recharge-bizName").val(data.bizName);
							$("#recharge-identityCard").val(data.identityCard);
							$("#recharge-currentBalance").val(data.currentBalance);
							$("#recharge-balance").removeAttr("readonly");
							//alertMsg.correct('查询成功!');
						}else if(cardStatus=="1"){
							alertMsg.error('该卡已挂失!');
							$("#recharge-balance").attr("readonly",true);
						}else{
							alertMsg.error('该卡已注销!');
							$("#recharge-balance").attr("readonly",true);
						}
					}else{
						alertMsg.error('该卡已被解除绑定!');
						$("#recharge-balance").attr("readonly",true);
					}
				},"json");
				//$("#recharge-bizName").val(data.data['bizName']);
				//$("#recharge-identityCard").val(data.data['identityCard']);
			} else {
				alertMsg.error(data.data);
			}
		});
	});
});
</script>