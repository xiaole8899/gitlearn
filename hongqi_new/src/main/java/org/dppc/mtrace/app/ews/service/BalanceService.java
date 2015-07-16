package org.dppc.mtrace.app.ews.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.dppc.mtrace.app.ews.entity.BalanceEntity;
import org.dppc.mtrace.frame.base.OrderCondition;
import org.dppc.mtrace.frame.base.Page;
import org.dppc.mtrace.frame.kit.StringKit;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BalanceService {
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * 展示小秤管理信息页
	 * @param page
	 * @param order
	 * @param balance
	 * @param startdate
	 * @param enddate
	 * @param request
	 * @author weiyuzhen
	 */
	public void listBalance(Page<BalanceEntity> page,OrderCondition order,BalanceEntity balance,String startdate,String enddate,HttpServletRequest request){
		Session session=sessionFactory.getCurrentSession();
		StringBuilder hql = new StringBuilder();
		hql.append("select balance from BalanceEntity balance where 1=1 ");
		
		if(StringKit.isNotBlank(balance.getBalanceNo())){
			hql.append(" and balance.balanceNo like '%"+balance.getBalanceNo()+"%' ");
		}
		if(StringKit.isNotBlank(balance.getBizId())){
			hql.append(" and balance.bizId like '"+balance.getBizId()+"'");
		}
		if(StringKit.isNotBlank(balance.getBizName())){
			hql.append(" and balance.bizName like '%"+balance.getBizName()+"%'");
		}
		if(startdate!=null&&!(startdate.isEmpty())&&startdate!=""){
			hql.append(" and balance.boundTime >= '"+startdate+"'");
		}
		if(enddate!=null&&!(enddate.isEmpty())&&enddate!=""){
			hql.append(" and balance.boundTime <= '"+enddate+"'");
		}
		
		hql.append(order.toSql());
		Query queryCount=session.createQuery(hql.toString());	
		page.setTotalRows(queryCount.list().size());
		Query query=session.createQuery(hql.toString());
		query.setMaxResults(page.getPageSize());
		query.setFirstResult((page.getPageIndex()-1)*page.getPageSize());
		@SuppressWarnings("unchecked")
		List<BalanceEntity> balanceList=query.list();
		page.setRows(balanceList);	
		
	}
	
	/**
	 * 通过小秤的编号查找小秤信息
	 * @param balance
	 * @return
	 * @author weiyuzhen
	 */
	public BalanceEntity findBalanceByBalanceNo(BalanceEntity balance){
		Session session=sessionFactory.getCurrentSession();
		StringBuilder hql = new StringBuilder();
		hql.append("select balance from BalanceEntity balance where balance.balanceNo = ? ");
		Query query = session.createQuery(hql.toString());
		query.setParameter(0, balance.getBalanceNo());
		return (BalanceEntity) query.uniqueResult();
		
	}
	/**
	 * 通过商户编号查找小秤的信息
	 * @param bizId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BalanceEntity> findBalanceBybizId(String bizId){
		Session session = sessionFactory.getCurrentSession();
		StringBuilder hql = new StringBuilder();
		hql.append(" select balance from BalanceEntity balance where balance.bizId =?");
		Query query = session.createQuery(hql.toString());
		query.setParameter(0, bizId);
		return query.list();
		
	}
	/**
	 * 增加一条小秤信息
	 * @param balance
	 * @return
	 * @author weiyuzhen
	 */
	@Transactional
	public int balanceAdd(BalanceEntity balance){
		sessionFactory.getCurrentSession().save(balance);
		return 1;
		
	}
	
	/**
	 * 通过主键查找小秤信息
	 * @param balance
	 * @return
	 * @author weiyuzhen
	 */
	@Transactional
	public BalanceEntity findBalanceById(BalanceEntity balance) {
		BalanceEntity balanceEntity = (BalanceEntity) sessionFactory.getCurrentSession().get(BalanceEntity.class,balance.getBaId());	
		return balanceEntity;
	}
	
	/**
	 * 修改小秤的信息
	 * @param balance
	 * @return
	 * @author weiyuzhen
	 */
	@Transactional
	public boolean balanceUpdate(BalanceEntity balance) {
		sessionFactory.getCurrentSession().update(balance);
		return true;
	}
	
	/**
	 * 删除小秤
	 * @param ews
	 * @return
	 * @author weiyuzhen
	 */
	@Transactional
	public boolean deleteBalance(BalanceEntity balance) {
		BalanceEntity BalanceEntity = (BalanceEntity) sessionFactory.getCurrentSession().get(BalanceEntity.class, balance.getBaId());
		sessionFactory.getCurrentSession().delete(BalanceEntity);
		return true;
	}
	

}
