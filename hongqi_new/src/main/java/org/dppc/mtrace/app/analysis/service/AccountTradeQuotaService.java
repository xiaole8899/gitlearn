package org.dppc.mtrace.app.analysis.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.dppc.mtrace.app.merchant.entity.Merchant;
import org.dppc.mtrace.app.trade.entity.MarketTranInfoBase;
import org.dppc.mtrace.frame.kit.StringKit;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountTradeQuotaService {
	
	@Autowired
	SessionFactory sessionFactory;
	/**
	 *查询支出信息
	 */
	@SuppressWarnings("unchecked")
	public List<MarketTranInfoBase> expenseInfos(Merchant merchant,HttpServletRequest request){
		StringBuilder buid = new StringBuilder();
		
		//获取session
		Session session = sessionFactory.getCurrentSession();
		
		buid = buid.append("select mbase from MarketTranInfoBase mbase where 1=1 ");
		
		if(StringKit.isNotEmpty(merchant.getBizName())){
			buid.append(" and mbase.retailerName = '"+merchant.getBizName()+"' ");
		}
		
		if(StringKit.isNotEmpty(merchant.getBizNo())){
			buid.append(" and mbase.retailerId ='"+merchant.getBizNo()+"' ");
		}
		if(request.getParameter("startDate")!=null && request.getParameter("startDate")!=""){
			buid.append(" and mbase.tranDate >= '"+request.getParameter("startDate")+"' ");
		}
		
		if(request.getParameter("endDate")!=null && request.getParameter("endDate")!=""){
			buid.append(" and mbase.tranDate <= '"+request.getParameter("endDate")+"' ");
		}
		
		Query query = session.createQuery(buid.toString());
		return  query.list();
		
	}
	
	/**
	 * 查询收入信息
	 * @param merchant
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MarketTranInfoBase> incomeInfos(Merchant merchant,HttpServletRequest request){
		StringBuilder buid = new StringBuilder();
		
		//获取session
		Session session = sessionFactory.getCurrentSession();
		
		buid = buid.append("select mbase from MarketTranInfoBase mbase where 1=1 ");
		
		if(StringKit.isNotEmpty(merchant.getBizName())){
			buid.append(" and mbase.wholesalerName = '"+merchant.getBizName()+"' ");
		}
		
		if(StringKit.isNotEmpty(merchant.getBizNo())){
			buid.append(" and mbase.wholesalerId ='"+merchant.getBizNo()+"' ");
		}
		if(request.getParameter("startDate")!=null && request.getParameter("startDate")!=""){
			buid.append(" and mbase.tranDate >= '"+request.getParameter("startDate")+"' ");
		}
		
		if(request.getParameter("endDate")!=null && request.getParameter("endDate")!=""){
			buid.append(" and mbase.tranDate <= '"+request.getParameter("endDate")+"' ");
		}
		
		
		Query query = session.createQuery(buid.toString());
		return  query.list();
		
	}
	
	public List<?> findMarketSell(HttpServletRequest request){
		StringBuilder build = new StringBuilder();
		build.append("SELECT date_format(t.tran_date, '%Y-%m-%d') tranDate,sum(t.total_price) totalPrice ");
		build.append(" from t_trade_hdr t where 1=1 ");
		
		if(request.getParameter("startDate")!=null && request.getParameter("startDate")!=""){
			build.append(" and t.tran_date >= date_format('"+request.getParameter("startDate")+"', '%Y-%m-%d') ");
		}
		
		if(request.getParameter("endDate")!=null && request.getParameter("endDate")!=""){
			build.append(" and t.tran_date <= date_format('"+request.getParameter("endDate")+"', '%Y-%m-%d') ");
		}
		build.append(" GROUP BY date_format(t.tran_date, '%Y-%m-%d') LIMIT 15 ");
		
		SQLQuery sqlQuery=sessionFactory.getCurrentSession().createSQLQuery(build.toString());
		return sqlQuery.list();
		
	}
}
