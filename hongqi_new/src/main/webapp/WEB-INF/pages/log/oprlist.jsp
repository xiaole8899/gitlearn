<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/kit/dwz/js/dwz.resetformvalue.js"></script>
<%@ include file="../commons/taglibs.jsp" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${CTX }/operationLog/getOptList.do" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>操作人用户名：<input type="text" class="checkReset" name="optUserName" value="${operationLog.optUserName }" /></td>
					<td>操作人真实姓名：<input type="text" class="checkReset" name="optRealName" value="${operationLog.optRealName }" /></td>
				</tr> 
				<tr>
					<td>操作日期从：<input type="text" style="margin-left: 11px;" value="${operationLog.startTime }" name="startTime" class="date checkReset" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" size="20"/> 
					<a class="inputDateButton" href="javascript:;" style="float:right;">选择</a></td>
					<td>至：
						<input style="margin-left: 69px;" type="text" value="${operationLog.endTime}" name="endTime" class="date checkReset" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true"  size="20"/> <a class="inputDateButton" href="javascript:;" style="float:right;">选择</a>
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
			<li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="orglog_ids" href="${CTX }/operationLog/deleteOprLog.do" class="delete"><span>批量删除</span></a></li>
		</ul>
	</div>
	<%-- 数据列表 --%>
	<table class="table" width="1200" layoutH="163">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="orglog_ids" class="checkboxCtrl"></th>
				<th width="40">序号</th>
				<th orderfield="optUserId" class="<c:if test="${order.field eq 'optUserId'}">${order.direction }</c:if>">操作人编号</th>
				<th orderfield="optUserName" class="<c:if test="${order.field eq 'optUserName'}">${order.direction }</c:if>">操作人用户名</th>
				<th orderfield="optRealName" class="<c:if test="${order.field eq 'optRealName'}">${order.direction }</c:if>">操作人真实姓名</th>
				<th orderfield="optIp" class="<c:if test="${order.field eq 'optIp'}">${order.direction }</c:if>">操作人IP地址</th>
				<th orderfield="optTime" class="<c:if test="${order.field eq 'optTime'}">${order.direction }</c:if>">操作时间</th>
				<th orderfield="optUrl" class="<c:if test="${order.field eq 'optUrl'}">${order.direction }</c:if>">操作路径</th>
				<th orderfield="optActionName" class="<c:if test="${order.field eq 'optActionName'}">${order.direction }</c:if>">操作</th>
				<th orderfield="optResult" class="<c:if test="${order.field eq 'optResult'}">${order.direction }</c:if>">操作结果</th>
				<th orderfield="optComment" class="<c:if test="${order.field eq 'optComment'}">${order.direction }</c:if>">操作备注</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="opr" items="${page.rows }" varStatus="seq">
			<tr>
				<td><input name="orglog_ids" value="${opr.optId }" type="checkbox"></td>
				<td align="center" style="font-weight: bold; font-style: italic;">${seq.count +(page.pageIndex-1) * page.pageSize }</td>
				<td align="center">${opr.optUserId}</td>
				<td align="center">${opr.optUserName}</td>
				<td align="center">${opr.optRealName}</td>
				<td align="center">${opr.optIp}</td>
				<td align="center">
					<fmt:formatDate value="${opr.optTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td align="center">${opr.optUrl}</td>
				<td align="center">${opr.optActionName}</td>
				<td align="center">${opr.optResult}</td>
				<td align="center">${opr.optComment}</td>
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
	<form id="pagerForm" action="${CTX }/operationLog/getOptList.do" method="post">
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