<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/kit/dwz/js/dwz.resetformvalue.js"></script>	
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<%@ include file="../commons/taglibs.jsp" %>
<div class="pageHeader">
  		<form rel="dd" id="list_trade" onsubmit="return navTabSearch(this);" action="${CTX }/trade/dotraceByTraceIdList.do" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>追溯码：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" class="checkReset" name="traceId" value="${marketTranInfoBase.traceId}"/></td>
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
	<div style="height:25px; text-align:center; font-weight: bold;padding-left: 230px;margin-top:20px;padding-top:20px;border-bottom:1px solid black;">
		 <%-- <span><a href="${CTX }/index" style="float: left;">返回</a></span> --%>
		 <span style="margin-right:30%">追溯信息查询 </span>
	</div>
			<table   width="70%"  border="1"  style="border-collapse: collapse; margin-left:15%; margin-top:40px;padding-top:40px;">
				 <tr>
					  <td colspan="5"  align="left" style="font-weight:bold;">供货商</td>
					  <td colspan="5"  align="left" style="font-weight:bold;">${approach.wholesalerName }</td>
				 </tr>
				<tr>
					  <td colspan="5"  align="left" style="font-weight:bold;">供货商备案号</td>
					  <td colspan="5"  align="left" style="font-weight:bold;">${approach.wholesalerId }</td>
				 </tr>
				 <tr >
					  <td colspan="5"  align="left" style="font-weight:bold;">供货市场名称</td>
					  <td colspan="5"  align="left" style="font-weight:bold;">${approach.wsSupplierName }</td>
				 </tr>
				 <tr >
					  <td colspan="5"  align="left" style="font-weight:bold;">商品产地</td>
					  <td colspan="5"  align="left" style="font-weight:bold;">${approach.areaOriginName }</td>
				 </tr>
				 <tr>
					  <td colspan="5" align="left" style="font-weight:bold;" >商品名称</td>
					  <td colspan="5" align="left" style="font-weight:bold;">${approach.goodsName }</td>
				 </tr>
				 <tr>
				 	 <td  colspan="5" align="left" style="font-weight:bold;">商品价格</td>
					 <td  colspan="5"  align="left" style="font-weight:bold;">${approach.price }</td>
				 </tr>
				 <!-- <tr>
				 	 <td  colspan="5" align="left" style="font-weight:bold;">卡号</td>
					 <td  colspan="5"  align="left" style="font-weight:bold;">333333233</td>
				 </tr>
				 <tr>
				 	 <td  colspan="5" align="left" style="font-weight:bold;">卡内余额</td>
					 <td  colspan="5"  align="left" style="font-weight:bold;">33233</td>
				 </tr> -->
			</table> 
			
			<table  width="70%"  border="1"  style="border-collapse: collapse;margin-left:15%;margin-top:40px;padding-top:40px;">
				 <tr >
					  <td colspan="5"  align="left" style="font-weight:bold;">商户姓名</td>
					  <td colspan="5"  align="left" style="font-weight:bold;">${tranInfoBase.retailerName }</td>
				 </tr>
				 <tr >
					  <td colspan="5"  align="left" style="font-weight:bold;">商户编码</td>
					  <td colspan="5"  align="left" style="font-weight:bold;">${tranInfoBase.retailerId }</td>
				 </tr>
				 <tr>
					  <td colspan="5"  align="left" style="font-weight:bold;">交易日期</td>
					  <td colspan="5"  align="left" style="font-weight:bold;">${tranInfoBase.tranDate }</td>
				 </tr>
				 <tr>
					  <td colspan="5" align="left" style="font-weight:bold;" >总重量</td>
					  <td colspan="5" align="left" style="font-weight:bold;">${tranInfoBase.totalWeight }</td>
				 </tr>
				<!--  <tr>
				 	 <td  colspan="5" align="left" style="font-weight:bold;"> 年龄</td>
					 <td  colspan="5"  align="left" style="font-weight:bold;">33</td>
				 </tr>
				 <tr>
				 	 <td  colspan="5" align="left" style="font-weight:bold;">卡号</td>
					 <td  colspan="5"  align="left" style="font-weight:bold;">333333233</td>
				 </tr>
				 <tr>
				 	 <td  colspan="5" align="left" style="font-weight:bold;">卡内余额</td>
					 <td  colspan="5"  align="left" style="font-weight:bold;">33233</td>
				 </tr> -->
			</table> 
  			<table>
				<tr>
					<td><input type="text" style="border-collapse: collapse;margin-left:79%;margin-top:40px;"  value="${traceId}"/></td>
				</tr>
			 	
			
			</table>
   </div>

	
