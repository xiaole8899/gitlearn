<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/kit/dwz/js/dwz.resetformvalue.js"></script>
<%@ include file="../commons/taglibs.jsp" %>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${CTX }/log/logList.do" method="post">
		 <div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>登陆日期从：<input type="text"  value="${loginDateStart }" name="loginDateStart" class="date checkReset" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" size="20"/> <a class="inputDateButton" href="javascript:;" style="float:right;">选择</a></td>
					<td>至：
						<input type="text" style="margin-left: 33px;" value="${loginDateEnd}" name="loginDateEnd" class="date checkReset" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true"  size="20"/> <a class="inputDateButton" href="javascript:;" style="float:right;">选择</a>
					</td>
					<td>登出日期从：<input type="text" value="${logoutDateStart }" name="logoutDateStart" class="date checkReset" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" size="20"/> <a class="inputDateButton" href="javascript:;" style="float:right;">选择</a></td>
					<td>至：
						<input type="text" value="${logoutDateEnd}" name="logoutDateEnd" class="date checkReset" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true"  size="20"/> <a class="inputDateButton" href="javascript:;" style="float:right;">选择</a>
					</td>
				</tr>
				<tr>
					<td>用户名：<input type="text" class="checkReset" name="userName" value="${log.userName }"  style="margin-left: 24px;" /></td>
					<td>真实姓名：<input type="text" class="checkReset" name="realName" value="${log.realName }" /></td>
					<td>结果：<input type="text" class="checkReset" name="descriptions" value="${log.descriptions }"  style="margin-left: 36px;" /></td>
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
			<li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="loginlog_ids" href="${CTX }/log/deleteLoginLog.do" class="delete"><span>批量删除</span></a></li>
		</ul>
	</div>
	<%-- 数据列表 --%>
	<table class="table" width="1200" layoutH="160">
		<thead>
			<tr>
				<th width="22"><input type="checkbox" group="loginlog_ids" class="checkboxCtrl"></th>
				<th width="40">序号</th>
				<th orderfield="userId" class="<c:if test="${order.field eq 'userId'}">${order.direction }</c:if>">用户ID</th>
				<th orderfield="sessionId" class="<c:if test="${order.field eq 'sessionId'}">${order.direction }</c:if>">SessionId</th>
				<th orderfield="userName" class="<c:if test="${order.field eq 'userName'}">${order.direction }</c:if>">用户名</th>
				<th orderfield="realName" class="<c:if test="${order.field eq 'realName'}">${order.direction }</c:if>">真实姓名</th>
				<th orderfield="descriptions" class="<c:if test="${order.field eq 'descriptions'}">${order.direction }</c:if>">结果</th>
				<th orderfield="ipAddress" class="<c:if test="${order.field eq 'ipAddress'}">${order.direction }</c:if>">IP地址</th>
				<th orderfield="loginTime" class="<c:if test="${order.field eq 'loginTime'}">${order.direction }</c:if>">登陆时间</th>
				<th orderfield="logoutTime" class="<c:if test="${order.field eq 'logoutTime'}">${order.direction }</c:if>">登出时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="log" items="${page.rows }" varStatus="seq">
			<tr>
				<td><input name="loginlog_ids" value="${log.logId }" type="checkbox"></td>
				<td align="center" style="font-weight: bold; font-style: italic;">${seq.count +(page.pageIndex-1) * page.pageSize }</td>
				<td align="center">
					<c:if test="${log.userId==0 }">
						无
					</c:if>
					<c:if test="${log.userId!=0 }">
						${log.userId }
					</c:if>
				<td align="center">
					<c:if test="${log.sessionId==null }">
						无
					</c:if>
					<c:if test="${log.sessionId!=null }">
						${log.sessionId}
					</c:if>
				</td>
				<td align="center">
					<c:if test="${log.userName==''}">
						无
					</c:if>
					<c:if test="${log.userName!='' }">
						${log.userName }
					</c:if>
				</td>
				<td align="center">
					<c:if test="${log.realName==null }">
						无
					</c:if>
					<c:if test="${log.realName!=null }">
						${log.realName }
					</c:if>
				</td>
				<td align="center">${log.descriptions}</td>
				<td align="center">${log.ipAddress}</td>
				<td align="center"><fmt:formatDate value="${log.loginTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td align="center"><fmt:formatDate value="${log.logoutTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
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
	<form id="pagerForm" action="${CTX }/log/logList.do" method="post">
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