<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/kit/dwz/js/dwz.resetformvalue.js"></script>	
<%@ include file="../commons/taglibs.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="pageHeader">
	 <form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${CTX }/costs/costsList.do" method="post">
		 <div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><label>费用名称</label><input type="text" class="checkReset" name="costsName" value="${costsSet.costsName }" />
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
				<th>序号</th>
				<th orderfield="costsName" class="<c:if test="${order.field eq 'costsName'}">${order.direction }</c:if>">费用名称</th>
				<th orderfield="goodsCodes" class="<c:if test="${order.field eq 'goodsCodes'}">${order.direction }</c:if>">适用商品编号</th>
				<th orderfield="goodsNames" class="<c:if test="${order.field eq 'goodsNames'}">${order.direction }</c:if>">适用商品范围</th>
				<th orderfield="sellerQuota" class="<c:if test="${order.field eq 'sellerQuota'}">${order.direction }</c:if>">卖方定额(元)</th>
				<th orderfield="sellerRate" class="<c:if test="${order.field eq 'sellerRate'}">${order.direction }</c:if>">卖方比例(%)</th>
				<th orderfield="buyerQuota" class="<c:if test="${order.field eq 'buyerQuota'}">${order.direction }</c:if>">买方定额(元)</th>
				<th orderfield="buyerRate" class="<c:if test="${order.field eq 'buyerRate'}">${order.direction }</c:if>">买方比例(%)</th>
				<th orderfield="usrName" class="<c:if test="${order.field eq 'usrName'}">${order.direction }</c:if>">创建人</th>
				<th orderfield="createTime" class="<c:if test="${order.field eq 'createTime'}">${order.direction }</c:if>">创建时间</th>
				<th orderfield="updateTime" class="<c:if test="${order.field eq 'updateTime'}">${order.direction }</c:if>">最后一次数据更新时间</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="costs" items="${page.rows }" varStatus="seq">
			<tr target="costs_id" rel="${costs.costsId }">
				<td align="center">${seq.count +(page.pageIndex-1) * page.pageSize }</td>
				<td  align="center">${costs.costsName }</td>
				<td  align="center" title="${costs.goodsCodes }">
					<c:if test="${empty costs.goodsCodes  }">
						所有商品
					</c:if>
					<c:if test="${not empty costs.goodsCodes  }">
						个别商品
					</c:if>
				</td>
				<td  align="center" title="${costs.goodsNames }">
					<c:if test="${empty costs.goodsNames  }">
						所有商品
					</c:if>
					<c:if test="${not empty costs.goodsNames  }">
						个别商品
					</c:if>
				</td>
				<td  align="center">${costs.sellerQuota }</td>
				<td  align="center">${costs.sellerRate }</td>
				<td  align="center">${costs.buyerQuota }</td>
				<td  align="center">${costs.buyerRate }</td>
				<td  align="center">${costs.usrName }</td>
				<td  align="center">
					<fmt:formatDate value="${costs.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td  align="center">
					<fmt:formatDate value="${costs.updateTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<c:if test="${costs.status=='0' }">已关闭</c:if>
					<c:if test="${costs.status=='1' }">启用中</c:if>
				</td>
				<td>
					<div style="padding-left: 20px;">
						<a class="btnEdit" href="${CTX }/costs/toUpdateCosts.do?costsId=${costs.costsId }" target="dialog" rel="list-cost" width=550 height="380" title="编辑结算规则信息">编辑</a>
						<%-- <a class="btnDel" href="${CTX }/costs/deleteCosts.do?costsId=${costs.costsId }" target="ajaxTodo" title="确定要删除这条结算规则吗?">删除</a> --%>
						<a class="btnView"  href="${CTX }/costs/getDetailsCosts.do?costsId=${costs.costsId }" target="dialog" width="450" height="500" rel="details" title="查看详情" maxable="false">详情</a>
						<c:if test="${costs.status=='0' }">
							<a class="btnSelect" href="${CTX }/costs/openOrCloseCosts.do?costsId=${costs.costsId }&status=1" target="ajaxTodo" title="确定要启用此条交易规则吗?">启用</a>
						</c:if>
						<c:if test="${costs.status=='1' }">
							<a class="btnDel" href="${CTX }/costs/openOrCloseCosts.do?costsId=${costs.costsId }&status=0" target="ajaxTodo" title="确定要关闭此条交易规则吗?">关闭</a>
						</c:if>
					</div>
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
	<form id="pagerForm" action="${CTX }/costs/costsList.do"
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
