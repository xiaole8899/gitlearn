<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp" %>
<script type="text/javascript" src="${CTX }/js/cardjsonp.js"></script>
<div class="pageContent">
	<form method="post" action="${CTX }/trade/doAddTrade.do" class="pageForm required-validate" id="addTrade" onsubmit="return validateCallback(this,navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
			 	<label>卖方名称：</label> 
			 	<!-- 商户经营类型 -->
			 	<input type="hidden" name="bizLookup.bizType" value=""/>
			 	<!-- 商户类型 -->
			 	<input type="hidden" name="bizLookup.businessType" value=""/>
				<input type="text" size="25"  readonly="readonly" class="required" name="bizLookup.bizName" value="${wholesalerName }" suggestFields="" suggestUrl="${CTX }/trade/findMerchant.do" lookupGroup="bizLookup" />
				<%-- <a class="btnLook" href="${CTX }/trade/findMerchant.do" lookupGroup="bizLookup"  rel="bizMenchant">查找带回</a>	 --%>	
			</p>
			<p style="margin-left: 20%;">
			 	<label>卖方编码：</label> 
			 	<!-- 卖方备案号 -->
			 	<input type="text"  name="bizLookup.bizNo" readonly="readonly"  value="${wholesalerId }" />
			 	<!-- 商户经营类型 -->
			 	<input type="hidden" name="bizLookup.bizType" value=""/>
			 	<!-- 商户类型 -->
			 	<input type="hidden" name="bizLookup.businessType" value=""/>
			</p>
			<div class="divider"></div>
			<p >
				<label>买方类型：</label>
				<select class="combox required" name="buyerType" id="add-trade-buyerType">
					<option value="">请选择</option>
					<c:forEach var="buyer" items="${buyerType }">
						<option value="${buyer.key }">${buyer.value }</option>
					</c:forEach>
				</select>
			</p>
			<div class="divider"></div>
			<p>
				<label>买方名称：</label>
				<input type="text" class="required" name="retailerName" size="25"/>
			</p>
			
			<p style="margin-left: 20%;display: none;" id="add-trade-retailerId">
				<label>买家编码：</label>
				<input type="text" class="required" name="retailerId" size="25"/>
				<input type="button" id="add-trade-checkretailerId" style="margin-left:93%;margin-top:-5.5%" value="验证是否可用" />
			</p>
			<div class="divider"></div>
			<p>
            	<label>交易类型:</label>
            	<input type="hidden" name="tradeType" value="0" />
            	<input type="text" readonly="readonly" value="现金" style="border: 0px solid green; color: green;"  />
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
	            <table class="list nowrap itemDetail" addButton="增加商品明细" width="60%" >
					<thead>
						<tr>
							<th size="30" fieldclass="required"  width="500"   
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
	//改变卖方类型时触发
	var $buyerTypeChange=$("#add-trade-buyerType").change(function(){
		//未注册
		if($(this).val()=="0"){
			$("#add-trade-retailerId").css("display","none");
			$("input[name='retailerId']").removeClass();
		}else{
			$("#add-trade-retailerId").css("display","block");
			$("input[name='retailerId']").addClass("required");
		}
	});
	
	//改变交易类型时触发
	var $tradeTypeChange=$("#add-trade-tradeType").change(function(){
		//现金
		if($(this).val()=="0"){
			$("input[name='sellerCardNo']").removeClass();
			$("input[name='buyCardNo']").removeClass();
			$("#add-trade-sellerCardNo,#add-trade-buyerCardNo,#add-trade-tradeTypeDivider").css("display","none");
		}else{
			$("input[name='sellerCardNo']").addClass("required");
			$("input[name='buyCardNo']").addClass("required");
			$("#add-trade-sellerCardNo,#add-trade-buyerCardNo,#add-trade-tradeTypeDivider").css("display","block");
		}
	});
	
	//验证商户是否存在
	var $checkretailerId=$("#add-trade-checkretailerId").click(function(){
		var bizNo=$("input[name='retailerId']").val();
		if(bizNo==""){
			alertMsg.error('买方编码不能为空!')
			return;
		}
		
		$.post("${CTX}/trade/isExitsByBizNo.do",{bizNo:bizNo},function(data){
			if(data!=""){
				alertMsg.correct('该商户可用!')
				$("input[name='retailerName']").val(data);
			}else{
				alertMsg.error('商户编号不存在,请核对!')
			}
		});
	});
});



</script>
