package org.dppc.mtrace.app.trade;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.dppc.mtrace.app.AppConstant;
import org.dppc.mtrace.app.approach.entity.ApproachEntity;
import org.dppc.mtrace.app.approach.util.PrintWriterUtil;
import org.dppc.mtrace.app.dict.entity.GoodsEntity;
import org.dppc.mtrace.app.merchant.entity.FundsRecord;
import org.dppc.mtrace.app.merchant.entity.Merchant;
import org.dppc.mtrace.app.merchant.service.FundsRecordService;
import org.dppc.mtrace.app.merchant.service.MerchantsService;
import org.dppc.mtrace.app.trade.entity.CostsSet;
import org.dppc.mtrace.app.trade.entity.MarketTranInfoBase;
import org.dppc.mtrace.app.trade.entity.MarketTranInfoDetail;
import org.dppc.mtrace.app.trade.service.TradeService;
import org.dppc.mtrace.frame.annotation.OperationLog;
import org.dppc.mtrace.frame.base.DwzResponse;
import org.dppc.mtrace.frame.base.OrderCondition;
import org.dppc.mtrace.frame.base.Page;
import org.dppc.mtrace.frame.kit.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/trade")
public class TradeController {
	@Autowired
	private TradeService tradeService;
	@Autowired
	private MerchantsService  merchantsService;
	@Autowired
	private FundsRecordService recordService;
	
	
	private DecimalFormat df = new DecimalFormat("0000000");

	/**
	 * 查看交易流水主表
	 * 
	 * @param marketTranInfoBase
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/listTradeBase")
	@OperationLog(value="查询主表交易操作")
	public String listTradeBase(MarketTranInfoBase marketTranInfoBase,
			HttpServletRequest request, HttpServletResponse response) throws ParseException {
		Page<MarketTranInfoBase> page = new Page<MarketTranInfoBase>();
		String pageIndex = request.getParameter("pageNum");
		if (StringUtils.isEmpty(pageIndex)) {
			page.setPageIndex(1);
		} else {
			page.setPageIndex(Integer.parseInt(pageIndex));
		}
		// 当前页格式
		String pageSize = request.getParameter("numPerPage");
		if (StringUtils.isEmpty(pageSize)) {
			page.setPageSize(20);
		} else {
			page.setPageSize(Integer.parseInt(pageSize));
		}
		// 排序条件
		String filed = request.getParameter("orderField");
		// 排序规则（升序或降序）
		String direction = request.getParameter("orderDirection");
		if (StringUtils.isEmpty(direction)) {
			direction = "asc";
		}
		// 排序
		OrderCondition order = new OrderCondition(filed, direction);
		 //开始时间
	    String startDate= request.getParameter("startDate");
	    request.setAttribute("startDate",startDate);
	    //结束时间
	    String endDate=request.getParameter("endDate");
	    if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)){
	    	if(!DateUtil.compareTwoDate(startDate,endDate,DateUtil.DATETIMEFORMAR)){
	    		DwzResponse dwzResponse=new DwzResponse();
	    		dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
	    		dwzResponse.setMessage("开始时间不能大于结束时间!");
	    		dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
	    		PrintWriterUtil.writeObject(response, dwzResponse);
	    	}
	    }
	    request.setAttribute("endDate", endDate);
		request.setAttribute("order", order);
		tradeService.listMarketTranInfoBase(order, page, marketTranInfoBase ,startDate,endDate);
		request.setAttribute("marketTranInfoBase", marketTranInfoBase);
		request.setAttribute("page", page);
		return "trade/listTradeBase";

	}
     
	/**
	 * 单条交易流水明细表
	 * 
	 * @param marketTranInfoBase
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/listTradeDetail")
	public String listTradeDetail(HttpServletRequest request,
			HttpServletResponse response,
			MarketTranInfoDetail marketTranInfoDetail) {
		Page<MarketTranInfoDetail> page = new Page<MarketTranInfoDetail>();
		String hdrId = request.getParameter("hdrId");
		String pageIndex = request.getParameter("pageNum");
		if (StringUtils.isEmpty(pageIndex)) {
			page.setPageIndex(1);
		} else {
			page.setPageIndex(Integer.parseInt(pageIndex));
		}
		// 当前页格式
		String pageSize = request.getParameter("numPerPage");
		if (StringUtils.isEmpty(pageSize)) {
			page.setPageSize(20);
		} else {
			page.setPageSize(Integer.parseInt(pageSize));
		}
		// 排序条件
		String filed = request.getParameter("orderField");
		// 排序规则（升序或降序）
		String direction = request.getParameter("orderDirection");
		if (StringUtils.isEmpty(direction)) {
			direction = "asc";
		}
		// 排序
		OrderCondition order = new OrderCondition(filed, direction);
		request.setAttribute("order", order);
		tradeService.listMarketTranInfoDetail(hdrId, order, page,
				marketTranInfoDetail);
		request.setAttribute("marketTranInfoDetail", marketTranInfoDetail);
		request.setAttribute("page", page);
		request.setAttribute("hdrId", hdrId);
		return "trade/listTradeDetail";

	}

	/**
	 * 展示所有交易流水明细表
	 * 
	 * @param marketTranInfoBase
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/listAllTradeDetail")
	@OperationLog(value=("查询细表交易流水操作"))
	public String listAllTradeDetail(HttpServletRequest request,
			HttpServletResponse response,
			MarketTranInfoDetail marketTranInfoDetail) throws ParseException {
		Page<MarketTranInfoDetail> page = new Page<MarketTranInfoDetail>();
		String tranId=request.getParameter("tranId");
		String pageIndex = request.getParameter("pageNum");
		if (StringUtils.isEmpty(pageIndex)) {
			page.setPageIndex(1);
		} else {
			page.setPageIndex(Integer.parseInt(pageIndex));
		}
		// 当前页格式
		String pageSize = request.getParameter("numPerPage");
		if (StringUtils.isEmpty(pageSize)) {
			page.setPageSize(20);
		} else {
			page.setPageSize(Integer.parseInt(pageSize));
		}
		// 排序条件
		String filed = request.getParameter("orderField");
		// 排序规则（升序或降序）
		String direction = request.getParameter("orderDirection");
		if (StringUtils.isEmpty(direction)) {
			direction = "asc";
		}
		// 排序
		OrderCondition order = new OrderCondition(filed, direction);
		 //开始时间
	    String startDate= request.getParameter("startDate");
	    request.setAttribute("startDate",startDate);
	    //结束时间
	    String endDate=request.getParameter("endDate");
	    if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)){
	    	if(!DateUtil.compareTwoDate(startDate,endDate,DateUtil.DATETIMEFORMAR)){
	    		DwzResponse dwzResponse=new DwzResponse();
	    		dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
	    		dwzResponse.setMessage("开始时间不能大于结束时间!");
	    		dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
	    		PrintWriterUtil.writeObject(response, dwzResponse);
	    	}
	    }
	    request.setAttribute("endDate", endDate);
		request.setAttribute("order", order);
		tradeService.listAllMarketTranInfoDetail(order, page,
				marketTranInfoDetail,startDate,endDate,tranId);
		request.setAttribute("marketTranInfoDetail", marketTranInfoDetail);
		request.setAttribute("page", page);
		return "trade/listAllTradeDetail";

	}

	/**
	 * 跳转人工添加现金交易流水
	 * 
	 * @return
	 */
	@RequestMapping("/toAddTrade")
	public String toAddTrade(HttpServletRequest request) {
		
		Map<String,String> buyerType=AppConstant.buyerType;
		request.setAttribute("buyerType", buyerType);
		return "trade/addTrade";
	}
	
