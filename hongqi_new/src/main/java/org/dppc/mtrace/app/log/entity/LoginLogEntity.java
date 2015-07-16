package org.dppc.mtrace.app.log.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.dppc.mtrace.frame.base.BaseEntity;


/**
 * 登陆日志实体类
 * 
 * @author sunlong
 *
 */
@Entity
@Table(name = "t_login_log")
public class LoginLogEntity extends BaseEntity{
	
	private static final long serialVersionUID = -2391420249835405763L;
	
	private int logId;					// ID
	
	private int userId;					// 登陆id
	
	private String userName;			// 用户名
	
	private String realName;			// 真实姓名
	
	private String descriptions;		// 日志描述
	
	private String ipAddress;			// Ip地址
	
	private Date loginTime;				// 登陆时间
	
	private Date logoutTime;			// 登出时间
	
	private String sessionId;			// sessionId

	@Id
	@Column(name="log_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getLogId() {
		return logId;
	}

	@Column(name="user_id",length=40)
	public int getUserId() {
		return userId;
	}
	
	@Column(name="username",length=20)
	public String getUserName() {
		return userName;
	}

	@Column(name="real_name",length=20)
	public String getRealName() {
		return realName;
	}

	@Column(name="descriptions",length=40)
	public String getDescriptions() {
		return descriptions;
	}

	@Column(name="ipAddress",length=40)
	public String getIpAddress() {
		return ipAddress;
	}

	@Column(name="login_time",length=20)
	public Date getLoginTime() {
		return loginTime;
	}
	
	@Column(name="logout_time",length=20)
	public Date getLogoutTime() {
		return logoutTime;
	}

	@Column(name="session_id",length=40)
	public String getSessionId() {
		return sessionId;
	}
	
	public void setLogId(int logId) {
		this.logId = logId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}
