package com.motormt.admin.cart;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.motormt.admin.service.SerRepository;
import com.motormt.common.entity.Account;
import com.motormt.common.entity.Cart;
import com.motormt.common.entity.Ser;

@Service
@Transactional
public class CartService {

	@Autowired
	private CartRepository repo;
	
	public Integer addSer(Integer IDdichvu, Integer quantity, Account account) {
		Integer updatedQuantity = quantity;
		Ser ser = new Ser(IDdichvu);
		Cart shoppingCart = repo.findByAccountAndSer(account, ser);
		
		if(shoppingCart == null) {
			shoppingCart = new Cart();
			shoppingCart.setAccount(account);
			shoppingCart.setSer(ser);
		}else {
			updatedQuantity = shoppingCart.getQuantity() + quantity;
		}
		
		shoppingCart.setQuantity(updatedQuantity);
		repo.save(shoppingCart);
		return updatedQuantity;
	}
	
	public List<Cart> listCartItems(Account account){
		return repo.findByAccount(account);
	}
	
	public void removeSer(Integer IDdichvu, Account account) {
		repo.deleteByAccountAndSer(account.getIDtaikhoan(), IDdichvu);
	}
	
	public void deleteByAccount(Account account) {
		repo.deleteByAccount(account.getIDtaikhoan());
	}
}
