package org.dppc.mtrace.frame.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.dppc.mtrace.frame.kit.StringKit;

public class LoadResourceTag extends SimpleTagSupport {
	private static final String JS_PREFIX ="<script type=\"text/javascript\" src=\"";
	private static final String JS_SUFFIX ="\"></script>";
	private static final String CSS_PREFIX ="<link rel=\"stylesheet\" type=\"text/css\" href=\"";
	private static final String CSS_SUFFIX ="\"/>";
	
	
	private String path;
	private String media;
	private Boolean isCss =null;

	@Override
	public void doTag() throws JspException, IOException {
		if (isCss != null) {
			PageContext pc =(PageContext) this.getJspContext();
			String contextPath =pc.getServletContext().getContextPath();
			if (StringKit.equals(contextPath, "/") || path.startsWith("http://")) {
				contextPath =StringKit.EMPTY;
			} else {
				contextPath =contextPath +"/";
			}
			StringBuffer sb =new StringBuffer();
			if (isCss) {
				sb.append(CSS_PREFIX);
				sb.append(contextPath).append(path);
				if (StringKit.isNotEmpty(media)) {
					sb.append("\" media=\"").append(media);
				}
				sb.append(CSS_SUFFIX);
			} else {
				sb.append(JS_PREFIX);
				sb.append(contextPath).append(path);
				sb.append(JS_SUFFIX);
			}
			sb.append("\n");
			JspWriter writer =pc.getOut();
			writer.write(sb.toString());
			writer.flush();
		}
		super.doTag();
	}
	
	
	public void setPath(String path) {
		this.path =StringKit.trimToNull(path);
		if (this.path == null) {
			return;
		} 
		String PATH =this.path.toUpperCase();
		if (PATH.endsWith(".CSS")) {
			isCss =true;
		} else if (PATH.endsWith(".JS")) {
			isCss =false;
		}
	}
	
	
	public void setMedia(String media) {
		this.media =StringKit.trim(media);
	}
}
