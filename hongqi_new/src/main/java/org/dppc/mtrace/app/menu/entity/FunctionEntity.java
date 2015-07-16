package org.dppc.mtrace.app.menu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.dppc.mtrace.frame.annotation.UseIDPatch;
import org.dppc.mtrace.frame.base.BaseEntity;

/**
 * 功能实体类
 * 
 * @author sunlong
 *
 */
@UseIDPatch
@Entity
@Table(name="t_function")
public class FunctionEntity extends BaseEntity {
	
	private static final long serialVersionUID = 1459040229277405941L;
	
	private int functionId;							//功能ID
	
	private String functionName;					//功能名称
	
	private String url;								//url地址
	
	private String description;						//功能类型
	
	private MenuEntity menu;

	@Id
	@Column(name="function_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getFunctionId() {
		return functionId;
	}

	public void setFunctionId(int functionId) {
		this.functionId = functionId;
	}

	@Column(name="function_name", length=40, nullable=false)
	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	@Column(name="url", length=100, nullable=false)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(length=200)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="menu_id")
	public MenuEntity getMenu() {
		return menu;
	}

	public void setMenu(MenuEntity menu) {
		this.menu = menu;
	}
	
}
