<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/kit/dwz/js/dwz.resetformvalue.js"></script>
<%@ include file="../commons/taglibs.jsp" %>
<div class="pageHeader">
  		<form rel="dd" id="approachList" onsubmit="return navTabSearch(this);" action="${CTX }/approach/listApproMeat.do?goodsType=${approch_state}" method="post">
		<div class="searchBar">
		    <input type="hidden" name="approachState" value="${approch_state }"/>
			<table class="searchContent">
				<tr>
					<td><label>商户名称：</label><input  type="text" class="checkReset" id="wholesalerName"  name="wholesalerName" value="${approch.wholesalerName }"/></td>
					<td><label>商户编号：</label><input type="text" class="checkReset" id="wholesalerId" name="wholesalerId" value="${approch.wholesalerId}"/></td>
					<td><label>进货批次号：</label><input type="text" class="checkReset" id="batchId" name="batchId" value="${approch.batchId }"/></td>
			   </tr>
			    <tr>
			        <td><label>商户类型：</label>
			           <select name="bizType" class="checkResetSelect" style="width:152px;height:23px;">
					   <option value="" >请选择：</option>
					     <c:forEach items="${businessType}" var="btype">
							<option value="${btype.key }" <c:if test="${approch.bizType==btype.key }">selected="selected"</c:if>>${btype.value }</option>
					    </c:forEach>
				    </select>
			       </td>
					<td><label>进场时间从：</label><input  type="text" name="startDate" class="date checkReset" value="${startDate }" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" size="20"/><a class="inputDateButton" href="javascript:;" style="float:right;">选择</a>
					</td>
					<td><label>至：</label><input type="text" name="endDate" class="date checkReset" value="${endDate }" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" size="20"/><a class="inputDateButton" href="javascript:;" style="float:right;" >选择</a>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button id="approachListBtn" type="submit">检索</button></div></div></li>
					<li><div class="buttonActive"><div class="buttonContent"><button class="resetForm">重置</button></div></div></li>
				</ul>
			</div>
		</div>	
	</form>
</div>
<div class="pageContent">

     <%-- 增加，删除，修改按钮 --%>
	<div class="panelBar">
			<ul class="toolBar">
			<li><a class="add" href="${CTX }/approach/goApprMeatAdd.do?approachState=${approch_state }" target="dialog" drawable="true" width="600" height="520" title="肉菜进场登记" rel="goApprMeatAdd"><span>添加</span></a></li>
			<li><a class="delete" href="${CTX }/approach/deleteApproach.do?approachId={approach_id}" target="ajaxTodo" title="确定要删除吗?" ><span>删除</span></a></li>
			<li><a class="edit" href="${CTX }/approach/goToApproachEdit.do?approachId={approach_id}&approachState=${approch_state }" target="dialog" rel="editApproach" width="600" height="520"><span>修改</span></a></li>
		</ul>
	</div> 
	
	<%-- 数据列表 --%>
	<table class="table" width="1300" layoutH="158">
		<thead>
			<tr>
				<th>进货批次号</th>
				<th orderfield="wholesalerId" class="<c:if test="${order.field eq 'wholesalerId'}">${order.direction }</c:if>">商户编号</th>
				<th orderfield="wholesalerName" class="<c:if test="${order.field eq 'wholesalerName'}">${order.direction }</c:if>">商户名称</th>
				<th>商户类型</th>
				<th>商品名称</th>
				<th>商品重量</th>
				<th>商品价格</th>
				<th>是否上传</th>
				<th orderfield="inDate" class="<c:if test="${order.field eq 'inDate'}">${order.direction }</c:if>">进场日期</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="approach" items="${page.rows }" varStatus="seq">
			<tr target="approach_id" rel="${approach.approachId }">
				<td  align="center">
					<a  style="color: blue;" href="${CTX }/approach/getApproachById.do?approachId=${approach.approachId }" target="dialog" width="440" height="500" title="查看详情" maxable="false" resizable="false">
						${approach.batchId } 
					</a>
				</td>
				<td  align="center">${approach.wholesalerId }</td>
				<td align="center">${approach.wholesalerName }</td>
				<td align="center">
				   <c:forEach items="${businessType }" var="btype">
						<c:if test="${btype.key==approach.bizType }">
							${btype.value }
						</c:if>
					</c:forEach>
			    </td>
				<td align="center">${approach.goodsName }</td>
				<td  align="center">${approach.weight }</td>
				<td align="center">${approach.price }</td>
				<td align="center">
				 <c:if test="${approach.uploadTime == null }">否</c:if>
				  <c:if test="${approach.uploadTime !=null }">是</c:if>
				</td>
				<td  align="center">${approach.inDate }</td>
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
	<form id="pagerForm" action="${CTX }/approach/listApproMeat.do?goodsType=${approch_state}"
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