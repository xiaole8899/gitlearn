package org.dppc.mtrace.app.log;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.dppc.mtrace.app.approach.util.PrintWriterUtil;
import org.dppc.mtrace.app.log.entity.OperationLogEntity;
import org.dppc.mtrace.app.log.service.OperationLogService;
import org.dppc.mtrace.frame.base.DwzResponse;
import org.dppc.mtrace.frame.base.OrderCondition;
import org.dppc.mtrace.frame.base.Page;
import org.dppc.mtrace.frame.kit.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/operationLog")
public class OperationLogController {

	@Autowired
	private OperationLogService operationLogService;
	
	/**
	 * 获取操作日志列表
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/getOptList")
	public String getOperationLogList(HttpServletRequest request,OperationLogEntity opr,HttpServletResponse response) throws ParseException{
		Page<OperationLogEntity> page=new Page<OperationLogEntity>();
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
			filed="optTime";
		}
		//排序规则（升序或降序）
		String direction=request.getParameter("orderDirection");
		if(StringUtils.isEmpty(direction)){
			direction="desc";
		}
		
		if(StringUtils.isNotEmpty(opr.getStartTime()) && StringUtils.isNotEmpty(opr.getEndTime())){
	    	if(!DateUtil.compareTwoDate(opr.getStartTime(),opr.getEndTime(),DateUtil.DATETIMEFORMAR)){
	    		DwzResponse dwzResponse=new DwzResponse();
	    		dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
	    		dwzResponse.setMessage("开始时间不能大于结束时间!");
	    		dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
	    		PrintWriterUtil.writeObject(response, dwzResponse);
	    	}
	    }
		//排序
		OrderCondition order=new OrderCondition(filed,direction);
		request.setAttribute("order",order);
		operationLogService.getOptList(page, order, opr);
		request.setAttribute("page",page);
		//查询条件
		request.setAttribute("operationLog",opr);
		return "log/oprlist";
	}
	
	/**
	 * 批量删除操作日志
	 * @param orglog_ids  logId 数组
	 * @throws IOException 
	 */
	@RequestMapping(value="/deleteOprLog")
	@ResponseBody
	public DwzResponse deleteOprLog(String [] orglog_ids,HttpServletRequest request,HttpServletResponse response) throws IOException{
		DwzResponse dwzResponse=new DwzResponse();
		//标示是否操作成功
		boolean flag=false;
		//说明有选中数据
		if(orglog_ids!=null){
			for(String orgId:orglog_ids){
				flag=operationLogService.deleteOperationLog(Integer.parseInt(orgId));
			}
			if(flag){
				dwzResponse.setStatusCode(DwzResponse.SC_OK);
				dwzResponse.setMessage("操作成功!");
				dwzResponse.setCallbackType(DwzResponse.CT_FORWARD);
				dwzResponse.setForwardUrl("operationLog/getOptList.do");
			}else{
				dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
				dwzResponse.setCallbackType(DwzResponse.CT_FORWARD);
				dwzResponse.setForwardUrl("operationLog/getOptList.do");
				dwzResponse.setMessage("操作失败!");
			}
		}else{
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("请至少选中一条数据!");
		}
		return dwzResponse;
	}
}

