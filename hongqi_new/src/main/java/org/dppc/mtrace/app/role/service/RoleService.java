package org.dppc.mtrace.app.role.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
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
public class RoleService {
	
	@Autowired
	SessionFactory sessionFactory;
	
	/**
	 * 查询角色
	 * 
	 * @author sunlong
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public void selectRoleList(Page<RoleEntity> page,OrderCondition order,RoleEntity role,UserEntity user) {
		Session session=sessionFactory.getCurrentSession();
		StringBuffer hql=new StringBuffer();
		hql.append("select role from RoleEntity role where 1=1 ");
		if(StringUtils.isNotEmpty(role.getRoleName())){
			hql.append(" and role.roleName like '%"+role.getRoleName()+'%'+"'");
		}
		//若角色不是开发人员则屏蔽显示
		if(StringUtils.isNotEmpty(user.getUserName()) && !(user.getUserName().equals("super-admin"))){
			hql.append(" and role.roleName!='开发人员'");
		}
		hql.append(order.toSql());
		Query queryCount=session.createQuery(hql.toString());	
		page.setTotalRows(queryCount.list().size());
		Query query=session.createQuery(hql.toString());
		query.setMaxResults(page.getPageSize());
		query.setFirstResult((page.getPageIndex()-1)*page.getPageSize());
		List<RoleEntity> roleList=query.list();
		page.setRows(roleList);	
	}
	
	/**
	 * 添加角色
	 * 
	 * @author sunlong
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public int doAdd(RoleEntity role){
		String hql = "select role from RoleEntity role where role.roleName = ?";		
		List<UserEntity> list = sessionFactory.getCurrentSession().createQuery(hql).setParameter(0, role.getRoleName()).list();
		if(list.size()>0){
			return 2;
		}else{
			sessionFactory.getCurrentSession().save(role);
		}
		return 1;
	}
	
	/**
	 * 删除角色
	 * 
	 * @author sunlong
	 */
	@Transactional
	public boolean delete(RoleEntity role) {
		RoleEntity roleEntity = (RoleEntity)sessionFactory.getCurrentSession().get(RoleEntity.class, role.getRoleId());
		sessionFactory.getCurrentSession().delete(roleEntity);
		return true;
	}
	
	/**
	 * 修改角色跳转（根据id查询角色）
	 * 
	 * @author sunlong
	 */
	@Transactional
	public RoleEntity toEdit(RoleEntity role) {
		RoleEntity roleEntity = (RoleEntity) sessionFactory.getCurrentSession().get(RoleEntity.class,role.getRoleId());	
		return roleEntity;
	}
	
	/**
	 * 修改角色
	 * 
	 * @author sunlong
	 */
	@Transactional
	public boolean doEdit(RoleEntity role) {
		RoleEntity roleEntity = (RoleEntity) sessionFactory.getCurrentSession().get(RoleEntity.class,role.getRoleId());	
		role.setMenu(roleEntity.getMenu());
		sessionFactory.getCurrentSession().merge(role);
		return true;
	}
	
	/**
	 * 查询所有角色
	 * 
	 * @param user 当前登陆用户(若登陆用户不为super-admin 则查看信息时不展示工作人员开发使用的角色)
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public List<RoleEntity> selectRoleAll(UserEntity user) {
		String hql = "select role from RoleEntity role ";		
		//若登陆用户不为super-admin 则查看信息时不展示工作人员开发使用的角色
		if(!user.getUserName().equals("super-admin")){
			hql+=" where role.roleName!='开发人员'";
		}
		List<RoleEntity> list = sessionFactory.getCurrentSession().createQuery(hql).list();
		return list;
	}
	
	/**
	 * 
	 * 根据角色ID查询角色
	 * 
	 * @author SunLong
	 * 
	 */
	@Transactional
	public RoleEntity selectRoleById(RoleEntity role){
		String hql="from RoleEntity  r left join fetch r.menu where r.roleId=?";
		Query query=sessionFactory.getCurrentSession().createQuery(hql).setParameter(0,role.getRoleId());
		RoleEntity roleEntity= (RoleEntity) query.uniqueResult();
		//RoleEntity roleEntity=sessionFactory.getCurrentSession().get(RoleEntity.class, id)
		return roleEntity;
	}
	
	/**
	 * 
	 * 分配权限
	 * 
	 * @author SunLong
	 * 
	 */
	@Transactional
	public boolean doPermissions(RoleEntity role,String[] menuIds){
		RoleEntity roleEntity = (RoleEntity)sessionFactory.getCurrentSession().get(RoleEntity.class,role.getRoleId());
		Set<MenuEntity> menu = new HashSet<MenuEntity>();
		roleEntity.setMenu(menu);
		sessionFactory.getCurrentSession().update(roleEntity);
		for(int i = 0;i<menuIds.length;i++){
			MenuEntity menus = new MenuEntity();
			menus.setMenuId(Integer.parseInt(menuIds[i]));
			roleEntity.getMenu().add(menus);
		}
		sessionFactory.getCurrentSession().update(roleEntity);
		return true;
	}	
}
