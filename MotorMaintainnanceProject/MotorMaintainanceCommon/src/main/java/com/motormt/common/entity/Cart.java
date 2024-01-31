package com.motormt.common.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="cart")
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	@ManyToOne
	@JoinColumn(name="IDtaikhoan")
	private Account account;
	@ManyToOne
	@JoinColumn(name="IDdichvu")
	private Ser ser;
	private Integer quantity;
	public Cart() {
	}
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Ser getSer() {
		return ser;
	}
	public void setSer(Ser ser) {
		this.ser = ser;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	@Transient
	public int getSubTotal() {
		return ser.getDongia()*quantity;
	}
}
