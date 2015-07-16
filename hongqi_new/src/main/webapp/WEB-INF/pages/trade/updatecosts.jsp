<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp" %>
<div class="pageContent">
	<form id="addCard-formSubmit" method="post" action="${CTX }/costs/doUpdateCost.do" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<input  value="${costs.costsId }" name="costsId" type="hidden" />
			<input  value="${costs.createTime }" name="createTime" type="hidden" />
			<input  value="${costs.userId }" name="userId"  type="hidden" />
			<input  value="${costs.usrName }" name="usrName" type="hidden" />
			<input  value="${costs.status }" name="status" type="hidden" />
			<p>
				<label style="width:125px;" >费用名称：</label>
				<input type="text" style="margin-left: -1%" size="30"  name="costsName"  class="required" value="${costs.costsName }"  />
			</p>
			<p>
				<label>适用范围：</label>
				<c:if test="${empty costs.goodsNames }">
					<input type="radio" checked="checked"  name="update-costs-syfw" value="all" /> 所有商品
					<input type="radio" name="update-costs-syfw" value="nall" />个别商品
				</c:if>
				<c:if test="${not empty costs.goodsNames }">
					<input type="radio"   name="update-costs-syfw" value="all" /> 所有商品
					<input type="radio" name="update-costs-syfw" checked="checked" value="nall" />个别商品
				</c:if>
			</p>
			<p  <c:if test="${empty costs.goodsNames }">style='display:none'</c:if>  id="update-costs-goodsId">
				<label>适用商品编号：</label>
				<input type="text" value="${costs.goodsCodes }" <c:if test="${not empty costs.goodsNames }">class='required'</c:if>  name="costs.goodsCode"  readonly="readonly"  size="25" />
				<a class="btnLook" href="${CTX }/costs/lookUpGoods.do" rel="update-cost-lookupgoods"  lookupGroup="costs">查找带回</a>
			</p>
			<p  <c:if test="${empty costs.goodsNames }">style='display:none'</c:if> id="update-costs-goodsName">
				<label>适用商品名称：</label>
				<input type="text" value="${costs.goodsNames }" <c:if test="${not empty costs.goodsNames }">class='required'</c:if>  name="costs.goodsName" readonly="readonly"     size="30" />
			</p>
			<p>
				<label>卖方定额</label>
				<input type="text" name="sellerQuota"  value="${costs.sellerQuota }" size="30" />
				<span style="color: red;">(元)</span>
			</p>
			<p>
				<label>卖方比例</label>
				<input type="text" name="sellerRate"    value="${costs.sellerRate }" size="30" />
				<span style="color: red;">%</span>
			</p>
			<p>
				<label>买方定额</label>
				<input type="text" name="buyerQuota"    value="${costs.sellerRate }"  size="30" />
				<span style="color: red;">(元)</span>
			</p>
			<p>
				<label>买方比例</label>
				<input type="text" name="buyerRate"   value="${costs.sellerRate }"  size="30" />
				<span style="color: red;">%</span>
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
$(function(){
	//改变商品商品适用范围
	var changeSYFWRadio=$("input[name='update-costs-syfw']").change(function(){
		if($(this).val()=="all"){
			$("#update-costs-goodsId,#update-costs-goodsName").css("display","none");
			$("input[name='costs.goodsCode'],input[name='costs.goodsName']").removeClass("required");
			$("input[name='costs.goodsCode'],input[name='costs.goodsName']").val("");
		}else{
			$("#update-costs-goodsId,#update-costs-goodsName").css("display","block");
			$("input[name='costs.goodsCode'],input[name='costs.goodsName']").addClass("required");
		}
	});
});
</script>
