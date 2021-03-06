package com.kaidin.db.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.kaidin.appframe.service.dao.BaseEntity;
/**
 * EntityCfgUser generated by Hibernate Tools 3.3.0.GA
 * @date 2020-9-1 18:05:22
 */
@Entity
@Table(name="cfg_user"
	,catalog="kaidin"
)
public class EntityCfgUser extends BaseEntity
 {
	private static final long serialVersionUID = 0126L;
	public static final String ENTITY_NAME = "com.kaidin.db.entity.EntityCfgUser";
	
	public static final String P_Name	= "name";
	public static final String P_Password	= "password";
	public static final String P_Alias	= "alias";
	public static final String P_Gender	= "gender";
	public static final String P_Mail	= "mail";
	public static final String P_Birthday	= "birthday";
	public static final String P_Telphone	= "telphone";
	public static final String P_Address	= "address";
	public static final String P_LastLoginTime	= "lastLoginTime";
	public static final String P_PasswordChangeTime	= "passwordChangeTime";
	public static final String P_CreateTime	= "createTime";
	public static final String P_Status	= "status";
	public static final String P_Description	= "description";
	public static final String P_EntityRltUserRoles	= "entityRltUserRoles";

	// 登陆用户名
	private String name;
	// 登陆密码
	private String password;
	// 别名
	private String alias;
	// 性别
	private String gender;
	// 邮箱地址
	private String mail;
	// 生日
	private Date birthday;
	// 电话
	private String telphone;
	// 地址
	private String address;
	// 最后登陆时间
	private Date lastLoginTime;
	// 最后修改时间
	private Date passwordChangeTime;
	// 创建时间
	private Date createTime;
	// 状态
	private String status;
	// 描述信息
	private String description;
	private Set<EntityRltUserRole> entityRltUserRoles= new HashSet<EntityRltUserRole>(0);

	// default constructor
	public EntityCfgUser() {
	}
	public EntityCfgUser(long id, String name) {
		this.id = id;
		this.name = name;
	}
	public EntityCfgUser(long id, String name, String password, String alias, String gender, String mail, Date birthday, String telphone, String address, Date lastLoginTime, Date passwordChangeTime, Date createTime, String status, String description, Set<EntityRltUserRole> entityRltUserRoles) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.alias = alias;
		this.gender = gender;
		this.mail = mail;
		this.birthday = birthday;
		this.telphone = telphone;
		this.address = address;
		this.lastLoginTime = lastLoginTime;
		this.passwordChangeTime = passwordChangeTime;
		this.createTime = createTime;
		this.status = status;
		this.description = description;
		this.entityRltUserRoles = entityRltUserRoles;
	}

    
    @Column(name="name", nullable=false, length=32)
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
    
    @Column(name="password", length=32)
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    
    @Column(name="alias", length=32)
	public String getAlias() {
		return this.alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
    
    @Column(name="gender", length=10)
	public String getGender() {
		return this.gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
    
    @Column(name="mail", length=128)
	public String getMail() {
		return this.mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="birthday", length=10)
	public Date getBirthday() {
		return this.birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
    
    @Column(name="telphone", length=32)
	public String getTelphone() {
		return this.telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
    
    @Column(name="address", length=256)
	public String getAddress() {
		return this.address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="last_login_time", length=19)
	public Date getLastLoginTime() {
		return this.lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="password_change_time", length=19)
	public Date getPasswordChangeTime() {
		return this.passwordChangeTime;
	}
	public void setPasswordChangeTime(Date passwordChangeTime) {
		this.passwordChangeTime = passwordChangeTime;
	}
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="create_time", length=19)
	public Date getCreateTime() {
		return this.createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
    
    @Column(name="status", length=8)
	public String getStatus() {
		return this.status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
    
    @Column(name="description", length=128)
	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@OneToMany(fetch=FetchType.LAZY, mappedBy="entityCfgUser")
	public Set<EntityRltUserRole> getEntityRltUserRoles() {
		return this.entityRltUserRoles;
	}
	public void setEntityRltUserRoles(Set<EntityRltUserRole> entityRltUserRoles) {
		this.entityRltUserRoles = entityRltUserRoles;
	}


	public boolean equals(Object other) {
		if (this == other) return true;
		if (other == null) return false;
		if (!(other instanceof EntityCfgUser)) return false;
		EntityCfgUser castOther = (EntityCfgUser) other;
		return this.getId() == castOther.getId();
	}
	
	public int hashCode() {
		String code = ENTITY_NAME + ":" + this.getId();
		return code.hashCode();
	}
	
	/**
	 * getConstraintSet
	 * @return Set<BaseEntity>
	 */
	@Transient
	public Set<BaseEntity> getConstraintSet() {
		Set<BaseEntity> result = new HashSet<>();
		
		result.addAll(this.entityRltUserRoles);
		
		return result;
	}
}
