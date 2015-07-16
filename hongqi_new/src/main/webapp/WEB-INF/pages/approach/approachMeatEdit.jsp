<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="../commons/taglibs.jsp" %>
<script type="text/javascript">
<!--

//-->
var WYZ_APP ={
		 /*
		  * 凭证类型改变事件，联动着最后编码号的变动
		 */
		 batchValue :function(obj) {
			 $(obj).children().each(function(){
				  if($(this).attr("selected")=="selected"){
					  $("#wyzApp_batchId").removeAttr("readonly");
					  $("#batchIdLabel").html($(this).html()+":")
				      $("#wyzApp_batchId").next().html("请输入"+$(this).html());
					  if($(this).html()=="批次号"){
						  $("#wyzApp_batchId").attr("readonly","readonly");
						  $("#wyzApp_batchId").next().html("自动生成"+$(this).html());

					  }
				  }
			  });
		 }


};

</script>


<div class="pageContent">
	<form method="post" action="${CTX }/approach/editApproach.do" rel="addApproachMeat"  class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		 <div class="pageFormContent"  layoutH="56">
	    	<p>
	    		<label style="width:125px;">商户名称:</label>
	    		<input type="hidden" name="approachId" value="${approachD.approachId }"/> <!-- 进场主键 -->
	    		<input type="hidden" name="inDate" value="${approachD.inDate}"/> <!-- 进场日期 -->
 				<input type="hidden" name="bizLookup.bizNo" value="${approachD.wholesalerId}"/>
 				<input type="hidden" name="bizLookup.businessType" value="${approachD.bizType }"/>
				<input type="text" readonly="readonly" class="required" name="bizLookup.bizName" value="${approachD.wholesalerName }" suggestFields="bizNo,bizName,businessType" suggestUrl="${CTX }/approach/findMerchant.do" lookupGroup="bizLookup" />
				<a class="btnLook" href="${CTX }/approach/findMerchant.do?approachBizType=${approachState}" lookupGroup="bizLookup" width="1000" rel="bizMenchantE">查找带回</a>		
             </p>
	    	
	    	  <p>
	    		<label style="width:125px;" >商品名称:</label>
	         	<input type="hidden" name="goodsLookup.goodsCode" value="${approachD.goodsCode }"/>
	         	<input type="hidden" name="goodsLookup.goodsStatus" value="${approachD.goodsType }"/>
				<input type="text" class="required" readonly="readonly" name="goodsLookup.goodsName" value="${approachD.goodsName }" suggestFields="goodsCode,goodsName,goodsStatus" suggestUrl="${CTX }/approach/findGoodst.do" lookupGroup="orgLookup" />
				<a class="btnLook" href="${CTX }/approach/findGoods.do?approachStateAdd=${approachState}" lookupGroup="goodsLookup" width="1000" rel="lookGoodsE">查找带回</a>		
	    	  </p>
	    	
	    	 <p>
	    		<label style="width:125px;">商品价格:</label>
	    		<input type="text" class="required number" name="price" size="20" alt="请输入价格" value="${approachD.price }"> 
	    	 </p>
	    	
	    	  <p>
	    		<label style="width:125px;">商品重量：</label>
	    		<input type="text" class="required" name="weight" alt="请输入进货的重量" size="20" value="${approachD.weight }"></input>
	    	  </p>
	    	  <p>
	    		<label  style="width:125px;">凭证类型：</label>
	    		<select name="voucherType" class="required combox" onchange="WYZ_APP.batchValue(this);">
					<option value="">请选择</option>
					<c:forEach items="${voucherType }" var="btype">
							<option value="${btype.key }" <c:if test="${approachD.voucherType== btype.key}">selected="selected"</c:if>>${btype.value }</option>
					</c:forEach>
				</select>
	    	</p>
	    	  <p>
	    		<label id="batchIdLabel" style="width:125px;">进货批次号：</label>
	    		<input type="text"  name="batchId" id="wyzApp_batchId"  alt="请输入批次号" size="20" value="${approachD.batchId }" maxLength="20"></input>
	    	  </p>
	    	  <p>
	    		<label style="width:125px;">运输车牌号：</label>
	    		<input type="text"  name="transporterId" alt="请输入车牌号"  size="20" value="${approachD.transporterId }"></input>
	    	  </p>
	    	   
	    	  <p>
	    	    <label style="width:125px;">产地：</label>
	    	    <select class="combox" name="province" ref="w_combox_city_enterpriseadd" currentValue='${wyzprovince}' refUrl="${CTX }/county/findCountyByPrId.do?prentId={value}">
					<option value="all">请选择</option>
					<c:forEach var="are" items="${listArea}">
						<option value="${are.cId },${are.cName }">${are.cName }</option>
					</c:forEach>
				</select>
				<select class="combox" name="city" id="w_combox_city_enterpriseadd" currentValue='${city}' ref="w_combox_area_enterpriseadd" refUrl="${CTX }/county/findCountyByPrId.do?prentId={value}">
					<option value="all">请选择</option>
				</select>
				<select class="combox" name="street" currentValue='${street}' id="w_combox_area_enterpriseadd">
					<option value="all">请选择</option>
				</select>
	    	  </p>
	    	  <p>
	    	     <label>供货市场名称：</label>
	    	     <input type="hidden" name="marketLookup.no" value="${approachD.wsSupplierId }"/>
				<input type="text" class="required" name="marketLookup.name" value="${approachD.wsSupplierName }" suggestFields="no,name" suggestUrl="${CTX }/approach/findSuppMarket.do" lookupGroup="orgLookup" />
				<a class="btnLook" href="${CTX }/approach/findSuppMarket.do" lookupGroup="marketLookup" width="1000" rel="lookMarketE">查找带回</a>		
	    	  </p>
	      <div class="divider"></div>
		</div>
		
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
		
	</form>
</div>