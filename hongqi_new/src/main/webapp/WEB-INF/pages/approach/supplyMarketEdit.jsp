<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="../commons/taglibs.jsp" %>

<div class="pageContent">
	<form method="post" action="${CTX }/supplyMarket/editSupplyMarket.do" rel="editSupplyMarket"  class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		 <div class="pageFormContent"  layoutH="56">
		 <input type="hidden" name="suId" value="${suMarket.suId }"/>
		 <input type="hidden" name="oldAreaName" value="${suMarket.areaName }"/>
		 <input type="hidden" name="oldAreaNo" value="${suMarket.areaNo }"/>
	    	<p>
	    		<label>名称:</label>
	    		<input type="text" class="required" name="name" value="${suMarket.name }" size="30"></input>
	    	</p>
	    	
	    	 <p>
	    		<label>编号:</label>
	    		<input type="text" class="required nosuply" name="no" value="${suMarket.no }" size="30"></input>
	    	</p>
	    	
	    	<p>
	    		<label>类型:</label>
	    		<select name="sNodeType" class="required"  style="width:212px; height:22px;" >
					<option value="" style="width:225px;">请选择</option>
					<c:forEach items="${supplyType }" var="btype">
							<option value="${btype.key }" <c:if test="${suMarket.sNodeType== btype.key}">selected="selected"</c:if>>${btype.value }</option>
					</c:forEach>
				</select>
	    	</p>
	    	
	    	<p>
	    		<label>所在地：</label>
	    		  <select class="combox" name="province" ref="w_combox_city_enterpriseadd" currentValue='${province}' refUrl="${CTX }/county/findCountyByPrId.do?prentId={value}">
					<option value="all">请选择</option>
					<c:forEach var="are" items="${listArea}">
						<option value="${are.cId },${are.cName }">${are.cName }</option>
					</c:forEach>
				</select>
				<select class="combox" name="city" id="w_combox_city_enterpriseadd" currentValue='${city}' ref="w_combox_area_enterpriseadd" refUrl="${CTX }/county/findCountyByPrId.do?prentId={value}">
					<option value="all">请选择</option>
				</select>
				<select class="combox" name="street" id="w_combox_area_enterpriseadd" currentValue='${street}'>
					<option value="all">请选择</option>
				</select>
	    	</p>
	    	
	    	 <p>
	    		<label>法人代表:</label>
	    		<input type="text" class="required" name="legalRepresent" value="${suMarket.legalRepresent }" size="30"></input>
	    	</p>
			<p>
	    		<label>工商登记号:</label> 
	    		<input type="text" class="required number" name="regId" value="${suMarket.regId }" size="30"></input>
	    	</p>
	    	
	    	<p>
	    		<label>详细地址</label>
	    		<input type="text" class="required" name="addr" value="${suMarket.addr }" size="30"></input>
	    	</p>
	      <div class="divider"></div>
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