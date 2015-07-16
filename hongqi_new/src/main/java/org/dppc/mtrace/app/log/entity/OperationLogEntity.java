package org.dppc.mtrace.app.log.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.dppc.mtrace.frame.base.BaseEntity;

@Entity
@Table(name="t_operation_log")
/**
 * 操作日志实体类
 * @author hle
 *
 */
public class OperationLogEntity  extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7049051014443541422L;

	//日志编号
	private int optId;
	
	//操作人
	private int optUserId;
	
	//操作人名称
	private String optUserName;
	
	//操作人IP
	private String optIp;
	
	//操作时间
	private Timestamp optTime;
	
	//日志状态
	private String optStatus;
	
	//操作路径
	private String optUrl;
	
	//操作
	private String optActionName;
	
	//操作结果
	private String optResult;
	
	//备注
	private String optComment;
	
	//操作人真实姓名
	private String optRealName;
	
	//操作开始日期
	private String startTime;
	
	//操作结束日期
	private String endTime;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="opt_id")
	public int getOptId() {
		return optId;
	}

	public void setOptId(int optId) {
		this.optId = optId;
	}

	@Column(name="opt_user_id")
	public int getOptUserId() {
		return optUserId;
	}

	public void setOptUserId(int optUserId) {
		this.optUserId = optUserId;
	}

	@Column(name="opt_user_name",length=50)
	public String getOptUserName() {
		return optUserName;
	}

	public void setOptUserName(String optUserName) {
		this.optUserName = optUserName;
	}

	@Column(name="opt_ip",length=50)
	public String getOptIp() {
		return optIp;
	}

	public void setOptIp(String optIp) {
		this.optIp = optIp;
	}

	@Column(name="opt_time")
	public Timestamp getOptTime() {
		return optTime;
	}

	public void setOptTime(Timestamp optTime) {
		this.optTime = optTime;
	}

	@Column(name="opt_status",length=2)
	public String getOptStatus() {
		return optStatus;
	}

	public void setOptStatus(String optStatus) {
		this.optStatus = optStatus;
	}

	@Column(name="opt_url",length=200)
	public String getOptUrl() {
		return optUrl;
	}

	public void setOptUrl(String optUrl) {
		this.optUrl = optUrl;
	}

	@Column(name="opt_action_name",length=200)
	public String getOptActionName() {
		return optActionName;
	}

	public void setOptActionName(String optActionName) {
		this.optActionName = optActionName;
	}

	@Column(name="opt_result",length=100)
	public String getOptResult() {
		return optResult;
	}

	public void setOptResult(String optResult) {
		this.optResult = optResult;
	}

	@Column(name="opt_comment",length=200)
	public String getOptComment() {
		return optComment;
	}

	public void setOptComment(String optComment) {
		this.optComment = optComment;
	}

	@Column(name="opt_real_name",length=50)
	public String getOptRealName() {
		return optRealName;
	}

	public void setOptRealName(String optRealName) {
		this.optRealName = optRealName;
	}

	@Transient
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	@Transient
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
