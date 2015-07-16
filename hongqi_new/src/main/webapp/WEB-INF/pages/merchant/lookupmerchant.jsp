<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/kit/dwz/js/dwz.resetformvalue.js"></script>
<%@ include file="../commons/taglibs.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="pageHeader">
	 <form rel="pagerForm" onsubmit="return dialogSearch(this);" action="${CTX }/merchant/getNocardMerchant.do" method="post">
		 <div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><label>持卡人：</label><input type="text" class="checkReset" name="bizName" value="${merchant.bizName }" />
					<td><label>商户备案号：</label><input type="text" class="checkReset" name="bizNo" value="${merchant.bizNo }" />
					</td>
					<td>
						<label>商户性质：</label><select name="property" class="combox checkResetSelect">
							<option value="">请选择：</option>
							<option value="个体" <c:if test="${merchant.property=='个体' }">selected="selected"</c:if>>个体</option>
							<option value="企业" <c:if test="${merchant.property=='企业' }">selected="selected"</c:if>>企业</option>
						</select>
					</td>
				</tr>
				<tr style="margin-top: 10px;">
					<td>
						<label>商户类型：</label><select name="businessType" class="combox checkResetSelect">
							<option value="">请选择</option>
							<c:forEach items="${businessTypeList }" var="btype">
								<option  value="${btype.key }" <c:if test="${merchant.businessType==btype.key }">selected="selected"</c:if>>${btype.value }</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<label>经营类型：</label>
						<select name="bizType" class="combox checkResetSelect">
							<option value="">请选择：</option>
								<c:forEach var="bt" items="${bizTypeList }">
									<option value="${bt.key }" <c:if test="${bt.key==merchant.bizType }">selected="selected"</c:if>>${bt.value }</option>
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
	<table class="table" targetType="dialog"  width="100%" layoutH="135">
		<thead>
			<tr>
				<th width="50">序号</th>
				<th width="200">字号名称</th>
				<th width="200">企业名称</th>
				<th width="200" orderfield="merchant.bizName" class="<c:if test="${order.field eq 'merchant.bizName'}">${order.direction }</c:if>">持卡人</th>
				<th width="200" orderfield="merchant.bizNo" class="<c:if test="${order.field eq 'merchant.bizNo'}">${order.direction }</c:if>">追溯备案号</th>
				<th width="200">商户性质</th>
				<th width="100">法人</th>
				<th width="200">商户类型</th>
				<th width="200">经营类型</th>
				<th width="200">身份证号</th>
				<th width="200">工商登记号</th>
				<th width="200">联系电话</th>
				<th width="200" orderfield="merchant.boothNo" class="<c:if test="${order.field eq 'merchant.boothNo'}">${order.direction }</c:if>">摊位编号</th>
				<th width="220">是否备案</th>
				<th width="280" orderfield="merchant.updateTime" class="<c:if test="${order.field eq 'merchant.updateTime'}">${order.direction }</c:if>">最近更新时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="merchant" items="${page.rows }" varStatus="seq">
			<tr target="m_id" style="cursor: pointer;" ondblclick="$.bringBack({comName:'${merchant.comName }', bizNo:'${merchant.bizNo }',identityCard:'${merchant.identityCard }', bizName:'${merchant.bizName }',tel:'${merchant.tel }',bizId:'${merchant.bizId }'})">
				<td align="center">${seq.count +(page.pageIndex-1) * page.pageSize }</td>
				<td align="center">
					<c:if test="${merchant.property=='个体' }">
						${merchant.comName }
					</c:if>
					<c:if test="${merchant.property=='企业' }">
						-
					</c:if>
				</td>
				<td align="center">
					<c:if test="${merchant.property=='个体' }">
						-
					</c:if>
					<c:if test="${merchant.property=='企业' }">
						<a  style="color: blue;" href="${CTX }/merchant/toDetailsMerchant.do?mId=${merchant.bizId }" target="dialog" width="440" height="500" title="查看详情" maxable="false" resizable="false">
							${merchant.comName }
						</a>
					</c:if>
				</td>
				<td  align="center">${merchant.bizName }</td>
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
					<c:forEach var="bt" items="${bizTypeList }">
						<c:if test="${bt.key==merchant.bizType }">${bt.value }</c:if>
					</c:forEach>
				</td>
				<td  align="center">
					${merchant.identityCard }
				</td>
				<td>${merchant.regId }</td>
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
			       <a class="btnSelect" href="javascript:$.bringBack({comName:'${merchant.comName }', bizNo:'${merchant.bizNo }',identityCard:'${merchant.identityCard }', bizName:'${merchant.bizName }',tel:'${merchant.tel }',bizId:'${merchant.bizId }'})" title="查找带回">选择</a>
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
	<form id="pagerForm" action="${CTX }/merchant/getNocardMerchant.do"
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
