package org.dppc.mtrace.frame.kit;

import java.lang.reflect.Method;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 对象工具类
 * 
 * @author maomh
 */
public class ObjectKit {
	/* Object 默认的方法 */
	public static final String[] DEFAULT_OBJECT_METHOD_NAMES;
	static {
		Method[] methods =Object.class.getMethods();
		DEFAULT_OBJECT_METHOD_NAMES =new String[methods.length];
		for (int i=0; i<methods.length; ++i) {
			DEFAULT_OBJECT_METHOD_NAMES[i] =methods[i].getName();
		}
	}
	
	
	/**
	 * 判断某个方法是否是Object的默认方法
	 * 
	 * @param method
	 * @return
	 */
	public static boolean isDefaultObjectMethod(Method method) {
		return ArrayKit.contains(DEFAULT_OBJECT_METHOD_NAMES, method.getName());
	}
	
	
	/**
	 * 对象 toString() 方法重写
	 * 
	 * @param obj
	 * @return
	 */
	public static String objectToString(Object obj) {
		return ToStringBuilder.reflectionToString(obj);
	}
	
	
	/**
	 * 对象 hashCode() 方法重写
	 * 
	 * @param obj
	 * @return
	 */
	public static int objectHashCode(Object obj) {
		return HashCodeBuilder.reflectionHashCode(obj);
	}
	
	
	/**
	 * 对象 equals() 方法重写
	 * 
	 * @param left
	 * @param right
	 * @return
	 */
	public static boolean objectEquals(Object left, Object right) {
		return EqualsBuilder.reflectionEquals(left, right);
	}
	
	
	
}
