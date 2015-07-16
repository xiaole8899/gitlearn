<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp" %>
<%@ include file="../commons/dwzres.jsp" %>
<script type="text/javascript">
	$(function(){
		$("#import_button").click(function(){
			var file = $("#file").val();
			var str = file.substring(file.length-4,file.length);
			if(str==".xml"){
				return true;
			}
			else if(str==""){
				//alertMsg.error('请选择xml文件！ ');
				alert('请选择xml文件！ ');
				return false;
			}
			//alertMsg.error('只能选择xml文件！ ');
			alert('只能选择xml文件！ ');
			return false;
		});
	})
</script>
 <%-- <div class="pageContent">
	<form method="post" action="${CTX }/initialize/doImport.do" enctype="multipart/form-data" style="width:350px;margin: 100px auto 0" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		 <div class="pageFormContent" layoutH="56">
			<p>
				<label>选择文件：</label>
				<input type="file" id="file" name="file" size="30" class="required"/>
			</p>
			<div class="divider"></div>
		</div> 
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button id="import_button" type="submit">确定</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>   --%>
 <div align="center">
	<form method="post" action="${CTX }/initialize/doImport.do" enctype="multipart/form-data" style="width:350px;margin: 100px auto 0">
		<table border="1">
			<tr>
				<td>选择文件：</td>
				<td><input type="file" id="file" name="file" size="30" class="required"/></td>
			</tr>
			<tr>
				<td colspan="2" align="center" ><input type="submit" id="import_button" value="确定" /><input type="reset" value="取消"></td>
			</tr>
		</table>
	</form>
</div>
