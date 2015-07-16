package org.dppc.mtrace.app.dict.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.dppc.mtrace.frame.base.BaseEntity;

/**
 * 地区类
 * @author weiyuzhen
 *
 */
@Entity
@Table(name="t_country")
public class CountyEntity extends BaseEntity {
	private static final long serialVersionUID = -4348247962478680510L;
	
	private Integer cId;                   //地区编码
	private String cName;              //地区名称
	private String prentId;             //地区上级式的编码
	
	@Id
	@Column(name="Id",length=11)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getcId() {
		return cId;
	}
	public void setcId(Integer cId) {
		this.cId = cId;
	}
	
	@Column(name="name",length=255)
	public String getcName() {
		return cName;
	}
	public void setcName(String cName) {
		this.cName = cName;
	}
	
	@Column(name="parent_id",length=11)
	public String getPrentId() {
		return prentId;
	}
	public void setPrentId(String prentId) {
		this.prentId = prentId;
	}
	
	
}
