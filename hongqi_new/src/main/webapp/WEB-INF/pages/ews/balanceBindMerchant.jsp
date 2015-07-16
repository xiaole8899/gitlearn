<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp" %>

<div class="pageContent">
	<form method="post" action="${CTX }/balance/doBindMerchant.do" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input type="hidden" id="balanceBackId"  name="balanceBackId" value="${balanceBackId}">
		<div class="pageFormContent" layoutH="56">
			<h1>请选择您要绑定的商户：</h1><br>
			<div style="display:block; height:50px; ">
				<table class="table" width="560">
					<thead>
						<tr>
							<th width="50">请选择</th> 
							<th width="">商户号</th>
							<th width="">商户名称</th>
							<th width="">身份证号</th>
							<!-- <th width="">经营类型</th> -->
							<th width="">手机号</th>
							<th width="">商户摊位号</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="merall" items="${merall}" >
						<tr>
							<td align="center">
								<input type="radio" id="bizNumber" name="bizNumber"
									<c:if test="${merall.bizNo==balanceBack.bizId}"> checked="checked" </c:if>
								 value="${merall.bizNo}"  />
							</td> 
							<td align="center">${merall.bizNo }</td>
							<td align="center">${merall.bizName }</td>
							<td align="center">${merall.identityCard}</td>
							<%-- <td align="center">${merall.businessType}</td> --%>
							<td align="center">${merall.tel}</td>
							<td align="center">${merall.boothNo}</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
			
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button id="roleadd_btn" type="submit">确定</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
