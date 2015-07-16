package org.dppc.mtrace.app.menu.service;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.dppc.mtrace.app.menu.entity.FunctionEntity;
import org.dppc.mtrace.app.menu.entity.MenuEntity;
import org.dppc.mtrace.app.role.entity.RoleEntity;
import org.dppc.mtrace.app.user.entity.UserEntity;
import org.dppc.mtrace.frame.base.OrderCondition;
import org.dppc.mtrace.frame.base.Page;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuService {
	
	@Autowired
	SessionFactory sessionFactory;
	
	/**************************************主菜单begin***********************************************/
	/**
	 * 查询菜单
	 * 
	 * @author sunlong
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public void selectPrimaryList(Page<MenuEntity> page,OrderCondition order,MenuEntity menu) {
		Session session=sessionFactory.getCurrentSession();
		StringBuffer hql=new StringBuffer();
		hql.append("select menu from MenuEntity menu where menu.parentId=0 ");
		if(StringUtils.isNotEmpty(menu.getMenuName())){
			hql.append("and menu.menuName like '%"+menu.getMenuName()+'%'+"'");
		}
		hql.append(order.toSql());
		Query queryCount=session.createQuery(hql.toString());	
		page.setTotalRows(queryCount.list().size());
		Query query=session.createQuery(hql.toString());
		query.setMaxResults(page.getPageSize());
		query.setFirstResult((page.getPageIndex()-1)*page.getPageSize());
		List<MenuEntity> menuList=query.list();
		page.setRows(menuList);	
	}
	
	/**
	 * 主菜单添加跳转（查询最大排序值）
	 * @author SunLong
	 * return sort
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public int toPrimaryAdd(int parentId){
		String hql = "select max(menu.sort) from MenuEntity menu where menu.parentId = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, parentId);
		int sort = 0;
		List<Object> list = query.list();
		if(!(list.get(0)==null)){
			sort = Integer.parseInt(query.list().get(0).toString());
		}
		return sort+1;
	}
	
	/**
	 * 添加菜单
	 * 
	 * @author sunlong
	 */
	@Transactional
	public boolean doPrimaryAdd(MenuEntity menu){
		sessionFactory.getCurrentSession().save(menu);
		return true;
	}
	
	/**
	 * 删除主菜单
	 * 
	 * @author sunlong
	 */
	@Transactional
	public boolean deletePrimary(MenuEntity menu) {		
		
		//先查看菜单下是否有子菜单
		List<MenuEntity> menuChlidList=menu.getChildList();
		//若有先删除子菜单下的功能
		if(menu.getChildList()!=null){
			for(MenuEntity m:menuChlidList){
				Set<FunctionEntity> functions=m.getFunction();
				if(functions!=null){
					for(FunctionEntity f:functions){
						//f.setMenu(null);
						deleteFunctionByFId(f.getFunctionId());
					}
				}
			}
		}
		//继续删除菜单
		String hql = "delete from MenuEntity menu where menu.menuId = ? or menu.parentId = ?";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, menu.getMenuId());
		query.setParameter(1, menu.getMenuId());
		query.executeUpdate();
		return true;
	}
	
	/**
	 * 修改菜单跳转（根据id查询菜单）
	 * 
	 * @author sunlong
	 */
	@Transactional
	public MenuEntity toEdit(MenuEntity menu) {
		MenuEntity menuEntity = (MenuEntity) sessionFactory.getCurrentSession().get(MenuEntity.class,menu.getMenuId());	
		return menuEntity;
	}
	
	/**
	 * 修改菜单
	 * 
	 * @author sunlong
	 */
	@Transactional
	public boolean doPrimaryEdit(MenuEntity menu) {
		sessionFactory.getCurrentSession().update(menu);
		return true;
	}
	
	/**************************************主菜单end***********************************************/
	
	/**************************************子菜单begin***********************************************/
	
	/**
	 * 查询子菜单
	 * 
	 * @author sunlong
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public void selectChildList(String menuId,Page<MenuEntity> page,OrderCondition order,String menuName) {
		Session session=sessionFactory.getCurrentSession();
		StringBuffer hql=new StringBuffer();
		hql.append("select menu from MenuEntity menu where menu.parentId='"+menuId+"'");
		if(!(menuName==null||menuName.equals(""))){
			hql.append(" and menu.menuName like '%"+menuName+'%'+"'");
		}
		hql.append(order.toSql());
		Query queryCount=session.createQuery(hql.toString());	
		page.setTotalRows(queryCount.list().size());
		Query query=session.createQuery(hql.toString());
		query.setMaxResults(page.getPageSize());
		query.setFirstResult((page.getPageIndex()-1)*page.getPageSize());
		List<MenuEntity> menuList=query.list();
		page.setRows(menuList);	
	}
	
	/**
	 * 删除子菜单
	 * 
	 * @author sunlong
	 */
	@Transactional
	public boolean deleteChild(MenuEntity menu) {
		//先删除功能
		Set<FunctionEntity> functions=menu.getFunction();
		if(functions!=null){
			for(FunctionEntity f:functions){
				deleteFunctionByFId(f.getFunctionId());
			}
		}
		//继续删除菜单
		String hql = "delete from MenuEntity menu where menu.menuId = ? or menu.parentId = ?";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, menu.getMenuId());
		query.setParameter(1, menu.getMenuId());
		query.executeUpdate();
		return true;
	}
	
	/**************************************子菜单end***********************************************/
	
	/**************************************子菜单功能begin***********************************************/
	
	/**
	 * 查询功能
	 * 
	 * @author sunlong
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public void selectFunctionList(String menuId,Page<FunctionEntity> page,OrderCondition order,FunctionEntity function) {
		//获取session 
		Session session=sessionFactory.getCurrentSession();
		//hql语句
		StringBuffer hql=new StringBuffer();
		//获取个数
		hql.append("select function from FunctionEntity function where function.menu.menuId='"+menuId+"'");
		if(StringUtils.isNotEmpty(function.getFunctionName())){
			hql.append(" and function.functionName like '%"+function.getFunctionName()+'%'+"'");
		}
		hql.append(order.toSql());
		Query queryCount=session.createQuery(hql.toString());	
		page.setTotalRows(queryCount.list().size());
		Query query=session.createQuery(hql.toString());
		query.setMaxResults(page.getPageSize());
		query.setFirstResult((page.getPageIndex()-1)*page.getPageSize());
		List<FunctionEntity> functionList=query.list();
		page.setRows(functionList);	
	}
	
	/**
	 * 添加功能
	 * 
	 * @author sunlong
	 */
	@Transactional
	public int doAddFunction(FunctionEntity function,int menuId){
		MenuEntity menuEntity = (MenuEntity) sessionFactory.getCurrentSession().get(MenuEntity.class,menuId);
		function.setMenu(menuEntity);
		sessionFactory.getCurrentSession().save(function);
		return 1;
	}
	
	/**
	 * 删除功能
	 * 
	 * @author sunlong
	 */
	@Transactional
	public boolean deleteFunction(FunctionEntity function) {
		FunctionEntity functionEntity = (FunctionEntity)sessionFactory.getCurrentSession().get(FunctionEntity.class, function.getFunctionId());
		sessionFactory.getCurrentSession().delete(functionEntity);
		return true;
	}
	
	
	/**
	 * 根据编号删除功能
	 * 
	 * @param fId  功能编号
	 */
	@Transactional
	public boolean deleteFunctionByFId(int fId) {
		String hql="delete from FunctionEntity f where f.functionId=:fid";
		Query query =sessionFactory.getCurrentSession().createQuery(hql).setParameter("fid",fId);
		query.executeUpdate();
		return true;
	}
	
	/**
	 * 修改功能跳转（根据id查询功能）
	 * 
	 * @author sunlong
	 */
	@Transactional
	public FunctionEntity toFunctionEdit(FunctionEntity function) {
		FunctionEntity functionEntity = (FunctionEntity) sessionFactory.getCurrentSession().get(FunctionEntity.class,function.getFunctionId());	
		return functionEntity;
	}
	
	/**
	 * 修改功能
	 * 
	 * @author sunlong
	 */
	@Transactional
	public boolean doEditFunction(FunctionEntity function,String menuId) {
		MenuEntity menuEntity = (MenuEntity) sessionFactory.getCurrentSession().get(MenuEntity.class,Integer.parseInt(menuId));
		function.setMenu(menuEntity);
		sessionFactory.getCurrentSession().saveOrUpdate(function);
		return true;
	}
	
	/**************************************子菜单功能end***********************************************/
	
	/****************************************公共模块功能引用begin*********************************************/
	
	/**
	 * 
	 * 查询菜单
	 * 
	 * @author SunLong
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public List<MenuEntity> selectMainMenu(UserEntity user,String menuId){
		String hql = "select menu from MenuEntity menu left join fetch menu.role r where r.roleId in(";
		if(user!=null && user.getRoles()!=null){
			//标示是否时开发人员
			boolean flag=true;
			for(RoleEntity r:user.getRoles()){
				//说明时系统管理员拥有所有的权限
				if(user.getUserName().equals("super-admin")){
					flag=false;
					hql="";
					hql+="select menu from MenuEntity menu where 1=1";
					break;
				}
				hql+=r.getRoleId()+",";
			}
			if(flag){
				hql+="0)";
			}
		}
		if(StringUtils.isNotEmpty(menuId)){
			hql+=" and menu.parentId="+menuId;
		}
		List<MenuEntity> list = sessionFactory.getCurrentSession().createQuery(hql).list();
		return list;
	}
	
	/**
	 * 
	 * 查询子菜单
	 * 
	 * @author SunLong
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public List<MenuEntity> selectChildMenu(int menuId){
		String hql = "select menu from MenuEntity menu where menu.parentId = ?";		
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, menuId);
		List<MenuEntity> list = query.list();
		return list;
	}
	
	/**
	 * 根据菜单编号获取菜单名称
	 * @param menuId
	 * @return
	 */
	@Transactional
	public MenuEntity getMenuByMenuId(Integer menuId){
		String hql="from MenuEntity menu left join fetch menu.function where menu.menuId=:menuId";
		Query query =sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("menuId",menuId);
		@SuppressWarnings("unchecked")
		List<MenuEntity> menus=query.list();
		if(menus!=null){
			MenuEntity menu=menus.get(0);
			menu.setChildList(selectChildMenu(menuId));
			return menu;
		}
		return null;
	}
	
	
	
	/****************************************公共模块功能引用end*********************************************/
}
