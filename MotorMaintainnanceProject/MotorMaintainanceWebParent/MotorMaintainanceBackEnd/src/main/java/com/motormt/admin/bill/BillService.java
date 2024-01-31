package com.motormt.admin.bill;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.motormt.admin.checkout.CheckoutInfo;
import com.motormt.common.entity.Account;
import com.motormt.common.entity.Bill;
import com.motormt.common.entity.BillDetail;
import com.motormt.common.entity.Cart;
import com.motormt.common.entity.Ser;

@Service
@Transactional
public class BillService {
	public static final int BILL_PER_PAGE = 5;
	@Autowired
	private BillRepository repo;
	
	public Page<Bill> listByPage(int pageNum, String keyword){
		Pageable pageable = PageRequest.of(pageNum - 1, BILL_PER_PAGE);
		if(keyword != null) {
			return repo.findAll(keyword, pageable);
		}
		return repo.findAll(pageable);
	}

	public Bill get(Integer IDhoadon) throws BillNotFoundException {
		try {
			return repo.findById(IDhoadon).get();
		} catch (NoSuchElementException ex) {
			throw new BillNotFoundException("Không tìm thấy hóa đơn có ID: " + IDhoadon);
		}
	}

	public void updateBillStatus(Integer IDhoadon, boolean trangthai) {
		repo.updateBillStatus(IDhoadon, trangthai);
	}
	
	public Bill createBill(Account account, String khachhang, List<Cart> cartItems, CheckoutInfo checkoutInfo) {
		Bill newBill= new Bill();
		newBill.setCreateTime(new Date());
		newBill.setAccount(account);
		newBill.setKhachhang(khachhang);
		newBill.setTotal(checkoutInfo.getTotal());
		newBill.setSettled(false);
		
		Set<BillDetail> orderDetails = newBill.getBillDetails();
		
		for(Cart item : cartItems) {
			Ser ser = item.getSer();
			BillDetail billDetail = new BillDetail();
			billDetail.setBill(newBill);
			billDetail.setSer(ser);
			billDetail.setDongia(item.getSubTotal());
			
			orderDetails.add(billDetail);
		}
		
		return repo.save(newBill);
	}

}
