<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp" %>
<form id="pagerForm" action="${CTX }/costs/lookUpGoods.do">
</form>

<div class="pageHeader">
	<form rel="pagerForm" method="post" action="${CTX }/costs/lookUpGoods.do" onsubmit="return dwzSearch(this, 'dialog');">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>商品名称：</label>
				<input class="textInput" name="goodsName" value="${goods.goodsName }" type="text">
			</li>	  
			<li>
				<label>商品编码：</label>
				<input class="textInput" name="goodsCode" value="${goods.goodsCode }" type="text">
			</li>
		</ul>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button"  bsf="goodsName" dyts="最多只能选择3种商品" multLookup="goods" maxNum='3' warn="请选择商品">选择带回</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">

	<table class="table" layoutH="118" targetType="dialog" width="100%">
		<thead>
			<tr>
				<th align="center"><input type="checkbox" class="checkboxCtrl" group="goods" /></th>
				<th>序号</th>
				<th align="center">商品名称</th>
				<th align="center">商品编码</th>
				<th align="center">商品拼音</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="goods" items="${goodsList }" varStatus="seq">
			<tr>
				<td ><input type="checkbox" name="goods" value="{goodsId:'${goods.goodsId }',goodsName:'${goods.goodsName }', goodsCode:'${goods.goodsCode }'}"/></td>
				<td align="center">${seq.index+1 }</td>
				<td>${goods.goodsCode }</td>
				<td>${goods.goodsName }</td>
				<td>${goods.pinYin }</td>
			</tr>
		</c:forEach>
		<c:if test="${empty goodsList}">
			<tr>
				<td colspan="5" align="center">
					暂无结果
				</td>
			</tr>
		</c:if>
		</tbody>
	</table>
</div>