	@RequestMapping("/toAddTradeAfterApproach")
	public String toAddTradeAfterApproach(HttpServletRequest request) throws UnsupportedEncodingException {
		
		Map<String,String> buyerType=AppConstant.buyerType;
		request.setAttribute("buyerType", buyerType);
		String wholesalerName = request.getParameter("wholesalerName");
		wholesalerName =new String(wholesalerName.getBytes("ISO-8859-1"),"utf-8");
		String wholesalerId = request.getParameter("wholesalerId");
		request.setAttribute("wholesalerName", wholesalerName);
		request.setAttribute("wholesalerId", wholesalerId);
		
		return "trade/addTradeAfterApproach";
	}
	/**
	 *加载卖方进场数据 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getWholesalerId")
	public void toAddTradeAfterApproach (HttpServletRequest request,HttpServletResponse response) {
		DwzResponse dwzResponse=new DwzResponse();
		dwzResponse.setStatusCode(DwzResponse.SC_OK);
		dwzResponse.setCallbackType(DwzResponse.CT_FORWARD);
		String wholesalerName = request.getParameter("bizLookup.bizName");
		String wholesalerId = request.getParameter("bizLookup.bizNo");
		dwzResponse.setForwardUrl("trade/toAddTradeAfterApproach.do?wholesalerId="+wholesalerId+"&wholesalerName="+wholesalerName);
		PrintWriterUtil.writeObject(response, dwzResponse);

	}
	
	
	
	/**
	 * 跳转人工添加刷卡交易流水
	 * 
	 * @return
	 */
	@RequestMapping("/toAddTradeByCard")
	public String toAddTradeByCard(HttpServletRequest request) {
		// 加载交易类型
		Map<String, String> tradeMap = AppConstant.tradeType;
		request.setAttribute("tradeMap", tradeMap);
		Map<String,String> buyerType=AppConstant.buyerType;
		request.setAttribute("buyerType", buyerType);
		return "trade/addTradeByCard";
	}
	
