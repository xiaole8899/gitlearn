package org.dppc.mtrace.app.merchant.entity;


import java.sql.Timestamp;

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

import org.dppc.mtrace.frame.base.BaseEntity;

@Entity
@Table(name="t_card_hdr")
public class MCard extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//主键
	private int id;
	
	//卡号
	private String cardNo;
	
	//卡的类型
	private String cardType;
	
	//卡的性质
	private String cardKind;
	
	//排序
	private int sortOrder;
	
	//卡的状态
	private String cardStatus;
	
	//商户
	private Merchant merchant;
	
	//持卡人
	private String cardHolder;
	
	//持卡人证件
	private String cardHolderIdentity;
	
	//开卡时间
	private Timestamp openCardTime;
	
	//持卡人电话
	private String holderTel;

	@Id
	@Column(name="c_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="biz_id")
	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	@Column(name="card_no",length=30)
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	@Column(name="card_type")
	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	@Column(name="card_kind")
	public String getCardKind() {
		return cardKind;
	}

	public void setCardKind(String cardKind) {
		this.cardKind = cardKind;
	}

	@Column(name="sort_order")
	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Column(name="card_status",length=2,nullable=false)
	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}

	@Column(name="card_holder",length=20)
	public String getCardHolder() {
		return cardHolder;
	}

	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}

	@Column(name="card_holder_identity",length=20)
	public String getCardHolderIdentity() {
		return cardHolderIdentity;
	}

	public void setCardHolderIdentity(String cardHolderIdentity) {
		this.cardHolderIdentity = cardHolderIdentity;
	}

	@Column(name="open_card_time")
	public Timestamp getOpenCardTime() {
		return openCardTime;
	}

	public void setOpenCardTime(Timestamp openCardTime) {
		this.openCardTime = openCardTime;
	}

	@Column(name="holder_tel",length=20)
	public String getHolderTel() {
		return holderTel;
	}

	public void setHolderTel(String holderTel) {
		this.holderTel = holderTel;
	}
	
	
}
