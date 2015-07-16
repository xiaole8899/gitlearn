package org.dppc.mtrace.frame.interceptor;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dppc.mtrace.app.menu.entity.FunctionEntity;
import org.dppc.mtrace.app.menu.entity.MenuEntity;
import org.dppc.mtrace.app.user.entity.UserEntity;
import org.dppc.mtrace.frame.base.DwzResponse;
import org.dppc.mtrace.frame.kit.JsonKit;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 权限控制
 * 
 * @author sunlong
 *
 */
@Component
public class PermissionInterceptor extends HandlerInterceptorAdapter {
	
	private Pattern regex =Pattern.compile("^/(login|tologin|dologin|doajaxlogin|logout)$", Pattern.CASE_INSENSITIVE);
	
	private Log log =LogFactory.getLog("[访问登陆过滤器]");
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String path = request.getRequestURI();
		path=path.replace(request.getContextPath(),"");
 		path = path.substring(path.indexOf("/"), path.lastIndexOf("."));
		if (!regex.matcher(path).matches()) {
			HttpSession session = request.getSession();
			// 测试、session 20秒失效
			//session.setMaxInactiveInterval(15);
			UserEntity user=(UserEntity) session.getAttribute("USER_SESSION_KEY");
			if (user == null) {
				try {
					// 先判断是否是 ajax 请求
					if (StringUtils.equalsIgnoreCase("XMLHttpRequest", request.getHeader("X-Requested-With")) 
							|| StringUtils.isNotEmpty(request.getParameter("ajax"))) {
						DwzResponse dr =new DwzResponse();
						dr.setStatusCode(DwzResponse.SC_TIMEOUT);
						dr.setMessage("对不起，您在30分钟内没有任何操作，请重新登陆！");
						response.setContentType("text/html;charset=UTF-8");
						PrintWriter pw=response.getWriter();
						pw.print(JsonKit.toJSON(dr));
						pw.flush();
						pw.close();
					}
					log.info(path +" ---> 未登录，已禁止");
					return false;
				} catch (Exception e) {
					throw new ServletException(e);
				}
			} else {
				// 解析Url-Mappings
				Set<String> mappings =(Set<String>) session.getAttribute("PASS_URL");
				if (mappings == null) {
					mappings =new HashSet<String>();
					// 放权过滤 (首页、登陆、注销、服务、dwz配置)
					mappings.add("^\\/(index.do|login.do|toLogin.do|doLogin.do|doajaxlogin.do|logout.do|webservice.do|loadDwzFrags.do)?(\\?.*)?$");
					// 菜单和功能过滤
					for (MenuEntity menu : user.getMenus()) {
						String url =menu.getUrl();
						if (StringUtils.isEmpty(url)) continue;
						saveRegexMappings(mappings, url);
					}
					for (FunctionEntity function : user.getFunctions()) {
						String url =function.getUrl();
						if (StringUtils.isEmpty(url)) continue;
						saveRegexMappings(mappings, url);
					}
				}
					String servletPath =StringUtils.defaultIfEmpty(request.getServletPath(), "/");
					String pathInfo =StringUtils.defaultIfEmpty(request.getPathInfo(), StringUtils.EMPTY);
					String queryString =StringUtils.isEmpty(request.getQueryString()) ? StringUtils.EMPTY : ("?" +request.getQueryString());
					String requestPath =servletPath +pathInfo +queryString;
					
					for (String regex : mappings) {
						
						if (Pattern.matches(regex, requestPath)) {
							if (log.isDebugEnabled()) {
								log.debug("{" +requestPath +"} 匹配成功 - " +regex);
							}
							return true;
						}
					}
					DwzResponse dr =new DwzResponse();               
					dr.setStatusCode(DwzResponse.SC_ERROR);
					dr.setMessage("对不起，您没有权限进行该操作！");
					dr.setCallbackType(DwzResponse.CT_CLOSE);
					response.setContentType("text/html;charset=UTF-8");
					PrintWriter pw=response.getWriter();
					pw.print(JsonKit.toJSON(dr));
					pw.flush();
					pw.close();
					return false;
			}
		}
		return true;
	}
	//从url分析出pattern
	private void saveRegexMappings(Set<String> mappings, String url) {
		String regex ="^" +url.replaceAll("\\/\\$\\d", "\\/([^\\/]+)").replace("/", "\\/") +"(\\?.*)?$";
		mappings.add(regex);
	}
}
