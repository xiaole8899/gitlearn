package org.dppc.mtrace.frame;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 当前请求辅助类
 * 
 * @author maomh
 *
 */
public class WebContextHelper {
	/**
	 * 获取当前请求
	 * 
	 * @return
	 */
	public static HttpServletRequest getCurrentRequest() {
		ServletRequestAttributes sra =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return sra.getRequest();
	}
	
	/**
	 * 获取当前响应
	 * 
	 * @return
	 */
	public static HttpServletResponse getCurrentResponse() {
		ServletRequestAttributes sra =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return sra.getResponse();
	}
}
