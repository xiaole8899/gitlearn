package org.dppc.mtrace.app.merchant.service;

import org.apache.commons.lang.StringUtils;
import org.dppc.mtrace.app.merchant.entity.FundsRecord;
import org.dppc.mtrace.app.merchant.entity.MCard;
import org.dppc.mtrace.frame.base.OrderCondition;
import org.dppc.mtrace.frame.base.Page;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**资金变动记录
 * @author teng
 *
 */
@Service
public class FundsRecordService {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private MCardService MCardService;

	/**
	 * 资金变动记录(买卖双方交易);
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public void tradeRecordList(Page<FundsRecord> page, OrderCondition order,
			String startdate,String enddate,FundsRecord record) {
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select record from FundsRecord record where record.type='002' ");
		if (StringUtils.isNotEmpty(record.getUserName())) {
			hql.append(" and record.userName like '%"+record.getUserName()+'%'+"'");
		}
		if (StringUtils.isNotEmpty(record.getUserNo())) {
			hql.append(" and record.userNo like '%"+record.getUserNo()+'%'+"'");
		}
		if (StringUtils.isNotEmpty(record.getTranId())) {
			hql.append(" and record.tranId like '"+record.getTranId()+'%'+"'");
		}
		if(startdate!=null&&!(startdate.isEmpty())&&startdate!=""){
			hql.append(" and record.changeTime >= '"+startdate+"'");
		}
		if(enddate!=null&&!(enddate.isEmpty())&&enddate!=""){
			hql.append(" and record.changeTime <= '"+enddate+"'");
		}
		Query count = sessionFactory.getCurrentSession().createQuery(hql.toString());
		
		if (count.list() != null) {
			page.setTotalRows(count.list().size());
		} else {
			page.setTotalRows(0);
		}
		if (order != null) {
			hql.append(order.toSql());
		}
		Query queryList = sessionFactory.getCurrentSession().createQuery(hql.toString());
		queryList.setMaxResults(page.getPageSize());
		queryList.setFirstResult(page.getPageSize()*(page.getPageIndex()-1));
		page.setRows(queryList.list());
	}
	
	/**
	 * 添加资金走向记录
	 * @param fundsRecord
	 * @return
	 */
	@Transactional
	public int addFundsRecord(FundsRecord fundsRecord){
		sessionFactory.getCurrentSession().save(fundsRecord);
		return 1;
	}
	
	/**
	 * 充值记录查询
	 * @param page 
	 * @param order
	 * @param startDate 开始日期
	 * @param endDate   结束日期
	 * @param bizName   持卡人
	 * @param identityCard 持卡人证件
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public void rechargeList(Page<FundsRecord> page, OrderCondition order,String startDate
			,String endDate,String bizName,String identityCard,String cardNo) {
		StringBuffer hql = new StringBuffer();
		hql.append("select record from FundsRecord record,Merchant merchant where merchant.bizNo=record.userNo  and record.type!='002' ");
		
		//持卡人
		if (StringUtils.isNotEmpty(bizName)) {
			hql.append(" and record.userName like '%"+bizName+'%'+"'");
		}
		
		//证件
		if(StringUtils.isNotEmpty(identityCard)){
			hql.append(" and merchant.identityCard= '"+identityCard+"'");
		}
		
		//开始日期
		if(startDate!=null&&!(startDate.isEmpty())&&startDate!=""){
			hql.append(" and record.changeTime >= '"+startDate+"'");
		}
		
		//结束日期
		if(endDate!=null&&!(endDate.isEmpty())&&endDate!=""){
			hql.append(" and record.changeTime <= '"+endDate+"'");
		}
		
		//若卡号不为空
		if(StringUtils.isNotEmpty(cardNo)){
			//按卡号查询
			MCard mCard=MCardService.getCardInfoByCardNo(cardNo);
			if(mCard!=null){
				hql.append(" and merchant.identityCard= '"+mCard.getMerchant().getIdentityCard()+"'");
			}else{
				//制造找不到的条件
				hql.append(" and merchant.identityCard= 'aaaa'");
			}
		}
		
		Query count = sessionFactory.getCurrentSession().createQuery(hql.toString());
		
		if (count.list() != null) {
			page.setTotalRows(count.list().size());
		} else {
			page.setTotalRows(0);
		}
		if (order != null) {
			hql.append(order.toSql());
		}
		Query queryList = sessionFactory.getCurrentSession().createQuery(hql.toString());
		queryList.setMaxResults(page.getPageSize());
		queryList.setFirstResult(page.getPageSize()*(page.getPageIndex()-1));
		page.setRows(queryList.list());
	}
	
}