	@RequestMapping("/toAddTradeByCardAfterApp")
	public String toAddTradeByCardAfterApp (HttpServletRequest request) throws UnsupportedEncodingException {
		Map<String,String> buyerType=AppConstant.buyerType;
		request.setAttribute("buyerType", buyerType);
		String wholesalerName = request.getParameter("wholesalerName");
		wholesalerName =new String(wholesalerName.getBytes("ISO-8859-1"),"utf-8");
		String wholesalerId = request.getParameter("wholesalerId");
		String wholesalerCard = request.getParameter("wholesalerCard");
		request.setAttribute("wholesalerName", wholesalerName);
		request.setAttribute("wholesalerId", wholesalerId);
		request.setAttribute("wholesalerCard", wholesalerCard);
		return "trade/addTradeByCardAfterApproach";
	}
	/**
	 * 加载卖方的进场数据
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getByCardwholesalerId")
	public void toAddTradeByCardAfterApp (HttpServletRequest request,HttpServletResponse response) {
		DwzResponse dwzResponse = new DwzResponse();
		dwzResponse.setStatusCode(DwzResponse.SC_OK);
		dwzResponse.setCallbackType(DwzResponse.CT_FORWARD);
		String wholesalerName = request.getParameter("wholesalerName");
		String wholesalerId = request.getParameter("wholesalerId");
		String wholesalerCard = request.getParameter("sellerCardNo");
		dwzResponse.setForwardUrl("trade/toAddTradeByCardAfterApp.do?wholesalerId="+wholesalerId+"&wholesalerName="+wholesalerName+"&wholesalerCard="+wholesalerCard);
		PrintWriterUtil.writeObject(response, dwzResponse);
	}
	
	
	
	/**
	 * 查找带回卖方用户
	 * 
	 * @param request
	 * @param response
	 * @param merchant
	 * @return
	 */
	@RequestMapping("/findMerchant")
	public String findMerchant(HttpServletRequest request,
			HttpServletResponse response, Merchant merchant) {
		Page<Merchant> page = new Page<Merchant>();
		// 当前页
		String pageIndex = request.getParameter("pageNum");
		if (StringUtils.isEmpty(pageIndex)) {
			page.setPageIndex(1);
		} else {
			page.setPageIndex(Integer.parseInt(pageIndex));
		}
		// 当前页格式
		String pageSize = request.getParameter("numPerPage");
		if (StringUtils.isEmpty(pageSize)) {
			page.setPageSize(20);
		} else {
			page.setPageSize(Integer.parseInt(pageSize));
		}

		// 排序条件
		String filed = request.getParameter("orderField");
		if (StringUtils.isEmpty(filed)) {
			filed = "updateTime";
		}
		// 排序规则（升序或降序）
		String direction = request.getParameter("orderDirection");
		if (StringUtils.isEmpty(direction)) {
			direction = "desc";
		}
		// 排序
		OrderCondition order = new OrderCondition(filed, direction);
		request.setAttribute("order", order);
		merchantsService.getMerchatList(page, order, merchant);
		request.setAttribute("page", page);
		// 查询条件
		request.setAttribute("merchant", merchant);
		// 商户类型
		Map<String, String> businessTypeList = AppConstant.businessType;
		request.setAttribute("businessTypeList", businessTypeList);
		return "trade/lookUpMerchant";

	}
	
	
	/**
	 * 根据卖方显示其进货的商品
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/findGoods")
	public String findGoods(HttpServletRequest request,HttpServletResponse response,GoodsEntity goods){
		goods.setGoodsState( request.getParameter("approachStateAdd"));
		Page<GoodsEntity> page = new Page<GoodsEntity>();
		//当前页
		String pageIndex=request.getParameter("pageNum");
		String wholesalerId = request.getParameter("wholesalerId");
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
		String field = request.getParameter("orderField");
		
		//排序规则
		String direction = request.getParameter("orderDirection");
	
		//排序
		OrderCondition order = new OrderCondition(field,direction);
		
		//调用业务
		tradeService.listGoodsChild(page, order, goods,wholesalerId);
		
		request.setAttribute("approachState", request.getParameter("approachStateAdd"));
	    request.setAttribute("goods", goods);    //查询条件
		request.setAttribute("order", order);    //排序
		request.setAttribute("page", page);      //分页及数据展示
		
		return "trade/lookupGoodsNameBySeller";
		
	}
	
	/**
	 * 获取交易信息
	 * @param base 交易
	 * @param request 请求
	 * @param sellerId 卖方
	 * @param names    商品集合
	 * @param prices
	 * @param goodsCodes
	 * @param weights
	 * @return
	 */
	public MarketTranInfoBase getMarketTranInfoBase(MarketTranInfoBase base,
			HttpServletRequest request,String sellerId,String [] names,
			String [] prices,String [] goodsCodes,String [] weights){
		List<MarketTranInfoDetail> tranList = new ArrayList<MarketTranInfoDetail>();
		//获取交易时间
		String tranDate = request.getParameter("tranDate1");
		//获取带回的卖方名称
		if(StringUtils.isEmpty(base.getWholesalerName())){
			base.setWholesalerName(request.getParameter("bizLookup.bizName"));
		}
		//买方
		sellerId = request.getParameter("bizLookup.bizNo");
		String retailerName = request.getParameter("retailerName");
		String retailerId = request.getParameter("retailerId");
		double tradeMoneys=calcSum(weights, prices);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			base.setTranDate(sdf.parse(tranDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		base.setTotalPrice(tradeMoneys);
		base.setRetailerId(retailerId);
		base.setWholesalerId(sellerId);
		base.setRetailerName(retailerName);
		base.setWholesalerBtype(request.getParameter("bizLookup.bizType"));
		base.setSalerBusinessType(request.getParameter("bizLookup.businessType"));
		//遍历
		for (int i = 0; i < names.length; i++) {	
			MarketTranInfoDetail md = new MarketTranInfoDetail();
			md.setGoodsName(names[i]);
			md.setPrice(Double.parseDouble(prices[i]));
			md.setWeight(Double.parseDouble(weights[i]));
			String codes=goodsCodes[i];
			md.setGoodsCode(codes);
			String batchId = tradeService.selectBatchIdByGoodsCodeAndSellerId(codes, sellerId);
			if (batchId == null || batchId.equals("")) {
				batchId=tradeService.selectBatchIdFromApproach(codes, sellerId);
			}
			md.setBatchId(batchId);
			if(goodsCodes[i].startsWith("2111")){
				md.setGoodsType("0");
			}else if(goodsCodes[i].startsWith("012")){
				md.setGoodsType("1");
			}
			md.setMarketTranInfoBase(base);
			tranList.add(md);
		}
		base.setMarketTranInfoDetailItems(tranList);
		base.setTranId(getTimeNM());
		return base;
	}
	
	/**
	 * 判断商户编码是否存在存在则返回名称
	 * @param bizNo
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="/isExitsByBizNo")
	public void isExitsByBizNo(String bizNo,HttpServletResponse response) throws IOException{
		Merchant merchant=tradeService.isExitsBiz(bizNo);
		String bizName="";
		response.setContentType("text/html; charset=utf-8");
		PrintWriter pw=response.getWriter();
		if(merchant!=null){
			bizName=merchant.getBizName();
		}
		pw.write(bizName);
		pw.flush();
		pw.close();
	}

	/**
	 * 添加现金交易流水
	 * 
	 * @param marketTranInfoBase
	 * @param request
	 * @param response
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping("/doAddTrade")
	@OperationLog(value="添加交易流水操作")
	@Transactional
	public void doAddTrade(MarketTranInfoBase base, HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException, InvocationTargetException {
		//卖方编号
		String sellerId=request.getParameter("bizLookup.bizNo");
		
		//买方类型
		String buyerType=base.getBuyerType();
		//未注册商户不用验证商户是否存在,buyerType 为0未注册商户，
		DwzResponse dwzResponse=null;
		
		if(buyerType.equals("0")){
			
			dwzResponse=freeTrade(base, request, response, sellerId);
		
		}else{
			
			//已经注册的商户
			dwzResponse=actualTrade(base, request, response, sellerId);
		}
		
		PrintWriterUtil.writeObject(response, dwzResponse);
	}
	
	
	/**
	 * 添加刷卡交易流水
	 * 
	 * @param marketTranInfoBase
	 * @param request
	 * @param response
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping("/doAddTradeByCard")
	@OperationLog(value="添加刷卡交易流水操作")
	@Transactional
	public void doAddTradeByCard(MarketTranInfoBase base, HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException, InvocationTargetException {
		
		//卖方编号
		String sellerId=request.getParameter("wholesalerId");
		//买方类型
		String buyerType=base.getBuyerType();
		//未注册商户不用验证商户是否存在,buyerType 为0未注册商户，
		DwzResponse dwzResponse=null;
		if(buyerType.equals("0")){
			dwzResponse=freeTrade(base, request,response, sellerId);
		}else{//已经注册的商户
			dwzResponse=actualTrade(base, request, response, sellerId);
		}
		PrintWriterUtil.writeObject(response, dwzResponse);
	}
	
	/**
	 * 判断余额是否充足(返回true 说明是余额不足了)
	 * @param weights
	 * @param prices      
	 * @param retailerIds 卖方编号
	 * @return
	 */
	public boolean isBalanceEnough(String [] weights,String [] prices,String retailerIds){
		//计算总金额
		double totalPrice = 0 ;
		for (int i = 0;i < weights.length;i++) {
			totalPrice +=Double.parseDouble(prices[i])*Double.parseDouble(weights[i]);
		}
		Double banlance=tradeService.selectCurrentMoney(retailerIds);
		if(banlance!=null){
			if(totalPrice > banlance){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 计算总金额方法
	 * @param weights
	 * @param prices
	 * @return
	 */
	public double calcSum(String [] weights,String [] prices){
		//计算总金额
		double totalPrice = 0 ;
		for (int i = 0;i < weights.length;i++) {
			totalPrice +=Double.parseDouble(prices[i])*Double.parseDouble(weights[i]);
		}
		return totalPrice;
	}
	
	/**
	 * 获取资金变动对象
	 * @param name
	 * @param tradeMoney
	 * @param id
	 * @param ts
	 * @param tranId
	 * @param buyAfterTrade
	 * @return
	 */
	public FundsRecord getFundsRecord(String name,Double tradeMoney,
			String id,Timestamp ts,String tranId,Double buyAfterTrade){
		FundsRecord fundsRecord=new FundsRecord();
		fundsRecord.setUserName(name);
		fundsRecord.setAmount(tradeMoney);
		fundsRecord.setUserNo(id);
		fundsRecord.setChangeTime(ts);
		fundsRecord.setTranId(tranId);
		fundsRecord.setType("002");
		if(buyAfterTrade!=null){
			fundsRecord.setBalance(String.valueOf(buyAfterTrade));
		}
		return fundsRecord;
	}

	/**
	 * 已注册商户交易信息 
	 * @param base
	 * @param request
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private DwzResponse actualTrade(MarketTranInfoBase base, HttpServletRequest request,HttpServletResponse response,String sellerId) throws IllegalAccessException, InvocationTargetException {
		//买方商户名称
		String retailerName = request.getParameter("retailerName");
		
		//买方商户编码
		String retailerId = request.getParameter("retailerId");
		
		// 商品名称数组
		String[] names = request.getParameterValues("goodsLookup.goodsName");
		// 价格数组
		String[] prices = request.getParameterValues("prices");
		// 重量数组
		String[] weights = request.getParameterValues("weights");
		// 商品编码 数组
		String[] goodsCodes = request.getParameterValues("goodsLookup.goodsCode");
		DwzResponse dwzResponse = new DwzResponse();
		//卖方商户不存在提醒
		if(tradeService.isExitsBiz(sellerId)==null){
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("卖方编码不存在,请核对!");
			return dwzResponse;
		}
		
		//买方商户不存在提醒
		if(tradeService.isExitsBiz(base.getRetailerId())==null){
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("买方编码不存在,请核对!");
			return dwzResponse;
		}
		
		//买卖双方为同一个提示
		if(base.getRetailerId().equals(sellerId)){
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("买卖双方不能为同一个商户!");
			return dwzResponse;
		}
		//必须有明细情况下先判断是否有进货信息，再判断余额
		if(names!=null && names.length>0){
			
			for(int i=0;i<goodsCodes.length;i++){
				if(!tradeService.iswholesalerApproachInformation(sellerId, goodsCodes[i])){
					dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
					dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
					dwzResponse.setMessage("未查到卖方进货信息,商品名称:"+names[i]+"!");
					return dwzResponse;
				}
			}
			//若交易类型为现金则不判断余额(0:现金1:刷卡)
			if(base.getTradeType().equals("1")){
				if (!isBalanceEnough(weights, prices, retailerId)) {
					dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
					dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
					dwzResponse.setMessage("卡余额不足!");
					return dwzResponse;
				}
			}
			//保存交易
			int num = tradeService.addTrade(getMarketTranInfoBase(base, request, sellerId, names, prices, goodsCodes, weights));
			//更新明细表的追溯码
			if (num > 0) {
				List<MarketTranInfoDetail> mis=base.getMarketTranInfoDetailItems();
				for(MarketTranInfoDetail mi:mis){
					mi.setTraceId(sellerId + df.format(mi.getDtlId()).toString());
				}
			num=tradeService.updateTradeDetails(mis);
			//买方应付的总钱数(卖方应得的总钱数)
			double tradeMoney=calcSum(weights, prices);
			DecimalFormat doubleFormat = new DecimalFormat("0.00");
			Timestamp ts = null;
			//日期格式
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//获取交易时间
			String tranDate = request.getParameter("tranDate1");
			try {
				ts = new Timestamp(dateFormat.parse(tranDate).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			//如果是刷卡,则更新商户表卡里的余额;如果是现金则不更新
			if (base.getTradeType().equals("1")) {
				//更新买方卡里的余额
				double buyCurrentMoney = tradeService.selectCurrentMoney(retailerId);
				double buyAfterTrade = buyCurrentMoney - tradeMoney;
				tradeService.updateBalance(doubleFormat.format(buyAfterTrade), retailerId);
				//更新卖方卡里的余额
				double sellCurrentMoney = tradeService.selectCurrentMoney(sellerId);
				double sellAfterTrade = sellCurrentMoney + tradeMoney;
				tradeService.updateBalance(doubleFormat.format(sellAfterTrade), sellerId);
				//保存买方资金变动记录
				FundsRecord buyRecord =getFundsRecord(retailerName,-tradeMoney, retailerId, ts, base.getTranId(), buyAfterTrade);				
				recordService.addFundsRecord(buyRecord);
				//保存卖方资金变动记录
				FundsRecord sellRecord = getFundsRecord(base.getWholesalerName(), tradeMoney,sellerId, ts,base.getTranId(), sellAfterTrade);
				recordService.addFundsRecord(sellRecord);
			} else {
				//保存买方资金变动记录
				FundsRecord buyRecord =getFundsRecord(retailerName, -tradeMoney, retailerId, ts, base.getTranId(), null);				
				recordService.addFundsRecord(buyRecord);
				//保存卖方资金变动记录
				FundsRecord sellRecord = getFundsRecord(base.getWholesalerName(), tradeMoney,sellerId, ts,base.getTranId(), null);
				recordService.addFundsRecord(sellRecord);
			}
				//交易完后,买方保存进场信息
				ApproachEntity approach = tradeService.palceAndSupper(sellerId);
				String ifBuyNo = tradeService.ifSingleBuy(retailerId);
				for (int i = 0; i < names.length; i++) {
					ApproachEntity ap=new ApproachEntity();
					if(approach!=null){
						BeanUtils.copyProperties(ap, approach);
						ap.setApproachId(null);
					}
					ap.setWholesalerName(retailerName);
					ap.setWholesalerId(retailerId);
					ap.setGoodsName(names[i]);
					ap.setGoodsCode(goodsCodes[i]);
					Integer goodsType = null;
					if (goodsCodes[i].substring(0, 3).equals("211")) {
						goodsType =0;
					} else {
						goodsType = 1;
					}
					//0是肉 1是菜
					ap.setGoodsType(goodsType);
					ap.setPrice(Double.parseDouble(prices[i]));
					ap.setWeight(Double.parseDouble(weights[i]));
					ap.setVoucherType("1");
					ap.setInDate(ts);
					ap.setBatchId(base.getMarketTranInfoDetailItems().get(i).getTraceId());
					//如果买方是散户则不保存进场
					if (ifBuyNo != null && !ifBuyNo.equals("1004")) {
						tradeService.addApproachIn(ap);
					}
					String balanceNo = tradeService.ifBanding(retailerId);
					if (balanceNo !=null) {
						try {
							tradeService.writeTrade(ap,sellerId,balanceNo);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				//交易流水编号
				if (num > 0) {
					dwzResponse.setStatusCode(DwzResponse.SC_OK);
					dwzResponse.setCallbackType(DwzResponse.CT_FORWARD);
					dwzResponse.setForwardUrl("trade/toAddTrade.do");
					dwzResponse.setMessage("添加交易信息成功");
				} else {
					dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
					dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
					dwzResponse.setMessage("添加交易信息失败");
				}
			} else {
				dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
				dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
				dwzResponse.setMessage("添加交易信息失败");
			}
		}else{
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("请至少选择一个交易的商品明细!");
		}
		return dwzResponse;
	}

	/**
	 * 未注册商户及散户的交易信息
	 */
	private DwzResponse freeTrade(MarketTranInfoBase base, HttpServletRequest request,HttpServletResponse response,String sellerId) {
		DwzResponse dwzResponse = new DwzResponse();
		//未注册时无法验证买卖双方是否是同一个人、余额不用验证、但是需要验证卖方是否有进货信息
		
		// 商品名称数组
		String[] names = request.getParameterValues("goodsLookup.goodsName");
		// 价格数组
		String[] prices = request.getParameterValues("prices");
		// 重量数组
		String[] weights = request.getParameterValues("weights");
		// 商品编码 数组
		String[] goodsCodes = request.getParameterValues("goodsLookup.goodsCode");
		//获取交易时间
		String tranDate = request.getParameter("tranDate1");
		//买方
		String retailerName = request.getParameter("retailerName");
		String retailerId = request.getParameter("retailerId");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			base.setTranDate(sdf.parse(tranDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		double tradeMoney = 0.00  ;
		if(names!=null && names.length>0){
			//验证卖方是否存在进货信息
			for(int i=0;i<goodsCodes.length;i++){
				if(!tradeService.iswholesalerApproachInformation(sellerId, goodsCodes[i])){
					dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
					dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
					dwzResponse.setMessage("未查到卖方进货信息,商品名称:"+names[i]+"!");
					return dwzResponse;
				}
			}
			int num = tradeService.addTrade(getMarketTranInfoBase(base, request, sellerId, names, prices, goodsCodes, weights));
			//保存买方资金变动记录
			
			Timestamp ts = null;
			try {
				ts = new Timestamp(dateFormat.parse(tranDate).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			//保存买方资金变动记录
			FundsRecord buyRecord =getFundsRecord(retailerName, -tradeMoney, retailerId, ts, base.getTranId(), null);				
			recordService.addFundsRecord(buyRecord);
			//保存卖方资金变动记录
			FundsRecord sellRecord = getFundsRecord(base.getWholesalerName(), tradeMoney,sellerId, ts,base.getTranId(), null);
			recordService.addFundsRecord(sellRecord);
			if (num > 0) {
				List<MarketTranInfoDetail> mis=base.getMarketTranInfoDetailItems();
				for(MarketTranInfoDetail mi:mis){
					mi.setTraceId(sellerId + df.format(mi.getDtlId()).toString());
				}
				num=tradeService.updateTradeDetails(mis);
				
				//交易流水编号
				if (num > 0) {
					dwzResponse.setStatusCode(DwzResponse.SC_OK);
					dwzResponse.setCallbackType(DwzResponse.CT_FORWARD);
					dwzResponse.setForwardUrl("trade/toAddTrade.do");
					dwzResponse.setMessage("添加交易信息成功");
				} else {
					dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
					dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
					dwzResponse.setMessage("添加交易信息失败");
				}
			} else {
				dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
				dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
				dwzResponse.setMessage("添加交易信息失败");
			} 
		}else{
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("请至少选择一个交易的商品明细!");
		}
		return dwzResponse;
	}
	
	/**
	 * 进入交易清单的查询列表
	 * @return
	 * @author weiyuzhen
	 */
	@RequestMapping("/toTradeChecklist")
	public String listTradeChecklist(){
		
		return "trade/listTradeChecklistInit";
		
	}
	
	/**
	 * 查询出具体交易凭证号下的交易信息
	 * @param tranBase
	 * @return
	 * @author weiyuzhen
	 */
	@RequestMapping("/doTradeChecklist")
	public String doListTradeCheecklist(HttpServletRequest request,MarketTranInfoBase tranBase){
		if(tranBase.getTranId() !=null && tranBase.getTranId()!=""){
			List<MarketTranInfoBase> list = tradeService.queryMarketTranInfoBaseByTranid(tranBase.getTranId());
			//重新构建一下数据
			if(list.size()>0){
				List<MarketTranInfoDetail> tranDetil = list.get(0).getMarketTranInfoDetailItems();
				int tranDetilLength = tranDetil.size();
				Double baseTotal = 0.0;
				if(tranDetilLength>0){
					List<MarketTranInfoDetail> newTranDetil = machineTradeDetil(tranDetil); 
					//计算这个单据的总价
					baseTotal = countTradeBase(newTranDetil);
					tranBase.setTotalPrice(baseTotal);
					tranBase.setWholesalerName(list.get(0).getWholesalerName());
					tranBase.setRetailerName(list.get(0).getRetailerName());
					tranBase.setMarketTranInfoDetailItems(newTranDetil);
				}
				request.setAttribute("marketTranInfoBase", tranBase);
				return "trade/listTradeChecklist";
			}
		}
		return "trade/listTradeChecklistInit";
		
	}
	
	/**根据交易明细，计算交易的总计
	 * @param newTranDetil
	 * @return
	 * @author weiyuzhen
	 */
	private Double countTradeBase(List<MarketTranInfoDetail> newTranDetil) {
		int length = newTranDetil.size();
		Double total = 0.0;
		for(int i=0;i<length;i++){
			total = total + newTranDetil.get(i).getTotals() ;
		}
		return total;
	}

	/**
	 * 将明细中个各条总价计算出来
	 * @param tranDetil
	 * @return
	 */
	private List<MarketTranInfoDetail> machineTradeDetil(List<MarketTranInfoDetail> tranDetil) {
		int length = tranDetil.size();
		for(int i =0 ;i<length;i++){
			tranDetil.get(i).setTotals(tranDetil.get(i).getPrice()*tranDetil.get(i).getWeight());
		}
		return tranDetil;
	}
    
	@RequestMapping("/printTradeCheckList")
	public String printTradeCheckList(HttpServletRequest request,MarketTranInfoBase tranBase){
		if(tranBase.getTranId() !=null && tranBase.getTranId()!=""){
			List<MarketTranInfoBase> list = tradeService.queryMarketTranInfoBaseByTranid(tranBase.getTranId());
			//重新构建一下数据
			if(list.size()>0){
				List<MarketTranInfoDetail> tranDetil = list.get(0).getMarketTranInfoDetailItems();
				int tranDetilLength = tranDetil.size();
				Double baseTotal = 0.0;
				if(tranDetilLength>0){
					List<MarketTranInfoDetail> newTranDetil = machineTradeDetil(tranDetil); 
					//计算这个单据的总价
					baseTotal = countTradeBase(newTranDetil);
					tranBase.setTotalPrice(baseTotal);
					tranBase.setWholesalerName(list.get(0).getWholesalerName());
					tranBase.setRetailerName(list.get(0).getRetailerName());
					tranBase.setMarketTranInfoDetailItems(newTranDetil);
				}
			}
			request.setAttribute("marketTranInfoBase", tranBase);
		}
		return "trade/tradeChecklistPrint";
		
	}
	
	
	/**
	 * 跳转结算清单页面
	 */
	@RequestMapping("/toTradeSettlementList")
	public String toTradeSettlementList () {
		
		return "trade/tradeSettlementlistInit";
	}
	
	
	/**
	 * 查询出指定交易凭证号下的的结算清单
	 * 
	 */
	@RequestMapping("/doTradeSettlementList")
	public String doTradeSettlementList (HttpServletRequest request , MarketTranInfoBase base) {
		if (base.getTranId() != null && base.getTranId() != "") {
			List<MarketTranInfoBase> list = tradeService.querySettlementList(base.getTranId());
			if (list.size() > 0) {
				List<MarketTranInfoDetail> tranDetil = list.get(0).getMarketTranInfoDetailItems();
				int tranDetilLength = tranDetil.size();
				Double baseTotal = 0.0;
				Double totalBuyTransfer = 0.0;
				Double totalSellTransfer = 0.0;
				
				if(tranDetilLength>0){	
				List<MarketTranInfoDetail> newTranDetil = machineTradeDetil(tranDetil); 
				for (MarketTranInfoDetail infoDetail:newTranDetil) {
					List<CostsSet> costsList = tradeService.queryByGoodsCode(infoDetail.getGoodsCode());
					double buyTransfer = tradeService.getBuyTransfer(infoDetail.getTotals(), costsList);
					infoDetail.setRetailerTransfer(buyTransfer);
					totalBuyTransfer += buyTransfer;
					double sellTransfer = tradeService.getSellTransfer(infoDetail.getTotals(), costsList);
					infoDetail.setWholesalerTransfer(sellTransfer);
					totalSellTransfer += sellTransfer;
				}
				//计算这个单据的总价
				baseTotal = countTradeBase(newTranDetil);
				base.setTotalPrice(baseTotal);
				base.setRetailerName(list.get(0).getRetailerName());
				base.setWholesalerName(list.get(0).getWholesalerName());
				base.setTotalBuyTransfer(totalBuyTransfer);
				base.setTotalSellTransfer(totalSellTransfer);
				base.setMarketTranInfoDetailItems(newTranDetil);
				}
			}
			request.setAttribute("marketTranInfoBase", base);
			return "trade/tradeSettlementlist";
		} else {
			return "trade/tradeSettlementlistInit";
		}
		
	}
	
	
	/**
	 * 打印结算清单
	 * 
	 */
	@RequestMapping("/doPrintTradeSettlementList")
	public String doPrintTradeSettlementList (HttpServletRequest request , MarketTranInfoBase base) {
		if (base.getTranId() != null && base.getTranId() != "") {
			List<MarketTranInfoBase> list = tradeService.querySettlementList(base.getTranId());
			if (list.size() > 0) {
				List<MarketTranInfoDetail> tranDetil = list.get(0).getMarketTranInfoDetailItems();
				int tranDetilLength = tranDetil.size();
				Double baseTotal = 0.0;
				Double totalBuyTransfer = 0.0;
				Double totalSellTransfer = 0.0;
				
				if(tranDetilLength>0){	
				List<MarketTranInfoDetail> newTranDetil = machineTradeDetil(tranDetil); 
				for (MarketTranInfoDetail infoDetail:newTranDetil) {
					List<CostsSet> costsList = tradeService.queryByGoodsCode(infoDetail.getGoodsCode());
					double buyTransfer = tradeService.getBuyTransfer(infoDetail.getTotals(), costsList);
					infoDetail.setRetailerTransfer(buyTransfer);
					totalBuyTransfer += buyTransfer;
					double sellTransfer = tradeService.getSellTransfer(infoDetail.getTotals(), costsList);
					infoDetail.setWholesalerTransfer(sellTransfer);
					totalSellTransfer += sellTransfer;
				}
				//计算这个单据的总价
				baseTotal = countTradeBase(newTranDetil);
				base.setTotalPrice(baseTotal);
				base.setRetailerName(list.get(0).getRetailerName());
				base.setWholesalerName(list.get(0).getWholesalerName());
				base.setTotalBuyTransfer(totalBuyTransfer);
				base.setTotalSellTransfer(totalSellTransfer);
				base.setMarketTranInfoDetailItems(newTranDetil);
				}
			}
			request.setAttribute("marketTranInfoBase", base);
		} 
		return "trade/tradeSettlementprint";
		
	}
	/**
	 * 获取当前时间的纳秒数
	 * @return
	 */
	public static String getTimeNM(){
		Date date=new Date();
		long m=date.getTime()/1000; //获取秒
		System.out.println("m"+m);
		long wm=m*1000;//微妙
		long nm=wm*1000;//纳秒 
		System.out.println("nm"+nm);
		String str=String.valueOf(m)+""+String.valueOf(nm);
		System.out.println(str);
		return str.substring(0,20);
	}

	
	//............................安全追溯.....................................................
	
	/**
	 * 跳转结算安全追溯页面
	 */
	@RequestMapping("/totraceByTraceIdListInit")
	public String totraceByTraceIdListInit () {
		
		return "trade/traceByTraceIdListInit";
	}
	
	/**
	 * 俺去追溯
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/dotraceByTraceIdList")
	public String traceByTraceId (HttpServletRequest request,HttpServletResponse response) {
		String traceId = request.getParameter("traceId");
		MarketTranInfoBase tranInfoBase = tradeService.selectByTraceId(traceId);
		String batchId = tradeService.selectbatchIdByTraceId(traceId);
		ApproachEntity approach = tradeService.selectApproachByBatchId(batchId);
		request.setAttribute("tranInfoBase", tranInfoBase);
		request.setAttribute("approach", approach);
		request.setAttribute("traceId", traceId);
		return "trade/traceByTraceIdList";
	}
















}
