package org.dppc.mtrace.app.detection;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.dppc.mtrace.app.AppConstant;
import org.dppc.mtrace.app.approach.entity.ApproachEntity;
import org.dppc.mtrace.app.approach.util.PrintWriterUtil;
import org.dppc.mtrace.app.detection.entity.DetectionEntity;
import org.dppc.mtrace.app.detection.service.DetectionService;
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
 * 检疫检测管理
 * 
 * @author sunlong
 *
 */
@Controller
@RequestMapping("/detection")
public class DetectionController {
	
	@Autowired
	private DetectionService detectionService;
	
	/**
	 * 检测查询
	 * 
	 * @author sunlong
	 * @throws ParseException 
	 */
	@RequestMapping("/detectionList")
	@OperationLog(value="检测查询")
	public String detectionList(DetectionEntity detection,HttpServletRequest request,HttpServletResponse response) throws ParseException {
		Page<DetectionEntity> page=new Page<DetectionEntity>();
		String dateStart = request.getParameter("dateStart");
		String dateEnd = request.getParameter("dateEnd");
		if(StringUtils.isNotEmpty(dateStart) && StringUtils.isNotEmpty(dateEnd)){
	    	if(!DateUtil.compareTwoDate(dateStart,dateEnd,DateUtil.DATETIMEFORMAR)){
	    		DwzResponse dwzResponse=new DwzResponse();
	    		dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
	    		dwzResponse.setMessage("开始时间不能大于结束时间!");
	    		dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
	    		PrintWriterUtil.writeObject(response, dwzResponse);
	    	}
	    }
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
		request.setAttribute("order",order);
		detectionService.selectDetectionList(page,order,detection,dateStart,dateEnd);
		request.setAttribute("detection", detection);
		request.setAttribute("page",page);
		request.setAttribute("dateStart", dateStart);
		request.setAttribute("dateEnd", dateEnd);
		Map<String,Object> map=new HashMap<String,Object>();
		map.putAll(AppConstant.voucherType);
		map.putAll(AppConstant.voucherTypeV);
		request.setAttribute("voucherTypeVList",map);
		return "detection/detectionList";
	}
	
	/**
	 * 检测添加跳转
	 * 
	 * @author sunlong
	 */
	@RequestMapping("/toAddDetection")
	public String toAddDetection() {
		return "detection/addDetection";
	}
	
	/**
	 * 查找带回进厂蔬菜信息
	 * 
	 * @author SunLong
	 */
	@RequestMapping("/backList")
	public String backList(HttpServletRequest request,HttpServletResponse response,ApproachEntity approach){
		Page<ApproachEntity> page=new Page<ApproachEntity>();
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
		Map<String,Object> map=new HashMap<String,Object>();
		map.putAll(AppConstant.voucherType);
		map.putAll(AppConstant.voucherTypeV);
		request.setAttribute("map",map);
		OrderCondition order=new OrderCondition(filed,direction);
		request.setAttribute("order",order);
		detectionService.selectApproachDetailList(page,order,approach);
		request.setAttribute("page",page);
		request.setAttribute("approach", approach);
		return "detection/backList";
	}
	
	/**
	 * 检测添加
	 * 
	 * @author sunlong
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping("/doAddDetection")
	@OperationLog(value="检测添加")
	public void doAddDetection(DetectionEntity detection,HttpServletRequest request,HttpServletResponse response) throws IOException, ParseException {
		DwzResponse dwzResponse=new DwzResponse();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));  
        Date dt = new Date();
        String approachId = request.getParameter("approach.approachId");
		String goodsName = request.getParameter("approach.goodsName");
		String batchId = request.getParameter("approach.batchId");
		String batchType = request.getParameter("approach.batchType");
		String wholesalerName = request.getParameter("approach.wholesalerName");
		String wholesalerId = request.getParameter("approach.wholesalerId");
		String goodsCode = request.getParameter("approach.goodsCode");
		String date = request.getParameter("date");
		if(date.equals("")||date==null||date==""){
			detection.setDetectionDate(dt);
		}else{
			detection.setDetectionDate(sdf.parse(date));
		}
		detection.setGoodsName(goodsName);
		detection.setBatchId(batchId);
		detection.setBatchType(batchType);
		detection.setWholesalerName(wholesalerName);
		detection.setWholesalerId(wholesalerId);
		detection.setGoodsCode(goodsCode);
		detection.setUploadTime(dt);
		if(detectionService.doAddDetection(detection,approachId)){
			dwzResponse.setStatusCode(DwzResponse.SC_OK);
			dwzResponse.setCallbackType(DwzResponse.CT_FORWARD);
			dwzResponse.setForwardUrl("detection/detectionList.do");
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
	
	/**
	 * 查看检测明细
	 * 
	 * @author sunlong
	 * @throws ParseException 
	 */
	@RequestMapping("/showDetectionDetail")
	@OperationLog(value="查看检测明细")
	public String showDetectionDetail(DetectionEntity detection,HttpServletRequest request,HttpServletResponse response) throws ParseException {
		DetectionEntity detectionEntity = detectionService.showDetectionDetail(detection);
		request.setAttribute("detection", detectionEntity);
		return "detection/showDetectionDetail";
	}
	
}

