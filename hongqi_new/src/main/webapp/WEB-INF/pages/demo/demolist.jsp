<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="../commons/taglibs.jsp" %>
<div class="pageHeader">
	<%-- <form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${CTX }/demo/getDemoList.do" method="post">
		 <div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>类型名称：<input type="text" name="name" value="${name }" />
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
				</ul>
			</div>
		</div>	 
	</form> --%>
</div>


<div class="pageContent">
	 <div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${CTX }/demo/toAddDemo.do" target="dialog" drawable="false" width="600" height="300"><span>添加</span></a></li>
			<li><a class="delete" href="${CTX }/demo/deleteDemo.do?demoId={demo_id}" target="ajaxTodo" title="删除将删除其下级别,确定要删除吗?"><span>删除</span></a></li>
			<li><a class="edit" href="${CTX }/demo/toUpdateDemo.do?demoId={demo_id}" target="dialog"><span>修改</span></a></li>
		</ul>
	</div> 
	<%-- 数据列表 --%>
	<table class="table" width="1240" layoutH="85">
		<thead>
			<tr>
				<th orderfield="demoId" class="<c:if test="${order.field eq 'demoId'}">${order.direction }</c:if>" width="450">序号</th>
				<th orderfield="demoName" class="<c:if test="${order.field eq 'demoName'}">${order.direction }</c:if>">示例名称</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="demo" items="${page.rows }" varStatus="seq">
			<tr target="demo_id" rel="${demo.demoId }">
				<td align="center">${demo.demoId }</td>
				<td  align="center">${demo.demoName }</td>
			</tr>
		</c:forEach>
		<c:if test="${rows==0}">
			<tr>
				<td colspan="5" align="center">
					暂无结果
				</td>
			</tr>
		</c:if>
		</tbody>
	</table>
	
	
	<%-- 分页查询的Form --%>
	<form id="pagerForm" action="${CTX }/demo/getDemoList.do"
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
