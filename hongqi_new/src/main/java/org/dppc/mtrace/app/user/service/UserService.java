package org.dppc.mtrace.app.user.service;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.dppc.mtrace.app.approach.entity.SupplyMarketEntity;
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

/**
 * 用户管理
 * 
 * @author sunlong
 *
 */
@Service
public class UserService {
	
	@Autowired
	private SessionFactory sessionFactory;;
	
	/**
	 * 查询用户
	 * 
	 * @author sunlong
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public void selectUserList(Page<UserEntity> page,OrderCondition order,UserEntity user,String userName) {
		Session session=sessionFactory.getCurrentSession();
		StringBuffer hql=new StringBuffer();
		hql.append("select user from UserEntity user where 1 = 1");
		if(StringUtils.isNotEmpty(user.getUserName())){
			hql.append(" and user.userName like '%"+user.getUserName()+'%'+"'");
		}
		if(StringUtils.isNotEmpty(user.getRealName())){
			hql.append(" and user.realName like '%"+user.getRealName()+'%'+"'");
		}
		if(StringUtils.isNotEmpty(user.getDepartment())){
			hql.append(" and user.department like '%"+user.getDepartment()+'%'+"'");
		}
		if(StringUtils.isNotEmpty(user.getPosition())){
			hql.append(" and user.position like '%"+user.getPosition()+'%'+"'");
		}
		//若不是开发人员隐藏开发人员用户
		if(!userName.equals("super-admin")){
			hql.append(" and user.userName!='super-admin'");
		}
		hql.append(order.toSql());
		Query queryCount=session.createQuery(hql.toString());	
		page.setTotalRows(queryCount.list().size());
		Query query=session.createQuery(hql.toString());
		query.setMaxResults(page.getPageSize());
		query.setFirstResult((page.getPageIndex()-1)*page.getPageSize());
		List<UserEntity> userList=query.list();
		page.setRows(userList);	
	}
	
	/**
	 * 用户添加
	 * 
	 * @author sunlong
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public int userAdd(UserEntity user) {
		String hql = "select user from UserEntity user where user.userName = ?";		
		List<UserEntity> list = sessionFactory.getCurrentSession().createQuery(hql).setParameter(0, user.getUserName()).list();
		if(list.size()>0){
			return 2;
		}else{
			sessionFactory.getCurrentSession().save(user);
		}
		return 1;
	}
	
	/**
	 * 用户删除
	 * 
	 * @author sunlong
	 * @throws IOException 
	 */
	@Transactional
	public boolean delete(UserEntity user) {
		UserEntity userEntity = (UserEntity) sessionFactory.getCurrentSession().get(UserEntity.class, user.getUserId());
		sessionFactory.getCurrentSession().delete(userEntity);
		return true;
	}
	
	/**
	 * 用户修改跳转
	 * 
	 * @author sunlong
	 * @throws IOException 
	 */
	@Transactional
	public UserEntity toEdit(UserEntity user) {
		UserEntity userEntity = (UserEntity) sessionFactory.getCurrentSession().get(UserEntity.class,user.getUserId());	
		return userEntity;
	}
	
	/**
	 * 用户修改
	 * 
	 * @author sunlong
	 * @throws IOException 
	 */
	@Transactional
	public boolean doEdit(UserEntity user) {
		UserEntity userEntity = (UserEntity) sessionFactory.getCurrentSession().get(UserEntity.class,user.getUserId());	
		user.setRoles(userEntity.getRoles());
		sessionFactory.getCurrentSession().merge(user);
		return true;
	}
	
	/**
	 * 通过id查询用户
	 * 
	 * @author sunlong
	 * @throws IOException 
	 */
	@Transactional
	public UserEntity selectById(UserEntity user) {
		UserEntity userEntity = (UserEntity) sessionFactory.getCurrentSession().get(UserEntity.class,user.getUserId());	
		return userEntity;
	}
	
	/**
	 * 
	 * 角色添加
	 * 
	 * @author SunLong
	 */
	@Transactional
	public boolean doRoleAdd(UserEntity user,String[] roleid){
		if(roleid.length!=0&&!(roleid[0].equals(""))){
			UserEntity userEntity = (UserEntity) sessionFactory.getCurrentSession().get(UserEntity.class,user.getUserId());	
			Set<RoleEntity> role = new HashSet<RoleEntity>();
			userEntity.setRoles(role);
			sessionFactory.getCurrentSession().update(userEntity);
			for(int i = 0;i<roleid.length;i++){
				RoleEntity roles = new RoleEntity();
				roles.setRoleId(Integer.parseInt(roleid[i]));
				userEntity.getRoles().add(roles);
			}
			sessionFactory.getCurrentSession().update(userEntity);
		}
		return true;
	}
	
	/**
	 * 重置密码
	 * 
	 * @author sunlong
	 * @throws IOException 
	 */
	@Transactional
	public boolean doResPwd(UserEntity user,String newPwd) {
		UserEntity userEntity = (UserEntity) sessionFactory.getCurrentSession().get(UserEntity.class,user.getUserId());	
		userEntity.setPassWord(newPwd);
		sessionFactory.getCurrentSession().merge(userEntity);
		return true;
	}
	
	/**
	 * 个人信息修改
	 * 
	 * @author sunlong
	 * @throws IOException 
	 */
	@Transactional
	public boolean doPersonalEdit(UserEntity user) {
		UserEntity userEntity = (UserEntity) sessionFactory.getCurrentSession().get(UserEntity.class,user.getUserId());	
		user.setPassWord(userEntity.getPassWord());
		user.setRoles(userEntity.getRoles());
		sessionFactory.getCurrentSession().merge(user);
		return true;
	}
	
	/**
     * 修改节点信息
     * 
     * @author sunlong
     */
	@Transactional
	public boolean doEditShowNode(SupplyMarketEntity market){
		Date now = new Date();
		market.setRecordDate(now);
		SupplyMarketEntity bean =(SupplyMarketEntity) sessionFactory.getCurrentSession()
				    .get(SupplyMarketEntity.class, market.getSuId());
		if(market.getAreaNo()==null){
			String oname =bean.getAreaName();
			String oid =bean.getAreaNo();
			// 复制 approach 的所有属性值到 bean
			try {
				//BeanUtils.copyProperties(bean, market);
				market.setAreaName(oname);
				market.setAreaNo(oid);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		sessionFactory.getCurrentSession().merge(market);
		return true;
	}
	
	/**
     * 查询节点信息
     * 
     * @author sunlong
     */
	@Transactional
	public SupplyMarketEntity selectNode(){
		SupplyMarketEntity sm = (SupplyMarketEntity) sessionFactory.getCurrentSession().get(SupplyMarketEntity.class, 888888);
		return sm;
	}
}
