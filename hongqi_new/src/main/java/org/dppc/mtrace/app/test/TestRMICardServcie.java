package org.dppc.mtrace.app.test;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dppc.cardws.app.rmi.RMICardService;
import org.dppc.cardws.frame.card.exp.CardException;
import org.dppc.mtrace.frame.kit.ResponseKit;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 临时测试用的 servlet - 测试RMICardService
 * 
 * @author maomh
 *
 */
/*@WebServlet(
	name="testrmicardservice",
	urlPatterns="/test/rmicardservice"
)*/
public class TestRMICardServcie extends HttpServlet {

	private static final long serialVersionUID = 2151543175194443832L;
	@SuppressWarnings("unused")
	private ObjectMapper om =new ObjectMapper();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ServletContext sc =req.getServletContext();
		ApplicationContext ctx =WebApplicationContextUtils.getWebApplicationContext(sc);
		RMICardService rmicard =ctx.getBean(RMICardService.class);
		
		String result;
		
		try {
			result =rmicard.readUserInfo();
		} catch (CardException e) {
			result =e.getMessage();
		}
		
		resp.setCharacterEncoding("UTF-8");
		ResponseKit.printlnAndFlush(resp, result);
	}

}
