package org.dppc.mtrace.app.ews;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.dppc.mtrace.app.approach.util.PrintWriterUtil;
import org.dppc.mtrace.app.ews.entity.BalanceEntity;
import org.dppc.mtrace.app.ews.service.BalanceService;
import org.dppc.mtrace.app.ews.service.EwsService;
import org.dppc.mtrace.app.merchant.entity.Merchant;
import org.dppc.mtrace.frame.annotation.OperationLog;
import org.dppc.mtrace.frame.base.DwzResponse;
import org.dppc.mtrace.frame.base.OrderCondition;
import org.dppc.mtrace.frame.base.Page;
import org.dppc.mtrace.frame.kit.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/balance")
public class BalanceController {
	@Autowired
	private BalanceService balanceService;
	
	@Autowired
	private EwsService ewsService;
	/**
	 * 小秤及小秤的绑定信息展示
	 * @param request
	 * @param response
	 * @param balance
	 * @return
	 * @throws ParseException
	 * @author weiyuzhen
	 */
	@RequestMapping(value="/listBalance.do")
	public String listBalance(HttpServletRequest request,HttpServletResponse response,BalanceEntity balance) throws ParseException{
		
		//检索--时间范围
		String startdate = request.getParameter("statrdate");
		String enddate = request.getParameter("enddate");
		
		Page<BalanceEntity> page=new Page<BalanceEntity>();
		String pageIndex=request.getParameter("pageNum");
		if(StringUtils.isEmpty(pageIndex)){
			page.setPageIndex(1);
		}else{
			page.setPageIndex(Integer.parseInt(pageIndex));
		}
		String pageSize=request.getParameter("numPerPage");
		if(StringUtils.isEmpty(pageSize)){
			page.setPageSize(20);
		}else{
			page.setPageSize(Integer.parseInt(pageSize));
		}
		String filed=request.getParameter("orderField");
		String direction=request.getParameter("orderDirection");
		if(StringUtils.isEmpty(direction)){
			direction="asc";
		}
		OrderCondition order=new OrderCondition(filed,direction);
		
	    if(StringUtils.isNotEmpty(startdate) && StringUtils.isNotEmpty(enddate)){
	    	if(!DateUtil.compareTwoDate(startdate,enddate,DateUtil.DATETIMEFORMAR)){
	    		DwzResponse dwzResponse=new DwzResponse();
	    		dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
	    		dwzResponse.setMessage("开始时间不能大于结束时间!");
	    		dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
	    		PrintWriterUtil.writeObject(response, dwzResponse);
	    	}
	    }
	    request.setAttribute("enddate", enddate);
		request.setAttribute("order",order);
		request.setAttribute("startdate", startdate);
		balanceService.listBalance(page, order, balance, startdate, enddate, request);
		request.setAttribute("balance", balance);
		request.setAttribute("page",page);
		return "ews/balanceList";
		
	}
	
	/**
	 * 跳转到增加电子秤页
	 * @return
	 * @author weiyuzhen
	 */
	@RequestMapping(value="/toBalanceAdd.do")
	public String toBalanceAdd(){
		return "ews/balanceAdd";
		
	}
	
	/**
	 * 新增小秤管理
	 * @author weiyuzhen
	 * @return
	 */
	@RequestMapping(value="/doAddBalance.do")
	@OperationLog("小秤添加操作")
	@ResponseBody
	public DwzResponse doAddBalance(HttpServletRequest request,HttpServletResponse response,BalanceEntity balance){
		DwzResponse dwzResponse = new DwzResponse();
		//先判断是否有重复小秤编号--再进行添加
		BalanceEntity balan = balanceService.findBalanceByBalanceNo(balance);
		if(balan != null ){
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("小秤编号重复!");
		}else{
			int flag = balanceService.balanceAdd(balance);
			if(flag>0){
				dwzResponse.setStatusCode(DwzResponse.SC_OK);
				dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
				dwzResponse.setMessage("操作成功!");
			}else{
				dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
				dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
				dwzResponse.setMessage("操作失败!");
			}
		}
		
		return dwzResponse;		
	}
	
	/**
	 * 跳转到小秤管理的修改页
	 * @param request
	 * @param response
	 * @param balance
	 * @return
	 * @throws ParseException
	 * @author weiyuzhen
	 */
	@RequestMapping("/toUpdateBalance")
	public String toUpdateFec(HttpServletRequest request,HttpServletResponse response,BalanceEntity balance) throws ParseException{
		BalanceEntity balanceEntity = balanceService.findBalanceById(balance);
		
		Date dt = balance.getBoundTime();
		String date = null;
		if(dt != null){
			date = dt.toString();
			//转换时间格式
			date = date.substring(0,date.length()-2);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			//sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));  
			balance.setBoundTime((Timestamp) sdf.parse(date));
		}
		
		request.setAttribute("boundTime",date);
		request.setAttribute("balance", balanceEntity);
		
		return "ews/balanceEdit";
	}
	
