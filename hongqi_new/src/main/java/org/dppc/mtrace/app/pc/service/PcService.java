package org.dppc.mtrace.app.pc.service;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.dppc.mtrace.app.pc.entity.PcEntity;
import org.dppc.mtrace.frame.base.OrderCondition;
import org.dppc.mtrace.frame.base.Page;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PcService {
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@SuppressWarnings("unchecked")
	@Transactional
	public void selectPcList(Page<PcEntity> page,OrderCondition order,PcEntity pc,String startdate,String enddate) {
		Session session=sessionFactory.getCurrentSession();
		StringBuffer hql=new StringBuffer();
		hql.append("select pc from PcEntity pc where 1 = 1");
		
		//用于检索查询
		if(StringUtils.isNotEmpty(pc.getPcIp())){
			hql.append(" and pc.pcIp like '%"+pc.getPcIp()+'%'+"'");
		}
		if(StringUtils.isNotEmpty(pc.getMac())){
			hql.append(" and pc.mac like '%"+pc.getMac()+'%'+"'");
		}
		if(StringUtils.isNotEmpty(pc.getMainBoard())){
			hql.append(" and pc.mainBoard like '%"+pc.getMainBoard()+'%'+"'");
		}
		if(StringUtils.isNotEmpty(pc.getVgaDriver())){
			hql.append(" and pc.vgaDriver like '%"+pc.getVgaDriver()+'%'+"'");
		}
		if(startdate!=null&&!(startdate.isEmpty())&&startdate!=""){
			hql.append(" and pc.inDate >= '"+startdate+"'");
		}
		if(enddate!=null&&!(enddate.isEmpty())&&enddate!=""){
			hql.append(" and pc.inDate <= '"+enddate+"'");
		}
		
		hql.append(order.toSql());
		Query queryCount=session.createQuery(hql.toString());	
		page.setTotalRows(queryCount.list().size());
		Query query=session.createQuery(hql.toString());
		query.setMaxResults(page.getPageSize());
		query.setFirstResult((page.getPageIndex()-1)*page.getPageSize());
		List<PcEntity> pcList=query.list();
		page.setRows(pcList);	
	}
	
	/**
	 * PC机添加前判断MAC号
	 * 
	 * @author dx
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public int pcPanDuanMac(PcEntity pc){
		String hql = "select pc from PcEntity pc where pc.mac=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, pc.getMac());
		List<PcEntity> list = query.list();
		
		if(list.size()>0){
			return 1;
		}else{
			return 0;
		}
	}
	
	/**
	 * PC机添加前判断IP号
	 * @author dx
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public int pcPanDuanIp(PcEntity pc){
		String hql = "select pc from PcEntity pc where pc.pcIp=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, pc.getPcIp());
		List<PcEntity> list = query.list();
		
		if(list.size()>0){
			return 1;
		}else{
			return 0;
		}
	}
	
	/**
	 * PC机添加
	 * @author dx
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public int pcAdd(PcEntity pc){
		String hql = "select pc from PcEntity pc where pc.pcIp = ?";		
		List<PcEntity> list = sessionFactory.getCurrentSession().createQuery(hql).setParameter(0, pc.getPcIp()).list();
		if(list.size()>0){
			return 2;
		}else{
			sessionFactory.getCurrentSession().save(pc);
		}
		return 1;
		
	}
	
	/**
	 * PC机修改跳转-查询要修改的数据
	 * 
	 * @author dx
	 * @throws  
	 */
	@Transactional
	public PcEntity toUpdatePc(PcEntity pc) {
		PcEntity pcEntity = (PcEntity) sessionFactory.getCurrentSession().get(PcEntity.class,pc.getPcId());	
		return pcEntity;
	}
	
	/**
	 * PC机修改前判断SN号
	 * 
	 * @author dx
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public int pcEditPanDuanMac(PcEntity pc){
		String hql = "select pc from PcEntity pc where pc.mac=? and pc.pcId !=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, pc.getMac());
		query.setParameter(1, pc.getPcId());
		List<PcEntity> list = query.list();
		
		if(list.size()>0){
			return 1;
		}else{
			return 0;
		}
	}
	
	/**
	 * PC机修改前判断IP号
	 * 
	 * @author dx
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public int pcEditPanDuanIp(PcEntity pc){
		String hql = "select pc from PcEntity pc where pc.pcIp=? and pc.pcId !=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, pc.getPcIp());
		query.setParameter(1, pc.getPcId());
		List<PcEntity> list = query.list();
		
		if(list.size()>0){
			return 1;
		}else{
			return 0;
		}
	}
	
	/**
	 * PC机修改
	 * 
	 * @author dx
	 * @throws 
	 */
	@Transactional
	public boolean pcUpdate(PcEntity pc) {
		//EwsEntity userEntity = (EwsEntity) sessionFactory.getCurrentSession().get(EwsEntity.class,ews.getEwsId());	
		sessionFactory.getCurrentSession().update(pc);
		return true;
	}
	
	/**
	 * PC机删除
	 * 
	 * @author dx
	 * @throws 
	 */
	@Transactional
	public boolean deletePc(PcEntity pc) {
		PcEntity pcEntity = (PcEntity) sessionFactory.getCurrentSession().get(PcEntity.class, pc.getPcId());
		sessionFactory.getCurrentSession().delete(pcEntity);
		return true;
	}
	
}
