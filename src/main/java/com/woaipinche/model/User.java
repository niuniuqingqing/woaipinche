package com.woaipinche.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class User {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	@Id   
	private String id;

	@Column(name = "name", length = 32)
	private String name;

	@Column(name = "phone", length = 32)
	private String phone;

	@Column(name = "is_huiyuan", length = 32)
	private String isHuiyuan;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIsHuiyuan() {
		return isHuiyuan;
	}

	public void setIsHuiyuan(String isHuiyuan) {
		this.isHuiyuan = isHuiyuan;
	}



}
