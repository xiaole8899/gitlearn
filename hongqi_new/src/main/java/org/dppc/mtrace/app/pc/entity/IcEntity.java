package org.dppc.mtrace.app.pc.entity;

import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.dppc.mtrace.frame.base.BaseEntity;

/**
 * IC卡实体类
 * @author dx
 *
 */
@Entity
@Table(name="t_ic")
public class IcEntity extends BaseEntity{ 
	private static final long serialVersionUID = 1L;

	//主键
	private int icId = 0;
	
	//IC卡读写器名称
	private String icName;
	
	//时间
	private Date inDate;

	@Id
	@Column(name="ic_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getIcId() {
		return icId;
	}

	public void setIcId(int icId) {
		this.icId = icId;
	}
	
	@Column(name="ic_name",length=30)
	public String getIcName() {
		return icName;
	}

	public void setIcName(String icName) {
		this.icName = icName;
	}

	@Column(name="in_date",length=30)
	public Date getInDate() {
		return inDate;
	}

	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}     
	
}
