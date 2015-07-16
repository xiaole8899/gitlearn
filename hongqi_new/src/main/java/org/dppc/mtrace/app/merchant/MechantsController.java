package org.dppc.mtrace.app.merchant;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.dppc.mtrace.app.AppConstant;
import org.dppc.mtrace.app.approach.entity.SupplyMarketEntity;
import org.dppc.mtrace.app.approach.service.SupplyMarketService;
import org.dppc.mtrace.app.approach.util.PrintWriterUtil;
import org.dppc.mtrace.app.merchant.entity.FundsRecord;
import org.dppc.mtrace.app.merchant.entity.MCard;
import org.dppc.mtrace.app.merchant.entity.Merchant;
import org.dppc.mtrace.app.merchant.service.FundsRecordService;
import org.dppc.mtrace.app.merchant.service.MCardService;
import org.dppc.mtrace.app.merchant.service.MerchantsService;
import org.dppc.mtrace.frame.annotation.OperationLog;
import org.dppc.mtrace.frame.base.DwzResponse;
import org.dppc.mtrace.frame.base.OrderCondition;
import org.dppc.mtrace.frame.base.Page;
import org.dppc.mtrace.frame.kit.JsonKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author hle
 *
 */
@Controller
@RequestMapping(value="/merchant")
public class MechantsController {

	@Autowired
	private MerchantsService merchantsService;
	
	@Autowired
	private MCardService mcardService;
	
	@Autowired
	private SupplyMarketService supplyMarketService;
	
	
	@Autowired
	private FundsRecordService recordService;
	
	private DecimalFormat df=new DecimalFormat("0000");
	
	
	
	/**
	 * 跳转手动备案商户页面
	 * @return
	 */
	@RequestMapping(value="/toAddMerchant")
	public String toAddMerchant(HttpServletRequest request){
		//加载商户类型
		Map<String,String> businessTypeList=AppConstant.businessType;
		request.setAttribute("businessTypeList",businessTypeList);
		//加载商户经营类型
		Map<String,String> bizTypeList=AppConstant.bizType;
		request.setAttribute("bizTypeList",bizTypeList);
		return "merchant/addmerchant";
	}
	
	 
	
	/**
	 * 跳转读卡备案商户页面
	 * @return
	 */
	@RequestMapping(value="/toAddMerchantByCard")
	public String toAddMerchantByCard(HttpServletRequest request){
		//加载商户经营类型
		Map<String,String> bizTypeList=AppConstant.bizType;
		request.setAttribute("bizTypeList",bizTypeList);
		return "merchant/addmerchantbycard";
	}
	
	/**
	 * 读卡商户备案操作
	 * @param merchants
	 * @throws IOException 
	 */
	@RequestMapping(value="/doAddMerchantByCard")
	@OperationLog(value="读卡商户备案操作")
	public void doAddMerchantByCard(Merchant merchants,HttpServletRequest request,HttpServletResponse response) throws IOException{
		DwzResponse dwzResponse=new DwzResponse();
		response.setContentType("text/html; charset=utf-8");
		PrintWriter pw=response.getWriter();
	
		if(merchantsService.getMerchantByBizNo(merchants.getBizNo())!=null){
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setMessage("重复备案");
		}else{
			
			//设置为已上传备案信息
			merchants.setUploadTime(new Timestamp(new Date().getTime()));
			merchants.setBalance(0.0);
			merchants.setProperty("企业");
			merchants.setBusinessType("1002");
			Timestamp updateTime = new Timestamp(new Date().getTime());
			merchants.setUpdateTime(updateTime);
			//默认余额为0.0
			merchants.setBalance(0.0);
			merchants.setTradePwd(DigestUtils.md5Hex(merchants.getTradePwd()));
			Set<MCard> cards=new HashSet<MCard>();
			MCard card=new MCard();
			card.setCardHolder(merchants.getBizName());
			card.setCardHolderIdentity(merchants.getIdentityCard());
			card.setCardKind("01");
			String cardNo=request.getParameter("cardNo");
			card.setCardNo(cardNo);
			card.setCardStatus("0");
			card.setCardType("01");
			card.setCardType("01");
			card.setMerchant(merchants);
			cards.add(card);
			merchants.setCards(cards);
			int num=merchantsService.addMerchant(merchants);
			if(num>0){
				dwzResponse.setCallbackType(DwzResponse.CT_FORWARD);
				dwzResponse.setForwardUrl("merchant/toAddMerchantByCard.do");
				dwzResponse.setStatusCode(DwzResponse.SC_OK);
				dwzResponse.setMessage("操作成功!");
			}else{
				dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
				dwzResponse.setMessage("操作失败!");
			}
		}
		//json 数据
		String strJson=JsonKit.toJSON(dwzResponse);
		pw.write(strJson);
		pw.flush();
		pw.close();
	}
	
