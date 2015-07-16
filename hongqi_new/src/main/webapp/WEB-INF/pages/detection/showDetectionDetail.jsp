<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp" %>

<div class="pageContent">
		<fieldset style="border: 1px solid #b8d0d6;height: 330px;width: 180px;margin-left: 2%;">
	    	<legend style="border: 0px solid">检测信息明细</legend>
	    		<!-- <p align="center"><font><b></b></font></p> -->
			  	<div class="pageFormContent" layoutH="66" style="border: 0px solid; margin-left: 3%;">
					<p align="left">
						<label>批次号</label>
						<input value="${detection.batchId }" style="border: 0px solid;color:red;" readonly="readonly"/>
					</p>
					<p align="left">
						<label>商品名称</label>
						<input value="${detection.goodsName }" style="border: 0px solid;color:red;" readonly="readonly"/>
					</p>
					<p align="left">
						<label>商品编号</label>
						<input value="${detection.goodsCode }" style="border: 0px solid;color:red;" readonly="readonly"/>
					</p>
					<p align="left">
						<label>批发商编号</label>
						<input value="${detection.wholesalerId }" style="border: 0px solid;color:red;" readonly="readonly"/>
					</p>
					<p align="left">
						<label>样品编码</label>
						<input value="${detection.sampleId }" style="border: 0px solid;color:red;" readonly="readonly"/>
					</p>
					<p align="left">
						<label>检测结果说明</label>
						<%-- <input value="" style="border: 0px solid;color:red;" readonly="readonly"/> --%>
						<textarea rows="3" cols="17" readonly="readonly" style="border: 0px solid;color:red;">${detection.resultExplain }</textarea>
					</p>
				</div>
	  	</fieldset>
</div>