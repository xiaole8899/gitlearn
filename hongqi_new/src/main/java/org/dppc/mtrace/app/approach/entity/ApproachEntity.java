package org.dppc.mtrace.app.approach.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.dppc.mtrace.frame.base.BaseEntity;

@Entity
@Table(name="t_approach")
public class ApproachEntity extends BaseEntity{
	private static final long serialVersionUID = 2320152763885141822L;
	private Integer approachId;		//主键
	private Timestamp inDate;		//入场时间
	private String wholesalerId;		//商户编码即 商户表里面的bizNo
	private String wholesalerName;			//商户名称 商户表里的bizName
	private String bizType;		//商户类型
	private String batchId;		//进货批次号,是多中类型号的，根据凭证类型
	private String voucherType;		//凭证类型
	private Timestamp uploadTime;		//上传时间
	private String transporterId;		//运输车牌号
	private String goodsCode;		//商品编码
	private String goodsName;		//商品名称
	private double weight;		//商品重量
	private double price;		//商品价格
	private int goodsType;		//类型 1 蔬菜 0 肉
	//private String animalProId;		//动物产品检疫合格证号   //干掉
//	private String inspectMeatId;		//肉品品质检验合格证号  //干掉
//	private String provId;		//产地证明号
//	private String quarantineVegId;		//监测合格证号   //干掉
	private String areaOriginId;		//产地编码
	private String areaOriginName;		//产地名称
	private String baseName;		//生产基地 蔬菜种植户
	private String wsSupplierId;		//供货屠宰厂或批发市场编码
	private String wsSupplierName;		//供货屠宰厂或批发市场名称
	
	private String approachState;  		//是猪肉还是蔬菜的页面 不在数据表映射
	
	private Timestamp updateDate;     //修改时间
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getApproachId() {
		return approachId;
	}
	public void setApproachId(Integer approachId) {
		this.approachId = approachId;
	}
	
	@Column(name="in_date")
	public Timestamp getInDate() {
		return inDate;
	}
	public void setInDate(Timestamp inDate) {
		this.inDate = inDate;
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
	@Column(name="biz_type")
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	@Column(name="batch_id",length=20)
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	
	@Column(name="voucher_type",length=4)
	public String getVoucherType() {
		return voucherType;
	}
	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}
	
	@Column(name="upload_time")
	public Timestamp getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Timestamp uploadTime) {
		this.uploadTime = uploadTime;
	}
	@Column(name="tansporter_id")
	public String getTransporterId() {
		return transporterId;
	}
	public void setTransporterId(String transporterId) {
		this.transporterId = transporterId;
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
	@Column(name="weight",length=15)
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	@Column(name="price",length=15)
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	@Column(name="goods_type",length=4)
	public int getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(int goodsType) {
		this.goodsType = goodsType;
	}
	
	@Column(name="area_origin_id",length=20)
	public String getAreaOriginId() {
		return areaOriginId;
	}
	public void setAreaOriginId(String areaOriginId) {
		this.areaOriginId = areaOriginId;
	}
	@Column(name="area_origin_name",length=50)
	public String getAreaOriginName() {
		return areaOriginName;
	}
	public void setAreaOriginName(String areaOriginName) {
		this.areaOriginName = areaOriginName;
	}
	@Column(name="base_name",length=200)
	public String getBaseName() {
		return baseName;
	}
	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}
	
	@Column(name="ws_supplier_id",length=20)
	public String getWsSupplierId() {
		return wsSupplierId;
	}
	public void setWsSupplierId(String wsSupplierId) {
		this.wsSupplierId = wsSupplierId;
	}
	
	@Column(name="update_date",length=20)
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	@Column(name="ws_supplier_name",length=50)
	public String getWsSupplierName() {
		return wsSupplierName;
	}
	public void setWsSupplierName(String wsSupplierName) {
		this.wsSupplierName = wsSupplierName;
	}
	@Transient
	public String getApproachState() {
		return approachState;
	}
	public void setApproachState(String approachState) {
		this.approachState = approachState;
	}
	
	
}
