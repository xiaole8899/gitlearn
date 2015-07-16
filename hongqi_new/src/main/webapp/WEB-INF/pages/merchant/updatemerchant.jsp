<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp" %>
<div class="pageContent">
	<form method="post" action="${CTX }/merchant/doUpdateMerchant.do" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<input type="hidden" name="bizId" value="${merchant.bizId }" />
		<input type="hidden" name="status" value="0" />
		<input type="hidden" name="bizNo" value="${merchant.bizNo }"/>
		<input type="hidden" name="tradePwds" value="${merchant.tradePwd }"/>
		<input type="hidden" name="balance" value="${merchant.balance }"/>
		<div class="pageFormContent" layoutH="56">
			<p>
				<label  id="update-merchant-name">
					<c:if test="${merchant.property=='个体' }">
						字号名称:
					</c:if>
					<c:if test="${merchant.property=='企业' }">
						企业名称:
					</c:if>
				</label>
				<input type="text" value="${merchant.comName }" class="required" name="comName" size="30" />
			</p>
			
			<p>
				<label>商户性质：</label>
				<input type="radio"   class="required" <c:if test="${merchant.property=='个体' }">checked="checked"</c:if> name="property" value="个体" size="5" />个体
				<input type="radio" class="required"  name="property" <c:if test="${merchant.property=='企业' }">checked="checked"</c:if>  value="企业" size="5" />企业
			</p>
			<p>
				<label  id="update-merchant-name">持卡人：</label>
				<input type="text" value="${merchant.bizName }" class="required" name="bizName" size="30" />
			</p>
			<p  id="updateFaren">
				<label>法人：</label>
				<input type="text" value="${merchant.legalRepresent }" <c:if test="${merchant.property=='企业' }">class="required"</c:if>   name="legalRepresent" size="30" />
			</p>
			
			<div class="divider"></div>
			<p>
				<label>经营类型：</label>
				<select name="businessType" class="required combox" size="50">
					<option value="">请选择：</option>
					<c:forEach items="${businessTypeList }" var="btype">
							<option value="${btype.key }" <c:if test="${merchant.businessType==btype.key }">selected="selected"</c:if>>${btype.value }</option>
					</c:forEach>
				</select>
			</p>
			
			<p>
				<label>商户类型：</label>
				<select name="bizType" class="required combox" size="50">
					<option value="">请选择：</option>
					<c:forEach items="${bizTypeList }" var="bt">
							<option value="${bt.key }" <c:if test="${merchant.bizType==bt.key }">selected="selected"</c:if>>${bt.value }</option>
					</c:forEach>
				</select>
			</p>
			
			<p id="updateSfzDiv">
				<label>身份证号：</label>
				<input type="text"    name="identityCard" value="${merchant.identityCard }" <c:if test="${merchant.property=='个体' }">class="required idCard"</c:if> size="30" />
			</p>
		
			<p  id="updateGsDiv">
				<label>工商登记号：</label>
				<input type="text" class="required" value="${merchant.regId }" <c:if test="${merchant.property=='企业' }">class="required"</c:if>  name="regId" size="30" />
			</p>
			
			<p>
				<label>交易密码：</label>
				<input type="button" id="edit-trade-pwd" value="编辑密码"/>
				<input type="password" id="tradePwd" name="tradePwd" disabled="disabled" class="required password"  value="${merchant.tradePwd}" size="18"/>
			</p>
			
			<p>
				<label>联系电话：</label>
				<input type="text" value="${merchant.tel }" class="required tel" name="tel" size="30" />
			</p>
			<p>
				<label>摊位编号：</label>
				<input type="text"  name="boothNo" value="${merchant.boothNo }" size="30" />
			</p>
			<%-- <p>
				<label>供货商：</label>
				<c:forEach var="supply" items="${supplyList }">
					<input type="checkbox" name="supplier"  class="supplyList" value="${supply.suId }"/>${supply.name }
				</c:forEach>
				<span class="error" id="supplyInfo" style="display: none;margin-left: 60px;">必填项</span>
			</p> --%>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">



//编辑密码
$("#edit-trade-pwd").click(function(){
	$("input[name='status']").val("1");
	$("#tradePwd").val("");
	$("#tradePwd").removeAttr("disabled");
});

var $updateMerchantChangeProperty=$("input[name='property']").change(function(){
	if($(this).val()=="个体"){
		$("#update-merchant-name").text("字号名称:");
	}else{
		$("#update-merchant-name").text("企业名称:");
	}
});


</script>
