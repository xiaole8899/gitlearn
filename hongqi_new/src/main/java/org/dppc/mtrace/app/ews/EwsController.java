package org.dppc.mtrace.app.ews;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.dppc.mtrace.app.AppConstant;
import org.dppc.mtrace.app.approach.util.PrintWriterUtil;
import org.dppc.mtrace.app.ews.entity.EwsEntity;
import org.dppc.mtrace.app.ews.entity.FeComputerEntity;
import org.dppc.mtrace.app.ews.entity.NoticeEntity;
import org.dppc.mtrace.app.ews.service.EwsService;
import org.dppc.mtrace.app.merchant.entity.Merchant;
import org.dppc.mtrace.app.merchant.service.MCardService;
import org.dppc.mtrace.app.merchant.service.MerchantsService;
import org.dppc.mtrace.frame.annotation.OperationLog;
import org.dppc.mtrace.frame.base.DwzResponse;
import org.dppc.mtrace.frame.base.OrderCondition;
import org.dppc.mtrace.frame.base.Page;
import org.dppc.mtrace.frame.kit.DateUtil;
import org.dppc.mtrace.frame.kit.JsonKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 电子秤管理大模块
 * @author dx
 *
 */
@Controller
@RequestMapping(value="/ews")
public class EwsController {

	@Autowired
	private MerchantsService merchantsService;
	
	@Autowired
	private MCardService mcardService;
	
	@Autowired
	private EwsService ewsService;
	
	/***********************************************start 电子秤 ************************************************/
	/**
	 * 电子秤管理列表展示
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/listEws")
	@OperationLog("电子秤展示操作")
	public String toListEws(EwsEntity ews,HttpServletRequest request,HttpServletResponse response) throws ParseException{
		//检索--时间范围
		String startdate = request.getParameter("statrdate");
		String enddate = request.getParameter("enddate");
		
		Page<EwsEntity> page=new Page<EwsEntity>();
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
		ewsService.selectEwsList(page,order,ews,startdate,enddate);
		request.setAttribute("ews", ews);
		request.setAttribute("page",page);
		return "ews/list";
	}
	
	
	/**
	 * 电子秤添加跳转
	 * 
	 * @author dx
	 */
	@RequestMapping("/toAddEws")
	public String toAddEws(){
		return "ews/addews";
	}
	
