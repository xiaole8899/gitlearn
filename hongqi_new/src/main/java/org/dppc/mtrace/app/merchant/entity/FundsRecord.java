package org.dppc.mtrace.app.merchant.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.dppc.mtrace.frame.base.BaseEntity;
/**
 * 用户资金变动记录表
 * @author weiyuzhen
 *
 */
@Entity
@Table(name="t_funds_record")
public class FundsRecord  extends BaseEntity{

	private static final long serialVersionUID = 3186660794381092154L;
	private int id;   //主键
	private String userName; //用户名称
	private String userNo;      //用户编码
	private double amount;  //变动金额
	private Timestamp changeTime;  //变动时间
	private String type; //交易类型 001： 充值 ，002：交易 003：取现（及提款）
	private String tranId; //交易凭证号
	
	private String balance;  //当前余额
	
	
	//商户
	private Merchant merchant;
	
	
	
	@Id
	@Column(name="f_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	@Column(name="user_name",length=50)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name="user_no",length=30)
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	@Column(name="f_amount",length=30)
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	@Column(name="change_time")
	public Timestamp getChangeTime() {
		return changeTime;
	}
	public void setChangeTime(Timestamp changeTime) {
		this.changeTime = changeTime;
	}
	@Column(name="f_type",length=8)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column(name="tran_id",length=20)
	public String getTranId() {
		return tranId;
	}
	public void setTranId(String tranId) {
		this.tranId = tranId;
	}
	
	@Transient
	public Merchant getMerchant() {
		return merchant;
	}
	
	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	@Column(name="balance")
	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}
	
	
}