	/**
	 * 修改小秤
	 * @param request
	 * @param response
	 * @param balance
	 * @throws IOException
	 * @throws ParseException
	 * @author weiyuzhen
	 */
	@RequestMapping("/doUpdateBlance")
	@OperationLog("小秤修改操作")
	public void doUpdateFec(HttpServletRequest request,HttpServletResponse response,BalanceEntity balance) throws IOException, ParseException {
		DwzResponse dwzResponse=new DwzResponse();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));  
		
		//添加绑定时间
		String date = request.getParameter("date");
		if(balance.equals("")||date==null||date==""){
			//fec.setBindTime(dt);
		}else{
			  Timestamp ts = new Timestamp(sdf.parse(date).getTime());
			balance.setBoundTime(ts);
		}
		
		//先判断是否有重复IP--再进行修改
		BalanceEntity balanceEntity =  balanceService.findBalanceByBalanceNo(balance);
		if(balanceEntity != null){
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("小秤编号重复!");
		}else{
			if(balanceService.balanceUpdate(balance)){
				dwzResponse.setStatusCode(DwzResponse.SC_OK);
				dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
				dwzResponse.setMessage("操作成功!");
			}else{
				dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
				dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
				dwzResponse.setMessage("操作失败!");
			}
		}
		
		PrintWriterUtil.writeObject(response, dwzResponse);
		
	}
	
	/**
	 * 删除小秤的操作
	 * @param request
	 * @param response
	 * @param ews
	 * @throws IOException
	 */
	@RequestMapping("/deleteBalance")
	@OperationLog("小秤删除操作")
	public void delete(HttpServletRequest request,HttpServletResponse response,BalanceEntity balance) throws IOException {
		DwzResponse dwzResponse=new DwzResponse();
		if(balanceService.deleteBalance(balance)){
			dwzResponse.setStatusCode(DwzResponse.SC_OK);
			dwzResponse.setCallbackType(DwzResponse.CT_FORWARD);
			dwzResponse.setMessage("操作成功!");
		}else{
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("操作失败!");
		}
		PrintWriterUtil.writeObject(response, dwzResponse);
	}
	
	/**
	 * 查询商户信息
	 * @param request
	 * @param response
	 * @param balance
	 * @return
	 * @throws IOException
	 * @author weiyuzhen
	 */
	@RequestMapping("/toBindMerchant")
	public String toBindMerchant(HttpServletRequest request,HttpServletResponse response,BalanceEntity balance) throws IOException {
		//查询所有的商户
		List<Merchant> merall = ewsService.selectMerchantAll();
		
		//根据小秤的id查询当前对象
		BalanceEntity BalanceEntity = balanceService.findBalanceById(balance);
		
		//分配
		//Set<FeComputerEntity> fecyet = ewsService.selectById(ews).getFes();
		request.setAttribute("balanceBackId", balance.getBaId());
		request.setAttribute("balanceBack", BalanceEntity);
		request.setAttribute("merall",merall);
		return "ews/balanceBindMerchant";
	}
	
	@RequestMapping("/doBindMerchant")
	@OperationLog("小秤绑定商户操作")
	public void doBindMerchant(HttpServletRequest request,HttpServletResponse response,BalanceEntity balance) throws IOException {
		DwzResponse dwzResponse=new DwzResponse();
		//所操作的小秤的id
		String balanceBackId = request.getParameter("balanceBackId");
		//带回要绑定的商户编码
		String bizNumber = request.getParameter("bizNumber");
		
		if(bizNumber == null){
			dwzResponse.setStatusCode(DwzResponse.SC_OK);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("操作成功!");
		}else{
			//根据商户编码查询当前对象
			Merchant merEntity = ewsService.selectByBizId(bizNumber);
			balance.setBaId(Integer.parseInt(balanceBackId));
			BalanceEntity balanceEntity = balanceService.findBalanceById(balance);
			//获取当前绑定时间
			Timestamp dt = new Timestamp(System.currentTimeMillis()); 
			balanceEntity.setBoundTime(dt);
			balanceEntity.setBizId(merEntity.getBizNo());
			balanceEntity.setBizName(merEntity.getBizName());
			balanceEntity.setBoothNo(merEntity.getBoothNo());
			balanceEntity.setBusinessType(merEntity.getBusinessType());
			
			if(balanceService.balanceUpdate(balanceEntity)){
				dwzResponse.setStatusCode(DwzResponse.SC_OK);
				dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
				dwzResponse.setMessage("操作成功!");
			}else{
				dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
				dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
				dwzResponse.setMessage("操作失败!");
			}
		}
		
		PrintWriterUtil.writeObject(response, dwzResponse);

		
	}
	
	/**
	 * 解除商户绑定
	 * @param request
	 * @param response
	 * @param balance
	 * @author weiyuzhen
	 */
	@RequestMapping("/deleteBindMerchant.do")
	public void deleteBindMerchant(HttpServletRequest request,HttpServletResponse response,BalanceEntity balance){
		DwzResponse dwzResponse=new DwzResponse();
		//根据小秤的id查询当前对象
		BalanceEntity balanceEntity = balanceService.findBalanceById(balance);
		balanceEntity.setBizId("");
		balanceEntity.setBizName("");
		balanceEntity.setBoothNo("");
		balanceEntity.setBusinessType("");
		balanceEntity.setBoundTime(null);
		if(balanceService.balanceUpdate(balanceEntity)){
			dwzResponse.setStatusCode(DwzResponse.SC_OK);
			dwzResponse.setCallbackType(DwzResponse.CT_FORWARD);
			dwzResponse.setMessage("操作成功!");
		}else{
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("操作失败!");
		}
		
		PrintWriterUtil.writeObject(response, dwzResponse);
	}
	
}
