<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp" %>
<div>
	<fieldset style="border: 1px solid #b8d0d6;height: 380px;width: 300px;margin-left: 30%;margin-top:10%;">
		<div class="pageFormContent" layoutH="120" style="border: 0px solid;margin-left: 3%;">
			<input type="hidden" name="suId" value="${sm.suId}">
			<p align="center"><font><b>节点信息</b></font></p>
			<div class="divider"></div>
			<p align="left">
				<label>供货市场编号</label>
				<input value="${sm.no }" style="border: 0px solid;color:red;" readonly="readonly"/>
			</p>
			<p align="left">
				<label>供货市场名称</label>
				<input value="${sm.name }" style="border: 0px solid;color:red;" readonly="readonly"/>
			</p>
			<p align="left">
				<label>节点类型</label>
				<c:forEach items="${supplyType }" var="btype">
					<c:if test="${btype.key==sm.sNodeType }">
						<input value="${btype.value }" style="border: 0px solid;color:red;" readonly="readonly"/>
					</c:if>
					
				</c:forEach>
			</p>
			<p align="left">
				<label>法人代表：</label>
				<input value="${sm.legalRepresent }" style="border: 0px solid;color:red;" readonly="readonly"/>
			</p>
			<p align="left">
				<label>工商登记号：</label>
				<input value="${sm.regId }" style="border: 0px solid;color:red;" readonly="readonly"/>
			</p>
			<p align="left">
				<label>所属地区编码</label>
				<input value="${sm.areaNo }" style="border: 0px solid;color:red;" readonly="readonly"/>
			</p>
			<p align="left">
				<label>所属地区名称</label>
				<input value="${sm.areaName }" style="border: 0px solid;color:red;" readonly="readonly"/>
			</p>
			<p align="left">
				<label>企业地址</label>
				<input value="${sm.addr }" style="border: 0px solid;color:red;" readonly="readonly"/>
			</p>
			<p align="left">
				<label>添加时间</label>
				<input value="<fmt:formatDate value="${sm.recordDate }" pattern="yyyy-MM-dd HH:mm:ss" />" pattern="yyyy-MM-dd " style="border: 0px solid;color:red;" readonly="readonly"/>
			</p>
			<div class="divider"></div>
			<p align="right">
				<button id="submit-node"  title="修改节点" value="修改" style="margin-right: 10px;">修改</button>
			</p>
		</div>
	</fieldset>
</div>
<script type="text/javascript">
	 $(function(){
		$("#submit-node").click(function(){
			alertMsg.confirm("您确定要修改节点信息吗？ ", {
				okCall: function(){
					navTab.reload("${CTX}/user/toUpdateShowNode.do",null,"aaa");
				}
			});
		});
	}); 
</script>