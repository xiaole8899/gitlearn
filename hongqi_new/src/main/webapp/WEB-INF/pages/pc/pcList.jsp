<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/kit/dwz/js/dwz.resetformvalue.js"></script>
<%@ include file="../commons/taglibs.jsp" %>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${CTX }/pc/listPc.do" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>IP地址：<input type="text" class="checkReset" name="pcIp" value="${pc.pcIp}" /></td>
					<td>mac地址：<input type="text" class="checkReset" name="mac" value="${pc.mac}" /></td>
					<td>主板型号：<input type="text" class="checkReset" name="mainBoard" value="${pc.mainBoard}" /></td>
					<td>显卡型号：<input type="text" class="checkReset" name="vgaDriver" value="${pc.vgaDriver}"  /></td>
				</tr>
				<tr>
					<td>
					    时间：&nbsp;&nbsp;
					    <input type="text" name="statrdate" class="date checkReset" value="${startdate }"  dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" size="20"  />
					    <a class="inputDateButton" href="javascript:;" style="float:right;">选择</a>
					</td>
					<td>
					    至
					    <input type="text" name="enddate" class="date checkReset" value="${enddate }" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" size="20" style="margin-left:44px;" />
					    <a class="inputDateButton" href="javascript:;" style="float:right;">选择</a>
					</td>
				</tr> 
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button id="button_thing" type="submit">检索</button></div></div></li>
					<li><div class="buttonActive"><div class="buttonContent"><button class='resetForm'>重置</button></div></div></li>>
				</ul>
			</div>
		</div>	
	</form>
</div>


<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${CTX }/pc/toAddPc.do" target="dialog" rel="pctoadd"><span>添加PC机</span></a></li>
			<li><a class="edit" href="${CTX }/pc/toUpdatePc.do?pcId={pc_id}" target="dialog"><span>修改PC机</span></a></li>
			<li><a class="delete" href="${CTX }/pc/deletePc.do?pcId={pc_id}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<%-- <li><a class="icon" href="${CTX }/ews/toBindFec.do?ewsId={ews_id}" target="dialog" title="绑定IC卡"><span>绑定IC卡</span></a></li> --%>
		</ul>
	</div>
	<%-- 数据列表 --%>
	<table class="table" width="1200" layoutH="162">
		<thead>
			<tr>
				<th width="40">序号</th>
				<th orderfield="pcIp" class="<c:if test="${order.field eq 'pcIp'}">${order.direction }</c:if>">IP地址</th>
				<th orderfield="mac" class="<c:if test="${order.field eq 'mac'}">${order.direction }</c:if>">MAC地址</th>
				<th orderfield="mainBoard" class="<c:if test="${order.field eq 'mainBoard'}">${order.direction }</c:if>">主板型号</th>
				<th orderfield="vgaDriver" class="<c:if test="${order.field eq 'vgaDriver'}">${order.direction }</c:if>">显卡型号</th>
				<th orderfield="cpu" class="<c:if test="${order.field eq 'cpu'}">${order.direction }</c:if>">CPU</th>
				<th orderfield="memoryBank" class="<c:if test="${order.field eq 'memoryBank'}">${order.direction }</c:if>">内存</th>
				<th orderfield="monito" class="<c:if test="${order.field eq 'monito'}">${order.direction }</c:if>">显示器</th>
				<th orderfield="inDate" class="<c:if test="${order.field eq 'inDate'}">${order.direction }</c:if>">时间</th>
				   
				   
			</tr>
		</thead>
		<tbody>
		<c:forEach var="pc" items="${page.rows }" varStatus="seq">
			<tr target="pc_id" rel="${pc.pcId }">
				<td align="center" style="font-weight: bold; font-style: italic;">${seq.count +(page.pageIndex-1) * page.pageSize }</td>
				<td align="center">${pc.pcIp}</td>
				<td align="center">${pc.mac}</td>
				<td align="center">${pc.mainBoard}</td>
				<td align="center">${pc.vgaDriver}</td>
				<td align="center">${pc.cpu}</td>
				<td align="center">${pc.memoryBank}</td>
				<td align="center">${pc.monito}</td>
				<td align="center">${pc.inDate}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<%-- 分页查询的Form --%>
	<form id="pagerForm" action="${CTX }/pc/listPc.do" method="post">
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