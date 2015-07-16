package org.dppc.mtrace.app.systeminitialize;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dppc.mtrace.app.systeminitialize.service.InitializeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/initialize")
public class InitializeController{
	
	/**
	 * 
	 * 系统重置
	 * 
	 * @author SunLong
	 */
	
	@Autowired
	InitializeService initializeService;
	
	/**
	 * 导出跳转
	 * 
	 * @author SunLong
	 */
	@RequestMapping("/toExport")
	public String toExport(){
		return "initialize/export";
	}
	
	@RequestMapping("/doExport")
	public void doExport(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Document document = initializeService.doExport();
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");//根据需要设置编码
		XMLWriter writer = new XMLWriter(response.getOutputStream(), format);
		response.setContentType("text/xml");
		response.addHeader("Content-Disposition","attachment;filename=system.xml");
		writer.write(document);
	}
	
	/**
	 * 导入
	 * 
	 * @author SunLong
	 */
	@RequestMapping("/toImport")
	public String toImple(){
		return "initialize/import";
	}
	
	@RequestMapping("/doImport")
	public void doImple(HttpServletRequest request,HttpServletResponse response) throws IOException, DocumentException, ParseException, FileUploadException{
		
		FileItemFactory fileItemFactory =new DiskFileItemFactory();
		ServletFileUpload fileUpload =new ServletFileUpload(fileItemFactory);
		InputStream xmlStream =null;
		List<FileItem> items =fileUpload.parseRequest(request);
		for (FileItem item : items) {
			if (item.isFormField()) {
				continue;
			} else {
				xmlStream =item.getInputStream();
			}
		}
		
		if (xmlStream == null) {
			throw new NullPointerException("无法打开上传文件的文件流！");
		}
		SAXReader saxReader=new SAXReader();
		Document doc=saxReader.read(xmlStream);
		initializeService.doImport(doc);
		
		response.setContentType("text/html;utf-8");
		response.getWriter().print("操作成功!");
	}
	
}