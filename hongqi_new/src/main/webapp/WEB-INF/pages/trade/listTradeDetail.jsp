<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/kit/dwz/js/dwz.resetformvalue.js"></script>
<%@ include file="../commons/taglibs.jsp" %>
<div class="pageHeader">
  		<form rel="dd" id="orgList" onsubmit="return dialogSearch(this);" action="${CTX }/trade/listTradeDetail.do" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><input type="hidden"  name="hdrId" value="${hdrId }" />
					追溯码：<input value="${marketTranInfoDetail.traceId }"   type="text" class="checkReset" id="name"  name="traceId"/></td>
					<td>商品名称：<input type="text" class="checkReset" id="no" value="${marketTranInfoDetail.goodsName }" name="goodsName"/></td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
					<li><div class="buttonActive"><div class="buttonContent"><button class='resetForm'>重置</button></div></div></li>
				</ul>
			</div>
		</div>	
	</form>
</div>
<div class="pageContent">
	<table class="table" width="1000" layoutH="108">
		<thead>
			<tr>
				<th>追溯码</th>
				<th>商品名称</th>
				<th>单价（元）</th>
				<th>重量（公斤）</th>
				<th>价格</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="marketTranInfoDetail" items="${page.rows }" varStatus="seq">
			<tr target="dtl_id" rel="${marketTranInfoDetail.dtlId }">
				<td align="center">${marketTranInfoDetail.traceId }</td>
				<td align="center">${marketTranInfoDetail.goodsName }</td>
				<td align="center">
				<fmt:formatNumber value="${marketTranInfoDetail.price }" pattern="0.00"/>
				</td>
				<td align="center">
					<fmt:formatNumber value="${marketTranInfoDetail.weight }" pattern="0.00" />
				</td>
				<td align="center">
					<fmt:formatNumber value="${marketTranInfoDetail.price*marketTranInfoDetail.weight }" pattern="0.00" />
				</td>
			
			
			</tr>
		</c:forEach>
		    <tr>
				<c:if test="${page.totalRows==0 }">
					<td colspan="4" style="text-align: center;">
						<a style="cursor: pointer;">暂无结果</a>
					</td>
				</c:if>
			</tr>
		</tbody>
	</table>
	
	<%-- 分页查询的Form --%>
	<form id="pagerForm" action="${CTX }/trade/listTradeDetail.do"
	 method="post">
		<input type="hidden" name="pageNum" value="${page.pageIndex }" />
		<input type="hidden" name="numPerPage" value="${page.pageSize }" />
		<input type="hidden" name="orderField" value="${order.field }" />
		<input type="hidden" name="orderDirection" value="${order.direction }" /> 
		
	</form> 
	
		<%-- 分页脚注 --%>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage"  onchange="dialogPageBreak({numPerPage:this.value})">
				<option value="1" <c:if test="${page.pageSize eq 20 }">selected</c:if>>20</option>
				<option value="2" <c:if test="${page.pageSize eq 50 }">selected</c:if>>50</option>
				<option value="3" <c:if test="${page.pageSize eq 100 }">selected</c:if>>100</option>
				<option value="4" <c:if test="${page.pageSize eq 200 }">selected</c:if>>200</option>
			</select>
			<span>条，共${page.totalRows}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${page.totalRows }" currentPage="${page.pageIndex }" numPerPage="${page.pageSize }"></div>
	</div>
	
</div>
