package org.dppc.mtrace.app.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dppc.mtrace.app.AppConstant;

import com.github.cage.Cage;
import com.github.cage.GCage;

/**
 * 验证码生成  Servlet
 * 
 * @author zhangkedong
 *
 */
@WebServlet(name="yzmServlet",urlPatterns="/yzmServlet")
public class YzmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Cage cage=new GCage();

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public YzmServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
   @Override
   public void service(HttpServletRequest  request, HttpServletResponse response)
		throws ServletException, IOException {
		    response.setContentType("image/" + cage.getFormat());
		    response.setHeader("Cache-Control", "no-cache, no-store");
		    response.setHeader("Pragma", "no-cache");
		    
		    long time = System.currentTimeMillis();
		    response.setDateHeader("Last-Modified", time);
		    response.setDateHeader("Date", time);
		    response.setDateHeader("Expires", time);
		    
		    // 生成验证码字符串
		    String token = cage.getTokenGenerator().next();
		    // 验证码位数
		    token=token.substring(0, 4);
		    request.getSession().setAttribute(AppConstant.SESSION_YZM_TOKEN, token);
		    
		    // 根据验证码 生成验证码图片 写入返回流
		    cage.draw(token, response.getOutputStream());
	}
  
}
