package org.dppc.mtrace.frame.kit;


/**
 * 断言类
 * 
 * @author maomh
 */
public abstract class Assert {
	/**
	 * 布尔表达式断言, 表达式必须是{@code true}
	 * 
	 * @param expression 表达式
	 * @param message 异常相关信息
	 */
	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw new IllegalArgumentException(message);
		}
	}
	
	/**
	 * 布尔表达式断言，表达式必须是{@code true}
	 * 
	 * @param expression
	 */
	public static void isTrue(boolean expression) {
		isTrue(expression, "[断言失败] - 该表达式必须是 true");
	}
	
	
	/**
	 * 对象非空断言，obj不能是{@code null}
	 * 
	 * @param obj
	 * @param message
	 */
	public static void notNull(Object obj, String message) {
		if (obj == null) {
			throw new IllegalArgumentException(message);
		}
	}
	
	
	/**
	 * 对象非空断言，obj不能是{@code null}
	 * 
	 * @param obj
	 */
	public static void notNull(Object obj) {
		notNull(obj, "[断言失败] - 这个参数是必需的，它不能是 null");
	}
	
	
	/**
	 * 字符串非空断言，str不能为{@code null} 或 {@code ""}空字符串
	 * 
	 * @param str
	 * @param message
	 */
	public static void notEmpty(String str, String message) {
		if (StringKit.isEmpty(str)) {
			throw new IllegalArgumentException(message);
		}
	}
	
	
	/**
	 * 字符串非空断言，str不能为{@code null} 或 {@code ""}空字符串
	 * 
	 * @param str
	 */
	public static void notEmpty(String str) {
		notEmpty(str, "[断言失败] - 字符串不能为 null 或 \"\"（空字符串）");
	}
	
	
	/**
	 * 字符串非空白断言，str不能为{@code null}，并且不能都是空白字符
	 * 
	 * @param str
	 * @param message
	 */
	public static void notBlank(String str, String message) {
		if (StringKit.isBlank(str)) {
			throw new IllegalArgumentException(message);
		}
	}
	
	
	/**
	 * 字符串非空白断言，str不能为{@code null}，并且不能都是空白字符
	 * 
	 * @param str
	 */
	public static void notBlank(String str) {
		notBlank(str, "[断言失败] - 字符串不能为null，并且不能都是空白字符");
	}
	
}
