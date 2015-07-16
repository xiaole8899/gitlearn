<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp" %>


<style type="text/css">
	fieldset input{
		color: green;
	}
</style>
<script type="text/javascript">
	$(function(){
		//当点击确认开卡时发送AJAX请求
		var addCardButtonClick=$("#addCardButton").click(function(){
			
			//开卡之前先验证卡号是否为空
			var cardNo=$("#add-card-cardNo").val();
			if(cardNo==""){
				alertMsg.error('请输入卡号')
			}else{
				var bizNo=$("#add-card-bizNo").val(); //商户备案号
				var cardType='03';  //卡类型
				var cardNumber=$("#add-card-cardNo").val(); //卡号
				var phone=$("#add-card-tel").val(); //联系电话
				var identityCard=$("#add-card-identityCard").val(); //身份证号码
				var bizName=$("#add-card-bizName").val();          //联系人
				var userType='3';         //用户类型
				var mainName=$("#add-card-mainName").val();       //主体名称
				var cardStr={bizNo:bizNo,cardType:cardType,
						cardNumber:cardNumber,phone:phone,
						identityCard:identityCard,bizName:bizName,
						userType:userType,mainName:mainName
				};
				//alert("bizNo"+bizNo+"cardType"+cardType+"cardNumber"+cardNumber+"phone"+phone+"identityCard"+identityCard+"bizName"+bizName+"mainName"+mainName);
				//写卡操作
				$.writeuser(function(data){
					if (data.result == "success") {
						alertMsg.correct('成功!');
						$("#addCard-formSubmit").submit();
					} else {
						alert(data.data);
					}	
				},cardStr);
			}
		});
	});
</script>


<div>
	<fieldset style="border: 1px solid #b8d0d6;height: 290px;width: 520px;margin-left: 25%;margin-top:12%;">
	<form id="addCard-formSubmit" method="post" action="${CTX }/merchant/doAddCard.do" class="pageForm required-validate" onsubmit="return validateCallback(this,navTabAjaxDone);">
		<p align="center"><font><b>开卡</b></font></p>
		<div class="divider"></div>
		<div class="pageFormContent">
			<p>
				<label style="width:125px;" >商户名称:</label>
 				<input type="hidden"  name="bizLookup.bizId" value=""/>
				<input type="text" style="margin-left: -1%" size="25"  id="add-card-mainName" readonly="readonly" class="required" name="bizLookup.comName" value="" suggestFields="bizNo,bizName,businessType" suggestUrl="${CTX }/approach/findMerchant.do" lookupGroup="bizLookup" />
				<a class="btnLook" href="${CTX }/merchant/getNocardMerchant.do"  lookupGroup="bizLookup" width="1000" rel="openCard-BizMenchant">查找带回</a>		
			</p>
			<p>
				<label>持卡人：</label>
				<input type="text" name="bizLookup.bizName"  id="add-card-bizName"  readonly="readonly"  size="30" />
			</p>
			<p>
				<label>持卡人证件：</label>
				<input type="text" name="bizLookup.identityCard" id="add-card-identityCard"   readonly="readonly"  size="30" />
			</p>
			<p>
				<label>联系电话：</label>
				<input type="text" name="bizLookup.tel" id="add-card-tel"   readonly="readonly"  size="30" />
			</p>
			<p>
				<label>商户备案号：</label>
				<input type="text" name="bizLookup.bizNo" id="add-card-bizNo"  readonly="readonly"  size="30" />
			</p>
			<p>
				<label>卡号：</label>
				<input type="text" class="required number" name="cardNo" id="add-card-cardNo" size="30" />
			</p>
			<p>
				<label>卡类型：</label>
				<input type="hidden" name="cardType"  readonly="readonly"  size="30" value="01" />
				<input type="text" readonly="readonly"  size="30"  value="服务卡" />
			</p>
			
			<div style="padding-left: 77%;padding-top: 40%;">
				<div class="buttonActive"><div class="buttonContent"><button type="button" id="addCardButton">确定</button></div></div>
				<div style="margin-left: 10px;" class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
			</div>
		</div>
	</form>
	</fieldset>
</div>
