package org.dppc.mtrace.app.ews.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.dppc.mtrace.app.ews.entity.EwsEntity;
import org.dppc.mtrace.app.ews.entity.FeComputerEntity;
import org.dppc.mtrace.app.ews.entity.NoticeEntity;
import org.dppc.mtrace.app.merchant.entity.Merchant;
import org.dppc.mtrace.frame.base.OrderCondition;
import org.dppc.mtrace.frame.base.Page;
import org.dppc.mtrace.frame.kit.ArrayKit;
import org.dppc.mtrace.frame.kit.StringKit;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EwsService {

	@Autowired
	private SessionFactory sessionFactory;
	
	
	
	/***********************************************start 电子秤 ************************************************/
	/**
	 * 查询电子秤列表数据
	 * 
	 * @author dx
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public void selectEwsList(Page<EwsEntity> page,OrderCondition order,EwsEntity ews,String startdate,String enddate) {
		Session session=sessionFactory.getCurrentSession();
		StringBuffer hql=new StringBuffer();
		hql.append("select ews from EwsEntity ews where 1 = 1");
		
		//用于检索查询
		if(StringUtils.isNotEmpty(ews.getEwsIp())){
			hql.append(" and ews.ewsIp like '%"+ews.getEwsIp()+'%'+"'");
		}
		if(StringUtils.isNotEmpty(ews.getSalerName())){
			hql.append(" and ews.salerName like '%"+ews.getSalerName()+'%'+"'");
		}
		if(StringUtils.isNotEmpty(ews.getSalerNumber())){
			hql.append(" and ews.salerNumber like '%"+ews.getSalerNumber()+'%'+"'");
		}
		if(startdate!=null&&!(startdate.isEmpty())&&startdate!=""){
			hql.append(" and ews.bindTime >= '"+startdate+"'");
		}
		if(enddate!=null&&!(enddate.isEmpty())&&enddate!=""){
			hql.append(" and ews.bindTime <= '"+enddate+"'");
		}
		
		hql.append(order.toSql());
		Query queryCount=session.createQuery(hql.toString());	
		page.setTotalRows(queryCount.list().size());
		Query query=session.createQuery(hql.toString());
		query.setMaxResults(page.getPageSize());
		query.setFirstResult((page.getPageIndex()-1)*page.getPageSize());
		List<EwsEntity> ewsList=query.list();
		page.setRows(ewsList);	
	}
	
	/**
	 * 电子秤添加前判断SN号
	 * 
	 * @author dx
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public int ewsPanDuanSn(EwsEntity ews){
		String hql = "select ews from EwsEntity ews where ews.ewsSn=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, ews.getEwsSn());
		List<EwsEntity> list = query.list();
		
		if(list.size()>0){
			return 1;
		}else{
			return 0;
		}
	}
	
	/**
	 * 电子秤添加前判断IP号
	 * 
	 * @author dx
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public int ewsPanDuanIp(EwsEntity ews){
		String hql = "select ews from EwsEntity ews where ews.ewsIp=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, ews.getEwsIp());
		List<EwsEntity> list = query.list();
		
		if(list.size()>0){
			return 1;
		}else{
			return 0;
		}
		
	}
	
	/**
	 * 电子秤添加
	 * 
	 * @author dx
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public int ewsAdd(EwsEntity ews){
		String hql = "select ews from EwsEntity ews where ews.salerName = ?";		
		List<EwsEntity> list = sessionFactory.getCurrentSession().createQuery(hql).setParameter(0, ews.getSalerName()).list();
		if(list.size()>0){
			return 2;
		}else{
			sessionFactory.getCurrentSession().save(ews);
		}
		return 1;
		
	}
	
	/**
	 * 电子秤修改跳转-查询要修改的数据
	 * 
	 * @author dx
	 * @throws  
	 */
	@Transactional
	public EwsEntity toUpdateEws(EwsEntity ews) {
		EwsEntity ewsEntity = (EwsEntity) sessionFactory.getCurrentSession().get(EwsEntity.class,ews.getEwsId());	
		return ewsEntity;
	}
	
	/**
	 * 电子秤修改前判断SN号
	 * 
	 * @author dx
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public int ewsEditPanDuanSn(EwsEntity ews){
		String hql = "select ews from EwsEntity ews where ews.ewsSn=? and ews.ewsId !=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, ews.getEwsSn());
		query.setParameter(1, ews.getEwsId());
		List<EwsEntity> list = query.list();
		
		if(list.size()>0){
			return 1;
		}else{
			return 0;
		}
	}
	
	/**
	 * 电子秤修改前判断IP号
	 * 
	 * @author dx
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public int ewsEditPanDuanIp(EwsEntity ews){
		String hql = "select ews from EwsEntity ews where ews.ewsIp=? and ews.ewsId !=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, ews.getEwsIp());
		query.setParameter(1, ews.getEwsId());
		List<EwsEntity> list = query.list();
		
		if(list.size()>0){
			return 1;
		}else{
			return 0;
		}
		
	}
	
	/**
	 * 电子秤修改
	 * 
	 * @author dx
	 * @throws 
	 */
	@Transactional
	public boolean ewsUpdate(EwsEntity ews) {
		//EwsEntity userEntity = (EwsEntity) sessionFactory.getCurrentSession().get(EwsEntity.class,ews.getEwsId());	
		sessionFactory.getCurrentSession().update(ews);
		return true;
	}
	
	/**
	 * 电子秤删除
	 * 
	 * @author dx
	 * @throws 
	 */
	@Transactional
	public boolean deleteEws(EwsEntity ews) {
		EwsEntity ewsEntity = (EwsEntity) sessionFactory.getCurrentSession().get(EwsEntity.class, ews.getEwsId());
		sessionFactory.getCurrentSession().delete(ewsEntity);
		return true;
	}
	
	/***********************************************end   电子秤************************************************/
	
	
	
	
	
	
	
	
	
	
	
	
	/***********************************************start 前置机 ************************************************/
	/**
	 * 查询前置机列表数据
	 * 
	 * @author dx
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public void selectFecList(Page<FeComputerEntity> page,OrderCondition order,FeComputerEntity fec,String startdate,String enddate) {
		Session session=sessionFactory.getCurrentSession();
		StringBuffer hql=new StringBuffer();
		hql.append("select fec from FeComputerEntity fec where 1 = 1");
		
		//用于检索查询
		if(StringUtils.isNotEmpty(fec.getFecIp())){
			hql.append(" and fec.fecIp like '%"+fec.getFecIp()+'%'+"'");
		}
		if(StringUtils.isNotEmpty(fec.getPort())){
			hql.append(" and fec.port like '%"+fec.getPort()+'%'+"'");
		}
		if(startdate!=null&&!(startdate.isEmpty())&&startdate!=""){
			hql.append(" and fec.bindTime >= '"+startdate+"'");
		}
		if(enddate!=null&&!(enddate.isEmpty())&&enddate!=""){
			hql.append(" and fec.bindTime <= '"+enddate+"'");
		}
		
		hql.append(order.toSql());
		Query queryCount=session.createQuery(hql.toString());	
		page.setTotalRows(queryCount.list().size());
		Query query=session.createQuery(hql.toString());
		query.setMaxResults(page.getPageSize());
		query.setFirstResult((page.getPageIndex()-1)*page.getPageSize());
		List<FeComputerEntity> fecList=query.list();
		page.setRows(fecList);	
	}
	
	/**
	 * 前置机添加前判断IP号是否重复
	 * 
	 * @author dx
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public int fecPanDuanIp(FeComputerEntity fec){
		String hql = "select fec from FeComputerEntity fec where fec.fecIp=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, fec.getFecIp());
		List<FeComputerEntity> list = query.list();
		
		if(list.size()>0){
			return 1;
		}else{
			return 0;
		}
	}
	
	/**
	 * 前置机添加
	 * 
	 * @author dx
	 */
	@Transactional
	public int fecAdd(FeComputerEntity fec){
		/*String hql = "select fec from FeComputerEntity fec where fec.salerName = ?";		
		List<EwsEntity> list = sessionFactory.getCurrentSession().createQuery(hql).setParameter(0, ews.getSalerName()).list();
		if(list.size()>0){
			return 2;
		}else{
			sessionFactory.getCurrentSession().save(fec);
		}*/
		
		sessionFactory.getCurrentSession().save(fec);
		
		return 1;
		
	}
	
	/**
	 * 前置机修改跳转-查询要修改的数据
	 * 
	 * @author dx
	 * @throws  
	 */
	@Transactional
	public FeComputerEntity toUpdateFec(FeComputerEntity fec) {
		FeComputerEntity fecEntity = (FeComputerEntity) sessionFactory.getCurrentSession().get(FeComputerEntity.class,fec.getFecId());	
		return fecEntity;
	}
	
	/**
	 * 前置机修改前判断IP号是否重复
	 * 
	 * @author dx
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public int fecEditPanDuanIp(FeComputerEntity fec){
		String hql = "select fec from FeComputerEntity fec where fec.fecIp=? and fec.fecId !=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, fec.getFecIp());
		query.setParameter(1, fec.getFecId());
		List<FeComputerEntity> list = query.list();
		
		if(list.size()>0){
			return 1;
		}else{
			return 0;
		}
	}
	
	/**
	 * 前置机修改
	 * 
	 * @author dx
	 * @throws 
	 */
	@Transactional
	public boolean fecUpdate(FeComputerEntity fec) {
		sessionFactory.getCurrentSession().update(fec);
		
		return true;
	}
	
	/**
	 * 前置机删除
	 * 
	 * @author dx
	 * @throws 
	 */
	@Transactional
	public boolean deleteFec(FeComputerEntity fec) {
		FeComputerEntity fecEntity = (FeComputerEntity) sessionFactory.getCurrentSession().get(FeComputerEntity.class, fec.getFecId());
		sessionFactory.getCurrentSession().delete(fecEntity);
		return true;
	}
	
	
	/***********************************************end   前置机************************************************/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/***********************************************start 关联电子秤和前置机 ************************************************/
	/**
	 * 查询所有前置机
	 * 
	 * @author dx
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public List<FeComputerEntity> selectFecAll() {
		String hql = "select fec from FeComputerEntity fec ";		
		List<FeComputerEntity> list = sessionFactory.getCurrentSession().createQuery(hql).list();
		return list;
	}
	
	/**
	 * 通过电子秤id查询对象
	 * 
	 * @author dx
	 * @throws IOException 
	 */
	@Transactional
	public EwsEntity selectById(EwsEntity ews) {
		EwsEntity ewsEntity = (EwsEntity) sessionFactory.getCurrentSession().get(EwsEntity.class,ews.getEwsId());	
		return ewsEntity;
	}
	
	/**
	 * 
	 * 前置机添加
	 * 
	 * @author dx
	 */
	@Transactional
	public boolean doFecAdd(EwsEntity ews, String[] fecid){
		EwsEntity ewsEntity = (EwsEntity) sessionFactory.getCurrentSession().get(EwsEntity.class,ews.getEwsId());
		ewsEntity.getFes().clear();
		if(!ArrayKit.isEmpty(fecid)){
			if (StringKit.isNotEmpty(fecid[0])) {
				for(int i = 0; i<fecid.length; i++){
					FeComputerEntity fecs = new FeComputerEntity();
					fecs.setFecId(Integer.parseInt(fecid[i]));
					//fecs.setBindTime(new Date());
					ewsEntity.getFes().add(fecs);
				}
			}
			
			sessionFactory.getCurrentSession().update(ewsEntity);
			
			Date now =new Date();
			for (FeComputerEntity fec : ewsEntity.getFes()) {
				FeComputerEntity fecEntity = (FeComputerEntity) sessionFactory.getCurrentSession().get(FeComputerEntity.class,fec.getFecId());
				fecEntity.setBindTime(now);
				sessionFactory.getCurrentSession().update(fecEntity);
			}
			
		}
		
		return true;
	}
	
	
	
	/***********************************************end   关联电子秤和前置机************************************************/
	
	
	
	
	
	
	
	/***********************************************start 关联前置机和电子秤 ************************************************/
	/**
	 * 查询所有前置机
	 * 
	 * @author dx
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public List<EwsEntity> selectEwsAll() {
		String hql = "select ews from EwsEntity ews ";		
		List<EwsEntity> list = sessionFactory.getCurrentSession().createQuery(hql).list();
		return list;
	}
	
	/**
	 * 通过前置机id查询对象
	 * 
	 * @author dx
	 * @throws IOException 
	 */
	@Transactional
	public FeComputerEntity selectByIdGetEws(FeComputerEntity fec) {
		FeComputerEntity fecEntity = (FeComputerEntity) sessionFactory.getCurrentSession().get(FeComputerEntity.class,fec.getFecId());	
		return fecEntity;
	}
	
	/**
	 * 
	 * 电子秤添加
	 * 
	 * @author dx
	 */
	@Transactional
	public boolean doEwsFecAdd(FeComputerEntity fec,String[] ewsid){
		FeComputerEntity ewsEntity = (FeComputerEntity) sessionFactory.getCurrentSession().get(FeComputerEntity.class,fec.getFecId());
		ewsEntity.getEws().clear();
		if(!ArrayKit.isEmpty(ewsid)){
			if (StringKit.isNotEmpty(ewsid[0])) {
				for(int i = 0; i<ewsid.length; i++){
					EwsEntity fecs = new EwsEntity();
					fecs.setEwsId(Integer.parseInt(ewsid[i]));
					ewsEntity.setBindTime(fec.getBindTime());
					ewsEntity.getEws().add(fecs);
				}
			}
			
			sessionFactory.getCurrentSession().update(ewsEntity);
		}
		
		return true;
	}
	
	
	
	/***********************************************end   关联前置机和电子秤************************************************/
	
	
	
	
	
	
	
	
	
	
	/***********************************************start 电子秤绑定商户 ************************************************/
	/**
	 * 查询商户
	 * 
	 * @author dx
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Merchant> selectMerchantAll() {
		String hql = "select m from Merchant m ";		
		List<Merchant> list = sessionFactory.getCurrentSession().createQuery(hql).list();
		return list;
	}
	
	
	/**
	 * 查询带回商户信息
	 * 
	 * @author dx
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public void selectMerchantDetailList(Page<Merchant> page,OrderCondition order,Merchant merchant) {
		Session session=sessionFactory.getCurrentSession();
		StringBuffer hql=new StringBuffer();
		hql.append("select merchant from Merchant merchant where 1=1");
		/*if(StringUtils.isNotEmpty(approach.getBatchId())){
			hql.append(" and approach.batchId like '%"+approach.getBatchId()+'%'+"'");
		}
		if(StringUtils.isNotEmpty(approach.getWholesalerName())){
			hql.append(" and approach.wholesalerName like '%"+approach.getWholesalerName()+'%'+"'");
		}
		if(StringUtils.isNotEmpty(approach.getGoodsName())){
			hql.append(" and approach.goodsName like '%"+approach.getGoodsName()+'%'+"'");
		}*/
		hql.append(order.toSql());
		Query queryCount=session.createQuery(hql.toString());	
		page.setTotalRows(queryCount.list().size());
		Query query=session.createQuery(hql.toString());
		query.setMaxResults(page.getPageSize());
		query.setFirstResult((page.getPageIndex()-1)*page.getPageSize());
		List<Merchant> merchantList=query.list();
		page.setRows(merchantList);
	}
	
	/**
	 * 通过商户编码查询对象
	 * 
	 * @author dx
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public Merchant selectByBizId(String bizNo) {
		String hql = "select mer from Merchant mer where mer.bizNo=?";	
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0,bizNo);
		List<Merchant> list=query.list();
		
		return list!=null?list.get(0):null;
	}
	
	/**
	 * 根据电子秤封装的信息更新数据
	 * 
	 * @author dx  
	 */
	@Transactional
	public boolean doBindMerchant(String ewsId,Merchant mer,EwsEntity ews) {
		EwsEntity ew=new EwsEntity();
		ew.setEwsId(Integer.parseInt(ewsId));
		ew=selectById(ew);
		ew.setSalerName(mer.getBizName());
		ew.setSalerNumber(mer.getBizNo());
		ew.setBindTime(ews.getBindTime());
		sessionFactory.getCurrentSession().update(ew);
		return true;
	}
	
	
	
	/**
	 * 
	 * 前置机添加
	 * 
	 * @author dx
	 */
	//@Transactional
	//public boolean doFecAdd(EwsEntity ews,String[] fecid){
	//	if(fecid.length!=0&&!(fecid[0].equals(""))){
		//	EwsEntity ewsEntity = (EwsEntity) sessionFactory.getCurrentSession().get(EwsEntity.class,ews.getEwsId());	
		/*	Set<FeComputerEntity> fec = new HashSet<FeComputerEntity>();
			ewsEntity.setFes(fec);
			sessionFactory.getCurrentSession().update(ewsEntity);
			for(int i = 0;i<fecid.length;i++){
				FeComputerEntity fecs = new FeComputerEntity();
				fecs.setFecId(Integer.parseInt(fecid[i]));
				ewsEntity.getFes().add(fecs);
			}
			sessionFactory.getCurrentSession().update(ewsEntity);
		}
		return true;
	}*/
	
	
	
	/***********************************************end   电子秤绑定商户************************************************/
	
	
	
	
	
	
	
	
	
	/***********************************************start 公告管理 ************************************************/
	/**
	 * 查询电子秤列表数据
	 * 
	 * @author dx
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public void selectNoticeList(Page<NoticeEntity> page,OrderCondition order,NoticeEntity nt,String startdate,String enddate) {
		Session session=sessionFactory.getCurrentSession();
		StringBuffer hql=new StringBuffer();
		hql.append("select nt from NoticeEntity nt where 1 = 1");
		
		//用于检索查询
		if(StringUtils.isNotEmpty(nt.getNoticeUser())){
			hql.append(" and nt.noticeUser like '%"+nt.getNoticeUser()+'%'+"'");
		}
		if(StringUtils.isNotEmpty(nt.getContent())){
			hql.append(" and nt.content like '%"+nt.getContent()+'%'+"'");
		}
		if(startdate!=null&&!(startdate.isEmpty())&&startdate!=""){
			hql.append(" and nt.ctime >= '"+startdate+"'");
		}
		if(enddate!=null&&!(enddate.isEmpty())&&enddate!=""){
			hql.append(" and nt.ctime <= '"+enddate+"'");
		}
		
		hql.append(order.toSql());
		Query queryCount=session.createQuery(hql.toString());	
		page.setTotalRows(queryCount.list().size());
		Query query=session.createQuery(hql.toString());
		query.setMaxResults(page.getPageSize());
		query.setFirstResult((page.getPageIndex()-1)*page.getPageSize());
		List<NoticeEntity> noticeList=query.list();
		page.setRows(noticeList);	
	}
	
	/**
	 * 公告添加
	 * 
	 * @author dx
	 */
	@Transactional
	public int noticeAdd(NoticeEntity notice){
		/*String hql = "select notice from NoticeEntity notice where notice.salerName = ?";		
		List<EwsEntity> list = sessionFactory.getCurrentSession().createQuery(hql).setParameter(0, ews.getSalerName()).list();
		if(list.size()>0){
			return 2;
		}else{
			sessionFactory.getCurrentSession().save(ews);
		}*/
		sessionFactory.getCurrentSession().save(notice);
		
		return 1;
		
	}
	
	/**
	 * 公告修改跳转-查询要修改的数据
	 * 
	 * @author dx
	 * @throws  
	 */
	@Transactional
	public NoticeEntity toUpdateNotice(NoticeEntity notice) {
		NoticeEntity noticeEntity = (NoticeEntity) sessionFactory.getCurrentSession().get(NoticeEntity.class,notice.getNoticeId());	
		return noticeEntity;
	}
	
   /**
	 * 公告修改
	 * 
	 * @author dx
	 * @throws 
	 */
	@Transactional
	public boolean noticeUpdate(NoticeEntity notice) {
		sessionFactory.getCurrentSession().update(notice);
		return true;
	}
	
	/**
	 * 公告删除
	 * 
	 * @author dx
	 * @throws 
	 */
	@Transactional
	public boolean deleteNotice(NoticeEntity notice) {
		NoticeEntity noticeEntity = (NoticeEntity) sessionFactory.getCurrentSession().get(NoticeEntity.class, notice.getNoticeId());
		sessionFactory.getCurrentSession().delete(noticeEntity);
		return true;
	}
	
	/***********************************************end   公告管理************************************************/
}
