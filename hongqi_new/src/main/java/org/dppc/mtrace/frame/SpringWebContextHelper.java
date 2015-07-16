package org.dppc.mtrace.frame;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Spring上下文帮助类
 * 
 * @author maomh
 */
public class SpringWebContextHelper {
	private static SpringWebContextHelper sch;
	
	private ServletContext sc;
	private SpringWebContextHelper(ServletContext sc) {
		this.sc =sc;
		if (sch == null) {
			sch =this;
		}
	}
	
	
	/**
	 * 获取当前的 Spring-Root-Context 实例
	 * 
	 * @return
	 */
	public static ApplicationContext getApplicationContext() {
		return WebApplicationContextUtils.getWebApplicationContext(sch.sc);
	}
	
	
	/**
	 * 获取ServletContext实例
	 * 
	 * @return
	 */
	public static ServletContext getServletContext() {
		return sch.sc;
	}
}
