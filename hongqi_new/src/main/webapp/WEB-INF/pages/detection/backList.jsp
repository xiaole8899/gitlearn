<%@ page language="java" import="org.dppc.mtrace.app.AppConstant, org.dppc.mtrace.app.approach.entity.ApproachEntity" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/kit/dwz/js/dwz.resetformvalue.js"></script>	
<%@ include file="../commons/taglibs.jsp" %>
<% 
	Map<String, String> typeMap =(Map<String, String>)request.getAttribute("map");
%>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return dialogSearch(this);" action="${CTX }/detection/backList.do" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>批次号：<input type="text" class="checkReset" name="batchId" value="${approach.batchId}" name="roleName"/></td>
					<td>商户名称：<input type="text" class="checkReset" name="wholesalerName" value="${approach.wholesalerName}" name="roleName"/></td>
					<td>商品名称：<input type="text" class="checkReset" name="goodsName" value="${approach.goodsName}" name="roleName"/></td>
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
	<%-- 数据列表 --%>
	<table class="table"targetType="dialog" width="830" layoutH="138">
		<thead>
			<tr>
				<th width="40">序号</th>
				<th orderfield="batchId" class="<c:if test="${order.field eq 'batchId'}">${order.direction }</c:if>">批次号</th>
				<th orderfield="voucherType" class="<c:if test="${order.field eq 'voucherType'}">${order.direction }</c:if>">批次类型</th>
				<th orderfield="wholesalerId" class="<c:if test="${order.field eq 'wholesalerId'}">${order.direction }</c:if>">商户编码</th>
				<th orderfield="wholesalerName" class="<c:if test="${order.field eq 'wholesalerName'}">${order.direction }</c:if>">商户名称</th>
				<th orderfield="goodsCode" class="<c:if test="${order.field eq 'goodsCode'}">${order.direction }</c:if>">商品编码</th>
				<th orderfield="goodsName" class="<c:if test="${order.field eq 'goodsName'}">${order.direction }</c:if>">商品名称</th>
				<th width="80" align="center">查找带回</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="approach" items="${page.rows }" varStatus="seq">
			<tr target="id" rel="${approach.approachId }">
				<c:set scope="request" var="approachEntity" value="${approach }"/>
				<td align="right" style="font-weight: bold; font-style: italic;">${seq.count +(page.pageIndex-1) * page.pageSize }</td>
				<td align="center">${approach.batchId }</td>
				<td  align="center">
  					<% ApproachEntity approachEntity =(ApproachEntity)request.getAttribute("approachEntity"); %>
					<%=typeMap.get(approachEntity.getVoucherType()) %>
				</td>
				<td align="center">${approach.wholesalerId }</td>
				<td align="center">${approach.wholesalerName }</td>
				<td align="center">${approach.goodsCode }</td>
				<td align="center">${approach.goodsName }</td>
				<td>
					<a class="btnSelect" href="javascript:$.bringBack({approachId:'${approach.approachId }',batchId:'${approach.batchId }',batchType:'${approach.voucherType }', batchTypeName:'<%=typeMap.get(approachEntity.getVoucherType()) %>',wholesalerId:'${approach.wholesalerId }', wholesalerName:'${approach.wholesalerName }', goodsCode:'${approach.goodsCode }', goodsName:'${approach.goodsName }'})" >选择</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<%-- 分页查询的Form --%>
	<form id="pagerForm" action="${CTX }/detection/backList.do" method="post">
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
				<option value="20" <c:if test="${page.pageSize eq 20 }">selected</c:if>>20</option>
				<option value="50" <c:if test="${page.pageSize eq 50 }">selected</c:if>>50</option>
				<option value="100" <c:if test="${page.pageSize eq 100 }">selected</c:if>>100</option>
				<option value="200" <c:if test="${page.pageSize eq 200 }">selected</c:if>>200</option>
			</select>
			<span>条，共${page.totalRows}条</span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="${page.totalRows }" currentPage="${page.pageIndex }" numPerPage="${page.pageSize }"></div>
	</div>
</div>
