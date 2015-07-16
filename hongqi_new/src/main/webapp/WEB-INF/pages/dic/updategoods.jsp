<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp" %>
<dppc:resource path="js/jQuery.Hz2Py-min.js"/>
<div class="pageContent">
	<form method="post" action="${CTX}/goods/doUpdateGoods.do" class="pageForm required-validate" rel="menudoadd" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<input name="goodsId" type="hidden" value="${goods.goodsId }" />
			<p>
				<label>商品名称：</label>
				<input type="text" class="required" maxlength="10" name="goodsName" id="update-gm"    value="${goods.goodsName }" size="30" />
			</p>
			<p>
				<label>商品编号：</label>
				<input type="text" class="required number" maxlength="8"  name="goodsCode" value="${goods.goodsCode }"  size="30" />
			</p>
			<p>
				<label>商品上级编号：</label>
				<input type="text" class="required" value="${goods.preCode }"  readonly="readonly" name="preCode"  size="30" />
			</p>
			<p>
				<label>商品类型：</label>
				<select name="goodsStatus" class="required combox">
					<option value="">请选择：</option>
					<c:forEach items="${bizTypeList }" var="bt">
						<option value="${bt.key }" <c:if test="${bt.key==goods.goodsStatus }">selected='selected'</c:if>>
							${bt.value }
						</option>
					</c:forEach>
				</select>
			</p>
			<p>
				<label>商品昵称：</label>
				<input type="text"  name="goodsAlias" maxlength="25" value="${goods.goodsAlias }"  size="30" />
			</p>
			<p>
				<label>商品拼音：</label>
				<input type="text" readonly="readonly" name="pinYin" id="update-py" value="${goods.pinYin }"  size="30" />
			</p>
			<p>
				<label>商品首字母：</label>
				<input type="text" readonly="readonly"  name="firstLetter" id="update-fl" value="${goods.firstLetter }"  size="30" />
			</p>
			<div class="divider"></div>
		</div>
		<div class="formBar">
			<ul>
			    <li><input type="hidden" name="parentId" id="parentId" value="${parentId}" /></li>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>

<script type="text/javascript">
$(function(){
	//根据拼音加载首字母
	$("#update-gm").blur(function(){
		var pinYin=$('#update-gm').toPinyin();
		$("#update-py").val(pinYin);
		var firstLetter="";
		//大写字母正则
		var firstLetterReg=/^[A-Z]$/;
		if(pinYin!=""){
			for(var i=0;i<pinYin.length;i++){
				//取首字母
				var zM=pinYin.substring(i,i+1);
				if(firstLetterReg.test(zM)){
					firstLetter+=zM;
				}
			}
			$("#update-fl").val(firstLetter);
		} 
	});
});
</script>