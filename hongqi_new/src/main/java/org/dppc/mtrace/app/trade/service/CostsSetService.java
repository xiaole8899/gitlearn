package org.dppc.mtrace.app.trade.service;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.dppc.mtrace.app.dict.entity.GoodsEntity;
import org.dppc.mtrace.app.trade.entity.CostsSet;
import org.dppc.mtrace.frame.base.OrderCondition;
import org.dppc.mtrace.frame.base.Page;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CostsSetService {

	
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * 根据条件分页并排序
	 * @param page
	 * @param order
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public void getCostsList(Page<CostsSet> page,OrderCondition order,CostsSet costsSet){
		StringBuffer hql=new StringBuffer();
		hql.append("select costs from CostsSet costs ");
		if(StringUtils.isNotEmpty(costsSet.getCostsName())){
			hql.append(" where costs.costsName like '%"+costsSet.getCostsName()+"%'");
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
	 * 添加费用规则
	 * @param costs
	 * @return
	 */
	@Transactional
	public int addCosts(CostsSet costs){
		sessionFactory.getCurrentSession().save(costs);
		return 1;
	}
	
	/**
	 * 添加费用规则
	 * @param costs
	 * @return
	 */
	@Transactional
	public int updateCosts(CostsSet costs){
		sessionFactory.getCurrentSession().update(costs);
		return 1;
	}
	
	/**
	 * 根据条件获取全部最底层商品
	 * @param goodsEntity
	 * @return
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public List<GoodsEntity> getGoodsList(GoodsEntity goodsEntity){
		StringBuffer hql = new StringBuffer();
		hql.append("select goods  from  GoodsEntity  goods where  goods.goodsCode not in(select preGoods.preCode from GoodsEntity preGoods) and goods.goodsCode !=0  ");
		if(StringUtils.isNotEmpty(goodsEntity.getGoodsName())){
			hql.append(" and goods.goodsName like '%"+goodsEntity.getGoodsName()+"%'");
		}
		if(StringUtils.isNotEmpty(goodsEntity.getGoodsCode())){
			hql.append(" and goods.goodsCode like '%"+goodsEntity.getGoodsCode()+"%'");
		}
		Query query =sessionFactory.getCurrentSession().createQuery(hql.toString());
		List<GoodsEntity> listGoods=query.list();
		if(listGoods!=null && listGoods.size()>0){
			return listGoods;
		}
		return null;
	}
	
	/**
	 * 根据编号获取详细费用信息
	 * @param costsId
	 * @return
	 */
	@Transactional
	public CostsSet getCosts(Integer costsId){
		StringBuffer hql=new StringBuffer();
		hql.append("select costs from CostsSet costs where costs.costsId=:costsId");
		Query query=sessionFactory.getCurrentSession().createQuery(hql.toString());
		query.setParameter("costsId",costsId);
		@SuppressWarnings("unchecked")
		List<CostsSet> costsList=query.list();
		if(costsList!=null && costsList.size()>0){
			return costsList.get(0);
		}
		return null;
	}
	
	/**
	 * 根据编号删除费用信息
	 * @param costsId
	 * @return
	 */
	@Transactional
	public int deleteCost(Integer costsId){
		sessionFactory.getCurrentSession().delete(getCosts(costsId));
		return 1;
	}
}
