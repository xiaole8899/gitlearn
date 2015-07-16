<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/kit/dwz/js/dwz.resetformvalue.js"></script>	

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<%@ include file="../commons/taglibs.jsp" %>
<script type="text/javascript">
	
var WYZ_APP ={
     
   printpage: function (myDiv){ 
    //var newstr = document.all.item(myDiv).innerHTML; 
    var newstr = document.getElementById(myDiv).innerHTML;
    // alert(newstr);
    var oldstr = document.body.innerHTML; 
    document.body.innerHTML = newstr; 
    window.print(); 
    document.body.innerHTML = oldstr; 
    return false; 
    }

};


</script>

<div class="pageContent" >
	<div style="margin-left:10%;margin-top:5%; width:80%;height:auto;"  id="tradeCheckList">
	<table width="100%"  border="1" style="border-collapse: collapse;">
		 <tr height="35px;">
		  <td colspan="8" align="center" style="font-size:18px; font-weight:bold;">交易凭证号：${marketTranInfoBase.tranId }</td>
		</tr>
		<tr height="25px;">
		  <th style="font-weight:bold;">买方名称</th>
		  <td align="center">${marketTranInfoBase.retailerName }</td> 
		  <th style="font-weight:bold;">卖方名称</th>
		  <td align="center">${marketTranInfoBase.wholesalerName }</td> 
		  <th style="font-weight:bold;" colspan="2" >总计</th>
		  <td align="center" colspan="2" ><fmt:formatNumber value="${marketTranInfoBase.totalPrice }" pattern="0.00"/>元</td></tr>
		 <!-- 具体明细 start -->
		 <c:forEach var="tradeDetail" items="${marketTranInfoBase.marketTranInfoDetailItems }" varStatus="seq">
		 <tr height="25px;" >
		  <th><strong>商品名称</strong></th>
		  <td align="center">${tradeDetail.goodsName}</td>
		  <th style="font-weight:bold;">单价</th>
		  <td align="center" >${tradeDetail.price }元/公斤</td>
		  <th style="font-weight:bold;">重量</th>
		  <td align="center"><fmt:formatNumber value="${tradeDetail.weight}" pattern="0.00"/>公斤</td>
		  <th style="font-weight:bold;">总价</th>
		  <td align="center"><fmt:formatNumber value="${tradeDetail.totals }" pattern="0.00"/>元</td>
		 </tr>
		 </c:forEach>
		
		<!-- 具体明细 end -->
		<tr  height="35px;" >
		  <th style="font-weight:bold;"  colspan="8" > 
		  <span style="margin-left:-28%; font-size:18px;">买方签字：</span>
		  <span style="margin-left:30%; font-size:18px;">卖方签字：</span>
		  </th>
	   </tr>
	</table>
	  
   </div>
   <div style="float:right; margin-right:42%; margin-top:2%;">  <input type="button" onclick="javascript:WYZ_APP.printpage('tradeCheckList')"   value="打印"/>
	
</div>
 