<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/kit/dwz/js/dwz.resetformvalue.js"></script>
<%@ include file="../commons/taglibs.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="pageHeader">
	 <form rel="pagerForm" onsubmit="return dialogSearch(this);" action="${CTX }/trade/findMerchant.do" method="post">
		 <div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><label>商户名称：</label><input type="text" class="checkReset" name="bizName" value="${merchant.bizName }" />
					<td><label>商户备案号：</label><input type="text" class="checkReset" name="bizNo" value="${merchant.bizNo }" />
					</td>
				</tr>
				
				<tr>
					<td>
						<label>商户性质:</label><select name="property" class="combox checkResetSelect">
							<option value="">请选择：</option>
							<option value="个体" <c:if test="${merchant.property=='个体' }">selected="selected"</c:if>>个体</option>
							<option value="企业" <c:if test="${merchant.property=='企业' }">selected="selected"</c:if>>企业</option>
						</select>
					</td>
					<td>
						<label>商户类型:</label><select name="businessType" class="combox checkResetSelect">
							<option value="">请选择：</option>
							<c:forEach items="${businessTypeList }" var="btype">
								<option value="${btype.key }" <c:if test="${merchant.businessType==btype.key }">selected="selected"</c:if>>${btype.value }</option>
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
	<table class="table" targetType="dialog" width="150%" layoutH="160">
		<thead>
			<tr>
				<th>序号</th>
				<th orderfield="bizName" class="<c:if test="${order.field eq 'bizName'}">${order.direction }</c:if>">商户名称</th>
				<th orderfield="bizNo" class="<c:if test="${order.field eq 'bizNo'}">${order.direction }</c:if>">追溯备案号</th>
				<th>商户性质</th>
				<th>法人</th>
				<th>商户类型</th>
				<th>身份证号/工商登记号</th>
				<th>联系电话</th>
				<th orderfield="boothNo" class="<c:if test="${order.field eq 'boothNo'}">${order.direction }</c:if>">摊位编号</th>
				<th>是否备案</th>
				<th orderfield="updateTime" class="<c:if test="${order.field eq 'updateTime'}">${order.direction }</c:if>">最后一次数据更新时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="merchant" items="${page.rows }" varStatus="seq">
			<tr target="m_id" rel="${merchant.bizId }" style="cursor: pointer;" ondblclick="$.bringBack({businessType:'${merchant.businessType }', bizNo:'${merchant.bizNo }', bizType:'${merchant.bizType }',bizName:'${merchant.bizName }'})">
				<td align="center">${seq.count +(page.pageIndex-1) * page.pageSize }</td>
				<td align="center">
					${merchant.bizName }
				</td>
				<td  align="center">${merchant.bizNo }</td>
				<td  align="center">${merchant.property }</td>
				<td  align="center">${merchant.legalRepresent }</td>
				<td  align="center">
					<c:forEach items="${businessTypeList }" var="btype">
						<c:if test="${btype.key==merchant.businessType }">
							${btype.value }
						</c:if>
					</c:forEach>
				</td>
				<td  align="center">
					<c:if test="${merchant.property=='个体' }">${merchant.identityCard }</c:if>
					<c:if test="${merchant.property=='企业' }">${merchant.regId }</c:if>
				</td>
				<td  align="center">${merchant.tel }</td>
				<td  align="center">${merchant.boothNo }</td>
				<td  align="center">
					<c:if test="${ empty merchant.uploadTime }">否</c:if>
					<c:if test="${ not empty merchant.uploadTime }">是</c:if>
				</td>
				<td  align="center">
					<fmt:formatDate value="${merchant.updateTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
			       <a class="btnSelect" href="javascript:$.bringBack({businessType:'${merchant.businessType }', bizNo:'${merchant.bizNo }', bizType:'${merchant.bizType }',bizName:'${merchant.bizName }'})" title="查找带回">选择</a>
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
	<form id="pagerForm" action="${CTX }/trade/findMerchant.do"
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
