package org.dppc.mtrace.frame.servlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import org.springframework.web.servlet.DispatcherServlet;

/**
 * Spring MVC
 * 
 * @author maomh
 *
 */

@WebServlet(
	urlPatterns="*.do", 
	loadOnStartup=1, 
	initParams={
		@WebInitParam(
			name="contextConfigLocation", 
			value="classpath:mvc-context.xml"
		)
	}
)
public class MvcServlet extends DispatcherServlet {
	private static final long serialVersionUID = 8782986728369944531L;

}
