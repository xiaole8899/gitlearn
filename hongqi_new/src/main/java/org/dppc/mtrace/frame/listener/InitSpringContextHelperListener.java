package org.dppc.mtrace.frame.listener;

import java.lang.reflect.Constructor;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.dppc.mtrace.frame.SpringWebContextHelper;

/**
 * SpringContextHelper 初始化监听类
 * 
 * @author maomh
 *
 */
@WebListener
public class InitSpringContextHelperListener implements ServletContextListener {
	private Logger logger =LoggerFactory.getLogger("[SpringContextHelper]");

	@Override
	public void contextDestroyed(ServletContextEvent sc) {}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Class<SpringWebContextHelper> clazz =SpringWebContextHelper.class;
		try {
			// 获取SpringContextHelper构造对象
			Constructor<SpringWebContextHelper> c =clazz.getDeclaredConstructor(ServletContext.class);
			// 设置有权访问
			c.setAccessible(true);
			// 直接创建一个SpringContextHelper对象， 此时SpringContexHelper会进行初始化
			c.newInstance(sce.getServletContext());
			logger.info("初始化完毕！");
		} catch (Exception e) {
			logger.error("初始化失败！", e);
			throw new RuntimeException(e);
		}
	}

}
