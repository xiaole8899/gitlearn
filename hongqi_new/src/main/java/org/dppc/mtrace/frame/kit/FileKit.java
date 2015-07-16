package org.dppc.mtrace.frame.kit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.apache.commons.lang.StringUtils;


public class FileKit {
	
	
	/**
	 * 创建目录
	 */
	 public static void createDirectory(String path) throws Exception {  
	        if (StringUtils.isEmpty(path)) {  
	            return;  
	        }  
	        try {  
	            // 获得文件对象  
	            File f = new File(path);  
	            if (!f.exists()) {  
	                // 如果路径不存在,则创建  
	                f.mkdirs();  
	            }  
	        } catch (Exception e) {  
	            throw e;  
	        }  
	    } 
	 
	/** 
	* 创建文件 
	* 
	* @throws IOException 
	*/ 
	public static boolean creatTxtFile(String name) throws IOException { 
	boolean flag = false; 
	File filename = new File(name); 
	if (!filename.exists()) { 
	//filename.mkdirs();
	filename.createNewFile(); 
	flag = true; 
	} 
	return flag; 
	} 
	
	
	/** 
	* 写文件 
	* 
	* @param newStr 
	*            新内容 
	* @throws IOException 
	*/ 
	@SuppressWarnings("unused")
	public static boolean writeTxtFile (String newStr,String filenameTemp) 
	throws IOException { 
	// 先读取原有文件内容，然后进行写入操作 
	boolean flag = false; 
	String filein = newStr + "\r\n"; 
	String temp = ""; 

	FileInputStream fis = null; 
	InputStreamReader isr = null; 
	BufferedReader br = null; 

	FileOutputStream fos = null; 
	PrintWriter pw = null; 
	try { 
	// 文件路径 
	File file = new File(filenameTemp); 
	// 将文件读入输入流 
	fis = new FileInputStream(file); 
	isr = new InputStreamReader(fis); 
	br = new BufferedReader(isr); 
	StringBuffer buf = new StringBuffer(); 

	// 保存该文件原有的内容 
	for (int j = 1; (temp = br.readLine()) != null; j++) { 
	buf = buf.append(temp); 
	// System.getProperty("line.separator") 
	// 行与行之间的分隔符 相当于“\n” 
	buf = buf.append(System.getProperty("line.separator")); 
	} 
	buf.append(filein); 

	fos = new FileOutputStream(file); 
	pw = new PrintWriter(fos); 
	pw.write(buf.toString().toCharArray()); 
	pw.flush(); 
	flag = true; 
	} catch (IOException e1) { 
	// TODO 自动生成 catch 块 
	throw e1; 
	} finally { 
	if (pw != null) { 
	pw.close(); 
	} 
	if (fos != null) { 
	fos.close(); 
	} 
	if (br != null) { 
	br.close(); 
	} 
	if (isr != null) { 
	isr.close(); 
	} 
	if (fis != null) { 
	fis.close(); 
	} 
	} 
	return flag; 
	} 

	
	public static void main(String[] args) throws IOException {
		File file =new File("d:\\bbhh\\ttt");
		if(!file.exists()){
			file.mkdirs();
		}
		
		File xcwj=new File(file.getAbsoluteFile()+"\\aa.txt");
		if(!xcwj.exists()){
			xcwj.createNewFile();
		}
	}
	
}
