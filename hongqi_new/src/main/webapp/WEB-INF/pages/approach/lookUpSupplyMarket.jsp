<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/kit/dwz/js/dwz.resetformvalue.js"></script>	
<%@ include file="../commons/taglibs.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="pageHeader">
	 <form rel="pagerForm" onsubmit="return dialogSearch(this);" action="${CTX }/approach/findSuppMarket.do" method="post">
		 <div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><label>供货市场名称：</label><input type="text" class="checkReset" name="name" /></td>
					<td><label>供货市场编号：</label><input type="text" class="checkReset" name="no" /></td>
		          <td>类型：
						<select  name ="sNodeType"  class="checkResetSelect"
								style="width:150px;">  
	    		        	<c:forEach var="map" items="${map}">
                      			<option value="${map.key}"  <c:if test="${market.sNodeType==map.key }">selected="selected"</c:if> >${map.value}</option>  
                           </c:forEach>
                        </select>   
					</td>				
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
	 <%-- <div class="panelBar">
		<ul class="toolBar">
			<li><a class="delete" href="${CTX }/merchants/deleteMerchant.do?mId={m_id}" target="ajaxTodo" title="确定要注销此用户吗?"><span>删除</span></a></li>
			<li><a class="edit" href="${CTX }/merchants/toUpdateMerchant.do?mId={m_id}" target="dialog"><span>修改</span></a></li>
		</ul>
	</div>  --%>
	<%-- 数据列表 --%>
	<table class="table" width="100%" layoutH="110">
		<thead>
			<tr>
				<th orderfield="comp_no" class="<c:if test="${order.field eq 'comp_no'}">${order.direction }</c:if>">供货市场编号</th>
				<th orderfield="comp_name" class="<c:if test="${order.field eq 'comp_name'}">${order.direction }</c:if>">供货市场名称</th>
				<th>类型</th>
				<th>所属地区编码</th>
				<th>所属地区名称</th>
				<th>企业地址</th>
				<th>添加时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="market" items="${page.rows }" varStatus="seq">
			<tr target="market_id" rel="${market.suId }" ondblclick="$.bringBack({no:'${market.no  }', name:'${market.name }'})" style="cursor: pointer;">
				<td  align="center">${market.no }</td>
				<td align="center">${market.name }</td>
				<td align="center">
					<c:forEach var="map" items="${map}">
                    	<c:if test="${market.sNodeType==map.key }">${map.value}</c:if>  
                   	</c:forEach>
				</td>
				<td  align="center">${market.areaNo }</td>
				<td align="center">${market.areaName }</td>
				<td  align="center">${market.addr }</td>
				<td  align="center">${market.recordDate }</td>
				<td>
			       <a class="btnSelect" href="javascript:$.bringBack({no:'${market.no  }', name:'${market.name }'})" title="查找带回">选择</a>
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
	
	
	<%-- 分页查询的Form --%>
	<form id="pagerForm" action="${CTX }/approach/findSuppMarket.do"
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
			<select class="combox" name="numPerPage"  onchange="dialogPageBreak({numPerPage:this.value})">
				<option value="20" <c:if test="${page.pageSize eq 20 }">selected</c:if>>20</option>
				<option value="50" <c:if test="${page.pageSize eq 50 }">selected</c:if>>50</option>
				<option value="100" <c:if test="${page.pageSize eq 100 }">selected</c:if>>100</option>
				<option value="200" <c:if test="${page.pageSize eq 200 }">selected</c:if>>200</option>
			</select>
			<span>条，共${page.totalRows}条</span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="${page.totalRows }" currentPage="${page.pageIndex }" numPerPage="${page.pageSize }"></div>
	</div>
</div>
