package com.motormt.common.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="bill")
public class Bill {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer IDhoadon;
	
	@ManyToOne
	@JoinColumn(name="IDtaikhoan")
	private Account account;
	
	private String khachhang;
	
	private Date createTime;
	private int total;
	private boolean isSettled;
	@OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
	private Set<BillDetail> billDetails = new HashSet<>();
	
	public Integer getIDhoadon() {
		return IDhoadon;
	}
	public void setIDhoadon(Integer iDhoadon) {
		IDhoadon = iDhoadon;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public Set<BillDetail> getBillDetails() {
		return billDetails;
	}
	public void setBillDetails(Set<BillDetail> billDetails) {
		this.billDetails = billDetails;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public String getKhachhang() {
		return khachhang;
	}
	public void setKhachhang(String khachhang) {
		this.khachhang = khachhang;
	}
	public boolean isSettled() {
		return isSettled;
	}
	public void setSettled(boolean isSettled) {
		this.isSettled = isSettled;
	}
	
}
