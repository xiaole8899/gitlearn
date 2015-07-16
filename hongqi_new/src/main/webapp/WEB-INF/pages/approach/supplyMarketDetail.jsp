<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp" %>

<div class="pageContent">
		<fieldset style="border: 1px solid #b8d0d6;height: 430px;width: 180px;margin-left: 10px;">
	    	<legend style="border: 0px solid">详细信息</legend>
			  	<div class="pageFormContent" layoutH="66" style="border: 0px solid;">
					<p>
						<label>供货市场编号：</label>
						<input style="border: 0px solid;color:red;" type="text" value="${supplyMarketInfo.no }" readonly="readonly" name="bizName" size="30" />
					</p>
					<p>
						<label>供货市场名称：</label>
						<input style="border: 0px solid;color:red;" type="text" readonly="readonly" value="${supplyMarketInfo.name }" name="bizNo" size="30" />
					</p>
					<p>
						<label>类型：</label>
    		    	<c:forEach var="map" items="${map}">
                    	<c:if test="${supplyMarketInfo.sNodeType==map.key }">
                    	<input type="text" style="border: 0px solid;color:red;" readonly="readonly" value="${map.value}" size="30" />
                    	</c:if>  
                   	</c:forEach>
					</p>
					<p>
						<label>所属地区编码：</label>
						<input type="text" style="border: 0px solid;color:red;" value="${supplyMarketInfo.areaNo }"  name="legalRepresent" size="30" />
					</p>
					
					<p>
						<label>所属地区名称：</label>
						<input type="text" style="border: 0px solid;color:red;" value="${supplyMarketInfo.areaName }"  name="legalRepresent" size="30" />
					</p>
					
					<p>
						<label>企业地址：</label>
						<input type="text" style="border: 0px solid;color:red;" value="${supplyMarketInfo.addr }"  name="legalRepresent" size="30" />
					</p>
					
					<p>
						<label>添加时间：</label>
						<input type="text"   name="identityCard" value="${supplyMarketInfo.recordDate }" readonly="readonly" style="border: 0px solid;color:red;" size="30" />
					</p>
				</div>
	  	</fieldset>
</div>
