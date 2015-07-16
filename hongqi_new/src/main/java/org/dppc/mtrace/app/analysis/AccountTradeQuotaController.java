package org.dppc.mtrace.app.analysis;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.dppc.mtrace.app.analysis.entity.AccountTrade;
import org.dppc.mtrace.app.analysis.service.AccountTradeQuotaService;
import org.dppc.mtrace.app.approach.util.PrintWriterUtil;
import org.dppc.mtrace.app.merchant.entity.Merchant;
import org.dppc.mtrace.app.trade.entity.MarketTranInfoBase;
import org.dppc.mtrace.app.trade.entity.MarketTranInfoDetail;
import org.dppc.mtrace.frame.base.DwzResponse;
import org.dppc.mtrace.frame.kit.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/accountTrade")
public class AccountTradeQuotaController {
	
	@Autowired
	AccountTradeQuotaService accountTradeQuotaService;
	@RequestMapping("/listQuota")
	public String listQuota(HttpServletRequest request,HttpServletResponse response,Merchant merchant){
		DecimalFormat    df   = new DecimalFormat("######0.00");   
	    List<MarketTranInfoBase> expence = accountTradeQuotaService.expenseInfos(merchant,request);
	    //统计支出的总的价格
	    int expenceLength = expence.size();
	    Double expenceTotal =0.00;
	    if(expenceLength>0){
	    	
	    	for(int i=0;i<expenceLength;i++){
	    	
	    		expenceTotal = expenceTotal + expence.get(i).getTotalPrice();
	    	}
	    }
	    
	    List<MarketTranInfoBase> income = accountTradeQuotaService.incomeInfos(merchant,request);
	    Double incomeTotal = 0.00;
	    int incomeLength = income.size();
	    if(incomeLength > 0){
	    	
	    	for(int i=0;i<incomeLength;i++){
	    		incomeTotal = incomeTotal + income.get(i).getTotalPrice();
	    	}
	    }
		//判断开始日期不能大于结束日期!
		if(StringUtils.isNotEmpty(request.getParameter("startDate")) && StringUtils.isNotEmpty(request.getParameter("endDate"))){
			try {
				if(!DateUtil.compareTwoDate(request.getParameter("startDate"),request.getParameter("endDate"),DateUtil.DATETIMEFORMAR)){
					DwzResponse dwzResponse=new DwzResponse();
					dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
					dwzResponse.setMessage("开始日期不能大于结束日期!");
					dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
					PrintWriterUtil.writeObject(response, dwzResponse);
					return "analysis/accountTradeQuota";
				}
				
				int dfm=DateUtil.compareTwoDateDiff(request.getParameter("startDate"),request.getParameter("endDate"),DateUtil.DATETIMEFORMAR);
				
				if(dfm>15){
					DwzResponse dwzResponse=new DwzResponse();
					dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
					dwzResponse.setMessage("开始日期与结束日期间隔天数超过15天!");
					dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
					PrintWriterUtil.writeObject(response, dwzResponse);
					return "analysis/accountTradeQuota";
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	 //   System.out.println("expence:"+df.format(expenceTotal)+"+++++++++++"+"income:"+df.format(incomeTotal));
	    request.setAttribute("startDate", request.getParameter("startDate"));
	    request.setAttribute("endDate", request.getParameter("endDate"));
	    request.setAttribute("expenceTotal", df.format(expenceTotal));
	    request.setAttribute("incomeTotal", df.format(incomeTotal));
	    request.setAttribute("merchant", merchant);
	    request.setAttribute("expence", createExpenceData(expence));
	    request.setAttribute("income", createIncomeData(income));
		return "analysis/accountTradeQuota";
		
	}
	
	/**
	 * 创建收入展示数据
	 * @param income
	 * @return
	 */
	private Object createIncomeData(List<MarketTranInfoBase> income) {
		List<AccountTrade> accountTrade = new ArrayList<AccountTrade>();
		for(int i=0;i<income.size();i++){
			
			List<MarketTranInfoDetail> marketDetail = income.get(i).getMarketTranInfoDetailItems();
			for(int j=0;j<marketDetail.size();j++){
	           AccountTrade accD = new AccountTrade();
	           accD.setBizName(income.get(i).getRetailerName());
	           accD.setDealTime(income.get(i).getTranDate());
	           accD.setGoodsName(marketDetail.get(j).getGoodsName());
	           accD.setPrice(marketDetail.get(j).getPrice());
	           accD.setTranId(marketDetail.get(j).getTraceId());
	           accD.setWeight(marketDetail.get(j).getWeight());
	           accountTrade.add(accD);
			}
		}
		return accountTrade;
	}

	/**
	 * 创建支出展示数据
	 * @param expence
	 * @return
	 */
	private List<AccountTrade> createExpenceData(List<MarketTranInfoBase> expence) {
		List<AccountTrade> accountTrade = new ArrayList<AccountTrade>();
		for(int i=0;i<expence.size();i++){
			
			List<MarketTranInfoDetail> marketDetail = expence.get(i).getMarketTranInfoDetailItems();
			for(int j=0;j<marketDetail.size();j++){
	           AccountTrade accD = new AccountTrade();
	           accD.setBizName(expence.get(i).getWholesalerName());
	           accD.setDealTime(expence.get(i).getTranDate());
	           accD.setGoodsName(marketDetail.get(j).getGoodsName());
	           accD.setPrice(marketDetail.get(j).getPrice());
	           accD.setTranId(marketDetail.get(j).getTraceId());
	           accD.setWeight(marketDetail.get(j).getWeight());
	           accountTrade.add(accD);
			}
		}
		return accountTrade;
	}
	
	@RequestMapping("/listMarketSell")
	public String listMarketSellMoney(HttpServletRequest request,HttpServletResponse response){
		List<?>  listMarket = accountTradeQuotaService.findMarketSell(request);
		List<Object> years = new ArrayList<Object>();
		List<Object> data = new ArrayList<Object>();
		for(int i=0;i<listMarket.size();i++){
			Object[] datas = (Object[]) listMarket.get(i);
			for(int j=0;j<datas.length;j++){
				if(j==0){
					years.add(datas[j]);
				}
				if(j==1){
					data.add(datas[j]);
				}
			}
			
		}
		String yearD="";
		String dataD="";
		//拼装前台显示的数据样式
		for(int i=0;i<years.size();i++){
			String[] days = years.get(i).toString().split("-");
			int month = Integer.parseInt(days[1]);
			int day = Integer.parseInt(days[2]);
			String monthDay = month +"-"+day;
			yearD +="'"+monthDay+"', ";
		}
		int widthjj = 60;
	    Double moneyTotal = new Double(0.0);
		for(int i=0;i<data.size();i++){
			dataD +=""+data.get(i)+", ";
			moneyTotal+= Double.valueOf(data.get(i).toString());
			widthjj = widthjj +60;
		}
		request.setAttribute("yearD", yearD);
		request.setAttribute("dataD", dataD);
		request.setAttribute("years", years);
		request.setAttribute("data", data);
		request.setAttribute("widthjj", widthjj);
		request.setAttribute("listSize", data.size());
		request.setAttribute("moneyTotal", moneyTotal);
		request.setAttribute("startDate", request.getParameter("startDate"));
		request.setAttribute("endDate", request.getParameter("endDate"));
	//	request.setAttribute("marketSell", listMarket);
		return "analysis/marketSellMoney";
		
	}
}
