package org.dppc.mtrace.app.analysis.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.dppc.mtrace.app.analysis.entity.GoodsPriceEntity;
import org.dppc.mtrace.app.dict.entity.GoodsEntity;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsPriceService {

	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * 获取全部交易商品
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Transactional
	public List getMarketTranInfoDetails(){
		String sql="select DISTINCT(goods_code),goods_name from t_trade_dtl";
		SQLQuery sqlQuery= sessionFactory.getCurrentSession().createSQLQuery(sql);
		return sqlQuery.list();
	}
	
	/**
	 * 获取全部已出售商品信息
	 * @return
	 */
	public List<GoodsEntity> getGoodsEntities(){
		List<GoodsEntity> goodsList=new ArrayList<GoodsEntity>();
		@SuppressWarnings("rawtypes")
		List details=getMarketTranInfoDetails();
		for(int i=0;i<details.size();i++){
			Object [] obj=(Object [])details.get(i);
			GoodsEntity gs=new GoodsEntity();
			for(int j=0;j<obj.length;j++){
				if(j==0){
					gs.setGoodsCode((String)obj[0]);
				}
				if(j==1){
					gs.setGoodsName((String)obj[1]);
				}
			}
			goodsList.add(gs);
		}
		return goodsList;
	}
	
	/**
	 * 根据商品编号查询商品详情
	 * @param goodsId
	 * @return
	 */
	@Transactional
	public GoodsEntity getGoodsEntity(String goodsCode){
		String hql="from GoodsEntity goods where goods.goodsCode=:goodsCode";
		Query query=sessionFactory.getCurrentSession().createQuery(hql).setParameter("goodsCode",goodsCode);
		@SuppressWarnings("unchecked")
		List<GoodsEntity> goodsEntities=query.list();
		if(goodsEntities!=null && goodsEntities.size()>0){
			return goodsEntities.get(0);
		}
		return null;
	}
	
	/**
	 * 根据条件获取选中的商品价格数据
	 * @return
	 */
	public List<GoodsPriceEntity> getGoodsPriceList(String goodsCode,String StartDate,String endDate){
		String sql="select avg(price) goodsPrice,d.goods_name goodsName,date_format(tran_date,'%Y-%m-%d') detailDate from t_trade_dtl d,t_trade_hdr h where h.hdr_id=d.hdr_id"
				+ " and goods_code=? "
				+" and date_format(tran_date,'%Y-%m-%d') BETWEEN ? and ? "
				+ " GROUP BY  date_format(tran_date,'%Y-%m-%d')";
		SQLQuery sqlQuery=sessionFactory.getCurrentSession().createSQLQuery(sql);
		sqlQuery.setParameter(0,goodsCode);
		sqlQuery.setParameter(1,StartDate);
		sqlQuery.setParameter(2, endDate);
		List<GoodsPriceEntity> goodsPriceList=new ArrayList<GoodsPriceEntity>();;
		@SuppressWarnings("rawtypes")
		List list=sqlQuery.list();
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				Object [] obj=(Object [])list.get(i);
				GoodsPriceEntity goodsPriceEntity=new GoodsPriceEntity();
				for(int j=0;j<obj.length;j++){
					//价格赋值
					if(j==0){
						DecimalFormat df = new DecimalFormat("####.0");
						goodsPriceEntity.setGoodsPrice(Double.parseDouble(df.format((Double)obj[0])));
					}
					//名称赋值
					if(j==1){
						goodsPriceEntity.setGoodsName((String)obj[1]);
					}
					if(j==2){
						goodsPriceEntity.setDetailDate((String)obj[2]);
					}
				}
				goodsPriceList.add(goodsPriceEntity);
			}
		}
		return goodsPriceList;
	}
}
