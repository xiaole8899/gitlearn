package org.dppc.mtrace.app.trade.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.dppc.mtrace.frame.base.BaseEntity;

/**
 * 费用设置实体类
 * @author hle
 *
 */
@Entity
@Table(name="t_costs_set")
public class CostsSet extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer costsId; //编号
	
	private String costsName; //名称
	
	private String goodsCodes; //商品编码
	
	private String goodsNames;  //商品名称
	
	private String buyerQuota;  //买方定额
	
	private String buyerRate;  //买方比例
	
	private String sellerQuota;   //卖方定额
	
	private String sellerRate;  //卖方比例
	
	private String userId;  //创建人编号
	
	private String usrName;  //创建人名称
	
	private Timestamp createTime; //创建时间
	
	private Timestamp updateTime;  //更新时间
	
	private String status;       //0关闭1启用
	
	

	@Id
	@Column(name="costs_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getCostsId() {
		return costsId;
	}

	public void setCostsId(Integer costsId) {
		this.costsId = costsId;
	}

	@Column(name="costs_name")
	public String getCostsName() {
		return costsName;
	}

	public void setCostsName(String costsName) {
		this.costsName = costsName;
	}

	@Column(name="goods_codes",length=2500)
	public String getGoodsCodes() {
		return goodsCodes;
	}

	public void setGoodsCodes(String goodsCodes) {
		this.goodsCodes = goodsCodes;
	}

	@Column(name="goods_names",length=2500)
	public String getGoodsNames() {
		return goodsNames;
	}

	public void setGoodsNames(String goodsNames) {
		this.goodsNames = goodsNames;
	}

	@Column(name="buyer_quota")
	public String getBuyerQuota() {
		return buyerQuota;
	}

	public void setBuyerQuota(String buyerQuota) {
		this.buyerQuota = buyerQuota;
	}

	@Column(name="buyer_rate")
	public String getBuyerRate() {
		return buyerRate;
	}

	public void setBuyerRate(String buyerRate) {
		this.buyerRate = buyerRate;
	}

	@Column(name="seller_quota")
	public String getSellerQuota() {
		return sellerQuota;
	}

	public void setSellerQuota(String sellerQuota) {
		this.sellerQuota = sellerQuota;
	}

	@Column(name="seller_rate")
	public String getSellerRate() {
		return sellerRate;
	}

	public void setSellerRate(String sellerRate) {
		this.sellerRate = sellerRate;
	}

	
	@Column(name="user_id")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name="user_name")
	public String getUsrName() {
		return usrName;
	}

	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}

	@Column(name="create_time")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name="update_time")
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name="status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	
	
	
	
	
}
