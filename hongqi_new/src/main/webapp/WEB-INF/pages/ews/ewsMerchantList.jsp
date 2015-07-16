<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/kit/dwz/js/dwz.resetformvalue.js"></script>
<%@ include file="../commons/taglibs.jsp" %>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${CTX }/ews/ewsBindList.do" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
				    <td>电子秤IP地址：<input type="text" class="checkReset" name="ewsIp" value="${ews.ewsIp }" /></td>
					<td>商户名称：<input type="text" class="checkReset" name="salerName" value="${ews.salerName }" /></td>
					<td>商户编号：<input type="text" class="checkReset" name="salerNumber"  /></td>
				</tr> 
				
				<tr>
				    <td>绑定时间：
				        <input type="text" name="statrdate" class="date checkReset " dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" size="18" style="margin-left: 20px" />
				        <a class="inputDateButton" href="javascript:;" style="float:right;">选择</a>
				    </td>
				    <td>至
					    <input type="text" name="enddate" class="date checkReset " dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" size="18" style="margin-left: 45px" />
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
			<li><a class="icon" href="${CTX }/ews/toBindMerchant.do?ewsId={ews_id}" target="dialog" title="绑定商户" width="600" ref="ddBindMerchant"><span>绑定商户</span></a></li>
		</ul>
	</div>
	<%-- 数据列表 --%>
	<table class="table" width="1200" layoutH="138">
		<thead>
			<tr>
				<th width="40">序号</th>
				<th orderfield="ewsSn" class="<c:if test="${order.field eq 'ewsSn'}">${order.direction }</c:if>">SN号</th>
				<th orderfield="ewsIp" class="<c:if test="${order.field eq 'ewsIp'}">${order.direction }</c:if>">电子秤IP地址</th>
				<th orderfield="ewsMac" class="<c:if test="${order.field eq 'ewsMac'}">${order.direction }</c:if>">电子秤MAC地址</th>
				<th orderfield="salerName" class="<c:if test="${order.field eq 'salerName'}">${order.direction }</c:if>">绑定商户名称</th>
				<th orderfield="salerNumber" class="<c:if test="${order.field eq 'salerNumber'}">${order.direction }</c:if>">绑定商户编号</th>
				<th orderfield="bindTime" class="<c:if test="${order.field eq 'bindTime'}">${order.direction }</c:if>">绑定时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="ews" items="${page.rows }" varStatus="seq">
			<tr target="ews_id" rel="${ews.ewsId }">
				<td align="center" style="font-weight: bold; font-style: italic;">${seq.count +(page.pageIndex-1) * page.pageSize }</td>
				<td align="center">${ews.ewsSn}</td>
				<td align="center">${ews.ewsIp}</td>
				<td align="center">${ews.ewsMac}</td>
				<td align="center">${ews.salerName}</td>
				<td align="center">${ews.salerNumber}</td>
				<td align="center">${ews.bindTime}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<%-- 分页查询的Form --%>
	<form id="pagerForm" action="${CTX }/ews/ewsBindList.do" method="post">
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