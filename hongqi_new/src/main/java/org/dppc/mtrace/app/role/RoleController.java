package org.dppc.mtrace.app.role;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.dppc.mtrace.app.AppConstant;
import org.dppc.mtrace.app.menu.entity.MenuEntity;
import org.dppc.mtrace.app.menu.service.MenuService;
import org.dppc.mtrace.app.role.entity.RoleEntity;
import org.dppc.mtrace.app.role.service.RoleService;
import org.dppc.mtrace.frame.annotation.OperationLog;
import org.dppc.mtrace.frame.base.DwzResponse;
import org.dppc.mtrace.frame.base.OrderCondition;
import org.dppc.mtrace.frame.base.Page;
import org.dppc.mtrace.frame.kit.Assert;
import org.dppc.mtrace.frame.kit.JsonKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 角色管理
 * 
 * @author sunlong
 *
 */
@Controller
@RequestMapping("/role")
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private MenuService menuService;
	
	/**
	 * 查询角色
	 * 
	 * @author sunlong
	 */
	@RequestMapping("/list")
	@OperationLog(value="查询角色")
	public String RoleList(RoleEntity role,HttpServletRequest request,HttpServletResponse response) {
		Page<RoleEntity> page=new Page<RoleEntity>();
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
		roleService.selectRoleList(page,order,role,AppConstant.getUserEntity(request));
		request.setAttribute("role", role);
		request.setAttribute("page",page);
		return "role/list";
	}
	
	/**
	 * 角色添加跳转
	 * 
	 * @author sunlong
	 */
	@RequestMapping("/toAdd")
	public String toAdd() {
		
		return "role/add";
	}
	
	/**
	 * 角色添加
	 * 
	 * @author sunlong
	 * @throws IOException 
	 */
	@RequestMapping("/doAdd")
	@OperationLog(value="添加角色")
	public void doAdd(HttpServletRequest request,HttpServletResponse response,RoleEntity Role) throws IOException {
		DwzResponse dwzResponse=new DwzResponse();
		int flag = roleService.doAdd(Role);
		if(flag==2){
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("角色名重复!");
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
	 * 角色删除
	 * 
	 * @author sunlong
	 * @throws IOException 
	 * 
	 */
	@RequestMapping("/delete")
	@OperationLog(value="删除角色")
	public void delete(HttpServletRequest request,HttpServletResponse response,RoleEntity role) throws IOException {
		DwzResponse dwzResponse=new DwzResponse();
		if(roleService.delete(role)){
			dwzResponse.setStatusCode(DwzResponse.SC_OK);
			dwzResponse.setCallbackType(DwzResponse.CT_FORWARD);
			dwzResponse.setForwardUrl("role/list.do");
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
	 * 角色修改跳转
	 * 
	 * @author SunLong
	 */
	@RequestMapping("/toEdit")
	public String toEdit(HttpServletRequest request,HttpServletResponse response,RoleEntity Role) {
		RoleEntity roleEntity = roleService.toEdit(Role);
		request.setAttribute("role", roleEntity);
		return "role/edit";
	}
	
	/**
	 * 角色修改
	 * 
	 * @author SunLong
	 * @throws IOException 
	 */
	@RequestMapping("/doEdit")
	@OperationLog(value="修改角色")
	public void doEdit(HttpServletRequest request,HttpServletResponse response,RoleEntity role) throws IOException {
		DwzResponse dwzResponse=new DwzResponse();
		if(roleService.doEdit(role)){
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
	 * 分配权限之前加载数据
	 * @return
	 */
	@RequestMapping("/toAddPermission")
	public String toAermtoAddPermissionissions(HttpServletRequest request,HttpServletResponse response,RoleEntity role){
		//所有菜单及功能
		List<MenuEntity> menuList = menuService.selectMainMenu(AppConstant.getUserEntity(request),"0");
		//用于去除重复选项
		TreeSet<MenuEntity> menuSet=new TreeSet<MenuEntity>();
		menuSet.addAll(menuList);
		menuList.clear();
		menuList.addAll(menuSet);
		for(MenuEntity menu:menuList){
			menuSet=new TreeSet<MenuEntity>();
			List<MenuEntity> childMenuEntities=menuService.selectMainMenu(AppConstant.getUserEntity(request),String.valueOf(menu.getMenuId()));
			menuSet.addAll(childMenuEntities);
			childMenuEntities.clear();
			childMenuEntities.addAll(menuSet);
			menu.setChildList(childMenuEntities);
		}
		//已分配的菜单和功能
		RoleEntity roleYet = roleService.selectRoleById(role);
		Set<MenuEntity> menuYet = roleYet.getMenu();
		Assert.notNull(menuYet);
		/*for(MenuEntity menu:menuYet){
			System.out.println(menu.getMenuName());
		}*/
		request.setAttribute("roleId", role.getRoleId());
		request.setAttribute("menuYet", menuYet);
		request.setAttribute("menuList", menuList);
		return "role/permissionAdd";
	}
	
	/**
	 * 
	 * 分配权限
	 * 
	 * @author SunLong
	 * @throws IOException 
	 */
	@RequestMapping("/doPermissions")
	@OperationLog(value="角色分配权限")
	public void doPermissions(HttpServletRequest request,HttpServletResponse response,RoleEntity role) throws IOException {
		DwzResponse dwzResponse=new DwzResponse();
		String[] menuIds = request.getParameterValues("menuId");
		if(roleService.doPermissions(role,menuIds)){
			dwzResponse.setStatusCode(DwzResponse.SC_OK);
			dwzResponse.setCallbackType(DwzResponse.CT_CLOSE);
			dwzResponse.setMessage("操作成功！");
		}else{
			dwzResponse.setStatusCode(DwzResponse.SC_ERROR);
			dwzResponse.setMessage("操作失败！");
		}
		PrintWriter pw=response.getWriter();
		String strJson=JsonKit.toJSON(dwzResponse);
		pw.write(strJson);
		pw.flush();
		pw.close();
	}
}

