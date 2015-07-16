package org.dppc.mtrace.app.trade.entity;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.dppc.mtrace.frame.base.BaseEntity;
@Entity
@Table(name="t_trade_dtl")
public class MarketTranInfoDetail extends BaseEntity {
	/**
	 * 交易主表 
	 */
	private static final long serialVersionUID = 1L;
	//主键id
	private int dtlId;          
	//进货批次号或交易凭证号
	private String batchId;     
	//商品编码
	private String goodsCode;  
	//商品名称
	private String goodsName;   
	//商品类型
	
	private String goodsType; 
	//重量
	private double weight;    
	//单价
	private double price;      
	//追溯通码
	private String traceId;     
	//凭证类型
	private String voucherType; 
	//头表属性
	private MarketTranInfoBase marketTranInfoBase;
	//模式(0是计重1是计件)
	private String modelType;
	//数量(如果是计重的话数量默认为1，如果是计件的话数量则是电子称上传过来的数量)
	private int count;
	private Double totals; //总价 计算清单使用，不做为数据库中的字段
	//卖家交易费
	private double wholesalerTransfer;
	//买家交易费
	private double retailerTransfer;
	@Transient
	public Double getTotals() {
		return totals;
	}
	public void setTotals(Double totals) {
		this.totals = totals;
	}
	@Column(name="count",length=10)
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Column(name="model_type",length=4)
	public String getModelType() {
		return modelType;
	}
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}
	@Id
	@Column(name="dtl_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getDtlId() {
		return dtlId;
	}
	public void setDtlId(int dtlId) {
		this.dtlId = dtlId;
	}
	@Column(name="batch_id",length=20)
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	@Column(name="goods_code",length=20)
	public String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	@Column(name="goods_name",length=50)
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	@Column(name="goods_type",length=4)
	public String getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	@Column(name="weight")
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	@Column(name="price")
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	@Column(name="trace_id",length=20)
	public String getTraceId() {
		return traceId;
	}
	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}
	@Column(name="voucher_type",length=4)
	public String getVoucherType() {
		return voucherType;
	}
	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}
	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="hdr_id")
	public MarketTranInfoBase getMarketTranInfoBase() {
		return marketTranInfoBase;
	}
	public void setMarketTranInfoBase(MarketTranInfoBase marketTranInfoBase) {
		this.marketTranInfoBase = marketTranInfoBase;
	}
	@Transient
	public double getWholesalerTransfer() {
		return wholesalerTransfer;
	}
	public void setWholesalerTransfer(double wholesalerTransfer) {
		this.wholesalerTransfer = wholesalerTransfer;
	}
	@Transient
	public double getRetailerTransfer() {
		return retailerTransfer;
	}
	public void setRetailerTransfer(double retailerTransfer) {
		this.retailerTransfer = retailerTransfer;
	}
	
}
