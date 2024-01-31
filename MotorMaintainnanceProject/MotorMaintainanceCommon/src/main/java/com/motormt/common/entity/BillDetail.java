package com.motormt.common.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="bill_detail")
public class BillDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	private int dongia;
	
	@ManyToOne
	@JoinColumn(name="IDdichvu")
	private Ser ser;
	
	@ManyToOne
	@JoinColumn(name="IDhoadon")
	private Bill bill;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public int getDongia() {
		return dongia;
	}

	public void setDongia(int dongia) {
		this.dongia = dongia;
	}

	public Ser getSer() {
		return ser;
	}

	public void setSer(Ser ser) {
		this.ser = ser;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}
	
}
