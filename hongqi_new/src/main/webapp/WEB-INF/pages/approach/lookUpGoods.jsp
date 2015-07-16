<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/kit/dwz/js/dwz.resetformvalue.js"></script>
<%@ include file="../commons/taglibs.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="pageHeader">
	 <form rel="pagerForm" onsubmit="return dialogSearch(this);" action="${CTX }/approach/findGoods.do?approachStateAdd=${approachState}" method="post">
		 <div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><label>商品名称：</label><input type="text"  class="checkReset" name="goodsName" value="${goods.goodsName }" /></td>
					<td><label>商品编号：</label><input type="text" class="checkReset" name="goodsCode" value="${goods.goodsCode }" /></td>
					<td><label>拼音：</label><input type="text" class="checkReset" name="pinYin" value="${goods.pinYin }" /></td>
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
				<th orderfield="goodsName" class="<c:if test="${order.field eq 'goodsName'}">${order.direction }</c:if>">商品名称</th>
				<th orderfield="goodsCode" class="<c:if test="${order.field eq 'goodsCode'}">${order.direction }</c:if>">商品编号</th>
				<th>商品别名</th>
				<th>上级编码</th>
				<th>拼音码</th>
				<th>首字母</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="goods" items="${page.rows }" varStatus="seq">
			<tr target="m_id" rel="${goods.goodsId }" style="cursor: pointer;" ondblclick="$.bringBack({goodsCode:'${goods.goodsCode  }',goodsName:'${goods.goodsName }',goodsStatus:'${goods.goodsStatus }'})">
				<td align="center">${seq.count +(page.pageIndex-1) * page.pageSize }</td>
				<td align="center">
					${goods.goodsName }
				</td>
				<td  align="center">${goods.goodsCode }</td>
				<td  align="center">${goods.goodsAlias }</td>
				<td  align="center">${goods.preCode }</td>
				<td  align="center">${goods.pinYin }</td>
				<td  align="center">${goods.firstLetter }</td>
				<td>
			       <a class="btnSelect" href="javascript:$.bringBack({goodsCode:'${goods.goodsCode  }',goodsName:'${goods.goodsName }',goodsStatus:'${goods.goodsStatus }'})" title="查找带回">选择</a>
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
	<form id="pagerForm" action="${CTX }/approach/findGoods.do?approachStateAdd=${approachState}"
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
<!-- <script type="text/javascript">
	function ClearTextBox() {
		$("input[name=goodsName]").val("");
		$("input[name=goodsCode]").val("");
		$("input[name=pinYin]").val("");
		
	}
</script> -->
