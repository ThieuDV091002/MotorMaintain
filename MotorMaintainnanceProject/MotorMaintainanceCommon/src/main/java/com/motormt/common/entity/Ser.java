package com.motormt.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="service")
public class Ser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer IDdichvu;
	@Column(length = 64)
	private String hinhanh;
	@Column(length = 128, nullable = false)
	private String tendichvu;
	@Column(length = 128, nullable = false)
	private String loaidv;
	@Column(length = 5120, nullable = false)
	private String mota;
	@Column(length = 8, nullable = false)
	private Integer dongia;
	private Integer price;
	@Column(name = "discount_percent")
	private float discountPercent;
	@Column(length = 128, nullable = false)
	private String hotro;
	public Ser() {
	}
	public Ser(Integer iDdichvu) {
		this.IDdichvu = iDdichvu;
	}

	public Integer getIDdichvu() {
		return IDdichvu;
	}
	public void setIDdichvu(Integer iDdichvu) {
		this.IDdichvu = iDdichvu;
	}
	public String getHinhanh() {
		return hinhanh;
	}
	public void setHinhanh(String hinhanh) {
		this.hinhanh = hinhanh;
	}
	public String getTendichvu() {
		return tendichvu;
	}
	public void setTendichvu(String tendichvu) {
		this.tendichvu = tendichvu;
	}
	public String getLoaidv() {
		return loaidv;
	}
	public void setLoaidv(String loaidv) {
		this.loaidv = loaidv;
	}
	public String getMota() {
		return mota;
	}
	public void setMota(String mota) {
		this.mota = mota;
	}
	public Integer getDongia() {
		return dongia;
	}
	public void setDongia(Integer dongia) {
		this.dongia = dongia;
	}
	public String getHotro() {
		return hotro;
	}
	public void setHotro(String hotro) {
		this.hotro = hotro;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public float getDiscountPercent() {
		return discountPercent;
	}
	public void setDiscountPercent(float discountPercent) {
		this.discountPercent = discountPercent;
	}
	@Transient
	public String getSerPhotoPath() {
		if(hinhanh == null) return "image/noimage.jpg";
		return "/service-photos/" + this.IDdichvu + "/" + this.hinhanh;
	}
}
