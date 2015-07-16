<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/kit/dwz/js/dwz.resetformvalue.js"></script>	
<%@ include file="../commons/taglibs.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<div class="pageHeader">
  		<form rel="dd" id="orgList" onsubmit="return navTabSearch(this);" action="${CTX }/trade/listAllTradeDetail.do" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>&nbsp;&nbsp;追溯码：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input  type="text" class="checkReset" id="name"  name="traceId" value="${marketTranInfoDetail.traceId }"/></td>
					<td>商品名称：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" class="checkReset" id="name" name="goodsName" value="${marketTranInfoDetail.goodsName }"/></td>
					<td>交易凭证号：<input type="text" class="checkReset" id="name" name="tranId" value="${marketTranInfoDetail.marketTranInfoBase.tranId }"/></td>
				</tr>
				<tr>	
					<td><label>交易时间从：</label><input type="text" name="startDate" class="date checkReset"  dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" size="20"/><a class="inputDateButton" href="javascript:;" style="float:right;">选择</a></td>
					<td><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;至：</label><input type="text" name="endDate" class="date checkReset"  dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" size="20"/><a class="inputDateButton" href="javascript:;" style="float:right;" >选择</a></td> 
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
	<table class="table" width="1238" layoutH="135">
		<thead>
			<tr>
				<th>追溯码</th>
				<th>交易凭证号</th>
				<th>商品名称</th>
				<th>单价（元）</th>
				<th>重量（公斤）</th>
			    <th>价格</th>
			    <th width="150" orderfield="marketTranInfoBase.tranDate" class="<c:if test="${order.field eq 'marketTranInfoBase.tranDate'}">${order.direction }</c:if>">交易时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="marketTranInfoDetail" items="${page.rows }" varStatus="seq">
			<tr target="dtl_id" rel="${marketTranInfoDetail.dtlId }">
				<td align="center">${marketTranInfoDetail.traceId }</td>
				<td align="center">${marketTranInfoDetail.marketTranInfoBase.tranId }</td>
				<td align="center">${marketTranInfoDetail.goodsName }</td>
				<td align="center">
					<fmt:formatNumber value="${marketTranInfoDetail.price }" pattern="0.00"/>
				</td>
				<td align="center">
					<fmt:formatNumber value="${marketTranInfoDetail.weight }" pattern="0.00"/>
				</td>
				<td align="center">
					<fmt:formatNumber value="${marketTranInfoDetail.price*marketTranInfoDetail.weight }" pattern="0.00"/>
				</td>
				<td align="center">
					<fmt:formatDate value="${marketTranInfoDetail.marketTranInfoBase.tranDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
		</c:forEach>
		<c:if test="${page.totalRows==0 }">
			<tr>
				<td  colspan="5" align="center">
				暂无结果
				</td>
			</tr>
		</c:if>
		</tbody>
	</table>
	
	<%-- 分页查询的Form --%>
	<form id="pagerForm" action="${CTX }/trade/listAllTradeDetail.do"
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
			<select class="combox" name="numPerPage"  onchange="navTabPageBreak({numPerPage:this.value})">
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
