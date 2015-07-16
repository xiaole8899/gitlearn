<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/kit/dwz/js/dwz.resetformvalue.js"></script>
<%@ include file="../commons/taglibs.jsp" %>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${CTX }/user/list.do" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>用户名：<input type="text" class="checkReset" name="userName" value="${user.userName }" /></td>
					<td>真实姓名：<input type="text" class="checkReset" name="realName" value="${user.realName }" /></td>
					<td>部门名称：<input type="text" class="checkReset" name="department" value="${user.department }" /></td>
					<td>职位：<input type="text" class="checkReset" name="position" value="${user.position }" /></td>
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
			<li><a class="add" href="${CTX }/user/toAdd.do" target="dialog" width="520" height="400" maxable="false" resizable="false" rel="usertoadd"><span>添加用户</span></a></li>
			<li><a class="delete" href="${CTX }/user/delete.do?userId={user_id}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li><a class="edit" href="${CTX }/user/toEdit.do?userId={user_id}" width="520" height="350" maxable="false" resizable="false" rel="userToEdit" target="dialog"><span>修改</span></a></li>
			<li><a class="add" href="${CTX }/user/roleAdd.do?userId={user_id}" rel="userRoleAdd" target="dialog"><span>分配角色</span></a></li>
		</ul>
	</div>
	<%-- 数据列表 --%>
	<table class="table" width="1200" layoutH="138">
		<thead>
			<tr>
				<th width="40">序号</th>
				<th orderfield="userName" class="<c:if test="${order.field eq 'userName'}">${order.direction }</c:if>">用户名</th>
				<th orderfield="realName" class="<c:if test="${order.field eq 'realName'}">${order.direction }</c:if>">真实姓名</th>
				<th orderfield="department" class="<c:if test="${order.field eq 'department'}">${order.direction }</c:if>">部门名称</th>
				<th orderfield="position" class="<c:if test="${order.field eq 'position'}">${order.direction }</c:if>">职位</th>
				<th orderfield="mobilePhone" class="<c:if test="${order.field eq 'mobilePhone'}">${order.direction }</c:if>">手机</th>
				<th orderfield="telephone" class="<c:if test="${order.field eq 'telephone'}">${order.direction }</c:if>">电话</th>
				<th orderfield="email" class="<c:if test="${order.field eq 'email'}">${order.direction }</c:if>">邮箱</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="user" items="${page.rows }" varStatus="seq">
			<tr target="user_id" rel="${user.userId }">
				<td align="center" style="font-weight: bold; font-style: italic;">${seq.count +(page.pageIndex-1) * page.pageSize }</td>
				<td align="center">${user.userName}</td>
				<td align="center">${user.realName}</td>
				<td align="center">${user.department}</td>
				<td align="center">${user.position}</td>
				<td align="center">${user.mobilePhone}</td>
				<td align="center">${user.telephone}</td>
				<td align="center">${user.email}</td>
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
	<form id="pagerForm" action="${CTX }/user/list.do" method="post">
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