<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp" %>
<div>
	<fieldset style="border: 1px solid #b8d0d6;height: 317px;width: 233px;margin-left: 366px;margin-top:90">
		<div class="pageFormContent" layoutH="66" style="border: 0px solid;">
			<input type="hidden" name="suId" value="${sm.suId}">
			<p align="center"><font><b>节点信息</b></font></p>
			<div class="divider"></div>
			<p align="center">
				<label>供货市场编号</label>
				<input value="${sm.no }" style="border: 0px solid;color:red;" readonly="readonly"/>
			</p>
			<p align="center">
				<label>供货市场名称</label>
				<input value="${sm.name }" style="border: 0px solid;color:red;" readonly="readonly"/>
			</p>
			<p align="center">
				<label>类型</label>
				<input value="${sm.sNodeType }" style="border: 0px solid;color:red;" readonly="readonly"/>
			</p>
			<p align="center">
				<label>所属地区编码</label>
				<input value="${sm.areaNo }" style="border: 0px solid;color:red;" readonly="readonly"/>
			</p>
			<p align="center">
				<label>所属地区名称</label>
				<input value="${sm.areaName }" style="border: 0px solid;color:red;" readonly="readonly"/>
			</p>
			<p align="center">
				<label>企业地址</label>
				<input value="${sm.addr }" style="border: 0px solid;color:red;" readonly="readonly"/>
			</p>
			<p align="center">
				<label>添加时间${CTX }</label>
				<input value="${sm.recordDate }" pattern="yyyy-MM-dd " style="border: 0px solid;color:red;" readonly="readonly"/>
			</p>
			<div class="divider"></div>
			<p align="right">
				<a id="submit-node"  title="修改节点">修改</a>
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