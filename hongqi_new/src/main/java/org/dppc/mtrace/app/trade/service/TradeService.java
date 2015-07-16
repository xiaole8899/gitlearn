package org.dppc.mtrace.app.trade.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dppc.mtrace.app.approach.entity.ApproachEntity;
import org.dppc.mtrace.app.dict.entity.GoodsEntity;
import org.dppc.mtrace.app.ews.entity.BalanceEntity;
import org.dppc.mtrace.app.merchant.entity.Merchant;
import org.dppc.mtrace.app.trade.entity.CostsSet;
import org.dppc.mtrace.app.trade.entity.MarketTranInfoBase;
import org.dppc.mtrace.app.trade.entity.MarketTranInfoDetail;
import org.dppc.mtrace.frame.base.OrderCondition;
import org.dppc.mtrace.frame.base.Page;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TradeService {
	@Autowired
	SessionFactory sessionFactory;

	@Value("${apporach.file.read}")
	private String approachInUrl;
    /**
     * 展示交易流水主表信息
     */
	@SuppressWarnings("unchecked")
	@Transactional
	public void listMarketTranInfoBase(OrderCondition order,Page<MarketTranInfoBase> page, 
			  MarketTranInfoBase marketTranInfoBase,String startDate, String endDate ){
    	
    	Session session=sessionFactory.getCurrentSession();
    	StringBuffer hql = new StringBuffer();
		hql.append("select tradeBase from MarketTranInfoBase tradeBase where  1=1 ");
		if( StringUtils.isNotEmpty( marketTranInfoBase.getWholesalerName() ) ){
 			hql.append(" and tradeBase.wholesalerName like '%"+marketTranInfoBase.getWholesalerName()+'%'+"'");
		} 
		if(StringUtils.isNotEmpty(marketTranInfoBase.getTranId())) {
			hql.append(" and tradeBase.tranId like '%"+marketTranInfoBase.getTranId()+'%'+"'");
		} 
		if(startDate!=null&&!(startDate.isEmpty())&&startDate!="") {
			hql.append(" and tradeBase.tranDate>='"+startDate+"'");
		} 
		if(endDate!=null&&!(endDate.isEmpty())&&endDate!="") {
			hql.append(" and tradeBase.tranDate<='"+endDate+"'");
			
		}
		hql.append(order.toSql());
		Query queryCount = session.createQuery(hql.toString());
		page.setTotalRows(queryCount.list().size());  
		Query query = session.createQuery(hql.toString());
		
		//设置下限
		query.setMaxResults(page.getPageSize());
		
		//设置开始位置，按下标
		query.setFirstResult((page.getPageIndex()-1)*page.getPageSize());
		List<MarketTranInfoBase> suList = query.list();
		page.setRows(suList);
    	
    }
	
	/**
	 * 根据交易凭证号查询交易详情
	 * @author:weiyuzhen
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<MarketTranInfoBase> queryMarketTranInfoBaseByTranid(String tranId){
		String sql = "select tradeBase from MarketTranInfoBase tradeBase where  tradeBase.tranId = '"+tranId+"' ";
		Session session=sessionFactory.getCurrentSession();
		Query query = session.createQuery(sql);
		List<MarketTranInfoBase> list = query.list();
		return list;
		
	}
	/**
     * 单条展示交易流水细表信息 
     */
	@SuppressWarnings("unchecked")
	@Transactional
	public void listMarketTranInfoDetail(String hdrId,OrderCondition order,Page<MarketTranInfoDetail> page,MarketTranInfoDetail marketTranInfoDetail){
    	
    	Session session=sessionFactory.getCurrentSession();
    	StringBuffer hql = new StringBuffer();
		hql.append("select b from MarketTranInfoDetail b where b.marketTranInfoBase.hdrId="+hdrId);
		if( StringUtils.isNotEmpty( marketTranInfoDetail.getTraceId() ) ){
			hql.append(" and b.traceId like '%"+marketTranInfoDetail.getTraceId()+'%'+"'");
		}
		if( StringUtils.isNotEmpty( marketTranInfoDetail.getGoodsName() ) ){
			hql.append(" and b.goodsName like '%"+marketTranInfoDetail.getGoodsName()+'%'+"'");
		}
		hql.append(order.toSql());
		Query queryCount = session.createQuery(hql.toString());
		page.setTotalRows(queryCount.list().size());  
		Query query = session.createQuery(hql.toString());
		
		//设置下限
		query.setMaxResults(page.getPageSize());
		
		//设置开始位置，按下标
		query.setFirstResult((page.getPageIndex()-1)*page.getPageSize());
		List<MarketTranInfoDetail> suList = query.list();
		page.setRows(suList);
    	
    }
	
	
	/**
     * 展示所有交易流水细表信息
     */
	@SuppressWarnings("unchecked")
	@Transactional
	public void listAllMarketTranInfoDetail(OrderCondition order,Page<MarketTranInfoDetail> page
			,MarketTranInfoDetail marketTranInfoDetail , String startDate, String endDate,String tranId){
    	
    	Session session=sessionFactory.getCurrentSession();
    	StringBuffer hql = new StringBuffer();
		hql.append("select b from MarketTranInfoDetail b where  1=1 ");
		if( StringUtils.isNotEmpty( marketTranInfoDetail.getTraceId() ) ){
			hql.append(" and b.traceId like '%"+marketTranInfoDetail.getTraceId()+'%'+"'");
		}
		if( StringUtils.isNotEmpty( marketTranInfoDetail.getGoodsName() ) ){
			hql.append(" and b.goodsName like '%"+marketTranInfoDetail.getGoodsName()+'%'+"' ");
		}
		if( tranId!=null&&!(tranId.isEmpty())&&tranId!=""){
			hql.append(" and b.marketTranInfoBase.tranId like '%"+tranId+'%'+"'");
		}
		if(startDate!=null&&!(startDate.isEmpty())&&startDate!="") {
			hql.append(" and b.marketTranInfoBase.tranDate>='"+startDate+"'");
		}
		if(endDate!=null&&!(endDate.isEmpty())&&endDate!="") {
			hql.append(" and b.marketTranInfoBase.tranDate<='"+endDate+"'");
		}
		hql.append(order.toSql());
		Query queryCount = session.createQuery(hql.toString());
		page.setTotalRows(queryCount.list().size());  
		Query query = session.createQuery(hql.toString());
		
		//设置下限
		query.setMaxResults(page.getPageSize());
		
		//设置开始位置，按下标
		query.setFirstResult((page.getPageIndex()-1)*page.getPageSize());
		List<MarketTranInfoDetail> suList = query.list();
		page.setRows(suList);
    	
    }
	
	/**
	 * 根据卖方展示其下的进货商品 
	 * @param page
	 * @param order
	 * @param goods
	 */
	@SuppressWarnings("unchecked")
	public void listGoodsChild(Page<GoodsEntity> page,OrderCondition order,GoodsEntity goods,String wholesalerId){
		//获取session
 		Session session = sessionFactory.getCurrentSession();
		StringBuffer hql = new StringBuffer();
		hql.append("select goods  from  GoodsEntity  goods where  goods.goodsName in (select goodsName from ApproachEntity where wholesalerId='"+wholesalerId+"') ");
	     
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
	
	
	
	
	@Transactional
    public boolean  addMarketTranInfo(MarketTranInfoDetail marketTranInfoDetail){
		sessionFactory.getCurrentSession().save(marketTranInfoDetail);
    	return true;
    }
	
	
	/**
	 * 添加交易并添加详情
	 * @param base 
	 * @return
	 */
	@Transactional
	public int addTrade(MarketTranInfoBase base){
		sessionFactory.getCurrentSession().save(base);
		return 1;
	}
	
	/**
	 * 修改追溯码
	 * @param market
	 * @return
	 */
	@Transactional
	public int updateTradeDetails(List<MarketTranInfoDetail> market){
		for(MarketTranInfoDetail mi:market){
			sessionFactory.getCurrentSession().update(mi);
		}
		return 1;
	}
	
	/**
	 * 根据备案号查询商户信息是否存在
	 * @param bizNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public Merchant isExitsBiz(String bizNo){
		String hql="select m from Merchant m where m.bizNo=?";
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0,bizNo);
		List<Merchant> merList=query.list();
		Merchant merchant=null;
		if(merList!=null && merList.size()>0){
			merchant=merList.get(0);
		}
		return merchant;
	}

	
	/**
	 * 交易时保存批次码根据卖方备案号和商品名称去交易明细表里查询,如果没有则去进场表里查询
	 * @param goodsCode
	 * @param wholesalerId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String selectBatchIdByGoodsCodeAndSellerId (String goodsCode,String wholesalerId) {
		String hql = "select a from MarketTranInfoDetail a where a.goodsCode=? and a.marketTranInfoBase.retailerId=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, goodsCode);
		query.setParameter(1, wholesalerId);
		List<MarketTranInfoDetail> list = query.list();
		if (list != null && list.size() > 0) {
			return list.get(0).getBatchId();
		}
		return null;
	}
	
	
	/**
	 * 没有的情况下 直接在进场表里查询
	 * @param goodsCode
	 * @param wholesalerId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String selectBatchIdFromApproach (String goodsCode,String wholesalerId) {
		String hql = "select a from ApproachEntity a where a.goodsCode=? and a.wholesalerId=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, goodsCode);
		query.setParameter(1, wholesalerId);
		List<ApproachEntity> list = query.list();
		if (list != null && list.size() > 0) {
			return list.get(0).getBatchId();
		}
		return null;
	}
	
	/////////////////////////////////和小秤相关///////////////////////////////////////////////////
	
	/**
	 * 买卖双方交易时,买方保存交易的进场信息
	 * 查询卖方最近一次进场的信息(产地,供货市场名称)
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public ApproachEntity palceAndSupper (String wholesalerId) {
		String hql = "select a from ApproachEntity a where a.inDate=(select max(inDate) from ApproachEntity where wholesalerId=?) ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, wholesalerId);
		List<ApproachEntity> listApproach = query.list();
		ApproachEntity approach = null;
		if (listApproach != null && listApproach.size() > 0) {
			approach = listApproach.get(0);
		}
		return approach;
	
	}
	
	/**
	 * 根据卖方编码和商品编码查询卖方进货信息(若返回类型为false,则说明卖方没有此进货信息)
	 * @param wholesalerId
	 * @param goodsCode
	 * @return
	 */
	@Transactional
	public boolean iswholesalerApproachInformation(String wholesalerId,String goodsCode){
		String hql="from ApproachEntity a where a.wholesalerId=:wholesalerId and a.goodsCode=:goodsCode";
		Query query =sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter("wholesalerId",wholesalerId);
		query.setParameter("goodsCode",goodsCode);
		@SuppressWarnings("unchecked")
		List<ApproachEntity> apprList=query.list();
		if(apprList!=null && apprList.size()>0){
			return true;
		}
		return false;
	}
	
	
	/**
	 * 判断买方是不是散户,如果是散户则交易完后不进场
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String ifSingleBuy (String buyId) {
		String hql = "select b from Merchant b where b.bizNo = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, buyId);
		List<Merchant> merchantList=query.list();
		Merchant merchant=null;
		if(merchantList!=null && merchantList.size()>0){
			merchant=merchantList.get(0);
		}
		return merchant.getBizType();
	}
	
	
	/**
	 * 将买方交易时的交易信息保存成进场信息
	 * @param approach
	 * @return
	 */
	@Transactional
	public int addApproachIn(final ApproachEntity approach){
		approach.setApproachId(null);
		try {
		Session session = sessionFactory.getCurrentSession();
		session.persist(approach);
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		} 
		return 1;
		
	}   
	
	/**
	 * 判断商户是否绑定小秤,如果绑定则执行写入操作,如果没有绑定,则不写入
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String ifBanding (String bizNo) {
		String hql = "select a from BalanceEntity a where a.bizId = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, bizNo);
		List<BalanceEntity> list = query.list();
		BalanceEntity balance = null;
		if (list != null && list.size() > 0) {
			balance = list.get(0);  
			return balance.getBalanceNo();
		} else {
			return null;
		}
	}
	
	
	/**
	 * 写入操作
	 * @param approach
	 * @param path
	 * @throws IOException
	 */
	@Transactional
	public void writeTrade(ApproachEntity approach,String bizNo,String balanceNo) throws IOException {
		if (balanceNo != null) {
			FileWriter fileWriter = null;
			BufferedWriter bufferWriter = null;
			String fileName = "M_JZ_"+balanceNo+".txt";
			File file = new File(approachInUrl);
			File[] fileList = file.listFiles();
			boolean flag = this.ifHaveThistxt(fileList, fileName);
			if (file.exists()) {
				if (fileList.length > 0 && flag) {
					fileWriter = new FileWriter(new File(approachInUrl+"M_JZ_"+balanceNo+".txt"),true);
					bufferWriter = new BufferedWriter(fileWriter);
					writeApproach(approach,bufferWriter,fileWriter);
				} else {
					file.createNewFile();
					fileWriter = new FileWriter(new File(approachInUrl+"M_JZ_"+balanceNo+".txt"),true);
					bufferWriter = new BufferedWriter(fileWriter);
					//刷出表头信息数据               
					bufferWriter.write("进场时间");
					bufferWriter.write("\t");
					bufferWriter.write("进场批次号/追溯码");
					bufferWriter.write("\t");
					bufferWriter.write("进货重量");
					bufferWriter.write("\t");
					bufferWriter.write("商品编码");
					bufferWriter.write("\t");
					bufferWriter.write("名称");
					bufferWriter.write("\r\n");
					writeApproach(approach,bufferWriter,fileWriter);
				}
			} else {
				file.mkdirs();
				String newFileName = approachInUrl+"M_JZ_"+balanceNo+".txt";
				File newName = new File(newFileName);
				newName.createNewFile();
				fileWriter = new FileWriter(new File(newFileName),true);
				bufferWriter = new BufferedWriter(fileWriter);
				//刷出表头信息数据               
				bufferWriter.write("进场时间");
				bufferWriter.write("\t");
				bufferWriter.write("进场批次号/追溯码");
				bufferWriter.write("\t");
				bufferWriter.write("进货重量");
				bufferWriter.write("\t");
				bufferWriter.write("商品编码");
				bufferWriter.write("\t");
				bufferWriter.write("名称");
				bufferWriter.write("\r\n");
				writeApproach(approach,bufferWriter,fileWriter);
			}
		}
	}				
	
	
	public void writeApproach (ApproachEntity approach,BufferedWriter bufferWriter,FileWriter fileWriter) throws IOException {
		DecimalFormat format = new DecimalFormat("0.00");
		bufferWriter.write(approach.getInDate().toString());
		bufferWriter.write("\t");
		bufferWriter.write(approach.getBatchId());
		bufferWriter.write("\t");
		bufferWriter.write(format.format(approach.getWeight()));
		bufferWriter.write("\t");
		bufferWriter.write(approach.getGoodsCode());
		bufferWriter.write("\t");
		bufferWriter.write(approach.getGoodsName());
		bufferWriter.write("\t");
		bufferWriter.write("\r\n");
		fileWriter.flush();
		bufferWriter.flush();
		fileWriter.close();
		bufferWriter.close();
		
	}
	
	/**
	 * 判断该文件夹下有没有这个文件
	 * @param fileList
	 * @param fileName
	 * @return
	 */
	public boolean ifHaveThistxt (File[] fileList,String fileName) {
		if (fileList != null && fileList.length > 0 ) {
			for (int i = 0;i<fileList.length;i++) {
				if (fileList[i].getName().equals(fileName)) return true;
			}
		}
		return false;
	}		

	
////////////////////////////////////当买卖双方刷卡交易的时候/////////////////////////////////////////////////////////////	
	
	/**
	 * 查询买方账户的当前余额
	 * @param bizNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Double selectCurrentMoney (String bizNo) {
		String hql = "select a from Merchant a  where a.bizNo=?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, bizNo);
		List<Merchant> listMerchant = query.list();
		Merchant merchant = null;
		if (listMerchant != null && listMerchant.size() > 0) {
			merchant = listMerchant.get(0);
			return merchant.getBalance();
		}
		return null;
	} 
	

	/**
	 * 买卖双方交易完后,更新其账户
	 */
	
	public void updateBalance(String afterTrade,String bizNo) {
		String hql = "update Merchant a set a.balance ="+afterTrade+" where a.bizNo="+bizNo+"";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.executeUpdate();
	
	}

	
	//////////////////////////////////结算清单//////////////////////////////////////////////////////////////


	/**
	 * 根据交易凭证号查询,结算清单
	 */
	
	@SuppressWarnings("unchecked")
	public List<MarketTranInfoBase> querySettlementList (String tranId) {
		String hql = "select tradeBase from MarketTranInfoBase tradeBase where tradeBase.tranId=?";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter(0, tranId);
		List <MarketTranInfoBase> list = query.list();
		return list;
	}
	
	
	/**
	 * 根据商品编码查询费用设置表里的定额和比例
	 * 以便求得买卖双方在交易时所扣的钱
	 * 
	 */
	
	@SuppressWarnings("unchecked")
	public List<CostsSet> queryCostsList() {
		String hql = "select cost from CostsSet cost ";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hql);
		List<CostsSet> costList = query.list();
		return costList;
	}
	
	
	/**
	 * 用商品编码查询出费用参数
	 * 
	 * 
	 */
	
	public List<CostsSet> queryByGoodsCode(String goodsCode) {
		List<CostsSet> costsList = queryCostsList();
		List<CostsSet> costsNewList=new ArrayList<CostsSet>();
		if (costsList != null && costsList.size() > 0) {
			for (CostsSet costs:costsList) {
				String goodsCodes = costs.getGoodsCodes();
				if (goodsCodes != null) {
					String [] goods=goodsCodes.split(",");
					for(String good:goods){
						if(good.equals(goodsCode)){
							costsNewList.add(costs);
						}
					}
				}
			}
		}
		return costsNewList;
	}
	
	
	/**
	 * 查出买家费用
	 * @param totalPrice
	 * @param costsList
	 * @param flag flag 
	 * @return
	 */
	
	public double getBuyTransfer (double totalPrice,List<CostsSet> costsList) {
		double totalBuyPrice =0;
		if(costsList!=null && costsList.size()>0){
			for (CostsSet cost:costsList) {
				totalBuyPrice += Double.parseDouble(cost.getBuyerQuota()) + Double.parseDouble(cost.getBuyerRate())*totalPrice;
			}
			
		}
		return totalBuyPrice;
	}
	
	
	/**
	 * 查出卖家费用
	 * @param totalPrice
	 * @param costsList
	 * @return
	 */
	
	public double getSellTransfer (double totalPrice, List<CostsSet> costsList) {
		double totalSellPrice = 0;
		if (costsList != null && costsList.size() > 0) {
			for (CostsSet cost : costsList) {
				totalSellPrice += Double.parseDouble(cost.getSellerQuota()) + Double.parseDouble(cost.getSellerRate())*totalPrice;
			}
		}
		return  totalSellPrice;
	}


	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<安全追溯>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	
	/**
	 * 安全追溯显示：基层根据页面传过来的追溯码
	 * @param traceId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public MarketTranInfoBase selectByTraceId (String traceId) {
		String hql = "select d from MarketTranInfoDetail d where d.traceId =?";
		//String hql = "select d.marketTranInfoBase from MarketTranInfoDetail d where d.traceId=?";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter(0, traceId);
		List<MarketTranInfoDetail> list = query.list();
		if (list != null && list.size() > 0) {
			System.out.println(list.get(0).getMarketTranInfoBase().getRetailerName());
			return list.get(0).getMarketTranInfoBase();
			
		}
		return null;
	}

	
	
	/**
	 * 根据批次号 查询供货商
	 * @param batchId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ApproachEntity selectApproachByBatchId (String batchId) {
		String hql = "select a from ApproachEntity a where a.batchId=?";
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter(0, batchId);
		List<ApproachEntity> list = query.list();
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	
	
	/**
	 * 根据追溯码 查到批次码
	 * @param traceId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String selectbatchIdByTraceId (String traceId) {
		String hql = "select a from MarketTranInfoDetail a where a.traceId=?";
		Session session = sessionFactory.getCurrentSession();
		Query query =session.createQuery(hql);
		query.setParameter(0, traceId);
		List<MarketTranInfoDetail> list = query.list();
		if (list != null && list.size() > 0) {
			return list.get(0).getBatchId();
		
		}
		return null;
	}

	




}
