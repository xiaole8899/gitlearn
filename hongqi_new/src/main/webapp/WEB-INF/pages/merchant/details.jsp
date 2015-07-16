<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp" %>

<div class="pageContent"  style="border: 0px solid red;">
	<input type="hidden" name="id" value="${merchant.bizId }" />
		<fieldset style="border: 1px solid #b8d0d6;height: 430px;width: 180px;margin-left: 10px;">
	    	<legend style="border: 0px solid">详细信息</legend>
	    		<!-- <font style="padding-top: 50px;font-weight: bold;color: red;">该商户已在城市平台备案,备案号为:110110110110</font> -->
			  	<div class="pageFormContent" layoutH="68" style="border: 0px solid red;">
					<p>
						<label>
							<c:if test="${merchant.property=='个体' }">
								字号名称:
							</c:if>
							<c:if test="${merchant.property=='企业' }">
								企业名称:
							</c:if>
						</label>
						<input style="border: 0px solid;color:red;" type="text" value="${merchant.comName }" readonly="readonly" name="bizName" size="30" />
					</p>
					<p>
						<label>商户备案号：</label>
						<input style="border: 0px solid;color:red;" type="text" readonly="readonly" value="${merchant.bizNo }" name="bizNo" size="30" />
					</p>
					<p>
						<label>商户性质：</label>
						<input type="text" style="border: 0px solid;color:red;" readonly="readonly"  name="property" value="${merchant.property }" />
					</p>
					<p>
						<label>持卡人：</label>
						<input style="border: 0px solid;color:red;" type="text" value="${merchant.bizName }" readonly="readonly" name="bizName" size="30" />
					</p>
					<p  id="faren">
						<label>法人：</label>
						<input type="text" style="border: 0px solid;color:red;" value="${merchant.legalRepresent }"  name="legalRepresent" size="30" />
					</p>
					
					<p>
						<label>经营类型：</label>
						<c:forEach items="${businessTypeList }" var="btype">
							<c:if test="${btype.key==merchant.businessType }">
								<input type="text" style="border: 0px solid;color:red;" readonly="readonly" value="${btype.value }"   size="30" />
							</c:if>
						</c:forEach>
					</p>
					
					<p>
						<label>商户类型：</label>
						<c:forEach items="${bizTypeList }" var="bt">
							<c:if test="${bt.key==merchant.bizType }">
								<input type="text" style="border: 0px solid;color:red;" readonly="readonly" value="${bt.value }"   size="30" />
							</c:if>
						</c:forEach>
					</p>
					
					<p id="sfzDiv">
						<label>身份证号：</label>
						<input type="text"   name="identityCard" value="${merchant.identityCard }" readonly="readonly" style="border: 0px solid;color:red;" size="30" />
					</p>
				
					<p  id="gsDiv">
						<label>工商登记号：</label>
						<input type="text" value="${merchant.regId }" readonly="readonly" style="border: 0px solid;color:red;"  name="regId" size="30" />
					</p>
					<p>
						<label>联系电话：</label>
						<input type="text" value="${merchant.tel }" readonly="readonly" style="border: 0px solid;color:red;" name="tel" size="30" />
					</p>
					<p>
						<label>摊位编号：</label>
						<input type="text" style="border: 0px solid;color:red;" readonly="readonly" name="boothNo" value="${merchant.boothNo }" size="30" />
					</p>
					<p style="font-size: 12px; font-weight: bold; margin-top: 10px; margin-bottom: 10px;">
						该用户的卡信息如下：
						<%-- <a id="openCardGo" mId="${merchant.bizId }"  style="font-size: 16px; font-weight: bold;cursor: pointer;margin-left: 20px;">去开卡</a> --%>
					</p>
					<table class="table" width="415">
							<thead>
								<tr>
									<th>卡号</th>
									<th>卡类型</th>
									<!-- <th>卡性质</th> -->
									<th>卡状态</th> 
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach var="card" items="${merchant.cards }">
								<tr align="center">
									<td>${card.cardNo }</td>
									<%-- <td>
										<c:if test="${card.cardKind=='01' }">主卡</c:if>
										<c:if test="${card.cardKind=='02' }">副卡</c:if>
									</td> --%>
									<td>
										<c:if test="${card.cardType=='01'}">服务卡</c:if>
									</td>
									<td>
										<c:if test="${card.cardStatus=='0'}">正常</c:if>
										<c:if test="${card.cardStatus=='1' }">挂失</c:if>
										<c:if test="${card.cardStatus=='2' }">注销</c:if>
									</td>
									<td>
										<c:if test="${card.cardStatus!='2'}">
										<a style="cursor: pointer;" class="btnDel" id='cardDelete' status="2" cardId="${card.id }" mId="${card.merchant.bizId }"   title="注销账户">注销账户</a>
										</c:if>
										<%-- <c:if test="${card.cardStatus=='0'}">
											<a style="cursor: pointer;" id="gsCard" class="btnInfo" status="1"  cardId="${card.id }" mId="${card.merchant.bizId }"   title="挂失">挂失</a>
										</c:if>
										<c:if test="${card.cardStatus=='1'}">
											<a style="cursor: pointer;" id="jgCard" class="btnSelect" status="0"   cardId="${card.id }" mId="${card.merchant.bizId }"   title="解挂">解挂</a>
										</c:if> --%>
										<c:if test="${card.cardStatus=='2'}">
											无
										</c:if>
									</td>
								</tr>
							</c:forEach>
							<c:if test="${ cardSize ==0 }">
								<tr align="center">
									<td colspan="3" style="font: 14px bold;">该用户未开卡  </td>
								</tr>
							</c:if>
						</tbody>
						</table>
					<!-- <button style="float: right;margin-top: 20px;" class="close" value=" 关闭">关闭</button> -->
				</div>
	  	</fieldset>
