package org.dppc.mtrace.app.systeminitialize.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dppc.mtrace.app.approach.entity.SupplyMarketEntity;
import org.dppc.mtrace.app.menu.entity.FunctionEntity;
import org.dppc.mtrace.app.menu.entity.MenuEntity;
import org.dppc.mtrace.app.role.entity.RoleEntity;
import org.dppc.mtrace.app.systeminitialize.until.AnalysisXml;
import org.dppc.mtrace.app.systeminitialize.until.CreateXml;
import org.dppc.mtrace.app.user.entity.UserEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InitializeService {
	
	@Autowired
	SessionFactory sessionFactory;
	
	/**
	 * 功能初始化
	 * 
	 * @author sunlong
	 */
	public List<FunctionEntity> getInitializeFunction(){
		FunctionEntity functionExport =  new FunctionEntity();
		functionExport.setUrl("/initialize/doExport.do");
		FunctionEntity functionImport =  new FunctionEntity();
		functionImport.setUrl("/initialize/doImport.do");
		List<FunctionEntity> list = new ArrayList<FunctionEntity>();
		list.add(functionExport);
		list.add(functionImport);
		return list;
	}
	
	/**
	 * 菜单初始化
	 * 
	 * @author sunlong
	 */
	public List<MenuEntity> getInitializeMenu(){
		MenuEntity menuEntity = new MenuEntity();
		//主菜单
		menuEntity.setMenuId(1);
		menuEntity.setParentId(0);
		menuEntity.setSort(1);
		menuEntity.setUrl("/");
		menuEntity.setMenuName("系统初始化");
		
		//子菜单1
		MenuEntity menuEntityChild1 = new MenuEntity();
		menuEntityChild1.setMenuId(-1);
		menuEntityChild1.setParentId(1);
		menuEntityChild1.setSort(1);
		menuEntityChild1.setUrl("/initialize/doExport.do");
		menuEntityChild1.setMenuName("导出");
		
		//子菜单2
		MenuEntity menuEntityChild2 = new MenuEntity();
		menuEntityChild2.setMenuId(-2);
		menuEntityChild2.setParentId(1);
		menuEntityChild2.setRel("initializetoimport");
		menuEntityChild2.setUrl("/initialize/toImport.do");
		menuEntityChild2.setSort(2);
		menuEntityChild2.setMenuName("导入");
		
		List<MenuEntity> menuList = new ArrayList<MenuEntity>();
		menuList.add(menuEntity);
		menuList.add(menuEntityChild1);
		menuList.add(menuEntityChild2);
		return menuList;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Document doExport(){
		// 用户表
		String userSql = "select user from UserEntity user";
		List<UserEntity> userList = sessionFactory.getCurrentSession().createQuery(userSql).list();
		// 角色表
		String roleSql = "select role from RoleEntity role";
		List<RoleEntity> roleList = sessionFactory.getCurrentSession().createQuery(roleSql).list();
		// 菜单表
		String menuSql = "select menu from MenuEntity menu";
		List<MenuEntity> menuList = sessionFactory.getCurrentSession().createQuery(menuSql).list();
		// 功能表
		String functionSql = "select function from FunctionEntity function";
		List<FunctionEntity> functionList = sessionFactory.getCurrentSession().createQuery(functionSql).list();
		// 节点信息表
		SupplyMarketEntity sm = (SupplyMarketEntity) sessionFactory.getCurrentSession().get(SupplyMarketEntity.class, 888888);
		Document document = CreateXml.createXml(userList,roleList,menuList,functionList,sm);
		return document;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public void doImport(Document document) throws ParseException{
		Map resultMap = AnalysisXml.analisisXml(document);
		List<UserEntity> userList = (List<UserEntity>) resultMap.get("userList");
		List<RoleEntity> roleList = (List<RoleEntity>) resultMap.get("roleList");
		List<MenuEntity> menuList = (List<MenuEntity>) resultMap.get("menuList");
		List<FunctionEntity> functionList = (List<FunctionEntity>) resultMap.get("functionList");
		SupplyMarketEntity sm = (SupplyMarketEntity) resultMap.get("sm");
		
		//添加节点信息
		sessionFactory.getCurrentSession().save("SupplyMarketEntity", sm);

		//添加用户信息
		for(UserEntity user : userList) {
			sessionFactory.getCurrentSession().save(user);
		}
		
		//添加角色信息
		for(RoleEntity role : roleList) {
			sessionFactory.getCurrentSession().save(role);
		}
		
		//添加菜单信息
		for(MenuEntity menu : menuList) {
			sessionFactory.getCurrentSession().save(menu);
		}
		//添加功能信息
		for(FunctionEntity function : functionList) {
			System.out.println("menuId>>>>>>>>>>>"+function.getMenu().getMenuId());
			sessionFactory.getCurrentSession().save(function);
		}
	}
}
