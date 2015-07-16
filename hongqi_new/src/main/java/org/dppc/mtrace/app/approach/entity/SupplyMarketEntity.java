package org.dppc.mtrace.app.approach.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.dppc.mtrace.frame.annotation.UseIDPatch;
import org.dppc.mtrace.frame.base.BaseEntity;

/**
 * 供货市场实体类
 * @author weiyuzhen
 *@Time 2015.03.02
 */
@UseIDPatch
@Entity
@Table(name="t_node_info")
public class SupplyMarketEntity extends BaseEntity {

	private static final long serialVersionUID = -2255180582664230297L;

	private Integer suId;                          //主键
	private String no;                          // 编号
	private String name;                    // 名称
	private String regId;                    //工商注册登记号
	private String sNodeType;                   // 供货市场的类型：0001屠宰企业    0002批发  003菜市场     0004超市  0005团体  0006其他
	private String areaNo;                 // 供货商所在的城市编码
	private String areaName;            //供货市场所在地的城市名称
	private Date recordDate;         //备案日期
	private String legalRepresent;  //法人代表
	private String addr;              //供货商所在的详细地址
    private String tel;                //电话号码
	private Date updateTime;          //最后更新时间
	private String isModified; 		// 是否修改   
	// 0-未修改，不需要采集  1-已修改，需要上传到城市采集  2-未进行备案
	
	//商户集合
	//private Set<Merchant> merchants;

	@Id
	@Column(name="su_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getSuId() {
		return suId;
	}

	public void setSuId(Integer suId) {
		this.suId = suId;
	}

	@Column(name="update_time")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name="comp_no",length=20)
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	@Column(name="comp_name",length=60)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="node_type" ,length=4)
	public String getsNodeType() {
		return sNodeType;
	}

	public void setsNodeType(String sNodeType) {
		this.sNodeType = sNodeType;
	}

	@Column(name="area_code" ,length=6)
	public String getAreaNo() {
		return areaNo;
	}

	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}

	@Column(name="area_name")
	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@Column(name="record_date",length=50)
	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	@Column(name="legal_represent",length=20)
	public String getLegalRepresent() {
		return legalRepresent;
	}

	public void setLegalRepresent(String legalRepresent) {
		this.legalRepresent = legalRepresent;
	}

	@Column(name="addr",length=200)
	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@Column(name="tel", length=20)
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name="reg_id",length=50)
	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	/*@ManyToMany(targetEntity=Merchant.class,mappedBy="suppliers",fetch=FetchType.LAZY)
	public Set<Merchant> getMerchants() {
		return merchants;
	}

	public void setMerchants(Set<Merchant> merchants) {
		this.merchants = merchants;
	}*/
	
	@Column(name="is_modified", length=2)
	public String getIsModified() {
		return isModified;
	}

	public void setIsModified(String isModified) {
		this.isModified = isModified;
	}
	
}
