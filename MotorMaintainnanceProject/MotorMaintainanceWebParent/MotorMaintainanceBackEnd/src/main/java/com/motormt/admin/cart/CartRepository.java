package com.motormt.admin.cart;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.motormt.common.entity.Account;
import com.motormt.common.entity.Cart;
import com.motormt.common.entity.Ser;

public interface CartRepository extends CrudRepository<Cart, Integer>{

	public List<Cart> findByAccount(Account account);
	
	public Cart findByAccountAndSer(Account account, Ser ser);
	
	@Modifying
	@Query("DELETE FROM Cart c WHERE c.account.IDtaikhoan = ?1 AND c.ser.IDdichvu = ?2")
	public void deleteByAccountAndSer(Integer IDtaikhoan, Integer IDdichvu);
	
	@Query("DELETE Cart c WHERE c.account.IDtaikhoan = ?1")
	@Modifying
	public void deleteByAccount(Integer IDtaikhoan);
}
