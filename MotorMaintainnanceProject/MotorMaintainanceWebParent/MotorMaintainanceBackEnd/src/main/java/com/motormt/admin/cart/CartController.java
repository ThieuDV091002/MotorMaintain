package com.motormt.admin.cart;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.motormt.admin.account.AccountService;
import com.motormt.common.entity.Account;
import com.motormt.common.entity.Cart;

@Controller
public class CartController {

	@Autowired
	private CartService service;
	
	@Autowired
	private AccountService accountService;
	
	@GetMapping("/cart")
	public String viewShoppingCart(Model model, HttpServletRequest request) {
		Account account = getAuthenticatedAccount(request);
		List<Cart> cartItems = service.listCartItems(account);
		List<Account> listKhachs = accountService.listAllKhachHangs();
		int Total = 0;
		for(Cart item : cartItems) {
			Total += item.getSubTotal();
		}
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("listKhachs", listKhachs);
		model.addAttribute("Total", Total);
		return "cart/shopping_cart";
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
}
