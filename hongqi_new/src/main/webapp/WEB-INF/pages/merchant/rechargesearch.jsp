<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/kit/dwz/js/dwz.resetformvalue.js"></script>	
<%@ include file="../commons/taglibs.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="pageHeader">
	 <form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${CTX }/fundsRecord/rechargeList.do?status=1" method="post">
		 <div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<label>持卡人：</label>
						<input type="text" class="checkReset" name="bizName" value="${bizName }"  />
					</td>
					<td>
						<label>证件号码：</label>
						<input type="text" class="checkReset" name="identityCard"  />
					</td>
					<td>
						<label>卡号：</label>
						<input type="text" class="checkReset" name="cardNo" value="${cardNo }"  />
					</td>
				</tr>
				<tr style="margin-top: 10px;">
					<td><label>充值时间从：</label><input  type="text" name="startDate" class="date checkReset" value="${startDate }" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" size="20"/><a class="inputDateButton" href="javascript:;" style="float:right;">选择</a>
					</td>
					<td><label>至：</label><input type="text" name="endDate" class="date checkReset" value="${endDate }" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" size="20"/><a class="inputDateButton" href="javascript:;" style="float:right;" >选择</a>
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
	<table class="table" width="102%" layoutH="135">
		<thead>
			<tr>
				<th>序号</th>
				<th>用户名称</th>
				<th>用户编码</th>
				<th>操作类型</th>
				<th orderfield="record.amount" class="<c:if test="${order.field eq 'record.amount'}">${order.direction }</c:if>">操作金额</th>
				<th>余额</th>
				<th  orderfield="record.changeTime" class="<c:if test="${order.field eq 'record.changeTime'}">${order.direction }</c:if>">充值日期</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="record" items="${page.rows }" varStatus="seq">
				<tr target="f_id" rel="${record }">
					<td align="center">${seq.count +(page.pageIndex-1) * page.pageSize }</td> 
					<td  align="center">${record.userName }</td>
					<td  align="center">${record.userNo }</td>
					<td  align="center">
						<c:if test="${record.type=='001' }">充值</c:if>
						<c:if test="${record.type=='003' }">取现</c:if>
					</td>
					<c:if test="${record.type=='001' }">
						<td style="color: green;"  align="center">${record.amount }</td>
					</c:if>
					<c:if test="${record.type=='003' }">
						<td  align="center" style="color: red;">${record.amount }</td>
					</c:if>
					<td align="center">${record.balance }</td>
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
	<form id="pagerForm" action="${CTX }/fundsRecord/rechargeList.do?status=1"
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
