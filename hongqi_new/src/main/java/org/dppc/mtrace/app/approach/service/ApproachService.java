package org.dppc.mtrace.app.approach.service;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.dppc.mtrace.app.approach.entity.ApproachEntity;
import org.dppc.mtrace.app.dict.entity.GoodsEntity;
import org.dppc.mtrace.app.ews.entity.BalanceEntity;
import org.dppc.mtrace.app.ews.service.BalanceService;
import org.dppc.mtrace.frame.base.OrderCondition;
import org.dppc.mtrace.frame.base.Page;
import org.dppc.mtrace.frame.kit.FileKit;
import org.dppc.mtrace.frame.kit.StringKit;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ApproachService {
	@SuppressWarnings("unused")
	private static final int MM = 0;

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	private BalanceService banceServ;
	
	@Value("${apporach.file.read}")
	private  String url;
	/*
	 * 展示肉菜进场信息
	 * 
	 *@author weiyuzhen
	 */
	@SuppressWarnings("unchecked")
	public void listApproach(Page<ApproachEntity> page,OrderCondition order,ApproachEntity approach,HttpServletRequest request){
		if(StringUtils.isEmpty(String.valueOf(approach.getGoodsType())) ){
			approach.setGoodsType(0);
		}
		//获取session
		Session session = sessionFactory.getCurrentSession();
		StringBuffer hql = new StringBuffer();
		hql.append("select appro  from  ApproachEntity appro where  appro.goodsType="+approach.getGoodsType()+"  ");
		
		 if(StringUtils.isNotEmpty(approach.getWholesalerId())){
				hql.append(" and appro.wholesalerId='"+approach.getWholesalerId()+"' ");
		 }
		 if(StringUtils.isNotEmpty( approach.getWholesalerName() )){
				hql.append(" and appro.wholesalerName like '%"+approach.getWholesalerName()+'%'+"' ");
		 }
		 if(StringUtils.isNotEmpty(approach.getBizType())) {
			    hql.append(" and appro.bizType = "+approach.getBizType()+"");
		 }
		 if(StringUtils.isNotEmpty( approach.getBatchId() )){
			 hql.append(" and appro.batchId ='"+approach.getBatchId()+"'");
		 }
		 if(request.getParameter("startDate")!=null && request.getParameter("startDate")!=""){
			 hql.append(" and appro.inDate >= '"+request.getParameter("startDate")+"' ");
		 }
		 if(request.getParameter("endDate")!=null && request.getParameter("endDate")!=""){
			 hql.append(" and appro.inDate <= '"+request.getParameter("endDate")+"'");
		 }
		
		hql.append(order.toSql());
		Query queryCount = session.createQuery(hql.toString());
		System.out.println("====="+request.getParameter("endDate"));
		page.setTotalRows(queryCount.list().size());  
		Query query = session.createQuery(hql.toString());
		
		//设置下限
		query.setMaxResults(page.getPageSize());
		
		//设置开始位置，按下标
		query.setFirstResult((page.getPageIndex()-1)*page.getPageSize());
		List<ApproachEntity> appro = query.list();
		page.setRows(appro);
	}
	
	/**
	 * 获取子类的商品信息
	 * 
	 *@author weiyuzhen
	 */
	@SuppressWarnings("unchecked")
	public void listGoodsChild(Page<GoodsEntity> page,OrderCondition order,GoodsEntity goods){
		//获取session
 		Session session = sessionFactory.getCurrentSession();
		StringBuffer hql = new StringBuffer();
		hql.append("select goods  from  GoodsEntity  goods where  goods.goodsCode not in(select preGoods.preCode from GoodsEntity preGoods) and goods.goodsCode !=0  ");
	     
		if(StringUtils.isNotEmpty(goods.getGoodsCode())){
			hql.append(" and goods.goodsCode like '%"+goods.getGoodsCode()+"%' ");
		}
		if(StringUtils.isNotEmpty( goods.getGoodsName())){
			hql.append(" and goods.goodsName like '%"+ goods.getGoodsName() +"%' ");
		}
		if(StringUtils.isNotEmpty( goods.getFirstLetter() )){
			hql.append(" and goods.firstLetter like '%"+goods.getFirstLetter()+"%'");
		}
		if(StringUtils.isNotEmpty(goods.getGoodsState())){
			hql.append( " and  goods.goodsStatus= "+goods.getGoodsState()+" ");
		}
		if(StringUtils.isNotEmpty(goods.getPinYin())){
			hql.append(" and goods.pinYin like '%"+goods.getPinYin()+"%'");
		}

		
		hql.append(order.toSql());
		
		Query queryCount = session.createQuery(hql.toString());
		page.setTotalRows(queryCount.list().size());  
		Query query = session.createQuery(hql.toString());
		
		//设置下限
		query.setMaxResults(page.getPageSize());
		
		//设置开始位置，按下标
		query.setFirstResult((page.getPageIndex()-1)*page.getPageSize());
		List<GoodsEntity> goodsList = query.list();
		page.setRows(goodsList);
	}
	
	/**
	 * 增加进场数据
	 * 
	 *@author weiyuzhen
	 */
	@Transactional
	public int addApproachIn(final ApproachEntity approach){
		try{
			Session session = sessionFactory.getCurrentSession();
			Calendar cal = Calendar.getInstance();
			Timestamp now= new Timestamp(cal.getTimeInMillis());//获取当前时间
			approach.setInDate(now);
			approach.setUpdateDate(now);
			session.save(approach);
	
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
		return 1;
		
	}
	
	/**
	 * 往小秤中写文件的实体类
	 */
	public void writeBalanceTxt(ApproachEntity approach) {
		//判断有没有和小秤绑定，有绑定的时候写，没有绑定的时候不做操作
		List<BalanceEntity> banlanceL = banceServ.findBalanceBybizId(approach.getWholesalerId());
		int balanceLength = banlanceL.size();
		if(balanceLength>0){
			//在对应文件下查找是否有当前用户的文件
			for(int i=0;i<balanceLength;i++){
				
				String fileName = "M_Traceno_"+banlanceL.get(i).getBalanceNo();
				try {
					FileKit.createDirectory(url +"/");
					String filenameTemp = url +"/"+fileName+".txt";
					FileKit.creatTxtFile(filenameTemp);
					String newStr = createNewStr(approach);
					FileKit.writeTxtFile(newStr,filenameTemp);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
	}

	/**
	 * 编辑进场数据
	 * 
	 *@author weiyuzhen
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Transactional
	public boolean editApproach(ApproachEntity approach) {
		//每次修改更新--将当前时间更新到实体中
		Calendar cal = Calendar.getInstance();
		Timestamp now = new Timestamp(cal.getTimeInMillis());
		approach.setUpdateDate(now);
		
		Session session = sessionFactory.getCurrentSession();
		ApproachEntity bean =(ApproachEntity) session.get(ApproachEntity.class, approach.getApproachId());
		String oname =bean.getAreaOriginName();
		String oid =bean.getAreaOriginId();
		// 复制 approach 的所有属性值到 bean
		try {
			BeanUtils.copyProperties(bean, approach);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		if (StringKit.isEmpty(bean.getAreaOriginName())) {
			bean.setAreaOriginName(oname);
		}
		if (StringKit.isEmpty(bean.getAreaOriginId())) {
			bean.setAreaOriginId(oid);
		}
		session.update(bean);
		return true;
		
	}
	
	/**
	 * 删除进场数据
	 * 
	 *@author weiyuzhen
	 */
	@Transactional
	public boolean deleteApproach(ApproachEntity approach){
		Session session = sessionFactory.getCurrentSession();
		session.delete(approach);
		return true;
		
	}
	
	/**
	 * 根据条件查找具体的某条进场数据
	 * 
	 *@author weiyuzhen
	 */
	public ApproachEntity findApproach(ApproachEntity approach){
	    Session session1 = sessionFactory.getCurrentSession();
	    ApproachEntity approachD = (ApproachEntity) session1.get(ApproachEntity.class, approach.getApproachId());
		return approachD;
		
	}

	public static String createNewStr(ApproachEntity approach){
		StringBuffer buf = new StringBuffer();
		  DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");  
		buf.append(sdf.format(approach.getInDate()));
		buf.append("\t");
		
		buf.append(StringKit.fillZero(approach.getBatchId(),20));
		buf.append("\t");
		
		buf.append(approach.getWeight());
		buf.append("\t");
		
		buf.append(approach.getGoodsCode());
		buf.append("\t");

		buf.append(approach.getGoodsName());

		return buf.toString();
		
	}
	
	

	
	public static void main(String[] args) {
		System.out.println(StringKit.fillZero("3706025690000058",20));
	}
}
