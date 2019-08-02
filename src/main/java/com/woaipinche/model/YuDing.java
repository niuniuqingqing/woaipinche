package com.woaipinche.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "YU_DING")
public class YuDing {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name = "price", length = 32)
	private String price;

	@Column(name = "owner", length = 32)
	private String owner;

	@Column(name = "start_point", length = 32)
	private String startPoint;

	@Column(name = "end_point", length = 32)
	private String endPoint;

	@Column(name = "set_out_date", length = 7)
	private Date setOutDate;

	@Column(name = "remaining_seats", length = 32)
	private String remainingSeats;

	@Column(name = "remark", length = 255)
	private String remark;

	@Column(name = "phone", length = 32)
	private String phone;

	@Column(name = "model", length = 32)
	private String model;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(String startPoint) {
		this.startPoint = startPoint;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public Date getSetOutDate() {
		return setOutDate;
	}

	public void setSetOutDate(String setOutDate) {
		try {
			// 2019年07月19日 16时52分
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy年MM月dd日 HH时mm分");
			Date d;
			d = format.parse(setOutDate);
			this.setOutDate = d;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getRemainingSeats() {
		return remainingSeats;
	}

	public void setRemainingSeats(String remainingSeats) {
		this.remainingSeats = remainingSeats;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

}
