package org.dppc.mtrace.app.login.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dppc.mtrace.app.approach.entity.SupplyMarketEntity;
import org.dppc.mtrace.app.menu.entity.FunctionEntity;
import org.dppc.mtrace.app.menu.entity.MenuEntity;
import org.dppc.mtrace.app.role.entity.RoleEntity;
import org.dppc.mtrace.app.user.entity.UserEntity;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService{
	
	@Autowired
	SessionFactory sessionFactory;
	/**
	 * 登陆
	 * 
	 * @author SunLong
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public Map<String,Object> login(UserEntity user) {
		Map<String,Object> userMap = new HashMap<String, Object>();
		String hql = "from UserEntity user left join fetch user.roles "
				+ " as role left join fetch role.menu as menu "
				+ " left join fetch menu.function where user.userName = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, user.getUserName());
		List<UserEntity> list = query.list();
		UserEntity userEntity = new UserEntity();
		List<MenuEntity> menuList = new ArrayList<MenuEntity>();
		if(!(list.isEmpty())){
			userEntity = list.get(0);
			if(userEntity.getPassWord().equals(user.getPassWord())){
				for(RoleEntity roles : userEntity.getRoles()){
					for(MenuEntity menu : roles.getMenu()){
						menuList.add(menu);
						for(FunctionEntity function : menu.getFunction()){
							userEntity.getFunctions().add(function);
						}
					}
				}
				Set<MenuEntity> temp =new HashSet<MenuEntity>();
				temp.addAll(menuList);
				userEntity.getMenus().addAll(temp);
				userMap.put("user", userEntity);
				return userMap;
			}else {
				// 如果密码不匹配
				userMap.put("user", userEntity);
				userMap.put("state", "pwdNull");
				return userMap;
			}
		}else {
			user.setUserName("");
			userMap.put("user", user);
			return userMap;
		}
	}
	
	/**
	 * 查询节点信息
	 * 
	 * @author SunLong
	 * 
	 */
	@Transactional
	public SupplyMarketEntity queryNode(){
		SupplyMarketEntity sm = (SupplyMarketEntity) sessionFactory.getCurrentSession().get(SupplyMarketEntity.class, 888888);
		return sm;
	}
}
