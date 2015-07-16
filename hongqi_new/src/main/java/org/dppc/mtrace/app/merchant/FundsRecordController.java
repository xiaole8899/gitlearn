package org.dppc.mtrace.app.merchant;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.dppc.mtrace.app.approach.util.PrintWriterUtil;
import org.dppc.mtrace.app.merchant.entity.FundsRecord;
import org.dppc.mtrace.app.merchant.service.FundsRecordService;
import org.dppc.mtrace.frame.base.DwzResponse;
import org.dppc.mtrace.frame.base.OrderCondition;
import org.dppc.mtrace.frame.base.Page;
import org.dppc.mtrace.frame.kit.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value="/fundsRecord")
public class FundsRecordController {
	@Autowired
	private FundsRecordService recordService;
	
	
	/**
	 * 状态为1,则不加载页面
	 * @param record
	 * @param status
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value="/tradeRecordList")
	public String listTradeBase(FundsRecord record,String status ,
		HttpServletRequest request, HttpServletResponse response) throws ParseException {
		
		if (StringUtils.isNotEmpty(status) && status.equals("1")) {
			Page<FundsRecord> page = new Page<FundsRecord>();
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
		    String startdate= request.getParameter("startdate");
		    request.setAttribute("startDate",startdate);
		    //结束时间
		    String enddate=request.getParameter("enddate");
		    if(StringUtils.isNotEmpty(startdate) && StringUtils.isNotEmpty(enddate)){
		    	if(!DateUtil.compareTwoDate(startdate,enddate,DateUtil.DATETIMEFORMAR)){
		    		DwzResponse dwzResponse=new DwzResponse();
		    		dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
		    		dwzResponse.setMessage("开始时间不能大于结束时间!");
		    		dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
		    		PrintWriterUtil.writeObject(response, dwzResponse);
		    	}
		    }
		    request.setAttribute("endDate", enddate);
			request.setAttribute("order", order);
			recordService.tradeRecordList(page, order, startdate, enddate, record);
			request.setAttribute("record", record);
			request.setAttribute("page", page);
		}
		return "merchant/tradeRecordList";
	}

	/**
	 * 
	 * @param status 状态不为1则为初始化不加载数据
	 * @param request 
	 * @param response
	 * @param startDate 开始日期
	 * @param endDate   结束日期
	 * @param bizName   持卡人
	 * @param identityCard 持卡人证件
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value="/rechargeList")
	public String rechargeList(String status,HttpServletRequest request,HttpServletResponse response,
			String startDate,String endDate,String bizName,String identityCard,String cardNo) throws ParseException{
		if(StringUtils.isNotEmpty(status) && status.equals("1")){
			Page<FundsRecord> page = new Page<FundsRecord>();
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
		    request.setAttribute("startDate",startDate);
		    //结束时间
		    String enddate=request.getParameter("endDate");
		    if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)){
		    	if(!DateUtil.compareTwoDate(startDate,enddate,DateUtil.DATETIMEFORMAR)){
		    		DwzResponse dwzResponse=new DwzResponse();
		    		dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
		    		dwzResponse.setMessage("开始时间不能大于结束时间!");
		    		dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
		    		PrintWriterUtil.writeObject(response, dwzResponse);
		    	}
		    }
		    request.setAttribute("endDate", enddate);
			request.setAttribute("order", order);
			recordService.rechargeList(page, order,startDate, enddate,bizName,identityCard,cardNo);
			request.setAttribute("record",null);
			request.setAttribute("page", page);
			request.setAttribute("bizName",bizName);
			request.setAttribute("cardNo",cardNo);
		}
		return "merchant/rechargesearch";
	}
}
