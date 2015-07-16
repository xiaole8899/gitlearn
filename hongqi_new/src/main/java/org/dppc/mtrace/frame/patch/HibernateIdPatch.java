package org.dppc.mtrace.frame.patch;

import java.io.Serializable;
import java.lang.reflect.Method;

import javax.annotation.PostConstruct;
import javax.persistence.Id;

import org.dppc.mtrace.frame.annotation.UseIDPatch;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.hibernate.event.spi.SaveOrUpdateEventListener;
import org.hibernate.internal.SessionFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Hibernate ID 的补丁<br/>
 * 
 * 如果实体类已经拥有了ID，则在保存的时候用实体提供的ID作为主键而不用数据库自动生成
 * 
 * @author SunLong
 *
 */
@Component
public class HibernateIdPatch implements SaveOrUpdateEventListener {
	private static final long serialVersionUID = -2714721510058285551L;
	private Logger logger =LoggerFactory.getLogger(getClass());

	@Autowired
	private SessionFactory sf;
	
	@Override
	public void onSaveOrUpdate(SaveOrUpdateEvent event)
			throws HibernateException {
		Object data =event.getObject();
		
		if (data != null && data.getClass().isAnnotationPresent(UseIDPatch.class)) {
			try {
				for (Method method : data.getClass().getMethods()) {
					if (method.isAnnotationPresent(Id.class)) {
						Object id =method.invoke(data);
						if (id !=null && id instanceof Serializable) {
							event.setRequestedId((Serializable) id);
							break;
						}
						logger.warn("实体[" +data.getClass() +"] 的主键采用数据库自动生成或未实现 Serializable 接口！");
					}
				}
			} catch (Exception e) {
				logger.error("设置主键失败！", e);
				throw new RuntimeException("设置主键失败！", e);
			}
		} else {
			logger.debug("实体为null或该实体类没有注解 @UseIDPath，故忽略此实体的主键补丁");
		}
	}

	@PostConstruct
	public void registeMe() {
		logger.info("注册HibernationIdPatch！");
		SessionFactoryImpl sfi =(SessionFactoryImpl) sf;
		EventListenerRegistry registry =sfi.getServiceRegistry().getService(EventListenerRegistry.class);
		registry.prependListeners(EventType.SAVE, this);
	}
}
