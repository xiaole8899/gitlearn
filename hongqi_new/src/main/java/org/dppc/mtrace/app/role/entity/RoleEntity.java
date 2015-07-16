package org.dppc.mtrace.app.role.entity;

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

import org.dppc.mtrace.app.menu.entity.MenuEntity;
import org.dppc.mtrace.frame.annotation.UseIDPatch;
import org.dppc.mtrace.frame.base.BaseEntity;


/**
 * 角色实体类
 * 
 * @author maomh
 *
 */
@UseIDPatch
@Entity
@Table(name="t_role")
public class RoleEntity extends BaseEntity {
	private static final long serialVersionUID = 1459040229277405941L;

	private int roleId;						//角色ID
	
	private String roleName;				//角色名称
	
	private String descriptions;			//角色描述
	
	//private Set<UserEntity> users;			//关联用户
	
	private Set<MenuEntity> menu;			//关联菜单
	
	@Id
	@Column(name="role_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getRoleId() {
		return roleId;
	}

	@Column(name="role_name", length=40, nullable=false)
	public String getRoleName() {
		return roleName;
	}

	@Column(length=200)
	public String getDescriptions() {
		return descriptions;
	}

	
	
	@ManyToMany(targetEntity=MenuEntity.class,fetch=FetchType.LAZY)
	@JoinTable(
			name="t_role_menu", 
			joinColumns=@JoinColumn(name="role_id"), 
			inverseJoinColumns=@JoinColumn(name="menu_id")
		)
	public Set<MenuEntity> getMenu() {
		return menu;
	}

	public void setMenu(Set<MenuEntity> menu) {
		this.menu = menu;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}


}
