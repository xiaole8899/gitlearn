<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/kit/dwz/js/dwz.resetformvalue.js"></script>
<%@ include file="../commons/taglibs.jsp" %>

<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${CTX }/menu/functionList.do?menuId=${menuId}&parentId=${returnChildMenu}" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>功能名称：<input type="text" class="checkReset" name="functionName" value="${function.functionName}"/></td>
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
		    <li><a class="add" style="display:inline-block;" href="${CTX }/menu/toFunctionAdd.do?menuId=${menuId}" target="dialog" rel="tofunctionadd"><span>添加功能</span></a></li>
			<li><a class="edit" href="${CTX }/menu/toFunctionEdit.do?functionId={function_id}" target="dialog"><span>修改功能</span></a></li>
			<li><a class="delete" href="${CTX }/menu/deleteFunction.do?functionId={function_id}&menuId=${menuId}&parentId=${returnChildMenu}" target="ajaxTodo" title="确定要删除该功能吗?"><span>删除功能</span></a></li>
			<li class="line">line</li>
			<%--<li><a class="icon" href="${CTX }/area/toExport" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
		    --%>
		</ul>
	</div>
	<%-- 数据列表 --%>
	<table class="table" width="1200" layoutH="138">
		<thead>
			<tr>
			    <th width="100">序号</th>
				<th>功能名称</th>
				<th>地址(url)</th>
				<th>描述</th>
				<%--<th width="700">操作</th>
			--%></tr>
		</thead>
		<tbody>
		<c:forEach var="function" items="${page.rows}" varStatus="seq">
			<tr target="function_id" rel="${function.functionId}">
				<td align="center" style="font-weight: bold; font-style: italic;">${seq.count +(page.pageIndex-1) * page.pageSize }</td>
				<td align="center">${function.functionName }</td>
				<td align="center">${function.url }</td>
				<td align="center">${function.description }</td>
				<%--<td>
				   <a class="addChildMenu" style="display:inline-block;" href="${CTX }/permission/toChildMenu/${menus.menuId}" target="dialog"><font color="blue">增加子菜单</font></a> 
				   <a class="searchChildMenu" style="display:inline-block;" href="${CTX }/permission/toMenu/${menus.menuId}" target="dialog"><font color="blue">查看子菜单</font></a>
				</td>
			--%>
			</tr>
		</c:forEach>
		<c:if test="${page.totalRows==0}">
			<tr>
				<td colspan="5" align="center">
					暂无结果
				</td>
			</tr>
		</c:if>
		    <tr>
				<td colspan="4" style="text-align: center;">
					<a  onclick="goBackToChildMenu(${returnChildMenu})"  style="cursor: pointer;">返回上一级</a>
				</td>
			</tr>
		</tbody>
		
	</table>
	
	<div class="button-div">
		<input type="hidden" name="editChangeMenu" id="editChangeMenu" value="" />
		<input type="hidden" name="returnChildMenu" id="returnChildMenu" value="${returnChildMenu}" />
	</div>
	
	<%-- 分页查询的Form --%>
	<form id="pagerForm" action="${CTX }/menu/functionList.do?menuId=${menuId}&parentId=${returnChildMenu}" method="post">
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
	//返回上一步重新载入页面
	function goBackToChildMenu(menuId){
		navTab.reload("menu/toChildList.do?menuId="+menuId);
	}
</script>