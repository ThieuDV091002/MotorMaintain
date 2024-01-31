package com.motormt.site.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.motormt.common.entity.Account;
import com.motormt.site.security.CustomerUserDetails;

@Controller
public class CustomerController {

	@Autowired
	private CustomerService service;
	
	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		Account account = new Account();
		model.addAttribute("account", account);
		
		return "register";
	}
	
	@PostMapping("/create-customer")
	public String saveAccount(Account account, RedirectAttributes redirectAttributes) {
		service.save(account);
		redirectAttributes.addFlashAttribute("message", "Tài khoản đã được tạo thành công!");
		return "login";
	}
	
	@GetMapping("/customer-update")
	public String viewDetails(@AuthenticationPrincipal CustomerUserDetails loggedAccount,
			Model model) {
		String email = loggedAccount.getEmail();
		Account account = service.getAccountByEmail(email);
		model.addAttribute("account", account);
		return "customer-update";
	}
	
	@PostMapping("/customer-updatedetail")
	public String saveAccountDetail(Account account, RedirectAttributes redirectAttributes,
			@AuthenticationPrincipal CustomerUserDetails loggedAccount) {
		service.updateAccount(account);
		loggedAccount.setHoten(account.getHoten());
		redirectAttributes.addFlashAttribute("message", "Tài khoản của bạn đã lưu tạo thành công!");
		return "redirect:/";
	}
}
