package org.dppc.mtrace.app;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dppc.mtrace.app.log.service.OperationLogService;
import org.dppc.mtrace.frame.base.DwzResponse;
import org.dppc.mtrace.frame.kit.ResponseKit;
import org.dppc.mtrace.frame.kit.StringKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 控制器异常捕获器
 * 
 * @author maomh
 *
 */
@ControllerAdvice(basePackages="org.dppc.mtrace.app")
public class ExceptionCatchHandler {
	private Logger logger =LoggerFactory.getLogger(getClass());
	
	@Autowired
	private OperationLogService operationLogService;
	
	/**
	 * 异常处理方法
	 * 
	 * @param e
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@ExceptionHandler
	public void handleException(Exception e, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		logger.error(request.getRequestURL().append(" 请求失败失败！").toString(), e);
		
		//若存在logId,则
			Integer logId=(Integer) request.getAttribute(AppConstant.REQUEST_OPR_LOG_ID);
			if(logId!=null){
				System.out.println("日志编号为:<<<<<<<<<<<"+logId);
				boolean flag=operationLogService.alerCommentsByLogId(logId);
				if(flag){
					logger.info("修改状态成功!");
				}
			}
	
		// 判断是否是 ajax 请求
		if (StringKit.equalsIgnoreCase("XMLHttpRequest", request.getHeader("X-Requested-With"))
				|| StringKit.isNotEmpty(request.getParameter("ajax"))) {
			DwzResponse dr =new DwzResponse();
			dr.setStatusCode(DwzResponse.SC_ERROR);
			dr.setMessage("很抱歉，服务器发生错误！");
			ResponseKit.printObjectToJson(response, dr);
			return;
		}
		
		// 若不是 ajax 请求，则直接返回 500 错误代码
		response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
	}
}
