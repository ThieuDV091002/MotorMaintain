package com.motormt.admin.bill;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.motormt.common.entity.Bill;

public interface BillRepository extends PagingAndSortingRepository<Bill, Integer>{

	@Query("SELECT b FROM Bill b JOIN b.billDetails bd JOIN bd.ser s WHERE b.account.hoten LIKE %?1% OR s.tendichvu LIKE %?1%")
	public Page<Bill> findAll(String keyword, Pageable pageable);
	
	@Query("UPDATE Bill b SET b.isSettled =?2 WHERE b.IDhoadon = ?1")
	@Modifying
	public void updateBillStatus(Integer IDhoadon, boolean isSettled);
}
