package com.motormt.admin.checkout;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.motormt.admin.account.AccountService;
import com.motormt.admin.bill.BillService;
import com.motormt.admin.cart.CartService;
import com.motormt.common.entity.Account;
import com.motormt.common.entity.Cart;

@Controller
public class CheckoutController {

	@Autowired
	private CheckoutService checkoutService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private CartService cartService;
	@Autowired
	private BillService billService;
	
	@GetMapping("/checkout")
	public String viewCheckoutPage(Model model, HttpServletRequest request) {
		Account account = getAuthenticatedAccount(request);
		List<Cart> cartItems = cartService.listCartItems(account);
		List<Account> listKhachs = accountService.listAllKhachHangs();
		
		CheckoutInfo checkoutInfo = checkoutService.prepareCheckout(cartItems);
		model.addAttribute("account", account);
		model.addAttribute("listKhachs", listKhachs);
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("checkoutInfo", checkoutInfo);
		return "bill/bill_checkout";
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
	
	@PostMapping("/place-order")
	public String placeOrder(HttpServletRequest request) {
		String ten = request.getParameter("khachhang");
		String khachhang = String.valueOf(ten);	
		Account account = getAuthenticatedAccount(request);
		List<Cart> cartItems = cartService.listCartItems(account);
		CheckoutInfo checkoutInfo = checkoutService.prepareCheckout(cartItems);
		billService.createBill(account, khachhang, cartItems, checkoutInfo);
		cartService.deleteByAccount(account);
		return "bill/bill";
	}
	
}
