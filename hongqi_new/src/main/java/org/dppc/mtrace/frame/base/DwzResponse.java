package org.dppc.mtrace.frame.base;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Dwz Ajax表单提交后服务器端所作出的响应对象（需转为Json）
 * 
 * @author maomh
 */
public class DwzResponse implements Serializable {
	private static final long serialVersionUID = 2534681006275756667L;
	
	public static final String SC_OK ="200";
	public static final String SC_ERROR ="300";
	public static final String SC_TIMEOUT ="301";
	
	public static final String CT_FORWARD ="forward";
	public static final String CT_CLOSE ="closeCurrent";
	
	
	// 默认 响应成功
	private String statusCode =SC_OK;
	@JsonInclude(Include.NON_EMPTY)
	private String message;
	@JsonInclude(Include.NON_EMPTY)
	private String navTabId;
	@JsonInclude(Include.NON_EMPTY)
	private String rel;
	private String callbackType;
	@JsonInclude(Include.NON_EMPTY)
	private String forwardUrl;
	
	public DwzResponse() {
		
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNavTabId() {
		return navTabId;
	}

	public void setNavTabId(String navTabId) {
		this.navTabId = navTabId;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getCallbackType() {
		return callbackType;
	}

	public void setCallbackType(String callbackType) {
		this.callbackType = callbackType;
	}

	public String getForwardUrl() {
		return forwardUrl;
	}

	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}
}