	/**
	 * 电子秤添加
	 * 
	 * @author dx
	 * @throws IOException 
	 */
	@RequestMapping("/doAddEws")
	@OperationLog("电子秤添加操作")
	public void doAddEws(HttpServletRequest request,HttpServletResponse response,EwsEntity ews) throws ParseException, IOException{
		DwzResponse dwzResponse = new DwzResponse();
		PrintWriter pw=response.getWriter();
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));  
        Date dt = new Date();
		
		//添加绑定时间
		String date = request.getParameter("date");
		if(ews.equals("")||date==null||date==""){
			ews.setBindTime(dt);
		}else{
			ews.setBindTime(sdf.parse(date));
		}*/
		
		//先判断SN号和IP地址是否重复--再进行添加
		int flagSn = ewsService.ewsPanDuanSn(ews);
		int flagIp = ewsService.ewsPanDuanIp(ews);
		if(flagSn == 1){
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("SN号重复!");
		}else if(flagIp == 1){
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("IP号重复!");
		}else{
			int flag = ewsService.ewsAdd(ews);
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
		
		//json 数据
		String strJson=JsonKit.toJSON(dwzResponse);
		pw.write(strJson);
		pw.flush();
		pw.close();
		
	}
	
	/**
	 * 电子秤修改跳转
	 * @return
	 * @author dx
	 * @throws ParseException 
	 */
	@RequestMapping("/toUpdateEws")
	public String toUpdateEws(HttpServletRequest request,HttpServletResponse response,EwsEntity ews) throws ParseException{
		EwsEntity ewsEntity = ewsService.toUpdateEws(ews);
		Date bindTime = ewsEntity.getBindTime();
		String date = null;
		System.out.println(bindTime);
		if(bindTime != null){
			date = ewsEntity.getBindTime().toString();
			//转换时间格式
			date = date.substring(0,date.length()-2);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			//sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));  
			ewsEntity.setBindTime(sdf.parse(date));
		}
		
		request.setAttribute("bindTime",date);
		request.setAttribute("ews", ewsEntity);
		
		return "ews/editews";
	}
	
	/**
	 * 电子秤修改
	 * 
	 * @author dx
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping("/doUpdateEws")
	@OperationLog("电子秤修改操作")
	public void doUpdateEws(HttpServletRequest request,HttpServletResponse response,EwsEntity ews) throws IOException, ParseException {
		DwzResponse dwzResponse=new DwzResponse();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));  
		
		//添加绑定时间
		String date = request.getParameter("date");
		if(ews.equals("")||date==null||date==""){
			//ews.setBindTime(dt);
		}else{
			ews.setBindTime(sdf.parse(date));
		}
		
		
		//先判断SN号和IP地址是否重复--再进行修改
		int flagSn = ewsService.ewsEditPanDuanSn(ews);
		int flagIp = ewsService.ewsEditPanDuanIp(ews);
		if(flagSn == 1){
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("SN号重复!");
		}else if(flagIp == 1){
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("IP号重复!");
		}else{
			if(ewsService.ewsUpdate(ews)){
				dwzResponse.setStatusCode(DwzResponse.SC_OK);
				dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
				dwzResponse.setMessage("操作成功!");
			}else{
				dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
				dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
				dwzResponse.setMessage("操作失败!");
			}
		}
		
				
				
		/*if(ewsService.ewsUpdate(ews)){
			dwzResponse.setStatusCode(DwzResponse.SC_OK);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("操作成功!");
		}else {
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("操作失败!");
		}*/
		PrintWriter pw=response.getWriter();
		String strJson=JsonKit.toJSON(dwzResponse);
		pw.write(strJson);
		pw.flush();
		pw.close();
	}
	
	/**
	 * 电子秤删除
	 * 
	 * @author dx
	 * @throws IOException 
	 * 
	 */
	@RequestMapping("/deleteEws")
	@OperationLog("电子秤删除操作")
	public void delete(HttpServletRequest request,HttpServletResponse response,EwsEntity ews) throws IOException {
		DwzResponse dwzResponse=new DwzResponse();
		if(ewsService.deleteEws(ews)){
			dwzResponse.setStatusCode(DwzResponse.SC_OK);
			dwzResponse.setCallbackType(DwzResponse.CT_FORWARD);
			dwzResponse.setMessage("操作成功!");
		}else{
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("操作失败!");
		}
		PrintWriter pw=response.getWriter();
		String strJson=JsonKit.toJSON(dwzResponse);
		pw.write(strJson);
		pw.flush();
		pw.close();
	}
	
	/***********************************************end   电子秤************************************************/
	
	
	
	/***********************************************start 前置机 ************************************************/
	/**
	 * 前置机管理列表展示
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/listFec")
	@OperationLog("前置机列表展示操作")
	public String toListFec(FeComputerEntity fec,HttpServletRequest request,HttpServletResponse response) throws ParseException{
		//检索--时间范围
		String startdate = request.getParameter("statrdate");
		String enddate = request.getParameter("enddate");
		
		Page<FeComputerEntity> page=new Page<FeComputerEntity>();
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
		ewsService.selectFecList(page,order,fec,startdate,enddate);
		request.setAttribute("fec", fec);
		request.setAttribute("page",page);
		return "ews/feclist";
	}
	
	/**
	 * 前置机添加跳转
	 * 
	 * @author dx
	 */
	@RequestMapping("/toAddFec")
	public String toAddFec(){
		return "ews/addfec";
	}
	
	/**
	 * 前置机添加
	 * 
	 * @author dx
	 * @throws ParseException 
	 * @throws IOException 
	 */
	@RequestMapping("/doAddFec")
	@OperationLog("前置机添加操作")
	@ResponseBody
	public DwzResponse doAddFec(HttpServletRequest request,HttpServletResponse response,FeComputerEntity fec) throws ParseException {
		DwzResponse dwzResponse = new DwzResponse();
		
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));  
        Date dt = new Date();
		
		//添加绑定时间
		String date = request.getParameter("date");
		if(fec.equals("")||date==null||date==""){
			fec.setBindTime(dt);
		}else{
			fec.setBindTime(sdf.parse(date));
		}*/
		
		//先判断是否有重复IP--再进行添加
		int flagIp = ewsService.fecPanDuanIp(fec);
		if(flagIp == 1){
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("IP号重复!");
		}else{
			int flag = ewsService.fecAdd(fec);
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
	 * 前置机修改跳转
	 * @return
	 * @author dx
	 * @throws ParseException 
	 */
	@RequestMapping("/toUpdateFec")
	public String toUpdateFec(HttpServletRequest request,HttpServletResponse response,FeComputerEntity fec) throws ParseException{
		FeComputerEntity fecEntity = ewsService.toUpdateFec(fec);
		
		Date dt = fecEntity.getBindTime();
		String date = null;
		if(dt != null){
			date = dt.toString();
			//转换时间格式
			date = date.substring(0,date.length()-2);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			//sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));  
			fecEntity.setBindTime(sdf.parse(date));
		}
		
		request.setAttribute("bindTime",date);
		request.setAttribute("fec", fecEntity);
		
		return "ews/editfec";
	}
	
	/**
	 * 前置机修改
	 * 
	 * @author dx
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping("/doUpdateFec")
	@OperationLog("前置机修改操作")
	public void doUpdateFec(HttpServletRequest request,HttpServletResponse response,FeComputerEntity fec) throws IOException, ParseException {
		DwzResponse dwzResponse=new DwzResponse();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));  
		
		//添加绑定时间
		String date = request.getParameter("date");
		if(fec.equals("")||date==null||date==""){
			//fec.setBindTime(dt);
		}else{
			fec.setBindTime(sdf.parse(date));
		}
		
		//先判断是否有重复IP--再进行修改
		int flagIp = ewsService.fecEditPanDuanIp(fec);
		if(flagIp == 1){
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("IP号重复!");
		}else{
			if(ewsService.fecUpdate(fec)){
				dwzResponse.setStatusCode(DwzResponse.SC_OK);
				dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
				dwzResponse.setMessage("操作成功!");
			}else{
				dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
				dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
				dwzResponse.setMessage("操作失败!");
			}
		}
		
		/*if(ewsService.fecUpdate(fec)){
			dwzResponse.setStatusCode(DwzResponse.SC_OK);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("操作成功!");
		}else {
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("操作失败!");
		}*/
		PrintWriter pw=response.getWriter();
		String strJson=JsonKit.toJSON(dwzResponse);
		pw.write(strJson);
		pw.flush();
		pw.close();
	}
	
	
	/**
	 * 前置机删除
	 * 
	 * @author dx
	 * @throws IOException 
	 * 
	 */
	@RequestMapping("/deleteFec")
	@OperationLog("前置机删除操作")
	public void deleteFec(HttpServletRequest request,HttpServletResponse response,FeComputerEntity fec) throws IOException {
		DwzResponse dwzResponse=new DwzResponse();
		if(ewsService.deleteFec(fec)){
			dwzResponse.setStatusCode(DwzResponse.SC_OK);
			dwzResponse.setCallbackType(DwzResponse.CT_FORWARD);
			dwzResponse.setMessage("操作成功!");
		}else{
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("操作失败!");
		}
		PrintWriter pw=response.getWriter();
		String strJson=JsonKit.toJSON(dwzResponse);
		pw.write(strJson);
		pw.flush();
		pw.close();
	}
	/***********************************************end   前置机************************************************/
	
	
	
	
	
	/***********************************************start 关联电子秤和前置机 ************************************************/
	/**
	 * 加载前置机
	 * 
	 * @author dx
	 * @throws IOException 
	 */
	@RequestMapping("/toBindFec")
	public String toBindFec(HttpServletRequest request,HttpServletResponse response,EwsEntity ews) throws IOException {
		List<FeComputerEntity> fecall = ewsService.selectFecAll();
		
		//分配
		Set<FeComputerEntity> fecyet = ewsService.selectById(ews).getFes();
		request.setAttribute("ewsId", ews.getEwsId());
		request.setAttribute("fecall",fecall);
		request.setAttribute("fecyet",fecyet);
		
		return "ews/bindFec";
	}
	
	/**
	 * 添加前置机
	 * 
	 * @author dx
	 * @throws IOException 
	 */
	@RequestMapping("/doBindFec")
	@OperationLog("电子秤关联前置机操作")
	public void doFecAdd(HttpServletRequest request,HttpServletResponse response,EwsEntity ews) throws IOException {
		DwzResponse dwzResponse=new DwzResponse();
		String[] fecids = request.getParameterValues("fecIds");
		if(ewsService.doFecAdd(ews,fecids)){
			dwzResponse.setStatusCode(DwzResponse.SC_OK);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("操作成功!");
		}else{
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("操作失败!");
		}
		PrintWriter pw=response.getWriter();
		String strJson=JsonKit.toJSON(dwzResponse);
		pw.write(strJson);
		pw.flush();
		pw.close();
		
	}
	
	/***********************************************end   关联电子秤和前置机************************************************/
	
	
	
	
	
	
	
	
	
	
	
	/***********************************************start 关联前置机和电子秤 ************************************************/
	/**
	 * 加载电子秤
	 * 
	 * @author dx
	 * @throws IOException 
	 */
	@RequestMapping("/toBindEws")
	public String toBindEws(HttpServletRequest request,HttpServletResponse response,FeComputerEntity fec) throws IOException {
		List<EwsEntity> ewsall = ewsService.selectEwsAll();
		
		//分配
		Set<EwsEntity> ewsyet = ewsService.selectByIdGetEws(fec).getEws();
		request.setAttribute("fecId", fec.getFecId());
		request.setAttribute("ewsall",ewsall);
		request.setAttribute("ewsyet",ewsyet);
		
		return "ews/bindEws";
	}
	
	/**
	 * 添加电子秤
	 * 
	 * @author dx
	 * @throws IOException 
	 */
	@RequestMapping("/doBindEws")
	@OperationLog("前置机关联电子秤操作")
	public void doBindEws(HttpServletRequest request,HttpServletResponse response,FeComputerEntity fec) throws IOException {
		DwzResponse dwzResponse=new DwzResponse();
		String[] ewsids = request.getParameterValues("ewsIds");
		
		//获取当前绑定时间
        Date dt = new Date();
		fec.setBindTime(dt);
		
		if(ewsService.doEwsFecAdd(fec,ewsids)){
			dwzResponse.setStatusCode(DwzResponse.SC_OK);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("操作成功!");
		}else{
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("操作失败!");
		}
		PrintWriter pw=response.getWriter();
		String strJson=JsonKit.toJSON(dwzResponse);
		pw.write(strJson);
		pw.flush();
		pw.close();
		
	}
	
	/***********************************************end   关联前置机和电子秤************************************************/
	
	
	
	
	
	
	
	
	
	
	/***********************************************start 电子秤绑定商户 ************************************************/
	/**
	 * 电子秤绑定商户列表展示
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/ewsBindList")
	public String ewsBindList(EwsEntity ews,HttpServletRequest request,HttpServletResponse response) throws ParseException{
		//检索--时间范围
		String startdate = request.getParameter("statrdate");
		String enddate = request.getParameter("enddate");
		
		Page<EwsEntity> page=new Page<EwsEntity>();
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
		ewsService.selectEwsList(page,order,ews,startdate,enddate);
		request.setAttribute("ews", ews);
		request.setAttribute("page",page);
		return "ews/ewsMerchantList";
	}
	
	/**
	 * 查询商户信息
	 * @author dx
	* @throws IOException 
	 */
	@RequestMapping("/toBindMerchant")
	public String toBindMerchant(HttpServletRequest request,HttpServletResponse response,EwsEntity ews) throws IOException {
		//查询所有的商户
		List<Merchant> merall = ewsService.selectMerchantAll();
		
		//根据电子秤的id查询当前对象
		EwsEntity ewsEntity = ewsService.selectById(ews);
		
		//分配
		//Set<FeComputerEntity> fecyet = ewsService.selectById(ews).getFes();
		request.setAttribute("ewsBackId", ews.getEwsId());
		request.setAttribute("ewsBack", ewsEntity);
		request.setAttribute("merall",merall);
		return "ews/bindMerchant";
	}
	
	
	/**
	 * 添加关联商户
	 * 
	 * @author dx
	 * @throws IOException 
	 */
	@RequestMapping("/doBindMerchant")
	@OperationLog("电子秤绑定商户操作")
	public void doBindMerchant(HttpServletRequest request,HttpServletResponse response,EwsEntity ews) throws IOException {
		DwzResponse dwzResponse=new DwzResponse();
		//所操作的电子秤的id
		String ewsId = request.getParameter("ewsBackId");
		//带回要绑定的商户编码
		String bizNumber = request.getParameter("bizNumber");
		
		if(bizNumber == null){
			dwzResponse.setStatusCode(DwzResponse.SC_OK);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("操作成功!");
		}else{
			//根据商户编码查询当前对象
			Merchant merEntity = ewsService.selectByBizId(bizNumber);
			//获取当前绑定时间
			Date dt = new Date();
			ews.setBindTime(dt);
			
			ews.setEwsIp(ewsId);
			ews.setSalerName(merEntity.getBizName());
			ews.setSalerNumber(merEntity.getBizNo());
			
			if(ewsService.doBindMerchant(ewsId,merEntity,ews)){
				dwzResponse.setStatusCode(DwzResponse.SC_OK);
				dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
				dwzResponse.setMessage("操作成功!");
			}else{
				dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
				dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
				dwzResponse.setMessage("操作失败!");
			}
		}
		
		PrintWriter pw=response.getWriter();
		String strJson=JsonKit.toJSON(dwzResponse);
		pw.write(strJson);
		pw.flush();
		pw.close();
		
	}
	
	
		
		/**
		 * 添加前置机
		 * 
		 * @author dx
		 * @throws IOException 
		 */
		/*@RequestMapping("/doBindFec")
		public void doFecAdd(HttpServletRequest request,HttpServletResponse response,EwsEntity ews) throws IOException {
			DwzResponse dwzResponse=new DwzResponse();
			String[] fecids = request.getParameterValues("fecIds");
			if(ewsService.doFecAdd(ews,fecids)){
				dwzResponse.setStatusCode(DwzResponse.SC_OK);
				dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
				dwzResponse.setMessage("操作成功!");
			}else{
				dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
				dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
				dwzResponse.setMessage("操作失败!");
			}
			PrintWriter pw=response.getWriter();
			String strJson=JsonKit.toJSON(dwzResponse);
			pw.write(strJson);
			pw.flush();
			pw.close();
			
		}*/
	
	
	
	/***************************************end 电子秤绑定商户*******************************************************/
	
	
	
	
	
	
	/***********************************************start 公告 ************************************************/
	/**
	 * 公告管理列表展示
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/listNotice")
	@OperationLog("公告列表展示操作")
	public String toListNotice(NoticeEntity nt,HttpServletRequest request,HttpServletResponse response) throws ParseException{
		//检索--时间范围
		String startdate = request.getParameter("statrdate");
		String enddate = request.getParameter("enddate");
		
		Page<NoticeEntity> page=new Page<NoticeEntity>();
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
		ewsService.selectNoticeList(page,order,nt,startdate,enddate);
		request.setAttribute("notice", nt);
		request.setAttribute("page",page);
		return "ews/noticelist";
	}
	
	
	/**
	 * 公告添加跳转
	 * 
	 * @author dx
	 */
	@RequestMapping("/toAddNotice")
	public String toAddNotice(){
		return "ews/addnotice";
	}
	
	/**
	 * 公告添加
	 * 
	 * @author dx
	 * @throws IOException 
	 */
	@RequestMapping("/doAddNotice")
	@OperationLog("公告添加操作")
	public void doAddNotice(HttpServletRequest request,HttpServletResponse response,NoticeEntity notice) throws ParseException, IOException{
		DwzResponse dwzResponse = new DwzResponse();
		PrintWriter pw=response.getWriter();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));  
        Date dt = new Date();
		
		//添加绑定时间
		String date = request.getParameter("date");
		if(notice.equals("")||date==null||date==""){
			notice.setCtime(dt);
		}else{
			notice.setCtime(sdf.parse(date));
		}
		
		//获取当前用户
		notice.setNoticeUser(AppConstant.getUserEntity(request).getUserName());
		
		int flag = ewsService.noticeAdd(notice);
		if(flag>0){
			dwzResponse.setStatusCode(DwzResponse.SC_OK);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("操作成功!");
		}else{
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("操作失败!");
		}
		
		//json 数据
		String strJson=JsonKit.toJSON(dwzResponse);
		pw.write(strJson);
		pw.flush();
		pw.close();
		
	}
	
	/**
	 * 公告修改跳转
	 * @return
	 * @author dx
	 * @throws ParseException 
	 */
	@RequestMapping("/toUpdateNotice")
	public String toUpdateNotice(HttpServletRequest request,HttpServletResponse response,NoticeEntity notice) throws ParseException{
		NoticeEntity noticeEntity = ewsService.toUpdateNotice(notice);
		String date = noticeEntity.getCtime().toString();
		//转换时间格式
		date = date.substring(0,date.length()-2);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        //sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));  
		noticeEntity.setCtime(sdf.parse(date));
		request.setAttribute("cTime",date);
		request.setAttribute("notice", noticeEntity);
		
		return "ews/editnotice";
	}
	
	/**
	 * 公告修改
	 * 
	 * @author dx
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping("/doUpdateNotice")
	@OperationLog("公告修改操作")
	public void doUpdateNotice(HttpServletRequest request,HttpServletResponse response,NoticeEntity notice) throws IOException, ParseException {
		DwzResponse dwzResponse=new DwzResponse();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));  
        Date dt = new Date();
		
		//添加绑定时间
		String date = request.getParameter("date");
		if(notice.equals("")||date==null||date==""){
			notice.setCtime(dt);
		}else{
			notice.setCtime(sdf.parse(date));
		}
		
		//获取当前用户
		notice.setNoticeUser(AppConstant.getUserEntity(request).getUserName());
		
		if(ewsService.noticeUpdate(notice)){
			dwzResponse.setStatusCode(DwzResponse.SC_OK);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("操作成功!");
		}else {
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("操作失败!");
		}
		PrintWriter pw=response.getWriter();
		String strJson=JsonKit.toJSON(dwzResponse);
		pw.write(strJson);
		pw.flush();
		pw.close();
	}
	
	/**
	 * 电子秤删除
	 * 
	 * @author dx
	 * @throws IOException 
	 * 
	 */
	@RequestMapping("/deleteNotice")
	@OperationLog("公告删除操作")
	public void deleteNotice(HttpServletRequest request,HttpServletResponse response,NoticeEntity notice) throws IOException {
		DwzResponse dwzResponse=new DwzResponse();
		if(ewsService.deleteNotice(notice)){
			dwzResponse.setStatusCode(DwzResponse.SC_OK);
			dwzResponse.setCallbackType(DwzResponse.CT_FORWARD);
			dwzResponse.setMessage("操作成功!");
		}else{
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("操作失败!");
		}
		PrintWriter pw=response.getWriter();
		String strJson=JsonKit.toJSON(dwzResponse);
		pw.write(strJson);
		pw.flush();
		pw.close();
	}
	
	/***********************************************end   公告管理************************************************/
	
}
