package org.dppc.mtrace.frame.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.web.bind.annotation.ResponseBody;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ResponseBody
public @interface JsonResponse {
	
	/**
	 * IE 不支持 application/json 会提示下载 Fuck！！！<br/>
	 * 所以这里默认统一采用 "text/html" 
	 * @return
	 */
	String type() default "text";
	String subtype() default "html";
	String charset() default "UTF-8";
}
