package org.dppc.mtrace.frame.kit;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class ResponseKit {
	/**
	 * 将字符串str用Writer.println()写入响应流 并进行flush操作
	 * 
	 * @param response
	 * @param strings
	 * @throws IOException
	 */
	public static void printlnAndFlush(HttpServletResponse response, String...strings) throws IOException {
		PrintWriter pw =response.getWriter();
		if (ArrayKit.isNotEmpty(strings)) {
			for (String str : strings) {
				pw.println(str);
			}
			pw.flush();
		}
	}
	
	
	/**
	 * 将字节流input利用OutputStream.write 写入响应流并进行flush操作
	 * 
	 * @param response
	 * @param input
	 * @throws IOException
	 */
	public static void wirteAndFlush(HttpServletResponse response, InputStream input) throws IOException {
		OutputStream output =response.getOutputStream();
		if (input != null) {
			byte[] buffers =new byte[256];
			int count =0;
			// 这里没有采用StreamKit的copy方法 因为StreamKit里面主要是大块儿（4096）
			while (StreamKit.EOF != (count =input.read(buffers))) {
				output.write(buffers, 0, count);
			}
			output.flush();
		}
	}
	
	
	/**
	 * 将对象转为Json字符串并写入响应
	 * 
	 * @param response
	 * @param obj
	 * @param mediaType 默认是 text/html
	 * @param charset 默认是 UTF-8
	 * @throws IOException 
	 */
	public static void printObjectToJson(HttpServletResponse response, Object obj, String mediaType, String charset) throws IOException {
		if (StringKit.isEmpty(mediaType)) {
			mediaType ="text/html";
		}
		if (StringKit.isEmpty(charset)) {
			charset ="UTF-8";
		}
		response.setContentType(mediaType +";" +charset);
		response.setCharacterEncoding(charset);
		
		// 清除缓存
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		
		String json =JsonKit.toJSON(obj);
		printlnAndFlush(response, json);
	}
	
	
	/**
	 * 将对象转为Json字符串并写入响应, 默认contentType是 text/html;charset=UTF-8
	 * 
	 * @param response
	 * @param obj
	 * @throws IOException
	 */
	public static void printObjectToJson(HttpServletResponse response, Object obj) throws IOException {
		printObjectToJson(response, obj, null, null);
	}
	
}
