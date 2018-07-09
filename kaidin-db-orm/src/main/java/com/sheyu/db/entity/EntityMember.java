package com.sheyu.db.entity;

import java.util.Date;

public class EntityMember {
	private long id;
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
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public Date getPasswordChangeTime() {
		return passwordChangeTime;
	}
	public void setPasswordChangeTime(Date passwordChangeTime) {
		this.passwordChangeTime = passwordChangeTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
