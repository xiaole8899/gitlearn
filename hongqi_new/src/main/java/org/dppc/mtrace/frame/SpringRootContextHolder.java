package org.dppc.mtrace.frame;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Spring的ApplicationContext（root）持有类
 * 
 * @author maomh
 *
 */
public class SpringRootContextHolder implements ApplicationContextAware {
	private static SpringRootContextHolder me;
	
	private ApplicationContext ctx;
	
	private SpringRootContextHolder() {
		if (me == null) {
			me =this;
		}
	}
	
	
	/**
	 * 获取 ApplicationContext 实例
	 * 
	 * @return
	 */
	public static ApplicationContext getRootApplicationContext() {
		return SpringRootContextHolder.me.ctx;
	}
	
	
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.ctx =applicationContext;
	}

}
