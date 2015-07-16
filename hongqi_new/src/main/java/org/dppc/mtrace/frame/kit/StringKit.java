package org.dppc.mtrace.frame.kit;

import org.apache.commons.lang.StringUtils;

/**
 * 明确StringUtils，同时拓展两个方法
 * 
 * @author maomh
 *
 */
public final class StringKit extends StringUtils {
	
	/**
	 * 将字符串首字母大写
	 * 
	 * @param str
	 * @return
	 */
	public static String fisrtLetterToUpperCase(String str) {
		if (isBlank(str)) {
			return str;
		}
		String others =str.substring(1);
		return Character.toUpperCase(str.charAt(0)) +others;
	}
	
	
	/**
	 * 将字符串首字母小写
	 * 
	 * @param str
	 * @return
	 */
	public static String firstLetterToLowerCase(String str) {
		if (isBlank(str)) {
			return str;
		}
		String others =str.substring(1);
		return Character.toLowerCase(str.charAt(0)) +others;
	}
	
	public static String fillZero(String str,int length){
		int actualLength = str.length();
		StringBuffer buf = new StringBuffer();
		if(actualLength < length){
			buf.append(str);
			int lengthCount = length -actualLength;
			for(int i=0;i<lengthCount;i++){
				buf.append(0);
			}
		}
		return buf.toString();
		
	}

}
