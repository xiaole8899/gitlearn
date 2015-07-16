package org.dppc.mtrace.frame.kit;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.cglib.proxy.Enhancer;


@SuppressWarnings("unchecked")
public class ClassKit {
	public static final String ARRAY_SUFFIX ="[]";
	
	
	public static final String CGLIB_CLASS_SEPARATOR ="$$";
	
	
	public static final String CLASS_FILE_SUFFIX =".class";
	
	@SuppressWarnings("rawtypes")
	private static final Map primitiveWrapperMap = new HashMap();
    static {
         primitiveWrapperMap.put(Boolean.TYPE, Boolean.class);
         primitiveWrapperMap.put(Byte.TYPE, Byte.class);
         primitiveWrapperMap.put(Character.TYPE, Character.class);
         primitiveWrapperMap.put(Short.TYPE, Short.class);
         primitiveWrapperMap.put(Integer.TYPE, Integer.class);
         primitiveWrapperMap.put(Long.TYPE, Long.class);
         primitiveWrapperMap.put(Double.TYPE, Double.class);
         primitiveWrapperMap.put(Float.TYPE, Float.class);
         primitiveWrapperMap.put(Void.TYPE, Void.TYPE);
    }
	
	
	/**
	 * 获取当前默认的类加载器
	 * 
	 * @return
	 */
	public static ClassLoader getDefaultClassLoader() {
		ClassLoader loader =null;
		try {
			loader =Thread.currentThread().getContextClassLoader();
		} catch (Throwable t) {
			// 走个过场而已  -_-!!!
		}
		// 如果 没有线程上下文环境的ClassLoader，那么就尝试加载自己的ClassLoader
		if (loader == null) {
			loader =ClassKit.class.getClassLoader();
			// 若还是没有  ---> 太特么极端了 基本不会出现
			if (loader == null) {
				try {
					loader =ClassLoader.getSystemClassLoader();
				} catch (Throwable t) {
					// 依然走个过场  -_-!!!
				}
			}
		}
		return loader;
	}
	
	
	/**
	 * 通过一个类名加载一个类
	 * 
	 * @param className 类名
	 * @param initialize 是否加载的同时进行初始化
	 * @return
	 */
	public static Class<?> loadClass(String className, boolean initialize) {
		Class<?> clazz =null;
		try {
			if (initialize) {
				clazz =Class.forName(className);
			} else {
				clazz =getDefaultClassLoader().loadClass(className);
			}
		} catch (Throwable t) {
			// 走个过场喽 ~~~
		}
		return clazz;
	}

	
	/**
	 * 初始化一个类
	 * 
	 * @param clazz
	 */
	public static void initClass(Class<?> clazz) {
		Assert.notNull(clazz, "要加载的类不能为空！");
		try {
			Class.forName(clazz.getName());
		} catch (Throwable t) {
			throw new RuntimeException("初始化类失败！", t);
		}
	}
	
	
	/**
	 * 初始化一批类
	 * 
	 * @param classes
	 */
	public static void initClasses(Class<?>...classes) {
		for (Class<?> clazz : classes) {
			initClass(clazz);
		}
	}
	
	/**
	 * 判断两个类的继承关系
	 * 
	 * @param superClass
	 * @param subClass
	 * @return
	 */
	public static boolean isInHeritance(Class<?> superClass, Class<?> subClass) {
		Assert.notNull(superClass, "超类不能为空！");
		Assert.notNull(subClass, "子类不能为空");
		return superClass.isAssignableFrom(subClass);
	}
	
	
	/**
	 * 判断一个类不是抽象也不是接口
	 * 
	 * @param clazz
	 * @return
	 */
	public static boolean isNotAbstractAndInterface(Class<?> clazz) {
		int mod =clazz.getModifiers();
		return !Modifier.isAbstract(mod) && !Modifier.isInterface(mod);
	}
	
	
	/**
	 * 判断一个类名是否属于Cglib生成的代理类
	 * 
	 * @param className
	 * @return
	 */
	public static boolean isCglibProxyClassName(String className) {
		return StringKit.isNotEmpty(className) && className.contains(CGLIB_CLASS_SEPARATOR);
	}
	
	
	/**
	 * 判断一个类是Cgblib生成的代理类
	 * 
	 * @param clazz
	 * @return
	 */
	public static boolean isCglibProxyClass(Class<?> clazz) {
		return clazz!= null && Enhancer.isEnhanced(clazz);
	}


	/**
	 * 解析出 超类
	 * 
	 * @param clazz
	 * @return
	 */
	public static Class<?>[] parseSuperClasses(Class<?> clazz) {
		List<Class<?>> slist =new LinkedList<Class<?>>();
		getAllSuper(slist, clazz);
		return slist.toArray(new Class<?>[slist.size()]);
	}

	// 递归 获取所有的超类
	private static void getAllSuper(List<Class<?>> slist, Class<?> clazz) {
		Class<?> s =(Class<?>) clazz.getGenericSuperclass();
		if (s == null || s.equals(Object.class)) {
			return ;
		}
		slist.add(s);
		getAllSuper(slist, s);
	}


	/**
	 * 解析出 所有接口
	 * 
	 * @param clazz
	 * @return
	 */
	public static Class<?>[] parseInterfaces(Class<?> clazz) {
		List<Class<?>> ilist =new LinkedList<Class<?>>();
		getAllInterfaces(ilist, clazz);
		return ilist.toArray(new Class<?>[ilist.size()]);
	}
	// 递归获取所有的接口
	private static void getAllInterfaces(List<Class<?>> ilist, Class<?> clazz) {
		Type[] interfaces =clazz.getGenericInterfaces();
		for (Type t : interfaces) {
			ilist.add((Class<?>) t);
			getAllInterfaces(ilist, (Class<?>) t);
		}
		Class<?> s =(Class<?>) clazz.getGenericSuperclass();
		if (s != null && !s.equals(Object.class)) {
			getAllInterfaces(ilist, s);
		}
	}


	/**
	 * 将基本类型转换为封装类型，也就是装包
	 * 
	 * @param type
	 * @return
	 */
	public static Class<?> primitiveToWrapper(Class<?> type) {
		if (type != null && type.isPrimitive()) {
            return (Class<?>) primitiveWrapperMap.get(type);
        }
		return null;
	}
	
}
