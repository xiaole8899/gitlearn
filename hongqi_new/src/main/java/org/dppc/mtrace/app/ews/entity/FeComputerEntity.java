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

@Entity
@Table(name="t_fec")
public class FeComputerEntity extends BaseEntity{

	/**
	 * 前置机实体类
	 */
	private static final long serialVersionUID = 1L;

	//主键
	private int fecId = 0;
	
	//前置机ip地址
	private String fecIp;
	
	//端口号
	private String port;
	
	//绑定时间
	private Date bindTime; 
	
	//关联电子秤
	private Set<EwsEntity> ews;

	@Id
	@Column(name="fec_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getFecId() {
		return fecId;
	}

	public void setFecId(int fecId) {
		this.fecId = fecId;
	}

	@Column(name="fec_ip",length=50)
	public String getFecIp() {
		return fecIp;
	}

	public void setFecIp(String fecIp) {
		this.fecIp = fecIp;
	}

	@Column(name="port",length=50)
	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@ManyToMany(targetEntity=EwsEntity.class,fetch=FetchType.LAZY)
	@JoinTable(
			name="t_ews_fec", 
			joinColumns=@JoinColumn(name="fec_id"), 
			inverseJoinColumns=@JoinColumn(name="ews_id")
	)
	public Set<EwsEntity> getEws() {
		return ews;
	}

	public void setEws(Set<EwsEntity> ews) {
		this.ews = ews;
	}

	@Column(name="bind_time",length=30)
	public Date getBindTime() {
		return bindTime;
	}

	public void setBindTime(Date bindTime) {
		this.bindTime = bindTime;
	}
	
	
}
