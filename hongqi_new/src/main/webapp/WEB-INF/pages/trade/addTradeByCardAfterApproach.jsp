<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp" %>

<div class="pageContent">
	<form method="post" action="${CTX }/trade/doAddTradeByCard.do" class="pageForm required-validate" id="addTrade" onsubmit="return validateCallback(this,navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<input type="hidden" name="buyerType" value="1" />
			<p>
			 	<label>卖方卡号：</label> 
			 	<!-- 卖方备案号 -->
			 	<input type="text" readonly="readonly" style="color: green;"  name="sellerCardNo" value="${wholesalerCard }"/>
			 	<input type="button" id="addTrade-getwholesalerInfo" style="margin-left: 1%"  value="请刷卖方卡" />
			</p>
			<div class="divider"></div>
			<p>
			 	<label>卖方编码：</label> 
			 	<!-- 卖方备案号 -->
			 	<!-- 商户经营类型 -->
			 	<input type="text" style="border: 0px solid green;color:green;" readonly="readonly" name="wholesalerId" value="请刷卖方卡"/>
			 	<!-- 商户类型 -->
			</p>
			<p style="margin-left: 20%">
			 	<label>卖方名称：</label> 
			 	<!-- 卖方备案号 -->
			 	<!-- 商户经营类型 -->
			 	<input type="text" style="border: 0px solid;color: green;" readonly="readonly" name="wholesalerName" value="请刷卖方卡"/>
			 	<!-- 商户类型 -->
			</p>
			 <div class="divider"></div>
			<p >
			 	<label>买方卡号：</label> 
			 	<!-- 卖方备案号 -->
			 	<input type="text" style="color: green;" readonly="readonly" name="buyCardNo" value=""/>
			 	<input type="button" id="addTrade-getretailerIdInfo" style="margin-left: 1%"  value="请刷买方卡" />
			</p>
			<div class="divider"></div>
			<p>
			 	<label>买方编码：</label> 
			 	<!-- 卖方备案号 -->
			 	<!-- 商户经营类型 -->
			 	<input type="text" style="border: 0px solid green;color:green;" readonly="readonly" name="retailerId" value="请刷买方卡"/>
			 	<!-- 商户类型 -->
			</p>
			<p style="margin-left: 20%">
			 	<label>买方名称：</label> 
			 	<!-- 卖方备案号 -->
			 	<!-- 商户经营类型 -->
			 	<input type="text" style="border: 0px solid;color:green;" readonly="readonly" name="retailerName" value="请刷买方卡"/>
			 	<!-- 商户类型 -->
			</p>
			
			<div class="divider"></div>
			<p>
            	<label>交易类型:</label>
            	<input type="hidden" name="tradeType" value="1" />
            	<input type="text" readonly="readonly" value="刷卡" style="border: 0px solid green; color: green;"  />
            </p> 
            <p style="margin-left: 20%">
				<label>交易日期：</label>
				<input type="text" name="tranDate1" class="required date" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" size="22"/>
				<a class="inputDateButton" href="javascript:;">选择</a>
			</p>		
			<div class="divider"></div>
             <p style="display:none;" id="add-trade-sellerCardNo">
				<label>卖方卡号:</label>
				<input type="text"  name="sellerCardNo" size="25"/>
			</p>		
            <p style="display:none;margin-left: 20%;" id="add-trade-buyerCardNo">
            	<label>买方卡号:</label>
            	<input type="text"  name="buyCardNo" size="25"/>
            </p> 
            <div class="divider" style="display: none;" id="add-trade-tradeTypeDivider"></div>
			<div class="unit" style="margin-top: 40px;">
	            <table class="list nowrap itemDetail"   addButton="增加商品明细" width="60%" >
					<thead>
						<tr>
							<th size="30" fieldclass="required" wholesalerId="5001050020002"  width="500"   
							lookupurl="${CTX }/trade/findGoods.do?wholesalerId=${wholesalerId }"  lookupPk="goodsCode"
							lookupgroup="goodsLookup" width="500" name="goodsLookup.goodsName"  type="lookup">商品名称
							</th>
							<th  fieldClass="required number" width="500" name="prices" size="20" type="text" width="500">单价(元)</th>
							<th type="text"  width="500"fieldclass="required number" size="20" name="weights" width="560">重量(KG)</th>
							<th  type="del" width="60">操作</th>
						</tr>
					</thead>
					<tbody></tbody>
				</table>
			</div>
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
	//读卡操作(获取卖方信息)
	var $getwholesalerInfo=$("#addTrade-getwholesalerInfo").click(function(){
		$.readuser(function(data) {
			if (data.result == "success") {
				$("input[name='sellerCardNo']").val(data.data['cardNumber']);
				$("input[name='wholesalerId']").val(data.data['bizNo']);
				$("input[name='wholesalerName']").val(data.data['bizName']);
			} else {
				alertMsg.error(data.data);
			}
		});
	});
	
	
	//读卡操作(获取买方信息)
	var $getretailerIdInfo=$("#addTrade-getretailerIdInfo").click(function(){
		$.readuser(function(data) {
			if (data.result == "success") {
				$("input[name='buyCardNo']").val(data.data['cardNumber']);
				$("input[name='retailerId']").val(data.data['bizNo']);
				$("input[name='retailerName']").val(data.data['bizName']);
			} else {
				alertMsg.error(data.data);
			}
		});
	});
});
</script>
