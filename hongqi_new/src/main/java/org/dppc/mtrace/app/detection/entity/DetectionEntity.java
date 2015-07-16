package org.dppc.mtrace.app.detection.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.dppc.mtrace.frame.base.BaseEntity;


/**
 * 检测信息实体类
 * 
 * @author sunlong
 *
 */
@Entity
@Table(name="t_market_detection_info")
public class DetectionEntity extends BaseEntity {
	private static final long serialVersionUID = 1459040229277405941L;

	private int mdId;				//	检测ID						
	
	private String batchType;		//  批次号类型	
	
	private String batchId;			//  批次号
	
	private String wholesalerId;	//  批发商编号
	
	private String wholesalerName;	//  批发商名称
	
	private String goodsCode;		//  商品编码
	
	private String goodsName;		//  商品名称
	
	private String sampleId;		//  样品编号
	
	private String surveyor;		//  检测员
	
	private Date detectionDate;		//  检测日期
	
	private String detectionResult;	//  检测结果
	
	private String resultExplain;   //  检测结果说明
	
	private Date uploadTime;		//  信息更新时间

	@Id
	@Column(name="md_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getMdId() {
		return mdId;
	}

	@Column(name="batch_type", length=4, nullable=false)
	public String getBatchType() {
		return batchType;
	}

	@Column(name="batch_id", length=20, nullable=false)
	public String getBatchId() {
		return batchId;
	}

	@Column(name="wholesaler_id", length=20, nullable=false)
	public String getWholesalerId() {
		return wholesalerId;
	}

	@Column(name="wholesaler_name", length=50, nullable=false)
	public String getWholesalerName() {
		return wholesalerName;
	}

	@Column(name="goods_code", length=20, nullable=false)
	public String getGoodsCode() {
		return goodsCode;
	}

	@Column(name="goods_name", length=50, nullable=false)
	public String getGoodsName() {
		return goodsName;
	}

	@Column(name="sample_id", length=20, nullable=false)
	public String getSampleId() {
		return sampleId;
	}

	@Column(name="surveyor", length=50, nullable=false)
	public String getSurveyor() {
		return surveyor;
	}

	@Column(name="detection_date")
	public Date getDetectionDate() {
		return detectionDate;
	}

	@Column(name="detection_result", length=20, nullable=false)
	public String getDetectionResult() {
		return detectionResult;
	}

	@Column(name="result_explain", length=100)
	public String getResultExplain() {
		return resultExplain;
	}

	@Column(name="upload_time")
	public Date getUploadTime() {
		return uploadTime;
	}

	public void setMdId(int mdId) {
		this.mdId = mdId;
	}

	public void setBatchType(String batchType) {
		this.batchType = batchType;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public void setWholesalerId(String wholesalerId) {
		this.wholesalerId = wholesalerId;
	}

	public void setWholesalerName(String wholesalerName) {
		this.wholesalerName = wholesalerName;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public void setSampleId(String sampleId) {
		this.sampleId = sampleId;
	}

	public void setSurveyor(String surveyor) {
		this.surveyor = surveyor;
	}

	public void setDetectionDate(Date detectionDate) {
		this.detectionDate = detectionDate;
	}

	public void setDetectionResult(String detectionResult) {
		this.detectionResult = detectionResult;
	}

	public void setResultExplain(String resultExplain) {
		this.resultExplain = resultExplain;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

}
