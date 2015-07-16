<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp" %>

<div class="pageContent">
		<fieldset style="border: 1px solid #b8d0d6;height: 430px;width: 180px;margin-left: 10px;">
	    	<legend style="border: 0px solid">详细信息</legend>
	    		<!-- <font style="padding-top: 50px;font-weight: bold;color: red;">该商户已在城市平台备案,备案号为:110110110110</font> -->
			  	<div class="pageFormContent" layoutH="66" style="border: 0px solid;">
			<p>
	    		<label style="width:125px;">商户名称:</label>
				<input type="text" readonly="readonly" style="border: 0px solid;color:red;"  name="bizLookup.bizName" value="${approachD.wholesalerName }" suggestFields="bizNo,bizName,businessType" suggestUrl="${CTX }/approach/findMerchant.do" lookupGroup="bizLookup" />
             </p>
	    	
	    	  <p>
	    		<label style="width:125px;" >商品名称:</label>
				<input type="text" style="border: 0px solid;color:red;"  readonly="readonly" name="goodsLookup.goodsName" value="${approachD.goodsName }" suggestFields="goodsCode,goodsName,goodsStatus" suggestUrl="${CTX }/approach/findGoodst.do" lookupGroup="orgLookup" />
	    	  </p>
	    	
	    	 <p>
	    		<label style="width:125px;">商品价格:</label>
	    		<input type="text" style="border: 0px solid;color:red;" name="price" readonly="readonly" size="20"  value="${approachD.price }"> 
	    	 </p>
	    	
	    	  <p>
	    		<label style="width:125px;">商品重量：</label>
	    		<input type="text" style="border: 0px solid;color:red;" name="weight" readonly="readonly"  size="20" value="${approachD.weight }"></input>
	    	  </p>
	    	  <p>
	    		<label  style="width:125px;">凭证类型：</label>
				<c:forEach items="${voucherTypes }" var="voucher">
					<c:if test="${approachD.voucherType== voucher.key}">
						<input type="text" style="border: 0px solid;color:red;" name="weight" readonly="readonly"  size="20" value="${ voucher.value}"></input>
					</c:if>
				</c:forEach>
	    	</p>
	    	  <p>
	    		<label id="batchIdLabel" style="width:125px;">进货批次号：</label>
	    		<input type="text"  name="batchId"  style="border: 0px solid;color:red;" readonly="readonly" size="20" value="${approachD.batchId }"></input>
	    	  </p>
	    	  <p>
	    		<label style="width:125px;">运输车牌号：</label>
	    		<input type="text"  name="transporterId" readonly="readonly" style="border: 0px solid;color:red;"   size="20" value="${approachD.transporterId }"></input>
	    	  </p>
	    	   
	    	  <p>
	    	    <label style="width:125px;">产地：</label>
	    	    <input type="text" readonly="readonly" style="border: 0px solid;color:red;"  name="marketLookup.name" value="${approachD.areaOriginName }"  />
	    	  </p>
	    	  <p>
	    	     <label>供货市场名称：</label>
				<input type="text" readonly="readonly" style="border: 0px solid;color:red;"  name="marketLookup.name" value="${approachD.wsSupplierName }"  />
	    	  </p>
					<!-- <button style="float: right;margin-top: 20px;" class="close" value=" 关闭">关闭</button> -->
				</div>
	  	</fieldset>
</div>