</div>
<script type="text/javascript">
$(function(){
	//注销卡
	$("#cardDelete").live("click",function(){
		//卡编号
		var cId=$(this).attr("cardId");
		//持卡人编号
		var mId=$(this).attr("mId");
		alertMsg.confirm("确认注销此卡吗？注销后不可恢复!", {
			okCall: function(){
				//发送AJAX请求
				$.post("${CTX}/merchant/deleteCard.do",{cId:cId},function(data){
					if(data.statusCode=="200"){
						alertMsg.correct('操作成功!')
						$.pdialog.reload("${CTX}/merchant/toDetailsMerchant.do?mId="+mId, {data:{}, dialogId:"deleteCard", callback:null})
					}else{
						alertMsg.error('操作失败!')
					}
				},"json");
			}
		});
	});
	//挂失和解挂
	$("#cardDelete,#gsCard,#jgCard").live("click",function(){
		//卡编号
		var cId=$(this).attr("cardId");
		//状态
		var status=$(this).attr("status");
		//持卡人编号
		var mId=$(this).attr("mId");
		//提示信息
		var info=""
		if(status=="0"){
			info="确认解挂此卡吗?";
		}else if(status=="1"){
			info="确认挂失此卡吗?";
		}else{
			info="确认注销卡吗,注销后将不可恢复?";
		}
		alertMsg.confirm(info, {
			okCall: function(){
				//发送AJAX请求
				$.post("${CTX}/merchant/lossOrSolutionCard.do",{cId:cId,status:status},function(data){
					if(data.statusCode=="200"){
						alertMsg.correct('操作成功!')
						$.pdialog.reload("${CTX}/merchant/toDetailsMerchant.do?mId="+mId, {data:{}, dialogId:"deleteCard", callback:null})
					}else{
						alertMsg.error('操作失败!')
					}
				},"json");
			}
		});
	});
	
	//跳转开卡页面
	/* $("#openCardGo").live("click",function(){
		$.pdialog.closeCurrent();
		//持卡人编号
		var mId=$(this).attr("mId");
		$.pdialog.open("${CTX }/merchant/toAddCard.do?mId="+mId,"openCard","开卡","{width:520,height:340,max:true,mask:true,mixable:true,minable:true,resizable:true,drawable:true,fresh:true}");
	});   */
});
</script>