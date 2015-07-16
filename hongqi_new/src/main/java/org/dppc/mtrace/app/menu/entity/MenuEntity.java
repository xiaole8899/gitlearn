package org.dppc.mtrace.app.menu.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.dppc.mtrace.app.role.entity.RoleEntity;
import org.dppc.mtrace.frame.annotation.UseIDPatch;
import org.dppc.mtrace.frame.base.BaseEntity;

/**
 * 菜单实体类
 * 
 * @author sunlong
 *
 */
@UseIDPatch
@Entity
@Table(name="t_menu")
public class MenuEntity extends BaseEntity implements Comparable<MenuEntity>{
	
	private static final long serialVersionUID = 1459040229277405941L;
	
	private int menuId;						//菜单ID
	
	private String menuName;				//菜单名称
	
	private String url;						//url
	
	private String description;				//描述
	
	private int sort;						//排序
	
	private int parentId;					//父ID
	
	private String rel;						//rel
	
	private Set<RoleEntity> role =new HashSet<RoleEntity>();			//关联角色

	private Set<FunctionEntity> function =new HashSet<FunctionEntity>(); 	//关联功能
	
	private List<MenuEntity> ChildList = new ArrayList<MenuEntity>();		//子集菜单

	@Id
	@Column(name="menu_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	@Column(name="menu_name", length=40, nullable=false)
	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	@Column(name="url", length=50, nullable=false)
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

	@Column(name="sort",length=20,nullable=false)
	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	@Column(name="parent_id", length=20, nullable=false)
	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	@Column(length=40)
	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	@ManyToMany(targetEntity=RoleEntity.class,mappedBy="menu",fetch=FetchType.LAZY)
	public Set<RoleEntity> getRole() {
		return role;
	}

	public void setRole(Set<RoleEntity> role) {
		this.role = role;
	}

	@OneToMany(cascade=CascadeType.ALL,mappedBy="menu",targetEntity=FunctionEntity.class,fetch=FetchType.LAZY)
	public Set<FunctionEntity> getFunction() {
		return function;
	}

	public void setFunction(Set<FunctionEntity> function) {
		this.function = function;
	}

	@Transient
	public List<MenuEntity> getChildList() {
		return ChildList;
	}

	public void setChildList(List<MenuEntity> childList) {
		ChildList = childList;
	}

	@Override
	public int compareTo(MenuEntity o) {
		// TODO Auto-generated method stub
		return o.getSort();
	}
	
}
