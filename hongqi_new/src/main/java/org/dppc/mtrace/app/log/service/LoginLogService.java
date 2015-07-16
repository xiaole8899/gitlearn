package org.dppc.mtrace.app.log.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dppc.mtrace.app.log.entity.LoginLogEntity;
import org.dppc.mtrace.frame.base.OrderCondition;
import org.dppc.mtrace.frame.base.Page;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginLogService{
	
	@Autowired
	SessionFactory sessionFactory;
	
	/**
	 * 查询日志
	 * 
	 * @author sunlong
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public void selectLogList(Page<LoginLogEntity> page, OrderCondition order, LoginLogEntity log, String loginDateStart, String loginDateEnd, String logoutDateStart, String logoutDateEnd) {
		Session session=sessionFactory.getCurrentSession();
		StringBuffer hql=new StringBuffer();
		hql.append("select log from LoginLogEntity log where 1 = 1");
		if(StringUtils.isNotEmpty(log.getUserName())){
			hql.append(" and log.userName like '%"+log.getUserName()+'%'+"'");
		}
		if(StringUtils.isNotEmpty(log.getRealName())){
			hql.append(" and log.realName like '%"+log.getRealName()+'%'+"'");
		}
		if(StringUtils.isNotEmpty(log.getDescriptions())){
			hql.append(" and log.descriptions like '%"+log.getDescriptions()+'%'+"'");
		}
		if(StringUtils.isNotBlank(loginDateStart)){
			hql.append(" and log.loginTime >= '"+loginDateStart+"'");
		}
		if(StringUtils.isNotBlank(loginDateEnd)){
			hql.append(" and log.loginTime <= '"+loginDateEnd+"'");
		}
		if(StringUtils.isNotBlank(logoutDateStart)){
			hql.append(" and log.logoutTime >= '"+logoutDateStart+"'");
		}
		if(StringUtils.isNotBlank(logoutDateEnd)){
			hql.append(" and log.logoutTime <= '"+logoutDateEnd+"'");
		}
		hql.append(order.toSql());
		Query queryCount=session.createQuery(hql.toString());	
		page.setTotalRows(queryCount.list().size());
		Query query=session.createQuery(hql.toString());
		query.setMaxResults(page.getPageSize());
		query.setFirstResult((page.getPageIndex()-1)*page.getPageSize());
		List<LoginLogEntity> logList=query.list();
		page.setRows(logList);	
	}
	
	/**
	 * 登陆日志
	 * 
	 * @author SunLong
	 */
	@Transactional
	public boolean loginLog(LoginLogEntity logEntity) {
		sessionFactory.getCurrentSession().save(logEntity);
		return true;
	} 
	
	/**
	 * 修改日志
	 * 
	 * @author SunLong
	 */
	@Transactional
	public boolean logout(String sessionId) {
		String hql = "select log from LoginLogEntity log where log.sessionId = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, sessionId);
		@SuppressWarnings("unchecked")
		List<LoginLogEntity> logList = (List<LoginLogEntity>) query.list();
		if(!logList.isEmpty()){
			LoginLogEntity logEntity = logList.get(0);
			logEntity.setLogoutTime(new Date());
			sessionFactory.getCurrentSession().update(logEntity);
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 根据日志编号删除日志
	 * @param logId
	 * @return
	 */
	public boolean deleteLoginLog(int logId){
		String hql="delete from LoginLogEntity where logId="+logId;
		Query query =sessionFactory.getCurrentSession().createQuery(hql);
		query.executeUpdate();
		return true;
	}
}