	/**
	 * 添加商户
	 * @param merchants
	 * @throws IOException 
	 */
	@RequestMapping(value="/doAddMerchant")
	@OperationLog(value="手动备案商户操作")
	public void doAddMerchant(Merchant merchants,HttpServletRequest request,HttpServletResponse response) throws IOException{
		DwzResponse dwzResponse=new DwzResponse();
		response.setContentType("text/html; charset=utf-8");
		PrintWriter pw=response.getWriter();
		dwzResponse.setCallbackType(DwzResponse.CT_FORWARD);
		dwzResponse.setForwardUrl("merchant/toAddMerchant.do");
		//默认设置未上传
		//当前时间
		SupplyMarketEntity supplyMarketEntity=AppConstant.getSupplyMarketEntity(request);
		if(supplyMarketEntity==null){
			supplyMarketEntity=supplyMarketService.SupplyMarketInfo("888888");
		}
		String isModified=supplyMarketEntity.getIsModified();
		if(StringUtils.isNotEmpty(isModified) && isModified.equals("0")){
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setMessage("未备案节点信息,无法注册商户!");
		}else{
			Timestamp updateTime = new Timestamp(new Date().getTime());
			merchants.setUpdateTime(updateTime);
			//默认余额为0.0
			merchants.setBalance(0.0);
			merchants.setTradePwd(DigestUtils.md5Hex(merchants.getTradePwd()));
			int num=merchantsService.addMerchant(merchants);
			if(num>0){
				merchants.setBizNo(AppConstant.getSupplyMarketEntity(request).getNo()+df.format(merchants.getBizId()).toString());
				num=merchantsService.updateMerchant(merchants);
				dwzResponse.setStatusCode(DwzResponse.SC_OK);
				dwzResponse.setMessage("操作成功!");
			}else{
				dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
				dwzResponse.setMessage("操作失败!");
			}
		}
		
		//json 数据
		String strJson=JsonKit.toJSON(dwzResponse);
		pw.write(strJson);
		pw.flush();
		pw.close();
	}
	
	/**
	 * 获取商户
	 * @return
	 */
	@RequestMapping(value="/getMerchantList")
	@OperationLog("查询商户操作")
	public String getMerchantList(HttpServletRequest request,Merchant merchant){
		Page<Merchant> page=new Page<Merchant>();
		//当前页
		String pageIndex=request.getParameter("pageNum");
		if(StringUtils.isEmpty(pageIndex)){
			page.setPageIndex(1);
		}else{
			page.setPageIndex(Integer.parseInt(pageIndex));
		}
		//当前页格式
		String pageSize=request.getParameter("numPerPage");
		if(StringUtils.isEmpty(pageSize)){
			page.setPageSize(20);
		}else{
			page.setPageSize(Integer.parseInt(pageSize));
		}
		
		//排序条件
		String filed=request.getParameter("orderField");
		if(StringUtils.isEmpty(filed)){
			filed="updateTime";
		}
		//排序规则（升序或降序）
		String direction=request.getParameter("orderDirection");
		if(StringUtils.isEmpty(direction)){
			direction="desc";
		}
		//排序
		OrderCondition order=new OrderCondition(filed,direction);
		request.setAttribute("order",order);
		merchantsService.getMerchatList(page,order,merchant);
		request.setAttribute("page",page);
		//查询条件
		request.setAttribute("merchant",merchant);
		//商户类型
		Map<String,String> businessTypeList=AppConstant.businessType;
		request.setAttribute("businessTypeList",businessTypeList);
		//加载商户经营类型
		Map<String,String> bizTypeList=AppConstant.bizType;
		request.setAttribute("bizTypeList",bizTypeList);
		return "merchant/list";
	}
	
	
	
	
	
