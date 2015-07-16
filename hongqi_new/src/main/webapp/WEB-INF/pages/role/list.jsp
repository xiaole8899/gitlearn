<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/kit/dwz/js/dwz.resetformvalue.js"></script>
<%@ include file="../commons/taglibs.jsp" %>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${CTX }/role/list.do" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>角色名称：<input type="text" class="checkReset" value="${role.roleName}" name="roleName"/></td>
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
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${CTX }/role/toAdd.do" target="dialog" rel="roletoadd" width="400" height="400" maxable="false" resizable="false"><span>添加</span></a></li>
			<li><a class="delete" href="${CTX }/role/delete.do?roleId={role_id}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li><a class="edit" href="${CTX }/role/toEdit.do?roleId={role_id}" target="dialog" width="400" height="240" maxable="false" resizable="false"><span>修改</span></a></li>
			<li class="line">line</li>
			<li><a class="add" href="${CTX }/role/toAddPermission.do?roleId={role_id}" target="dialog" width="575" height="550" maxable='false' title="系统权限设置"><span>分配权限</span></a></li>
		</ul>
	</div>
	<%-- 数据列表 --%>
	<table class="table" width="1200" layoutH="138">
		<thead>
			<tr>
				<th width="40">序号</th>
				<th orderfield="role_name" class="<c:if test="${order.field eq 'role_name'}">${order.direction }</c:if>">角色名称</th>
				<th orderfield="descriptions" class="<c:if test="${order.field eq 'descriptions'}">${order.direction }</c:if>">角色描述</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="role" items="${page.rows }" varStatus="seq">
			<tr target="role_id" rel="${role.roleId }">
				<td align="right" style="font-weight: bold; font-style: italic;">${seq.count +(page.pageIndex-1) * page.pageSize }</td>
				<td align="center">${role.roleName }</td>
				<td align="center">${role.descriptions }</td>
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
	<form id="pagerForm" action="${CTX }/role/list.do" method="post">
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
