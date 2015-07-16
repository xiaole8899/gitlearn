package org.dppc.mtrace.frame.patch;

import java.nio.charset.Charset;

import org.dppc.mtrace.frame.annotation.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 注解 JsonResponse 的处理器补丁 <br/><br/>
 * 
 * 1）防止IE 不接受 application/json 类型<br/>
 * 2）自定义 mediaType 和 charset，保证响应流字符编码与charset一致
 * 
 * @author maomh
 *
 */
@Order // 默认优先级最低
@ControllerAdvice
@Component
public class JsonResponsePatch implements ResponseBodyAdvice<Object> {
	private Logger logger =LoggerFactory.getLogger(getClass());

	@Override
	public boolean supports(MethodParameter returnType,
			Class<? extends HttpMessageConverter<?>> converterType) {
		return returnType.getMethod().isAnnotationPresent(JsonResponse.class);
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType,
			MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType,
			ServerHttpRequest request, ServerHttpResponse response) {
		JsonResponse jsonAnno =returnType.getMethod().getAnnotation(JsonResponse.class);
		// 响应 可以自定义 JsonType
		MediaType mt =new MediaType(jsonAnno.type(), jsonAnno.subtype(), Charset.forName(jsonAnno.charset()));
		response.getHeaders().setContentType(mt);
		if (logger.isDebugEnabled()) {
			logger.debug(response.getClass().getName());
			logger.debug("已将 @JsonResponse " +returnType.getMethod().getName() +"() 响应的ContentType转为 " +mt.toString());
		}
		return body;
	}
}
