package org.dppc.mtrace.frame.kit;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射工具类
 * 
 * @author maomh
 */
public class ReflectKit {
	
	/**
	 * 为某个对象设置某个属性值
	 * 
	 * @param target 目标对象
	 * @param field 属性
	 * @param value 值
	 */
	public static void setField(Object target, Field field, Object value) {
		Assert.notNull(target);
		Assert.notNull(field);
		field.setAccessible(true);
		try {
			field.set(target, value);
		} catch (Throwable t) {
			throw new RuntimeException("设置属性[" +target.getClass().getName() +"." +field.getName() +"] 失败！");
		}
	}

	/**
	 * 反射调用某个方法
	 * 
	 * @param target
	 * @param method
	 * @param parameters
	 * @return
	 */
	public static Object invoke(Object target, Method method, Object...parameters) {
		Assert.notNull(target);
		Assert.notNull(method);
		method.setAccessible(true);
		try {
			return method.invoke(target, parameters);
		} catch (Throwable t) {
			throw new RuntimeException("调用[" +target.getClass().getName() +"." +method.getName() +"()] 失败！");
		}
	}

	/**
	 * 利用Class.newInstance 实例化某个类
	 * 
	 * @param clazz
	 * @return
	 */
	public static Object newInstance(Class<?> clazz) {
		try {
			return clazz.newInstance();
		} catch (Throwable t) {
			throw new RuntimeException("实例化[" +clazz.getName() +"] 失败！");
		}
	}
}
