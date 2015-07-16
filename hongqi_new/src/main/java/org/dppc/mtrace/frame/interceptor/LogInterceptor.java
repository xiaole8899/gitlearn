package org.dppc.mtrace.frame.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

/**
 * 日志
 * 
 * @author maomh
 *
 */
@Component
public class LogInterceptor implements WebRequestInterceptor {
	private Logger logger =LoggerFactory.getLogger(getClass());

	@Override
	public void preHandle(WebRequest request) throws Exception {
		logger.info("================" +getClass().getSimpleName() +".preHandle()");
	}

	@Override
	public void postHandle(WebRequest request, ModelMap model) throws Exception {
		logger.info("================" +getClass().getSimpleName() +".postHandle()");
	}

	@Override
	public void afterCompletion(WebRequest request, Exception ex)
			throws Exception {
		logger.info("================" +getClass().getSimpleName() +".afterCompletion()");
	}
	
}
