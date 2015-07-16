package org.dppc.mtrace.app.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.dppc.mtrace.app.AppConstant;
import org.dppc.mtrace.app.approach.entity.SupplyMarketEntity;
import org.dppc.mtrace.app.dict.entity.CountyEntity;
import org.dppc.mtrace.app.dict.service.CountyService;
import org.dppc.mtrace.app.role.entity.RoleEntity;
import org.dppc.mtrace.app.role.service.RoleService;
import org.dppc.mtrace.app.user.entity.UserEntity;
import org.dppc.mtrace.app.user.service.UserService;
import org.dppc.mtrace.frame.annotation.OperationLog;
import org.dppc.mtrace.frame.base.DwzResponse;
import org.dppc.mtrace.frame.base.Md5;
import org.dppc.mtrace.frame.base.OrderCondition;
import org.dppc.mtrace.frame.base.Page;
import org.dppc.mtrace.frame.kit.JsonKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户管理
 * 
 * @author sunlong
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private CountyService countyService;
	/**
	 * 查询用户
	 * 
	 * @author sunlong
	 */
	@RequestMapping("/list")
	@OperationLog(value="查询用户")
	public String userList(UserEntity user,HttpServletRequest request,HttpServletResponse response) {
		
		Page<UserEntity> page=new Page<UserEntity>();
		String pageIndex=request.getParameter("pageNum");
		if(StringUtils.isEmpty(pageIndex)){
			page.setPageIndex(1);
		}else{
			page.setPageIndex(Integer.parseInt(pageIndex));
		}
		String pageSize=request.getParameter("numPerPage");
		if(StringUtils.isEmpty(pageSize)){
			page.setPageSize(20);
		}else{
			page.setPageSize(Integer.parseInt(pageSize));
		}
		String filed=request.getParameter("orderField");
		String direction=request.getParameter("orderDirection");
		if(StringUtils.isEmpty(direction)){
			direction="asc";
		}
		OrderCondition order=new OrderCondition(filed,direction);
		request.setAttribute("order",order);
		userService.selectUserList(page,order,user,AppConstant.getUserEntity(request).getUserName());
		request.setAttribute("user", user);
		request.setAttribute("page",page);
		return "user/list";
		
	}
	
	/**
	 * 用户添加跳转
	 * 
	 * @author sunlong
	 */
	@RequestMapping("/toAdd")
	public String toAdd() {
		
		return "user/add";
	}
	
	/**
	 * 用户添加
	 * 
	 * @author sunlong
	 * @throws IOException 
	 */
	@RequestMapping("/doAdd")
	@OperationLog(value="添加用户")
	public void doAdd(HttpServletRequest request,HttpServletResponse response,UserEntity user) throws IOException {
		DwzResponse dwzResponse=new DwzResponse();
		user.setPassWord(Md5.getMD5(user.getPassWord()));
		int flag = userService.userAdd(user);
		if(flag==2){
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("用户名重复!");
		}else if(flag==0){
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("操作失败!");
		}else{
			dwzResponse.setStatusCode(DwzResponse.SC_OK);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("操作成功!");
		}
		PrintWriter pw=response.getWriter();
		String strJson=JsonKit.toJSON(dwzResponse);
		pw.write(strJson);
		pw.flush();
		pw.close();
	}
	
	/**
	 * 用户删除
	 * 
	 * @author sunlong
	 * @throws IOException 
	 * 
	 */
	@RequestMapping("/delete")
	@OperationLog(value="删除用户")
	public void delete(HttpServletRequest request,HttpServletResponse response,UserEntity user) throws IOException {
		DwzResponse dwzResponse=new DwzResponse();
		if(userService.delete(user)){
			dwzResponse.setStatusCode(DwzResponse.SC_OK);
			dwzResponse.setCallbackType(DwzResponse.CT_FORWARD);
			dwzResponse.setForwardUrl("user/list.do");
			dwzResponse.setMessage("操作成功!");
		}else{
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("操作失败!");
		}
		PrintWriter pw=response.getWriter();
		String strJson=JsonKit.toJSON(dwzResponse);
		pw.write(strJson);
		pw.flush();
		pw.close();
	}
	
	/**
	 * 
	 * 用户修改跳转
	 * 
	 * @author SunLong
	 */
	@RequestMapping("/toEdit")
	public String toEdit(HttpServletRequest request,HttpServletResponse response,UserEntity user) {
		UserEntity userEntity = userService.toEdit(user);
		request.setAttribute("user", userEntity);
		return "user/edit";
	}
	
	/**
	 * 用户修改
	 * 
	 * @author SunLong
	 * @throws IOException 
	 */
	@RequestMapping("/doEdit")
	@ResponseBody
	@OperationLog(value="修改用户")
	public DwzResponse doEdit(HttpServletRequest request,HttpServletResponse response,UserEntity user) throws IOException {
		DwzResponse dwzResponse=new DwzResponse();
		String bz = request.getParameter("bz");
		String srcpwd =request.getParameter("passWord1");
		//没改密码
		if(bz.equals("false")){
			user.setPassWord(srcpwd);
		} else {
			user.setPassWord(Md5.getMD5(user.getPassWord()));
		}
		if(userService.doEdit(user)){
			dwzResponse.setStatusCode(DwzResponse.SC_OK);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("操作成功!");
		}else{
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("操作失败!");
		}
		return dwzResponse;
	}
	
	/**
	 * 添加角色跳转
	 * 
	 * @author SunLong
	 * @throws IOException 
	 */
	@RequestMapping("/roleAdd")
	public String roleAdd(HttpServletRequest request,HttpServletResponse response,UserEntity user) throws IOException {
		List<RoleEntity> roleall = roleService.selectRoleAll(AppConstant.getUserEntity(request));
		//已分配角色
		Set<RoleEntity> roleyet = userService.selectById(user).getRoles();
		request.setAttribute("userId", user.getUserId());
		request.setAttribute("roleall",roleall);
		request.setAttribute("roleyet",roleyet);
		return "user/roleAdd";
	}
	
	/**
	 * 添加角色
	 * 
	 * @author sunlong
	 * @throws IOException 
	 */
	@RequestMapping("/doRoleAdd")
	@OperationLog(value="添加角色")
	public void doRoleAdd(HttpServletRequest request,HttpServletResponse response,UserEntity user) throws IOException {
		DwzResponse dwzResponse=new DwzResponse();
		String[] roleids = request.getParameterValues("roleIds");
		if(userService.doRoleAdd(user,roleids)){
			dwzResponse.setStatusCode(DwzResponse.SC_OK);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("操作成功!");
		}else{
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("操作失败!");
		}
		PrintWriter pw=response.getWriter();
		String strJson=JsonKit.toJSON(dwzResponse);
		pw.write(strJson);
		pw.flush();
		pw.close();
		
	}
	
	/**
	 * 重置密码
	 * 
	 * @author SunLong
	 * 
	 */
	@RequestMapping("/resPassWord")
	public String resPwd(HttpServletRequest request,HttpServletResponse response) throws IOException {
		return "user/respwd";
	}
	
	/**
	 * 重置密码
	 * 
	 * @author SunLong
	 * @throws IOException 
	 * 
	 */
	@RequestMapping("/doResPwd")
	@OperationLog(value="重置密码")
	public void doResPwd(HttpServletRequest request,HttpServletResponse response) throws IOException{
		DwzResponse dwzResponse=new DwzResponse();
		HttpSession session = request.getSession();
		UserEntity user = (UserEntity) session.getAttribute("USER_SESSION_KEY");
		UserEntity userEntity = userService.selectById(user);
		String oldPwd = Md5.getMD5(request.getParameter("oldPassWord"));
		if(!(oldPwd.equals(userEntity.getPassWord()))){
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("原密码不正确!");
		}else{
			String newPwd = Md5.getMD5(request.getParameter("newPassWord"));
			if(userService.doResPwd(user,newPwd)){
				dwzResponse.setStatusCode(DwzResponse.SC_OK);
				dwzResponse.setCallbackType(DwzResponse.CT_FORWARD);
				dwzResponse.setForwardUrl("user/resPassWord.do");
				dwzResponse.setMessage("操作成功!");
			}else{
				dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
				dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
				dwzResponse.setMessage("操作失败!");
			}
		}
		PrintWriter pw=response.getWriter();
		String strJson=JsonKit.toJSON(dwzResponse);
		pw.write(strJson);
		pw.flush();
		pw.close();
	}
	
	/**
	 * 
	 * 个人信息修改跳转
	 * 
	 * @author SunLong
	 */
	@RequestMapping("/toPersonalEdit")
	public String toPersonalEdit(HttpServletRequest request,HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserEntity user = (UserEntity) session.getAttribute("USER_SESSION_KEY");
		UserEntity userEntity = userService.selectById(user);
		request.setAttribute("user", userEntity);
		return "user/personalEdit";
	}
	
	/**
	 * 个人信息修改
	 * 
	 * @author SunLong
	 * @throws IOException 
	 */
	@RequestMapping("/doPersonalEdit")
	@OperationLog(value="个人信息修改")
	public void doPersonalEdit(HttpServletRequest request,HttpServletResponse response,UserEntity user) throws IOException {
		DwzResponse dwzResponse=new DwzResponse();
		if(userService.doPersonalEdit(user)){
			dwzResponse.setStatusCode(DwzResponse.SC_OK);
			dwzResponse.setCallbackType(DwzResponse.CT_FORWARD);
			dwzResponse.setForwardUrl("user/toPersonalEdit.do");
			dwzResponse.setMessage("操作成功!");
		}else{
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("操作失败!");
		}
		PrintWriter pw=response.getWriter();
		String strJson=JsonKit.toJSON(dwzResponse);
		pw.write(strJson);
		pw.flush();
		pw.close();
	}
	
	/**
	 * 
	 * 展示本节点
	 * 
	 * @author SunLong
	 */
	@RequestMapping("/showNode")
	@OperationLog(value="查看节点信息")
	public String showNode(HttpServletRequest request,HttpServletResponse response) {
		Map<String,String> supplyType=AppConstant.supplyType;
		SupplyMarketEntity sm = userService.selectNode();
		request.setAttribute("sm", sm);
		request.setAttribute("supplyType", supplyType);
		return "user/showNode";
	}
	
	/**
	 * 修改节点跳转
	 *
	 * @author sunlong
	 */
	@RequestMapping(value="toUpdateShowNode")
	public String toUpdateShowNode(HttpServletRequest request ,HttpServletResponse response){
		//SupplyMarketEntity sm = userService.selectNode();
		SupplyMarketEntity suMarket = userService.selectNode();
		
		List<CountyEntity> listArea = countyService.findCountybyPrId("0");
        String areaOriginName = suMarket.getAreaName();
        String[] areas = areaOriginName.split("-");
        String province = "所有省市";
        String city = "所有城市";
        String street = "所有区县";
        switch( areas.length){
        case 3:{province = areas[0]; city = areas[1]; street = areas[2];}
        case 2:{province = areas[0]; city = areas[1];}
        case 1:{province = areas[0]; }
        }
        request.setAttribute("province", province);
        request.setAttribute("city", city);
        request.setAttribute("street", street);
        request.setAttribute("listArea", listArea);
		request.setAttribute("suMarket", suMarket);
		request.setAttribute("supplyType", AppConstant.supplyType);
		return "user/nodeInfoEdit";
	}
	
	/**
	 * 修改节点信息
	 * 
	 *@author sunlong
	 */
	@RequestMapping(value="doEditShowNode")
	@ResponseBody
	@OperationLog(value="修改节点")
	public DwzResponse doEditShowNode(SupplyMarketEntity suMarket,HttpServletRequest request ,HttpServletResponse response){
		Date now = new Date();
		suMarket.setRecordDate(now);
		//默认设置为需要采集并同步信息
		suMarket.setIsModified("1");
		String province =  request.getParameter("province");
		String city   = request.getParameter("city");
		String street = request.getParameter("street");
		StringBuilder originName = new StringBuilder();
		if(!"all".equals(province)){
			originName.append(province.split(",")[1]).append("-");
		}
		if(!"all".equals(city)){
			originName.append(city.split(",")[1]).append("-");
		}
		
		if(!"all".equals(street)){
			originName.append(street.split(",")[1]);
		}
		//----地区控制start
		if(!"all".equals(street)){       //省份
			suMarket.setAreaNo(street.split(",")[0]);
		}else {
			if(!"all".equals(city)){                    //城市
				suMarket.setAreaNo(city.split(",")[0]);
			}else{
				if(!"all".equals(province)){             //地区
					suMarket.setAreaNo(province.split(",")[0]);
				}
			}
		}
	//---地区控制end
		suMarket.setAreaName(originName.toString());//给地区赋值
		DwzResponse dwzResponse = new DwzResponse();
		if(userService.doEditShowNode(suMarket)){
			request.getServletContext().setAttribute("NODE_INFO", suMarket);
			dwzResponse.setStatusCode(DwzResponse.SC_OK);
			dwzResponse.setCallbackType(DwzResponse.CT_FORWARD);
			dwzResponse.setForwardUrl("user/showNode.do");
			dwzResponse.setMessage("节点信息修改成功!");
		}else{
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("节点信息修改失败");
		}
		return dwzResponse;
	}

}
