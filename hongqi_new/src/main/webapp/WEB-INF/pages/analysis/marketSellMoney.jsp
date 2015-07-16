<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/kit/dwz/js/dwz.resetformvalue.js"></script>
<%@ include file="../commons/taglibs.jsp" %>

<script type="text/javascript">
    var options = {
    	stacked: false,
    	gutter:20,
		axis: "0 0 1 1", // Where to put the labels (trbl)
		axisystep: 10 // How many x interval labels to render (axisystep does the same for the y axis)
	};
	
	$(function() {
		//alert(<%=request.getAttribute("data")%>+"year:"+<%=request.getAttribute("years")%>);
        // Creates canvas
        var r = Raphael("chartHolderSellMoney");
    	options.stacked=false;
        var data = [[<%=request.getAttribute("dataD")%>]]
        
        // stacked: false
		var chart1 = r.barchart(140, 50, <%=request.getAttribute("widthjj")%>, 250, data, options).hover(function() {
            this.flag = r.popup(this.bar.x, this.bar.y, this.bar.value+" 元").insertBefore(this);
        }, function() {
            this.flag.animate({opacity: 0}, 500, ">", function () {this.remove();});
        });
		chart1.label([[<%=request.getAttribute("yearD")%>]],true);
		
		
		// stacked: true
		options.stacked=true;
		

	});
</script>

<div class="pageHeader">
  		<form  id="approachList" onsubmit="return navTabSearch(this);" action="${CTX }/accountTrade/listMarketSell.do?" method="post">
		<div class="searchBar">
			<table class="searchContent">
				
			    <tr>
					<td><label>时间从：</label><input  type="text" name="startDate" class="date checkReset" value="${startDate }" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" size="20"/><a class="inputDateButton" href="javascript:;" style="float:right;">选择</a>
					</td>
					<td><label>至：</label><input type="text" name="endDate" class="date checkReset" value="${endDate }" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" size="20"/><a class="inputDateButton" href="javascript:;" style="float:right;" >选择</a>
					</td>
				</tr>
			</table>
			<div id="tips" style="margin-left:20px; margin-top:5px; color:red;"><span>最多显示查询时间段的前15条数据，请最好选择的时间跨度不要超过15</span></div>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button id="approachListBtn" type="submit">检索</button></div></div></li>
					<li><div class="buttonActive"><div class="buttonContent"><button class="resetForm">重置</button></div></div></li>
				</ul>
			</div>
		</div>	
	</form>
</div>

<%--柱状图展示 --%>
<div id="chartHolderSellMoney"></div>
<%-- <div>
  <span>${data}</span>
  <span>${years }</span>
</div> --%>
<div style="margin-left:20px; float:left; width:95%;">
 <table border ="1" >
	 <tr height="35">
	 	<th width="50">日期</th>
	 	<c:forEach var="years" items="${years}" varStatus="seq">
	 		<td width="124" align="center">${years}</td>
	    </c:forEach>
	 </tr>
	 <tr height="35">
	 	<th>金额</th>
	 	<c:forEach var="data" items="${data}" varStatus="dseq">
	 		<td align="center">${data} 元</td>
	 	</c:forEach>
	 </tr>
	 <tr height="35">
	 	<th>统计</th>
	 	<td colspan="${listSize}" align="center">${moneyTotal} 元 </td>
	 </tr>
 </table>
</div>
  
