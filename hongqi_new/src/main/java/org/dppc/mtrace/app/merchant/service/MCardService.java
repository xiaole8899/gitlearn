package org.dppc.mtrace.app.merchant.service;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.dppc.mtrace.app.merchant.entity.MCard;
import org.dppc.mtrace.app.merchant.entity.Merchant;
import org.dppc.mtrace.frame.base.Page;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MCardService {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private MerchantsService MerchantsService;
	
	/**
	 * 开卡
	 * @param mcard 卡信息
	 * @param mId   开卡人
	 * @return
	 */
	@Transactional
	public int addCard(MCard mcard,String mId){
		Merchant merchant=MerchantsService.getMerchantByMId(mId);
		mcard.setMerchant(merchant);
		mcard.setHolderTel(merchant.getTel());
		mcard.setCardHolder(merchant.getBizName());
		if(merchant.getProperty().equals("个体")){
			mcard.setCardHolderIdentity(merchant.getIdentityCard());
		}else{
			mcard.setCardHolderIdentity(merchant.getRegId());
		}
		sessionFactory.getCurrentSession().save(mcard);
		return 1;
	}
	
	/**
	 * 判断用户是否已经有主卡
	 * @param mId
	 * @return
	 */
	@Transactional
	public int isHasMainCard(String mId){
		String hql="select count(mc) from MCard mc where mc.merchant.id=? and mc.cardKind=? and mc.cardStatus=?";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0,Integer.parseInt(mId));
		query.setParameter(1,"01");
		query.setParameter(2,"0");
		@SuppressWarnings("rawtypes")
		List list=query.list();
		long num=0;
		if(list!=null){
			num=Integer.parseInt(list.get(0).toString());
		}
		return Integer.parseInt(String.valueOf(num));
	}
	
	
	/**
	 * 判断用户是否已经开卡
	 * @param mId
	 * @return
	 */
	@Transactional
	public int isHasCard(String mId){
		String hql="select count(mc) from MCard mc where mc.merchant.id=?";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0,Integer.parseInt(mId));
		@SuppressWarnings("rawtypes")
		List list=query.list();
		long num=0;
		if(list!=null){
			num=Integer.parseInt(list.get(0).toString());
		}
		return Integer.parseInt(String.valueOf(num));
	}
	
	
	
	/**
	 * 根据卡号获取详细信息
	 * @param cId 卡编号
	 * @return
	 */
	public MCard getMcard(String cId){
		MCard mCard=(MCard) sessionFactory.getCurrentSession().get(MCard.class,Integer.parseInt(cId));
		return mCard;
	}
	
	/**
	 * 挂失或者解挂或者注销
	 * @param status
	 * @return
	 */
	@Transactional
	public int lossOrSolutionCard(String status,String cId){
		MCard mCard=getMcard(cId);
		mCard.setCardStatus(status);
		sessionFactory.getCurrentSession().update(mCard);
		return 1;
	} 
	
	/**
	 * 查询商户注销的卡并分页
	 * @param page
	 */
	@SuppressWarnings("unchecked")
	public void cancelCardList(Page<MCard> page,MCard mCard){
		String hql="select mcard from MCard mcard where mcard.cardStatus='2' ";
		if(StringUtils.isNotEmpty(mCard.getCardNo())){
			hql+=" and mcard.cardNo="+mCard.getCardNo()+"";
		}
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		
		if(query.list()!=null){
			page.setTotalRows(query.list().size());
		}
		
		query.setMaxResults(page.getPageSize());
		query.setFirstResult(page.getPageSize()*(page.getPageIndex()-1));
		if(query.list()!=null){
			page.setRows(query.list());
		}
	}
	
	/**
	 * 根据卡的编号删除卡(永久删除)
	 * @param cId 卡号
	 * @return
	 */
	@Transactional
	public int deleteMCard(String cId){
		MCard mCard=getMcard(cId);
		mCard.setMerchant(null);
		sessionFactory.getCurrentSession().delete(mCard);
		return 1;
	}
	
	/**
	 * 根据卡号查询卡详情
	 * @param cardNo
	 * @return
	 */
	@Transactional
	public MCard getCardInfoByCardNo(String cardNo){
		String hql="select mcard from MCard mcard where mcard.cardNo=:cardNo";
		Query query=sessionFactory.getCurrentSession().createQuery(hql).setParameter("cardNo",cardNo);
		@SuppressWarnings("unchecked")
		List<MCard> cardList=query.list();
		if(cardList!=null && cardList.size()>0){
			return cardList.get(0);
		}
		return null;
	}
}
