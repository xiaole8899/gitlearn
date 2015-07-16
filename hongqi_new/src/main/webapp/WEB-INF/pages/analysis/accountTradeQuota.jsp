<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/kit/dwz/js/dwz.resetformvalue.js"></script>
<%@ include file="../commons/taglibs.jsp" %>
<script type="text/javascript" charset="utf-8">
/* Title settings */		
title = "商户收支状况统计图";
titleXpos = 390;
titleYpos = 85;

/* Pie Data */
pieRadius = 130;
pieXpos = 650;
pieYpos = 180;
 if(<%=request.getAttribute("expenceTotal")%>=="0" &&<%=request.getAttribute("incomeTotal")%>>0){
	 pieData = [<%=request.getAttribute("incomeTotal")%>];
	 pieLegend = [
     "%%.%% – 收入"
 ];
 }else if(<%=request.getAttribute("incomeTotal")%>=="0" &&<%=request.getAttribute("expenceTotal")%>>0){
	 pieData = [<%=request.getAttribute("expenceTotal")%>];
	 pieLegend = [
	 "%%.%% – 支出"
	
	 ];
 }else if(<%=request.getAttribute("expenceTotal")%>=="0" &&<%=request.getAttribute("incomeTotal")%>=="0"){
	 pieData = [];
	 pieLegend = [
		
	 ];
 }else{
	 pieData = [<%=request.getAttribute("expenceTotal")%>,<%=request.getAttribute("incomeTotal")%>];
	 pieLegend = [
	 "%%.%% – 支出", 
	 "%%.%% – 收入"
	 ];
 }


pieLegendPos = "数量";

$(function () {
	var r = Raphael("chartHolder");
	r.text(titleXpos, titleYpos, title).attr({"font-size": 20});
	
	var pie = r.piechart(pieXpos, pieYpos, pieRadius, pieData, {legend: pieLegend, legendpos: pieLegendPos});
	pie.hover(function () {
		this.sector.stop();
		this.sector.scale(1.1, 1.1, this.cx, this.cy);
		if (this.label) {
			this.label[0].stop();
			this.label[0].attr({ r: 7.5 });
			this.label[1].attr({"font-weight": 800});
		}
	}, function () {
		this.sector.animate({ transform: 's1 1 ' + this.cx + ' ' + this.cy }, 500, "bounce");
		if (this.label) {
			this.label[0].animate({ r: 5 }, 500, "bounce");
			this.label[1].attr({"font-weight": 400});
		}
	});
	
});
</script>

<div class="pageHeader">
  		<form  id="approachList" onsubmit="return navTabSearch(this);" action="${CTX }/accountTrade/listQuota.do?" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><label>商户名称：</label><input  type="text" class="checkReset" id="wholesalerName"  name="bizName" value="${merchant.bizName }"/></td>
					<td><label>商户编号：</label><input type="text" class="digits" id="wholesalerId" name="bizNo" value="${merchant.bizNo}"/></td>
			   </tr>
			    <tr>
					<td><label>交易时间从：</label><input  type="text" name="startDate" class="date checkReset" value="${startDate }" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" size="20"/><a class="inputDateButton" href="javascript:;" style="float:right;">选择</a>
					</td>
					<td><label>至(15天之内)：</label><input type="text" name="endDate" class="date checkReset" value="${endDate }" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" size="20"/><a class="inputDateButton" href="javascript:;" style="float:right;" >选择</a>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button id="approachListBtn" type="submit">检索</button></div></div></li>
					<li><div class="buttonActive"><div class="buttonContent"><button class="resetForm">重置</button></div></div></li>
				</ul>
			</div>
		</div>	
	</form>
</div>

<%--饼状图展示 --%>
<div id="chartHolder"></div>
  <div style="margin-bottom:5px; float:left; width:50%;"> <h1>卖出交易：</h1> </div>
  <div style="margin-bottom:5px; float:right; width:50%;"><h1>买入交易：</h1> </div>
  
<div style="float:left; width:100%;height:350px; overflow:scroll;">
  <div style="float:left; width:50%;">
    
    	<%-- 数据列表 --%>
	<table class="table" width="98%" layoutH="158">
		<thead>
			<tr>
				<th>序号</th>
				<th>追溯码</th>
				<th>时间</th>
				<th>买家</th>
				<th>品名</th>
				<th>重量(千克)</th>
				<th>总价(元)</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="income" items="${income}" varStatus="seq">
			<tr>
				<td  align="center">
					${seq.count }
				</td>
				<td  align="center">${income.tranId}</td>
				<td align="center">${income.dealTime }</td>
				<td align="center">
				  ${income.bizName }
			    </td>
				<td align="center">${income.goodsName}</td>
				<td  align="center">${income.weight }</td>
				<td align="center">${income.price }</td>
				
			</tr>
		</c:forEach>
		<c:if test="${rows==0}">
			<tr>
				<td colspan="7" align="center">
					暂无结果
				</td>
			</tr>
		</c:if>
		</tbody>
	</table>
  </div>
  
    <div style="float:right; width:50%; ">
    	<%-- 数据列表 --%>
	<table class="table" width="98%" layoutH="158">
		<thead>
			<tr>
				<th>序号</th>
				<th orderfield="wholesalerId" class="<c:if test="${order.field eq 'wholesalerId'}">${order.direction }</c:if>">追溯码</th>
				<th orderfield="wholesalerName" class="<c:if test="${order.field eq 'wholesalerName'}">${order.direction }</c:if>">时间</th>
				<th>卖家</th>
				<th>品名</th>
				<th>重量(千克)</th>
				<th>总价(元)</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="expence" items="${expence}" varStatus="seq">
			<tr>
				<td  align="center">
					${seq.count }
				</td>
				<td  align="center">${expence.tranId }</td>
				<td align="center">${expence.dealTime }</td>
				<td align="center">
				 ${expence.bizName }
			    </td>
				<td align="center">${expence.goodsName }</td>
				<td  align="center">${expence.weight }</td>
				<td align="center">${expence.price }</td>
				
			</tr>
		</c:forEach>
		<c:if test="${rows==0}">
			<tr>
				<td colspan="7" align="center">
					暂无结果
				</td>
			</tr>
		</c:if>
		</tbody>
	</table>
  </div>
  
</div>
