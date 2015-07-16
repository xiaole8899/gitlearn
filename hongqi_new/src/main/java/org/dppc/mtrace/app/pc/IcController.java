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
import org.dppc.mtrace.app.pc.entity.IcEntity;
import org.dppc.mtrace.app.pc.service.IcService;
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
 * IC机管理大模块
 * @author dx
 *
 */
@Controller
@RequestMapping(value="/ic")
public class IcController {
	@Autowired
	private IcService icService;
	
	/**
	 * IC卡读写器列表展示
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/listIc")
	@OperationLog("IC卡读写器展示操作")
	public String toListIc(IcEntity ic,HttpServletRequest request,HttpServletResponse response) throws ParseException{
		//检索--时间范围
		String startdate = request.getParameter("statrdate");
		String enddate = request.getParameter("enddate");
		
		Page<IcEntity> page=new Page<IcEntity>();
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
		icService.selectIcList(page,order,ic,startdate,enddate);
		request.setAttribute("ic", ic);
		request.setAttribute("page",page);
		return "pc/icList";
	}
	
	
	/**
	 * IC卡读写器添加跳转
	 * 
	 * @author dx
	 */
	@RequestMapping("/toAddIc")
	public String toAddIc(){
		return "pc/addIc";
	}
	
	/**
	 * IC卡读写器添加
	 * 
	 * @author dx
	 * @throws IOException 
	 */
	@RequestMapping("/doAddIc")
	@OperationLog("电子秤添加操作")
	public void doAddIc(HttpServletRequest request,HttpServletResponse response,IcEntity ic) throws ParseException, IOException{
		DwzResponse dwzResponse = new DwzResponse();
		PrintWriter pw=response.getWriter();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));  
        Date dt = new Date();
		
		//添加绑定时间
		String date = request.getParameter("date");
		if(ic.equals("")||date==null||date==""){
			ic.setInDate(dt);
		}else{
			ic.setInDate(sdf.parse(date));
		}
		
		//先判断SN号和IP地址是否重复--再进行添加
		int flagIcName = icService.panDuanIcName(ic);
		if(flagIcName == 1){
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("IC卡读写器重复!");
		}else{
			int flag = icService.icAdd(ic);
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
	 * IC卡读写器修改跳转
	 * @return
	 * @author dx
	 * @throws ParseException 
	 */
	@RequestMapping("/toUpdateIc")
	public String toUpdateIc(HttpServletRequest request,HttpServletResponse response,IcEntity ic) throws ParseException{
		IcEntity icEntity = icService.toUpdateIc(ic);
		Date inDate = icEntity.getInDate();
		String date = null;
		System.out.println(inDate);
		if(inDate != null){
			date = icEntity.getInDate().toString();
			//转换时间格式
			date = date.substring(0,date.length()-2);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			//sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));  
			icEntity.setInDate(sdf.parse(date));
		}
		
		request.setAttribute("date",date);
		request.setAttribute("ic", icEntity);
		
		return "pc/editIc";
	}
	
	/**
	 * IC卡读写器修改
	 * 
	 * @author dx
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping("/doUpdateIc")
	@OperationLog("IC卡读写器修改操作")
	public void doUpdateIc(HttpServletRequest request,HttpServletResponse response,IcEntity ic) throws IOException, ParseException {
		DwzResponse dwzResponse=new DwzResponse();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Date ft = new Date();
		
		//添加绑定时间
		String date = request.getParameter("date");
		if(ic.equals("")||date==null||date==""){
			ic.setInDate(ft);
		}else{
			ic.setInDate(sdf.parse(date));
		}
		
		//先判断IC卡读写器名称是否重复--再进行修改
		int flagIcName = icService.editPanDuanIcName(ic);
		if(flagIcName == 1){
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("IC卡读写器名称重复!");
		}else{
			if(icService.icUpdate(ic)){
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
	 * IC卡读写器删除
	 * 
	 * @author dx
	 * @throws IOException 
	 * 
	 */
	@RequestMapping("/deleteIc")
	@OperationLog("IC卡读写器删除操作")
	public void deleteIc(HttpServletRequest request,HttpServletResponse response,IcEntity ic) throws IOException {
		DwzResponse dwzResponse=new DwzResponse();
		if(icService.deleteIc(ic)){
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
