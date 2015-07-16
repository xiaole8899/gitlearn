<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/kit/dwz/js/dwz.resetformvalue.js"></script>
<%@ include file="../commons/taglibs.jsp" %>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${CTX }/ic/listIc.do" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>IC读写器名称：<input type="text" class="checkReset" name="icName" value="${ic.icName }" /></td> 
				<!-- </tr> 
				
				<tr> -->
				    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				        时间：
				        <input type="text" name="statrdate" class="date checkReset " dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" size="18" style="margin-left: 20px"/>
				        <a class="inputDateButton" href="javascript:;" style="float:right;">选择</a>
				    </td>
				    <td>
				        至
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
			<li><a class="add" href="${CTX }/ic/toAddIc.do" target="dialog" rel="ictoadd"><span>添加IC卡读写器</span></a></li>
			<li><a class="edit" href="${CTX }/ic/toUpdateIc.do?icId={ic_id}" target="dialog"><span>修改IC卡读写器</span></a></li>
			<li><a class="delete" href="${CTX }/ic/deleteIc.do?icId={ic_id}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<%-- <li><a class="icon" href="${CTX }/ews/toBindEws.do?fecId={fec_id}" target="dialog" title="绑定前置机"><span>绑定电子秤</span></a></li> --%>
		</ul>
	</div>
	<%-- 数据列表 --%>
	<table class="table" width="1200" layoutH="138">
		<thead>
			<tr>
				<th width="40">序号</th>
				<th orderfield="icName" class="<c:if test="${order.field eq 'icName'}">${order.direction }</c:if>">IC卡读写器名称</th>
				<th orderfield="inDate" class="<c:if test="${order.field eq 'inDate'}">${order.direction }</c:if>">时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="ic" items="${page.rows }" varStatus="seq">
			<tr target="ic_id" rel="${ic.icId }">
				<td align="center" style="font-weight: bold; font-style: italic;">${seq.count +(page.pageIndex-1) * page.pageSize }</td>
				<td align="center">${ic.icName}</td>
				<td align="center">${ic.inDate}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<%-- 分页查询的Form --%>
	<form id="pagerForm" action="${CTX }/ic/listIc.do" method="post">
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