<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/kit/dwz/js/dwz.resetformvalue.js"></script>
<%@ include file="../commons/taglibs.jsp" %>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${CTX }/ews/listFec.do" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>前置机IP地址：<input type="text" class="checkReset" name="fecIp" value="${fec.fecIp }" /></td>
					<td>前置机端口：<input type="text" class="checkReset" name="port" value="${fec.port }" /></td>
				</tr> 
				
				<tr>
				    <td>绑定时间：
				        <input type="text" name="statrdate" class="date checkReset " dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" size="18" style="margin-left: 20px"/>
				        <a class="inputDateButton" href="javascript:;" style="float:right;">选择</a>
				    </td>
				    <td>至
				        <input type="text" name="enddate" class="date checkReset " dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" size="18" style="margin-left: 56px" />
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
			<li><a class="add" href="${CTX }/ews/toAddFec.do" target="dialog" rel="ewstoadd"><span>添加前置机</span></a></li>
			<li><a class="edit" href="${CTX }/ews/toUpdateFec.do?fecId={fec_id}" target="dialog"><span>修改前置机</span></a></li>
			<li><a class="delete" href="${CTX }/ews/deleteFec.do?fecId={fec_id}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<%-- <li><a class="icon" href="${CTX }/ews/toBindEws.do?fecId={fec_id}" target="dialog" title="绑定前置机"><span>绑定电子秤</span></a></li> --%>
		</ul>
	</div>
	<%-- 数据列表 --%>
	<table class="table" width="1200" layoutH="138">
		<thead>
			<tr>
				<th width="40">序号</th>
				<th orderfield="fecIp" class="<c:if test="${order.field eq 'fecIp'}">${order.direction }</c:if>">前置机IP地址</th>
				<th orderfield="port" class="<c:if test="${order.field eq 'port'}">${order.direction }</c:if>">前置机端口号</th>
				<th orderfield="bindTime" class="<c:if test="${order.field eq 'bindTime'}">${order.direction }</c:if>">绑定时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="fec" items="${page.rows }" varStatus="seq">
			<tr target="fec_id" rel="${fec.fecId }">
				<td align="center" style="font-weight: bold; font-style: italic;">${seq.count +(page.pageIndex-1) * page.pageSize }</td>
				<td align="center">${fec.fecIp}</td>
				<td align="center">${fec.port}</td>
				<td align="center">${fec.bindTime}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<%-- 分页查询的Form --%>
	<form id="pagerForm" action="${CTX }/ews/listFec.do" method="post">
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