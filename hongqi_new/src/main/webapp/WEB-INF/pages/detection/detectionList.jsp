<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/kit/dwz/js/dwz.resetformvalue.js"></script>
<%@ include file="../commons/taglibs.jsp" %>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${CTX }/detection/detectionList.do" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>批次号类型：<input type="text" class="checkReset" value="${detection.batchType}" name="batchType"/></td>
					<td>批发商名称：<input type="text" class="checkReset"  value="${detection.wholesalerName}" name="wholesalerName"/></td>
					<td>商品名称：<input type="text" class="checkReset" value="${detection.goodsName }" name="goodsName"/></td>
				</tr>
				<tr>
					<td>检测日期从：<input type="text" value="${dateStart }" name="dateStart" class="date checkReset" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" size="20"/> <a class="inputDateButton" href="javascript:;" style="float:right;">选择</a></td>
					<td>至：
						<input type="text" value="${dateEnd}" name="dateEnd" class="date checkReset" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true"  size="20" style="margin-left: 45px"/> <a class="inputDateButton" href="javascript:;" style="float:right;">选择</a>
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
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class=edit href="${CTX }/detection/showDetectionDetail.do?mdId={md_id}" target="dialog" rel="showDetectionDetail" width="430" height="400" maxable="false" resizable="false"><span>查看明细</span></a></li>
		</ul>
	</div>
	<%-- 数据列表 --%>
	<table class="table" width="1200" layoutH="160">
		<thead>
			<tr>
				<th width="40">序号</th>
				<th orderfield="batchId" class="<c:if test="${order.field eq 'batchId'}">${order.direction}</c:if>">批次号</th>
				<th orderfield="batchType" class="<c:if test="${order.field eq 'batchType'}">${order.direction }</c:if>">批次号类型</th>
				<th orderfield="wholesalerName" class="<c:if test="${order.field eq 'wholesalerName'}">${order.direction }</c:if>">批发商名称</th>
				<th orderfield="goodsName" class="<c:if test="${order.field eq 'goodsName'}">${order.direction }</c:if>">商品名称</th>
				<th orderfield="surveyor" class="<c:if test="${order.field eq 'surveyor'}">${order.direction }</c:if>">检测员</th>
				<th orderfield="detectionResult" class="<c:if test="${order.field eq 'detectionResult'}">${order.direction }</c:if>">检测结果</th>
				<th orderfield="detectionDate" class="<c:if test="${order.field eq 'detectionDate'}">${order.direction }</c:if>">检测日期</th>
				<th orderfield="uploadTime" class="<c:if test="${order.field eq 'uploadTime'}">${order.direction }</c:if>">信息更新时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="detection" items="${page.rows }" varStatus="seq">
			<tr target="md_id" rel="${detection.mdId }">
				<td align="right" style="font-weight: bold; font-style: italic;">${seq.count +(page.pageIndex-1) * page.pageSize }</td>
				<td align="center">${detection.batchId }</td>
				<td align="center">
					<c:forEach items="${voucherTypeVList }" var="vi">
						<c:if test="${vi.key==detection.batchType }">
							${vi.value }
						</c:if>
					</c:forEach>
				</td>
				<td align="center">${detection.wholesalerName }</td>
				<td align="center">${detection.goodsName }</td>
				<td align="center">${detection.surveyor }</td>
				<td align="center">
					<c:if test="${detection.detectionResult == 1 }">
						合格
					</c:if>
					<c:if test= "${detection.detectionResult == 0 }">
						不合格
					</c:if>
				</td>
				<td align="center"><fmt:formatDate value="${detection.detectionDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td align="center"><fmt:formatDate value="${detection.uploadTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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
	<form id="pagerForm" action="${CTX }/detection/detectionList.do" method="post">
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
