<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/kit/dwz/js/dwz.resetformvalue.js"></script>	
<%@ include file="../commons/taglibs.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="pageHeader">
	 <form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${CTX }/fundsRecord/tradeRecordList.do?status=1" method="post">
		 <div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><label>商户姓名：</label><input type="text" class="checkReset" name="userName" value="${record.userName}" />
					<td><label>商户备案号：</label><input type="text" class="checkReset" name="userNo" value="${record.userNo }" />
					<td><label>交易凭证号：</label><input type="text" class="checkReset" name="tranId" value="${record.tranId }"/></td>
				</tr>
				<tr>
				    <td><label>变动时间从：</label><input  type="text" name="startdate" class="date checkReset" value="${startdate }" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" size="20"/><a class="inputDateButton" href="javascript:;" style="float:right;">选择</a>
					</td>
					<td><label>至：</label><input type="text" name="enddate" class="date checkReset" value="${enddate }" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" size="20"/><a class="inputDateButton" href="javascript:;" style="float:right;" >选择</a>
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
	
	<table class="table" width="100%" layoutH="135">
		<thead>
			<tr>
				<th>序号</th>
				<th>商户姓名</th>
				<th>商户备案号</th>
				<th>变动金额</th>
				<th>交易凭证号</th>
				<th orderfield="changeTime" class="<c:if test="${order.field eq 'changeTime'}">${order.direction }</c:if>">变动时间</th>
				<!-- <th>操作</th> -->
			</tr>
		</thead>
		<tbody>
		<c:forEach var="record" items="${page.rows }" varStatus="seq">
			<tr target="f_id" rel="${record }">
				<td align="center">${seq.count +(page.pageIndex-1) * page.pageSize }</td> 
				<td  align="center">${record.userName }</td>
				<td  align="center">${record.userNo }</td>
				<td  align="center">${record.amount }</td>
				
				<td  align="center">${record.tranId }</td>
				<td  align="center">
					<fmt:formatDate value="${record.changeTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
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
	<form id="pagerForm" action="${CTX }/fundsRecord/tradeRecordList.do"
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
