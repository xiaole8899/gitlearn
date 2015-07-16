package org.dppc.mtrace.app.approach.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.dppc.mtrace.frame.kit.JsonKit;

/**
 * 用来实现像前台打印的操作
 */
public class PrintWriterUtil{
	static PrintWriter pw;
	
	/**
	 * 将对象打印到前台
	 * @param response
	 * @param obj
	 * @author weiyuzhen
	 */
	public static void writeObject(HttpServletResponse response,Object obj){
		try {
			response.setContentType("text/html;utf-8");
			pw = response.getWriter();
			String strjson = JsonKit.toJSON(obj);
			pw.print(strjson);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			pw.flush();
			pw.close();
		}
	}
	
	
}
