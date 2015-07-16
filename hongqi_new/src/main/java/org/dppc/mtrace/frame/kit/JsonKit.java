package org.dppc.mtrace.frame.kit;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Json序列化/反序列化 简单工具类
 * 
 * @author maomh
 */
public class JsonKit {
	private static final ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 将 Java 对象转为 JSON 字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static <T> String toJSON(T obj) {
		String jsonStr;
		try {
			jsonStr = objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException("将 Java 对象转为 JSON 字符串 失败！", e);
		}
		return jsonStr;
	}
	
	
	/**
	 * 将 Java 对象转为 JSON 字符串 并格式化（比较美观，用于日志输出）
	 * 
	 * @param obj
	 * @return
	 */
	public static <T> String toPrettyJson(T obj) {
		String jsonStr;
		try {
			jsonStr =objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException("将 Java 对象转为 JSON 字符串 失败！", e);
		}
		return jsonStr;
	}

	
	/**
	 * 将 JSON 字符串转为 Java 对象
	 * 
	 * @param json
	 * @param type
	 * @return
	 */
	public static <T> T fromJSON(String json, Class<T> type) {
		T obj;
		try {
			obj = objectMapper.readValue(json, type);
		} catch (Exception e) {
			throw new RuntimeException("将 JSON 字符串转为 Java 对象", e);
		}
		return obj;
	}
}
