<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/kit/dwz/js/dwz.resetformvalue.js"></script>	
<%@ include file="../commons/taglibs.jsp" %>
<div class="pageHeader">
  		<form rel="dd" id="orgList" onsubmit="return navTabSearch(this);" action="${CTX }/supplyMarket/listSupplyMarket.do" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>供货市场名称：<input  type="text" class="checkReset" id="name"  name="name" value="${marketc.name }"/></td>
					<td>供货市场编号：<input type="text" class="checkReset" id="no" name="no" value="${marketc.no }"/></td>
					<td>类型：
						<select  name ="sNodeType" class="checkResetSelect" style="width:150px;">
						  <option value="">请选择类型</option>  
	    		        	<c:forEach var="map" items="${map}">
                      			<option value="${map.key}"  <c:if test="${marketc.sNodeType==map.key }">selected="selected"</c:if>>${map.value}</option>  
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

     <%-- 增加，删除，修改按钮 --%>
	<div class="panelBar">
			<ul class="toolBar">
			<li><a class="add" href="${CTX }/supplyMarket/gotoAddSupplyMarket.do" target="dialog" drawable="false" width="600" height="320"><span>添加</span></a></li>
			<li><a class="delete" href="${CTX }/supplyMarket/deleteSupplyMarket.do?suId={market_id}" target="ajaxTodo" title="删除将删除其下级别,确定要删除吗?"><span>删除</span></a></li>
			<li><a class="edit" href="${CTX }/supplyMarket/gotoUpdateSupplyMarket.do?suId={market_id}" target="dialog"><span>修改</span></a></li>
		</ul>
	</div> 
	
	<%-- 数据列表 --%>
	<table class="table" width="1240" layoutH="138">
		<thead>
			<tr>
				<th orderfield="no" class="<c:if test="${order.field eq 'no'}">${order.direction }</c:if>">供货市场编号</th>
				<th orderfield="name" class="<c:if test="${order.field eq 'name'}">${order.direction }</c:if>">供货市场名称</th>
				<th>类型</th>
				<th>所属地区编码</th>
				<th>所属地区名称</th>
				<th>法人</th>
				<th>工商登记号</th>
				<th>企业地址</th>
				<th orderfield ="updateTime"  class="<c:if test="${order.field eq 'updateTime' }">${order.direction }</c:if>">备案时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="market" items="${page.rows }" varStatus="seq">
			<tr target="market_id" rel="${market.suId }">
				<td  align="center">
				<a style="color: blue;"  href="${CTX }/supplyMarket/SupplyMarketDetail.do?supplyMarketId=${market.suId }" target="dialog" width="440" height="500" title="查看详情" maxable="false">
					${market.no  }
				</a>
				</td>
				<td align="center">${market.name }</td>
				<td align="center">
    		    	<c:forEach var="map" items="${map}">
                    	<c:if test="${market.sNodeType==map.key }">${map.value}</c:if>  
                   	</c:forEach>
				</td>
				<td  align="center">${market.areaNo }</td>
				<td align="center">${market.areaName }</td>
				<td align="center">${market.legalRepresent }</td>
				<td align="center">${market.regId }</td>
				<td  align="center">${market.addr }</td>
				<td  align="center">${market.recordDate }</td>
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
	<form id="pagerForm" action="${CTX }/supplyMarket/listSupplyMarket.do"
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
