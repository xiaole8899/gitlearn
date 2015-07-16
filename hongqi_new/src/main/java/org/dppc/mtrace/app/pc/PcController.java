package org.dppc.mtrace.app.pc;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.dppc.mtrace.app.approach.util.PrintWriterUtil;
import org.dppc.mtrace.app.pc.entity.PcEntity;
import org.dppc.mtrace.app.pc.service.PcService;
import org.dppc.mtrace.frame.annotation.OperationLog;
import org.dppc.mtrace.frame.base.DwzResponse;
import org.dppc.mtrace.frame.base.OrderCondition;
import org.dppc.mtrace.frame.base.Page;
import org.dppc.mtrace.frame.kit.DateUtil;
import org.dppc.mtrace.frame.kit.JsonKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * PC机管理大模块
 * @author dx
 *
 */
@Controller
@RequestMapping(value="/pc")
public class PcController {
	@Autowired
	private PcService pcService;
	/**
	 * PC机信息展示
	 * @param request
	 * @param response
	 * @param balance
	 * @return
	 * @throws ParseException
	 * @author weiyuzhen
	 */
	@RequestMapping(value="/listPc")
	@OperationLog("PC机展示操作")
	public String toListEws(PcEntity pc,HttpServletRequest request,HttpServletResponse response) throws ParseException{
		//检索--时间范围
		String startdate = request.getParameter("statrdate");
		String enddate = request.getParameter("enddate");
		
		Page<PcEntity> page=new Page<PcEntity>();
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
		pcService.selectPcList(page,order,pc,startdate,enddate);
		request.setAttribute("pc", pc);
		request.setAttribute("page",page);
		return "pc/pcList";
	}
	
	/**
	 * PC机添加跳转
	 * 
	 * @author dx
	 */
	@RequestMapping("/toAddPc")
	public String toAddPc(){
		return "pc/addPc";
	}
	
	/**
	 * PC机添加
	 * 
	 * @author dx
	 * @throws IOException 
	 */
	@RequestMapping("/doAddPc")
	@OperationLog("PC机添加操作")
	public void doAddPc(HttpServletRequest request,HttpServletResponse response,PcEntity pc) throws ParseException, IOException{
		DwzResponse dwzResponse = new DwzResponse();
		PrintWriter pw=response.getWriter();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));  
        Date dt = new Date();
		
		//添加绑定时间
		String date = request.getParameter("date");
		if(pc.equals("")||date==null||date==""){
			pc.setInDate(dt);
		}else{
			pc.setInDate(sdf.parse(date));
		}
		
		//先判断SN号和IP地址是否重复--再进行添加
		int flagMac = pcService.pcPanDuanMac(pc);
		int flagIp = pcService.pcPanDuanIp(pc);
		if(flagMac == 1){
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("MAC号重复!");
		}else if(flagIp == 1){
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("IP号重复!");
		}else{
			int flag = pcService.pcAdd(pc);
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
	 * PC机修改跳转
	 * @return
	 * @author dx
	 * @throws ParseException 
	 */
	@RequestMapping("/toUpdatePc")
	public String toUpdateEws(HttpServletRequest request,HttpServletResponse response,PcEntity pc) throws ParseException{
		PcEntity pcEntity = pcService.toUpdatePc(pc);
		Date inDate = pcEntity.getInDate();
		String date = null;
		System.out.println(inDate);
		if(inDate != null){
			date = pcEntity.getInDate().toString();
			//转换时间格式
			date = date.substring(0,date.length()-2);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			//sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));  
			pcEntity.setInDate(sdf.parse(date));
		}
		
		request.setAttribute("date",date);
		request.setAttribute("pc", pcEntity);
		
		return "pc/editPc";
	}
	
   /**
	 * PC机修改
	 * 
	 * @author dx
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping("/doUpdatePc")
	@OperationLog("PC机修改操作")
	public void doUpdatePc(HttpServletRequest request,HttpServletResponse response,PcEntity pc) throws IOException, ParseException {
		DwzResponse dwzResponse=new DwzResponse();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));  
        Date dt = new Date();
		
		//添加绑定时间
		String date = request.getParameter("date");
		if(pc.equals("")||date==null||date==""){
			pc.setInDate(dt);
		}else{
			pc.setInDate(sdf.parse(date));
		}
		
		//先判断SN号和IP地址是否重复--再进行修改
		int flagMac = pcService.pcEditPanDuanMac(pc);
		int flagIp = pcService.pcEditPanDuanIp(pc);
		if(flagMac == 1){
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("MAC号重复!");
		}else if(flagIp == 1){
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("IP号重复!");
		}else{
			if(pcService.pcUpdate(pc)){
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
	 * 电子秤删除
	 * 
	 * @author dx
	 * @throws IOException 
	 * 
	 */
	@RequestMapping("/deletePc")
	@OperationLog("PC机删除操作")
	public void deletePc(HttpServletRequest request,HttpServletResponse response,PcEntity pc) throws IOException {
		DwzResponse dwzResponse=new DwzResponse();
		if(pcService.deletePc(pc)){
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
	
}
