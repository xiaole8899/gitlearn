<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp" %>
<div class="pageContent">
	<form method="post" action="${CTX }/detection/doAddDetection.do" rel="addDetection" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<input name="approach.approachId" type="hidden"/>
			<p>
				<label>请选择样品：</label>
				<input type="text" size="27" readonly="readonly" class="required" name="approach.goodsName" value="" lookupGroup="approach" />
				<a class="btnLook" href="${CTX}/detection/backList.do" rel="backcompytypelist" lookupGroup="approach">请选择</a>
			</p>
			<p style="margin-left: 20%">
				<label>批次号：</label>
				<input name="approach.batchId" type="text" size="30" readonly="readonly"/>
			</p>
			<div class="divider"></div>
			<p>
				<label>批次号类型：</label> 
				<input name="approach.batchType"  type="hidden" readonly="readonly" size="30"/>
	    		<input name="approach.batchTypeName"  type="text" readonly="readonly" size="30"/>
			</p>
			<p style="margin-left: 20%">
				<label>批发商名称：</label>
				<input name="approach.wholesalerName" type="text" readonly="readonly" size="30"/>
			</p>
			<div class="divider"></div>
			<p>
				<label>批发商编码：</label>
				<input name="approach.wholesalerId" type="text" readonly="readonly" size="30"/>
			</p>			
			<p style="margin-left: 20%">
				<label>商品编码：</label>
				<input name="approach.goodsCode" type="text" readonly="readonly" size="30"/>
			</p>		
			<div class="divider"></div>
			<p>
				<label>样品编号：</label>
				<input name="sampleId" class="required" type="text" size="30"/>
			</p>		
			<p style="margin-left: 20%">
				<label>检测员：</label>
				<input name="surveyor" class="required" type="text" size="30"/>
			</p>
			<div class="divider"></div>
			<p>
				<label>检测日期：</label>
				<input type="text" name="date" class="date" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true" size="28"/>
				<a class="inputDateButton" href="javascript:;">选择</a>
				<!-- <span class="info">yyyy-MM-dd HH:mm:ss</span> -->
				<!-- <input name="detectionDate" class="required" type="text" size="30"/> -->
			</p>		
			<p style="margin-left: 20%">
				<label>检测结果：</label>
				<select name="detectionResult">
					<option value="1">合格</option>
					<option value="0">不合格</option>
				</select>
			</p>		
			<p>
				<label>检测结果说明：</label>
				<textarea name="resultExplain" rows="6" cols="83" maxlength="100" title="请输入不超过一百个字符"></textarea>
			</p>		
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>