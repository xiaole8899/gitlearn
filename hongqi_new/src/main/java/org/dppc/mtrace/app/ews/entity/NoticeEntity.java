package org.dppc.mtrace.app.ews.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.dppc.mtrace.frame.base.BaseEntity;

/**
 * 公告实体类
 * @author dx
 *
 */
@Entity
@Table(name="t_notice")
public class NoticeEntity extends BaseEntity{ 
	private static final long serialVersionUID = 1L;

	//主键
	private int noticeId;
	
	//公告内容
	private String content;
	
	//公告人
	private String noticeUser;
	
	//公告时间
	private Date ctime;
	
	//公告是否启用
	private String typeContent;
	
	@Id
	@Column(name="notice_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(int noticeId) {
		this.noticeId = noticeId;
	}

	@Column(name="content",length=500)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name="notice_user",length=20)
	public String getNoticeUser() {
		return noticeUser;
	}

	public void setNoticeUser(String noticeUser) {
		this.noticeUser = noticeUser;
	}


	@Column(name="c_time",length=30)
	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	@Column(name="type_content",length=20)
	public String getTypeContent() {
		return typeContent;
	}

	public void setTypeContent(String typeContent) {
		this.typeContent = typeContent;
	}
	
}
