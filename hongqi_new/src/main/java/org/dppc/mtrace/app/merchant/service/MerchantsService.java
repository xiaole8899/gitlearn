 package org.dppc.mtrace.app.merchant.service;


import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.dppc.mtrace.app.approach.entity.SupplyMarketEntity;
import org.dppc.mtrace.app.merchant.entity.Merchant;
import org.dppc.mtrace.frame.base.OrderCondition;
import org.dppc.mtrace.frame.base.Page;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商户信息业务类
 * @author hle
 *
 */
@Service
public class MerchantsService {

	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * 添加商户
	 * @param merchant
	 * @return
	 */
	@Transactional
	public int addMerchant(Merchant merchant){
		sessionFactory.getCurrentSession().save(merchant);
		return 1;
	}
	
	/**
	 * 根据条件分页并排序
	 * @param page
	 * @param order
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public void getMerchatList(Page<Merchant> page,OrderCondition order,Merchant merchant){
		StringBuffer hql=new StringBuffer();
		
		hql.append("select merchant from Merchant merchant where 1=1 ");
		//按商户名称查询
		if(StringUtils.isNotEmpty(merchant.getBizName())){
			hql.append(" and merchant.bizName like '%"+merchant.getBizName()+'%'+"'");
		}
		//按商户备案号查询
		if(StringUtils.isNotEmpty(merchant.getBizNo())){
			hql.append(" and merchant.bizNo ='"+merchant.getBizNo()+"'");
		}
		//按商户类型查询
		if(StringUtils.isNotEmpty(merchant.getProperty())){
			hql.append(" and merchant.property ='"+merchant.getProperty()+"'");
		}
		//按商户经营类型
		if(StringUtils.isNotEmpty(merchant.getBusinessType())){
			hql.append(" and merchant.businessType ='"+merchant.getBusinessType()+"'");
		}
		//按照商户类型查询
		if(StringUtils.isNotEmpty(merchant.getBizType())){
			hql.append(" and merchant.bizType ='"+merchant.getBizType()+"'");
		}
		Query count=sessionFactory.getCurrentSession().createQuery(hql.toString());
		if(count.list()!=null){
			page.setTotalRows(count.list().size());
		}else{
			page.setTotalRows(0);
		}
		//排序
		if(order!=null){
			hql.append(order.toSql());
		}
		Query queryList=sessionFactory.getCurrentSession().createQuery(hql.toString());
		queryList.setMaxResults(page.getPageSize());
		//设置开始位置(按下标)
		queryList.setFirstResult(page.getPageSize()*(page.getPageIndex()-1));
		page.setRows(queryList.list());
	}
	
	
	/**
	 * 根据mId获取商户详细信息
	 * @param mId
	 * @return
	 */
	public Merchant getMerchantByMId(String mId){
		Merchant merchants=(Merchant) sessionFactory.getCurrentSession().get(Merchant.class,Integer.parseInt(mId));
		return merchants;
	}
	
	/**
	 * 根据商户备案号查询商户信息
	 * @param bizNo 商户备案号
	 * @return
	 */
	public Merchant getMerchantByBizNo(String bizNo) {
		String hql="select merchant from Merchant merchant where 1=1  and merchant.bizNo=?";
		Query query=sessionFactory.getCurrentSession().createQuery(hql).setParameter(0,bizNo);
		@SuppressWarnings("unchecked")
		List<Merchant> list=query.list();
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	
	/**
	 * 修改
	 * @param merchants
	 */
	@Transactional
	public int updateMerchant(Merchant merchants){
		sessionFactory.getCurrentSession().saveOrUpdate(merchants);
		return 1;
	}
	
	
	/**
	 * 注销商户
	 * @param mId
	 * @return
	 */
	@Transactional
	public int deleteMerchant(String mId){
		//hql语句
		/*String hql="delete Merchant merchant  where merchant.id=?";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		//设置参数
		query.setParameter(0,Integer.parseInt(mId));*/
		//执行更改
		Merchant merchant=getMerchantByMId(mId);
		sessionFactory.getCurrentSession().delete(merchant);
		return 1;			
	}
	
	/**
	 * 查询供货市场集合
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SupplyMarketEntity> getSupplyList(){
		String hql="select su from  SupplyMarketEntity su";
		return sessionFactory.getCurrentSession().createQuery(hql).list();
	}
	
	/**
	 * 根据编号查询供货市场
	 * @param market
	 * @return
	 *@author weiyuzhen
	 */
	public SupplyMarketEntity getSupplyDetails(String suId){
		SupplyMarketEntity suMarket = (SupplyMarketEntity) sessionFactory.getCurrentSession().get(SupplyMarketEntity.class,Integer.parseInt(suId));
		return suMarket;
	}
	
	
	/**
	 * 根据商户编号查看信息是否已上传平台
	 * @param bizId
	 * @return
	 */
	public int  isDelete(String bizId){
		String hql="select count(merchant) from Merchant merchant where merchant.uploadTime is null and merchant.bizId=?";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0,Integer.parseInt(bizId));
		@SuppressWarnings("rawtypes")
		List list=query.list();
		long num=0;
		if(list!=null){
			num=Long.parseLong(list.get(0).toString());
		}
		return Integer.parseInt(String.valueOf(num));
		
	}
	
	/**
	 * 商户充值
	 * @param merchant 商户
	 */
	@Transactional
	public int merchantRecharge(Merchant merchant){
		sessionFactory.getCurrentSession().update(merchant);
		return 1;
	}
	
	/**
	 * 获取没有卡的用户
	 * @param page
	 * @param order
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public void getNoCardMerchant(Page<Merchant> page,OrderCondition order,Merchant merchant){
		StringBuffer hql=new StringBuffer();
		
		hql.append("select merchant from Merchant merchant left join merchant.cards c where c.cardNo is null or c.cardStatus!=0");
		//按商户名称查询
		if(StringUtils.isNotEmpty(merchant.getBizName())){
			hql.append(" and merchant.bizName like '%"+merchant.getBizName()+'%'+"'");
		}
		//按商户备案号查询
		if(StringUtils.isNotEmpty(merchant.getBizNo())){
			hql.append(" and merchant.bizNo ='"+merchant.getBizNo()+"'");
		}
		//按商户类型查询
		if(StringUtils.isNotEmpty(merchant.getProperty())){
			hql.append(" and merchant.property ='"+merchant.getProperty()+"'");
		}
		//按商户经营类型
		if(StringUtils.isNotEmpty(merchant.getBusinessType())){
			hql.append(" and merchant.businessType ='"+merchant.getBusinessType()+"'");
		}
		//按照商户类型查询
		if(StringUtils.isNotEmpty(merchant.getBizType())){
			hql.append(" and merchant.bizType ='"+merchant.getBizType()+"'");
		}
		Query count=sessionFactory.getCurrentSession().createQuery(hql.toString());
		if(count.list()!=null){
			page.setTotalRows(count.list().size());
		}else{
			page.setTotalRows(0);
		}
		//排序
		if(order!=null){
			hql.append(order.toSql());
		}
		Query queryList=sessionFactory.getCurrentSession().createQuery(hql.toString());
		queryList.setMaxResults(page.getPageSize());
		//设置开始位置(按下标)
		queryList.setFirstResult(page.getPageSize()*(page.getPageIndex()-1));
		page.setRows(queryList.list());
	}
}
