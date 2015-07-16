package org.dppc.mtrace.app.detection.service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dppc.mtrace.app.approach.entity.ApproachEntity;
import org.dppc.mtrace.app.detection.entity.DetectionEntity;
import org.dppc.mtrace.frame.base.OrderCondition;
import org.dppc.mtrace.frame.base.Page;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DetectionService {
	
	@Autowired
	SessionFactory sessionFactory;
	
	/**
	 * 查询检测信息
	 * 
	 * @author sunlong
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public void selectDetectionList(Page<DetectionEntity> page,OrderCondition order,DetectionEntity detection,String dateStart,String dateEnd) {
		Session session=sessionFactory.getCurrentSession();
		StringBuffer hql=new StringBuffer();
		hql.append("select detection from DetectionEntity detection where 1=1 ");
		if(StringUtils.isNotEmpty(detection.getBatchType())){
			hql.append(" and detection.batchType like '%"+detection.getBatchType()+'%'+"'");
		}
		if(StringUtils.isNotEmpty(detection.getWholesalerName())){
			hql.append(" and detection.wholesalerName like '%"+detection.getWholesalerName()+'%'+"'");
		}
		if(StringUtils.isNotEmpty(detection.getGoodsName())){
			hql.append(" and detection.goodsName like '%"+detection.getGoodsName()+'%'+"'");
		}
		if(dateStart!=null&&!(dateStart.isEmpty())&&dateStart!=""){
			hql.append(" and detection.detectionDate >= '"+dateStart+"'");
		}
		if(dateEnd!=null&&!(dateEnd.isEmpty())&&dateEnd!=""){
			hql.append(" and detection.detectionDate <= '"+dateEnd+"'");
		}
		hql.append(order.toSql());
		Query queryCount=session.createQuery(hql.toString());	
		page.setTotalRows(queryCount.list().size());
		Query query=session.createQuery(hql.toString());
		query.setMaxResults(page.getPageSize());
		query.setFirstResult((page.getPageIndex()-1)*page.getPageSize());
		List<DetectionEntity> detectionEntity=query.list();
		page.setRows(detectionEntity);
	}
	
	/**
	 * 查询检测信息
	 * 
	 * @author sunlong
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public void selectApproachDetailList(Page<ApproachEntity> page,OrderCondition order,ApproachEntity approach) {
		Session session=sessionFactory.getCurrentSession();
		StringBuffer hql=new StringBuffer();
		hql.append("select approach from ApproachEntity approach where  approach.uploadTime=null");
		if(StringUtils.isNotEmpty(approach.getBatchId())){
			hql.append(" and approach.batchId like '%"+approach.getBatchId()+'%'+"'");
		}
		if(StringUtils.isNotEmpty(approach.getWholesalerName())){
			hql.append(" and approach.wholesalerName like '%"+approach.getWholesalerName()+'%'+"'");
		}
		if(StringUtils.isNotEmpty(approach.getGoodsName())){
			hql.append(" and approach.goodsName like '%"+approach.getGoodsName()+'%'+"'");
		}
		hql.append(order.toSql());
		Query queryCount=session.createQuery(hql.toString());	
		page.setTotalRows(queryCount.list().size());
		Query query=session.createQuery(hql.toString());
		query.setMaxResults(page.getPageSize());
		query.setFirstResult((page.getPageIndex()-1)*page.getPageSize());
		List<ApproachEntity> approachList=query.list();
		page.setRows(approachList);
	}
	
	/**
	 * 添加检测信息
	 * 
	 * @author sunlong
	 */
	@Transactional
	public boolean doAddDetection(DetectionEntity detection,String approachId){
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        String uploadDate = sdf.format(detection.getUploadTime()); 
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        ts = Timestamp.valueOf(uploadDate);
		ApproachEntity approach = (ApproachEntity) sessionFactory.getCurrentSession().get(ApproachEntity.class, Integer.parseInt(approachId));
		approach.setUploadTime(ts);
		sessionFactory.getCurrentSession().update(approach);
		sessionFactory.getCurrentSession().save(detection);
		return true;
	}
	
	/**
	 * 查看检测信息明细
	 * 
	 * @author sunlong
	 */
	@Transactional
	public DetectionEntity showDetectionDetail(DetectionEntity detection){
		DetectionEntity detectionEntity = (DetectionEntity)sessionFactory.getCurrentSession().get(DetectionEntity.class, detection.getMdId());
		return detectionEntity;
	}
	
}
