package com.woaipinche.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PINLUN")
public class Pinlun {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	@Id   
	private String id;

	@Column(name = "user_id", length = 32)
	private String userId;

	@Column(name = "content", length = 4000)
	private String content;
	
	@Column(name = "fabu_date", length = 7)
	private Date fabuDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getFabuDate() {
		return fabuDate;
	}

	public void setFabuDate(Date fabuDate) {
		this.fabuDate = fabuDate;
	}

	

}
