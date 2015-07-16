package org.dppc.mtrace.app.pc.service;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.dppc.mtrace.app.ews.entity.EwsEntity;
import org.dppc.mtrace.app.pc.entity.IcEntity;
import org.dppc.mtrace.frame.base.OrderCondition;
import org.dppc.mtrace.frame.base.Page;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IcService {

	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * IC卡列表数据
	 * 
	 * @author dx
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public void selectIcList(Page<IcEntity> page,OrderCondition order,IcEntity ic,String startdate,String enddate) {
		Session session=sessionFactory.getCurrentSession();
		StringBuffer hql=new StringBuffer();
		hql.append("select ic from IcEntity ic where 1 = 1");
		
		//用于检索查询
		if(StringUtils.isNotEmpty(ic.getIcName())){
			hql.append(" and ic.icName like '%"+ic.getIcName()+'%'+"'");
		}
		if(startdate!=null&&!(startdate.isEmpty())&&startdate!=""){
			hql.append(" and ic.inDate >= '"+startdate+"'");
		}
		if(enddate!=null&&!(enddate.isEmpty())&&enddate!=""){
			hql.append(" and ic.inDate <= '"+enddate+"'");
		}
		
		hql.append(order.toSql());
		Query queryCount=session.createQuery(hql.toString());	
		page.setTotalRows(queryCount.list().size());
		Query query=session.createQuery(hql.toString());
		query.setMaxResults(page.getPageSize());
		query.setFirstResult((page.getPageIndex()-1)*page.getPageSize());
		List<IcEntity> icList=query.list();
		page.setRows(icList);	
	}
	
	/**
	 * 添加前判断IC卡读写器名称是否重复
	 * 
	 * @author dx
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public int panDuanIcName(IcEntity ic){
		String hql = "select ic from IcEntity ic where ic.icName=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, ic.getIcName());
		List<IcEntity> list = query.list();
		
		if(list.size()>0){
			return 1;
		}else{
			return 0;
		}
	}
	
	/**
	 * IC卡读写器添加
	 * 
	 * @author dx
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public int icAdd(IcEntity ic){
		String hql = "select ic from IcEntity ic where ic.icName = ?";		
		List<IcEntity> list = sessionFactory.getCurrentSession().createQuery(hql).setParameter(0, ic.getIcName()).list();
		if(list.size()>0){
			return 2;
		}else{
			sessionFactory.getCurrentSession().save(ic);
		}
		return 1;
	}
	
	/**
	 * IC卡读写器修改跳转-查询要修改的数据
	 * 
	 * @author dx
	 * @throws  
	 */
	@Transactional
	public IcEntity toUpdateIc(IcEntity ic) {
		IcEntity icEntity = (IcEntity) sessionFactory.getCurrentSession().get(IcEntity.class,ic.getIcId());	
		return icEntity;
	}
	
	/**
	 * IC卡读写器修改前判断名称
	 * 
	 * @author dx
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public int editPanDuanIcName(IcEntity ic){
		String hql = "select ic from IcEntity ic where ic.icName=? and ic.icId !=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, ic.getIcName());
		query.setParameter(1, ic.getIcId());
		List<EwsEntity> list = query.list();
		
		if(list.size()>0){
			return 1;
		}else{
			return 0;
		}
	}
	
	/**
	 * IC卡读写器修改
	 * 
	 * @author dx
	 * @throws 
	 */
	@Transactional
	public boolean icUpdate(IcEntity ic) {
		//EwsEntity userEntity = (EwsEntity) sessionFactory.getCurrentSession().get(EwsEntity.class,ews.getEwsId());	
		sessionFactory.getCurrentSession().update(ic);
		return true;
	}
	
	/**
	 * IC卡读写器删除
	 * 
	 * @author dx
	 * @throws 
	 */
	@Transactional
	public boolean deleteIc(IcEntity ic) {
		IcEntity icEntity = (IcEntity) sessionFactory.getCurrentSession().get(IcEntity.class, ic.getIcId());
		sessionFactory.getCurrentSession().delete(icEntity);
		return true;
	}
	
}
