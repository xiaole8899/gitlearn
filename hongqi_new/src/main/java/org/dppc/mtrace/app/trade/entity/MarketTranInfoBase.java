package org.dppc.mtrace.app.trade.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.dppc.mtrace.frame.base.BaseEntity;
@Entity
@Table(name="t_trade_hdr")
public class MarketTranInfoBase extends BaseEntity {
	
	/**
	 * 交易主表 
	 */
	private static final long serialVersionUID = 1L;
	//主键id
	private int hdrId;               
	//上传时间
	private Date uploadTime;    
	//交易日期
	private Date tranDate;      
	//批发商编码
	private String wholesalerId;     
	//批发商名称
	private String wholesalerName;   
	//零售商编码
	private String retailerId;       
	//零售商名称
	private String retailerName;     
	//交易凭证号
	private String tranId;  
	//卖方商户类型
	private String wholesalerBtype;
	//买方经营类型
	private String salerBusinessType;
	//交易类型
	private String  tradeType;
	//交易总额
	private double totalPrice; 
	//订单状态 0是已完成1是未完成2是取消
	private String orderStatus;
	//总重量
	private double totalWeight;
	//卖家明文卡号-银行卡卡号
	private String sellerCardNo;
	//买家明文卡号-银行卡卡号
	private String buyCardNo;
	//买家类型
	private String buyerType;
	//买方交易总手续费(仅供结算清单时使用)
	private double totalBuyTransfer;
	//卖方总手续费
	private double totalSellTransfer;
	//封装细表集合
	private List<MarketTranInfoDetail> marketTranInfoDetailItems;
	
	
	
	
	@Id
	@Column(name="hdr_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getHdrId() {
		return hdrId;
	}
	public void setHdrId(int hdrId) {
		this.hdrId = hdrId;
	}
	@Column(name="trade_type")
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	@Column(name="upload_time")
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	@Column(name="tran_date")
	public Date getTranDate() {
		return tranDate;
	}
	public void setTranDate(Date tranDate) {
		this.tranDate = tranDate;
	}
	@Column(name="wholesaler_id",length=20)
	public String getWholesalerId() {
		return wholesalerId;
	}
	public void setWholesalerId(String wholesalerId) {
		this.wholesalerId = wholesalerId;
	}
	@Column(name="wholesaler_name",length=50)
	public String getWholesalerName() {
		return wholesalerName;
	}
	public void setWholesalerName(String wholesalerName) {
		this.wholesalerName = wholesalerName;
	}
	@Column(name="retailer_id",length=20)
	public String getRetailerId() {
		return retailerId;
	}
	public void setRetailerId(String retailerId) {
		this.retailerId = retailerId;
	}
	
	@Column(name="retailer_name",length=50)
	public String getRetailerName() {
		return retailerName;
	}
	public void setRetailerName(String retailerName) {
		this.retailerName = retailerName;
	}
	@Column(name="tran_id",length=20)
	public String getTranId() {
		return tranId;
	}
	
	public void setTranId(String tranId) {
		this.tranId = tranId;
	}
	@OneToMany(targetEntity=MarketTranInfoDetail.class,mappedBy="marketTranInfoBase",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	public List<MarketTranInfoDetail> getMarketTranInfoDetailItems() {
		return marketTranInfoDetailItems;
	}
	public void setMarketTranInfoDetailItems(
			List<MarketTranInfoDetail> marketTranInfoDetailItems) {
		this.marketTranInfoDetailItems = marketTranInfoDetailItems;
	}
	@Column(name="wholesaler_btype")
	public String getWholesalerBtype() {
		return wholesalerBtype;
	}
	public void setWholesalerBtype(String wholesalerBtype) {
		this.wholesalerBtype = wholesalerBtype;
	}
	
	@Column(name="saler_business_type")
	public String getSalerBusinessType() {
		return salerBusinessType;
	}
	
	public void setSalerBusinessType(String salerBusinessType) {
		this.salerBusinessType = salerBusinessType;
	}
	@Column(name="total_weight")
	public double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}
	@Column(name="seller_cardNo")
	public String getSellerCardNo() {
		return sellerCardNo;
	}

	public void setSellerCardNo(String sellerCardNo) {
		this.sellerCardNo = sellerCardNo;
	}
	@Column(name="buy_cardNo")
	public String getBuyCardNo() {
		return buyCardNo;
	}

	public void setBuyCardNo(String buyCardNo) {
		this.buyCardNo = buyCardNo;
	}

	@Column(name="total_price")
	public double getTotalPrice() {
		return totalPrice;
	}
	
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	@Column(name="order_status")
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	@Column(name="buyer_type",length=2)
	public String getBuyerType() {
		return buyerType;
	}
	public void setBuyerType(String buyerType) {
		this.buyerType = buyerType;
	}
	@Transient
	public double getTotalBuyTransfer() {
		return totalBuyTransfer;
	}
	public void setTotalBuyTransfer(double totalBuyTransfer) {
		this.totalBuyTransfer = totalBuyTransfer;
	}
	@Transient
	public double getTotalSellTransfer() {
		return totalSellTransfer;
	}
	public void setTotalSellTransfer(double totalSellTransfer) {
		this.totalSellTransfer = totalSellTransfer;
	}
	
	

	
	
	
}
