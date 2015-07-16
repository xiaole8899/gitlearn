package org.dppc.mtrace.app.approach.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.dppc.mtrace.app.approach.entity.SupplyMarketEntity;
import org.dppc.mtrace.frame.base.OrderCondition;
import org.dppc.mtrace.frame.base.Page;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 供货市场管理业务类
 * @author weiyuzhen
 *
 */
@Service
@Transactional
public class SupplyMarketService {
	
	@Autowired
	SessionFactory sessionFactory;
	
	/**
	 * 展示供货市场信息
	 * @author weiyuzhen
	 */
	public void listSupplyMarket(Page<SupplyMarketEntity> page,OrderCondition order,SupplyMarketEntity market){
		
		//获取session
		Session session = sessionFactory.getCurrentSession();
		StringBuffer hql = new StringBuffer();
		hql.append("select su from  SupplyMarketEntity su where su.suId!=888888  ");
		if( StringUtils.isNotEmpty( market.getNo() ) ){
			hql.append(" and su.no ='"+market.getNo()+"' ");
		}
		if( StringUtils.isNotEmpty( market.getName() ) ){
			hql.append(" and su.name ='"+market.getName()+"'");
		}
		if( StringUtils.isNotEmpty( market.getsNodeType() ) ){
			hql.append(" and su.sNodeType ='"+market.getsNodeType()+"'");
		}
		hql.append(order.toSql());
		Query queryCount = session.createQuery(hql.toString());
		page.setTotalRows(queryCount.list().size());  
		Query query = session.createQuery(hql.toString());
		
		//设置下限
		query.setMaxResults(page.getPageSize());
		
		//设置开始位置，按下标
		query.setFirstResult((page.getPageIndex()-1)*page.getPageSize());
		@SuppressWarnings("unchecked")
		List<SupplyMarketEntity> suList = query.list();
		page.setRows(suList);
	}
	
	/**
	 * 单条供货市场信息详情根据
	 * @author 
	 */
	public SupplyMarketEntity SupplyMarketInfo(String supplyMarketId){
		SupplyMarketEntity supplyMarketInfo=(SupplyMarketEntity)sessionFactory.getCurrentSession().get(SupplyMarketEntity.class,Integer.parseInt(supplyMarketId));
		return supplyMarketInfo;
	}
	
	/**
	 * 添加供货市场
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public int addSupplyMarket(SupplyMarketEntity market){
		StringBuffer hql = new StringBuffer();
		hql.append("select su from SupplyMarketEntity su where su.name = ?");
		List<SupplyMarketEntity> marketList = sessionFactory.getCurrentSession().createQuery(hql.toString()).setParameter(0, market.getName()).list();
		if(marketList.size()>0){
			return 2;
		}else{
			try{
				 Date now = new Date();
				SimpleDateFormat h = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				 market.setRecordDate(now);
				 market.setUpdateTime(now);
				sessionFactory.getCurrentSession().save(market);
			}catch(Exception e){
				e.printStackTrace();
				return -1;
			}
		}
		return 1;
		
	}
	
	/*
	 * 删除供货市场
	 */
	public Boolean deleteSupplyMarket(SupplyMarketEntity market){
		String hql = "delete SupplyMarketEntity su where su.suId=? ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, market.getSuId());
		int result =query.executeUpdate();
		if(result>0){
			return true;
		}else{
			return false;
		}
		
	}
	
	/**
	 * 按照条件查找供货市场信息
	 * @param market
	 * @return
	 *@author weiyuzhen
	 */
	public SupplyMarketEntity findSupplyMarket(SupplyMarketEntity market){
		SupplyMarketEntity suMarket = (SupplyMarketEntity) sessionFactory.getCurrentSession().get(SupplyMarketEntity.class, market.getSuId());
		return suMarket;
	}
	
    /**
     * 编辑供货市场
     * @param market
     * @return
     *@author weiyuzhen
     */
	public boolean editSupplyMarket(SupplyMarketEntity market){
		Date now = new Date();
		market.setUpdateTime(now);
		SupplyMarketEntity bean =(SupplyMarketEntity) sessionFactory.getCurrentSession()
				    .get(SupplyMarketEntity.class, market.getSuId());
		if(market.getAreaNo()==null){
			String oname =bean.getAreaName();
			String oid =bean.getAreaNo();
			Date recordDate = bean.getRecordDate();
			// 复制 approach 的所有属性值到 bean
			try {
				//BeanUtils.copyProperties(bean, market);
				market.setAreaName(oname);
				market.setAreaNo(oid);
				market.setRecordDate(recordDate);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		sessionFactory.getCurrentSession().merge(market);
		return true;
	}
}
