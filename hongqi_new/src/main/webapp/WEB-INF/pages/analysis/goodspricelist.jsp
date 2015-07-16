<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/kit/dwz/js/dwz.resetformvalue.js"></script>
<%@ include file="../commons/taglibs.jsp" %>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${CTX }/goodsPrice/getGoodsPrice.do?status=1" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><label>时间从：</label><input  type="text" name="startDate" class="date required checkReset" value="${startDate }" dateFmt="yyyy-MM-dd" readonly="true" size="20"/><a class="inputDateButton" href="javascript:;" style="float:right;">选择</a>
					</td>
					<td><label>至(15天之内)：</label><input type="text" name="endDate" class="date required checkReset" value="${endDate }" dateFmt="yyyy-MM-dd" readonly="true" size="20"/><a class="inputDateButton" href="javascript:;" style="float:right;" >选择</a>
					</td>
					<td>
						<label>已采集商品：</label><select name="goodsList" class="combox required checkResetSelect">
							<option value="0">请选择：</option>
							<c:forEach var="goods" items="${goodsList }">
								<option value="${goods.goodsCode }" <c:if test="${goodsCode==goods.goodsCode }">selected='selected'</c:if>>${goods.goodsName }</option>
							</c:forEach>
						</select>
					</td>
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
	<div style="color: red;font-size: 14px; font-weight: bold;margin-left: 5%; margin-top: 5%;overflow: hidden;">
		${result }
	</div>
	<%-- 分页查询的Form --%>
	<form id="pagerForm" action="${CTX }/goodsPrice/getGoodsPrice.do?status=1" method="post">
	</form> 
</div>
