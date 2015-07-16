package org.dppc.mtrace.app.user.entity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
import javax.persistence.Transient;

import org.dppc.mtrace.app.menu.entity.FunctionEntity;
import org.dppc.mtrace.app.menu.entity.MenuEntity;
import org.dppc.mtrace.app.role.entity.RoleEntity;
import org.dppc.mtrace.frame.annotation.UseIDPatch;
import org.dppc.mtrace.frame.base.BaseEntity;


/**
 * 系统用户实体Bean
 * 
 * @author maomh
 *
 */
@UseIDPatch
@Entity
@Table(name = "t_user")
public class UserEntity extends BaseEntity{
	private static final long serialVersionUID = -2391420249835405763L;
	
	private int userId;					//用户ID
	
	private String userName;			//用户名
	
	private String passWord;			//密码
	
	private String realName;			//真实姓名
	
	private String department;	 		//部门名称
	
	private String email;	 			//邮箱
	
	private String mobilePhone;	 		//手机
	
	private String telephone;	 		//电话
	
	private String position;	 		//职位
	
	private Set<RoleEntity> roles;		//关联角色
	
	private TreeSet<MenuEntity> menus = new TreeSet<MenuEntity>(new Comparator<MenuEntity>() {
		@Override
		public int compare(MenuEntity o1, MenuEntity o2) {
			int ret =o1.getSort() - o2.getSort();
			return ret == 0 ? -1 : ret;
		};
	});
	
	private List<FunctionEntity> functions = new ArrayList<FunctionEntity>();

	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Column(name="username",length=40, nullable=false)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name="password",length=32, nullable=false)
	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	@Column(name="real_name",length=40,nullable=false)
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Column(name="department",length=150,nullable=false)
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Column(name="email",length=40,nullable=true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name="mobile_phone",length=20,nullable=true)
	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	@Column(name="telephone",length=20,nullable=true)
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name="position",length=100,nullable=false)
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@ManyToMany(targetEntity=RoleEntity.class,fetch=FetchType.EAGER)
	@JoinTable(
		name="t_user_role", 
		joinColumns=@JoinColumn(name="user_id"), 
		inverseJoinColumns=@JoinColumn(name="role_id")
	)
	public Set<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleEntity> roles) {
		this.roles = roles;
	}

	@Transient
	public TreeSet<MenuEntity> getMenus() {
		return menus;
	}

	public void setMenus(TreeSet<MenuEntity> menus) {
		this.menus = menus;
	}

	@Transient
	public List<FunctionEntity> getFunctions() {
		return functions;
	}

	public void setFunctions(List<FunctionEntity> functions) {
		this.functions = functions;
	}

}
