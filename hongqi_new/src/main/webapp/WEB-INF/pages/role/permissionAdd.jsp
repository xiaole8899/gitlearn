<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp" %>
<div class="pageContent">
	<form id="submitForm" method="post" action="${CTX }/role/doPermissions.do" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<input type="hidden" name="roleId" value="${roleId }" />
			<div id="content-div">
				<table id="table" class="table" style="width: 555 ;border: 0px solid">
					<thead >
						<tr>
							<th width="40">主菜单</th>
							<th width="40">子菜单</th>
							<th width="40">排序</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="menuList" items="${menuList}">
							<tr>
								<td align="center" bgcolor="#CCCCCC" class="parents">
									<input name="menuId" type="checkbox" class='menuIds' 
										<c:forEach var="menuYet" items="${menuYet}">
											<c:if test="${menuYet.menuId==menuList.menuId}">checked="checked"</c:if>
										</c:forEach>
						 			 value="${menuList.menuId }" />${menuList.menuName }
								</td>
								<td bgcolor="#CCCCCC"></td>
								<td align="center" bgcolor="#CCCCCC">${menuList.sort}</td>
							</tr>
							<c:forEach items="${menuList.childList}" var="childList">
								<tr>
									<td></td>
									<td align="center" class="childs">
										<input name="menuId"  type="checkbox"  value="${childList.menuId }" class='child_${menuList.menuId }' parentId="${menuList.menuId }"
											<c:forEach var="menuYet" items="${menuYet}">
												<c:if test="${menuYet.menuId==childList.menuId}">checked="checked"</c:if>
											</c:forEach>
										/>${childList.menuName }
									</td>
									<td align="center">${childList.sort }</td>
								</tr>
							</c:forEach>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button  type="submit">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">
 $(function() {
	//点击父菜单时子菜单全选
	$(".menuIds").live("click",function(){
		$(this).each(function(){
			if($(this).is(":checked")){
				$(".child_"+$(this).val()+"").attr("checked",true);
			}else{
				$(".child_"+$(this).val()+"").attr("checked",false);
			}
		});
	});
	
	//当选中子菜单时同时选中父菜单
	$("input[type=checkbox]").live("click",function(){
		$(this).each(function(){
			if($(this).attr("class")!="menuIds"){
				var child=$(this).attr("parentId");
				$("input[value='"+child+"']").attr("checked",true);
			}
		});
	});
}); 
</script>
