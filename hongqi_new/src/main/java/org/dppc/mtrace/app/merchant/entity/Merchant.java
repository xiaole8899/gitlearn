package org.dppc.mtrace.app.merchant.entity;


import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.dppc.mtrace.frame.base.BaseEntity;

/**
 * 商户信息实体类
 * @author hle
 *
 */
@Entity
@Table(name="t_biz_info")
public class Merchant extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//商户号
	private int bizId;
	
	//商户名称
	private String bizName;
	
	//经营者编码
	private String bizNo;
	
	//备案时间
	private Timestamp recordDate;
	
	//身份证号
	private String identityCard;
	
	//工商注册登记证
	private String regId;
	
	//经营者性质
	private String property;
	
	//经营类型
	private String businessType;
	
	//法人代表
	private String legalRepresent;
	
	//手机号
	private String tel;
	
	//更新时间
	private Timestamp updateTime;
	
	//上传时间
	private Timestamp uploadTime;
	
	//商户摊位号
	private String boothNo;
	
	//卡
	private Set<MCard> cards;
	
	//交易密码
	private String tradePwd;
	
	//商户类型
	private String bizType;
	
	//字号名称/企业名称
	private String comName;
	
	//用户余额 
	// 2015-7-7  添加一个默认值，否则给新用户开卡然后读卡的时候该值为空也没有做判断会抛异常
	//          所以干脆加了个默认值
	private Double balance =0.0;
	
	
	
	@Id
	@Column(name="biz_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getBizId() {
		return bizId;
	}

	public void setBizId(int bizId) {
		this.bizId = bizId;
	}

	@Column(name="biz_name",length=50,nullable=false)
	public String getBizName() {
		return bizName;
	}

	

	public void setBizName(String bizName) {
		this.bizName = bizName;
	}

	@Column(name="biz_no",length=20)
	public String getBizNo() {
		return bizNo;
	}

	public void setBizNo(String bizNo) {
		this.bizNo = bizNo;
	}

	@Column(name="record_date")
	public Timestamp getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Timestamp recordDate) {
		this.recordDate = recordDate;
	}

	@Column(name="identity_card",length=20)
	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	@Column(name="reg_id",length=50)
	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	@Column(name="property",length=50)
	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	@Column(name="business_type",length=10)
	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	@Column(name="tel",length=20)
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name="update_time")
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name="upload_time")
	public Timestamp getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Timestamp uploadTime) {
		this.uploadTime = uploadTime;
	}

	@Column(name="legal_represent",length=50)
	public String getLegalRepresent() {
		return legalRepresent;
	}

	public void setLegalRepresent(String legalRepresent) {
		this.legalRepresent = legalRepresent;
	}

	@Column(name="booth_no")
	public String getBoothNo() {
		return boothNo;
	}

	public void setBoothNo(String boothNo) {
		this.boothNo = boothNo;
	}

	@OneToMany(targetEntity=MCard.class,mappedBy="merchant",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	public Set<MCard> getCards() {
		return cards;
	}

	public void setCards(Set<MCard> cards) {
		this.cards = cards;
	}

	@Column(name="trade_pwd",length=50)
	public String getTradePwd() {
		return tradePwd;
	}

	public void setTradePwd(String tradePwd) {
		this.tradePwd = tradePwd;
	}

	@Column(name="biz_type")
	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	@Column(name="com_name")
	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	@Column(name="biz_balance")
	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}
}
