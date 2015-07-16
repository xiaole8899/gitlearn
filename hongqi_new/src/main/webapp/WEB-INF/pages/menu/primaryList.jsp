<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/kit/dwz/js/dwz.resetformvalue.js"></script>
<%@ include file="../commons/taglibs.jsp" %>

<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${CTX }/menu/primaryList.do" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>菜单名称：<input type="text" class="checkReset" name="menuName" value="${menu.menuName}"/></td>
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
			<li><a class="add" href="${CTX }/menu/toPrimaryAdd.do?parentId=0" target="dialog" rel="toprimaryadd"><span>添加主菜单</span></a></li>
			<li><a class="edit" href="${CTX }/menu/toPrimaryEdit.do?menuId={menu_id}" target="dialog"><span>修改主菜单</span></a></li>
			<li><a class="delete" href="${CTX }/menu/deletePrimary.do?menuId={menu_id}" target="ajaxTodo" title="删除主菜单，将会删除其子菜单及功能，确定要删除吗?"><span>删除主菜单</span></a></li>
			
			<li class="line">line</li>
		</ul>
	</div>
	<%-- 数据列表 --%>
	<table class="table" width="1200" layoutH="138">
		<thead>
			<tr><%--
			    orderfield="menu_name" class="<c:if test="${order.field eq 'menu_name'}">${order.direction }</c:if>"
			    --%>
			    <th width="100">主菜单编号</th>
			    <th orderfield="menuName" class="<c:if test="${order.field eq 'menuName'}">${order.direction }</c:if>">主菜单名称</th>
				<th>地址(url)</th>
				<th>描述</th>
				<th orderfield="sort" class="<c:if test="${order.field eq 'sort'}">${order.direction }</c:if>">排序</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="menus" items="${page.rows }" varStatus="seq">
			<tr target="menu_id" rel="${menus.menuId }">
				<td align="center" style="font-weight: bold; font-style: italic;">${seq.count +(page.pageIndex-1) * page.pageSize }</td>
				<td align="center">${menus.menuName }</td>
				<td align="center">${menus.url }</td>
				<td align="center">${menus.description }</td>
				<td align="center">${menus.sort}</td>
				<td align="center">
				   <%--<a class="addChildMenu" style="display:inline-block;" href="${CTX }/permission/toChildMenu/${menus.menuId}/0" target="dialog"><font color="blue">增加子菜单</font></a>&nbsp;&nbsp;&nbsp;&nbsp; 
				   --%>
				   <%--<a class="searchChildMenu" style="display:inline-block;" href="${CTX }/permission/toChildList/${menus.menuId}" target="navTab"><font color="blue">查看子菜单</font></a>
				   --%>
				   <a class="searchChildMenu" style="display:inline-block; cursor: pointer; color:blue;" onclick="goSecondChildMenuReturn(${menus.menuId})">查看子菜单</a>
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
	<div class="button-div">
		<input type="hidden" name="editChangeMenu" id="editChangeMenu" value="" />
	</div>
	<%-- 分页查询的Form --%>
	<form id="pagerForm" action="${CTX }/menu/primaryList.do" method="post">
	    <input type="hidden" name="menuNameSearch" value="${menuName}"/>
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

<script type="text/javascript">
	//定位当前tab
	function goSecondChildMenuReturn(id){
		navTab.reload("menu/toChildList.do?menuId="+id);
	}
</script>

