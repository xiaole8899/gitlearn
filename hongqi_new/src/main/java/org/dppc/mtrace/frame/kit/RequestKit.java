package org.dppc.mtrace.frame.kit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.dppc.mtrace.frame.RequestMethod;

/**
 * 请求工具类
 * 
 * @author maomh
 *
 */
public class RequestKit {
	public static Pattern RequestPathPattern =Pattern.compile("^(\\/.*)(\\.\\w+)$");
	
	/**
	 * 获取请求路径，如果有后缀则去掉后缀<br/>
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestPath(HttpServletRequest request) {
		String requestPath =getRequestURL(request);
		Matcher m =RequestPathPattern.matcher(requestPath);
		if (m.find()) {
			requestPath =m.group(1);
		}
		return requestPath;
	}
	
	
	/**
	 * 获取请求路径，如果有后缀也不会去掉后缀
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestURL(HttpServletRequest request) {
		String servletPath =StringKit.defaultIfEmpty(request.getServletPath(), "/");
		String pathInfo =StringKit.defaultIfEmpty(request.getPathInfo(), StringKit.EMPTY);
		return servletPath +pathInfo;
	}
	
	
	/**
	 * 获取请求类型
	 * 
	 * @param request
	 * @return
	 */
	public static RequestMethod getRequestMethod(HttpServletRequest request) {
		String method =request.getMethod().toUpperCase();
		if (StringKit.equals(method, RequestMethod.GET.name())) {
			return RequestMethod.GET;
		}
		if (StringKit.equals(method, RequestMethod.POST.name())) {
			return RequestMethod.POST;
		}
		if (StringKit.equals(method, RequestMethod.PUT.name())) {
			return RequestMethod.PUT;
		}
		if (StringKit.equals(method, RequestMethod.DELETE.name())) {
			return RequestMethod.DELETE;
		}
		return RequestMethod.ALL;
	}
}
