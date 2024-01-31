package com.motormt.admin.cart;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.motormt.admin.account.AccountService;
import com.motormt.common.entity.Account;

@RestController
public class CartRestController {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private AccountService accountService;
	
	@PostMapping("/cart-add-{IDdichvu}")
	public String addBookToCart(@PathVariable("IDdichvu") Integer IDdichvu, Integer quantity, 
			HttpServletRequest request) {
		quantity =1;
		Account account = getAuthenticatedAccount(request);
		Integer updatedQuantity = cartService.addSer(IDdichvu, quantity, account);
		return "Dịch vụ đã được thêm thành công!";
	}
	
	private Account getAuthenticatedAccount(HttpServletRequest request) {
		String customerName = getUsernameOfAuthenticatedCustomer(request);
		Account account = accountService.getAccountByUsername(customerName);
		String customerEmail = account.getEmail();
		return accountService.getAccountByEmail(customerEmail);
	}
	
	public static String getUsernameOfAuthenticatedCustomer(HttpServletRequest request) {
	    Object principal = request.getUserPrincipal();
	    String customerName = null;
	    if (principal instanceof UsernamePasswordAuthenticationToken) {
	        customerName = request.getUserPrincipal().getName();
	    }
	    return customerName;
	}

	@DeleteMapping("/cart-remove-{IDdichvu}")
	public String removeSer(@PathVariable("IDdichvu") Integer IDdichvu, HttpServletRequest request) {
		Account account = getAuthenticatedAccount(request);
		cartService.removeSer(IDdichvu, account);
		return "Dịch vụ đã được xóa khỏi danh sách của bạn";
	}
}