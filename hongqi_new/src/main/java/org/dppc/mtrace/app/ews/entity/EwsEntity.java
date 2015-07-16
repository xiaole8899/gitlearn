package org.dppc.mtrace.app.ews.entity;

import java.util.Date;
import java.util.Set;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.dppc.mtrace.frame.base.BaseEntity;

/**
 * 电子称实体类
 * @author dx
 *
 */
@Entity
@Table(name="t_ews")
public class EwsEntity extends BaseEntity{ 
	private static final long serialVersionUID = 1L;

	//主键
	private int ewsId = 0;
	
	//SN号
	private String ewsSn;
	
	//电子称mac地址  
	private String ewsMac;
	
	//电子称ip地址
	private String ewsIp;
	
	//商户编号
	private String salerNumber;
	
	//商户名称
	private String salerName;
	
	//绑定时间
	private Date bindTime;     
	
	//关联前置机
	private Set<FeComputerEntity> fes;			
	
	@Id
	@Column(name="ews_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getEwsId() {
		return ewsId;
	}

	public void setEwsId(int ewsId) {
		this.ewsId = ewsId;
	}

	@Column(name="ews_sn",length=50)
	public String getEwsSn() {
		return ewsSn;
	}

	public void setEwsSn(String ewsSn) {
		this.ewsSn = ewsSn;
	}

	@Column(name="ews_mac",length=30)
	public String getEwsMac() {
		return ewsMac;
	}

	public void setEwsMac(String ewsMac) {
		this.ewsMac = ewsMac;
	}

	@Column(name="ews_ip",length=30)
	public String getEwsIp() {
		return ewsIp;
	}

	public void setEwsIp(String ewsIp) {
		this.ewsIp = ewsIp;
	}

	@Column(name="saler_number",length=50)
	public String getSalerNumber() {
		return salerNumber;
	}

	public void setSalerNumber(String salerNumber) {
		this.salerNumber = salerNumber;
	}

	@Column(name="saler_name",length=30)
	public String getSalerName() {
		return salerName;
	}

	public void setSalerName(String salerName) {
		this.salerName = salerName;
	}

	@Column(name="bind_time",length=30)
	public Date getBindTime() {
		return bindTime;
	}

	public void setBindTime(Date bindTime) {
		this.bindTime = bindTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@ManyToMany(targetEntity=FeComputerEntity.class,fetch=FetchType.LAZY)
	@JoinTable(
			name="t_ews_fec", 
			joinColumns=@JoinColumn(name="ews_id"), 
			inverseJoinColumns=@JoinColumn(name="fec_id")
	)
	public Set<FeComputerEntity> getFes() {
		return fes;
	}

	public void setFes(Set<FeComputerEntity> fes) {
		this.fes = fes;
	}
	

}
