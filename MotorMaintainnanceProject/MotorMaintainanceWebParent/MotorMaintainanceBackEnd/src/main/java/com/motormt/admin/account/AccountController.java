package com.motormt.admin.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.motormt.admin.security.AdminAccountDetails;
import com.motormt.common.entity.Account;

@Controller
public class AccountController {

	@Autowired
	private AccountService service;
	
	@GetMapping("/account")
	public String listFirstPage(Model model) {
		return listByPage(1, model, null);
	}
	
	@GetMapping("/account-page-{pageNum}")
	public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
			@Param("keyword")String keyword) {
		Page<Account> page = service.listByPage(pageNum, keyword);
		List<Account> listAccounts = page.getContent();
		
		long startCount = (pageNum - 1)*AccountService.ACCOUNT_PER_PAGE + 1;
		long endCount = startCount + AccountService.ACCOUNT_PER_PAGE - 1;
		if(endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("listAccounts", listAccounts);
		model.addAttribute("keyword", keyword);
		return "account";
	}
	
	@GetMapping("/account-new")
	public String newAccount(Model model) {
		Account account = new Account();
		account.setTrangthai(true);
		model.addAttribute("account", account);
		return "account_form";
	}
	
	@PostMapping("/account-save")
	public String saveAccount(Account account, RedirectAttributes redirectAttributes) {
		service.save(account);
		redirectAttributes.addFlashAttribute("message", "Tài khoản đã được tạo thành công!");
		return "redirect:account";
	}
	
	@GetMapping("/account-detail-{IDtaikhoan}")
	public String viewAccountDetail(@PathVariable(name = "IDtaikhoan") Integer IDtaikhoan, 
			Model model, 
			RedirectAttributes redirectAttributes) {
		try {
			Account account = service.get(IDtaikhoan);
			model.addAttribute("account", account);
			return "account_detail";
		}catch(AccountNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
			return"redirect:account";
		}
	}
	
	@GetMapping("/account-{IDtaikhoan}-trangthai-{trangthai}")
	public String updateAccountStatus(@PathVariable("IDtaikhoan") Integer IDtaikhoan,
			@PathVariable("trangthai") boolean trangthai, RedirectAttributes redirectAttributes) {
		service.updateAccountStatus(IDtaikhoan, trangthai);
		String status = trangthai ? "được mở khóa" : "bị khóa";
		String message = "Tài khoản có ID " + IDtaikhoan + " đã " + status;
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:account";
	}
	
	@GetMapping("/account-update")
	public String viewDetails(@AuthenticationPrincipal AdminAccountDetails loggedAccount,
			Model model) {
		String email = loggedAccount.getEmail();
		Account account = service.getByEmail(email);
		model.addAttribute("account", account);
		return "account_update";
	}
	
	@PostMapping("/account-updatedetail")
	public String saveAccountDetail(Account account, RedirectAttributes redirectAttributes,
			@AuthenticationPrincipal AdminAccountDetails loggedAccount) {
		service.updateAccount(account);
		loggedAccount.setHoten(account.getHoten());
		redirectAttributes.addFlashAttribute("message", "Tài khoản của bạn đã lưu tạo thành công!");
		return "redirect:/account-update";
	}
	
}
