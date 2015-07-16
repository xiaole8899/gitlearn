<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp" %>
<style type="text/css">
	fieldset input{
		color: green;
	}
</style>
<div>
	<fieldset style="border: 1px solid #b8d0d6;height: 290px;width: 520px;margin-left: 25%;margin-top:12%;">
	<form id="addCard-formSubmit" method="post" action="${CTX }/costs/doAddCost.do" class="pageForm required-validate" onsubmit="return validateCallback(this,navTabAjaxDone);">
		<p align="center"><font><b>新增费用规则</b></font></p>
		<div class="divider"></div>
		<div class="pageFormContent">
			<p>
				<label style="width:125px;" >费用名称：</label>
				<input type="text" style="margin-left: -1%" size="30"  name="costsName"  class="required"   />
			</p>
			<p>
				<label>适用范围</label>
				<input type="radio" checked="checked"  name="add-costs-syfw" value="all" /> 所有商品
				<input type="radio" name="add-costs-syfw" value="nall" />个别商品
			</p>
			<p style="display: none;" id="add-costs-goodsId">
				<label>适用商品编号：</label>
				<input type="text"   name="costs.goodsCode"  readonly="readonly"  size="25" />
				<a class="btnLook" href="${CTX }/costs/lookUpGoods.do" rel="add-cost-lookupgoods" lookupGroup="costs">查找带回</a>
			</p>
			<p style="display: none;" id="add-costs-goodsName">
				<label>适用商品名称：</label>
				<input type="text" name="costs.goodsName" readonly="readonly"     size="30" />
			</p>
			<p>
				<label>卖方定额</label>
				<input type="text" name="sellerQuota"  value="0" size="30" />
				<span style="color: red;">(元)</span>
			</p>
			<p>
				<label>卖方比例</label>
				<input type="text" name="sellerRate"    value="0" size="30" />
				<span style="color: red;">%</span>
			</p>
			<p>
				<label>买方定额</label>
				<input type="text" name="buyerQuota"    value="0"  size="30" />
				<span style="color: red;">(元)</span>
			</p>
			<p>
				<label>买方比例</label>
				<input type="text" name="buyerRate"   value="0"  size="30" />
				<span style="color: red;">%</span>
			</p>
			
			<div style="padding-left: 77%;padding-top: 40%;">
				<div class="buttonActive"><div class="buttonContent"><button type="submit" id="addCardButton">确定</button></div></div>
				<div style="margin-left: 10px;" class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
			</div>
		</div>
	</form>
	</fieldset>
</div>

<script type="text/javascript">
$(function(){
	//改变商品商品适用范围
	var changeSYFWRadio=$("input[name='add-costs-syfw']").change(function(){
		if($(this).val()=="all"){
			$("#add-costs-goodsId,#add-costs-goodsName").css("display","none");
			$("input[name='costs.goodsCode'],input[name='costs.goodsName']").removeClass("required");
			$("input[name='costs.goodsCode'],input[name='costs.goodsName']").val("");
		}else{
			$("#add-costs-goodsId,#add-costs-goodsName").css("display","block");
			$("input[name='costs.goodsCode'],input[name='costs.goodsName']").addClass("required");
		}
	});
});
</script>
