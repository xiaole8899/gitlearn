<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/kit/dwz/js/dwz.resetformvalue.js"></script>
<%@ include file="../commons/taglibs.jsp" %>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${CTX }/balance/listBalance.do" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
				    <td>小秤编号：<input type="text" class="checkReset" name="balanceNo" value="${balance.balanceNo }" /></td>
					<td>商户名称：<input type="text" class="checkReset" name="bizName" value="${balance.bizName }" /></td>
					<td>商户编号：<input type="text" class="checkReset" name="bizId" value="${balance.bizId }" /></td>
				</tr> 
				
				<tr>
				    <td>绑定时间：
				        <input type="text" name="statrdate" class="date checkReset " value="${startdate }" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" size="18" style="margin-left: 20px" />
				        <a class="inputDateButton" href="javascript:;" style="float:right;">选择</a>
				    </td>
				    <td>至
					    <input type="text" name="enddate" class="date checkReset " value="${enddate }" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" size="18" style="margin-left: 45px" />
					    <a class="inputDateButton" href="javascript:;" style="float:right;">选择</a>
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


<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
		    <li><a class="add" href="${CTX }/balance/toBalanceAdd.do" target="dialog" rel="toBalanceAdd"><span>添加小秤</span></a></li>
			<li><a class="edit" href="${CTX }/balance/toUpdateBalance.do?baId={balance_id}" target="dialog"><span>修改小秤</span></a></li>
			<li><a class="delete" href="${CTX }/balance/deleteBalance.do?baId={balance_id}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li><a class="icon" href="${CTX }/balance/toBindMerchant.do?baId={balance_id}" target="dialog" title="绑定商户" width="600" ref="ddBindMerchant"><span>绑定商户</span></a></li>
			<li><a class="delete" href="${CTX }/balance/deleteBindMerchant.do?baId={balance_id}" target="ajaxTodo" title="确定解除商户绑定吗？" ><span>解除商户绑定</span></a></li>
			
		</ul>
	</div>
	<%-- 数据列表 --%>
	<table class="table" width="1200" layoutH="163">
		<thead>
			<tr>
				<th width="40">序号</th>
				<th orderfield="balanceNo" class="<c:if test="${order.field eq 'balanceNo'}">${order.direction }</c:if>">小秤编号</th>
				<th orderfield="boothNo" class="<c:if test="${order.field eq 'boothNo'}">${order.direction }</c:if>">绑定摊位号</th>
				<th orderfield="bizName" class="<c:if test="${order.field eq 'bizName'}">${order.direction }</c:if>">绑定商户名称</th>
				<th orderfield="bizId" class="<c:if test="${order.field eq 'bizId'}">${order.direction }</c:if>">绑定商户编号</th>
				<th orderfield="boundTime" class="<c:if test="${order.field eq 'boundTime'}">${order.direction }</c:if>">绑定时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="balance" items="${page.rows }" varStatus="seq">
			<tr target="balance_id" rel="${balance.baId }">
				<td align="center" style="font-weight: bold; font-style: italic;">${seq.count +(page.pageIndex-1) * page.pageSize }</td>
				<td align="center">${balance.balanceNo}</td>
				<td align="center">${balance.boothNo}</td>
				<td align="center">${balance.bizName}</td>
				<td align="center">${balance.bizId}</td>
				<td align="center">${balance.boundTime}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<%-- 分页查询的Form --%>
	<form id="pagerForm" action="${CTX }/balance/listBalance.do"" method="post">
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