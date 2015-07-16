<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/kit/dwz/js/dwz.resetformvalue.js"></script>	
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<%@ include file="../commons/taglibs.jsp" %>
<div class="pageHeader">
  		<form rel="dd" id="list_trade" onsubmit="return navTabSearch(this);" action="${CTX }/trade/listTradeBase.do" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>&nbsp;&nbsp;卖方名称：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" class="checkReset" name="wholesalerName" value="${marketTranInfoBase.wholesalerName}"/></td>
					<td>交易凭证号：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" class="checkReset" name="tranId" value="${marketTranInfoBase.tranId}"/></td>
				</tr>
			 	<tr>	
			 		<td><label>交易时间从：</label><input type="text" name="startDate" class="date checkReset"  dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" size="20"/><a class="inputDateButton" href="javascript:;" style="float:right;">选择</a></td>
					<td><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;至：</label><input type="text" name="endDate" class="date checkReset"  dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" size="20"/><a class="inputDateButton" href="javascript:;" style="float:right;" >选择</a></td> 
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
<div class="pageContent">
	<%-- 数据列表 --%>
	<table class="table" width="1242" layoutH="135">
		<thead>
			<tr>
			    <th width="400">交易凭证号</th>
				<th width="300">卖方名称</th>
				<th width="300">买方名称</th>
				<th width="300" orderfield="tranDate" class="<c:if test="${order.field eq 'tranDate'}">${order.direction }</c:if>">交易时间</th>
			    <th width="100">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="marketTranInfoBase" items="${page.rows }" varStatus="seq">
			<tr target="hdr_id" rel="${marketTranInfoBase.hdrId }">
				<td align="center">${marketTranInfoBase.tranId }</td>
				<td align="center">${marketTranInfoBase.wholesalerName }</td>
				<td align="center">${marketTranInfoBase.retailerName }</td>
				<td align="center">
					<fmt:formatDate value="${marketTranInfoBase.tranDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			     <td align="center">
			       <a class="btnView"   href="${CTX }/trade/listTradeDetail.do?hdrId=${marketTranInfoBase.hdrId }" target="dialog" width="1000" height="300" rel="details" title="查看详情" maxable="false">查看明细</a>
				</td>
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
	<form id="pagerForm" action="${CTX }/trade/listTradeBase.do"
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
 