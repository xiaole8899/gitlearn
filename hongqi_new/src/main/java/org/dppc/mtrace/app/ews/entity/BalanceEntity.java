package org.dppc.mtrace.app.ews.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.dppc.mtrace.frame.base.BaseEntity;

/**
 * 小秤实体类
 * @author weiyuzhen
 *
 */
@Entity
@Table(name="t_balance")
public class BalanceEntity extends BaseEntity{

	private static final long serialVersionUID = -357609999691001896L;
	private int baId;  //主键
	private String balanceNo;  //小秤的编号
	private String bizId;      //商户编号
	private String bizName;    //商户名称
	private String businessType; //商户类型
	private String boothNo;    //摊位号
	private Timestamp boundTime; //绑定时间
	
	@Id
	@Column(name="ba_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getBaId() {
		return baId;
	}
	public void setBaId(int baId) {
		this.baId = baId;
	}
	@Column(name="balance_no",length=50)
	public String getBalanceNo() {
		return balanceNo;
	}
	public void setBalanceNo(String balanceNo) {
		this.balanceNo = balanceNo;
	}
	@Column(name="biz_id")
	public String getBizId() {
		return bizId;
	}
	public void setBizId(String bizId) {
		this.bizId = bizId;
	}
	@Column(name="biz_name",length=50)
	public String getBizName() {
		return bizName;
	}
	public void setBizName(String bizName) {
		this.bizName = bizName;
	}
	@Column(name="booth_no")
	public String getBoothNo() {
		return boothNo;
	}
	public void setBoothNo(String boothNo) {
		this.boothNo = boothNo;
	}
	@Column(name="bound_time")
	public Timestamp getBoundTime() {
		return boundTime;
	}
	public void setBoundTime(Timestamp boundTime) {
		this.boundTime = boundTime;
	}
	@Column(name="business_type")
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	
	
}
