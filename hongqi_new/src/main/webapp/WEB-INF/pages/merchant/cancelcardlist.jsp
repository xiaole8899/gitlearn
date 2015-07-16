<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/kit/dwz/js/dwz.resetformvalue.js"></script>
<%@ include file="../commons/taglibs.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="pageHeader">
	 <form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${CTX }/merchant/cancelCardList.do" method="post">
		 <div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><label>卡号：</label><input type="text" class="checkReset" name="cardNo" value="${mCard.cardNo }" />
					</td>
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
	 <%-- <div class="panelBar">
		<ul class="toolBar">
			<li><a class="delete" href="${CTX }/merchants/deleteMerchant.do?mId={m_id}" target="ajaxTodo" title="确定要注销此用户吗?"><span>删除</span></a></li>
			<li><a class="edit" href="${CTX }/merchants/toUpdateMerchant.do?mId={m_id}" target="dialog"><span>修改</span></a></li>
		</ul>
	</div>  --%>
	<%-- 数据列表 --%>
	<table class="table" width="100%" layoutH="110">
		<thead>
			<tr>
				<th>序号</th>
				<th>持卡人</th>
				<th>持卡人证件/工商登记号</th>
				<th>联系电话</th>
				<th>卡类型</th>
				<th>卡号</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="mcard" items="${page.rows }" varStatus="seq">
			<tr target="c_id" rel="${mcard.id }">
				<td align="center">${seq.count +(page.pageIndex-1) * page.pageSize }</td>
				<td align="center">
					${mcard.cardHolder }
				</td>
				<td align="center">
					${mcard.cardHolderIdentity }
				</td>
				<td  align="center">${mcard.holderTel }</td>
				<td  align="center">
					服务卡
				</td>
				<td  align="center">${mcard.cardNo }</td>
				<td  align="center">
					<a class="btnDel" href="${CTX }/merchant/deleteCard.do?cId=${mcard.id }" target="ajaxTodo" title="确定要永久删除此卡吗?">删除</a>
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
	<form id="pagerForm" action="${CTX }/merchant/cancelCardList.do"
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
				<option value="20" <c:if test="${page.pageSize eq 20 }">selected</c:if>>20</option>
				<option value="50" <c:if test="${page.pageSize eq 50 }">selected</c:if>>50</option>
				<option value="100" <c:if test="${page.pageSize eq 100 }">selected</c:if>>100</option>
				<option value="200" <c:if test="${page.pageSize eq 200 }">selected</c:if>>200</option>
			</select>
			<span>条，共${page.totalRows}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${page.totalRows }" currentPage="${page.pageIndex }" numPerPage="${page.pageSize }"></div>
	</div>
</div>
