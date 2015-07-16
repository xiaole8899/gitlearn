<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp" %>

<div class="pageContent"  style="border: 0px solid red;">
	<input type="hidden" name="id" value="${merchant.bizId }" />
		<fieldset style="border: 1px solid #b8d0d6;height: 430px;width: 180px;margin-left: 10px;">
	    	<legend style="border: 0px solid">费用详细信息</legend>
	    		<!-- <font style="padding-top: 50px;font-weight: bold;color: red;">该商户已在城市平台备案,备案号为:110110110110</font> -->
			  	<div class="pageFormContent" layoutH="68" style="border: 0px solid red;">
					
					<p>
						<label>费用名称：</label>
						<input style="border: 0px solid;color:red;" type="text" readonly="readonly" value="${costs.costsName }"  size="30" />
					</p>
					<p>
						<label>适用商品编号：</label>
						<c:if test="${empty costs.goodsCodes  }">
							<input style="border: 0px solid;color:red;" type="text" readonly="readonly" value="所有商品"  size="30" />
						</c:if>
						<c:if test="${not empty costs.goodsCodes  }">
							<input style="border: 0px solid;color:red;" type="text" readonly="readonly" value="${costs.goodsCodes }"  size="30" />
						</c:if>
					</p>
					<p>
						<label>适用商品名称：</label>
						<c:if test="${empty costs.goodsNames  }">
							<input style="border: 0px solid;color:red;" type="text" readonly="readonly" value="所有商品"  size="30" />
						</c:if>
						<c:if test="${not empty costs.goodsNames  }">
							<input style="border: 0px solid;color:red;" type="text" readonly="readonly" value="${costs.goodsNames }"  size="30" />
						</c:if>
					</p>
					<p>
						<label>卖方定额(元)：</label>
						<input type="text" value="${costs.sellerQuota }" readonly="readonly" style="border: 0px solid;color:red;"  size="30" />
					</p>
					<p>
						<label>卖方比例(%)：</label>
						<input type="text" style="border: 0px solid;color:red;" readonly="readonly"  value="${costs.sellerRate }" size="30" />
					</p>
					<p>
						<label>买方定额(元)：</label>
						<input type="text" value="${costs.buyerQuota }" readonly="readonly" style="border: 0px solid;color:red;"  size="30" />
					</p>
					<p>
						<label>买方比例(%)：</label>
						<input type="text" style="border: 0px solid;color:red;" readonly="readonly"  value="${costs.buyerRate }" size="30" />
					</p>
					<p>
						<label>创建人：</label>
						<input type="text" value="${costs.usrName }" readonly="readonly" style="border: 0px solid;color:red;"  size="30" />
					</p>
					<p>
						<label>创建时间：</label>
						<input type="text" style="border: 0px solid;color:red;" readonly="readonly"  value="<fmt:formatDate value="${costs.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" size="30" />
					</p>
					<p>
						<label>最后一次数据更新时间：</label>
						<input type="text" style="border: 0px solid;color:red;" readonly="readonly"  value="<fmt:formatDate value="${costs.updateTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" size="30" />
					</p>
					<!-- <button style="float: right;margin-top: 20px;" class="close" value=" 关闭">关闭</button> -->
				</div>
	  	</fieldset>
</div>