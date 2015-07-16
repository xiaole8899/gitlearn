<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp" %>
<dppc:resource path="js/jQuery.Hz2Py-min.js"/>
<div class="pageContent">
	<form method="post" action="${CTX}/goods/doAddGoods.do" class="pageForm required-validate" rel="menudoadd" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>商品名称：</label>
				<input type="text" class="required"  name="goodsName" maxlength="10" id="add-gm"    size="30" />
			</p>
			<p>
				<label>商品编号：</label>
				<input type="text" class="required number" maxlength="8" name="goodsCode"  size="30" />
			</p>
			<p>
				<label>商品上级编号：</label>
				<input type="text" class="required" value="${preCode }" readonly="readonly" name="preCode"  size="30" />
			</p>
			<p>
				<label>商品类型：</label>
				<select name="goodsStatus" class="required combox">
					<option value="">请选择：</option>
					<c:forEach items="${bizTypeList }" var="bt">
						<option value="${bt.key }">${bt.value }</option>
					</c:forEach>
				</select>
			</p>
			<p>
				<label>商品昵称：</label>
				<input type="text"  name="goodsAlias" maxlength="20"  size="30" />
			</p>
			<p>
				<label>商品拼音：</label>
				<input type="text" readonly="readonly" name="pinYin" id="add-py"  size="30" />
			</p>
			<p>
				<label>商品首字母：</label>
				<input type="text" readonly="readonly"  name="firstLetter"  id="add-fl"  size="30" />
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
	$("#add-gm").blur(function(){
		var pinYin=$('#add-gm').toPinyin();
		$("#add-py").val(pinYin);
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
			$("#add-fl").val(firstLetter);
		} 
		
		
	});
});
</script>