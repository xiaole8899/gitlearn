<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/kit/dwz/js/dwz.resetformvalue.js"></script>	
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<%@ include file="../commons/taglibs.jsp" %>
<script type="text/javascript">
	
var WYZ_APP ={
		printpage1:function(){
			var tranId = $("#printTranId").val();
			window.open("trade/doPrintTradeSettlementList.do?tranId="+tranId+"");     
		}
	}
</script>

<div class="pageHeader">
  		<form rel="dd" id="list_trade" onsubmit="return navTabSearch(this);" action="${CTX }/trade/doTradeSettlementList.do" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>交易凭证号：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" class="checkReset" name="tranId" value="${marketTranInfoBase.tranId}"/></td>
				</tr>
			 	
			
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit" >检索</button></div></div></li>
					<li><div class="buttonActive"><div class="buttonContent"><button class='resetForm'>重置</button></div></div></li>
				</ul>
			</div>
		</div>	
	</form>
</div>
<div class="pageContent" >
	<div style="margin-left:10%;margin-top:5%;">
	 <table  width="70%"  border="1" class="gridtable">
		 <tr height="50px;">
		  	<td colspan="12" align="center" style="font-size:18px; font-weight:bold;">交易凭证号：${marketTranInfoBase.tranId }</td>
		</tr>
		<tr height="35px;">
			  <td colspan="3"  align="center" style="font-weight:bold;">买方名称</td>
			  <td colspan="3" align="left" >${marketTranInfoBase.retailerName }</td> 
			  <td align="center" colspan="3"style="font-weight:bold;">卖方名称</td>
			  <td colspan="3" align="left">${marketTranInfoBase.wholesalerName }</td> 
		 </tr>
		 <tr height="40px;">
			  <th style="font-weight:bold;" colspan="2">商品名称</th>
			  <th style="font-weight:bold;padding-right: -5px;" colspan="2" >买方费用</th>
			  <th style="font-weight:bold;" colspan="2" >卖方费用</th>
			  <th style="font-weight:bold;" colspan="2" >费用总价</th>
			  <th style="font-weight:bold;" colspan="2" >商品总价</th>
		  </tr>
		 <!-- 具体明细 start -->
		 <c:forEach var="trans" items="${marketTranInfoBase.marketTranInfoDetailItems }" varStatus="seq">
		 	<tr height="25px;" >
				 <th style="font-weight:bold;" colspan="2">${trans.goodsName}</th>
				 <td align="center" colspan="2">${trans.retailerTransfer }元</td>
				 <td align="center" colspan="2">${trans.wholesalerTransfer }元</td>
				 <td align="center" colspan="2">${trans.retailerTransfer + trans.wholesalerTransfer }元</td>
				 <td align="center" colspan="2">${trans.totals}元</td>
		 	</tr>
		 </c:forEach>
		 
		 <tr height="40px;">
			 <th style="font-weight:bold;" colspan="3"> <span style="margin-left:-10%; font-size:18px;">买方费用总价: </span>${marketTranInfoBase.totalBuyTransfer }元</th>
			 <th style="font-weight:bold;" colspan="3"> <span style="margin-left:-10%; font-size:18px;">卖方方费用总价: </span>${marketTranInfoBase.totalSellTransfer }元</th>
			 <th style="font-weight:bold;" colspan="3"> <span style="margin-left:-10%; font-size:18px;">买卖费用总价: </span>${marketTranInfoBase.totalBuyTransfer + marketTranInfoBase.totalSellTransfer }元</th>
			 <th style="font-weight:bold;" colspan="3"> <span style="margin-left:-10%; font-size:18px;">商品总价合计: </span>${marketTranInfoBase.totalPrice }元</th>
		 </tr>
		
		 <tr height="45px;" >
			<th style="font-weight:bold;"  colspan="5" > <span style="margin-left:-51%; font-size:18px;">买方签字：</span></th>
			<th style="font-weight:bold;"  colspan="7" ><span style="margin-left:-45%; font-size:18px;">卖方签字：</span></th>
	     </tr>
	</table> 
	</div>
   </div>
<div style="float:right; margin-right:42%; margin-top:2%;"> 
    <input type="hidden" id="printTranId" value="${marketTranInfoBase.tranId }"/>
    <input type="button" onclick="javascript:WYZ_APP.printpage1()" value="跳转到打印页"/>
</div>
	
