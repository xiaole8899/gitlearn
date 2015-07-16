<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="../commons/taglibs.jsp" %>

<div class="pageContent">
	<form method="post" action="${CTX }/supplyMarket/addSupplyMarket.do" rel="addSupplyMarket"  class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		 <div class="pageFormContent"  layoutH="56">
	    	<p>
	    		<label>供货市场名称:</label>
	    		<input type="text" class="required" name="name" alt="请输入名称" size="30"></input>
	    	</p>
	    	
	    	 <p>
	    		<label>供货市场编号:</label>
	    		<input type="text" class="required nosuply" name="no" alt="请输入编号" size="30"      minlength="9" maxlength="9" ></input>
	    	</p>
	    	
	    	<p>
	    		<label>类型:</label>
	    	<!-- 	<input type="text" class="required" name="nodeType" size="30"> -->
	    	 <select class="required" name ="sNodeType"   style="width:212px; height:22px;">  
	    		 <c:forEach var="map" items="${map}">
                    <option value="${map.key}" title="你确定选择此项">${map.value}</option>  
                  <!-- <option value="2">Saab</option>  
                    <option value="3">Mercedes</option>  
                    <option value="4" title="Audi, your best choice!">Audi</option>   -->
                 </c:forEach>
                </select>   
	    	</p>
	    	
	    	<p>
	    		<label>所属地区名称：</label>
<!-- 	    		<input type="text" class="required" name="areaName" alt="请输入所在地" size="30"></input>
 -->	    			
              	<select class="combox" name="province" ref="w_combox_city_enterpriseadd" refUrl="${CTX }/county//findCountyByPrId.do?prentId={value}">
					<option value="all">所有省市</option>
					<c:forEach var="are" items="${listArea}">
						<option value="${are.cId },${are.cName }">${are.cName }</option>
					</c:forEach>
				</select>
				<select class="combox" name="city" id="w_combox_city_enterpriseadd" ref="w_combox_area_enterpriseadd" refUrl="${CTX }/county//findCountyByPrId.do?prentId={value}">
					<option value="all">所有城市</option>
				</select>
				<select class="combox" name="street" id="w_combox_area_enterpriseadd">
					<option value="all">所有区县</option>
				</select>
	    	</p>
	    	 <p>
	    		<label>法人代表:</label>
	    		<input type="text" class="required" name="legalRepresent"  size="30"></input>
	    	</p>
			<p>
	    		<label>工商登记号:</label> 
	    		<input type="text" class="required number" name="regId"  size="30"></input>
	    	</p>
	    	<p>
	    		<label>企业地址：</label>
	    		<input type="text" class="required" name="addr" alt="请输入详细地址" size="30"></input>
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