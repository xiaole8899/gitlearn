<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/kit/dwz/js/dwz.resetformvalue.js"></script>
<%@ include file="../commons/taglibs.jsp" %>
<%@ taglib uri="/dppc-load" prefix="goods" %>
<style type="text/css">
	.open-goods{ background: url("${CTX}/kit/dwz/themes/default/images/tree/plus.gif") no-repeat; display: block; width: 18px; height: 18px; }
	.close-goods { background: url("${CTX}/kit/dwz/themes/default/images/tree/minus.gif")no-repeat; display: block; width: 18px; height: 18px;}
</style>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="${CTX }/goods/getList.do" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>商品名称：<input type="text" class="checkReset" name="goodsName" value="${goods.goodsName }" /></td>
					<td>商品首字母：<input type="text" class="checkReset" name="firstLetter" value="${goods.firstLetter }" /></td>
					<td>商品拼音：<input type="text" class="checkReset" name="pinYin" value="${goods.pinYin }" /></td>
				</tr> 
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button id="button_thing" type="submit">检索</button></div></div></li>
					<li><div class="buttonActive"><div class="buttonContent"><button class='resetForm'>重置</button></div></div></li>
				</ul>
			</div>
		</div>	
	</form>
</div>


<div class="pageContent" id="HL_goods">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${CTX }/goods/toAddGoods.do?preCode=0" target="dialog" rel="usertoadd"><span>添加商品</span></a></li>
			<%-- <li><a class="add" href="${CTX }/user/toAdd.do" target="dialog" rel="usertoadd"><span>添加用户</span></a></li>
			<li><a class="delete" href="${CTX }/user/delete.do?userId={user_id}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li><a class="edit" href="${CTX }/user/toEdit.do?userId={user_id}" target="dialog"><span>修改</span></a></li>
			<li><a class="add" href="${CTX }/user/roleAdd.do?userId={user_id}" target="dialog"><span>分配角色</span></a></li> --%>
		</ul>
	</div>
	<%-- 数据列表 --%>
	<goods:resource goodsList="${goodsList }"/>
	<%-- 分页查询的Form --%>
	<form id="pagerForm" action="${CTX }/goods/getList.do" method="post">
		<input type="hidden" name="pageNum" value="${page.pageIndex }" />
		<input type="hidden" name="numPerPage" value=" ${page.pageSize }" />
		<input type="hidden" name="orderField" value="${order.field }" />
		<input type="hidden" name="orderDirection" value="${order.direction }" />
	</form> 
	<%-- 分页脚注 --%>
	<%-- <div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage"  onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20" <c:if test="${page.pageSize eq 20 }">selected</c:if>>20</option>
				<option value="50" <c:if test="${page.pageSize eq 50 }">selected</c:if>>50</option>
				<option value="100" <c:if test="${page.pageSize eq 100 }">selected</c:if>>100</option>
				<option value="200" <c:if test="${page.pageSize eq 200 }">selected</c:if>>200</option>
			</select>
			<span>条，共${page.totalRows}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${page.totalRows }" currentPage="${page.pageIndex }" numPerPage="${page.pageSize }"></div>
	</div> --%>
</div>

<br id="ajaxStop_getGoodsList"/>

<script type="text/javascript">
// 
$("#ajaxStop_getGoodsList").bind("stop_ajax_custom", function() {
	//比较相等
	String.prototype.equals = function(s){  
	    return this == s;  
	}   
	
	//以。。开头
	String.prototype.startWith=function(str){    
	  var reg=new RegExp("^"+str);    
	  return reg.test(this);       
	} 

	//以。。结尾
	String.prototype.endWith=function(str){    
	  var reg=new RegExp(str+"$");    
	  return reg.test(this);       
	}
	
	//var $a =$("open-goods,close-goods");
	
	
	$(".open-goods,.close-goods").click(function(){
		//商品编号
		var goodsCode=$(this).attr("goodsCode");
		var level =$(this).attr("level");
		if($(this).attr("title").equals("打开")){
			$(this).attr("title","关闭");
			$(this).removeClass();
			$(this).addClass("close-goods");
			$("tr." +goodsCode.trim() +"-child").each(function() {
				var thislevel =$(this).attr("level");
				if ((thislevel-1) == (level-0)) {
					$(this).css("display", "table-row").find(".close-goods").attr("title","打开").removeClass().addClass("open-goods");
				}else{
					$(this).css("display", "none");
				}
			});
		}else{
			//$(this).text("打开");
			$(this).attr("title","打开")
			$(this).removeClass();
			$(this).addClass("open-goods");
			$("tr." +goodsCode.trim() +"-child").css("display", "none");
		}
	
	}); // click end
});
	
</script>