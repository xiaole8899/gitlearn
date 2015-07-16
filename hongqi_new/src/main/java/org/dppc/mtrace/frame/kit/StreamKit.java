package org.dppc.mtrace.frame.kit;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

import org.apache.commons.lang.StringUtils;

/**
 * 流工具, 推荐采用 IOUtils等 工具类
 * 
 * @author maomh
 */
public class StreamKit {
	public static int EOF =-1;
	public static int DEFAULT_BUFFER_SIZE =4096;
	
	public static String LINE_SEPARATOR =System.getProperty("line.separator", "\n");
	
	/*static { // 为LINE_SEPARATOR 赋值
		StringBuilderWriter buf =new StringBuilderWriter();
		PrintWriter pw =new PrintWriter(buf);
		pw.println();
		LINE_SEPARATOR =buf.toString();
		pw.close();
	}*/
	
	/**
	 * 赋值inputstream 到 outputstream
	 * 
	 * @param in
	 * @param out
	 * @return 拷贝的字节数
	 * @throws IOException
	 */
	public static int copy(InputStream in, OutputStream out) throws IOException {
		int count =0;
		byte[] buffer =new byte[DEFAULT_BUFFER_SIZE];
		int n =0;
		while (EOF !=(n =in.read(buffer))) {
			out.write(buffer, 0, n);
			count +=n;
		}
		return count;
	}
	
	
	/**
	 * 复制InputStream到一个Writer里
	 * 
	 * @param in
	 * @param w
	 * @param encoding 字符编码：GBK、UTF-8、ISO-8859-1 等
	 * @throws IOException
	 */
	public static void copy(InputStream in, Writer w, String encoding) throws IOException {
		Charset cs =null;
		if (StringUtils.isEmpty(encoding)) {
			cs =Charset.defaultCharset();
		} else {
			cs =Charset.forName(encoding);
		}
		InputStreamReader r =new InputStreamReader(in, cs);
		copy(r, w);
	}
	
	
	/**
	 * 复制Reader到一个Writer里
	 * 
	 * @param r
	 * @param out
	 * @param encoding
	 * @throws IOException
	 */
	public static void copy(Reader r, OutputStream out, String encoding) throws IOException {
		Charset cs =null;
		if (StringUtils.isEmpty(encoding)) {
			cs =Charset.defaultCharset();
		} else {
			cs =Charset.forName(encoding);
		}
		OutputStreamWriter w =new OutputStreamWriter(out, cs);
		copy(r, w);
		out.flush();
	}
	
	
	/**
	 * 复制Reader到Writer中
	 * 
	 * @param r
	 * @param w
	 * @return 拷贝的字符数
	 * @throws IOException
	 */
	public static int copy(Reader r, Writer w) throws IOException {
		int count =0;
		char[] buffer =new char[DEFAULT_BUFFER_SIZE];
		int n =0;
		while (EOF != (n =r.read(buffer))) {
			w.write(buffer, 0, n);
			count +=n;
		}
		return count;
	}
	
}
