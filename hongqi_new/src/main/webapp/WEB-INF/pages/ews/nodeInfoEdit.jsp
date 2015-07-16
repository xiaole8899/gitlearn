<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="../commons/taglibs.jsp" %>

<div class="pageHeader">
</div>

<div class="pageContent">
	<form method="post" action="${CTX }/user/doEditShowNode.do" rel="editSupplyMarket"  class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		 <div class="pageFormContent"  layoutH="56">
		 <input type="hidden" name="suId" value="${sm.suId }"/>
	    	<p>
	    		<label>名称:</label>
	    		<input type="text" class="required" name="name" value="${sm.name }" size="30"></input>
	    	</p>
	    	
	    	 <p>
	    		<label>编号:</label>
	    		<input type="text" class="required" name="no" value="${sm.no }" size="30"></input>
	    	</p>
	    	
	    	<p>
	    		<label>类型:</label>
	    		<input type="text" class="required" name="sNodeType" value="${sm.sNodeType }" size="30"></input>
	    	</p>
	    	
	    	<p>
	    		<label>所在地：</label>
	    		<input type="text" class="required" name="areaName" value="${sm.areaName }" size="30"></input>
	    	</p>
	    	
	    	<p>
	    		<label>详细地址</label>
	    		<input type="text" class="required" name="addr" value="${sm.addr }" size="30"></input>
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