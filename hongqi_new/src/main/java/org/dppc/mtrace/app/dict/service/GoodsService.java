package org.dppc.mtrace.app.dict.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.dppc.mtrace.app.dict.entity.GoodsEntity;
import org.dppc.mtrace.frame.kit.StringKit;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsService {

	@Autowired
	private SessionFactory sessionFactory;
	
	
	/**
	 * 递归循环遍历蔬菜
	 * @return
	 */
	public List<GoodsEntity> recursiveGoodsList(GoodsEntity goods){
		StringBuffer sql=new StringBuffer();
		sql.append("select goods from GoodsEntity goods where 1=1 ");
		boolean flag=false;
		if(goods!=null){
			if(StringUtils.isNotEmpty(goods.getGoodsName())){
				sql.append(" and goods.goodsName like ");
				sql.append("'%"+goods.getGoodsName()+"%'");
				flag=true;
			}
			if(StringUtils.isNotEmpty(goods.getFirstLetter())){
				sql.append(" and goods.firstLetter like ");
				sql.append("'%"+goods.getFirstLetter().toLowerCase()+"%'");		
				flag=true;
			}
			if(StringUtils.isNotEmpty(goods.getPinYin())){
				sql.append(" and goods.pinYin like ");
				sql.append("'%"+goods.getPinYin().toLowerCase()+"%'");
				flag=true;
			}
		}
		Query query=sessionFactory.getCurrentSession().createQuery(sql.toString());
		
		@SuppressWarnings("unchecked")
		List<GoodsEntity> goodsList=query.list();
		//新建集合存放新的商品
		List<GoodsEntity> list =new ArrayList<GoodsEntity>();
		//若按条件查询则不采用遍历方式输出
		if(flag){
			return goodsList;
		}
		if (CollectionUtils.isNotEmpty(goodsList)) {
			fillChildsList(list, "0", goodsList);
		}
		return list;
	}

	// 填充 子集合
	private void fillChildsList(List<GoodsEntity> childList, String preCode,
			List<GoodsEntity> goodsList) {
		
		for (GoodsEntity goods : goodsList) {
			//非空验证
			if(StringUtils.isEmpty(goods.getGoodsAlias())){
				goods.setGoodsAlias("");
			}
			if(StringUtils.isEmpty(goods.getFirstLetter())){
				goods.setFirstLetter("");
			}
			if(StringUtils.isEmpty(goods.getPinYin())){
				goods.setPinYin("");
			}
			if (StringKit.equals(goods.getPreCode(), preCode)) {
				childList.add(goods);
				// 查找这个goods的子类 并填充
				fillChildsList(goods.getGoodsList(), goods.getGoodsCode(), goodsList);
			} 
		}
	}
	
	/**
	 * 添加商品
	 * @param gs
	 * @return
	 */
	@Transactional
	public int addOrUpdateGoods(GoodsEntity gs){
		if(StringUtils.isNotEmpty(gs.getPinYin())){
			gs.setPinYin(gs.getPinYin().toLowerCase());
		}
		if(StringUtils.isNotEmpty(gs.getFirstLetter())){
			gs.setFirstLetter(gs.getFirstLetter().toLowerCase());
		}
		sessionFactory.getCurrentSession().saveOrUpdate(gs);
		return 1;
	}
	
	/**
	 * 根据编号获取商品详情
	 * @param goodsId
	 * @return
	 */
	public GoodsEntity getGoods(String goodsId){
		GoodsEntity gs=(GoodsEntity) sessionFactory.getCurrentSession().get(GoodsEntity.class,Integer.parseInt(goodsId));
		return gs;
	}
	
	
}