	/**
	 * 加载数据然后跳转至修改页面
	 * @param mId
	 * @return
	 */
	@RequestMapping(value="/toUpdateMerchant")
	public String toUpdateMerchant(String mId,HttpServletRequest request){
		Merchant merchant=merchantsService.getMerchantByMId(mId);
		request.setAttribute("merchant", merchant);
		//加载商户经营类型
		Map<String,String> businessTypeList=AppConstant.businessType;
		request.setAttribute("businessTypeList",businessTypeList);
		//加载商户经营类型
		Map<String,String> bizTypeList=AppConstant.bizType;
		request.setAttribute("bizTypeList",bizTypeList);
		return "merchant/updatemerchant";
	}
	
	/**
	 * 修改商户
	 * @param merchants
	 * @throws IOException 
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value="/doUpdateMerchant")
	@OperationLog(value="修改商户操作")
	public void doUpdateMerchant(Merchant merchants,HttpServletRequest request,HttpServletResponse response) throws IOException{
		DwzResponse dwzResponse=new DwzResponse();
		//dwzResponse.setNavTabId("");
		dwzResponse.setRel("");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter pw=response.getWriter();
		//默认设置未上传
		//更新数据时间
		Timestamp updateTime = new Timestamp(new Date().getTime());
		merchants.setUpdateTime(updateTime);
		//merchants.setUploadTime(updateTime);
		if(request.getParameter("status").equals("1")){
			merchants.setTradePwd(DigestUtils.md5Hex(merchants.getTradePwd()));
		}else{
			merchants.setTradePwd(request.getParameter("tradePwds"));
		}
		int num=merchantsService.isDelete(String.valueOf(merchants.getBizId()));
		if(num>0){
			num=merchantsService.updateMerchant(merchants);
			if(num>0){
				num=merchantsService.updateMerchant(merchants);
				dwzResponse.setStatusCode(DwzResponse.SC_OK);
				dwzResponse.setMessage("操作成功!");
				dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
				//dwzResponse.setNavTabId("merchantManage");
			}else{
				dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
				dwzResponse.setMessage("操作失败!");
				//dwzResponse.setCallbackType("closeCurrent");
			}
			dwzResponse.setStatusCode(DwzResponse.SC_OK);
			dwzResponse.setMessage("操作成功!");
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			//dwzResponse.setNavTabId("merchantManage");
		}else{
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setMessage("操作失败,已备案商户不允许修改信息!");
			dwzResponse.setCallbackType(dwzResponse.CT_CLOSE);
		}
		//json 数据
		String strJson=JsonKit.toJSON(dwzResponse);
		pw.write(strJson);
		pw.flush();
		pw.close();
	}
	
	/**
	 * 删除商户(设置商户状态)
	 * @param merchants
	 * @throws IOException 
	 */
	@RequestMapping(value="/deleteMerchant")
	@OperationLog(value="删除商户操作")
	public void deleteMerchant(String mId,HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		DwzResponse dwzResponse=new DwzResponse();
		//dwzResponse.setNavTabId("");
		dwzResponse.setRel("");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter pw=response.getWriter();
		int num=merchantsService.isDelete(mId);
		if(num>0){
			num=merchantsService.deleteMerchant(mId);
			if(num>0){
				dwzResponse.setStatusCode(DwzResponse.SC_OK);
				dwzResponse.setMessage("操作成功!");
				dwzResponse.setCallbackType(DwzResponse.CT_FORWARD);
				dwzResponse.setForwardUrl("merchant/getMerchantList.do");
				//dwzResponse.se tNavTabId("merchantManage");
			}else{
				dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
				dwzResponse.setMessage("操作失败!");
				//dwzResponse.setCallbackType("closeCurrent");
			}
			dwzResponse.setStatusCode(DwzResponse.SC_OK);
			dwzResponse.setMessage("操作成功!");
			dwzResponse.setCallbackType(DwzResponse.CT_FORWARD);
			dwzResponse.setForwardUrl("merchant/getMerchantList.do");
			//dwzResponse.se tNavTabId("merchantManage");
		}else{
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_FORWARD);
			dwzResponse.setForwardUrl("merchant/getMerchantList.do");
			dwzResponse.setMessage("操作失败,已备案用户不允许注销!");
			//dwzResponse.setCallbackType("closeCurrent");
		}
		//json 数据
		String strJson=JsonKit.toJSON(dwzResponse);
		pw.write(strJson);
		pw.flush();
		pw.close();
	}
	
	/**
	 * 根据编号查询详情
	 * @return
	 */
	@RequestMapping(value="/toDetailsMerchant")
	@OperationLog(value="查询商户详细信息操作")
	public String toDetailsMerchant(HttpServletRequest request,String mId){
		Merchant merchant=merchantsService.getMerchantByMId(mId);
		//获取用户卡
		Set<MCard> cards=merchant.getCards();
		if(cards!=null){
			request.setAttribute("cardSize",cards.size());
		}else{
			request.setAttribute("cardSize",0);
		}
		request.setAttribute("merchant",merchant);
		//加载商户经营类型
		Map<String,String> businessTypeList=AppConstant.businessType;
		request.setAttribute("businessTypeList",businessTypeList);
		//加载商户经营类型
		Map<String,String> bizTypeList=AppConstant.bizType;
		request.setAttribute("bizTypeList",bizTypeList);
		return "merchant/details";
	}
	
	
	/**
	 * 跳转开卡页面
	 * @param mcard 卡信息
	 * @param mId   持卡人
	 * @return
	 */
	@RequestMapping(value="/toAddCard")
	public String toAddCard(HttpServletRequest request){
		//加载商户卡类型
		Map<String,String> cardTypeList=AppConstant.cardType;
		request.setAttribute("cardTypeList",cardTypeList);
		//加载商户性质
		Map<String,String> cardKindList=AppConstant.cardKind;
		request.setAttribute("cardKindList",cardKindList);
		return "merchant/addcard";
	}
	
	
	/**
	 * 获取没开卡用户
	 * @param merchant 商户
	 * @param request  请求
	 * @param response 相应
	 * @return
	 */
	@RequestMapping(value="/getNocardMerchant")
	public String getNocardMerchant(Merchant merchant,HttpServletRequest request,HttpServletResponse response){
		Map<String,String> businessTypeList=AppConstant.businessType;
		request.setAttribute("businessTypeList",businessTypeList);
		//加载商户经营类型
		Map<String,String> bizTypeList=AppConstant.bizType;
		request.setAttribute("bizTypeList",bizTypeList);
		Page<Merchant> page=new Page<Merchant>();
		//当前页
		String pageIndex=request.getParameter("pageNum");
		if(StringUtils.isEmpty(pageIndex)){
			page.setPageIndex(1);
		}else{
			page.setPageIndex(Integer.parseInt(pageIndex));
		}
		//当前页格式
		String pageSize=request.getParameter("numPerPage");
		if(StringUtils.isEmpty(pageSize)){
			page.setPageSize(20);
		}else{
			page.setPageSize(Integer.parseInt(pageSize));
		}
		
		
	
		
		//排序条件
		String filed=request.getParameter("orderField");
		if(StringUtils.isEmpty(filed)){
			filed="merchant.updateTime";
		}
		//排序规则（升序或降序）
		String direction=request.getParameter("orderDirection");
		if(StringUtils.isEmpty(direction)){
			direction="desc";
		}
		//排序
		OrderCondition order=new OrderCondition(filed,direction);
		request.setAttribute("order",order);
		merchantsService.getNoCardMerchant(page,order,merchant);
		request.setAttribute("page",page);
		return "merchant/lookupmerchant";
	}
	
	/**
	 * 开卡
	 * @param merchants
	 * @throws IOException 
	 */
	@RequestMapping(value="/doAddCard")
	@ResponseBody
	@OperationLog(value="商户开卡操作")
	public DwzResponse doAddCard(String mId,MCard mcard,HttpServletRequest request) throws IOException{
		DwzResponse dwzResponse=new DwzResponse();
		dwzResponse.setRel("");
		//默认开卡时正常卡
		mcard.setCardStatus("0");
		mcard.setCardKind("01");
		int num=-1;
		//判断商户是否开卡
		mId=request.getParameter("bizLookup.bizId");
		num=mcardService.isHasMainCard(mId);
		if(num>0){
			//已开卡直接提示不允许再次开卡
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setMessage("该用户已开卡");
		}else{
			//未开卡允许开卡
			num=mcardService.addCard(mcard, mId);
			if(num>0){
				dwzResponse.setForwardUrl("merchant/toAddCard.do");
				dwzResponse.setCallbackType(DwzResponse.CT_FORWARD);
				dwzResponse.setStatusCode(DwzResponse.SC_OK);
				dwzResponse.setMessage("开卡成功,请充值!");
			}else{
				dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
				dwzResponse.setMessage("操作失败!");
			}
		}
		return dwzResponse;
	}
	
	/**
	 * 删除商户卡(设置商户状态)
	 * @param merchants
	 * @throws IOException 
	 */
	@RequestMapping(value="/deleteCard")
	@OperationLog(value="删除商户卡操作")
	public void deleteCard(String cId,HttpServletRequest request,HttpServletResponse response) throws IOException{
		DwzResponse dwzResponse=new DwzResponse();
		response.setContentType("text/html; charset=utf-8");
		PrintWriter pw=response.getWriter();
		int num=mcardService.deleteMCard(cId);
		if(num>0){
			dwzResponse.setStatusCode(DwzResponse.SC_OK);
			dwzResponse.setCallbackType(DwzResponse.CT_FORWARD);
			dwzResponse.setForwardUrl("merchant/cancelCardList.do");
			dwzResponse.setMessage("操作成功!");
		}else{
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setMessage("操作失败!");
		}
		//json 数据
		String strJson=JsonKit.toJSON(dwzResponse);
		pw.write(strJson);
		pw.flush();
		pw.close();
	}
	
	/**
	 * 解挂或者挂失
	 * @param status 1挂失 0解挂
	 * @param cId    卡编号
	 * @throws IOException 
	 */
	@RequestMapping(value="/lossOrSolutionCard")
	@OperationLog(value="商户挂失或解挂卡操作")
	public void lossOrSolutionCard(String status,String cId,HttpServletResponse response) throws IOException{
		DwzResponse dwzResponse=new DwzResponse();
		dwzResponse.setNavTabId("openCard");
		dwzResponse.setRel("openCard");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter pw=response.getWriter();
		MCard mcard=new MCard();
		mcard.setId(Integer.parseInt(cId));
		int num=mcardService.lossOrSolutionCard(status, cId);
		if(num>0){
			dwzResponse.setStatusCode(DwzResponse.SC_OK);
			dwzResponse.setMessage("操作成功!");
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			//dwzResponse.setForwardUrl("merchant/toDetailsMerchant.do?mId=26");
			//dwzResponse.se tNavTabId("merchantManage");
		}else{
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setMessage("操作失败!");
			//dwzResponse.setCallbackType("closeCurrent");
		}
		//json 数据
		String strJson=JsonKit.toJSON(dwzResponse);
		pw.write(strJson);
		pw.flush();
		pw.close();
	}
	
	/**
	 * 商户注销卡管理
	 * @return
	 */
	@RequestMapping(value="/cancelCardList")
	@OperationLog(value="商户销卡操作")
	public String cancelCardList(HttpServletRequest request,MCard mCard){
		Page<MCard> page=new Page<MCard>();
		//当前页
		String pageIndex=request.getParameter("pageNum");
		if(StringUtils.isEmpty(pageIndex)){
			page.setPageIndex(1);
		}else{
			page.setPageIndex(Integer.parseInt(pageIndex));
		}
		//当前页格式
		String pageSize=request.getParameter("numPerPage");
		if(StringUtils.isEmpty(pageSize)){
			page.setPageSize(20);
		}else{
			page.setPageSize(Integer.parseInt(pageSize));
		}
		mcardService.cancelCardList(page,mCard);
		request.setAttribute("page",page);
		request.setAttribute("mCard",mCard);
		return "merchant/cancelcardlist";
	}
	
	
	/**
	 * 跳转充值页面
	 * @return
	 */
	@RequestMapping(value="/toMerchantRecharge")
	public String toMerchantRecharge(HttpServletRequest request,String balance){
		//结果
		if(StringUtils.isNotEmpty(balance)){
			request.setAttribute("balance","当前余额为："+balance);
		}
		return "merchant/recharge";
	}
	
	/**
	 * 跳转取现页面
	 * @return
	 */
	@RequestMapping(value="/toMerchantTakeMoney")
	public String toMerchantTakeMoney(HttpServletRequest request,String balance){
		//结果
		if(StringUtils.isNotEmpty(balance)){
			request.setAttribute("balance","当前余额为："+balance);
		}
		return "merchant/takemoney";
	}
	
	
	/**
	 * 取现
	 * @param bizNo 商户备案号
	 * @param balance 商户余额
	 * @return
	 */
	@RequestMapping(value="/doMerchantTakeMoney")
	@ResponseBody
	@OperationLog(value="商户取现操作")
	public DwzResponse doMerchantTakeMoney(String cardNo,String balance){
		DwzResponse dwzResponse=new DwzResponse();
		//根据备案号查询信息
		MCard mCard=mcardService.getCardInfoByCardNo(cardNo);
		if(mCard==null){
			//为空则没查到信息
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setMessage("未找到有关卡的信息");
		}else{
			String cardStatus=mCard.getCardStatus();
			if(StringUtils.isNotEmpty(cardStatus)){
				//正常卡
				if(cardStatus.equals("0")){
					Merchant merchant=mCard.getMerchant();
					//卡本身原价
					Double originalPrice=merchant.getBalance();
					
					if(originalPrice>=Double.parseDouble(balance)){
						//现价
						Double balancePrice=originalPrice-Double.parseDouble(balance);
						merchant.setBalance(balancePrice);
						int num=merchantsService.merchantRecharge(merchant);
						if(num>0){
							//资金走向
							FundsRecord fs=new FundsRecord();
							Timestamp currentTime = new Timestamp(new Date().getTime());
							fs.setChangeTime(currentTime);
							fs.setType("003");
							fs.setUserName(merchant.getBizName());
							fs.setUserNo(merchant.getBizNo());
							fs.setAmount(-Double.parseDouble(balance));
							fs.setBalance(String.valueOf(balancePrice));
							num=recordService.addFundsRecord(fs);
							if(num>0){
								dwzResponse.setStatusCode(DwzResponse.SC_OK);
								dwzResponse.setMessage("取现成功!");
								dwzResponse.setCallbackType(DwzResponse.CT_FORWARD);
								dwzResponse.setForwardUrl("merchant/toMerchantTakeMoney.do?balance="+balancePrice+"");
							}
						}else{
							dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
							dwzResponse.setMessage("操作失败!");
						}
					}else{
						//挂失
						dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
						dwzResponse.setMessage("余额不足以取现");
					}
				}else if(cardStatus.equals("1")){
					//挂失
					dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
					dwzResponse.setMessage("该卡已挂失");
				}else if(cardStatus.equals("2")){
					//注销
					dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
					dwzResponse.setMessage("该卡已被注销");
				}else{
					//卡异常
					dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
					dwzResponse.setMessage("卡状态异常");
				}
			}else{
				dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
				dwzResponse.setMessage("卡状态异常");
			}
		}
		return dwzResponse;
	}
	
	/**
	 * 充值
	 * @param bizNo 商户备案号
	 * @param balance 商户余额
	 * @return
	 */
	@RequestMapping(value="/doMerchantRecharge")
	@ResponseBody
	@OperationLog(value="商户充值操作")
	public DwzResponse doMerchantRecharge(String cardNo,String balance){
		DwzResponse dwzResponse=new DwzResponse();
		//根据备案号查询信息
		MCard mCard=mcardService.getCardInfoByCardNo(cardNo);
		if(mCard==null){
			//为空则没查到信息
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setMessage("未找到有关卡的信息");
		}else{
			String cardStatus=mCard.getCardStatus();
			if(StringUtils.isNotEmpty(cardStatus)){
				//正常卡
				if(cardStatus.equals("0")){
					Merchant merchant=mCard.getMerchant();
					//卡本身原价
					Double originalPrice=merchant.getBalance();
					//现价
					Double balancePrice=originalPrice+Double.parseDouble(balance);
					merchant.setBalance(balancePrice);
					int num=merchantsService.merchantRecharge(merchant);
					if(num>0){
						//资金走向
						FundsRecord fs=new FundsRecord();
						Timestamp currentTime = new Timestamp(new Date().getTime());
						fs.setChangeTime(currentTime);
						fs.setType("001");
						fs.setUserName(merchant.getBizName());
						fs.setUserNo(merchant.getBizNo());
						fs.setAmount(Double.parseDouble(balance));
						fs.setBalance(String.valueOf(balancePrice));
						num=recordService.addFundsRecord(fs);
						if(num>0){
							dwzResponse.setStatusCode(DwzResponse.SC_OK);
							dwzResponse.setMessage("充值成功!");
							dwzResponse.setCallbackType(DwzResponse.CT_FORWARD);
							dwzResponse.setForwardUrl("merchant/toMerchantRecharge.do?balance="+balancePrice+"");
						}
					}else{
						dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
						dwzResponse.setMessage("操作失败!");
					}
				}else if(cardStatus.equals("1")){
					//挂失
					dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
					dwzResponse.setMessage("该卡已挂失");
				}else if(cardStatus.equals("2")){
					//注销
					dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
					dwzResponse.setMessage("该卡已被注销");
				}else{
					//卡异常
					dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
					dwzResponse.setMessage("卡状态异常");
				}
			}else{
				dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
				dwzResponse.setMessage("卡状态异常");
			}
		}
		return dwzResponse;
	}
	
	/**
	 * 根据卡号查询商户信息
	 * @param cardNo 卡号
	 * @return
	 */
	@RequestMapping(value="/getMerchantByCardNo")
	public void getMerchantByCardNo(String cardNo,HttpServletResponse response){
		Map<String,String> map=new HashMap<String, String>();
		MCard mCard=mcardService.getCardInfoByCardNo(cardNo);
		Merchant merchant=null;
		if(mCard!=null){
			merchant=mCard.getMerchant();
			map.put("status","200");
			map.put("cardStatus",mCard.getCardStatus());
			map.put("bizName",merchant.getBizName());
			map.put("identityCard",merchant.getIdentityCard());
			map.put("currentBalance",merchant.getBalance().toString());
		}else{
			//异常
			map.put("status","500");
		}
		PrintWriterUtil.writeObject(response, map);
	}
}
