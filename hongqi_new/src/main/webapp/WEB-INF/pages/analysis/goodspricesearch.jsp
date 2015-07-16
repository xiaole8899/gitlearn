<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/kit/dwz/js/dwz.resetformvalue.js"></script>
<%@ include file="../commons/taglibs.jsp" %>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${CTX }/goodsPrice/getGoodsPrice.do?status=1" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><label>时间从：</label><input  type="text" name="startDate" class="date checkReset" value="${startDate }" dateFmt="yyyy-MM-dd" readonly="true" size="20"/><a class="inputDateButton" href="javascript:;" style="float:right;">选择</a>
					</td>
					<td><label>至：(15天之内)</label><input type="text" name="endDate" class="date checkReset" value="${endDate }" dateFmt="yyyy-MM-dd" readonly="true" size="20"/><a class="inputDateButton" href="javascript:;" style="float:right;" >选择</a>
					</td>
					<td>
						<label>已采集商品：</label><select name="goodsList" class="combox checkResetSelect">
							<option value="">请选择：</option>
							<c:forEach var="goods" items="${goodsList }">
								<option value="${goods.goodsCode }" <c:if test="${goodsCode==goods.goodsCode }">selected='selected'</c:if>>${goods.goodsName }</option>
							</c:forEach>
						</select>
					</td>
				</tr> 
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button id="button_thing" type="submit">检索</button></div></div></li>
					<li><div class="buttonActive"><div class="buttonContent"><button class='resetForm'>重置</button></div></div></li>
				</ul>
			</div>
		</div>	
	</form>
</div>

<div style="margin-top: 5%;font-size: 23px; font-weight: bold;text-align: center;">
	${startDate }至${endDate } ${goods.goodsName }价格走势图
</div>
<div id="chartHolder" style="width: 650px;height: 450px;text-align: center; margin:-50px  auto 0;"></div>
<div class="pageContent" style="height: 200px;overflow: scroll;overflow-x: hidden;text-align: center; width:580px; margin:-130px auto 0;">
	<%-- 数据列表 --%>
	<table class="table" width="600">
		<thead>
			<tr>
				<th width="40">序号</th>
				<th>商品名称</th>
				<th>商品单价</th>
				<th>日期</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="goodsPrice" items="${goodsPriceList }" varStatus="seq">
			<tr>
				<td align="center" style="font-weight: bold; font-style: italic;">${seq.index+1 }</td>
				<td align="center">${goodsPrice.goodsName }</td>
				<td align="center">${goodsPrice.goodsPrice }</td>
				<td align="center">${goodsPrice.detailDate }</td>
			</tr>
		</c:forEach>
		<c:if test="${page.totalRows==0}">
			<tr>
				<td colspan="5" align="center">
					暂无结果
				</td>
			</tr>
		</c:if>
		</tbody>
	</table>
	
	<%-- 分页查询的Form --%>
	<form id="pagerForm" action="${CTX }/goodsPrice/getGoodsPrice.do?status=1" method="post">
		<input type="hidden" name="pageNum" value="1" />
		<input type="hidden" name="numPerPage" value="1" />
	</form> 
</div>
<script type="text/javascript">
    var options = {
    	stacked: false,
    	gutter:20,
		axis: "0 0 1 1", // Where to put the labels (trbl)
		axisystep: 10 // How many x interval labels to render (axisystep does the same for the y axis)
	};
	
	$(function() {
        // Creates canvas
        var r = Raphael("chartHolder");
		//options.axisystep = 5;
		options.stacked=false;
		var data3 = [[<%=request.getAttribute("allPrice") %>]];
		
		//第一个参数是Y坐标位置
		//第二个参数是X坐标位置
		//第三个参数是每个柱状占位置的宽度
		var chart3 = r.barchart(50,100,<%=request.getAttribute("kjwidth") %>, 200, data3, options).hover(function() {
            this.flag = r.popup(this.bar.x, this.bar.y, this.bar.value).insertBefore(this);
        }, function() {
            this.flag.animate({opacity: 0}, 500, ">", function () {this.remove();});
        });
		chart3.label([[<%=request.getAttribute("allDate") %>]],true);
	});
</script>