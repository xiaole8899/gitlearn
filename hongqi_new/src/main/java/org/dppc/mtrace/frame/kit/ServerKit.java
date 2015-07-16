package org.dppc.mtrace.frame.kit;

import javax.servlet.ServletContext;

/**
 * 服务器相关的工具类，主要是获取常用的应用服务器默认的servlet名字
 * 
 * @author maomh
 */
public class ServerKit {
	private static String DefaultServletName;
	
	/* Tomcat, Jetty, JBoss, GlassFish 默认Servlet名称 */
	private static final String COMMON_DEFAULT_SERVLET_NAME = "default";

	/* Google App Engine 默认Servlet名称 */
	private static final String GAE_DEFAULT_SERVLET_NAME = "_ah_default";

	/* Resin 默认Servlet名称  */
	private static final String RESIN_DEFAULT_SERVLET_NAME = "resin-file";

	/* WebLogic 默认Servlet名称  */
	private static final String WEBLOGIC_DEFAULT_SERVLET_NAME = "FileServlet";

	/* WebSphere 默认Servlet名称 */
	private static final String WEBSPHERE_DEFAULT_SERVLET_NAME = "SimpleFileServlet";
	
	
	
	/**
	 * 获取Servlet容器里 默认Servlet名称
	 * 
	 * @return
	 */
	public static String getDefaultServletName(ServletContext sc) {
		if (DefaultServletName != null) {
			return DefaultServletName;
		}
		
		if (sc.getNamedDispatcher(COMMON_DEFAULT_SERVLET_NAME) != null) {
			DefaultServletName = COMMON_DEFAULT_SERVLET_NAME;
		}
		else if (sc.getNamedDispatcher(GAE_DEFAULT_SERVLET_NAME) != null) {
			DefaultServletName = GAE_DEFAULT_SERVLET_NAME;
		}
		else if (sc.getNamedDispatcher(RESIN_DEFAULT_SERVLET_NAME) != null) {
			DefaultServletName = RESIN_DEFAULT_SERVLET_NAME;
		}
		else if (sc.getNamedDispatcher(WEBLOGIC_DEFAULT_SERVLET_NAME) != null) {
			DefaultServletName = WEBLOGIC_DEFAULT_SERVLET_NAME;
		}
		else if (sc.getNamedDispatcher(WEBSPHERE_DEFAULT_SERVLET_NAME) != null) {
			DefaultServletName = WEBSPHERE_DEFAULT_SERVLET_NAME;
		}
		else {
			throw new RuntimeException("无法识别当前服务器的 默认Servlet 名称！");
		}
		return DefaultServletName;
	}
}
